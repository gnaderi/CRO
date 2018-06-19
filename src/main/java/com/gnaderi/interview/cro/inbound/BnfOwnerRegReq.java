package com.gnaderi.interview.cro.inbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "BeneficialRegisterRequest", description = "Beneficial owner/stakeholder registration request resource representation")
@JsonIgnoreProperties(ignoreUnknown = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BnfOwnerRegReq implements Serializable {
    private static final long serialVersionUID = 1183253347355561833L;
    @ApiModelProperty(value = "Company Id", required = true)
    @JsonProperty(value = "CompanyId", required = true)
    private Integer companyId;

    @JsonProperty(value = "Stakeholders", required = true)
    @ApiModelProperty(value = "A collection of the company stakeholders ID's", required = true, dataType = "List")
    private List<Integer> stakeholders;


    public BnfOwnerRegReq() {
    }

    public BnfOwnerRegReq(Integer companyId, List<Integer> stakeholders) {
        this.companyId = companyId;
        this.stakeholders = stakeholders;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<Integer> getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(List<Integer> stakeholders) {
        this.stakeholders = stakeholders;
    }
}