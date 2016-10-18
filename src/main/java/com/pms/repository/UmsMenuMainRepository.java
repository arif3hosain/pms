package com.pms.repository;

import com.pms.domain.UmsMenuMain;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UmsMenuMain entity.
 */
public interface UmsMenuMainRepository extends JpaRepository<UmsMenuMain,Long> {

}
