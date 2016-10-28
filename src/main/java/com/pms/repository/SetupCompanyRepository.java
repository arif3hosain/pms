package com.pms.repository;

import com.pms.domain.SetupCompany;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the SetupCompany entity.
 */
public interface SetupCompanyRepository extends JpaRepository<SetupCompany,Long> {

    @Query("select setupCompany from SetupCompany setupCompany where setupCompany.user.id= :id ")
    SetupCompany getCompany(@Param("id") Long id);
}
