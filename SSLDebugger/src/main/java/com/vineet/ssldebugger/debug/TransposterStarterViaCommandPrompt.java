package com.vineet.ssldebugger.debug;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import com.vineet.ssldebugger.ui.TransposterView;

/**
 * Built as an entry to Transposter via command prompt. But the UI should be
 * used. Pleae refer to @link {@link TransposterView}
 *
 *
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 *
 */
@Deprecated
public class TransposterStarterViaCommandPrompt {

	private final String	ARG_PUBLIC_CERT_PATH		= "publicCertPath";
	private final String	ARG_PRIVATE_KEY_PATH		= "privateKeyPath";
	private final String	ARG_PRIVATE_KEY_PASSWORD	= "keyStorePassword";
	private final String	ARG_DATA_FILE_PATH			= "dataFilePath";
	private final String	ARG_END_POINT				= "endpoint";
	private final String	ARG_END_POINT_USER_NAME		= "endpointUserName";
	private final String	ARG_END_POINT_USER_PASSWORD	= "endpointPassword";
	private final String	ARG_HTTP_METHOD				= "httpMethod";
	private final String	ARG_HTTP_HEADERS			= "httpHeaders";

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

	public void attemptDataPost(final String args[]) throws Exception {

		final String endpoint = getArg(args, ARG_END_POINT);
		final String publicKeyPath = getArg(args, ARG_PUBLIC_CERT_PATH);
		final String privateKeyPath = getArg(args, ARG_PRIVATE_KEY_PATH);
		final String keyStorePassword = getArg(args, ARG_PRIVATE_KEY_PASSWORD);
		final String endpointUserName = getArg(args, ARG_END_POINT_USER_NAME);
		final String endpointPassword = getArg(args, ARG_END_POINT_USER_PASSWORD);
		final String dataFile = getArg(args, ARG_DATA_FILE_PATH);

		final String httpMethod = getArg(args, ARG_HTTP_METHOD);
		final String httpHeaders = getArg(args, ARG_HTTP_HEADERS);

		/*
		 * TransportRequest request = new TransportRequest(endpoint,
		 * publicKeyPath, privateKeyPath, keyStorePassword, endpointUserName,
		 * endpointPassword, dataFile, httpHeaders, httpMethod);
		 *
		 * Transporter transporter = new Transporter();
		 * transporter.doWork(request);
		 */
	}

	public static void main(String args[]) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException{

		String key = "test";

		String value = "test1234";
		byte[] keyBytes = key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

		 Mac mac = Mac.getInstance("HmacSHA1");
         mac.init(signingKey);

         byte[] rawHmac = mac.doFinal(value.getBytes());

         byte[] hexBytes = new org.apache.commons.codec.binary.Hex().encode(rawHmac);

         String output = new String(hexBytes, "UTF-8");
         System.out.println(output);

	}
}
