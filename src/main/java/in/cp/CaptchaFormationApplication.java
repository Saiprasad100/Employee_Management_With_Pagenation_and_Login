package in.cp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("in.cp")
public class CaptchaFormationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptchaFormationApplication.class, args);
	}

}
