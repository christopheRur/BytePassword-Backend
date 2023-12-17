package com.codelab.BytePassword.Repository;

import com.codelab.BytePassword.model.BytePwd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BytePwdRepository extends JpaRepository<BytePwd, Long> {

    Optional<BytePwd> findByEmail(String email);

    Optional<BytePwd> findByHint(String hint);

    Optional<BytePwd> deleteByEmail(String email);
    Optional<BytePwd> findIdByEmail(String email);


}
