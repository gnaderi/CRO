package com.gnaderi.interview.cro.repository;

import com.gnaderi.interview.cro.entity.Stakeholder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface StakeholderRepository extends CrudRepository<Stakeholder, Integer> {
    List<Stakeholder> findStakeholdersByFirstNameIsLikeOrLastNameIsLike(String firstName, String lastName);

    List<Stakeholder> findStakeholdersByFirstNameIsLikeAndLastNameIsLike(String firstName, String lastName);
}
