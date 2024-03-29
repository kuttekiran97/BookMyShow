package com.scaler.bookmyshowfeb23.services;

import com.scaler.bookmyshowfeb23.exceptions.ShowNotFoundException;
import com.scaler.bookmyshowfeb23.exceptions.ShowSeatNotAvailableException;
import com.scaler.bookmyshowfeb23.exceptions.UserNotFoundException;
import com.scaler.bookmyshowfeb23.models.*;
import com.scaler.bookmyshowfeb23.repositories.BookingRepository;
import com.scaler.bookmyshowfeb23.repositories.ShowRepository;
import com.scaler.bookmyshowfeb23.repositories.ShowSeatRepository;
import com.scaler.bookmyshowfeb23.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculatorService priceCalculatorService;
    private BookingRepository bookingRepository;

    BookingService(UserRepository userRepository, ShowRepository showRepository,
                   ShowSeatRepository showSeatRepository, PriceCalculatorService priceCalculatorService,
                   BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculatorService = priceCalculatorService;
        this.bookingRepository = bookingRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookTicket(Long userId, Long showId, List<Long> showSeatIds) throws UserNotFoundException, ShowNotFoundException, ShowSeatNotAvailableException {
        /*
        -------TODAY WE'LL HAVE LOCK ON THE COMPLETE METHOD (TAKE A LOCK)---------
        1. Get the user with the given userId.
        2. Get the show with the given showId.
        3. Get the list of showSeats with the given showSeatIds.
        4. Check the status of all the showSeat objects.
        5. If NOT available, throw an Exception.
        6. If YES, then block the seats.
        ------------TAKE A LOCK-----------------
        7. Check the status of the showSeats again.
        8. Save the status in the DB.
        ------------RELEASE THE LOCK-----------------
        9. Create the Booking the object.
        10. Return the Booking Object.
        -------TODAY WE'LL HAVE LOCK ON THE COMPLETE METHOD (RELEASE THE LOCK)---------
         */

        // 1. Get the user with the given userId.
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("Invalid userId");
        }
        User user = optionalUser.get();

        // 2. Get the show with the given showId.
        Optional<Show> optionalShow = showRepository.findById(showId);
        if (optionalShow.isEmpty()) {
            throw new ShowNotFoundException("Invalid Show");
        }
        Show show = optionalShow.get();

        //3. Get the list of showSeats with the given showSeatIds.
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        //4. Check the status of all the showSeat objects.
        for (ShowSeat showSeat : showSeats) {
            if (!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
                throw new ShowSeatNotAvailableException("Show Seat not found");
            }
        }

        //5.  If YES, then block the seats.
        //6. Save the status in the DB.
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            savedShowSeats.add(showSeatRepository.save(showSeat)); //update
        }

        //7. Create the Booking the object.
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowSeats(savedShowSeats);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(priceCalculatorService.calculatePrice(showSeats, show));
        Booking savedBooking = bookingRepository.save(booking);

        //Payment workflow will start after this.
        //1. If payment is success, change the booking to CONFIRMED.
        //2. If payment fails or time out, release the seats.
        return savedBooking;
    }
}
