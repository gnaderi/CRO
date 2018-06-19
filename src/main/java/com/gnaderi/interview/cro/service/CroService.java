package com.gnaderi.interview.cro.service;


import com.gnaderi.interview.cro.entity.BeneficialOwner;
import com.gnaderi.interview.cro.entity.Company;
import com.gnaderi.interview.cro.entity.Stakeholder;

import java.util.List;

public interface CroService {

    Company registerCompany(String name, String address, String city, String country, String email, String phoneNumber);

    Company registerCompany(String name, String address, String city, String country, String email, String phoneNumber, List<Stakeholder> stakeholders);

    Company updateCompany(Company company, String name, String address, String city, String country, String email, String phoneNumber);

    BeneficialOwner registerBeneficialOwner(Company company, Stakeholder stakeholder);

    Stakeholder createStakeholder(String firstName, String lastName);

    Stakeholder updateStakeholder(Stakeholder stakeholder, String firstName, String lastName);

    List<Company> findAllCompanies();

    List<Company> findCompanyByName(String companyName);

    Company findCompanyById(Integer croId);

    List<Stakeholder> findAllStakeholders();

    List<Stakeholder> findStakeholderByName(String stakeholderName);

    List<Stakeholder> findStakeholderByName(String firstName, String lastName);

    Stakeholder findStakeholderById(Integer stakeholderId);

    void delete(Stakeholder stakeholder);

    void delete(Company company);

    void delete(BeneficialOwner beneficialOwner);

    void deleteBeneficialOwner(Company company, Stakeholder stakeholder);

}