package com.gnaderi.interview.cro.outbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "Stakeholder", description = "Stakeholder resource representation")
@JsonPropertyOrder(value = {"Id","FirstName", "LastName", "Companies"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StakeholderDto implements Serializable {
    private static final long serialVersionUID = 202614981212228599L;
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @ApiModelProperty(value = "A list of companies details owned by this stakeholder", required = true, dataType = "List")
    @JsonProperty("Companies")
    private List<CompanyDto> companies;

    public StakeholderDto() {
    }

    public StakeholderDto(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public StakeholderDto(Integer id, String firstName, String lastName, List<CompanyDto> companies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companies = companies;
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

    public List<CompanyDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyDto> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
