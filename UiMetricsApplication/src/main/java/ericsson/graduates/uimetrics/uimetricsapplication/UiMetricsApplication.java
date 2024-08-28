package ericsson.graduates.uimetrics.uimetricsapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class UiMetricsApplication  {

	public static void main(String[] args) {
		SpringApplication.run(UiMetricsApplication.class, args);
	}

	@Bean
	@Scope("application")
	public long[] results()
	{//autowire initial metrics as per requirement here(Unit passes,fails, Integration passes,fails)
		return new long[]{0,0,0,0};
	}

}
