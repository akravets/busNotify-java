package com.akravets.busNotify;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import com.akravets.busNotify.configuration.ConfigProperties;
import com.akravets.busNotify.controller.CameraController;
import com.akravets.busNotify.service.CameraService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
@Slf4j
public class BusNotifySpringClientApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BusNotifySpringClientApplication.class, args);
		CameraController bean = context.getBean(CameraController.class);
		try {
			bean.analyze();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
