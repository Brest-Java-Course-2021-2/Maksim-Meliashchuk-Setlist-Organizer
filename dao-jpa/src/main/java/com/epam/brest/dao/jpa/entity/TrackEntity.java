package com.epam.brest.dao.jpa.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "track")
public class TrackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id", nullable = false)
    private Integer trackId;

    @Column(name = "track_name", nullable = false)
    private String trackName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_band_id")
    @ToString.Exclude
    private BandEntity band;

    @Column(name = "track_tempo")
    private Integer trackTempo;

    @Column(name = "track_duration")
    private Integer trackDuration;

    @Column(name = "track_details")
    private String trackDetails;

    @Column(name = "track_link")
    private String trackLink;

    @Column(name = "track_release_date")
    private LocalDate trackReleaseDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TrackEntity that = (TrackEntity) o;
        return trackId != null && Objects.equals(trackId, that.trackId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
