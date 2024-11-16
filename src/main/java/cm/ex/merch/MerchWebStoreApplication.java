package cm.ex.merch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MerchWebStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchWebStoreApplication.class, args);
		System.out.println("Merch Web Store Application");
	}
/*
drop database merch; create database merch; use merch;
*/
}
