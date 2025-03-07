package com.vineet.ssldebugger.constant;

/**
 * Constants.
 *
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 * */
public enum TransportRequestFieldNames {

	END_POINT("END_POINT"),
	PUBLIC_CERT_PATH("PUBLIC_CERT_PATH"),
	PRIVATE_CERT_PATH("PRIVATE_CERT_PATH"),
	PRIVATE_CERT_PASSWORD("PRIVATE_CERT_PASSWORD"),
	ENDPOINT_USERNAME("ENDPOINT_USERNAME"),
	ENDPOINT_PASSWORD("ENDPOINT_PASSWORD"),
	DATA_FILE_PATH("DATA_FILE_PATH"),
	HTTP_HEADERS("HTTP_HEADERS"),
	HTTP_METHOD_TYPE("HTTP_METHOD_TYPE"),
	IS_SSL_ENABLED("IS_SSL_ENABLED"),
	IS_SERVER_CERT_VALIDATION("IS_SERVER_CERT_VALIDATION"),
	IS_LINIENT_SSL("IS_LINIENT_SSL"),
	TIME_OUT("TIME_OUT");

	private final String	name;

	private TransportRequestFieldNames(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
