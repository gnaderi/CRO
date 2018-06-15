package com.gnaderi.interview.cro.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "BENEFICIAL_OWNER")
public class BeneficialOwner {
    private Integer id;
    private Company company;
    private Stakeholder stakeholder;

    public BeneficialOwner() {
    }

    public BeneficialOwner(Company company, Stakeholder stakeholder) {
        this.company = company;
        this.stakeholder = stakeholder;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeneficialOwner that = (BeneficialOwner) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID", nullable = false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company companyByCompanyId) {
        this.company = companyByCompanyId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STAKEHOLDER_ID", referencedColumnName = "ID", nullable = false)
    public Stakeholder getStakeholder() {
        return stakeholder;
    }

    public void setStakeholder(Stakeholder stakeholderByStakeholderId) {
        this.stakeholder = stakeholderByStakeholderId;
    }
}
