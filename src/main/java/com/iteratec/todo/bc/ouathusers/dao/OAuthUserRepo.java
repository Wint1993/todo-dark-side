package com.iteratec.todo.bc.ouathusers.dao;

import com.iteratec.todo.bc.ouathusers.dao.entity.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthUserRepo extends JpaRepository<OAuthUser, Long> {
    OAuthUser findOneByUsername(String username);
}
