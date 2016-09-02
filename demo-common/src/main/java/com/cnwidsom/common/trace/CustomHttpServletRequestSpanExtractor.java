package com.cnwidsom.common.trace;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanExtractor;

public class CustomHttpServletRequestSpanExtractor implements SpanExtractor<HttpServletRequest> {

	public Long getZipkinId(String key, Long defaultValue) {
		Long r = defaultValue;
		if (key != null)
			r = Span.hexToId(key);
		return r;
	}

	private String getValue(String key, String defaultValue) {
		String r = defaultValue;
		if (key != null)
			r = key;
		return r;
	}

	@Override
	public Span joinTrace(HttpServletRequest carrier) {
		Long traceId = getZipkinId(carrier.getHeader(Span.TRACE_ID_NAME), null);
		Long spanId = getZipkinId(carrier.getHeader(Span.SPAN_ID_NAME), null);
		Long parentId = getZipkinId(carrier.getHeader(Span.PARENT_ID_NAME), null);
		String spanName = getValue(carrier.getHeader(Span.SPAN_NAME_NAME), null);
		if (traceId != null && spanId != null) {
			Span.SpanBuilder builder = Span.builder().traceId(traceId).spanId(spanId).parent(parentId).name(spanName);
			// build rest of the Span
			return builder.build();
		}
		return null;
	}
}
