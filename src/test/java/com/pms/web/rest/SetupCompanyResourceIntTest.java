package com.pms.web.rest;

import com.pms.Application;
import com.pms.domain.SetupCompany;
import com.pms.repository.SetupCompanyRepository;
import com.pms.repository.search.SetupCompanySearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SetupCompanyResource REST controller.
 *
 * @see SetupCompanyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SetupCompanyResourceIntTest {

    private static final String DEFAULT_COMPANY_CODE = "AAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_ADD1 = "AAAAA";
    private static final String UPDATED_ADD1 = "BBBBB";
    private static final String DEFAULT_ADD2 = "AAAAA";
    private static final String UPDATED_ADD2 = "BBBBB";
    private static final String DEFAULT_PHONE1 = "AAAAA";
    private static final String UPDATED_PHONE1 = "BBBBB";
    private static final String DEFAULT_PHONE2 = "AAAAA";
    private static final String UPDATED_PHONE2 = "BBBBB";
    private static final String DEFAULT_FAX = "AAAAA";
    private static final String UPDATED_FAX = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_VATNO1 = "AAAAA";
    private static final String UPDATED_VATNO1 = "BBBBB";
    private static final String DEFAULT_WEB = "AAAAA";
    private static final String UPDATED_WEB = "BBBBB";
    private static final String DEFAULT_TIN = "AAAAA";
    private static final String UPDATED_TIN = "BBBBB";
    private static final String DEFAULT_CSYMBOL = "AAAAA";
    private static final String UPDATED_CSYMBOL = "BBBBB";
    private static final String DEFAULT_SECUSE = "AAAAA";
    private static final String UPDATED_SECUSE = "BBBBB";
    private static final String DEFAULT_BCSYMBOL = "AAAAA";
    private static final String UPDATED_BCSYMBOL = "BBBBB";
    private static final String DEFAULT_CFNAME = "AAAAA";
    private static final String UPDATED_CFNAME = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    @Inject
    private SetupCompanyRepository setupCompanyRepository;

    @Inject
    private SetupCompanySearchRepository setupCompanySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSetupCompanyMockMvc;

    private SetupCompany setupCompany;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SetupCompanyResource setupCompanyResource = new SetupCompanyResource();
        ReflectionTestUtils.setField(setupCompanyResource, "setupCompanySearchRepository", setupCompanySearchRepository);
        ReflectionTestUtils.setField(setupCompanyResource, "setupCompanyRepository", setupCompanyRepository);
        this.restSetupCompanyMockMvc = MockMvcBuilders.standaloneSetup(setupCompanyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        setupCompany = new SetupCompany();
        setupCompany.setCompanyCode(DEFAULT_COMPANY_CODE);
        setupCompany.setName(DEFAULT_NAME);
        setupCompany.setAdd1(DEFAULT_ADD1);
        setupCompany.setAdd2(DEFAULT_ADD2);
        setupCompany.setPhone1(DEFAULT_PHONE1);
        setupCompany.setPhone2(DEFAULT_PHONE2);
        setupCompany.setFax(DEFAULT_FAX);
        setupCompany.setEmail(DEFAULT_EMAIL);
        setupCompany.setVatno1(DEFAULT_VATNO1);
        setupCompany.setWeb(DEFAULT_WEB);
        setupCompany.setTin(DEFAULT_TIN);
        setupCompany.setCsymbol(DEFAULT_CSYMBOL);
        setupCompany.setSecuse(DEFAULT_SECUSE);
        setupCompany.setBcsymbol(DEFAULT_BCSYMBOL);
        setupCompany.setCfname(DEFAULT_CFNAME);
        setupCompany.setStatus(DEFAULT_STATUS);
        setupCompany.setCreatedDate(DEFAULT_CREATED_DATE);
        setupCompany.setUpdatedDate(DEFAULT_UPDATED_DATE);
        setupCompany.setCreatedBy(DEFAULT_CREATED_BY);
        setupCompany.setUpdatedBy(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createSetupCompany() throws Exception {
        int databaseSizeBeforeCreate = setupCompanyRepository.findAll().size();

        // Create the SetupCompany

        restSetupCompanyMockMvc.perform(post("/api/setupCompanys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupCompany)))
                .andExpect(status().isCreated());

        // Validate the SetupCompany in the database
        List<SetupCompany> setupCompanys = setupCompanyRepository.findAll();
        assertThat(setupCompanys).hasSize(databaseSizeBeforeCreate + 1);
        SetupCompany testSetupCompany = setupCompanys.get(setupCompanys.size() - 1);
        assertThat(testSetupCompany.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testSetupCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSetupCompany.getAdd1()).isEqualTo(DEFAULT_ADD1);
        assertThat(testSetupCompany.getAdd2()).isEqualTo(DEFAULT_ADD2);
        assertThat(testSetupCompany.getPhone1()).isEqualTo(DEFAULT_PHONE1);
        assertThat(testSetupCompany.getPhone2()).isEqualTo(DEFAULT_PHONE2);
        assertThat(testSetupCompany.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testSetupCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSetupCompany.getVatno1()).isEqualTo(DEFAULT_VATNO1);
        assertThat(testSetupCompany.getWeb()).isEqualTo(DEFAULT_WEB);
        assertThat(testSetupCompany.getTin()).isEqualTo(DEFAULT_TIN);
        assertThat(testSetupCompany.getCsymbol()).isEqualTo(DEFAULT_CSYMBOL);
        assertThat(testSetupCompany.getSecuse()).isEqualTo(DEFAULT_SECUSE);
        assertThat(testSetupCompany.getBcsymbol()).isEqualTo(DEFAULT_BCSYMBOL);
        assertThat(testSetupCompany.getCfname()).isEqualTo(DEFAULT_CFNAME);
        assertThat(testSetupCompany.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSetupCompany.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSetupCompany.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testSetupCompany.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSetupCompany.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void checkCompanyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = setupCompanyRepository.findAll().size();
        // set the field null
        setupCompany.setCompanyCode(null);

        // Create the SetupCompany, which fails.

        restSetupCompanyMockMvc.perform(post("/api/setupCompanys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupCompany)))
                .andExpect(status().isBadRequest());

        List<SetupCompany> setupCompanys = setupCompanyRepository.findAll();
        assertThat(setupCompanys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = setupCompanyRepository.findAll().size();
        // set the field null
        setupCompany.setName(null);

        // Create the SetupCompany, which fails.

        restSetupCompanyMockMvc.perform(post("/api/setupCompanys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupCompany)))
                .andExpect(status().isBadRequest());

        List<SetupCompany> setupCompanys = setupCompanyRepository.findAll();
        assertThat(setupCompanys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSetupCompanys() throws Exception {
        // Initialize the database
        setupCompanyRepository.saveAndFlush(setupCompany);

        // Get all the setupCompanys
        restSetupCompanyMockMvc.perform(get("/api/setupCompanys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(setupCompany.getId().intValue())))
                .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].add1").value(hasItem(DEFAULT_ADD1.toString())))
                .andExpect(jsonPath("$.[*].add2").value(hasItem(DEFAULT_ADD2.toString())))
                .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE1.toString())))
                .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE2.toString())))
                .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].vatno1").value(hasItem(DEFAULT_VATNO1.toString())))
                .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB.toString())))
                .andExpect(jsonPath("$.[*].tin").value(hasItem(DEFAULT_TIN.toString())))
                .andExpect(jsonPath("$.[*].csymbol").value(hasItem(DEFAULT_CSYMBOL.toString())))
                .andExpect(jsonPath("$.[*].secuse").value(hasItem(DEFAULT_SECUSE.toString())))
                .andExpect(jsonPath("$.[*].bcsymbol").value(hasItem(DEFAULT_BCSYMBOL.toString())))
                .andExpect(jsonPath("$.[*].cfname").value(hasItem(DEFAULT_CFNAME.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void getSetupCompany() throws Exception {
        // Initialize the database
        setupCompanyRepository.saveAndFlush(setupCompany);

        // Get the setupCompany
        restSetupCompanyMockMvc.perform(get("/api/setupCompanys/{id}", setupCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(setupCompany.getId().intValue()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.add1").value(DEFAULT_ADD1.toString()))
            .andExpect(jsonPath("$.add2").value(DEFAULT_ADD2.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE2.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.vatno1").value(DEFAULT_VATNO1.toString()))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB.toString()))
            .andExpect(jsonPath("$.tin").value(DEFAULT_TIN.toString()))
            .andExpect(jsonPath("$.csymbol").value(DEFAULT_CSYMBOL.toString()))
            .andExpect(jsonPath("$.secuse").value(DEFAULT_SECUSE.toString()))
            .andExpect(jsonPath("$.bcsymbol").value(DEFAULT_BCSYMBOL.toString()))
            .andExpect(jsonPath("$.cfname").value(DEFAULT_CFNAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingSetupCompany() throws Exception {
        // Get the setupCompany
        restSetupCompanyMockMvc.perform(get("/api/setupCompanys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetupCompany() throws Exception {
        // Initialize the database
        setupCompanyRepository.saveAndFlush(setupCompany);

		int databaseSizeBeforeUpdate = setupCompanyRepository.findAll().size();

        // Update the setupCompany
        setupCompany.setCompanyCode(UPDATED_COMPANY_CODE);
        setupCompany.setName(UPDATED_NAME);
        setupCompany.setAdd1(UPDATED_ADD1);
        setupCompany.setAdd2(UPDATED_ADD2);
        setupCompany.setPhone1(UPDATED_PHONE1);
        setupCompany.setPhone2(UPDATED_PHONE2);
        setupCompany.setFax(UPDATED_FAX);
        setupCompany.setEmail(UPDATED_EMAIL);
        setupCompany.setVatno1(UPDATED_VATNO1);
        setupCompany.setWeb(UPDATED_WEB);
        setupCompany.setTin(UPDATED_TIN);
        setupCompany.setCsymbol(UPDATED_CSYMBOL);
        setupCompany.setSecuse(UPDATED_SECUSE);
        setupCompany.setBcsymbol(UPDATED_BCSYMBOL);
        setupCompany.setCfname(UPDATED_CFNAME);
        setupCompany.setStatus(UPDATED_STATUS);
        setupCompany.setCreatedDate(UPDATED_CREATED_DATE);
        setupCompany.setUpdatedDate(UPDATED_UPDATED_DATE);
        setupCompany.setCreatedBy(UPDATED_CREATED_BY);
        setupCompany.setUpdatedBy(UPDATED_UPDATED_BY);

        restSetupCompanyMockMvc.perform(put("/api/setupCompanys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupCompany)))
                .andExpect(status().isOk());

        // Validate the SetupCompany in the database
        List<SetupCompany> setupCompanys = setupCompanyRepository.findAll();
        assertThat(setupCompanys).hasSize(databaseSizeBeforeUpdate);
        SetupCompany testSetupCompany = setupCompanys.get(setupCompanys.size() - 1);
        assertThat(testSetupCompany.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testSetupCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSetupCompany.getAdd1()).isEqualTo(UPDATED_ADD1);
        assertThat(testSetupCompany.getAdd2()).isEqualTo(UPDATED_ADD2);
        assertThat(testSetupCompany.getPhone1()).isEqualTo(UPDATED_PHONE1);
        assertThat(testSetupCompany.getPhone2()).isEqualTo(UPDATED_PHONE2);
        assertThat(testSetupCompany.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testSetupCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSetupCompany.getVatno1()).isEqualTo(UPDATED_VATNO1);
        assertThat(testSetupCompany.getWeb()).isEqualTo(UPDATED_WEB);
        assertThat(testSetupCompany.getTin()).isEqualTo(UPDATED_TIN);
        assertThat(testSetupCompany.getCsymbol()).isEqualTo(UPDATED_CSYMBOL);
        assertThat(testSetupCompany.getSecuse()).isEqualTo(UPDATED_SECUSE);
        assertThat(testSetupCompany.getBcsymbol()).isEqualTo(UPDATED_BCSYMBOL);
        assertThat(testSetupCompany.getCfname()).isEqualTo(UPDATED_CFNAME);
        assertThat(testSetupCompany.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSetupCompany.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSetupCompany.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testSetupCompany.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSetupCompany.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void deleteSetupCompany() throws Exception {
        // Initialize the database
        setupCompanyRepository.saveAndFlush(setupCompany);

		int databaseSizeBeforeDelete = setupCompanyRepository.findAll().size();

        // Get the setupCompany
        restSetupCompanyMockMvc.perform(delete("/api/setupCompanys/{id}", setupCompany.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SetupCompany> setupCompanys = setupCompanyRepository.findAll();
        assertThat(setupCompanys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
