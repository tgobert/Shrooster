package com.revature.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderQuantityKey implements Serializable {

    @Column(name = "tran_id")
    private int transactionId;

    @Column(name = "product_id")
    private int productId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderQuantityKey that = (OrderQuantityKey) o;
        return transactionId == that.transactionId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, productId);
    }

}
