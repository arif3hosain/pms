package com.pms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.pms.domain.enumeration.ClientType;

/**
 * A SetupClient.
 */
@Entity
@Table(name = "setup_client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "setupclient")
public class SetupClient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "website")
    private String website;

    @NotNull
    @Column(name = "industry_name", nullable = false)
    private String industryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_type")
    private ClientType clientType;

    @Column(name = "cust_vat_chal_code")
    private String custVatChalCode;

    @Column(name = "cust_vat_reg_no")
    private String custVatRegNo;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @ManyToOne
    @JoinColumn(name = "setup_company_id")
    private SetupCompany setupCompany;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getCustVatChalCode() {
        return custVatChalCode;
    }

    public void setCustVatChalCode(String custVatChalCode) {
        this.custVatChalCode = custVatChalCode;
    }

    public String getCustVatRegNo() {
        return custVatRegNo;
    }

    public void setCustVatRegNo(String custVatRegNo) {
        this.custVatRegNo = custVatRegNo;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public SetupCompany getSetupCompany() {
        return setupCompany;
    }

    public void setSetupCompany(SetupCompany setupCompany) {
        this.setupCompany = setupCompany;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SetupClient setupClient = (SetupClient) o;
        return Objects.equals(id, setupClient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SetupClient{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", website='" + website + "'" +
            ", industryName='" + industryName + "'" +
            ", clientType='" + clientType + "'" +
            ", custVatChalCode='" + custVatChalCode + "'" +
            ", custVatRegNo='" + custVatRegNo + "'" +
            ", status='" + status + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            '}';
    }
}
