package com.algaworks.billing.infrastructure.creditcard.service.fastpay.config;

import com.algaworks.billing.infrastructure.creditcard.service.fastpay.client.FastpayCreditCardTokenizationAPIClient;
import com.algaworks.billing.infrastructure.payment.properties.AlgaShopPaymentProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class FastpayCreditCardTokenizationAPIClientConfig {

    @Bean
    public FastpayCreditCardTokenizationAPIClient fastpayCreditCardTokenizationAPIClient(
            RestClient.Builder builder,
            AlgaShopPaymentProperties properties,
            @Value("${algashop.integrations.payment.fastpay.public-token}") String publicToken) {
        var fastpay = properties.getFastpay();

        RestClient build = builder.baseUrl(fastpay.getHostname()).requestInterceptor(
                ((request, body, execution) -> {
                    request.getHeaders().add("Token", publicToken);
                    return execution.execute(request, body);
                })
        ).build();

        RestClientAdapter adapter = RestClientAdapter.create(build);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFactory.createClient(FastpayCreditCardTokenizationAPIClient.class);
    }
}
