package com.revature.repositories;

import com.revature.models.Product;
import com.revature.models.User;
import com.revature.models.Wish;
import com.revature.models.WishKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, WishKey> {

    // .save(), .findAll(), .update(), .deleteById() are already created

    Optional<List<Wish>> findByUserId(int userId);

    Optional<Wish> findByUserIdAndProductId(int userId, int productId);

    // This is a custom query for deleting a single wish using the user and product IDs.
    @Modifying
    @Query("DELETE Wish w WHERE w.userId = :userId AND w.productId = :productId")
    int deleteWishByWishKey(@Param("userId") int userId, @Param("productId") int productId);

    // This is a custom query for deleting all wishes using the user ID.
    @Modifying
    @Query("DELETE Wish w WHERE w.userId = :userId")
    int deleteAllWishes(@Param("userId") int userId);

}
