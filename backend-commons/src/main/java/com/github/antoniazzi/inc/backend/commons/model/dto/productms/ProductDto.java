package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.ProductType;
import com.github.antoniazzi.inc.backend.commons.entity.enumeration.productms.VoucherSupport;
import com.github.antoniazzi.inc.backend.commons.model.dto.BaseAdministrationEntityDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_EMPTY)
public class ProductDto extends BaseAdministrationEntityDto {

	private static final long serialVersionUID = 1L;

	private String sku;

	@NotNull
	private ProductType productType;

	private Integer points;

	@NotNull
	private BigDecimal price;

	@NotNull
	private BigDecimal tax;

	@NotNull
	private ZonedDateTime availableFrom;

	private ZonedDateTime availableTo;

	private VoucherSupport voucherSupport;

	private BigDecimal voucherValue;

	private Integer stock;

	private Boolean archived;

	private Boolean comingSoon;

	private String ledgerAccountName;

	private Boolean euTax;

	private Integer taxLevel;

	private Boolean availableForAffiliates;

	private Boolean userDefinedPrice;

	private Boolean quickCheckout;

	private Boolean forceDirectPayment = false;

	private Long featuredImageId;

	@NotNull
	private Boolean priceRounding = false;

	private String salesPageUrl;

	private BigDecimal generalFlatCommission;

	private BigDecimal generalPercentageCommission;

	private Long invoiceTemplateId;

	private TypeDigitalDto typeDigital;

	private TypeServiceDto typeService;

	private TypeVoucherDto typeVoucher;

	private List<AttributeDto> attributes = new ArrayList<>();

	private List<PaymentScheduleDto> paymentSchedules = new ArrayList<>();

}
