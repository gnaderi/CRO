package com.gnaderi.interview.cro;

import com.gnaderi.interview.cro.service.CroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInitializerStartupRunner implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(DBInitializerStartupRunner.class);
    @Autowired
    private CroService croService;

    @Override
    public void run(String... args) throws Exception {
        croService.registerCompany("naderihub", "behan square", "dublin", "ireland", "nhl@gmail.com", "0174320"); //1
        croService.registerCompany("google", "silicon vally", "mountain view", "usa", "gooogle@goolge.com", "+1855 855 9772"); //2
        croService.registerCompany("mastercard.ie", "mountain view, central park", "dublin", "ireland", "info@mastercard.ie", "012178600"); //3

        croService.createStakeholder("ghodrat", "naderi"); //1
        croService.createStakeholder("naz", "mni"); //2
        croService.createStakeholder("richard", "haythornthwaite"); //3
        croService.createStakeholder("silvio", "barzi"); //4
        croService.createStakeholder("marc", "octavio"); //5
        croService.createStakeholder("larry", "page"); //6
        croService.createStakeholder("eric", "schmidt"); //7
        croService.createStakeholder("sergey", "brin"); //8

        croService.registerBeneficialOwner(croService.findCompanyById(1), croService.findStakeholderById(1));
        croService.registerBeneficialOwner(croService.findCompanyById(1), croService.findStakeholderById(2));
        croService.registerBeneficialOwner(croService.findCompanyById(2), croService.findStakeholderById(3));
        croService.registerBeneficialOwner(croService.findCompanyById(2), croService.findStakeholderById(4));
        croService.registerBeneficialOwner(croService.findCompanyById(2), croService.findStakeholderById(5));
        croService.registerBeneficialOwner(croService.findCompanyById(3), croService.findStakeholderById(6));
        croService.registerBeneficialOwner(croService.findCompanyById(3), croService.findStakeholderById(7));
        croService.registerBeneficialOwner(croService.findCompanyById(3), croService.findStakeholderById(8));

    }
}
