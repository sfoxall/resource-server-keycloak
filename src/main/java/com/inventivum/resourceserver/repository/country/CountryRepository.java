package com.inventivum.resourceserver.repository.country;

import com.inventivum.resourceserver.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {

}
