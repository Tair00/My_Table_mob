package ru.mvlikhachev.mytablepr.Domain;

public  class BookingItem {
    private String picture;
    private String date;
    private String time;
    private String name;
    private Boolean status;
    private String number;
    private Integer id;

    public BookingItem(boolean status, String picture, String date, String time, String name, String number, Integer user_id) {
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    private Integer user_id;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookingItem(Boolean status,String picture, String date, String time, String name, String number) {
        this.picture = picture;
        this.date = date;
        this.time = time;
        this.name = name;
        this.number = number;
        this.status = status;
    }

    public BookingItem(Boolean status, String picture, String date, String time, String name, Integer id,String number) {
        this.id = id;
        this.picture = picture;
        this.date = date;
        this.time = time;
        this.name = name;
        this.status=status;
        this.number=number;

    }



    public boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public String getPicture() {
        return picture;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}