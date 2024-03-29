package com.scaler.bookmyshowfeb23.repositories;

import com.scaler.bookmyshowfeb23.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Override
    List<ShowSeat> findAllById(Iterable<Long> longs);

    @Override
    ShowSeat save(ShowSeat showSeat);
    /*
    1. If we are creating a new Object, then this save method will act like an insert.
    2. If we are updating the object, it will just save the updated.
    -- upsert
     */
}
