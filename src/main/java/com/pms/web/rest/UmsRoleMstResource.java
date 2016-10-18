package com.pms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pms.domain.UmsRoleMst;
import com.pms.repository.UmsRoleMstRepository;
import com.pms.repository.search.UmsRoleMstSearchRepository;
import com.pms.web.rest.util.HeaderUtil;
import com.pms.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UmsRoleMst.
 */
@RestController
@RequestMapping("/api")
public class UmsRoleMstResource {

    private final Logger log = LoggerFactory.getLogger(UmsRoleMstResource.class);
        
    @Inject
    private UmsRoleMstRepository umsRoleMstRepository;
    
    @Inject
    private UmsRoleMstSearchRepository umsRoleMstSearchRepository;
    
    /**
     * POST  /umsRoleMsts -> Create a new umsRoleMst.
     */
    @RequestMapping(value = "/umsRoleMsts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmsRoleMst> createUmsRoleMst(@Valid @RequestBody UmsRoleMst umsRoleMst) throws URISyntaxException {
        log.debug("REST request to save UmsRoleMst : {}", umsRoleMst);
        if (umsRoleMst.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("umsRoleMst", "idexists", "A new umsRoleMst cannot already have an ID")).body(null);
        }
        UmsRoleMst result = umsRoleMstRepository.save(umsRoleMst);
        umsRoleMstSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umsRoleMsts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umsRoleMst", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umsRoleMsts -> Updates an existing umsRoleMst.
     */
    @RequestMapping(value = "/umsRoleMsts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmsRoleMst> updateUmsRoleMst(@Valid @RequestBody UmsRoleMst umsRoleMst) throws URISyntaxException {
        log.debug("REST request to update UmsRoleMst : {}", umsRoleMst);
        if (umsRoleMst.getId() == null) {
            return createUmsRoleMst(umsRoleMst);
        }
        UmsRoleMst result = umsRoleMstRepository.save(umsRoleMst);
        umsRoleMstSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umsRoleMst", umsRoleMst.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umsRoleMsts -> get all the umsRoleMsts.
     */
    @RequestMapping(value = "/umsRoleMsts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmsRoleMst>> getAllUmsRoleMsts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UmsRoleMsts");
        Page<UmsRoleMst> page = umsRoleMstRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umsRoleMsts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umsRoleMsts/:id -> get the "id" umsRoleMst.
     */
    @RequestMapping(value = "/umsRoleMsts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmsRoleMst> getUmsRoleMst(@PathVariable Long id) {
        log.debug("REST request to get UmsRoleMst : {}", id);
        UmsRoleMst umsRoleMst = umsRoleMstRepository.findOne(id);
        return Optional.ofNullable(umsRoleMst)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /umsRoleMsts/:id -> delete the "id" umsRoleMst.
     */
    @RequestMapping(value = "/umsRoleMsts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmsRoleMst(@PathVariable Long id) {
        log.debug("REST request to delete UmsRoleMst : {}", id);
        umsRoleMstRepository.delete(id);
        umsRoleMstSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umsRoleMst", id.toString())).build();
    }

    /**
     * SEARCH  /_search/umsRoleMsts/:query -> search for the umsRoleMst corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/umsRoleMsts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UmsRoleMst> searchUmsRoleMsts(@PathVariable String query) {
        log.debug("REST request to search UmsRoleMsts for query {}", query);
        return StreamSupport
            .stream(umsRoleMstSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
