package com.gnaderi.interview.cro.outbound;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gnaderi.interview.cro.entity.Company;
import com.gnaderi.interview.cro.entity.Stakeholder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "Company", description = "Company resource representation")
@JsonPropertyOrder(value = {"Id","Name","Address","City","Country","Email","PhoneNumber","Stakeholders"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDto implements Serializable {
    private static final long serialVersionUID = -1392048961010165536L;
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("City")
    private String city;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;
    @JsonProperty("Stakeholders")
    @ApiModelProperty(value = "A collection of the returned company stakeholders", required = true, dataType = "List")
    private List<StakeholderDto> stakeholders;

    public CompanyDto() {
    }

    public CompanyDto(Integer id, String name, String address, String city, String country, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public CompanyDto(Integer id, String name, String address, String city, String country, String email, String phoneNumber, List<StakeholderDto> stakeholders) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.stakeholders = stakeholders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<StakeholderDto> getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(List<StakeholderDto> stakeholders) {
        this.stakeholders = stakeholders;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}