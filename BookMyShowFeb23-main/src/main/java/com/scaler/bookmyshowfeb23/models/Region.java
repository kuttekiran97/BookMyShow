package com.scaler.bookmyshowfeb23.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "regions")
public class Region extends BaseModel {
    //City
    private String name;
    //private List<Theatre> theatres;
}


/*


  1        M
City --- Theatre => 1:M
  1          1
 */