package dev.sangco.jwmessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class JwMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwMessageApplication.class, args);
	}
}
