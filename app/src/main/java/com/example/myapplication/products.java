package com.example.myapplication;

public class products {
    private String id ;
    private String title;
    private String description;
    private String price;
    private  String t;
    private int qte ;
    public products() {
    }

    public products(String id ,String title, String description, String price, String t , int qte) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.t = t;
        this.qte = qte;
    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getT() {
        return t;
    }

    public int getQte() {
        return qte;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setT(String t) {
        this.t = t;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}
