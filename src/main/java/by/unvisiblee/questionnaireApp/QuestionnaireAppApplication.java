package by.unvisiblee.questionnaireApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class QuestionnaireAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionnaireAppApplication.class, args);
	}

}
