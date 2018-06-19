package com.gnaderi.interview.cro.service;

import com.gnaderi.interview.cro.RecordNotFoundException;
import com.gnaderi.interview.cro.entity.BeneficialOwner;
import com.gnaderi.interview.cro.entity.Company;
import com.gnaderi.interview.cro.entity.Stakeholder;
import com.gnaderi.interview.cro.repository.BeneficialOwnerRepository;
import com.gnaderi.interview.cro.repository.CompanyRepository;
import com.gnaderi.interview.cro.repository.StakeholderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Qualifier("SimpleCroService")
public class SimpleCroService implements CroService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCroService.class);
    @Autowired
    private CompanyRepository companyRepo;
    @Autowired
    private BeneficialOwnerRepository beneficialOwnerRepo;
    @Autowired
    private StakeholderRepository stakeholderRepo;

    @Override
    public Company registerCompany(String name, String address, String city, String country, String email, String phoneNumber) {
        Company company = new Company(name, address, city, country, email, phoneNumber);
        return companyRepo.save(company);
    }

    @Override
    public Company registerCompany(String name, String address, String city, String country, String email, String phoneNumber, List<Stakeholder> stakeholders) {
        Company company = registerCompany(name, address, city, country, email, phoneNumber);
        stakeholders.forEach(e -> registerBeneficialOwner(company, e));
        company.setStakeholders(stakeholders);
        return company;
    }

    @Override
    public Company updateCompany(Company company, String name, String address, String city, String country, String email, String phoneNumber) throws RecordNotFoundException {
        company.setName(name);
        company.setAddress(address);
        company.setCity(city);
        company.setCountry(country);
        company.setEmail(email);
        company.setPhoneNumber(phoneNumber);
        return companyRepo.save(company);
    }

    @Override
    public BeneficialOwner registerBeneficialOwner(Company company, Stakeholder stakeholder) {
        return beneficialOwnerRepo.save(new BeneficialOwner(company, stakeholder));
    }

    @Override
    public Stakeholder createStakeholder(String firstName, String lastName) {
        Stakeholder stakeholder = new Stakeholder(firstName, lastName);
        return stakeholderRepo.save(stakeholder);
    }

    @Override
    public Stakeholder updateStakeholder(Stakeholder stakeholder, String firstName, String lastName) {
        stakeholder.setFirstName(firstName);
        stakeholder.setLastName(lastName);
        return stakeholderRepo.save(stakeholder);
    }

    @Override
    public List<Company> findAllCompanies() {
        return (List<Company>) companyRepo.findAll();
    }

    @Override
    public List<Company> findCompanyByName(String companyName) {
        return companyRepo.findCompanyByNameIsContaining(companyName);
    }

    @Override
    public Company findCompanyById(Integer croId) {
        return companyRepo.findById(croId).orElseThrow(() -> new RecordNotFoundException("Invalid company Id#" + croId));
    }

    @Override
    public List<Stakeholder> findAllStakeholders() {
        return (List<Stakeholder>) stakeholderRepo.findAll();
    }

    @Override
    public List<Stakeholder> findStakeholderByName(String stakeholderName) {
        return stakeholderRepo.findStakeholdersByFirstNameIsLikeOrLastNameIsLike(stakeholderName, stakeholderName);
    }

    @Override
    public List<Stakeholder> findStakeholderByName(String firstName, String lastName) {
        return stakeholderRepo.findStakeholdersByFirstNameIsLikeAndLastNameIsLike(firstName, lastName);
    }

    @Override
    public Stakeholder findStakeholderById(Integer stakeholderId) {
        return stakeholderRepo.findById(stakeholderId).orElseThrow(() -> new RecordNotFoundException("Invalid stakeholder Id#" + stakeholderId));
    }

    @Override
    public void delete(Stakeholder stakeholder) {
        stakeholderRepo.delete(stakeholder);
    }

    @Override
    public void delete(Company company) {
        companyRepo.delete(company);
    }

    @Override
    public void delete(BeneficialOwner beneficialOwner) {
        beneficialOwnerRepo.delete(beneficialOwner);
    }

    @Override
    public void deleteBeneficialOwner(Company company, Stakeholder stakeholder) {
        beneficialOwnerRepo.deleteBeneficialOwnerByCompanyIdAndStakeholderId(company.getId(), stakeholder.getId());
    }
}