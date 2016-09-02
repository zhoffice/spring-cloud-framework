package com.cnwidsom;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.jmnarloch.spring.request.correlation.api.EnableRequestCorrelation;

@FeignClient(name = "demo-service2")
interface DemoService2 {

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	List<String> demo2(@PathVariable("id") String id);

}
