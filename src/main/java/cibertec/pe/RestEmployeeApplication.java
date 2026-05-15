package cibertec.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestEmployeeApplication.class, args);
		System.out.println("Rest Inicializada");
	}

}
