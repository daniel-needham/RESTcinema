package cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    private final static int row = 9;
    private final static int column = 9;

    @Bean
    public SeatPlan cinemaOne() {
        return new SeatPlan(row,column);
    }


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
