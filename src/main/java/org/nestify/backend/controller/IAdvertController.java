package org.nestify.backend.controller;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.nestify.backend.dto.AdvertFinalPageDto;
import org.nestify.backend.dto.AdvertMapDto;
import org.nestify.backend.dto.AdvertSearchFilterDto;
import org.nestify.backend.dto.PointMapDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;


public interface IAdvertController {

	@GetMapping("/advert/{id}")
	@Tag(name = "Інфо кінцевої сторінки")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema =
					@Schema(implementation = AdvertFinalPageDto.class))})
			}
	)
	ResponseEntity<?> getFinalPageAdvertById(@PathVariable("id") String id);

	@GetMapping("/search")
	@Tag(name = "Пошук по фільтру")
	@Parameters({
			@Parameter(name = "priceFrom", description = "Мінімальна сума", example = "0"),
			@Parameter(name = "priceTo", description = "Гранична сума", example = "1000000"),
			@Parameter(name = "rooms", description = "Кількість кімнат, які потрібно відфільтрувати", example = "1,2,3"),
			@Parameter(name = "districts", description = "Назви районів, які потрібно відфільтрувати", example = "Bratislava Raca,Bratislava III"),
			@Parameter(name = "address", description = "Назви адреси, які потрібно відфільтрувати", example = "Znievska 1"),
			@Parameter(name = "city", description = "Місто", example = "Bratislava"),
			@Parameter(name = "offset", description = "кількість записів, які потрібно пропустити від початку", example = "0, якщо потрібно першу сторінку"),
			@Parameter(name = "limit", description = "Кількість записів, які потрібно вивести", example = "5"),
			@Parameter(name = "typeOwner", description = "Тип продавця", example = "OWNER або REALTY (мапимо відповідно Vlastník, Realitná kancelária)"),
			@Parameter(name = "withPets", description = "Дозволено з тваринами", example = "YES, NO, UNDEFINED (мапимо відповіно Áno, Nie, Nie je uvedené)"),
			@Parameter(name = "withKids", description = "Дозволено з дітьми", example = "YES, NO, UNDEFINED  (мапимо відповіно Áno, Nie, Nie je uvedené)"),
			@Parameter(name = "typeRealty", description = "Тип нерухомості", example = "FLAT (Byt), HOUSE (Dom), HALF_FLAT (Garsónka), DACHA (Záhradný dom), ROOM (Izba), COMMERCIAL_REALTY (Komerčná nehnuteľnosť)")
	})
	ResponseEntity<Page<AdvertSearchFilterDto>> filterSearchForm(@RequestParam Map<String, List<String>> filterParams);

	@GetMapping("/map/view/{id}")
	@Tag(name = "Дістати оголошення на карті")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema =
					@Schema(implementation = AdvertMapDto.class))})
			}
	)
	ResponseEntity<?> getAdvertOnMap(@PathVariable("id") String id);


	@GetMapping("/map/view/")
	@Tag(name = "Фільтр точок на View Port")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200",
							content = @Content(array = @ArraySchema(
									schema = @Schema(implementation = PointMapDto.class))), description = "Get location information")
			}
	)
	ResponseEntity<?> getAdvertsOnMap(
			@RequestParam("sw_lng") double swLng,
			@RequestParam("sw_lat") double swLat,
			@RequestParam("ne_lng") double neLgt,
			@RequestParam("ne_lat") double neLat
	);
}
