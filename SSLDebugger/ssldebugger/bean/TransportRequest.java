package com.vineet.ssldebugger.bean;

import java.util.HashMap;
import java.util.Map;

import com.vineet.ssldebugger.constant.TransportRequestFieldNames;

/**
 * Class to representation a request for a transport attempt.
 *
 *
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 *
 */
public class TransportRequest extends HashMap<String, Object> {

	/** serial version */
	private static final long	serialVersionUID	= 1L;

	public String getEndpoint() {
		return (String) this.get(TransportRequestFieldNames.END_POINT.getName());
	}

	public String getPublicCertPath() {
		return (String) this.get(TransportRequestFieldNames.PUBLIC_CERT_PATH.getName());
	}

	public String getPrivateCertPath() {
		return (String) this.get(TransportRequestFieldNames.PRIVATE_CERT_PATH.getName());
	}

	public String getPrivateCertPassword() {
		return (String) this.get(TransportRequestFieldNames.PRIVATE_CERT_PASSWORD.getName());
	}

	public String getEndpointUserName() {
		return (String) this.get(TransportRequestFieldNames.ENDPOINT_USERNAME.getName());
	}

	public String getEndpointPassword() {
		return (String) this.get(TransportRequestFieldNames.ENDPOINT_PASSWORD.getName());
	}

	public String getDataFilePath() {
		return (String) this.get(TransportRequestFieldNames.DATA_FILE_PATH.getName());
	}

	public String getHttpHeaders() {
		return (String) this.get(TransportRequestFieldNames.HTTP_HEADERS.getName());
	}

	public String getHttpMethodType() {
		return (String) this.get(TransportRequestFieldNames.HTTP_METHOD_TYPE.getName());
	}

	public Integer getTimeOut() {
		return (Integer) this.get(TransportRequestFieldNames.TIME_OUT.getName());
	}

	public TransportRequest(final Map map) {
		this.putAll(map);
	}

	public boolean isSSLEnabled() {
		return (Boolean) this.get(TransportRequestFieldNames.IS_SSL_ENABLED.getName());
	}

	public boolean isLinientSSL() {
		return (Boolean) this.get(TransportRequestFieldNames.IS_LINIENT_SSL.getName());
	}

	public boolean isServercertValidationEnabled() {
		return (Boolean) this.get(TransportRequestFieldNames.IS_SERVER_CERT_VALIDATION.getName());
	}
}
