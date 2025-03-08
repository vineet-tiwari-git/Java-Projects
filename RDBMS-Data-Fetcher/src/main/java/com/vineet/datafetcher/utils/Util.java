package com.vineet.datafetcher.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

/**
 * class for Miscellaneous utility
 *
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 *
 */
public class Util {

    public static Stream<String> getLinesStreamForFile(final String filePath) throws Exception {
        final Path path = Paths.get(filePath);
        return Files.lines(path);
    }
    
	public static String getFileNameWithoutExtension(String fileNameWithExtension) {
		return StringUtils.substringBefore(fileNameWithExtension, ".");
	}

	public static String fileToString(final File inputFile) throws IOException {
		return fileToString(inputFile, StandardCharsets.UTF_8.name());
	}

	public static String unquote(String data) {
		return StringUtils.strip(data, "\"");
	}

	public static String quote(String data) {
		return "\"" + data + "\"";
	}

	public static String fileToString(final File inputFile, final String encoding) throws IOException {

		final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), encoding));

		final StringBuffer buffer = new StringBuffer();
		String readLine = "";
		while ((readLine = br.readLine()) != null) {
			buffer.append(readLine);
		}
		br.close();
		return buffer.toString();
	}

	public static String removeNewLines(final String data) {

		String dataToReturn = StringUtils.remove(data, "\n");
		dataToReturn = StringUtils.remove(dataToReturn, "\r");
		return data;
	}

	public static String removeAllWhiteSpaces(final String data) {
		if (data != null) {
			return data.replaceAll("\\s+", "");
		}
		return data;
	}

	/**
	 * @param args
	 * @param argName
	 * @return
	 */
	public static String getArg(final String args[], final String argName) {
		String result = null;
		if (null != args) {
			for (final String str : args) {
				if (StringUtils.startsWithIgnoreCase(str, argName + "=")) {
					result = StringUtils.substringAfterLast(str, "=");
				}
			}
		}
		return result;
	}

	/**
	 * @param readline
	 * @param separator
	 * @param debug
	 * @return
	 */
	/*
	 * public static String getMaskedCSVLine(final String readline, final String
	 * separator, final boolean debug) { final StringBuffer buffer = new
	 * StringBuffer();
	 *
	 * final String[] csvDataInArray = readline.split("\\" + separator);
	 *
	 * for (int i = 0; i < csvDataInArray.length; i++) { if ((i ==
	 * ChaseDataConstants.CC_INDEX) || (i ==
	 * ChaseDataConstants.EXPIRATION_INDEX)) {
	 *
	 *
	 * if (debug) { System.out.println(i + ":" + csvDataInArray[i]); }
	 *
	 * buffer.append("*****Masked******" + "|"); } else {
	 * buffer.append(csvDataInArray[i] + "|");
	 *
	 * if (debug) { System.out.println(i + ":" + csvDataInArray[i]); }
	 *
	 * } }
	 *
	 * buffer.append(System.lineSeparator()); return buffer.toString(); }
	 */
}
