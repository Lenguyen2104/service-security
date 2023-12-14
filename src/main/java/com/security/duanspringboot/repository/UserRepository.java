package com.security.duanspringboot.repository;

import com.security.duanspringboot.entity.UserModel;
import com.security.duanspringboot.entity.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    String TABLE = "user";
    UserModel findByUserRoleModel(UserRoleModel roleModel);
    Optional<UserModel> findByEmail(String email);


}
