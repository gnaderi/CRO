package com.gnaderi.interview.cro.repository;

import com.gnaderi.interview.cro.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    @Query(value = "SELECT r.* FROM USER_ACCESS  ua JOIN Role r ON ua.ROLE_ID=r.id WHERE ua.USER_ID=:userId", nativeQuery = true)
    List<Role> findUserRolesById(@Param("userId") Long userId);
}
