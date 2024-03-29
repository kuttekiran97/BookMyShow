package com.scaler.bookmyshowfeb23.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "seats")
public class Seat extends BaseModel {
    private String number;
    @ManyToOne
    private SeatType seatType;
    private int rowVal;
    private int colVal;

    @ManyToOne
    private Screen screen;
}
