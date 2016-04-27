package uk.ac.london.harness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import uk.ac.london.harness.config.SearchContext;
import uk.ac.london.harness.config.WebContext;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Import({ WebContext.class, SearchContext.class })
public class TestHarnessApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestHarnessApplication.class, args);
	}
}
