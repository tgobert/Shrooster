package com.revature.repositories;

import com.revature.models.Address;
import com.revature.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "select * from images where user_id = :user_id",
            nativeQuery = true)
    Optional<Image> getImageById(@Param(value = "user_id") int userId);

    @Query(value = "delete from images where user_id = :user_id returning *",
            nativeQuery = true)
    Optional<Image> deleteImageById(@Param(value = "user_id") int userId);

    @Query(value = "update images set pic_byte = :pic_byte, pic_name = :pic_name, pic_type = :pic_type where user_id = :user_id returning *",
            nativeQuery = true)
    Optional<Image> updateImageById(@Param(value = "pic_byte")  byte[] picByte,
                                    @Param(value = "pic_name")  String picName,
                                    @Param(value = "pic_type")  String picType,
                                    @Param(value = "user_id")  int userId);

    @Query(value = "insert into images (pic_byte, pic_name, pic_type, user_id) values (:pic_byte, :pic_name, :pic_type, :user_id) returning *",
            nativeQuery = true)
    Optional<Image> addNewImage(@Param(value = "pic_byte")  byte[] picByte,
                                @Param(value = "pic_name")  String picName,
                                @Param(value = "pic_type")  String picType,
                                @Param(value = "user_id")  int userId);
}
