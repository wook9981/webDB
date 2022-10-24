package com.webdb.api.dto;

public class PaymentDTO {
    long sales;
    long vat;
    long amount;
    
    public PaymentDTO(long sales, long vat, long amount) {
        super();
        this.sales = sales;
        this.vat = vat;
        this.amount = amount;
    }

    public long getSales() {
        return sales;
    }
    
    public void setSales(long sales) {
        this.sales = sales;
    }
    
    public long getVat() {
        return vat;
    }
    
    public void setVat(long vat) {
        this.vat = vat;
    }
    
    public long getAmount() {
        return amount;
    }
    
    public void setAmount(long amount) {
        this.amount = amount;
    }
}
