package org.nestify.backend.controller;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.nestify.backend.exceptions.NotFoundException;
import org.nestify.backend.dto.AdvertSearchFilterDto;
import org.nestify.backend.model.AdvertModel;
import org.nestify.backend.service.IAdvertService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/adverts/")
public class AdvertController implements IAdvertController {
	private final IAdvertService advertService;

	@Override
	public ResponseEntity<?> getFinalPageAdvertById(String uuid) {
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(advertService.getFinalPageAdvertById(uuid));
		} catch (NotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Page<AdvertSearchFilterDto>> filterSearchForm(Map<String, List<String>> searchRequestDto) {
		Page<AdvertSearchFilterDto> results = advertService.filterSearchForm(searchRequestDto);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(results);
	}

	@Override
	public ResponseEntity<?> getAdvertOnMap(String id) {
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(advertService.getAdvertOnMapById(id));
		} catch (NotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> getAdvertsOnMap(double swLng, double swLat, double neLgt, double neLat) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(advertService.getPointsOnPortView(swLng, swLat, neLat, neLgt));
	}


	@Hidden
	@GetMapping
	public List<AdvertModel> get() {
		return advertService.findAll();
	}

}
