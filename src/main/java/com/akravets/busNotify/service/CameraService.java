package com.akravets.busNotify.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
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
	
	public Optional<InputStream> analyze() throws ClientProtocolException, IOException, URISyntaxException {
		Optional<InputStream> is = Optional.empty();
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build()) {
			URIBuilder builder = new URIBuilder();
			builder.setPath(properties.getEndpointUrl());
			builder.setParameter("cameraFetchFrequency", "10");
			HttpGet request = new HttpGet(builder.build());

			/*
			 * Map<String, String> map = new LinkedHashMap<String, String>();
			 * map.put("cameraFetchFrequency", "10"); String payload = gson.toJson(map);
			 * 
			 * StringEntity entity = new StringEntity(payload,
			 * ContentType.APPLICATION_JSON);
			 * 
			 * request.setHeader("Content-type", "application/json");
			 */

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
