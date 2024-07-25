package com.zero.productcatalogservice.repository;

import com.zero.productcatalogservice.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}