package com.diana.form;

import java.text.SimpleDateFormat;

public class DiagnoseForm {
    private String email;
    private String name;
    private SimpleDateFormat date;

    public DiagnoseForm(String email, String name, SimpleDateFormat date) {
        this.email = email;
        this.name = name;
        this.date = date;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleDateFormat getDate() {
        return this.date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

}
