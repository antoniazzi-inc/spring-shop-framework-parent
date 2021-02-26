package com.github.antoniazzi.inc.backend.commons.pricing;

import javax.annotation.PostConstruct;

import org.javamoney.moneta.CurrencyUnitBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

@Service
@ConditionalOnProperty(value = PropPrefix.WPSOFT_ORDER_ENGINE_ENABLED, havingValue = "true")
public class OrderEngineService {

	@PostConstruct
	public void init() {
		CurrencyUnitBuilder.of("MONEY", "MoneyCurrencyProvider").setDefaultFractionDigits(2).build(true);
		CurrencyUnitBuilder.of("POINTS", "PointsCurrencyProvider").setDefaultFractionDigits(0).build(true);
		CurrencyUnitBuilder.of("MINUTES", "MinutesCurrencyProvider").setDefaultFractionDigits(0).build(true);
	}

}
