package com.gnaderi.interview.cro.repository;

import com.gnaderi.interview.cro.entity.UserAccess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserAccessRepository extends CrudRepository<UserAccess, Long> {
}
