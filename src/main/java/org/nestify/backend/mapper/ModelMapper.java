package org.nestify.backend.mapper;

import org.nestify.backend.model.AdvertModel;
import org.nestify.backend.dto.AdvertFinalPageDto;
import org.nestify.backend.dto.AdvertSearchFilterDto;

public class ModelMapper {
	public static AdvertSearchFilterDto mapperToAdvertSearchFilterDto(AdvertModel advertModel) {
		return AdvertSearchFilterDto.builder()
				.id(advertModel.getId())
				.floor(advertModel.getPropertyRealty().getFloor())
				.price(advertModel.getPropertyRealty().getTotalPrice())
				.address(advertModel.getAddress().getAddressName())
				.room(advertModel.getPropertyRealty().getRoom())
				.published_at(advertModel.getPublishedAt())
				.square(advertModel.getPropertyRealty().getSquare())
				.description(advertModel.getDescription())
				.advantageList(advertModel.getPropertyRealty().getAdvantageList())
				.district(advertModel.getAddress().getDistrict())
				.build();
	}

	public static AdvertFinalPageDto mapperToAdvertFinalPageDto(AdvertModel advertModel) {
		return AdvertFinalPageDto.builder()
				.id(advertModel.getId())
				.numberPhone(advertModel.getSeller().getNumberPhone())
				.images(advertModel.getImages())
				.description(advertModel.getDescription())
				.nameOwner(advertModel.getSeller().getNameOwner())
				.typeOwner(advertModel.getSeller().getTypeOwner())
				.propertyRealty(advertModel.getPropertyRealty())
				.address(advertModel.getAddress().getAddressName())
				.city(advertModel.getAddress().getCity())
				.district(advertModel.getAddress().getDistrict())
				.finalUrl(advertModel.getFinalUrl())
				.build();
	}
}
