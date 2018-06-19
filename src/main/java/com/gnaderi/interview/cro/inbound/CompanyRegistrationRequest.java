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

@ApiModel(value = "CompanyRegistrationRequest", description = "Company registration request resource representation")
@JsonIgnoreProperties(ignoreUnknown = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyRegistrationRequest implements Serializable {
    private static final long serialVersionUID = -5669140629119046544L;
    @NotNull
    @JsonProperty("Name")
    private String name;
    @NotNull
    @JsonProperty("Address")
    private String address;
    @NotNull
    @JsonProperty("City")
    private String city;
    @NotNull
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;

    @JsonProperty(value = "Stakeholders")
    @ApiModelProperty(value = "A collection of the company stakeholders ID's", required = true, dataType = "List")
    private List<Integer> stakeholders;


    public CompanyRegistrationRequest() {
    }

    public CompanyRegistrationRequest(@NotBlank String name, @NotBlank String address, @NotBlank String city, @NotBlank String country, String email, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public CompanyRegistrationRequest(@NotBlank String name, @NotBlank String address, @NotBlank String city, @NotBlank String country, String email, String phoneNumber, List<Integer> stakeholders) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.stakeholders = stakeholders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Integer> getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(List<Integer> stakeholders) {
        this.stakeholders = stakeholders;
    }
}