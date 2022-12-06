package entities.attribute;

import entities.CinemaHall;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaHallAttribute {
    private IntegerProperty hallId;

    private StringProperty hallType;

    private IntegerProperty hallSeatsNumber;

    public CinemaHallAttribute(CinemaHall cinemaHall) {

        this.hallId = new SimpleIntegerProperty(cinemaHall.getHallId());
        this.hallType = new SimpleStringProperty(cinemaHall.getHallType());
        this.hallSeatsNumber = new SimpleIntegerProperty(cinemaHall.getHallSeatsNumber());
    }


}

