package com.akravets.busNotify.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akravets.busNotify.configuration.ConfigProperties;
import com.google.gson.Gson;

@Service
public class CameraService {
	@Autowired
	ConfigProperties properties;
	
	@Autowired
	Gson gson;
	
	@Autowired
	RequestConfig config;
	
	public Optional<InputStream> analyze() throws ClientProtocolException, IOException {
		Optional<InputStream> is = Optional.empty();
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build()) {
			HttpPost request = new HttpPost(properties.getEndpointUrl());

			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("cameraFetchFrequency", "10");
			String payload =  gson.toJson(map);

			StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);

			request.setEntity(entity);
			request.setHeader("Content-type", "application/json");

			try (CloseableHttpResponse response = client.execute(request)) {
				int code = response.getStatusLine().getStatusCode();
				
				if(code != HttpStatus.SC_OK) {
					throw new IOException("Couldn't retrieve camera image");
				}
			
				is  = Optional.of(response.getEntity().getContent());
			}
		}
		return is;
	}
}
