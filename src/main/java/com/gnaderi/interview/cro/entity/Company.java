package com.gnaderi.interview.cro.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Company {
    private Integer id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String email;
    private String phoneNumber;
    private Collection<Stakeholder> stakeholders;

    public Company() {
    }

    public Company(String name, String address, String city, String country, String email, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ADDRESS", nullable = false, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "CITY", nullable = false, length = 255)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "COUNTRY", nullable = false, length = 255)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "EMAIL", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "PHONE_NUMBER", nullable = true, length = 255)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) &&
                Objects.equals(name, company.name) &&
                Objects.equals(address, company.address) &&
                Objects.equals(city, company.city) &&
                Objects.equals(country, company.country) &&
                Objects.equals(email, company.email) &&
                Objects.equals(phoneNumber, company.phoneNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, address, city, country, email, phoneNumber);
    }

    @ManyToMany(targetEntity = Stakeholder.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "BENEFICIAL_OWNER",
            joinColumns = @JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "STAKEHOLDER_ID", referencedColumnName = "ID")
    )
    public Collection<Stakeholder> getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(Collection<Stakeholder> stakeholders) {
        this.stakeholders = stakeholders;
    }
}
