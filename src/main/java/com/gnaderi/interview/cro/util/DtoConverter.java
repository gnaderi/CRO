package com.gnaderi.interview.cro.util;

import com.gnaderi.interview.cro.entity.Company;
import com.gnaderi.interview.cro.entity.Stakeholder;
import com.gnaderi.interview.cro.outbound.CompanyDto;
import com.gnaderi.interview.cro.outbound.StakeholderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
final public class DtoConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DtoConverter.class);

    public CompanyDto convertAndAttachStakeholders(Company com) {
        CompanyDto dto = new CompanyDto(com.getId(), com.getName(), com.getAddress(),
                com.getCity(), com.getCountry(), com.getEmail(), com.getPhoneNumber());
        final List<StakeholderDto> stakeholders = com.getStakeholders().stream().map(this::convert).collect(Collectors.toList());
        dto.setStakeholders(stakeholders);
        return dto;
    }
    public CompanyDto convert(Company com) {
        return new CompanyDto(com.getId(), com.getName(), com.getAddress(),
                com.getCity(), com.getCountry(), com.getEmail(), com.getPhoneNumber());
    }
    public StakeholderDto convertAndAttachCompanies(Stakeholder stakeholder) {
       final StakeholderDto dto = new StakeholderDto(stakeholder.getId(), stakeholder.getFirstName(), stakeholder.getLastName());

        final List<CompanyDto> companies = stakeholder.getCompanies().stream().map(this::convert).collect(Collectors.toList());
        dto.setCompanies(companies);
        return dto;
    }

    public StakeholderDto convert(Stakeholder stakeholder) {
        return new StakeholderDto(stakeholder.getId(), stakeholder.getFirstName(), stakeholder.getLastName());
    }
}
