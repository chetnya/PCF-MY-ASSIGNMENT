package com.test.cf.haash.client;

import java.util.List;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.test.cf.haash.client.connector.cloudfoundry.HaashServiceInfo;

@SpringBootApplication
public class HaashBrokerClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaashBrokerClientApplication.class, args);
	}
	
	@Bean
    Cloud cloud() {
        return new CloudFactory().getCloud();
    }

    @Bean
    HaashServiceInfo haashServiceInfo() {
        List<ServiceInfo> serviceInfos = cloud().getServiceInfos();
        for (ServiceInfo serviceInfo : serviceInfos) {
            if (serviceInfo instanceof HaashServiceInfo) {
                return (HaashServiceInfo) serviceInfo;
            }
        }
        throw new RuntimeException("Unable to find bound HaaSh instance!");
    }

    @Bean
    RestTemplate restTemplate() {
    	BasicCredentialsProvider credentialsProvider =  new BasicCredentialsProvider();
    	credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(haashServiceInfo().getUsername(), haashServiceInfo().getPassword()));
    	HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        ClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(rf);
    }
    
    @RequestMapping("/HaaSh/info")
    public HaashServiceInfo info() {
        return haashServiceInfo();
    }

    @RequestMapping(value = "/HaaSh/{key}", method = RequestMethod.PUT)
    public ResponseEntity<String> put(@PathVariable("key") String key,
                                      @RequestBody String value) {
        restTemplate().put(haashServiceInfo().getUri()+"/{key}", value, key);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/HaaSh/{key}", method = RequestMethod.GET)
    public ResponseEntity<String> put(@PathVariable("key") String key) {
        String response = restTemplate().getForObject(haashServiceInfo().getUri() + "/{key}", String.class, key);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
