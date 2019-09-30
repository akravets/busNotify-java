package com.akravets.busNotify.configuration;

import org.apache.http.client.config.RequestConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.google.gson.Gson;

@Configuration
@ConfigurationProperties(prefix="bus")
@Primary
public class ConfigProperties {
	private String hostName;
	private String port;
	private String cameraEndpoint;
	private static final int TIMEOUT = 50;
	
	public String getHostName() {
		return hostName;
	}
	
	public String getPort() {
		return port;
	}
	
	public String getCameraEndpoint() {
		return cameraEndpoint;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setCameraEndpoint(String cameraEndpoint) {
		this.cameraEndpoint = cameraEndpoint;
	}
	
	public String getEndpointUrl() {
		return hostName + ":" + port + "/" + cameraEndpoint;
	}
	
	@Bean
	public RequestConfig getRequestConfig() {
		return RequestConfig
				.custom()
				.setConnectTimeout(TIMEOUT * 1000)
				.setConnectionRequestTimeout(TIMEOUT * 1000)
				.setSocketTimeout(TIMEOUT * 1000).build();
	}
	
	@Bean
	public Gson getGson() {
		return new Gson();
	}
}
