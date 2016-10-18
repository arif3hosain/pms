package com.pms.repository;

import com.pms.domain.UmsRoleMst;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UmsRoleMst entity.
 */
public interface UmsRoleMstRepository extends JpaRepository<UmsRoleMst,Long> {

}
