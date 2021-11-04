package com.BE.HelpDIANA.test;

import java.sql.Date;

public class DiagnoseForm {
    private String email;
    private String name;

    private java.sql.Date date;

    public DiagnoseForm(String email, String name, Date date) {
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

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
