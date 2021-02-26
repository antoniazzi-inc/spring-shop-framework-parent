package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TermsAndConditions implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean agreeConditions;
	private Boolean agreePrivacyStatement;
	private List<MultiLanguage> conditionsLinkText;
	private List<MultiLanguage> privacyStatementLinkText;
	private String linkToConditions;
	private String linkToPrivacyStatement;

}
