package com.bebe.place_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String address;

    private double rating;

    private int price;

    private String description;

    private String menu;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Characteristic> characteristics;

    @ElementCollection
    @CollectionTable(name = "place_images", joinColumns = @JoinColumn(name = "place_id"))
    @Column(name = "images")
    private Set<String> images;

    @NonNull
    private Long adminUserId;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(name = "tables")
    @JsonManagedReference
    private Set<PlaceTable> tables;

    @ElementCollection
    @CollectionTable(name = "reservation_ids", joinColumns = @JoinColumn(name = "place_id"))
    @Column(name = "reservation_ids")
    private Set<Long> reservationIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Double.compare(rating, place.rating) == 0 &&
                price == place.price &&
                Objects.equals(id, place.id) &&
                Objects.equals(name, place.name) &&
                Objects.equals(address, place.address) &&
                Objects.equals(description, place.description) &&
                Objects.equals(menu, place.menu) &&
                Objects.equals(characteristics, place.characteristics) &&
                Objects.equals(images, place.images) &&
                Objects.equals(adminUserId, place.adminUserId) &&
                Objects.equals(tables, place.tables) &&
                Objects.equals(reservationIds, place.reservationIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, rating, price, description, menu, characteristics, images, adminUserId, reservationIds);
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", menu='" + menu + '\'' +
                ", characteristics=" + characteristics +
                ", images=" + images +
                ", adminUserId=" + adminUserId +
                ", tables=" + tables +
                ", reservationIds=" + reservationIds +
                '}';
    }
}
