package org.nestify.backend.controller;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.nestify.backend.dto.AdvertSearchFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


public interface IAdvertController {

	@GetMapping("/advert/{id}")
	@Tag(name="Інфо кінцевої сторінки")
	ResponseEntity<?> getFinalPageAdvertById(@PathVariable String id);

	@GetMapping("/search")
	@Tag(name="Пошук по фільтру")
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

}
