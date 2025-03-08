package com.vineet.datafetcher;

import org.apache.commons.lang3.StringUtils;

import com.vineet.datafetcher.utils.ApplicationContext;

public class Starter {

	public static void main(String[] args) throws Exception {
		ApplicationContext.initialize("settings.properties");
		String mode = ApplicationContext.getProperty("mode");
		String SQL = ApplicationContext.getProperty("SQL");
		System.out.println("Mode:" + mode);
		System.out.println("SQL:" + SQL);
		if (StringUtils.equalsIgnoreCase(mode, "no-params")) {
			SimpleQueryDataFetcher dataFetcher = new SimpleQueryDataFetcher();
			dataFetcher.start(args);
		} else {
			DataFetcherWithParams userDataFinder = new DataFetcherWithParams();
			userDataFinder.start(args, System.in);
		}
		System.out.println("Processing complete");
	}
}
