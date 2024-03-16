package org.nestify.backend.model;

import lombok.Data;
import org.nestify.backend.model.enums.TypeOwner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
public class Seller {
	private String nameOwner;
	private TypeOwner typeOwner;
	private String numberPhone;
	private List<AdvertModel> advertModels;
}
