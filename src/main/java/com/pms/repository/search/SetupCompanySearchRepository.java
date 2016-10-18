package com.pms.repository.search;

import com.pms.domain.SetupCompany;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SetupCompany entity.
 */
public interface SetupCompanySearchRepository extends ElasticsearchRepository<SetupCompany, Long> {
}
