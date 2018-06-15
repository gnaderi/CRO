package com.gnaderi.interview.cro.repository;

import com.gnaderi.interview.cro.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u from User  u where u.username=:username")
    User findByUsername(@Param("username") String username);
}
