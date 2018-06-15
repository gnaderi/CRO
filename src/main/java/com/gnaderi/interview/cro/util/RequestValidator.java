package com.gnaderi.interview.cro.util;


import com.gnaderi.interview.cro.CROException;
import com.gnaderi.interview.cro.inbound.CompanyRegistrationRequest;
import com.gnaderi.interview.cro.inbound.StakeholderRegistrationRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public final class RequestValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestValidator.class);

    public void validateRequest(final CompanyRegistrationRequest rq) throws CROException {
        try {
            if (StringUtils.isBlank(rq.getName()) ||
                    StringUtils.isBlank(rq.getAddress()) ||
                    StringUtils.isBlank(rq.getCountry()) ||
                    StringUtils.isBlank(rq.getCity())) {
                throw new CROException("Invalid Registration Request:Some properties are null!");
            }
        } catch (NullPointerException | IllegalArgumentException ex) {
            LOGGER.error("Invalid CompanyRegistrationRequest:", ex);
            throw new CROException("Invalid registrationRequest!");
        }
    }

    public void validateRequest(final StakeholderRegistrationRequest registrationRequest) throws CROException {
        try {
            //TODO
        } catch (NullPointerException | IllegalArgumentException ex) {
            LOGGER.error("Invalid StakeholderRegistrationRequest:", ex);
            throw new CROException("Invalid registrationRequest!");
        }
    }
}