/**
 *
 */
package com.vineet.ssldebugger.socket.factory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;


/**
 * An extension of {@code SSLProtocolSocketFactory} that allows injection of a
 * JDK supported algorithm to {@code SSLContext}.
 * @see SSLProtocolSocketFactory
 *
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 *
 */
public class ExtendedSSLProtocolSocketFactory extends SSLProtocolSocketFactory {

    /**
     * @see SSLContext
     * @see org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory#createSocket(java.lang.String, int, java.net.InetAddress, int)
     */
    @Override
    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException, UnknownHostException {
    	Thread.dumpStack();
        return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    /**
     * @see SSLContext
     * @see org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory#createSocket(java.lang.String, int, java.net.InetAddress, int, org.apache.commons.httpclient.params.HttpConnectionParams)
     */
    @Override
    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
    	Thread.dumpStack();
        final int timeout = params.getConnectionTimeout();
        final Socket s = this.createSocket(host, port, localAddress, localPort);
        if (timeout != 0) {
            s.setSoTimeout(timeout);
        }
        return s;
    }

    /**
     * @see SSLContext
     * @see org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory#createSocket(java.lang.String, int)
     */
    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
    	Thread.dumpStack();
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    /**
     * @see SSLContext
     * @see org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory#createSocket(java.net.Socket, java.lang.String, int, boolean)
     */
    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
    	Thread.dumpStack();
        final Socket s = this.createSocket(host, port);
        return super.createSocket(s, host, port, autoClose);
    }

    /**
     * Algorithm to use.
     */
    private String sslProtocolName;

    /**
     * @param protocolName Name of SSL protocol to use
     *
     */
    public ExtendedSSLProtocolSocketFactory(final String protocolName) {
        this.sslProtocolName = protocolName;
    }

    /**
     * Returns the <code>SSLContext</code> to be used for the HTTPS communication
     *
     * @return SSLContent The <code>SSLContext</code> to be used for the HTTPS communication
     */
    private SSLContext getSSLContext() {
        return createSSLContext();
    }

    /**
     * Creates a {@link SSLContext} for HTTPS communication. This uses the SSL protocol version configured for transport.
     * @return The {@link SSLContext} to be used for HTTPS communication
     */
    private SSLContext createSSLContext() {
    	Thread.dumpStack();
        try {
            final SSLContext context = SSLContext.getInstance(this.sslProtocolName);
            context.init(null, null, null);
            return context;
        } catch (Exception httpex) {
            throw new HttpClientError(httpex.toString());
        }
    }
}
