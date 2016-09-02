package com.cnwidsom.common.trace;

import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanInjector;

public class CustomHttpServletResponseSpanInjector implements SpanInjector<HttpServletResponse> {

	@Override
	public void inject(Span span, HttpServletResponse carrier) {
		if (span != null) {
			carrier.addHeader("correlationId", Span.idToHex(span.getTraceId()));
			carrier.addHeader("mySpanId", Span.idToHex(span.getSpanId()));
		}
	}
}
