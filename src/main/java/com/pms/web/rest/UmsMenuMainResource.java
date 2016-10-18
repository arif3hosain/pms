package com.pms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pms.domain.UmsMenuMain;
import com.pms.repository.UmsMenuMainRepository;
import com.pms.repository.search.UmsMenuMainSearchRepository;
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
 * REST controller for managing UmsMenuMain.
 */
@RestController
@RequestMapping("/api")
public class UmsMenuMainResource {

    private final Logger log = LoggerFactory.getLogger(UmsMenuMainResource.class);
        
    @Inject
    private UmsMenuMainRepository umsMenuMainRepository;
    
    @Inject
    private UmsMenuMainSearchRepository umsMenuMainSearchRepository;
    
    /**
     * POST  /umsMenuMains -> Create a new umsMenuMain.
     */
    @RequestMapping(value = "/umsMenuMains",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmsMenuMain> createUmsMenuMain(@Valid @RequestBody UmsMenuMain umsMenuMain) throws URISyntaxException {
        log.debug("REST request to save UmsMenuMain : {}", umsMenuMain);
        if (umsMenuMain.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("umsMenuMain", "idexists", "A new umsMenuMain cannot already have an ID")).body(null);
        }
        UmsMenuMain result = umsMenuMainRepository.save(umsMenuMain);
        umsMenuMainSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umsMenuMains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umsMenuMain", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umsMenuMains -> Updates an existing umsMenuMain.
     */
    @RequestMapping(value = "/umsMenuMains",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmsMenuMain> updateUmsMenuMain(@Valid @RequestBody UmsMenuMain umsMenuMain) throws URISyntaxException {
        log.debug("REST request to update UmsMenuMain : {}", umsMenuMain);
        if (umsMenuMain.getId() == null) {
            return createUmsMenuMain(umsMenuMain);
        }
        UmsMenuMain result = umsMenuMainRepository.save(umsMenuMain);
        umsMenuMainSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umsMenuMain", umsMenuMain.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umsMenuMains -> get all the umsMenuMains.
     */
    @RequestMapping(value = "/umsMenuMains",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmsMenuMain>> getAllUmsMenuMains(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UmsMenuMains");
        Page<UmsMenuMain> page = umsMenuMainRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umsMenuMains");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umsMenuMains/:id -> get the "id" umsMenuMain.
     */
    @RequestMapping(value = "/umsMenuMains/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmsMenuMain> getUmsMenuMain(@PathVariable Long id) {
        log.debug("REST request to get UmsMenuMain : {}", id);
        UmsMenuMain umsMenuMain = umsMenuMainRepository.findOne(id);
        return Optional.ofNullable(umsMenuMain)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /umsMenuMains/:id -> delete the "id" umsMenuMain.
     */
    @RequestMapping(value = "/umsMenuMains/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmsMenuMain(@PathVariable Long id) {
        log.debug("REST request to delete UmsMenuMain : {}", id);
        umsMenuMainRepository.delete(id);
        umsMenuMainSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umsMenuMain", id.toString())).build();
    }

    /**
     * SEARCH  /_search/umsMenuMains/:query -> search for the umsMenuMain corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/umsMenuMains/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UmsMenuMain> searchUmsMenuMains(@PathVariable String query) {
        log.debug("REST request to search UmsMenuMains for query {}", query);
        return StreamSupport
            .stream(umsMenuMainSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
