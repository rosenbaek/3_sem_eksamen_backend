package dtos;

import dtos.carwash.AssistantDTO;
import entities.Bookings;

import java.util.Date;
import java.util.List;

public class AddBookingDTO {
    private Integer id;
    private String carReg;
    private Date dateTime;
    private Integer duration;
    private List<AssistantDTO> washingAssistants;

    public Bookings getEntity() {
        Bookings bookings = new Bookings(this.dateTime, this.duration);
        washingAssistants.forEach(assistantDTO -> bookings.addWashingAssistant(assistantDTO.getEntity()));
        if (this.id != null) {
            bookings.setId(this.id);
        }
        return bookings;
    }

    public String getCarReg() {
        return carReg;
    }
}
