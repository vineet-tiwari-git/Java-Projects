package com.vineet.ssldebugger.debug;

import java.io.File;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpsURL;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang3.StringUtils;

import com.vineet.ssldebugger.bean.SSLTransportConext;
import com.vineet.ssldebugger.bean.TransportRequest;
import com.vineet.ssldebugger.bean.TransporterOptions;
import com.vineet.ssldebugger.constant.TransportConstants;
import com.vineet.ssldebugger.socket.factory.LenientSSLProtocolSocketFactory;
import com.vineet.ssldebugger.socket.factory.LocalClientAuthenticationSSLProtocolSocketFactory;
import com.vineet.ssldebugger.socket.factory.ServerTrustValidationSSLSocketFactory;
import com.vineet.ssldebugger.ui.util.Util;

/**
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 * 
 */
public class Transporter {

	private TransporterOptions transporterOptions;
	
	public Transporter(TransporterOptions transporterOptions) {
		this.transporterOptions = transporterOptions;
	}
	
	

	public void doWork(final TransportRequest request) throws Exception {

		SSLTransportConext context = null;
		try {

			// Import cerifiticates
			if (request.isSSLEnabled()) {
				context = this.importCertificatesAndCreateSSLContext(request);
			} else if (request.isServercertValidationEnabled()) {
				context = this.importServerCertificateAndCreateSSLContext(request);
			}

			// get data to send.
			final String data = Util.fileToString(new File(request.getDataFilePath()), TransportConstants.DEFAULT_CHARACER_ENCODING);

			// endpoint
			final String endpoint = this.processEndpointScheme(request.getEndpoint(), request);

			System.out.println("endpoint " + endpoint);

			// create an Http Client
			final HttpClient localizedHttpClient = this.createHttpClient(context, request.getTimeOut());

			// Identify the HTTP Method
			final HttpMethod method = this.createHttpMethod(endpoint, data, request.getHttpMethodType());

			// Add Headers
			this.addHttpHeaders(method, this.createHeaderMap(request.getHttpHeaders()));
			method.setFollowRedirects(this.transporterOptions.isFollowRedirects());

			// Add Authorization Header.
			if (StringUtils.isNotEmpty(request.getEndpointUserName())
					&& StringUtils.isNotEmpty(request.getEndpointPassword())) {
				this.handleBasicAuth(localizedHttpClient, method, request.getEndpointUserName(), request.getEndpointPassword());
			}

			// send the data
			System.out.println(" Attempting to send the data ");
			final int status = localizedHttpClient.executeMethod(method);

			System.out.println("Status Code Received --> " + status);
			System.out.println();
			
			if (this.transporterOptions.isPrintRequesteHeaders()) {
				System.out.println("Request Headers: ");
				Header[] headers = method.getRequestHeaders();
				for (Header h : headers) {
					System.out.println(h.getName()  + ":" + h.getValue());
				}
				System.out.println();
			}
			
			if (this.transporterOptions.isPrintResponseHeaders()) {
				System.out.println("Response Headers: ");
				Header[] headers = method.getResponseHeaders();
				for (Header h : headers) {
					System.out.println(h.getName()  + ":" + h.getValue());
				}
				System.out.println();
			}
			

			final String responseBody = new String(method.getResponseBody(), TransportConstants.DEFAULT_CHARACER_ENCODING);
			if (!StringUtils.isEmpty(responseBody)) {
				System.out.println("Response Received --> ");
				System.out.println(responseBody);
			} else {
				System.out.println("Empty response body");
			}

		} catch (final Exception exec) {
			throw exec;
		} finally {
			if (request.isSSLEnabled() && (context != null)) {
				this.deleteTempSSLFiles(context);
			}
		}
	}

	private void deleteTempSSLFiles(final SSLTransportConext context) {
		this.deleteFileIfExists(context.getTrustStoreFile());
		this.deleteFileIfExists(context.getKeyStoreFile());
	}

	private void addHttpHeaders(final HttpMethod method, final Map headers) {
		if ((headers != null) && (headers.size() > 0)) {
			for (final Iterator i = headers.keySet().iterator(); i.hasNext();) {
				final String headerName = (String) i.next();
				final String headerValue = (String) headers.get(headerName);
				System.out.println("HEADER Name: " + headerName);
				System.out.println("HEADER Val: " + headerValue);
				method.setRequestHeader(headerName, headerValue);
			}
		}
	}

	private Map<String, String> createHeaderMap(final String header) {
		final String[] headers = StringUtils.split(header, ';');
		final Map<String, String> headerMap = new HashMap<>();
		for (int i = 0; i < headers.length; i++) {
			final String[] s = headers[i].split(":");
			final String key = s[0];
			final String value = s[1];
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
				headerMap.put(key, value);
			}
		}
		return headerMap;
	}

	private void setupProtocols(final SSLTransportConext context) {

		// will be null in case of linient SSL or Standard protocols.
		if (context != null) {
			final ProtocolSocketFactory socketFactory = new LocalClientAuthenticationSSLProtocolSocketFactory(context);
			final Protocol clientAuthHttps = new Protocol(TransportConstants.CLIENT_AUTH_HTTPS_PROTOCOL_IDENTIFIER, socketFactory, 443);
			Protocol.registerProtocol(TransportConstants.CLIENT_AUTH_HTTPS_PROTOCOL_IDENTIFIER, clientAuthHttps);

			final Protocol serverTrustHttps = new Protocol(TransportConstants.SERVER_TRUST_PROTOCOL_IDENTIFIER.toString(), new ServerTrustValidationSSLSocketFactory(context), HttpsURL.DEFAULT_PORT);
			Protocol.registerProtocol(TransportConstants.SERVER_TRUST_PROTOCOL_IDENTIFIER.toString(), serverTrustHttps);
		}
		final Protocol linientHttps = new Protocol(TransportConstants.LINIENT_HTTPS_PROTOCOL_IDENTIFIER, new LenientSSLProtocolSocketFactory(), 443);
		Protocol.registerProtocol(TransportConstants.LINIENT_HTTPS_PROTOCOL_IDENTIFIER, linientHttps);
	}

	private HttpClient createHttpClient(final SSLTransportConext context, final int timeout) {

		this.setupProtocols(context);

		final HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(1);
		connectionManager.getParams().setMaxTotalConnections(1);
		connectionManager.getParams().setSoTimeout(new Integer(timeout));
		connectionManager.getParams().setConnectionTimeout(new Integer(timeout));

		connectionManager.closeIdleConnections(timeout);
		final HttpClient client = new HttpClient(connectionManager);
		return client;
	}

	private String processEndpointScheme(final String endPoint, final TransportRequest request) {

		if (!endPoint.startsWith("https")) {
			return endPoint;
		}

		if (request.isLinientSSL()) {
			return endPoint.replaceFirst("https", TransportConstants.LINIENT_HTTPS_PROTOCOL_IDENTIFIER);
		} else if (request.isSSLEnabled()) {
			return endPoint.replaceFirst("https", TransportConstants.CLIENT_AUTH_HTTPS_PROTOCOL_IDENTIFIER);
		} else if (request.isServercertValidationEnabled()) {
			return endPoint.replaceFirst("https", TransportConstants.SERVER_TRUST_PROTOCOL_IDENTIFIER);
		}
		return endPoint;
	}

	private void handleBasicAuth(final HttpClient httpClient, final HttpMethod method, final String userName, final String password) {
		method.setDoAuthentication(Boolean.TRUE);
		httpClient.getState().setCredentials(null, null, new UsernamePasswordCredentials(userName, password));
		httpClient.getParams().setAuthenticationPreemptive(Boolean.TRUE);
	}

	private HttpMethod createHttpMethod(final String endPoint, final String data,
			final String methodType) {
		if ("GET".equalsIgnoreCase(methodType)) {
			return this.createGetMethodWithData(endPoint, data);
		} else {
			return this.createPostMethodWithData(endPoint, data);
		}
	}

	private HttpMethod createPostMethodWithData(final String endPoint,
			final String data) {
		final PostMethod method = new PostMethod(endPoint);
		final RequestEntity entity = new StringRequestEntity(data);
		method.setRequestEntity(entity);
		return method;
	}

	private HttpMethod createGetMethodWithData(final String endPoint,
			final String data) {
		final GetMethod method = new GetMethod(endPoint);
		method.setQueryString(data);
		return method;
	}

	private SSLTransportConext importCertificatesAndCreateSSLContext(final TransportRequest request)
			throws Exception {

		File importedPublicCert = null;
		File importedPrivateKey = null;

		final SSLTransportConext sslContext = new SSLTransportConext();

		try {
			final String workingDirectoryCanonicalPath = new File("").getCanonicalPath();
			final CertificateImporter cImporter = new CertificateImporter(workingDirectoryCanonicalPath);

			final KeyStore trustStore = cImporter.createKeyStore(TransportConstants.JKS_KEYSTORE_TYPE);
			importedPublicCert = cImporter.importTrustCerticateIntoKeyStore(trustStore, TransportConstants.PUBLIC_CERT_DEFAULT_PASSWORD, request.getPublicCertPath());

			System.out.println(" Using Server Cert:"
					+ request.getPublicCertPath());

			importedPrivateKey = cImporter.importPrivateKey(request.getPrivateCertPath(), request.getPrivateCertPassword(), TransportConstants.PKCS12_KEY_STORE_TYPE);

			System.out.println(" Using Client Cert:"
					+ request.getPublicCertPath());

			sslContext.setTrustStoreFile(importedPublicCert);
			sslContext.setTrustStorePassword(TransportConstants.PUBLIC_CERT_DEFAULT_PASSWORD);
			sslContext.setTrustStoreType(TransportConstants.JKS_KEYSTORE_TYPE);
			sslContext.setKeyStoreFile(importedPrivateKey);
			sslContext.setKeyStorePassword(request.getPrivateCertPassword());
			sslContext.setKeyStoreType(TransportConstants.PKCS12_KEY_STORE_TYPE);

		} catch (final Exception exc) {
			this.deleteFileIfExists(importedPublicCert);
			this.deleteFileIfExists(importedPrivateKey);
			System.out.println("There was an error while importing the certs. Please verify files and the password for private key.");
			throw exc;
		}
		return sslContext;
	}

	private SSLTransportConext importServerCertificateAndCreateSSLContext(final TransportRequest request)
			throws Exception {

		File importedPublicCert = null;

		final SSLTransportConext sslContext = new SSLTransportConext();

		try {
			final String workingDirectoryCanonicalPath = new File("").getCanonicalPath();
			final CertificateImporter cImporter = new CertificateImporter(workingDirectoryCanonicalPath);

			final KeyStore trustStore = cImporter.createKeyStore(TransportConstants.JKS_KEYSTORE_TYPE);
			importedPublicCert = cImporter.importTrustCerticateIntoKeyStore(trustStore, TransportConstants.PUBLIC_CERT_DEFAULT_PASSWORD, request.getPublicCertPath());

			System.out.println(" Using Server Cert:"
					+ request.getPublicCertPath());

			sslContext.setTrustStoreFile(importedPublicCert);
			sslContext.setTrustStorePassword(TransportConstants.PUBLIC_CERT_DEFAULT_PASSWORD);
			sslContext.setTrustStoreType(TransportConstants.JKS_KEYSTORE_TYPE);

		} catch (final Exception exc) {
			this.deleteFileIfExists(importedPublicCert);
			System.out.println("There was an error while importing the certs. Please verify files and the password for private key.");
			throw exc;
		}
		return sslContext;
	}

	private void deleteFileIfExists(final File fileToDelete) {
		if ((fileToDelete != null) && fileToDelete.exists()) {
			fileToDelete.delete();
		}
	}
}
