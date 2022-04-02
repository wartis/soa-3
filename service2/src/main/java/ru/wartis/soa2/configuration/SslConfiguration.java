package ru.wartis.soa2.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ru.wartis.soa2.exceptions.RestTemplateResponseErrorHandler;

import javax.net.ssl.SSLContext;

@Configuration
public class SslConfiguration {
    @Value("${http.client.ssl.trust-store}")
    private Resource keyStore;
    @Value("${http.client.ssl.trust-store-password}")
    private String keyStorePassword;

    @Bean
    RestTemplate restTemplate() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
            .loadTrustMaterial(
                keyStore.getURL(),
                keyStorePassword.toCharArray()
            ).build();
        SSLConnectionSocketFactory socketFactory =
            new SSLConnectionSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        HttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory(socketFactory).build();
        HttpComponentsClientHttpRequestFactory factory =
            new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplateBuilder()
            .requestFactory(() -> factory)
            .errorHandler(new RestTemplateResponseErrorHandler())
            .build(); //new RestTemplate(factory);
    }
}
