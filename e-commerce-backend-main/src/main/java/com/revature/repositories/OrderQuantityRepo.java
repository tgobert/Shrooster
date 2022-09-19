package com.revature.repositories;

import com.revature.models.OrderQuantityBought;
import com.revature.models.OrderQuantityKey;
import com.revature.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderQuantityRepo extends JpaRepository<OrderQuantityBought, OrderQuantityKey> {


}
