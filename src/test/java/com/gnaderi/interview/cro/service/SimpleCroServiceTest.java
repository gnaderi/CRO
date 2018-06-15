package com.gnaderi.interview.cro.service;

import com.gnaderi.interview.cro.DBInitializerStartupRunner;
import com.gnaderi.interview.cro.TestConfiguration;
import com.gnaderi.interview.cro.entity.BeneficialOwner;
import com.gnaderi.interview.cro.entity.Company;
import com.gnaderi.interview.cro.entity.Stakeholder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class}, loader = AnnotationConfigContextLoader.class)
public class SimpleCroServiceTest {
    @Autowired
    private CroService croService;
    @Autowired
    private DBInitializerStartupRunner dbInitializerStartupRunner;
    private static boolean isDbInit = false;

    @Before
    public void setUp() throws Exception {
        if (!isDbInit) {
            dbInitializerStartupRunner.run();
            isDbInit = true;
        }
    }

    @Test
    public void registerCompany() {
        Company company = croService.registerCompany("apple", "myAddress", "Cork", "Ireland", "info@appl.ie", "02144342");
        assertEquals(company.getCity(), "Cork");
        assertEquals(company.getEmail(), "info@appl.ie");
        croService.delete(company);
    }

    @Test
    public void updateCompany() {
        Company naderihub = croService.findCompanyById(1);
        naderihub.setName("NaderiHub Ltd");
        Company updateNHL = croService.updateCompany(naderihub, naderihub.getName(), naderihub.getAddress(), naderihub.getCity(), naderihub.getCountry(), naderihub.getEmail(), naderihub.getPhoneNumber());
        assertEquals(updateNHL.getName(), "NaderiHub Ltd");
        assertEquals(updateNHL.getCity(), "dublin");
        croService.updateCompany(naderihub, "naderihub", naderihub.getAddress(), naderihub.getCity(), naderihub.getCountry(), naderihub.getEmail(), naderihub.getPhoneNumber());

    }

    @Test
    public void registerBeneficialOwner() {
        Company google = croService.findCompanyById(2);
        Stakeholder newStakeholder = croService.findStakeholderById(1);
        BeneficialOwner beneficialOwner = croService.registerBeneficialOwner(google, newStakeholder);
        google = croService.findCompanyById(2);
        assertEquals(4, google.getStakeholders().size());
        croService.deleteBeneficialOwner(google, newStakeholder);
    }

    @Test
    public void createStakeholder() {
        Stakeholder stakeholder = croService.createStakeholder("Guinness", "Morphy");
        assertEquals("Guinness", stakeholder.getFirstName());
        assertEquals("Morphy", stakeholder.getLastName());
        croService.delete(stakeholder);
    }

    @Test
    public void findAllCompanies() {
        List<Company> allCompanies = croService.findAllCompanies();
        assertEquals(3, allCompanies.size());
    }

    @Test
    public void findCompanyByName() {
        List<Company> naderihub = croService.findCompanyByName("naderihub");
        assertEquals(1, naderihub.size());
        assertEquals("dublin", naderihub.get(0).getCity());
        assertEquals(2, naderihub.get(0).getStakeholders().size());

    }

    @Test
    public void findCompanyById() {
        Company naderihub = croService.findCompanyById(1);
        assertEquals("dublin", naderihub.getCity());
        assertEquals("naderihub", naderihub.getName());
    }

    @Test
    public void findAllStakeholders() {
        List<Stakeholder> allStakeholders = croService.findAllStakeholders();
        assertEquals(8, allStakeholders.size());


    }

    @Test
    public void findStakeholderByName() {
        List<Stakeholder> stakeholderByName = croService.findStakeholderByName("ghodrat");
        assertEquals(1, stakeholderByName.size());
        assertEquals("ghodrat", stakeholderByName.get(0).getFirstName());
        assertEquals("naderi", stakeholderByName.get(0).getLastName());
    }

    @Test
    public void findStakeholderById() {
        Stakeholder stakeholderById = croService.findStakeholderById(1);
        assertNotNull(stakeholderById);
        assertEquals("ghodrat", stakeholderById.getFirstName());
        assertEquals("naderi", stakeholderById.getLastName());
    }

    @Test
    public void findCompanyStakeholders() {
        Company google = croService.findCompanyById(2);
        assertEquals(3, google.getStakeholders().size());
        assertNotNull(google.getStakeholders().stream().filter(e -> e.getLastName().equalsIgnoreCase("page")).findFirst());
    }


    @Test
    public void updateStakeholder() {
        Stakeholder stakeholderByName = croService.findStakeholderByName("ghodrat").get(0);
        croService.updateStakeholder(stakeholderByName, "Ghodrat", "Naderi");
        assertEquals("Ghodrat", stakeholderByName.getFirstName());
        assertEquals("Naderi", stakeholderByName.getLastName());
        croService.updateStakeholder(stakeholderByName, "ghodrat", "naderi");
    }


    @Test
    public void deleteBeneficialOwner() {
        Company companyById = croService.findCompanyById(3);
        assertEquals(3, companyById.getStakeholders().size());
        Stakeholder stakeholderById = croService.findStakeholderById(8);
        assertEquals(1, stakeholderById.getCompanies().size());
        croService.deleteBeneficialOwner(companyById, stakeholderById);
        companyById = croService.findCompanyById(3);
        assertEquals(2, companyById.getStakeholders().size());
        stakeholderById = croService.findStakeholderById(8);
        assertEquals(0, stakeholderById.getCompanies().size());
    }
}