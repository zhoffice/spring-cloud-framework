package com.cnwidsom.common.trace;

import java.util.Random;
import java.util.concurrent.Callable;

import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.SpanReporter;
import org.springframework.cloud.sleuth.log.SpanLogger;
import org.springframework.cloud.sleuth.trace.DefaultTracer;

/**
 * 纯粹为了将Span.exportable设置为true，等弄明白了这个怎么配置后可能会删除此类
 * 
 * @author hitachi
 *
 */
public class DistributeTracer extends DefaultTracer {

	private final Sampler defaultSampler;

	private final Random random;

	private final SpanNamer spanNamer;

	private final SpanLogger spanLogger;

	private final SpanReporter spanReporter;

	public DistributeTracer(Sampler defaultSampler, Random random, SpanNamer spanNamer, SpanLogger spanLogger,
			SpanReporter spanReporter) {
		super(defaultSampler, random, spanNamer, spanLogger, spanReporter);
		this.defaultSampler = defaultSampler;
		this.random = random;
		this.spanNamer = spanNamer;
		this.spanLogger = spanLogger;
		this.spanReporter = spanReporter;
	}

	@Override
	public Span createSpan(String name, Sampler sampler) {
		Span span;
		if (isTracing()) {
			span = createChild(getCurrentSpan(), name);
		} else {
			long id = createId();
			span = Span.builder().name(name).traceId(id).spanId(id).build();
			if (sampler == null) {
				sampler = this.defaultSampler;
			}
			span = sampledSpan(name, id, span, sampler);
			this.spanLogger.logStartedSpan(null, span);
		}
		return continueSpan(span);
	}

	protected Span createChild(Span parent, String name) {
		long id = createId();
		if (parent == null) {
			Span span = Span.builder().name(name).traceId(id).spanId(id).build();
			span = sampledSpan(name, id, span, this.defaultSampler);
			this.spanLogger.logStartedSpan(null, span);
			return span;
		} else {
			return super.createChild(parent, name);
		}
	}

	private Span sampledSpan(String name, long id, Span span, Sampler sampler) {
		if (!sampler.isSampled(span)) {
			return Span.builder().begin(span.getBegin()).name(name).traceId(id).spanId(id).exportable(true).build();
		}
		return span;
	}

	private long createId() {
		return this.random.nextLong();
	}

}
