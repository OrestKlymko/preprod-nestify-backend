package org.nestify.backend.model;

import lombok.Data;
import org.nestify.backend.model.enums.Advantage;
import org.nestify.backend.model.enums.AllowedStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
public class PropertyRealty {
	private float square;
	private int floor;
	private int room;
	private long realtyPrice;
	private long energyPrice;
	private long totalPrice;
	private String material;
	private AllowedStatus withPets;
	private AllowedStatus withKids;
	private List<Advantage> advantageList;
}
