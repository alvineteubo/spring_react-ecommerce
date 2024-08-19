package com.proxidevcode.spring_react_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


import org.springframework.boot.CommandLineRunner;
import com.proxidevcode.spring_react_ecommerce.models.Category;
import com.proxidevcode.spring_react_ecommerce.repositories.CategoryRepository;

@SpringBootApplication
public class SpringReactEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactEcommerceApplication.class, args);
	}
@Bean

	CommandLineRunner runner(CategoryRepository categoryRepository){
		return args -> {
			Category cat1 = new Category();
			cat1.setName("Chocolat");
			
			Category category = new Category(null, "Mambo");
          
			Category cat3 = Category.builder()
			.name("Repe")
			.build();
            categoryRepository.saveAll(List.of(cat1, cat3, category));
		    
		};

	}

}

