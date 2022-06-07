package cinema;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class SeatingController {


    @Autowired
    private SeatPlan cinemaOne;

    @GetMapping("/seats")
    public SeatPlan returnSeatPlan() {
        return cinemaOne;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seatRequested) {
        try {
            Seat seatPurchased = cinemaOne.reserveSeat(seatRequested.getRow(), seatRequested.getColumn());
            return new ResponseEntity<>(seatPurchased, HttpStatus.OK);
        } catch (SeatPlan.SeatFilledException e) {
            return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
    }
}
