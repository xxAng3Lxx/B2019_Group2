package com.b2019.studentspadmysql.Model;

public class DataModel {
    private int id;
    private String note_title, note_sub, note_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return note_title;
    }

    public void setNama(String note_title) { this.note_title = note_title;
    }

    public String getAlamat() {
        return note_sub;
    }

    public void setAlamat(String note_sub) {
        this.note_sub = note_sub;
    }

    public String getTelepon() {
        return note_text;
    }

    public void setTelepon(String telepon) {
        this.note_text = telepon;
    }
}