package com.hch.nutrients_info_platform.model;

public class Model {
    private String name;
    private String price;
    private String image;
    private String ingredient;
    private String how;
    private String precautions;

    public Model(){}

    //이름, 가격, 이미지
    public Model(String name, String price, String image){
        this.name = name;
        this.price = price;
        this.image = image;
    }

    //이름, 가격, 이미지, 성분, 복용방법, 주의사항
    public Model(String name, String price, String image, String ingredient, String how, String precautions){
        this.name = name;
        this.price = price;
        this.image = image;
        this.ingredient = ingredient;
        this.how = how;
        this.precautions = precautions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String Precautions) {
        this.precautions = Precautions;
    }
}
