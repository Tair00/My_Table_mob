package ru.mvlikhachev.mytablepr.Domain;

import java.io.Serializable;

public class TableDomain implements Serializable {
    private int id;

    private String title;
    private String desc;
    private String pic;
    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public TableDomain(int id, String title, String desc, String pic,Integer price) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.pic = pic;
        this.price=price;

    }
}
