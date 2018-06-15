package com.gnaderi.interview.cro.controller;

import com.gnaderi.interview.cro.entity.Company;
import com.gnaderi.interview.cro.entity.Stakeholder;
import com.gnaderi.interview.cro.inbound.CompanyRegistrationRequest;
import com.gnaderi.interview.cro.outbound.CompanyDto;
import com.gnaderi.interview.cro.service.CroService;
import com.gnaderi.interview.cro.util.DtoConverter;
import com.gnaderi.interview.cro.util.RequestValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Company Registrations Office REST API Interface", tags = "Company Registrations Office REST API Interface", description = "Company Registrations Office REST API Interface")
@PropertySource(value = "classpath:application.properties")
@RestController
@RequestMapping(value = "/cro")
public class CompanyRegOfficeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyRegOfficeController.class);

    @Qualifier("SimpleCroService")
    @Autowired
    private CroService service;

    @Autowired
    private RequestValidator requestValidator;

    @Autowired
    private DtoConverter dtoConverter;

    @ApiOperation(value = "Get a list of all companies stored in application.", notes = "Retrieve all companies details.", produces = MediaType.TEXT_PLAIN_VALUE, response = CompanyDto.class, responseContainer = "List", httpMethod = "GET")
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity fetchAllCompanies() {
        try {
            LOGGER.info("Incoming request for all companies:");
            List<Company> allCompanies = service.findAllCompanies();
            final List<CompanyDto> companies = allCompanies.stream().map(e -> dtoConverter.convertAndAttachStakeholders(e)).collect(Collectors.toList());

            LOGGER.info("List of the companies:{}", companies);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception ex) {

            return new ResponseEntity("Unable to finish fetch request:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "Receive a company details as a request to be created on Company Registration Office(CRO).",
            notes = "Receive a company details as a request to be created on Company Registration Office(CRO)",
            produces = MediaType.TEXT_PLAIN_VALUE, response = String.class, responseContainer = "String", httpMethod = "POST")
    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity createCompany(@RequestBody CompanyRegistrationRequest request) {
        LOGGER.info("Incoming request to register a Company info#{}.", request);
        try {
            requestValidator.validateRequest(request);
            Company company = service.registerCompany(request.getName(), request.getAddress(), request.getCity(), request.getCountry(), request.getEmail(), request.getPhoneNumber());
            company.setStakeholders(request.getStakeholders().stream().map(e -> {
                Stakeholder stakeholderById = service.findStakeholderById(e);
                service.registerBeneficialOwner(company, stakeholderById);
                return stakeholderById;
            }).collect(Collectors.toList()));
            CompanyDto companyDto = dtoConverter.convert(company);
            LOGGER.info("Registered Company info at CRO:{}", companyDto);
            return new ResponseEntity<>(companyDto, HttpStatus.CREATED);


        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish company registration operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "Receive a company details as an update request to be updated on Company Registration Office.", notes = "Receive an updateRequest for update an existing company in store.", produces = MediaType.TEXT_PLAIN_VALUE, response = String.class, responseContainer = "String", httpMethod = "PUT")
    @RequestMapping(value = "/companies/{companyId}", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity updateCompany(@PathVariable Integer companyId, @RequestBody CompanyRegistrationRequest updateRequest) {
        LOGGER.info("Incoming request for update a Company info#{}.", updateRequest);
        try {
            requestValidator.validateRequest(updateRequest);
            Company company = service.findCompanyById(companyId);
            if (company == null) {
                return new ResponseEntity<>("Invalid Company id.", HttpStatus.BAD_REQUEST);
            }
            company = service.updateCompany(company, updateRequest.getName(), updateRequest.getAddress(), updateRequest.getCity(),
                    updateRequest.getCountry(), updateRequest.getEmail(), updateRequest.getPhoneNumber());
            if (company != null) {
                CompanyDto dto = dtoConverter.convert(company);
                LOGGER.info("Update Company Info:{}", dto);
                return new ResponseEntity<>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unable to finish updating company operation.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish updating company operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Search a company based on the received company Id.", notes = "Search a company based on the received company Id.", produces = MediaType.TEXT_PLAIN_VALUE, response = String.class, responseContainer = "String", httpMethod = "PUT")
    @RequestMapping(value = "/companies/{companyId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity searchCompanyById(@PathVariable Integer companyId) {
        LOGGER.info("Incoming request to find/search for a company#{}.", companyId);
        try {
            Company company = service.findCompanyById(companyId);
            if (company != null) {
                CompanyDto dto = dtoConverter.convert(company);
                LOGGER.info("Found Company Info:{}", dto);
                return new ResponseEntity<>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Company not found.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish searching for a company operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Search a company based on the received company Id.", notes = "Search a company based on the received company Id.", produces = MediaType.TEXT_PLAIN_VALUE, response = String.class, responseContainer = "String", httpMethod = "PUT")
    @RequestMapping(value = "/companies/{companyName}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity searchCompanyByName(@PathVariable String companyName) {
        LOGGER.info("Incoming request to find/search for a company#{}.", companyName);
        try {
            List<Company> companies = service.findCompanyByName(companyName);
            if (companies != null && !companies.isEmpty()) {
                List<CompanyDto> foundCompanies = companies.stream().map(e -> dtoConverter.convert(e)).collect(Collectors.toList());
                LOGGER.info("Founded Company Info:{}", foundCompanies);
                return new ResponseEntity<>(foundCompanies, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Company not found.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish searching for a company operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
