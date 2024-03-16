package org.nestify.backend.model;


import lombok.Data;
import org.nestify.backend.model.enums.TypeRealty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "adverts")
public class AdvertModel {
	@Id
	private String id;
	private TypeRealty typeRealty;
	private LocalDateTime publishedAt;
	private LocalDateTime edited_at;
	private String description;
	private String finalUrl;
	private List<String> images;
	private PropertyRealty propertyRealty;
	private Address address;
	private Seller seller;
}
