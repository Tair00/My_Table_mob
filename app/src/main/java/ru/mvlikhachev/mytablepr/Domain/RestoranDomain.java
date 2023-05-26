package ru.mvlikhachev.mytablepr.Domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "cart_items")
public class RestoranDomain implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String pic;
    private String description;
    private Float price;
    private Float star;
    private int table;
    private int cat_id;


    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getStar() {
        return star;
    }

    public void setStar(Float star) {
        this.star = star;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RestoranDomain(int id,String title, String pic, String description, Float price, Float star, int table,int cat_id) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.price = price;
        this.star = star;
        this.table = table;
        this.id=id;
        this.cat_id=cat_id;
    }
}