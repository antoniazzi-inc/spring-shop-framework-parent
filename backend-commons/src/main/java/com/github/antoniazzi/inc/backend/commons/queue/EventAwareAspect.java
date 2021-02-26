package com.github.antoniazzi.inc.backend.commons.queue;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.antoniazzi.inc.backend.commons.PropPrefix;

@Aspect
@Component
@ConditionalOnProperty(value = PropPrefix.WPSOFT_QUEUE_ASPECT, havingValue = "true")
public class EventAwareAspect {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private DestinationsProperties dp;

	@Around("@annotation(nl.wpsoft.backend.commons.queue.EventAware)")
	public Object sendEvent(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();

		if (result != null) {
			String exchangeName = dp.getQueues().get("eventms").getExchange();

			MethodSignature signature = (MethodSignature) pjp.getSignature();
			Method method = signature.getMethod();

			EventAware eventAwareAnnotation = method.getAnnotation(EventAware.class);
			String eventType = eventAwareAnnotation.type();
			String eventAction = eventAwareAnnotation.action();

			amqpTemplate.convertAndSend(exchangeName, "",
					new Event(eventType, eventAction, objectMapper.convertValue(result, Map.class)));
		}

		return result;
	}

}
