package com.pms.repository;

import com.pms.domain.SetupClient;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SetupClient entity.
 */
public interface SetupClientRepository extends JpaRepository<SetupClient,Long> {

}
