package com.github.antoniazzi.inc.backend.commons.queue;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

@Component
@ConditionalOnProperty(value = PropPrefix.WPSOFT_QUEUE_ENABLED, havingValue = "true")
public class MessageListenerContainerFactory {

	@Autowired
	private ConnectionFactory connectionFactory;

	public MessageListenerContainerFactory() {
	}

	public MessageListenerContainer createMessageListenerContainer(String queueName) {

		SimpleMessageListenerContainer mlc = new SimpleMessageListenerContainer(connectionFactory);

		mlc.addQueueNames(queueName);
		mlc.setAcknowledgeMode(AcknowledgeMode.AUTO);

		return mlc;
	}

}