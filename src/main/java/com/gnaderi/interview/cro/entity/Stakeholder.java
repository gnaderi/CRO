package com.gnaderi.interview.cro.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Stakeholder {
    private Integer id;
    private String firstName;
    private String lastName;
    private Collection<Company> companies;

    public Stakeholder() {
    }

    public Stakeholder(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FIRST_NAME", nullable = false, length = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stakeholder that = (Stakeholder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName);
    }

    @ManyToMany(targetEntity = Stakeholder.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "BENEFICIAL_OWNER",
            joinColumns = @JoinColumn(name = "STAKEHOLDER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID")
    )
    public Collection<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Collection<Company> companies) {
        this.companies = companies;
    }
}
