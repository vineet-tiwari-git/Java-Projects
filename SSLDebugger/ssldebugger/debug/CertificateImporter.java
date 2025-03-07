package com.vineet.ssldebugger.debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import com.vineet.ssldebugger.constant.TransportConstants;
import com.vineet.ssldebugger.ui.util.LocalKeyStoreUtil;

/**
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 * 
 */
public class CertificateImporter {

	private final String workingDirectoryPath;

	public CertificateImporter(final String workingDirectoryPath) {
		this.workingDirectoryPath = workingDirectoryPath;
	}

	public KeyStore createKeyStore(final String keystoreType)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		return LocalKeyStoreUtil.createKeyStore(keystoreType);
	}

	public File importTrustCerticateIntoKeyStore(final KeyStore trustStore, final String password,
			final String publicCertfilePath) throws Exception {
		final File trustCert = new File(publicCertfilePath);
		final FileInputStream inputStream = new FileInputStream(trustCert);
		final Certificate c = LocalKeyStoreUtil.importX509Certificate(inputStream);
		LocalKeyStoreUtil.addCertificate(c, TransportConstants.CERTIFICATE_ALIAS, trustStore);
		inputStream.close();
		return this.saveTrustStore(trustStore, TransportConstants.TRUST_CERT_FILE_NAME, password);
	}

	private File saveTrustStore(final KeyStore trustStore, final String fileName, final String password)
			throws Exception {
		final File trustStoreFile = new File(this.workingDirectoryPath, fileName);
		final FileOutputStream outputStream = new FileOutputStream(trustStoreFile);
		LocalKeyStoreUtil.saveKeyStore(trustStore, outputStream, password);
		outputStream.close();
		return trustStoreFile;
	}

	public File importPrivateKey(final String filePath, final String keyStorePassword, final String keystoreType)
			throws Exception {
		final File privateKey = new File(filePath);
		final FileInputStream inputStream = new FileInputStream(privateKey);
		final KeyStore keyStore = LocalKeyStoreUtil.loadKeyStore(inputStream, keyStorePassword, keystoreType);
		inputStream.close();
		return this.saveTrustStore(keyStore, TransportConstants.PRIVATE_CERT_FILE_NAME, keyStorePassword);
	}
}
