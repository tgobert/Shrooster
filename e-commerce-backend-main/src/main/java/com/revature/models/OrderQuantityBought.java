package com.revature.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@Entity
@Table(name = "Order_Quantities_Bought")
@NoArgsConstructor
@AllArgsConstructor
public class OrderQuantityBought {

    @JsonIgnore
    @EmbeddedId
    OrderQuantityKey id = new OrderQuantityKey();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Transaction.class)
    @MapsId("transactionId")
    @JoinColumn(name = "transaction_id")
    private Transaction transactionId;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, targetEntity = Product.class)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product productId;

    private int quantity;

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", quantity='" + getQuantity() + "'" +
                "}";
    }

}
