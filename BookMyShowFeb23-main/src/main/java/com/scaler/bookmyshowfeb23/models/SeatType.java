package com.scaler.bookmyshowfeb23.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "seat_types")
public class SeatType extends BaseModel {
    private String name;
}
