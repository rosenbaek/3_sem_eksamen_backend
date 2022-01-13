package dtos.carwash;

import entities.User;
import entities.WashingAssistants;

public class AssistantDTO {
    private Integer id;
    private String name;
    private String primaryLanguage;
    private float rate;
    private float experience;

    public AssistantDTO(WashingAssistants washingAssistant) {
        if(washingAssistant.getId() != null){
            this.id = washingAssistant.getId();
        }
        this.name = washingAssistant.getName();
        this.primaryLanguage = washingAssistant.getPrimaryLanguage();
        this.rate = washingAssistant.getRate();
        this.experience = washingAssistant.getExperience();
    }

    public WashingAssistants getEntity(){
        WashingAssistants washingAssistant = new WashingAssistants(this.name, this.primaryLanguage,this.experience, this.rate);
        if (this.id != null) {
            washingAssistant.setId(id);
        }
        return washingAssistant;
    }
}
