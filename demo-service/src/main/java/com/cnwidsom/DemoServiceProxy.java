package com.cnwidsom;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.trace.DefaultTracer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class DemoServiceProxy {
	@Autowired
	private DemoService2 demoServcie2;

	@HystrixCommand(fallbackMethod = "defaultDemo2")
	public List<String> demo2(String id) {
		return demoServcie2.demo2(id);
	}

	public List<String> defaultDemo2(String id) {
		List<String> s = new ArrayList<String>();
		s.add("I am default demo2");
		return s;
	}
}
