package org.deshand.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner loadData(CustomerRepository repository) {
		return (args) -> {
			repository.deleteAll();
//			repository.save(new Customer("Jack", "Bauer"));
//			repository.save(new Customer("Chloe", "O'Brian"));
//			repository.save(new Customer("Kim", "Bauer"));
//			repository.save(new Customer("David", "Palmer"));
//			repository.save(new Customer("Michelle", "Dessler")); CentralWareHouseRepository

		};
	}
	
	@Bean
	public CommandLineRunner loadData1(CentralWareHouseRepository repository) {
		return (args) -> {
			repository.deleteAll();
//			repository.save(new CentralWareHouse("К-04-03","no","Мотор электрический KME Engineering GmbH Unkermotoren BG65X25SI+2142DF27-1 24V, 3080 об/мин",
//			        "1058030-000-00","3452861","0","0","0","22-05-17 KDF4-1 Новик 1.07.17 KDF4 - 1"));
			

		};
	}
	
	@Bean
	public CommandLineRunner readExcelData(ApachePOIExcelRead reader) {
		return (args) -> {
		reader.readExcel2();
			

		};
	}

}
