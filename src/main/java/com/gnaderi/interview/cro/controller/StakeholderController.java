package com.gnaderi.interview.cro.controller;

import com.gnaderi.interview.cro.entity.Stakeholder;
import com.gnaderi.interview.cro.inbound.StakeholderRegistrationRequest;
import com.gnaderi.interview.cro.outbound.StakeholderDto;
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
public class StakeholderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StakeholderController.class);

    @Qualifier("SimpleCroService")
    @Autowired
    private CroService service;

    @Autowired
    private RequestValidator requestValidator;
    @Autowired
    private DtoConverter dtoConverter;

    @ApiOperation(value = "Get a list of all stakeholders stored in application.", notes = "Retrieve all stakeholders registered in CRO.", produces = MediaType.TEXT_PLAIN_VALUE, response = StakeholderDto.class, responseContainer = "String", httpMethod = "GET")
    @RequestMapping(value = "/stakeholders", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity fetchAllStakeholders() {
        try {
            LOGGER.info("Incoming request for all stakeholders.");
            List<Stakeholder> allStakeholders = service.findAllStakeholders();
            List<StakeholderDto> stakeholderDtos = allStakeholders.stream().map(e -> dtoConverter.convert(e)).collect(Collectors.toList());
            LOGGER.info("List Of All Stakeholders Stored In CRO:{}", stakeholderDtos);
            return new ResponseEntity<>(stakeholderDtos, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity("Unable to finish fetch request:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Receive a Stakeholder details as a request to register it at CRO.", notes = "Receive a Stakeholder details as a request to register it at CRO.", produces = MediaType.TEXT_PLAIN_VALUE, response = String.class, responseContainer = "String", httpMethod = "POST")
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

    @ApiOperation(value = "Receive a Stakeholder details to update a Stakeholder on CRO.", notes = "Receive an updateRequest for update an existing Stakeholder in CRO.", produces = MediaType.TEXT_PLAIN_VALUE, response = String.class, responseContainer = "String", httpMethod = "PUT")
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

    @ApiOperation(value = "Search a stakeholder based on the received stakeholder name.", notes = "Search a stakeholder based on the received stakeholder name.", produces = MediaType.TEXT_PLAIN_VALUE, response = String.class, responseContainer = "String", httpMethod = "GET")
    @RequestMapping(value = "/stakeholders/{stakeholderId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity searchStakeholderById(@PathVariable Integer stakeholderId) {
        LOGGER.info("Incoming request to find/search for a stakeholder#{}.", stakeholderId);
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

    @ApiOperation(value = "Search a stakeholder based on the received stakeholder name.", notes = "Search a stakeholder based on the received stakeholder name.", produces = MediaType.TEXT_PLAIN_VALUE, response = String.class, responseContainer = "String", httpMethod = "GET")
    @RequestMapping(value = "/stakeholders/{name}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity searchStakeholderByName(@PathVariable String name) {
        LOGGER.info("Incoming request to find/search for a stakeholder#{}.", name);
        try {
            List<Stakeholder> stakeholders = service.findStakeholderByName(name);
            if (stakeholders != null && !stakeholders.isEmpty()) {
                List<StakeholderDto> foundStakeholders = stakeholders.stream().map(e -> dtoConverter.convert(e)).collect(Collectors.toList());
                LOGGER.info("Founded stakeholders Info:{}", foundStakeholders);
                return new ResponseEntity<>(foundStakeholders, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Stakeholder not found.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to finish searching for a stakeholder operation:" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
