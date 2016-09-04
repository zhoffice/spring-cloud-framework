package com.cnwidsom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.SpanExtractor;
import org.springframework.cloud.sleuth.SpanInjector;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.SpanReporter;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.log.SpanLogger;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.sleuth.trace.DefaultTracer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cnwidsom.common.trace.CustomHttpServletRequestSpanExtractor;
import com.cnwidsom.common.trace.CustomHttpServletResponseSpanInjector;
import com.cnwidsom.common.trace.DistributeTracer;

import feign.RequestInterceptor;
import io.jmnarloch.spring.request.correlation.api.EnableRequestCorrelation;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients
public class Application {
	private static final Logger log = Logger.getLogger(Application.class.getName());

	@Autowired
	@Lazy(value = true)
	private DemoServiceProxy demoServiceProxy;

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
		// log.log(Level.INFO, "enter demoService-home");
		List<String> l = new ArrayList<String>();
		l.add("demo-service");
		return l;
	}

	@RequestMapping("/demo2")
	public List<String> demoService2() {
		// log.log(Level.INFO, "call demoService2");
		return demoServiceProxy.demo2("1");
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

	}
}
