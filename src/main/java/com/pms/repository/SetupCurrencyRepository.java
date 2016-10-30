package com.pms.repository;

import com.pms.domain.SetupCurrency;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SetupCurrency entity.
 */
public interface SetupCurrencyRepository extends JpaRepository<SetupCurrency,Long> {

}
