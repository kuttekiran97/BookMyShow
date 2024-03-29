package com.scaler.bookmyshowfeb23.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "theatres")
public class Theatre extends BaseModel {
    private String name;

    @ManyToOne
    private Region region;

    @OneToMany(mappedBy = "theatre")
    private List<Screen> screens;
}

/*

   1           1
Theatre --- Region
   M           1


   1             M
Theatre ------ Screen
   1             1
 */
