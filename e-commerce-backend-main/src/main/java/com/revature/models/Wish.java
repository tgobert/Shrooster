package com.revature.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "wishes")
@NoArgsConstructor
@AllArgsConstructor
public class Wish {

    @EmbeddedId
    WishKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;

    @Column(name = "product_id", insertable = false, updatable = false)
    private int productId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishId;

    public Wish(WishKey wishKey, User user, Product product) {
        this.id = wishKey;
        this.user = user;
        this.product = product;
    }

    // private String groupId;
    // private String groupName;
}
