package com.revature.repositories;

import com.revature.models.Address;
import com.revature.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository <Payment, Integer>{
    @Query(value = "insert into payments (cc_number, exp_period, svc_code, zip_code, user_id) values (:cc_number, :exp_period, :svc_code, :zip_code, :user_id) returning *",
            nativeQuery = true)
    Optional<Payment> addNewPayment(@Param(value = "cc_number") String ccNumber,
                                    @Param(value = "exp_period") String expPeriod,
                                    @Param(value = "svc_code") String svcCode,
                                    @Param(value = "zip_code") String zipCode,
                                    @Param(value = "user_id") int userId);

    @Query(value = "update payments set cc_number = :cc_number, exp_period = :exp_period, svc_code = :svc_code, zip_code = :zip_code where user_id = :user_id returning *",
            nativeQuery = true)
    Optional<Payment> updatePaymentById(@Param(value = "cc_number") String ccNumber,
                                        @Param(value = "exp_period") String expPeriod,
                                        @Param(value = "svc_code") String svcCode,
                                        @Param(value = "zip_code") String zipCode,
                                        @Param(value = "user_id") int userId);

    @Query(value = "select * from payments where user_id = :user_id",
            nativeQuery = true)
    Optional<Payment> getPaymentById(@Param(value = "user_id") int userId);

    @Query(value = "delete from payments where user_id = :user_id returning *",
            nativeQuery = true)
    Optional<Payment> deletePayment(@Param(value = "user_id") int userId);
}
