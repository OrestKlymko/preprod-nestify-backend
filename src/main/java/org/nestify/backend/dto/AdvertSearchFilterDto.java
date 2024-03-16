package org.nestify.backend.dto;

import lombok.Builder;
import org.nestify.backend.model.enums.Advantage;
import org.nestify.backend.model.enums.AllowedStatus;
import org.nestify.backend.model.enums.TypeOwner;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AdvertSearchFilterDto(
		String id,
		long price,
		int room,
		float square,
		int floor,
		String address,
		String description,
		List<Advantage> advantageList,
		LocalDateTime published_at,
		String district,
		int buildIdMapTiler
) {
}
