package com.cnwidsom.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.core.env.Environment;

@ConfigurationProperties(ConfigClientProperties.PREFIX)
public class CustomConfigClientProperties extends ConfigClientProperties {

	public CustomConfigClientProperties(Environment environment) {
		super(environment);
	}

	@Override
	public ConfigClientProperties override(org.springframework.core.env.Environment environment) {
		ConfigClientProperties override = super.override(environment);
		if (environment.containsProperty(ConfigClientProperties.PREFIX + ".uri")) {
			override.setUri(environment.getProperty(ConfigClientProperties.PREFIX + ".uri"));
		}
		return override;
	}
}
