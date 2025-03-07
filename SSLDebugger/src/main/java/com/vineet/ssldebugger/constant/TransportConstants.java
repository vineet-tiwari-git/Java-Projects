package com.vineet.ssldebugger.constant;

/**
 *
 * Constants.
 *
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 */
public class TransportConstants {

	public static final String JKS_KEYSTORE_TYPE = "JKS";
	public static final String PKCS12_KEY_STORE_TYPE = "PKCS12";
	public static final String CERTIFICATE_ALIAS = "certificate";

	public static final String DEFAULT_CHARACER_ENCODING = "UTF-8";

	public static final String PUBLIC_CERT_DEFAULT_PASSWORD = "Jarvis@1234";

	// input arguments name constants
	public static final String ARG_PUBLIC_CERT_PATH = "publicCertPath";
	public static final String ARG_public_KEY_PATH = "publicKeyPath";
	public static final String ARG_public_KEY_PASSWORD = "keyStorePassword";
	public static final String ARG_DATA_FILE_PATH = "dataFilePath";
	public static final String ARG_END_POINT = "endpoint";
	public static final String ARG_END_POINT_USER_NAME = "endpointUserName";
	public static final String ARG_END_POINT_USER_PASSWORD = "endpointPassword";
	public static final String ARG_HTTP_METHOD = "httpMethod";
	public static final String ARG_HTTP_HEADERS = "httpHeaders";
	public static final String ARG_RESOURCE_FILE_BASE = "resourceFileBase";

	public static final String TRUST_CERT_FILE_NAME = "temp-trust.jks";
	public static final String PRIVATE_CERT_FILE_NAME = "temp-key.jks";

	public static final String SUN_X509_IDENTIFIER = "SunX509";
	public static final String TLS_PROTOCOL_IDENTIFIER = "TLS";

	public static final String CLIENT_AUTH_HTTPS_PROTOCOL_IDENTIFIER = "clientauthhttps";
	public static final String SECURE_PORT_NUMBER = "443";
	public static final String LINIENT_HTTPS_PROTOCOL_IDENTIFIER = "linienthttps";

	public static final String SERVER_TRUST_PROTOCOL_IDENTIFIER = "servertrusthttps";
	public static final String JAVAX_NET_DEBUG_VM_ARGUMENT = "javax.net.debug";

}
