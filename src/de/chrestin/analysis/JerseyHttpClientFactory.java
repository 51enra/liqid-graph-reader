package de.chrestin.analysis;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class JerseyHttpClientFactory {

	private JerseyHttpClientFactory() {
	}
	
    static Client getJerseyHTTPSClient() throws KeyManagementException, NoSuchAlgorithmException {
    	
    	JerseyHttpClientFactory factory = new JerseyHttpClientFactory();
    	
        SSLContext sslContext = getSslContext(factory);
        HostnameVerifier allHostsValid = factory.new NoOpHostnameVerifier();

        return ClientBuilder.newBuilder()
                .sslContext(sslContext)
                .hostnameVerifier(allHostsValid)
                .build();
    }

    private static SSLContext getSslContext(JerseyHttpClientFactory factory) throws NoSuchAlgorithmException,
                                                     KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLSv1");

        KeyManager[] keyManagers = null;
        TrustManager[] trustManager = {factory.new NoOpTrustManager()};
        SecureRandom secureRandom = new SecureRandom();

        sslContext.init(keyManagers, trustManager, secureRandom);

        return sslContext;
    }
    
    private class NoOpTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
    
    private class NoOpHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }
}
