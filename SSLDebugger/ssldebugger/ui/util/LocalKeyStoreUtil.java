package com.vineet.ssldebugger.ui.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class LocalKeyStoreUtil {
	private static final Logger LOGGER = Logger
			.getLogger(LocalKeyStoreUtil.class.getName());

	public static void addCertificate(Certificate certificate, String alias,
			KeyStore keyStore) throws KeyStoreException {
		if (keyStore == null) {
			throw new KeyStoreException("'keyStore' parameter was not supplied");
		}

		if (StringUtils.isEmpty(alias)) {
			throw new KeyStoreException("'alias' parameter was not supplied");
		}

		if (certificate == null) {
			throw new KeyStoreException(
					"'certificate' parameter was not supplied");
		}

		keyStore.setCertificateEntry(alias, certificate);
	}

	public static KeyStore createKeyStore(String type)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance(type);
		ks.load(null, null);
		return ks;
	}

	public static Certificate importX509Certificate(InputStream is)
			throws CertificateException {
		CertificateFactory certificateFactory = CertificateFactory.getInstance(
				"X.509", new BouncyCastleProvider());
		return certificateFactory.generateCertificate(is);
	}

	public static Certificate getCertificate(String alias, KeyStore keyStore)
			throws KeyStoreException {
		if (keyStore == null) {
			throw new KeyStoreException("'keyStore' parameter was not supplied");
		}

		if (StringUtils.isEmpty(alias)) {
			throw new KeyStoreException("'alias' parameter was not supplied");
		}

		return keyStore.getCertificate(alias);
	}

	public static Key getKey(String alias, String password, KeyStore keyStore)
			throws KeyStoreException, UnrecoverableKeyException,
			NoSuchAlgorithmException {
		if (keyStore == null) {
			throw new KeyStoreException("'keyStore' parameter was not supplied");
		}

		if (StringUtils.isEmpty(alias)) {
			throw new KeyStoreException("'alias' parameter was not supplied");
		}

		if (StringUtils.isEmpty(password)) {
			throw new KeyStoreException("'password' parameter was not supplied");
		}

		return keyStore.getKey(alias, password.toCharArray());
	}

	public static KeyStore loadKeyStore(InputStream is, String password)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {
		if (is == null) {
			throw new KeyStoreException("'is' parameter was not supplied");
		}

		if (StringUtils.isEmpty(password)) {
			throw new KeyStoreException("'password' parameter was not supplied");
		}

		Security.addProvider(new BouncyCastleProvider());
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(is, password.toCharArray());
		return keyStore;
	}

	public static KeyStore loadKeyStore(InputStream is, String password,
			String keyStoreType) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		if (is == null) {
			throw new KeyStoreException("'is' parameter was not supplied");
		}

		if (StringUtils.isEmpty(password)) {
			throw new KeyStoreException("'password' parameter was not supplied");
		}

		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		keyStore.load(is, password.toCharArray());
		return keyStore;
	}

	public static void saveKeyStore(KeyStore keyStore, OutputStream os,
			String password) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		if (keyStore == null) {
			throw new KeyStoreException("'keyStore' parameter was not supplied");
		}

		if (os == null) {
			throw new KeyStoreException("'os' parameter was not supplied");
		}

		if (StringUtils.isEmpty(password)) {
			throw new KeyStoreException("'password' parameter was not supplied");
		}

		keyStore.store(os, password.toCharArray());
	}

	public static void storeKey(Key key, String alias, String password,
			Certificate[] certificateChain, KeyStore keyStore)
			throws KeyStoreException {
		if (key == null) {
			throw new KeyStoreException("'key' parameter was not supplied");
		}

		if (StringUtils.isEmpty(alias)) {
			throw new KeyStoreException("'alias' parameter was not supplied");
		}

	}
}