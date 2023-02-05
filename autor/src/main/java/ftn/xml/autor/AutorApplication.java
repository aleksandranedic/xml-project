package ftn.xml.autor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutorApplication.class, args);
	}

}
