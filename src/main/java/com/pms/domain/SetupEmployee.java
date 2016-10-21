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

/**
 * A SetupEmployee.
 */
@Entity
@Table(name = "setup_employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "setupemployee")
public class SetupEmployee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "emp_code", length = 100, nullable = false)
    private String empCode;

    @Column(name = "employee_title")
    private String employeeTitle;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "std_working_hour")
    private Integer stdWorkingHour;

    @Column(name = "over_time")
    private Integer overTime;

    @Column(name = "tin_no")
    private String tinNo;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "application_id")
    private Integer applicationId;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @Column(name = "profile_picture_content_type")        private String profilePictureContentType;
    @Column(name = "superior_id")
    private Integer superiorId;

    @Column(name = "deg_code")
    private String degCode;

    @Column(name = "employee_type")
    private String employeeType;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "hourly_rate")
    private Integer hourlyRate;

    @Column(name = "fixed_salary")
    private Integer fixedSalary;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

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

    @OneToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "setup_company_id")
    private SetupCompany setupCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmployeeTitle() {
        return employeeTitle;
    }

    public void setEmployeeTitle(String employeeTitle) {
        this.employeeTitle = employeeTitle;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Integer getStdWorkingHour() {
        return stdWorkingHour;
    }

    public void setStdWorkingHour(Integer stdWorkingHour) {
        this.stdWorkingHour = stdWorkingHour;
    }

    public Integer getOverTime() {
        return overTime;
    }

    public void setOverTime(Integer overTime) {
        this.overTime = overTime;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePictureContentType() {
        return profilePictureContentType;
    }

    public void setProfilePictureContentType(String profilePictureContentType) {
        this.profilePictureContentType = profilePictureContentType;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    public String getDegCode() {
        return degCode;
    }

    public void setDegCode(String degCode) {
        this.degCode = degCode;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Integer getFixedSalary() {
        return fixedSalary;
    }

    public void setFixedSalary(Integer fixedSalary) {
        this.fixedSalary = fixedSalary;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SetupCompany getSetupCompany() {
        return setupCompany;
    }

    public void setSetupCompany(SetupCompany setupCompany) {
        this.setupCompany = setupCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SetupEmployee setupEmployee = (SetupEmployee) o;
        return Objects.equals(id, setupEmployee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SetupEmployee{" +
            "id=" + id +
            ", empCode='" + empCode + "'" +
            ", employeeTitle='" + employeeTitle + "'" +
            ", employeeName='" + employeeName + "'" +
            ", joiningDate='" + joiningDate + "'" +
            ", stdWorkingHour='" + stdWorkingHour + "'" +
            ", overTime='" + overTime + "'" +
            ", tinNo='" + tinNo + "'" +
            ", deptId='" + deptId + "'" +
            ", applicationId='" + applicationId + "'" +
            ", profilePicture='" + profilePicture + "'" +
            ", profilePictureContentType='" + profilePictureContentType + "'" +
            ", superiorId='" + superiorId + "'" +
            ", degCode='" + degCode + "'" +
            ", employeeType='" + employeeType + "'" +
            ", bloodGroup='" + bloodGroup + "'" +
            ", phone='" + phone + "'" +
            ", address='" + address + "'" +
            ", hourlyRate='" + hourlyRate + "'" +
            ", fixedSalary='" + fixedSalary + "'" +
            ", dateOfBirth='" + dateOfBirth + "'" +
            ", status='" + status + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            '}';
    }
}
