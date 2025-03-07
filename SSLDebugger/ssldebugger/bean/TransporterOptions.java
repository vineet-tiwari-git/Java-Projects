package com.vineet.ssldebugger.bean;

public class TransporterOptions {
	
	private boolean printRequesteHeaders;

	private boolean printResponseHeaders;

	private boolean followRedirects;
	
	public TransporterOptions(final boolean printRequesteHeaders, final boolean printResponseHeaders, final boolean followRedirects) {
		this.printRequesteHeaders = printRequesteHeaders;
		this.printResponseHeaders = printResponseHeaders;
		this.followRedirects = followRedirects;
	}

	/**
	 * @return the printRequesteHeaders
	 */
	public boolean isPrintRequesteHeaders() {
		return printRequesteHeaders;
	}

	/**
	 * @param printRequesteHeaders the printRequesteHeaders to set
	 */
	public void setPrintRequesteHeaders(boolean printRequesteHeaders) {
		this.printRequesteHeaders = printRequesteHeaders;
	}

	/**
	 * @return the printResponseHeaders
	 */
	public boolean isPrintResponseHeaders() {
		return printResponseHeaders;
	}

	/**
	 * @param printResponseHeaders the printResponseHeaders to set
	 */
	public void setPrintResponseHeaders(boolean printResponseHeaders) {
		this.printResponseHeaders = printResponseHeaders;
	}

	/**
	 * @return the followRedirects
	 */
	public boolean isFollowRedirects() {
		return followRedirects;
	}

	/**
	 * @param followRedirects the followRedirects to set
	 */
	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}
}
