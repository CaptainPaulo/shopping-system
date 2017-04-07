package com.jweb.beans;

/**
 * Created by adenis_e on 17-4-6.
 */
public class Pannier {
    private long id;
    private long member;
    private long product;
    private long numberOfProduct;
    private boolean buy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMember() {
        return member;
    }

    public void setMember(long member) {
        this.member = member;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(long numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }
}
