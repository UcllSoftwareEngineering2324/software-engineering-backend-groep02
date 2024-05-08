package be.ucll.se.groep02backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class Groep02BackendApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(Groep02BackendApplication.class, args);
	}

}
