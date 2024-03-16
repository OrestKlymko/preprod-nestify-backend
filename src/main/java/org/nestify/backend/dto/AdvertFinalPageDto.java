package org.nestify.backend.dto;

import lombok.Builder;
import org.nestify.backend.model.PropertyRealty;
import org.nestify.backend.model.enums.TypeOwner;


import java.util.List;
import java.util.UUID;

@Builder
public record AdvertFinalPageDto(
		String id,
		String address,
		PropertyRealty propertyRealty,
		String description,
		String nameOwner,
		TypeOwner typeOwner,
		String numberPhone,
		String city,
		String district,
		String finalUrl,
		List<String> images
) {

}
