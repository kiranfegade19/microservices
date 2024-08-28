package com.org.eureka_servicediscovery.eureka_servicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServicediscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServicediscoveryApplication.class, args);
	}

}
