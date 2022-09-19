package com.revature.services;

import com.revature.exceptions.WishNotFoundException;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.models.Wish;
import com.revature.models.WishKey;
import com.revature.repositories.WishRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private WishRepository wishRepo;

    @Autowired
    public WishService(WishRepository wishRepo) {
        this.wishRepo = wishRepo;
    }

    // Service for getting all products on a wish list with a user ID.
    @Transactional(readOnly = true)
    public List<Wish> getWishesByUserId(int userId) {
        log.info("Found Wishlist for User: " + userId );
        // User ID is passed to the repo layer and returns a list of wishes, or else returns null if
        // no user exists with that ID, or no wishes exist for that user.
        return wishRepo.findByUserId(userId).orElse(null);
    }

    // This service has no use in our current application, but is kept around in case future iterations
    // need it.
    @Transactional(readOnly = true)
    public Wish getWishByUserIdAndProductId(int userId, int productId) {
        log.info("Found wish with user id {} and product id {}.", userId, productId);
        return wishRepo.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new WishNotFoundException("Wish with user id " + userId +
                        " and product id " + productId + " not found."));
    }

    // This is the service for creating a new wish list item using a user, product, and wishkey
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Wish addWish(User user, Product product) {
        // A full user Object and full product Object are passed in and their IDs are used to create the wishkey.
        WishKey wishKey = new WishKey(user.getUserId(), product.getProdId());
        // A new wish list item is created using the wishkey, full user Object and full product Object.
        Wish newWish = new Wish(wishKey, user, product);
        log.info("Added a new Wish for User: " + user.getUserId());
        // The new wish list item is passed to the repo level.
        return wishRepo.save(newWish);
    }

    // Service for deleting a single user wish.
    @Transactional
    public void deleteWish(User user, Product product) {
        // A full user and product object are passed in, and we send their IDs to the repo level.
        wishRepo.deleteWishByWishKey(user.getUserId(), product.getProdId());
        log.info("Deleted Wish for User: " + user.getUserId());
    }

    // Service for deleting all user wishes.
    @Transactional
    public void deleteAllWishes(User user) {
        // A full user Object is passed in and we then pass the user ID to the repo level.
        wishRepo.deleteAllWishes(user.getUserId());
        log.info("Deleted all Wishes for User: " + user.getUserId());
    }

}
