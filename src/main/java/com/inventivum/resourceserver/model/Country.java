package com.inventivum.resourceserver.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "country", schema = "core")
@AttributeOverride(name = "id", column = @Column(name = "country_id", columnDefinition = "uuid"))
public class Country extends Audit {

    @Column(name = "name")
    private String name;

    @Column(name = "iso3_code", unique = true)
    private String iso3Code;

    @Column(name = "iso3_short_code", unique = true)
    private String iso3ShortCode;

    @Column(name = "dialling_code")
    private String diallingCode;

    public Country() {
    }

    @Builder
    public Country(UUID id, LocalDateTime createdOn, LocalDateTime deletedOn, LocalDateTime lastUpdatedOn, String name,
                   String iso3Code, String iso3ShortCode, String diallingCode)
    {
        super(id, createdOn, deletedOn, lastUpdatedOn);
        this.name = name;
        this.iso3Code = iso3Code;
        this.iso3ShortCode = iso3ShortCode;
        this.diallingCode = diallingCode;
    }
}
