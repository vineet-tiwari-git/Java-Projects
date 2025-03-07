/**
 *
 */
package com.vineet.ssldebugger.socket.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import com.vineet.ssldebugger.bean.SSLTransportConext;
import com.vineet.ssldebugger.ui.util.LocalKeyStoreUtil;

/**
 * A custom implementation of {@link ProtocolSocketFactory} that validates <b>trusted certificate</b>,<br/>
 * which is presented by the server against a pre-configured <b>trust-store</b> for a given
 * {@code HttpTransport}.
 *
 * @see ClientAuthenticationSSLProtocolSocketFactory
 * @see LenientSSLProtocolSocketFactory
 *
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 *
 * @since 4.1.3
 */
public class ServerTrustValidationSSLSocketFactory implements ProtocolSocketFactory {

    public static final String SUN_X509_IDENTIFIER = "SunX509";
    public static final String TLS_PROTOCOL_IDENTIFIER = "TLS";

    private final File trustStoreFile;
    private final String trustStorePassword;
    private final String trustStoreType;

    public ServerTrustValidationSSLSocketFactory(final SSLTransportConext context) {

        this.trustStoreFile = context.getTrustStoreFile();
        this.trustStorePassword = context.getTrustStorePassword();
        this.trustStoreType = context.getTrustStoreType();
    }

    /**
     * @see org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String, int)
     */
    @Override
    public Socket createSocket(final String host, final int port) throws IOException, UnknownHostException {
        return this.getSSLContext().getSocketFactory().createSocket(host, port);
    }

    /**
     * @see org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String, int,
     *      java.net.InetAddress, int)
     */
    @Override
    public Socket createSocket(final String host, final int port, final InetAddress clientHost, final int clientPort) throws IOException, UnknownHostException {
        return this.getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    /**
     * @see org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String, int,
     *      java.net.InetAddress, int, org.apache.commons.httpclient.params.HttpConnectionParams)
     */
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

    /**
     * Creates a {@link SSLContext} for HTTPS communication. This uses the trust-store configured for
     * transport.
     *
     * @see #getHttpTransport()
     * @return The {@link SSLContext} to be used for HTTPS communication
     */
    private SSLContext createSSLContext() {
        try {
            // set up a trust manager so we can recognize the server
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(ServerTrustValidationSSLSocketFactory.SUN_X509_IDENTIFIER);
            final KeyStore trustStore = LocalKeyStoreUtil.loadKeyStore(new FileInputStream(this.trustStoreFile), this.trustStorePassword, this.trustStoreType);
            trustManagerFactory.init(trustStore);
            // create a context and set up a socket factory
            final SSLContext context = SSLContext.getInstance(ServerTrustValidationSSLSocketFactory.TLS_PROTOCOL_IDENTIFIER);
            context.init(null, trustManagerFactory.getTrustManagers(), null);
            return context;
        } catch (final Exception e) {
            throw new HttpClientError(e.toString());
        }
    }

    /**
     * Returns the <code>SSLContext</code> to be used for the HTTPS communication
     *
     * @return SSLContent The <code>SSLContext</code> to be used for the HTTPS communication
     */
    private SSLContext getSSLContext() {
        return this.createSSLContext();
    }
}
