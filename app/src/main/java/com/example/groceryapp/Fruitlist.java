package com.example.groceryapp;

public class Fruitlist {
    String fruitname;
    int price;
    int Quantity;
    int Total;

    public Fruitlist(String fruitname,int price,int Quantity) {
        this.fruitname = fruitname;
        this.price = price;
        this.Quantity = Quantity;
        this.Total = price * Quantity;
    }

    public String getFruitname() {
        return fruitname;
    }

    public void setFruitname(String fruitname) {
        this.fruitname = fruitname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {

        this.price = price;

    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = this.price * this.Quantity ;
    }
}
