package cinema;

public class View {
    static class Public {}

    static class Seats extends Public{}

    static class Transactions extends Public{}
    static class Purchase extends Transactions{}
    static class Return extends Transactions{}
}
