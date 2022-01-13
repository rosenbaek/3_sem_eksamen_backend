package dtos.carwash;

import entities.Bookings;

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

    public BookingDTO(Bookings booking) {
        if (booking.getId() != null){
            this.id = booking.getId();
        }
        this.dateTime = booking.getDateTime();
        this.duration = booking.getDuration();
        booking.getWashingAssistantsList().forEach(a -> washingAssistants.add(new AssistantDTO(a)));
    }
}
