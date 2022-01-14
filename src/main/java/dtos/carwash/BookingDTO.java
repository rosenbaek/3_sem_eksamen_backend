package dtos.carwash;

import entities.Bookings;
import entities.Car;
import entities.WashingAssistants;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDTO {
    private Integer id;
    private Date dateTime;
    private Integer duration;
    private List<AssistantDTO> washingAssistants = new ArrayList<>();
    private Float bookingCost = 0f;

    public BookingDTO(Bookings booking) {
        if (booking.getId() != null){
            this.id = booking.getId();
        }
        this.dateTime = booking.getDateTime();
        this.duration = booking.getDuration();
        for (WashingAssistants a: booking.getWashingAssistantsList()) {
            washingAssistants.add(new AssistantDTO(a));
            bookingCost += (a.getRate()/60)*duration;
        }
    }

    public Bookings getEntity(){
        Bookings booking = new Bookings(this.dateTime, this.duration);
        if (this.id != null) {
            booking.setId(this.id);
        }
        if (this.washingAssistants.size()>0) {
            washingAssistants.forEach(w -> booking.addWashingAssistant(w.getEntity()));
        }
        return booking;
    }
    public Float getBookingCost() {
        return bookingCost;
    }
}
