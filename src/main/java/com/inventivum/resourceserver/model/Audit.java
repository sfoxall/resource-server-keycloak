package com.inventivum.resourceserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Audit extends Base {

    @CreatedDate
    @Column(name = "created_on", columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "deleted_on", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedOn;

    @LastModifiedDate
    @Column(name = "last_updated_on", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdatedOn;

    public Audit(UUID id, LocalDateTime createdOn, LocalDateTime deletedOn, LocalDateTime lastUpdatedOn) {
        super(id);
        this.createdOn = createdOn;
        this.deletedOn = deletedOn;
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Audit() {
    }

}
