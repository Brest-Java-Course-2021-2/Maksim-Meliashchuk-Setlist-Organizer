package com.epam.brest.dao.jpa.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BandEntity that = (BandEntity) o;
        return bandId != null && Objects.equals(bandId, that.bandId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
