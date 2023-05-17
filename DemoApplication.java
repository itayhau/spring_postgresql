package spring1.web1.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StreamUtils;
import spring1.web1.demo.model.Customer;
import spring1.web1.demo.repository.CustomerRepository;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		//var jdbcTemplate = context.getBean("JdbcTemplate", JdbcTemplate.class)
		//String scriptContent = StreamUtils.copyToString(scriptResource.getInputStream(), StandardCharsets.UTF_8);
		//jdbcTemplate.execute(scriptContent);
	}

	@Bean
	CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate, CustomerRepository customerRepository) {
		return args -> {
			jdbcTemplate.execute(
					"DROP TABLE IF EXISTS customer_order cascade;\n" +
							"DROP TABLE IF EXISTS customer cascade;\n" +
					"CREATE TABLE customer ("+
					"    id SERIAL PRIMARY KEY,\n" +
					"    first_name varchar(255) NOT NULL default '',\n" +
					"    last_name varchar(255) NOT NULL default '',\n" +
					"    email varchar(255) NOT NULL default '');\n" +
					"CREATE TABLE customer_order (" +
					"    id SERIAL PRIMARY KEY,\n" +
					"    customer_id int NOT NULL,\n" +
					"    item_name varchar(255) NOT NULL default '',\n" +
					"    price DECIMAL(100,2) NOT NULL default 0,\n" +
					"    FOREIGN KEY (customer_id) REFERENCES customer(id));");

			customerRepository.createCustomer(new Customer(0, "tomer", "avivi", "tomeravivi@gmail.com"));

		};
	}

}
