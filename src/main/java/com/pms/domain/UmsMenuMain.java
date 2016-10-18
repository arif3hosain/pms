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
 * A UmsMenuMain.
 */
@Entity
@Table(name = "ums_menu_main")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "umsmenumain")
public class UmsMenuMain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @NotNull
    @Column(name = "menu_type", nullable = false)
    private String menuType;

    @Column(name = "run_file_name")
    private String RunFileName;

    @Column(name = "menu_sl")
    private Integer MenuSl;

    @Column(name = "parent_id")
    private Integer ParentId;

    @Column(name = "home_dir")
    private String homeDir;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "menu_level")
    private Integer menuLevel;

    @Lob
    @Column(name = "prev_icon_level")
    private byte[] prevIconLevel;

    @Column(name = "prev_icon_level_content_type")        private String prevIconLevelContentType;
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getRunFileName() {
        return RunFileName;
    }

    public void setRunFileName(String RunFileName) {
        this.RunFileName = RunFileName;
    }

    public Integer getMenuSl() {
        return MenuSl;
    }

    public void setMenuSl(Integer MenuSl) {
        this.MenuSl = MenuSl;
    }

    public Integer getParentId() {
        return ParentId;
    }

    public void setParentId(Integer ParentId) {
        this.ParentId = ParentId;
    }

    public String getHomeDir() {
        return homeDir;
    }

    public void setHomeDir(String homeDir) {
        this.homeDir = homeDir;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public byte[] getPrevIconLevel() {
        return prevIconLevel;
    }

    public void setPrevIconLevel(byte[] prevIconLevel) {
        this.prevIconLevel = prevIconLevel;
    }

    public String getPrevIconLevelContentType() {
        return prevIconLevelContentType;
    }

    public void setPrevIconLevelContentType(String prevIconLevelContentType) {
        this.prevIconLevelContentType = prevIconLevelContentType;
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
        UmsMenuMain umsMenuMain = (UmsMenuMain) o;
        return Objects.equals(id, umsMenuMain.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UmsMenuMain{" +
            "id=" + id +
            ", menuName='" + menuName + "'" +
            ", menuType='" + menuType + "'" +
            ", RunFileName='" + RunFileName + "'" +
            ", MenuSl='" + MenuSl + "'" +
            ", ParentId='" + ParentId + "'" +
            ", homeDir='" + homeDir + "'" +
            ", remarks='" + remarks + "'" +
            ", menuLevel='" + menuLevel + "'" +
            ", prevIconLevel='" + prevIconLevel + "'" +
            ", prevIconLevelContentType='" + prevIconLevelContentType + "'" +
            ", status='" + status + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            '}';
    }
}
