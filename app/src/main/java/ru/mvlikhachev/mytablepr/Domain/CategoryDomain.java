package ru.mvlikhachev.mytablepr.Domain;

public class CategoryDomain {
    private String title;
    private String pic;
    private int id;

    public CategoryDomain(String title,String pic,int id){
        this.title = title;
        this.pic = pic;
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title ){
        this.title =title;


    }
    public String getPic(){
        return  pic;
    }
    public void setPic(){

        this.pic = pic;
    }
    public int getId(){
        return  id;
    }
    public void setId(){

        this.id = id;
    }
}
