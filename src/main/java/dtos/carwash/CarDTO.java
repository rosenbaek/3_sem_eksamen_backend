package dtos.carwash;

import entities.Car;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class CarDTO {
    private String registration;
    private String brand;
    private String make;
    private Integer year;
    private List<BookingDTO> bookings = new ArrayList<>();

    public CarDTO(Car car) {
        this.registration = car.getRegistration();
        this.brand = car.getBrand();
        this.make = car.getMake();
        this.year = car.getYear();
        car.getBookingsList().forEach(b -> this.bookings.add(new BookingDTO(b)));
    }
}
