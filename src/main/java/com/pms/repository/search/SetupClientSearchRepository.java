package com.pms.repository.search;

import com.pms.domain.SetupClient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SetupClient entity.
 */
public interface SetupClientSearchRepository extends ElasticsearchRepository<SetupClient, Long> {
}
