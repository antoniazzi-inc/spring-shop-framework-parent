package com.github.antoniazzi.inc.backend.commons.queue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

@Configuration
@ConditionalOnProperty(value = PropPrefix.WPSOFT_QUEUE_ENABLED, havingValue = "true")
public class DestinationsConfiguration {

	private static Logger log = LoggerFactory.getLogger(DestinationsConfiguration.class);

	{
		log.debug("Registering Queues");
	}

	@Autowired
	private AmqpAdmin amqpAdmin;

	@Autowired
	private DestinationsProperties destinationsProperties;

	@PostConstruct
	public void setupQueueDestinations() {
		destinationsProperties.getQueues().forEach((key, destination) -> {
			Exchange ex = ExchangeBuilder.directExchange(destination.getExchange()).durable(true).build();

			amqpAdmin.declareExchange(ex);

			String routingKey = destination.getRoutingKey();
			String queueName = destination.getRoutingKey();

			if (routingKey == null || routingKey.equals("")) {
				routingKey = "";
				queueName = destination.getExchange() + "Queue";
			}

			Queue q = QueueBuilder.durable(queueName).build();

			amqpAdmin.declareQueue(q);

			Binding b = BindingBuilder.bind(q).to(ex).with(routingKey).noargs();

			amqpAdmin.declareBinding(b);
		});
	}

	@PostConstruct
	public void setupTopicDestinations() {
		destinationsProperties.getTopics().forEach((key, destination) -> {
			ExchangeBuilder exb = ExchangeBuilder.topicExchange(destination.getExchange()).durable(true);

			if (destination.getAlternate() != null) {
				Exchange altEx = ExchangeBuilder.directExchange(destination.getAlternate()).durable(true).build();

				amqpAdmin.declareExchange(altEx);

				exb = exb.alternate(destination.getAlternate());
			}

			Exchange ex = exb.build();

			amqpAdmin.declareExchange(ex);
		});
	}
}
