package com.pms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pms.domain.SetupEmployee;
import com.pms.repository.SetupEmployeeRepository;
import com.pms.repository.search.SetupEmployeeSearchRepository;
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
 * REST controller for managing SetupEmployee.
 */
@RestController
@RequestMapping("/api")
public class SetupEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(SetupEmployeeResource.class);
        
    @Inject
    private SetupEmployeeRepository setupEmployeeRepository;
    
    @Inject
    private SetupEmployeeSearchRepository setupEmployeeSearchRepository;
    
    /**
     * POST  /setupEmployees -> Create a new setupEmployee.
     */
    @RequestMapping(value = "/setupEmployees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupEmployee> createSetupEmployee(@Valid @RequestBody SetupEmployee setupEmployee) throws URISyntaxException {
        log.debug("REST request to save SetupEmployee : {}", setupEmployee);
        if (setupEmployee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("setupEmployee", "idexists", "A new setupEmployee cannot already have an ID")).body(null);
        }
        SetupEmployee result = setupEmployeeRepository.save(setupEmployee);
        setupEmployeeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/setupEmployees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("setupEmployee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /setupEmployees -> Updates an existing setupEmployee.
     */
    @RequestMapping(value = "/setupEmployees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupEmployee> updateSetupEmployee(@Valid @RequestBody SetupEmployee setupEmployee) throws URISyntaxException {
        log.debug("REST request to update SetupEmployee : {}", setupEmployee);
        if (setupEmployee.getId() == null) {
            return createSetupEmployee(setupEmployee);
        }
        SetupEmployee result = setupEmployeeRepository.save(setupEmployee);
        setupEmployeeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("setupEmployee", setupEmployee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /setupEmployees -> get all the setupEmployees.
     */
    @RequestMapping(value = "/setupEmployees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SetupEmployee>> getAllSetupEmployees(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SetupEmployees");
        Page<SetupEmployee> page = setupEmployeeRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/setupEmployees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /setupEmployees/:id -> get the "id" setupEmployee.
     */
    @RequestMapping(value = "/setupEmployees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupEmployee> getSetupEmployee(@PathVariable Long id) {
        log.debug("REST request to get SetupEmployee : {}", id);
        SetupEmployee setupEmployee = setupEmployeeRepository.findOne(id);
        return Optional.ofNullable(setupEmployee)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /setupEmployees/:id -> delete the "id" setupEmployee.
     */
    @RequestMapping(value = "/setupEmployees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSetupEmployee(@PathVariable Long id) {
        log.debug("REST request to delete SetupEmployee : {}", id);
        setupEmployeeRepository.delete(id);
        setupEmployeeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("setupEmployee", id.toString())).build();
    }

    /**
     * SEARCH  /_search/setupEmployees/:query -> search for the setupEmployee corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/setupEmployees/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SetupEmployee> searchSetupEmployees(@PathVariable String query) {
        log.debug("REST request to search SetupEmployees for query {}", query);
        return StreamSupport
            .stream(setupEmployeeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
