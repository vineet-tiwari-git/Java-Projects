package com.vineet.datafetcher;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

import com.vineet.datafetcher.utils.ApplicationContext;
import com.vineet.datafetcher.utils.Util;

public class SimpleQueryDataFetcher {

	private DataBaseConnPool connectionPool = null;

	private String sql = null;
	private PrintStream out = null;
	private String outputDataDelimiter = ",";

	public void start(String[] args) throws Exception {

		out = new PrintStream(System.out, false, "UTF-8");
		sql = ApplicationContext.getProperty("SQL");
		if (StringUtils.isEmpty(sql)) {
			throw new IllegalArgumentException("SQL is Null");
		}

		if (StringUtils.isNotBlank(ApplicationContext.getProperty("output.data.delimiter"))) {
			outputDataDelimiter = ApplicationContext.getProperty("output.data.delimiter");
		}

		if (StringUtils.isEmpty(sql)) {
			sql = new String(Files.readAllBytes(Paths.get(ApplicationContext.getProperty("sql.file.path"))));

			System.out.println("Constructor SQL from file: " + sql);

			if (StringUtils.isBlank(sql)) {
				throw new IllegalArgumentException("SQL can't be empty");
			}
		}

		System.out.println("SQL Read:" + sql);
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String connString = ApplicationContext.getProperty("connString");
		System.out.println("connString Read:" + connString);
		String username = ApplicationContext.getProperty("username");
		String password = ApplicationContext.getProperty("password");
		int threadpoolsize = Integer.parseInt(Util.getArg(args, "tps"));

		this.initDBPool(connString, username, password, threadpoolsize);
		System.out.println("readData");
		this.fetchData(sql);
		System.out.println("main Over");
	}

	private void initDBPool(String connString, String username, String password, int threadpoolsize) throws Exception {
		this.connectionPool = new DataBaseConnPool(connString, username, password, threadpoolsize);
	}

	public Optional<Boolean> fetchData(String updatedSQL) throws Exception {

		System.out.println("updatedSQL:" + updatedSQL);
		if (StringUtils.isBlank(updatedSQL)) {
			updatedSQL = "select      sli.subscription_id,     req.site_id from     sub_subscription_li_item sli, 	req_line_item rli, 	req_requisition req, 	usr_address uadr where  	sli.creation_Date > sysdate - 365 AND sli.line_item_type='ORIGINAL' AND sli.initial_is_automatic='1' AND sli.expiration_Date - activation_Date > 35 AND rli.line_item_id = sli.req_line_item_id AND req.requisition_id = rli.requisition_id AND uadr.address_id = req.bill_to_address_id AND uadr.country='DE'";
		}

		Connection con = this.getConnection();
		try (Statement stmt = con.prepareStatement(updatedSQL)) {
			ResultSet rs = stmt.executeQuery(updatedSQL);
			ResultSetMetaData resultSetMetaData = rs.getMetaData();
			final int columnCount = resultSetMetaData.getColumnCount();

			while (rs.next()) {
				List values = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					values.add((rs.getObject(i) != null ? rs.getObject(i).toString() : "null"));
				}
				Object collect = values.stream().collect(Collectors.joining("\"" + outputDataDelimiter + "\""));
				System.out.println("\"" + collect.toString() + "\"");
			}
			// rs.close();
			this.releaseConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exiting");
			System.exit(0);
		}

		return Optional.ofNullable(true);

	}

	public Connection getConnection() throws SQLException {

		return this.connectionPool.getConnection();
	}

	public void releaseConnection(Connection conn) throws SQLException {

		this.connectionPool.returnConnection(conn);
	}

}
