package com.vineet.ssldebugger.ui.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {

	public static void saveFile(String contents, String fileLocation) throws IOException {

		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(fileLocation));
			writer.write(contents);
			writer.flush();
		} finally {
			if (writer != null){
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}

	}

	public static String fileToString(File inputFile, String encoding)
			throws IOException {
		if (inputFile == null) {
			return null;
		}

		BufferedReader br = null;
		if (encoding != null)
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					inputFile), encoding));
		else {
			br = new BufferedReader(new FileReader(inputFile));
		}

		StringBuffer buffer = new StringBuffer();
		char[] chars = new char[4096];
		int numRead = br.read(chars);
		while (numRead >= 0) {
			buffer.append(chars, 0, numRead);
			numRead = br.read(chars);
		}
		br.close();
		return buffer.toString();
	}
}
