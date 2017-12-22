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
	public CommandLineRunner readExcelData(CentralWareHouseRepository repository, ApachePOIExcelRead reader) {
		return (args) -> {
			repository.deleteAll();
			reader.readExcel2();

		};
	}

}
