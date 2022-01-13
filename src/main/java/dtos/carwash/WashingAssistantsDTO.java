package dtos.carwash;

import entities.WashingAssistants;

import java.util.ArrayList;
import java.util.List;

public class WashingAssistantsDTO {
    List<AssistantDTO> washingAssistants = new ArrayList<>();

    public WashingAssistantsDTO(List<WashingAssistants> washingAssistantsList) {
        washingAssistantsList.forEach(w -> washingAssistants.add(new AssistantDTO(w)));
    }

    public List<AssistantDTO> getWashingAssistants() {
        return washingAssistants;
    }
}
