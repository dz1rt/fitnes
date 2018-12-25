package com.example.fitnes.fitnes.testProgram;

import java.io.Serializable;

// Модель элемента
public class TestProgramItem implements Serializable {

    private String title;
    private String time;
    private String description;

    public TestProgramItem(String title, String time, String description) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
