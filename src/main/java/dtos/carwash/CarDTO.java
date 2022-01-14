package dtos.carwash;

import entities.Bookings;
import entities.Car;
import entities.WashingAssistants;

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
    private Float totalCost = 0f;

    public CarDTO(Car car) {
        this.registration = car.getRegistration();
        this.brand = car.getBrand();
        this.make = car.getMake();
        this.year = car.getYear();
        for (Bookings b: car.getBookingsList()) {
            BookingDTO dto = new BookingDTO(b);
            bookings.add(dto);
            totalCost += dto.getBookingCost();
        }
    }
}
