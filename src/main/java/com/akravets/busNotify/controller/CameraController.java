package com.akravets.busNotify.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.akravets.busNotify.service.CameraService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CameraController {
	@Autowired
	CameraService service;

	@RequestMapping(value = "/analyze", method = RequestMethod.GET)
	public ModelAndView analyze() throws ClientProtocolException, IOException, URISyntaxException {
			ModelAndView mv = new ModelAndView("showResults");
			Optional<InputStream> analyze = service.analyze();
			if(analyze.isPresent()) {
				mv.addObject(analyze.get());
				return mv;
			}
			return new ModelAndView("noData");
	}

}
