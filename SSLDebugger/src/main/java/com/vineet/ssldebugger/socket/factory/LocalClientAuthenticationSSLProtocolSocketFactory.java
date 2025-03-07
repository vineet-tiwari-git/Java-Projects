package com.vineet.ssldebugger.socket.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import com.vineet.ssldebugger.bean.SSLTransportConext;
import com.vineet.ssldebugger.ui.util.LocalKeyStoreUtil;


public class LocalClientAuthenticationSSLProtocolSocketFactory implements ProtocolSocketFactory {

    public static final String SUN_X509_IDENTIFIER = "SunX509";
    public static final String TLS_PROTOCOL_IDENTIFIER = "TLS";

    private final File trustStoreFile;
    private final String trustStorePassword;
    private final String trustStoreType;

    private final File keyStoreFile;
    private final String keyStorePassword;
    private final String keyStoreType;

    public LocalClientAuthenticationSSLProtocolSocketFactory(final SSLTransportConext context) {

        this.trustStoreFile = context.getTrustStoreFile();
        this.trustStorePassword = context.getTrustStorePassword();
        this.trustStoreType = context.getTrustStoreType();

        this.keyStoreFile = context.getKeyStoreFile();
        this.keyStorePassword = context.getKeyStorePassword();
        this.keyStoreType = context.getKeyStoreType();

    }

    @Override
    public Socket createSocket(final String host, final int port) throws IOException, UnknownHostException {
        return this.getSSLContext().getSocketFactory().createSocket(host, port);
    }

    @Override
    public Socket createSocket(final String host, final int port, final InetAddress clientHost, final int clientPort) throws IOException, UnknownHostException {
        return this.getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    @Override
    public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort, final HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        final int timeout = params.getConnectionTimeout();
        final Socket s = this.createSocket(host, port, localAddress, localPort);
        if (timeout != 0) {
            s.setSoTimeout(timeout);
        }

        return s;
    }

    private SSLContext createSSLContext() {

        FileInputStream keyStoreFileInputStream = null;
        FileInputStream trustStoreFileInputStream = null;
        try {

            keyStoreFileInputStream = new FileInputStream(this.keyStoreFile);
            final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(LocalClientAuthenticationSSLProtocolSocketFactory.SUN_X509_IDENTIFIER);
            final KeyStore clientStore = LocalKeyStoreUtil.loadKeyStore(keyStoreFileInputStream, this.keyStorePassword, this.keyStoreType);
            keyManagerFactory.init(clientStore, this.keyStorePassword.toCharArray());

            // set up a trust manager so we can recognize the server
            trustStoreFileInputStream = new FileInputStream(this.trustStoreFile);
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(LocalClientAuthenticationSSLProtocolSocketFactory.SUN_X509_IDENTIFIER);
            final KeyStore trustStore = LocalKeyStoreUtil.loadKeyStore(trustStoreFileInputStream, this.trustStorePassword, this.trustStoreType);
            trustManagerFactory.init(trustStore);

            // create a context and set up a socket factory
            final SSLContext context = SSLContext.getInstance(LocalClientAuthenticationSSLProtocolSocketFactory.TLS_PROTOCOL_IDENTIFIER);
            context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            return context;
        } catch (final Exception e) {
            throw new HttpClientError(e.toString());
        } finally {
            if (keyStoreFileInputStream != null) {
                try {
                    keyStoreFileInputStream.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
            if (trustStoreFileInputStream != null) {
                try {
                    trustStoreFileInputStream.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private SSLContext getSSLContext() {
        return this.createSSLContext();
    }
}
