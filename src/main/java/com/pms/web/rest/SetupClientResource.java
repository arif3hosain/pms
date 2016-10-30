package com.pms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pms.domain.SetupClient;
import com.pms.repository.SetupClientRepository;
import com.pms.repository.search.SetupClientSearchRepository;
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
 * REST controller for managing SetupClient.
 */
@RestController
@RequestMapping("/api")
public class SetupClientResource {

    private final Logger log = LoggerFactory.getLogger(SetupClientResource.class);
        
    @Inject
    private SetupClientRepository setupClientRepository;
    
    @Inject
    private SetupClientSearchRepository setupClientSearchRepository;
    
    /**
     * POST  /setupClients -> Create a new setupClient.
     */
    @RequestMapping(value = "/setupClients",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupClient> createSetupClient(@Valid @RequestBody SetupClient setupClient) throws URISyntaxException {
        log.debug("REST request to save SetupClient : {}", setupClient);
        if (setupClient.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("setupClient", "idexists", "A new setupClient cannot already have an ID")).body(null);
        }
        SetupClient result = setupClientRepository.save(setupClient);
        setupClientSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/setupClients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("setupClient", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /setupClients -> Updates an existing setupClient.
     */
    @RequestMapping(value = "/setupClients",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupClient> updateSetupClient(@Valid @RequestBody SetupClient setupClient) throws URISyntaxException {
        log.debug("REST request to update SetupClient : {}", setupClient);
        if (setupClient.getId() == null) {
            return createSetupClient(setupClient);
        }
        SetupClient result = setupClientRepository.save(setupClient);
        setupClientSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("setupClient", setupClient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /setupClients -> get all the setupClients.
     */
    @RequestMapping(value = "/setupClients",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SetupClient>> getAllSetupClients(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SetupClients");
        Page<SetupClient> page = setupClientRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/setupClients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /setupClients/:id -> get the "id" setupClient.
     */
    @RequestMapping(value = "/setupClients/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupClient> getSetupClient(@PathVariable Long id) {
        log.debug("REST request to get SetupClient : {}", id);
        SetupClient setupClient = setupClientRepository.findOne(id);
        return Optional.ofNullable(setupClient)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /setupClients/:id -> delete the "id" setupClient.
     */
    @RequestMapping(value = "/setupClients/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSetupClient(@PathVariable Long id) {
        log.debug("REST request to delete SetupClient : {}", id);
        setupClientRepository.delete(id);
        setupClientSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("setupClient", id.toString())).build();
    }

    /**
     * SEARCH  /_search/setupClients/:query -> search for the setupClient corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/setupClients/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SetupClient> searchSetupClients(@PathVariable String query) {
        log.debug("REST request to search SetupClients for query {}", query);
        return StreamSupport
            .stream(setupClientSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
