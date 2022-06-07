package cinema;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.UUID;

public class JsonResponseView {
    @JsonView(View.Purchase.class)
    private UUID token;
    @JsonView(View.Public.class)
    private Seat ticket;

    public JsonResponseView(Seat seat) {
        token = seat.getUuid();
        ticket = seat;

    }
}
