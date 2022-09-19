package com.revature.dtos;


public class ProductCreateDTO {

    private int prodQuantity;
    private double prodPrice;
    private String prodDesc;
    private String prodImage;
    private String prodName;



    //Constructor, Getters and Setters

    public ProductCreateDTO() {}

    public ProductCreateDTO(int prodQuantity, double prodPrice, String prodDesc, String prodImage, String prodName) {
        this.prodQuantity = prodQuantity;
        this.prodPrice = prodPrice;
        this.prodDesc = prodDesc;
        this.prodImage = prodImage;
        this.prodName = prodName;
    }

    public int getProdQuantity() {return prodQuantity;}

    public void setProdQuantity(int prodQuantity) {this.prodQuantity = prodQuantity;}

    public double getProdPrice() {return prodPrice;}

    public void setProdPrice(double prodPrice) {this.prodPrice = prodPrice;}

    public String getProdDesc() {return prodDesc;}

    public void setProdDesc(String prodDesc) {this.prodDesc = prodDesc;}

    public String getProdImage() {return prodImage;}

    public void setProdImage(String prodImage) {this.prodImage = prodImage;}

    public String getProdName() {return prodName;}

    public void setProdName(String prodName) {this.prodName = prodName;}
}
