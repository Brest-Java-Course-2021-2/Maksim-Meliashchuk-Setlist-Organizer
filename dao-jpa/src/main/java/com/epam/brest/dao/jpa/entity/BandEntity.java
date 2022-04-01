package com.epam.brest.dao.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "band")
public class BandEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "band_id", nullable = false)
    private Integer bandId;

    @Column(name = "band_name", nullable = false)
    private String bandName;

    @Column(name = "band_details")
    private String bandDetails;

}
