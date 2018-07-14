package chun.li.GStack.Console;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        run(Application.class, args);
    }
}
