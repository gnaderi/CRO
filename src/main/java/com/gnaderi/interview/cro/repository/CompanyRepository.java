package com.gnaderi.interview.cro.repository;

import com.gnaderi.interview.cro.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {
    List<Company> findCompanyByNameIsContaining(String croName);
}
