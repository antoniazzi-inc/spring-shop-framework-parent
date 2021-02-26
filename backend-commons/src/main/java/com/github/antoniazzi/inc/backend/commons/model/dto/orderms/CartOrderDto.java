package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class CartOrderDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private BigDecimal nettoAmount;

	private BigDecimal discountAmount;

	private BigDecimal taxAmount;

	private BigDecimal paymentMethodCostAmount;

	private BigDecimal shippingCostAmount;

	private BigDecimal totalAmount;

	@NotNull
	private String currency;

	private BigDecimal euroConversionRate;

	private String relationRequirementJson;

	@NotNull
	private String languageCode;

	private ZonedDateTime finishedOn;

	private InvoiceDto invoice;

	@Valid
	@NotNull
	private CustomerBillingAddressDto customerBillingAddress;

	@Valid
	private CustomerDeliveryAddressDto customerDeliveryAddress;

	@Valid
	private OrderPaymentMethodDto orderPaymentMethod;

	@Valid
	@NotNull
	private OrderCustomerDto orderCustomer;

	@Valid
	@NotEmpty
	private List<OrderLineDto> orderLines = new ArrayList<>();

	@Valid
	private List<OrderDiscountLineDto> orderDiscountLines = new ArrayList<>();

}
