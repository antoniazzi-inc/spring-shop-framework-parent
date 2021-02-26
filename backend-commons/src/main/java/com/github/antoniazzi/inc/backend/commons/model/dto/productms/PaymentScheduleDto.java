package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.PaymentSchedulePeriod;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class PaymentScheduleDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private String name;

	private PaymentSchedulePeriod period;

	private Boolean availableForCustomers;

	private ProductDto product;

	private List<PaymentScheduleOptionDto> paymentScheduleOptions = new ArrayList<>();

}
