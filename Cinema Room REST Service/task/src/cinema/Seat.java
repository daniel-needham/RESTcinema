package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Seat {
    private int row;
    private int column;
    private boolean taken;
    private int price;

    private UUID uuid;


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

    @JsonIgnore
    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public int getPrice() {
        return price;
    }
    @JsonIgnore
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
