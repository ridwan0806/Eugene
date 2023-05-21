package com.example.eugene.Model;

public class Foods {
    private String Name,Image;
    double Price;
    int CategoryId,IsFood,IsDrink,IsEnable;

    public Foods() {
    }

    public Foods(String name, String image, double price, int categoryId, int isFood, int isDrink, int isEnable) {
        Name = name;
        Image = image;
        Price = price;
        CategoryId = categoryId;
        IsFood = isFood;
        IsDrink = isDrink;
        IsEnable = isEnable;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getIsFood() {
        return IsFood;
    }

    public void setIsFood(int isFood) {
        IsFood = isFood;
    }

    public int getIsDrink() {
        return IsDrink;
    }

    public void setIsDrink(int isDrink) {
        IsDrink = isDrink;
    }

    public int getIsEnable() {
        return IsEnable;
    }

    public void setIsEnable(int isEnable) {
        IsEnable = isEnable;
    }
}
