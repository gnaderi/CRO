package com.gnaderi.interview.cro.controller;

import com.gnaderi.interview.cro.entity.Stakeholder;
import com.gnaderi.interview.cro.inbound.StakeholderRegistrationRequest;
import com.gnaderi.interview.cro.outbound.StakeholderDto;
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
public class StakeholderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StakeholderController.class);

    @Qualifier("SimpleCroService")
    @Autowired
    private CroService service;

    @Autowired
    private RequestValidator requestValidator;
    @Autowired
    private DtoConverter dtoConverter;

    @ApiOperation(value = "Search in or get a list of all stakeholders stored in application.", notes = "Retrieve all stakeholders registered in CRO.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = StakeholderDto.class, responseContainer = "List", httpMethod = "GET")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    @RequestMapping(value = "/stakeholders", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity searchStakeholders(@ApiParam(value = "FirstName or LastName of the stakeholder", name = "StakeholderName") @RequestParam(value = "name", required = false) String name,
                                      @ApiParam(value = "LastName of the stakeholder", name = "LastName") @RequestParam(value = "lastname", required = false) String lastName,
                                      @ApiParam(value = "FirstName of the stakeholder", name = "FirstName") @RequestParam(value = "firstname", required = false) String firstName,
                                      @ApiParam(value = "Show stakeholders with all companies owned details.", name = "showall") @RequestParam(value = "showall", required = false) boolean showall) {
        try {
            List<Stakeholder> allStakeholders = new ArrayList<>();
            if (StringUtils.isAllEmpty(name, lastName, firstName)) {
                LOGGER.info("Incoming request for get details of all stakeholders.");
                allStakeholders.addAll(service.findAllStakeholders());
            } else if (StringUtils.isNotBlank(name) && StringUtils.isAllEmpty(lastName, firstName)) {
                LOGGER.info("Incoming request for get details of any stakeholders with name#{}", name);
                allStakeholders.addAll(service.findStakeholderByName(name));
            } else if (StringUtils.isNoneBlank(firstName, lastName)) {
                LOGGER.info("Incoming request for get details of any stakeholders with FirstName#{} and LastName#{}", firstName, lastName);
                allStakeholders.addAll(service.findStakeholderByName(firstName, lastName));
            } else if (StringUtils.isNotBlank(firstName)) {
                LOGGER.info("Incoming request for get details of any stakeholders with FirstName#{}", firstName);
                allStakeholders.addAll(service.findStakeholderByName(firstName));
            } else {
                LOGGER.info("Incoming request for get details of any stakeholders with LastName#{}", firstName);
                allStakeholders.addAll(service.findStakeholderByName(lastName));
            }
            List<StakeholderDto> stakeholderDtos = allStakeholders.stream().map(e -> showall ? dtoConverter.convertAndAttachCompanies(e) : dtoConverter.convert(e)).collect(Collectors.toList());
            LOGGER.info("List Of All Stakeholders Stored In CRO:{}", stakeholderDtos);
            return new ResponseEntity<>(stakeholderDtos, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity("Unable to finish fetch request:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Receive a Stakeholder details as a request to register it at CRO.", notes = "Receive a Stakeholder details as a request to register it at CRO.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = StakeholderDto.class, responseContainer = "String", httpMethod = "POST")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(value = "/stakeholders", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity registerStakeholder(@RequestBody StakeholderRegistrationRequest request) {
        LOGGER.info("Incoming request for registering a Stakeholder info#{}.", request);
        try {
            requestValidator.validateRequest(request);
            Stakeholder stakeholder = service.createStakeholder(request.getFirstName(), request.getLastName());
            if (stakeholder != null) {
                StakeholderDto stakeholderDto = dtoConverter.convert(stakeholder);
                LOGGER.info("Registered Stakeholder Details:{}", stakeholderDto);
                return new ResponseEntity<>(stakeholderDto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Unable to finish create Stakeholder operation.", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish create Stakeholder operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Receive a Stakeholder details to update a Stakeholder on CRO.", notes = "Receive an updateRequest for update an existing Stakeholder in CRO.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = StakeholderDto.class, responseContainer = "String", httpMethod = "PUT")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(value = "/stakeholders/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity updateStakeholder(@PathVariable Integer id, @RequestBody StakeholderRegistrationRequest updateRequest) {
        LOGGER.info("Incoming Update Request For Stakeholder Info#{}.", updateRequest);
        try {
            requestValidator.validateRequest(updateRequest);
            Stakeholder stakeholder = service.findStakeholderById(id);
            if (stakeholder == null) {
                return new ResponseEntity<>("Invalid stakeholder id.", HttpStatus.BAD_REQUEST);
            }
            stakeholder = service.updateStakeholder(stakeholder, updateRequest.getFirstName(), updateRequest.getLastName());

            StakeholderDto stakeholderDto = dtoConverter.convert(stakeholder);
            LOGGER.info("Updated Stakeholder details:{}", stakeholderDto);
            return new ResponseEntity<>(stakeholderDto, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish updating stakeholder operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Search a stakeholder based on the received stakeholder name.", notes = "Search a stakeholder based on the received stakeholder name.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = StakeholderDto.class, responseContainer = "String", httpMethod = "GET")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    @RequestMapping(value = "/stakeholders/{stakeholderId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getStakeholderById(@PathVariable Integer stakeholderId) {
        LOGGER.info("Incoming request to get details of a stakeholder#{}.", stakeholderId);
        try {
            Stakeholder stakeholder = service.findStakeholderById(stakeholderId);
            if (stakeholder != null) {
                StakeholderDto stakeholderDto = dtoConverter.convert(stakeholder);
                LOGGER.info("Founded stakeholders Info:{}", stakeholderDto);
                return new ResponseEntity<>(stakeholderDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Stakeholder not found.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish searching for a stakeholder operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
