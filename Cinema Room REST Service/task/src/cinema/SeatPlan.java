package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class SeatPlan {

    @JsonIgnore
    private Seat[] seatPlan;
    @JsonIgnore
    private List<Seat> purchasedSeats;
    @JsonView(View.Public.class)
    @JsonProperty("total_rows")
    private int rows;
    @JsonView(View.Public.class)
    @JsonProperty("total_columns")
    private int columns;
    @JsonView(View.Public.class)
    @JsonProperty("available_seats")
    private List<Seat> availableSeats;
    @JsonIgnore
    private int currentIncome;


    public SeatPlan(int row, int column) {
        this.rows = row;
        this.columns = column;
        seatPlan = new Seat[rows * columns];
        int iter = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                seatPlan[iter] = new Seat(i + 1, j + 1);
                iter++;
            }

        }
        availableSeats = new ArrayList<>(Arrays.asList(seatPlan));
        purchasedSeats = new LinkedList<>();
        currentIncome = 0;
    }

    public Seat reserveSeat(int row, int column) throws SeatFilledException {
        int seatPlanIndex = twoToOneDimensionConv(row - 1, column - 1);
        if (row > rows || column > columns || row < 1 || column < 1) {
            throw new IndexOutOfBoundsException();
        }
        if (seatPlan[seatPlanIndex].isTaken()) {
            throw new SeatFilledException("This seat is occupied");
        } else {

            Seat seat = seatPlan[seatPlanIndex];
            availableSeats.remove(seat);
            purchasedSeats.add(seat);
            seat.setTaken(true);
            seat.setUuid(UUID.randomUUID());
            currentIncome += seat.getPrice();
            return seat;
        }
    }

    public Seat unreserveSeat(UUID uuid) throws NoSuchElementException {
        Seat foundSeat = null;
        for (Seat seat : purchasedSeats) {
            if (seat.getUuid().equals(uuid)) {
                foundSeat = seat;
            }
        }
        if (foundSeat == null) throw new NoSuchElementException();
        foundSeat.setTaken(false);
        availableSeats.add(foundSeat);
        purchasedSeats.remove(foundSeat);
        currentIncome -= foundSeat.getPrice();
        return foundSeat;
    }

    public void clearSeats() {
        for (Seat seat : seatPlan) {
            seat.setTaken(false);
        }
        availableSeats = List.of(seatPlan);
        purchasedSeats.clear();
    }

    public class SeatFilledException extends RuntimeException {
        public SeatFilledException(String message) {
            super(message);
        }
    }

    private int twoToOneDimensionConv(int row, int column) {
        int result = (row * this.rows) + column;
        return result;
    }

    public int getAmountOfPurchased() {
        return purchasedSeats.size();
    }

    public int getAmountOfAvailable() {
        return availableSeats.size();
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public Seat[] getSeatPlan() {
        return seatPlan;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

}
