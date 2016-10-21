package com.pms.repository.search;

import com.pms.domain.SetupEmployee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SetupEmployee entity.
 */
public interface SetupEmployeeSearchRepository extends ElasticsearchRepository<SetupEmployee, Long> {
}
