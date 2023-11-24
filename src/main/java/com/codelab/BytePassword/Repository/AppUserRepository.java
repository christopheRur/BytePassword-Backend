package com.codelab.BytePassword.Repository;

import com.codelab.BytePassword.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findUserByEmail(String email);
    Optional<AppUser> findUserByHint(String hint);
    AppUser findPasswordByEmail(String email);
}
