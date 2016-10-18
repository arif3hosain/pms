package com.pms.repository;

import com.pms.domain.SetupCompany;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SetupCompany entity.
 */
public interface SetupCompanyRepository extends JpaRepository<SetupCompany,Long> {

}
