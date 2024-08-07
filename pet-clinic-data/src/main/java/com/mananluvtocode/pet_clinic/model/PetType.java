package com.mananluvtocode.pet_clinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "types")
public class PetType extends BaseEntity {
    @Builder
    public PetType(Long id, String name) {
        super(id);
        this.name = name;
    }
    @Column(name = "name")
    private String name;
}
