package dtos.carwash;

import entities.Bookings;
import entities.Car;
import entities.WashingAssistants;

import java.util.ArrayList;
import java.util.List;

public class CarsDTO {
    List<CarDTO> cars = new ArrayList<>();

    public CarsDTO(List<Car> cars) {
        cars.forEach(c -> this.cars.add(new CarDTO(c)));
    }

    public List<CarDTO> getWashingAssistants() {
        return cars;
    }
}
