package com.pms.repository.search;

import com.pms.domain.UmsRoleMst;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UmsRoleMst entity.
 */
public interface UmsRoleMstSearchRepository extends ElasticsearchRepository<UmsRoleMst, Long> {
}
