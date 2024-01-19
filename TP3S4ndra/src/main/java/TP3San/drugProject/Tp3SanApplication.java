package TP3San.drugProject;

import java.util.List;

import TP3San.drugProject.menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Tp3SanApplication {

	public static void main(String[] args) throws IllegalAccessException {
		SpringApplication.run(Tp3SanApplication.class, args);
		Tp3SanApplication.displayMenu();
	}

	public static void displayMenu() throws IllegalAccessException {
		Menu menu = new Menu();
		menu.run();
	}
}

/*<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
			<version>3.2.0</version>
		</dependency>*/