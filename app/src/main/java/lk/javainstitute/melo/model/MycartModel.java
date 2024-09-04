package lk.javainstitute.melo.model;

import java.io.Serializable;

public class MycartModel implements Serializable {

    String currentDate;
    String currentTime;
    String productName;
    String productPrice;
    String totalQunatity;
    int totalPrice;

    public MycartModel() {

    }

    public MycartModel(String currentDate, String currentTime, String productName, String productPrice, String totalQunatity, int totalPrice) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQunatity = totalQunatity;
        this.totalPrice = totalPrice;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalQunatity() {
        return totalQunatity;
    }

    public void setTotalQunatity(String totalQunatity) {
        this.totalQunatity = totalQunatity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
