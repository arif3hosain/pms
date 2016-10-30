package com.pms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pms.domain.SetupCurrency;
import com.pms.repository.SetupCurrencyRepository;
import com.pms.repository.search.SetupCurrencySearchRepository;
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
 * REST controller for managing SetupCurrency.
 */
@RestController
@RequestMapping("/api")
public class SetupCurrencyResource {

    private final Logger log = LoggerFactory.getLogger(SetupCurrencyResource.class);
        
    @Inject
    private SetupCurrencyRepository setupCurrencyRepository;
    
    @Inject
    private SetupCurrencySearchRepository setupCurrencySearchRepository;
    
    /**
     * POST  /setupCurrencys -> Create a new setupCurrency.
     */
    @RequestMapping(value = "/setupCurrencys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupCurrency> createSetupCurrency(@Valid @RequestBody SetupCurrency setupCurrency) throws URISyntaxException {
        log.debug("REST request to save SetupCurrency : {}", setupCurrency);
        if (setupCurrency.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("setupCurrency", "idexists", "A new setupCurrency cannot already have an ID")).body(null);
        }
        SetupCurrency result = setupCurrencyRepository.save(setupCurrency);
        setupCurrencySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/setupCurrencys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("setupCurrency", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /setupCurrencys -> Updates an existing setupCurrency.
     */
    @RequestMapping(value = "/setupCurrencys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupCurrency> updateSetupCurrency(@Valid @RequestBody SetupCurrency setupCurrency) throws URISyntaxException {
        log.debug("REST request to update SetupCurrency : {}", setupCurrency);
        if (setupCurrency.getId() == null) {
            return createSetupCurrency(setupCurrency);
        }
        SetupCurrency result = setupCurrencyRepository.save(setupCurrency);
        setupCurrencySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("setupCurrency", setupCurrency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /setupCurrencys -> get all the setupCurrencys.
     */
    @RequestMapping(value = "/setupCurrencys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SetupCurrency>> getAllSetupCurrencys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SetupCurrencys");
        Page<SetupCurrency> page = setupCurrencyRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/setupCurrencys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /setupCurrencys/:id -> get the "id" setupCurrency.
     */
    @RequestMapping(value = "/setupCurrencys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupCurrency> getSetupCurrency(@PathVariable Long id) {
        log.debug("REST request to get SetupCurrency : {}", id);
        SetupCurrency setupCurrency = setupCurrencyRepository.findOne(id);
        return Optional.ofNullable(setupCurrency)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /setupCurrencys/:id -> delete the "id" setupCurrency.
     */
    @RequestMapping(value = "/setupCurrencys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSetupCurrency(@PathVariable Long id) {
        log.debug("REST request to delete SetupCurrency : {}", id);
        setupCurrencyRepository.delete(id);
        setupCurrencySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("setupCurrency", id.toString())).build();
    }

    /**
     * SEARCH  /_search/setupCurrencys/:query -> search for the setupCurrency corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/setupCurrencys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SetupCurrency> searchSetupCurrencys(@PathVariable String query) {
        log.debug("REST request to search SetupCurrencys for query {}", query);
        return StreamSupport
            .stream(setupCurrencySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
