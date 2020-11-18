package Main.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/micro1")
public class microservice1Controller {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/message")
	public String test() {
		
		return "Hello from microservice 1";
	}
	
	@GetMapping("/call")
	public String test1() {
		
		final String fooResourceUrl = "http://MICRO2/micro2/message";

		final ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl,
		String.class);

		String flag = response.getBody();// guardando el boolean del valid token 
		
		return flag;
	
	}
}
