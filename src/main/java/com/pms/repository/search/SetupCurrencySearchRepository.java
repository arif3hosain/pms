package com.pms.repository.search;

import com.pms.domain.SetupCurrency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SetupCurrency entity.
 */
public interface SetupCurrencySearchRepository extends ElasticsearchRepository<SetupCurrency, Long> {
}
