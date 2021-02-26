package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaWrapperDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private List<MediaDto> images;

	private String bodyContentType;

}
