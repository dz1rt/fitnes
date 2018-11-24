package com.example.fitnes.fitnes.ListProgram;

import java.io.Serializable;

// Модель элемента
public class ProgramItem implements Serializable {

    private String programName;
    private String description;
    private Integer weight;
    private Integer height;
    private Boolean swtch;

    public ProgramItem(String programName, String description, Integer weight, Integer height, Boolean swtch) {
        this.programName = programName;
        this.description = description;
        this.weight = weight;
        this.height = height;
        this.swtch = swtch;
    }

    public String getProgramName() {
        return programName;
    }

    public String getDescription() {
        return description;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Boolean getSwtch() {
        return swtch;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setSwtch(Boolean swtch) {
        this.swtch = swtch;
    }
}
