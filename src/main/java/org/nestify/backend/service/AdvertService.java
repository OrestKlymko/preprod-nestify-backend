package org.nestify.backend.service;


import org.nestify.backend.dto.AdvertFinalPageDto;
import org.nestify.backend.dto.AdvertSearchFilterDto;
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
		int priceFrom = 0;
		int priceTo = Integer.MAX_VALUE;
		for (Map.Entry<String, List<String>> parameter : urlParameters.entrySet()) {
			switch (parameter.getKey()) {
				case "city":
					query.addCriteria(Criteria.where("address.city").regex(String.valueOf(parameter.getValue()), "i"));
					break;
				case "address":
					query.addCriteria(Criteria.where("address.addressName").regex(String.valueOf(parameter.getValue()), "i"));
					break;
				case "rooms":
					String[] split = String.valueOf(parameter.getValue()).split(",");
					List<Integer> list = Arrays.stream(split).map(Integer::parseInt).toList();
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
					priceFrom = Integer.parseInt(String.valueOf(parameter.getValue()));
					break;
				case "priceTo":
					priceTo = Integer.parseInt(String.valueOf(parameter.getValue()));
					break;
				case "typeOwner":
					query.addCriteria(Criteria.where("seller.typeOwner").is(String.valueOf(parameter.getValue())));
					break;
				case "withKids":
					query.addCriteria(Criteria.where("propertyRealty.withKids").is(String.valueOf(parameter.getValue())));
					break;
				case "withPets":
					query.addCriteria(Criteria.where("propertyRealty.withPets").is(String.valueOf(parameter.getValue())));
					break;
				case "typeRealty":
					query.addCriteria(Criteria.where("propertyRealty.typeRealty").is(String.valueOf(parameter.getValue())));
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

		return new PageImpl<>(adverts, pageRequest, total);
	}

	@Override
	public List<AdvertModel> findAll() {
		return advertRepository.findAll();
	}


}
