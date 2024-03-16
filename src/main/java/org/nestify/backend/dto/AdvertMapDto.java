package org.nestify.backend.dto;

import lombok.Builder;

@Builder
public record AdvertMapDto(
		String id,
		String linkImage,
		long price,
		int room,
		float square,
		int floor,
		String address
) {
}
