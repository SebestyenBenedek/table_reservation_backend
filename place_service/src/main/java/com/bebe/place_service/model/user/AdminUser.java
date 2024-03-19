package com.bebe.place_service.model.user;

import com.bebe.place_service.model.Place;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AdminUser extends User {
    @NonNull
    @OneToOne(mappedBy = "owner")
    private Place ownedPlace;
}
