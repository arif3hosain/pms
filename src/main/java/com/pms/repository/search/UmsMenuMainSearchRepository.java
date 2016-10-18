package com.pms.repository.search;

import com.pms.domain.UmsMenuMain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UmsMenuMain entity.
 */
public interface UmsMenuMainSearchRepository extends ElasticsearchRepository<UmsMenuMain, Long> {
}
