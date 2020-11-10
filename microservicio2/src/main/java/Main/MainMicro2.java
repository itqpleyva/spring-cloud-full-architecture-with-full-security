package Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MainMicro2 {

	public static void main(String[] args) {
		
		SpringApplication.run(MainMicro2.class, args);
		
	}

}
