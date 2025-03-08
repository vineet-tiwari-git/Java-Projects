package com.vineet.datafetcher;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.vineet.datafetcher.utils.ApplicationContext;
import com.vineet.datafetcher.utils.Util;

public class DataFetcherWithParams {

	private DataBaseConnPool connectionPool = null;
	private PrintStream out = null;
	private String sql = null;
	boolean logMode = false;
	private String outputDataDelimiter = ",";

	public void start(String[] args, InputStream in) throws Exception {

		out = new PrintStream(System.out, false, "UTF-8");
		logMode = Boolean.valueOf(Util.getArg(args, "logMode"));
		System.out.println("logMode: " + logMode);

		sql = ApplicationContext.getProperty("SQL");
		if (StringUtils.isNotBlank(ApplicationContext.getProperty("output.data.delimiter"))) {
			outputDataDelimiter = ApplicationContext.getProperty("output.data.delimiter");
		}

		System.out.println("Constructor SQL: " + sql);

		if (StringUtils.isEmpty(sql)) {
			sql = new String(Files.readAllBytes(Paths.get(ApplicationContext.getProperty("sql.file.path"))));

			System.out.println("Constructor SQL from file: " + sql);

			if (StringUtils.isBlank(sql)) {
				throw new IllegalArgumentException("SQL can't be empty");
			}
		}

		Class.forName("oracle.jdbc.driver.OracleDriver");

		String connString = ApplicationContext.getProperty("connString");
		String username = ApplicationContext.getProperty("username");
		String password = ApplicationContext.getProperty("password");
		int threadpoolsize = Integer.parseInt(Util.getArg(args, "tps"));

		this.initDBPool(connString, username, password, threadpoolsize);

		if (StringUtils.isNotBlank(ApplicationContext.getProperty("output.data.header"))) {
			System.out.println(ApplicationContext.getProperty("output.data.header"));
		}

		this.readData(threadpoolsize);
		System.out.println("DataFetcherWithParams Over");
	}

	private void initDBPool(String connString, String username, String password, int threadpoolsize) throws Exception {
		this.connectionPool = new DataBaseConnPool(connString, username, password, threadpoolsize);
	}

	private String getQuotedValues(String s) {
		StringBuilder stringBuilder = new StringBuilder();

		AtomicInteger isFirstLine = new AtomicInteger(0);
		Arrays.asList(StringUtils.split(s, ",")).forEach(a -> {

			if (isFirstLine.intValue() > 0) {
				stringBuilder.append(",");
			}
			stringBuilder.append("'").append(a).append("'");
			isFirstLine.getAndIncrement();
		});

		return stringBuilder.toString();
	}

	private Optional<Boolean> processLineWithStatement(String readLine) throws Exception {

		try {
			Connection con = this.getConnection();

			Statement stmt = con.createStatement();
			
			//not using prepared statement to be able to deal with different types
			String updateSQL = StringUtils.replace(this.sql, "PLACE_HOLDER", getQuotedValues(readLine));

			if (logMode) {
				System.out.println("processLineWithStatement.updateSQL SQL: " + updateSQL);
			}

			ResultSet rs = stmt.executeQuery(updateSQL);
			ResultSetMetaData resultSetMetaData = rs.getMetaData();
			final int columnCount = resultSetMetaData.getColumnCount();

			while (rs.next()) {
				List values = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					String val = getValue(rs, i);
					values.add(val);
				}
				Object collect = values.stream().collect(Collectors.joining("\"" + outputDataDelimiter + "\""));
				out.println("\"" + collect.toString() + "\"");
			}
			rs.close();
			this.releaseConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exiting");
			System.exit(0);
		}
		return Optional.ofNullable(true);
	}

	private String getValue(ResultSet rs, int i) throws Exception {
		String val = rs.getObject(i) != null ? rs.getObject(i).toString() : "";
		val = "\"" + val + "\"";

		return val;
	}

	public Optional<Boolean> readData(int threadpoolsize) throws Exception {

		final ExecutorService executorService = Executors.newFixedThreadPool(threadpoolsize);
		final CompletionService<Optional<Boolean>> completionService = new ExecutorCompletionService<>(executorService);

		Files.readAllLines(Paths.get(ApplicationContext.getProperty("InputFilePath"))).stream()
				.forEach(s -> completionService.submit(() -> processLineWithStatement(s)));

		if (!executorService.isShutdown()) {
			executorService.shutdown();
		}

		System.out.println("Over");
		return Optional.ofNullable(true);
	}

	public Connection getConnection() throws SQLException {

		return this.connectionPool.getConnection();
	}

	public void releaseConnection(Connection conn) throws SQLException {

		this.connectionPool.returnConnection(conn);
	}

}
