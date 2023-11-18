package com.codelab.BytePassword.Repository;

import com.codelab.BytePassword.model.BytePwd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BytePwdRepository extends JpaRepository<BytePwd,Long> {
}
