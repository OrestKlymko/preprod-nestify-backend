package org.nestify.backend.service;

import org.nestify.backend.dto.AdvertFinalPageDto;
import org.nestify.backend.dto.AdvertMapDto;
import org.nestify.backend.dto.AdvertSearchFilterDto;
import org.nestify.backend.dto.PointMapDto;
import org.nestify.backend.exceptions.NotFoundException;
import org.nestify.backend.model.AdvertModel;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Map;

public interface IAdvertService {
	AdvertFinalPageDto getFinalPageAdvertById(String uuid) throws NotFoundException;

	PageImpl<AdvertSearchFilterDto> filterSearchForm(Map<String, List<String>> request);

	AdvertMapDto getAdvertOnMapById(String uuid) throws NotFoundException;

	List<AdvertModel> findAll();

	List<PointMapDto> getPointsOnPortView(double swLng, double swLat, double neLat, double neLgt);


}