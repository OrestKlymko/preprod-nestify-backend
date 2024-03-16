package org.nestify.backend.model;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
public class Address {
	private int id;
	private UUID advertId;
	private int buildIdMapTiler;
	private String district;
	private String addressName;
	private String city;
	private float latitude;
	private float longitude;
}
