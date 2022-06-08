package cinema;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SeatingController {


    @Autowired
    private SeatPlan cinemaOne;

    @GetMapping("/seats")
    @JsonView(View.Seats.class)
    public SeatPlan returnSeatPlan() {
        return cinemaOne;
    }

    @PostMapping("/purchase")
    @JsonView(View.Purchase.class)
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seatRequested) {
        try {
            Seat seatPurchased = cinemaOne.reserveSeat(seatRequested.getRow(), seatRequested.getColumn());
            return new ResponseEntity<>(new JsonResponseView(seatPurchased), HttpStatus.OK);
        } catch (SeatPlan.SeatFilledException e) {
            return new ResponseEntity<>(new ErrorResponse("The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(new ErrorResponse("The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/return")
    @JsonView(View.Return.class)
    public ResponseEntity<?> returnSeat(@RequestBody Map<String, String> input) {
        try {
            Seat seatReturned = cinemaOne.unreserveSeat(UUID.fromString(input.get("token")));
            return new ResponseEntity<>(new JsonResponseView(seatReturned), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErrorResponse("Wrong token!"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<?> returnStats(@RequestParam(required = false) String password) {
        if (password == null || !password.equals("super_secret")) {
            return new ResponseEntity<>(new ErrorResponse("The password is wrong!"), HttpStatus.UNAUTHORIZED);

        }
        return new ResponseEntity<>(Map.of("current_income", cinemaOne.getCurrentIncome(), "number_of_available_seats", cinemaOne.getAmountOfAvailable()
                , "number_of_purchased_tickets", cinemaOne.getAmountOfPurchased()), HttpStatus.OK);
    }

}
