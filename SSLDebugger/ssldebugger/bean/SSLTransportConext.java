package com.vineet.ssldebugger.bean;

import java.io.File;

/**
 * Class to hold needed context information for an SSL Connection.
 *
 * 
 */
public class SSLTransportConext {

	private File	trustStoreFile;

	public File getTrustStoreFile() {
		return this.trustStoreFile;
	}
	public void setTrustStoreFile(final File trustStoreFile) {
		this.trustStoreFile = trustStoreFile;
	}
	public File getKeyStoreFile() {
		return this.keyStoreFile;
	}
	public void setKeyStoreFile(final File keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}
	private File	keyStoreFile;

	private String	trustStorePassword;
	private String	trustStoreType;

	private String	keyStorePassword;
	private String	keyStoreType;

	public String getTrustStorePassword() {
		return this.trustStorePassword;
	}
	public void setTrustStorePassword(final String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}
	public String getTrustStoreType() {
		return this.trustStoreType;
	}
	public void setTrustStoreType(final String trustStoreType) {
		this.trustStoreType = trustStoreType;
	}

	public String getKeyStorePassword() {
		return this.keyStorePassword;
	}
	public void setKeyStorePassword(final String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}
	public String getKeyStoreType() {
		return this.keyStoreType;
	}
	public void setKeyStoreType(final String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	public boolean isSSL(){
		return( this.getKeyStoreFile() != null);
	}

}
