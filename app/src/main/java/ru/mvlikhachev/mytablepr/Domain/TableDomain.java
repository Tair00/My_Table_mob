package ru.mvlikhachev.mytablepr.Domain;

import java.io.Serializable;

public class TableDomain implements Serializable {
    private int id;

    private String title;
    private String seat;
    private String pic;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String desc) {
        this.seat = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public TableDomain(int id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.seat = desc;
        this.pic = pic;


    }
}
