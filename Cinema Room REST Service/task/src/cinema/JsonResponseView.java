package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.UUID;

public class JsonResponseView {
    @JsonView(View.Purchase.class)
    private UUID token;
    @JsonView(View.Purchase.class)
    private Seat ticket;
    @JsonView(View.Return.class)
    @JsonProperty("returned_ticket")
    private Seat returnedTicket;

    public JsonResponseView(Seat seat) {
        token = seat.getUuid();
        ticket = seat;
        returnedTicket = ticket;

    }
}
