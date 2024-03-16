package org.nestify.backend.service;

import org.nestify.backend.dto.AdvertFinalPageDto;
import org.nestify.backend.dto.AdvertSearchFilterDto;
import org.nestify.backend.exceptions.NotFoundException;
import org.nestify.backend.model.AdvertModel;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IAdvertService {
	public AdvertFinalPageDto getFinalPageAdvertById(String uuid) throws NotFoundException;

	public PageImpl<AdvertSearchFilterDto> filterSearchForm(Map<String,List<String>> request);

	List<AdvertModel> findAll();


}