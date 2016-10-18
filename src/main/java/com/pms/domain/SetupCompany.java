package com.pms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SetupCompany.
 */
@Entity
@Table(name = "setup_company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "setupcompany")
public class SetupCompany implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "company_code", nullable = false)
    private String companyCode;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "add1")
    private String add1;

    @Column(name = "add2")
    private String add2;

    @Column(name = "phone1")
    private String phone1;

    @Column(name = "phone2")
    private String phone2;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "vatno1")
    private String vatno1;

    @Column(name = "web")
    private String web;

    @Column(name = "tin")
    private String tin;

    @Column(name = "csymbol")
    private String csymbol;

    @Column(name = "secuse")
    private String secuse;

    @Column(name = "bcsymbol")
    private String bcsymbol;

    @Column(name = "cfname")
    private String cfname;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVatno1() {
        return vatno1;
    }

    public void setVatno1(String vatno1) {
        this.vatno1 = vatno1;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getCsymbol() {
        return csymbol;
    }

    public void setCsymbol(String csymbol) {
        this.csymbol = csymbol;
    }

    public String getSecuse() {
        return secuse;
    }

    public void setSecuse(String secuse) {
        this.secuse = secuse;
    }

    public String getBcsymbol() {
        return bcsymbol;
    }

    public void setBcsymbol(String bcsymbol) {
        this.bcsymbol = bcsymbol;
    }

    public String getCfname() {
        return cfname;
    }

    public void setCfname(String cfname) {
        this.cfname = cfname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SetupCompany setupCompany = (SetupCompany) o;
        return Objects.equals(id, setupCompany.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SetupCompany{" +
            "id=" + id +
            ", companyCode='" + companyCode + "'" +
            ", name='" + name + "'" +
            ", add1='" + add1 + "'" +
            ", add2='" + add2 + "'" +
            ", phone1='" + phone1 + "'" +
            ", phone2='" + phone2 + "'" +
            ", fax='" + fax + "'" +
            ", email='" + email + "'" +
            ", vatno1='" + vatno1 + "'" +
            ", web='" + web + "'" +
            ", tin='" + tin + "'" +
            ", csymbol='" + csymbol + "'" +
            ", secuse='" + secuse + "'" +
            ", bcsymbol='" + bcsymbol + "'" +
            ", cfname='" + cfname + "'" +
            ", status='" + status + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            '}';
    }
}
