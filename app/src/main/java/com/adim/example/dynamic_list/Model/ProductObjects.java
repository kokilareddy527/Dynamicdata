package com.adim.example.dynamic_list.Model;

/**
 * Created by Adim on 2/25/2019.
 */

public class ProductObjects {

    private String productname;
    private String productimg;
    private String pdt_des;


    public ProductObjects() {

    }

    public ProductObjects(String productname, String productimg, String pdt_des) {


        this.productname = productname;
        this.productimg = productimg;
        this.pdt_des = pdt_des;

    }


    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }


    public String getPdt_des() {
        return pdt_des;
    }

    public void setPdt_des(String pdt_des) {
        this.pdt_des = pdt_des;

    }

    public String getProductname() {
        return productname;
    }

    public String getProductimg() {
        return productimg;
    }
}
