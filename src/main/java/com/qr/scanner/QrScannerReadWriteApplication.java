package com.qr.scanner;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value="classpath:messages.properties")
public class QrScannerReadWriteApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrScannerReadWriteApplication.class, args);
//        BasicConfigurator.configure();
			String log4jConfPath = "./src/main/resources/log4j.properties";
			PropertyConfigurator.configure(log4jConfPath);
	}

}
