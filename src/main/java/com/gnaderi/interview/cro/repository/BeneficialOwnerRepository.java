package com.gnaderi.interview.cro.repository;

import com.gnaderi.interview.cro.entity.BeneficialOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BeneficialOwnerRepository extends CrudRepository<BeneficialOwner, Integer> {
    void deleteBeneficialOwnerByCompanyIdAndStakeholderId(Integer companyId, Integer stakeholderId);
}
