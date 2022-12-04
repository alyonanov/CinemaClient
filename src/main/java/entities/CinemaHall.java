package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaHall {

    private int hallId;
    private String hallType;

    private int hallSeatsNumber;

    @Override
    public String toString() {
        return hallType;
    }
}
