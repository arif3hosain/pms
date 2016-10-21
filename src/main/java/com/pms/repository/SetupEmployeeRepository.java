package com.pms.repository;

import com.pms.domain.SetupEmployee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SetupEmployee entity.
 */
public interface SetupEmployeeRepository extends JpaRepository<SetupEmployee,Long> {

}
