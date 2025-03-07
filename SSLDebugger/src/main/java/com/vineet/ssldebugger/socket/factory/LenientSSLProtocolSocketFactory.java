/**
 *
 */
package com.vineet.ssldebugger.socket.factory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import com.vineet.ssldebugger.socket.security.LenientX509TrustManager;

/**
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 *
 */
public class LenientSSLProtocolSocketFactory implements ProtocolSocketFactory {

	private SSLContext			sslContext	= null;

	private X509TrustManager	trustManager;

	public Socket createSocket(final Socket socket, final String host, final int port, final boolean autoClose)
			throws IOException {
		return this.getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
	}

	@Override
	public Socket createSocket(final String host, final int port)
			throws IOException {
		return this.getSSLContext().getSocketFactory().createSocket(host, port);
	}

	@Override
	public Socket createSocket(final String host, final int port, final InetAddress clientHost, final int clientPort)
			throws IOException {
		return this.getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
	}

	@Override
	public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort, final HttpConnectionParams params)
			throws IOException {
		if (params == null) {
			throw new IllegalArgumentException("Parameters may not be null");
		}

		final int timeout = params.getConnectionTimeout();
		final SocketFactory socketFactory = this.getSSLContext().getSocketFactory();

		if (timeout == 0) {
			return socketFactory.createSocket(host, port, localAddress, localPort);
		}
		final Socket socket = socketFactory.createSocket();
		final SocketAddress localAddr = new InetSocketAddress(localAddress, localPort);
		final SocketAddress remoteAddr = new InetSocketAddress(host, port);
		socket.bind(localAddr);
		socket.connect(remoteAddr, timeout);
		return socket;
	}

	private SSLContext createSSLContext() {
		try {
			final SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[]{this.getTrustManager()}, null);
			return context;
		}

		catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private SSLContext getSSLContext() {
		if (this.sslContext == null) {
			this.sslContext = this.createSSLContext();
		}

		return this.sslContext;
	}

	private X509TrustManager getTrustManager() {
		if (this.trustManager == null) {
			this.trustManager = new LenientX509TrustManager();
		}
		return this.trustManager;
	}
}
