package com.github.antoniazzi.inc.backend.commons.queue;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

@Configuration
@ConditionalOnProperty(value = PropPrefix.WPSOFT_QUEUE_ENABLED, havingValue = "true")
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_QUEUE_DESTINATIONS, ignoreUnknownFields = false)
public class DestinationsProperties {

	private Map<String, DestinationInfoProperties> queues = new HashMap<>();

	private Map<String, DestinationInfoProperties> topics = new HashMap<>();

	public Map<String, DestinationInfoProperties> getQueues() {
		return queues;
	}

	public void setQueues(Map<String, DestinationInfoProperties> queues) {
		this.queues = queues;
	}

	public Map<String, DestinationInfoProperties> getTopics() {
		return topics;
	}

	public void setTopics(Map<String, DestinationInfoProperties> topics) {
		this.topics = topics;
	}

	public static class DestinationInfoProperties {

		private String exchange;
		private String alternate;
		private String routingKey;

		public String getExchange() {
			return exchange;
		}

		public void setExchange(String exchange) {
			this.exchange = exchange;
		}

		public String getAlternate() {
			return alternate;
		}

		public void setAlternate(String alternate) {
			this.alternate = alternate;
		}

		public String getRoutingKey() {
			return routingKey;
		}

		public void setRoutingKey(String routingKey) {
			this.routingKey = routingKey;
		}

	}

}