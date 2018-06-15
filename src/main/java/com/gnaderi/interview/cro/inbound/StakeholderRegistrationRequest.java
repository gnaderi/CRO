package com.gnaderi.interview.cro.inbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "CompanyRegistrationRequest", description = "Create Stakeholder registration request resource representation")
@JsonIgnoreProperties(ignoreUnknown = false)
@JsonPropertyOrder(value = {"FirstName", "LastName"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StakeholderRegistrationRequest implements Serializable {
    private static final long serialVersionUID = -8668081319824733651L;
    @NotBlank
    @JsonProperty("FirstName")
    private String firstName;
    @NotBlank
    @JsonProperty("LastName")
    private String lastName;

    public StakeholderRegistrationRequest() {
    }

    public StakeholderRegistrationRequest(@NotBlank String firstName, @NotBlank String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}