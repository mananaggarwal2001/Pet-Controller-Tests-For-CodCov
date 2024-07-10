package com.mananluvtocode.pet_clinic.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {
    @Builder
    public Visit(Long id, LocalDate date, String description) {
        super(id);
        this.date = date;
        this.description = description;
    }

    @Column(name = "visit_data")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
