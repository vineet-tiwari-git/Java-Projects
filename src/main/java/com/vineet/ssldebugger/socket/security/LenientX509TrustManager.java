/**
 *
 */
package com.vineet.ssldebugger.socket.security;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author <a href="mailto:tiwari_vineet@hotmail.com">Vineet Tiwari</a>
 *
 */
public class LenientX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {
        // No implementation needed

    }

    @Override
    public void checkServerTrusted(final X509Certificate[] arg0, final String arg1) throws CertificateException {
        // No implementation needed
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
