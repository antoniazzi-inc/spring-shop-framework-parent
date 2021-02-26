package com.github.antoniazzi.inc.backend.commons.async;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Async Configuration Properties
 * 
 * @version 1.0.0
 * @since 12.11.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_ASYNC, ignoreUnknownFields = false)
public class AsyncProperties {

	private Boolean enabled = true;

	private Boolean taskExecutor = true;

	private Boolean securityTaskExecutor = true;

	private AdviceMode mode = AdviceMode.PROXY;

	private Boolean proxyTargetClass = false;

	private Integer maxPoolSize = 1000;

	private Integer corePoolSize = 100;

	private Integer queueCapacity = 100;

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(Boolean taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public Boolean getSecurityTaskExecutor() {
		return securityTaskExecutor;
	}

	public void setSecurityTaskExecutor(Boolean securityTaskExecutor) {
		this.securityTaskExecutor = securityTaskExecutor;
	}

	public AdviceMode getMode() {
		return mode;
	}

	public void setMode(AdviceMode mode) {
		this.mode = mode;
	}

	public Boolean getProxyTargetClass() {
		return proxyTargetClass;
	}

	public void setProxyTargetClass(Boolean proxyTargetClass) {
		this.proxyTargetClass = proxyTargetClass;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public Integer getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(Integer corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public Integer getQueueCapacity() {
		return queueCapacity;
	}

	public void setQueueCapacity(Integer queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

}
