package com.revature.repositories;

import com.revature.models.Address;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

//    Optional<User> findByUserEmailAndUserPassword(String email, String password);

    @Query(value = "select * from users where (user_email like :user_email) and (user_password like :user_password)",
            nativeQuery = true)
    Optional<User> findByUserEmailAndUserPassword(@Param(value = "user_email") String userEmail,
                                                  @Param(value = "user_password") String userPassword);



    @Query(value = "select * from users where user_email like :user_email",
            nativeQuery = true)
    Optional<User> getByEmail(@Param(value = "user_email") String userEmail);

    @Query(value = "select * from users where (user_email like :user_email) and (user_id = :user_id)",
            nativeQuery = true)
    Optional<User> getByEmailAndId(@Param(value = "user_email") String userEmail,
                                   @Param(value = "user_id") int userId);

    @Query(value = "insert into users (first_name, last_name, token_id, user_email, user_password) values (:first_name, :last_name, :token_id, :user_email, :user_password) Returning *",
            nativeQuery = true)
    Optional<User> addNewUser(@Param(value = "first_name")  String firstName,
                              @Param(value = "last_name")  String lastName,
                              @Param(value = "token_id")  String newToken,
                              @Param(value = "user_email")  String userEmail,
                              @Param(value = "user_password")  String userPassword);

    @Query(value = "update users set token_id = :empty_token where (user_id = :user_id) and (user_email like :user_email) returning *",
            nativeQuery = true)
    Optional<User> removeToken(@Param(value = "user_email") String userEmail,
                               @Param(value = "user_id") int userId,
                               @Param(value = "empty_token") String emptyToken);

    @Query(value = "update users set token_id = :new_token where (user_id = :user_id) and (user_email like :user_email) returning *",
            nativeQuery = true)
    Optional<User> addToken(@Param(value = "user_email") String userEmail,
                            @Param(value = "user_id") int userId,
                            @Param(value = "new_token") String newToken);

    @Query(value = "update users set user_password = :new_password where (user_id = :user_id) and (user_email like :user_email) returning *",
            nativeQuery = true)
    Optional<User> setNewPassword(@Param(value = "user_email") String userEmail,
                                  @Param(value = "user_id") int userId,
                                  @Param(value = "new_password") String newPassword);

    @Query(value = "select * from users where (user_email like :user_email) and (user_id = :user_id) and (token_id like :current_token)",
            nativeQuery = true)
    Optional<User> validateSession(@Param(value = "user_email") String userEmail,
                                   @Param(value = "user_id") int userId,
                                   @Param(value = "current_token") String currentToken);

}