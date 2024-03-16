package org.nestify.backend.repository;


import org.nestify.backend.model.AdvertModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvertRepository extends MongoRepository<AdvertModel, String> {
}
