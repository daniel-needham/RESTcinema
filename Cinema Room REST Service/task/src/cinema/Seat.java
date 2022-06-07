package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.UUID;

public class Seat {
    @JsonView(View.Public.class)
    private int row;
    @JsonView(View.Public.class)
    private int column;
    @JsonIgnore
    private boolean taken;
    @JsonView(View.Public.class)
    private int price;

    @JsonIgnore
    private UUID uuid;

    public Seat(){

    }
    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        taken = false;
        price = (row <= 4) ? 10 : 8;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public int getPrice() {
        return price;
    }
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String returnString() {
        return "Row: " + row + "Column: " + column + " - " + taken;
    }
}
