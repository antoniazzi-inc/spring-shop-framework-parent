package com.github.antoniazzi.inc.backend.commons.model.dto.orderms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.ProductType;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.ServicePriceType;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.VoucherSupport;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseRelationAdministrationEntityDto;
import com.github.antoniazzi.inc.backend.commons.model.dto.IdDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class OrderProductDto extends BaseRelationAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long productId;

	private String sku;

	private String productName;

	private String productDescription;

	private BigDecimal productPrice;

	private BigDecimal taxPercentage;

	private Integer taxLevel;

	private String termsAndConditionsJson;

	private Integer points;

	private ProductType productType;

	private VoucherSupport voucherSupport;

	private BigDecimal voucherValue;

	private String downloadUrl;

	private ServicePriceType priceType;

	private String productTypeDetailsJson;

	private Long paymentScheduleId;

	@Valid
	private OrderProductEventDto orderProductEvent;

	@Valid
	private OrderProductPurchasedVoucherDto orderProductPurchasedVoucher;

	@Valid
	private List<OrderProductAttributeValueDto> orderProductAttributeValues = new ArrayList<>();

	private List<IdDto> productCategories = new ArrayList<>();

	private OrderLineDto orderLine;

}
