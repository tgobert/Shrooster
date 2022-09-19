package com.revature.repositories;

import com.revature.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query(value = "insert into addresses (city, country, state, street, zip_code, user_id) values (:city, :country, :state, :street, :zip_code, :user_id) returning *",
            nativeQuery = true)
    Optional<Address> addNewAddress(@Param(value = "city")  String city,
                                    @Param(value = "country")  String country,
                                    @Param(value = "state")  String state,
                                    @Param(value = "street")  String street,
                                    @Param(value = "zip_code")  String zipCode,
                                    @Param(value = "user_id")  int userId);

    @Query(value = "update addresses set city = :city, country = :country, state = :state, street = :street, zip_code = :zip_code where user_id = :user_id returning *",
            nativeQuery = true)
    Optional<Address> updateAddressById(@Param(value = "city")  String city,
                                        @Param(value = "country")  String country,
                                        @Param(value = "state")  String state,
                                        @Param(value = "street")  String street,
                                        @Param(value = "zip_code")  String zipCode,
                                        @Param(value = "user_id")  int userId);

    @Query(value = "select * from addresses where user_id = :user_id",
            nativeQuery = true)
    Optional<Address> getAddressById(@Param(value = "user_id") int userId);

    @Query(value = "delete from addresses where user_id = :user_id returning *",
            nativeQuery = true)
    Optional<Address> deleteAddress(@Param(value = "user_id") int userId);
}
