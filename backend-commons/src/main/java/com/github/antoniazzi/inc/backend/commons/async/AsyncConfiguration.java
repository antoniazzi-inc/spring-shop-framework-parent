package com.github.antoniazzi.inc.backend.commons.async;

import java.util.UUID;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Async Configuration
 * 
 * @version 1.0.0
 * @since 12.11.2019
 * @author Kristijan Georgiev
 */
@Configuration
@EnableAsync(mode = AdviceMode.ASPECTJ, proxyTargetClass = true)
@ConditionalOnProperty(value = PropPrefix.WPSOFT_ASYNC_ENABLED, havingValue = "true")
public class AsyncConfiguration {

	private static final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

	static {
		log.info("Async Enabled");
	}

	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired
	private AsyncProperties asyncProperties;

	private final ThreadPoolTaskExecutor buildExecutor(final ThreadPoolTaskExecutor executor) {
		log.info("Setting up ThreadPoolTaskExecutor");

		executor.setCorePoolSize(asyncProperties.getCorePoolSize());
		executor.setMaxPoolSize(asyncProperties.getMaxPoolSize());
		executor.setQueueCapacity(asyncProperties.getQueueCapacity());

		if (applicationName != null) {
			executor.setThreadNamePrefix(applicationName + "-");
		} else {
			executor.setThreadNamePrefix(UUID.randomUUID().toString() + "-");
		}

		log.info("threadNamePrefix=" + executor.getThreadNamePrefix() + ", corePoolSize=" + asyncProperties.getCorePoolSize() + ", maxPoolSize="
				+ asyncProperties.getMaxPoolSize() + ", queueCapacity=" + asyncProperties.getQueueCapacity());

		executor.afterPropertiesSet();
		executor.initialize();

		return executor;
	}

	// @Order(10)
	// @ConditionalOnMissingBean(Executor.class)
//	@AutoConfigureBefore(AsyncTaskExecutorConfiguration.class)
	// @ConditionalOnClass(DelegatingSecurityContextAsyncTaskExecutor.class)
	@ConditionalOnProperty(value = PropPrefix.WPSOFT_ASYNC_SECURITY_TASK_EXECUTOR, havingValue = "true", matchIfMissing = true)
	public class DelegatingSecurityContextAsyncTaskExecutorConfiguration {

		@Bean
		@Primary
		public Executor taskExecutor() {
			log.info("Setting up DelegatingSecurityContextAsyncTaskExecutor");

			DelegatingSecurityContextAsyncTaskExecutor securityExecutor = new DelegatingSecurityContextAsyncTaskExecutor(
					AsyncConfiguration.this.buildExecutor(new ThreadPoolTaskExecutor()), SecurityContextHolder.getContext());

			log.info("Setting up ExceptionHandlingAsyncTaskExecutor");

			return new ExceptionHandlingAsyncTaskExecutor(securityExecutor);
		}

	}
	/*
	 * @Order(20) // @ConditionalOnMissingBean(Executor.class)
	 * 
	 * @ConditionalOnProperty(value = PropPrefix.WPSOFT_ASYNC_TASK_EXECUTOR, havingValue = "true", matchIfMissing = true)
	 * public class AsyncTaskExecutorConfiguration {
	 * 
	 * @Bean
	 * 
	 * @Primary public Executor taskExecutor() { log.info("Setting up ExceptionHandlingAsyncTaskExecutor");
	 * 
	 * return new ExceptionHandlingAsyncTaskExecutor( AsyncConfiguration.this.buildExecutor(new ThreadPoolTaskExecutor()));
	 * }
	 * 
	 * }
	 */

}
