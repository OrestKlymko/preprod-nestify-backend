package org.nestify.backend.service;


import lombok.extern.slf4j.Slf4j;
import org.nestify.backend.dto.AdvertFinalPageDto;
import org.nestify.backend.dto.AdvertMapDto;
import org.nestify.backend.dto.AdvertSearchFilterDto;
import org.nestify.backend.dto.PointMapDto;
import org.nestify.backend.mapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import lombok.AllArgsConstructor;
import org.nestify.backend.exceptions.NotFoundException;
import org.nestify.backend.model.AdvertModel;
import org.nestify.backend.repository.AdvertRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AdvertService implements IAdvertService {
	private final AdvertRepository advertRepository;
	private final MongoTemplate mongoTemplate;

	@Override
	public AdvertFinalPageDto getFinalPageAdvertById(String uuid) throws NotFoundException {
		AdvertModel advertModel = advertRepository.findById(uuid)
				.orElseThrow(() -> new NotFoundException(String.format("Advert with id %s not found", uuid)));
		return ModelMapper.mapperToAdvertFinalPageDto(advertModel);
	}

	@Override
	public PageImpl<AdvertSearchFilterDto> filterSearchForm(Map<String, List<String>> urlParameters) {

		Query query = new Query();
		long priceFrom = 0;
		long priceTo = Long.MAX_VALUE;
		for (Map.Entry<String, List<String>> parameter : urlParameters.entrySet()) {
			switch (parameter.getKey()) {
				case "city":
					query.addCriteria(Criteria.where("address.city").regex(String.valueOf(parameter.getValue()), "i"));
					break;
				case "address":
					query.addCriteria(Criteria.where("address.addressName").regex(String.valueOf(parameter.getValue()), "i"));
					break;
				case "rooms":
					List<Integer> list = Arrays.stream(String.valueOf(parameter.getValue()).split(","))
							.map(Integer::parseInt)
							.collect(Collectors.toList());

					query.addCriteria(Criteria.where("propertyRealty.room").in(list));
					break;
				case "districts":
					String[] splitDistrict = String.valueOf(parameter.getValue()).split(",");
					List<Criteria> districtCriterias = Arrays.stream(splitDistrict).toList().stream()
							.map(district -> Criteria.where("address.district").regex(district, "i"))
							.toList();
					query.addCriteria(new Criteria().orOperator(districtCriterias.toArray(new Criteria[0])));
					break;
				case "priceFrom":
					priceFrom = Long.parseLong(String.valueOf(parameter.getValue()));
					break;
				case "priceTo":
					priceTo = Long.parseLong(String.valueOf(parameter.getValue()));
					break;
				case "typeOwner":
					query.addCriteria(Criteria.where("seller.typeOwner").is(String.valueOf(parameter.getValue())));
					break;
				case "withKids":
					query.addCriteria(Criteria.where("propertyRealty.withKids").is(String.valueOf(parameter.getValue())));
					break;
				case "withPets":
					query.addCriteria(Criteria.where("propertyRealty.withPets").is(parameter.getValue()));
					break;
				case "typeRealty":
					query.addCriteria(Criteria.where("typeRealty").is(parameter.getValue()));
					break;
			}
		}

		query.addCriteria(Criteria.where("propertyRealty.totalPrice").gte(priceFrom).lte(priceTo));


		String offset = String.valueOf(urlParameters.get("offset"));
		String limit = String.valueOf(urlParameters.get("limit"));
		PageRequest pageRequest = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
		query.with(pageRequest);

		long total = mongoTemplate.count(query, AdvertModel.class);
		List<AdvertSearchFilterDto> adverts = mongoTemplate.find(query, AdvertModel.class)
				.stream()
				.map(ModelMapper::mapperToAdvertSearchFilterDto)
				.collect(Collectors.toList());
		System.out.println("adverts.size() = " + adverts.size());
		return new PageImpl<>(adverts, pageRequest, total);
	}

	@Override
	public AdvertMapDto getAdvertOnMapById(String uuid) throws NotFoundException {
		AdvertModel advertModel = advertRepository.findById(uuid)
				.orElseThrow(() -> new NotFoundException(String.format("Advert with id %s not found", uuid)));
		return ModelMapper.mapperToAdvertMapDto(advertModel);
	}

	@Override
	public List<AdvertModel> findAll() {
		return advertRepository.findAll();
	}

	@Override
	public List<PointMapDto> getPointsOnPortView(double swLng, double swLat, double neLat, double neLgt) {
		Query query = new Query();
		query.addCriteria(Criteria.where("address.latitude").gte(swLat).lte(neLat))
				.addCriteria(Criteria.where("address.longitude").gte(swLng).lte(neLgt));

		return mongoTemplate
				.find(query, AdvertModel.class)
				.stream()
				.map(ModelMapper::mapToPointMapDto)
				.toList();
	}


}
