package com.cnwidsom;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.SpanExtractor;
import org.springframework.cloud.sleuth.SpanInjector;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.SpanReporter;
import org.springframework.cloud.sleuth.log.SpanLogger;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cnwidsom.common.trace.CustomHttpServletRequestSpanExtractor;
import com.cnwidsom.common.trace.CustomHttpServletResponseSpanInjector;
import com.cnwidsom.common.trace.DistributeTracer;

@EnableCircuitBreaker
@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class Application {
	private static final Logger log = Logger.getLogger(Application.class.getName());

	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}

	@Bean
	public DistributeTracer sleuthTracer(Sampler sampler, Random random, SpanNamer spanNamer, SpanLogger spanLogger,
			SpanReporter spanReporter) {
		return new DistributeTracer(sampler, random, spanNamer, spanLogger, spanReporter);
	}

	@Bean
	@Primary
	SpanInjector<HttpServletResponse> getCustomHttpServletResponseSpanInjector() {
		return new CustomHttpServletResponseSpanInjector();
	}

	@Bean
	@Primary
	SpanExtractor<HttpServletRequest> getCustomHttpServletRequestSpanExtractor() {
		return new CustomHttpServletRequestSpanExtractor();
	}

	@RequestMapping("/")
	public List<String> home() {
		// log.log(Level.INFO, "enter demoService2-Home");
		List<String> l = new ArrayList<String>();
		l.add("demo-service2");
		return l;
	}

	@RequestMapping("/{id}")
	public List<String> demo2(@PathVariable("id") String id, HttpServletRequest request) {
		// log.log(Level.INFO, "enter demo2");
		StringBuffer sb = new StringBuffer();
		Enumeration<String> es = request.getHeaderNames();
		while (es.hasMoreElements()) {
			String header = es.nextElement();
			sb.append(header + "=" + request.getHeader(header) + "\n");
		}
		System.out.println(sb.toString());
		List<String> l = new ArrayList<String>();
		l.add("demo-service2_" + id);
		return l;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
