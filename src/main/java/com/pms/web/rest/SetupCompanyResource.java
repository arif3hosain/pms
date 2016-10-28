package com.pms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pms.domain.SetupCompany;
import com.pms.repository.SetupCompanyRepository;
import com.pms.repository.search.SetupCompanySearchRepository;
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
 * REST controller for managing SetupCompany.
 */
@RestController
@RequestMapping("/api")
public class SetupCompanyResource {

    private final Logger log = LoggerFactory.getLogger(SetupCompanyResource.class);

    @Inject
    private SetupCompanyRepository setupCompanyRepository;

    @Inject
    private SetupCompanySearchRepository setupCompanySearchRepository;

    /**
     * POST  /setupCompanys -> Create a new setupCompany.
     */
    @RequestMapping(value = "/setupCompanys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupCompany> createSetupCompany(@Valid @RequestBody SetupCompany setupCompany) throws URISyntaxException {
        log.debug("REST request to save SetupCompany : {}", setupCompany);
        if (setupCompany.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("setupCompany", "idexists", "A new setupCompany cannot already have an ID")).body(null);
        }
        SetupCompany company=setupCompany;
        company.setStatus(1);
        SetupCompany result = setupCompanyRepository.save(company);
        setupCompanySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/setupCompanys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("setupCompany", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /setupCompanys -> Updates an existing setupCompany.
     */
    @RequestMapping(value = "/setupCompanys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupCompany> updateSetupCompany(@Valid @RequestBody SetupCompany setupCompany) throws URISyntaxException {
        log.debug("REST request to update SetupCompany : {}", setupCompany);
        if (setupCompany.getId() == null) {
            return createSetupCompany(setupCompany);
        }
        SetupCompany result = setupCompanyRepository.save(setupCompany);
        setupCompanySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("setupCompany", setupCompany.getId().toString()))
            .body(result);
    }

    /**
     * GET  /setupCompanys -> get all the setupCompanys.
     */
    @RequestMapping(value = "/setupCompanys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SetupCompany>> getAllSetupCompanys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SetupCompanys");
        Page<SetupCompany> page = setupCompanyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/setupCompanys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /setupCompanys/:id -> get the "id" setupCompany.
     */
    @RequestMapping(value = "/setupCompanys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SetupCompany> getSetupCompany(@PathVariable Long id) {
        log.debug("REST request to get SetupCompany : {}", id);
        SetupCompany setupCompany = setupCompanyRepository.findOne(id);
        return Optional.ofNullable(setupCompany)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /setupCompanys/:id -> delete the "id" setupCompany.
     */
    @RequestMapping(value = "/setupCompanys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSetupCompany(@PathVariable Long id) {
        log.debug("REST request to delete SetupCompany : {}", id);
        setupCompanyRepository.delete(id);
        setupCompanySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("setupCompany", id.toString())).build();
    }

    /**
     * SEARCH  /_search/setupCompanys/:query -> search for the setupCompany corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/setupCompanys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SetupCompany> searchSetupCompanys(@PathVariable String query) {
        log.debug("REST request to search SetupCompanys for query {}", query);
        return StreamSupport
            .stream(setupCompanySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/setupCompanys/user/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public SetupCompany getCompanyByUserId(@PathVariable Integer id) {
        log.debug("REST request to search SetupCompanys for query {}", id);
        long l=id;
        SetupCompany setupCompany=this.setupCompanyRepository.getCompany(l);
        System.out.println("output>>>>>>>>>>>>>>>>>>>>>>>>>"+setupCompany);
            if(setupCompany ==null){
                return null;
            }
            return setupCompany;
    }
}
