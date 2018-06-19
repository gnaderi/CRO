package com.gnaderi.interview.cro.controller;

import com.gnaderi.interview.cro.entity.Company;
import com.gnaderi.interview.cro.entity.Stakeholder;
import com.gnaderi.interview.cro.inbound.BnfOwnerRegReq;
import com.gnaderi.interview.cro.inbound.CompanyRegistrationRequest;
import com.gnaderi.interview.cro.outbound.CompanyDto;
import com.gnaderi.interview.cro.service.CroService;
import com.gnaderi.interview.cro.util.DtoConverter;
import com.gnaderi.interview.cro.util.RequestValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Company Registrations Office REST API Interface", tags = "Company Registrations Office REST API Interface", description = "Company Registrations Office REST API Interface")
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

    @ApiOperation(value = "Get/search a list of all companies stored in application.",
            notes = "Retrieve all companies details.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = CompanyDto.class, responseContainer = "List", httpMethod = "GET")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity searchCompanies(@ApiParam(value = "name of the company", name = "CompanyName") @RequestParam(value = "name", required = false) String name,
                                   @ApiParam(value = "searching for a company by id") @RequestParam(value = "id", required = false) Integer id,
                                   @ApiParam(value = "Show companies with all stakeholders details.", name = "showall") @RequestParam(value = "showall", required = false) boolean showall) {
        try {
            List<Company> foundCompanies = new ArrayList<>();
            if (StringUtils.isBlank(name) && id == null) {
                LOGGER.info("Incoming request for all companies:");
                foundCompanies.addAll(service.findAllCompanies());
            } else if (id != null) {
                LOGGER.info("Incoming request for get details of company Id#{}", id);
                foundCompanies.add(service.findCompanyById(id));
            } else {
                LOGGER.info("Incoming request for get details of company name#{}", name);
                foundCompanies.addAll(service.findCompanyByName(name));
            }
            final List<CompanyDto> companies = foundCompanies.stream().map(com -> showall ?
                    dtoConverter.convertAndAttachStakeholders(com) : dtoConverter.convert(com))
                    .collect(Collectors.toList());

            LOGGER.info("List of the companies:{}", companies);
            return new ResponseEntity<>(companies, HttpStatus.OK);

        } catch (Exception ex) {

            return new ResponseEntity("Unable to finish fetch request:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "Receive a company details as a request to be created on Company Registration Office(CRO).",
            notes = "Receive a company details as a request to be created on Company Registration Office(CRO)",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = CompanyDto.class, responseContainer = "String", httpMethod = "POST")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity registerCompany(@RequestBody CompanyRegistrationRequest request) {
        LOGGER.info("Incoming request to register a Company info#{}.", request);
        try {
            requestValidator.validateRequest(request);
            Company company = service.registerCompany(request.getName(), request.getAddress(), request.getCity(), request.getCountry(), request.getEmail(), request.getPhoneNumber());
            request.getStakeholders().forEach(sId -> service.registerBeneficialOwner(company, service.findStakeholderById(sId)));
            Company updatedCompany = service.findCompanyById(company.getId());
            CompanyDto companyDto = request.getStakeholders().isEmpty() ?
                    dtoConverter.convert(updatedCompany) : dtoConverter.convertAndAttachStakeholders(updatedCompany);
            LOGGER.info("Registered Company info at CRO:{}", companyDto);
            return new ResponseEntity<>(companyDto, HttpStatus.CREATED);


        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish company registration operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "Receive a company Id and a list of stakeholders Id's as a request to be registered as beneficial owner of the company.",
            notes = "Receive a company Id and a list of stakeholders Id's as a request to be registered as beneficial owner of the company.",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = CompanyDto.class, responseContainer = "String", httpMethod = "POST")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(value = "/owners", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity registerBeneficialOwner(@RequestBody BnfOwnerRegReq req) {
        LOGGER.info("Incoming request to register a list of beneficial owners#[{}] for  Company Id#{}.", req.getCompanyId(), req.getStakeholders().toString());
        try {
            requestValidator.validateRequest(req);
            Company company = service.findCompanyById(req.getCompanyId());

            req.getStakeholders().forEach(sId -> service.registerBeneficialOwner(company, service.findStakeholderById(sId)));
            Company updatedCompany = service.findCompanyById(req.getCompanyId());
            CompanyDto companyDto = dtoConverter.convertAndAttachStakeholders(updatedCompany);
            LOGGER.info("Registered Company info at CRO:{}", companyDto);
            return new ResponseEntity<>(companyDto, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish company registration operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "Receive a company details as an update request to be updated on Company Registration Office.",
            notes = "Receive an updateRequest for update an existing company in store.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = CompanyDto.class, responseContainer = "String", httpMethod = "PUT")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
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

    @ApiOperation(value = "Search a company based on the received company Id.", notes = "Search a company based on the received company Id.",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = CompanyDto.class, responseContainer = "String", httpMethod = "GET")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    @RequestMapping(value = "/companies/{companyId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getCompanyById(@PathVariable Integer companyId) {
        LOGGER.info("Incoming request to get details of a Company Id#{}.", companyId);
        try {
            Company company = service.findCompanyById(companyId);
            if (company != null) {
                CompanyDto dto = dtoConverter.convertAndAttachStakeholders(company);
                LOGGER.info("Found Company Info:{}", dto);
                return new ResponseEntity<>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Company not found.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish searching for a company operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
