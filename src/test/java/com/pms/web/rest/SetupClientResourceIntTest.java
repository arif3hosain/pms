package com.pms.web.rest;

import com.pms.Application;
import com.pms.domain.SetupClient;
import com.pms.repository.SetupClientRepository;
import com.pms.repository.search.SetupClientSearchRepository;

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

import com.pms.domain.enumeration.ClientType;

/**
 * Test class for the SetupClientResource REST controller.
 *
 * @see SetupClientResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SetupClientResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_WEBSITE = "AAAAA";
    private static final String UPDATED_WEBSITE = "BBBBB";
    private static final String DEFAULT_INDUSTRY_NAME = "AAAAA";
    private static final String UPDATED_INDUSTRY_NAME = "BBBBB";


    private static final ClientType DEFAULT_CLIENT_TYPE = ClientType.New;
    private static final ClientType UPDATED_CLIENT_TYPE = ClientType.Existing;
    private static final String DEFAULT_CUST_VAT_CHAL_CODE = "AAAAA";
    private static final String UPDATED_CUST_VAT_CHAL_CODE = "BBBBB";
    private static final String DEFAULT_CUST_VAT_REG_NO = "AAAAA";
    private static final String UPDATED_CUST_VAT_REG_NO = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CREATED_BY = "AAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBB";

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    @Inject
    private SetupClientRepository setupClientRepository;

    @Inject
    private SetupClientSearchRepository setupClientSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSetupClientMockMvc;

    private SetupClient setupClient;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SetupClientResource setupClientResource = new SetupClientResource();
        ReflectionTestUtils.setField(setupClientResource, "setupClientSearchRepository", setupClientSearchRepository);
        ReflectionTestUtils.setField(setupClientResource, "setupClientRepository", setupClientRepository);
        this.restSetupClientMockMvc = MockMvcBuilders.standaloneSetup(setupClientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        setupClient = new SetupClient();
        setupClient.setName(DEFAULT_NAME);
        setupClient.setWebsite(DEFAULT_WEBSITE);
        setupClient.setIndustryName(DEFAULT_INDUSTRY_NAME);
        setupClient.setClientType(DEFAULT_CLIENT_TYPE);
        setupClient.setCustVatChalCode(DEFAULT_CUST_VAT_CHAL_CODE);
        setupClient.setCustVatRegNo(DEFAULT_CUST_VAT_REG_NO);
        setupClient.setStatus(DEFAULT_STATUS);
        setupClient.setCreatedDate(DEFAULT_CREATED_DATE);
        setupClient.setUpdatedDate(DEFAULT_UPDATED_DATE);
        setupClient.setCreatedBy(DEFAULT_CREATED_BY);
        setupClient.setUpdatedBy(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createSetupClient() throws Exception {
        int databaseSizeBeforeCreate = setupClientRepository.findAll().size();

        // Create the SetupClient

        restSetupClientMockMvc.perform(post("/api/setupClients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupClient)))
                .andExpect(status().isCreated());

        // Validate the SetupClient in the database
        List<SetupClient> setupClients = setupClientRepository.findAll();
        assertThat(setupClients).hasSize(databaseSizeBeforeCreate + 1);
        SetupClient testSetupClient = setupClients.get(setupClients.size() - 1);
        assertThat(testSetupClient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSetupClient.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testSetupClient.getIndustryName()).isEqualTo(DEFAULT_INDUSTRY_NAME);
        assertThat(testSetupClient.getClientType()).isEqualTo(DEFAULT_CLIENT_TYPE);
        assertThat(testSetupClient.getCustVatChalCode()).isEqualTo(DEFAULT_CUST_VAT_CHAL_CODE);
        assertThat(testSetupClient.getCustVatRegNo()).isEqualTo(DEFAULT_CUST_VAT_REG_NO);
        assertThat(testSetupClient.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSetupClient.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSetupClient.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testSetupClient.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSetupClient.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = setupClientRepository.findAll().size();
        // set the field null
        setupClient.setName(null);

        // Create the SetupClient, which fails.

        restSetupClientMockMvc.perform(post("/api/setupClients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupClient)))
                .andExpect(status().isBadRequest());

        List<SetupClient> setupClients = setupClientRepository.findAll();
        assertThat(setupClients).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndustryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = setupClientRepository.findAll().size();
        // set the field null
        setupClient.setIndustryName(null);

        // Create the SetupClient, which fails.

        restSetupClientMockMvc.perform(post("/api/setupClients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupClient)))
                .andExpect(status().isBadRequest());

        List<SetupClient> setupClients = setupClientRepository.findAll();
        assertThat(setupClients).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSetupClients() throws Exception {
        // Initialize the database
        setupClientRepository.saveAndFlush(setupClient);

        // Get all the setupClients
        restSetupClientMockMvc.perform(get("/api/setupClients?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(setupClient.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
                .andExpect(jsonPath("$.[*].industryName").value(hasItem(DEFAULT_INDUSTRY_NAME.toString())))
                .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].custVatChalCode").value(hasItem(DEFAULT_CUST_VAT_CHAL_CODE.toString())))
                .andExpect(jsonPath("$.[*].custVatRegNo").value(hasItem(DEFAULT_CUST_VAT_REG_NO.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void getSetupClient() throws Exception {
        // Initialize the database
        setupClientRepository.saveAndFlush(setupClient);

        // Get the setupClient
        restSetupClientMockMvc.perform(get("/api/setupClients/{id}", setupClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(setupClient.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.industryName").value(DEFAULT_INDUSTRY_NAME.toString()))
            .andExpect(jsonPath("$.clientType").value(DEFAULT_CLIENT_TYPE.toString()))
            .andExpect(jsonPath("$.custVatChalCode").value(DEFAULT_CUST_VAT_CHAL_CODE.toString()))
            .andExpect(jsonPath("$.custVatRegNo").value(DEFAULT_CUST_VAT_REG_NO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingSetupClient() throws Exception {
        // Get the setupClient
        restSetupClientMockMvc.perform(get("/api/setupClients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetupClient() throws Exception {
        // Initialize the database
        setupClientRepository.saveAndFlush(setupClient);

		int databaseSizeBeforeUpdate = setupClientRepository.findAll().size();

        // Update the setupClient
        setupClient.setName(UPDATED_NAME);
        setupClient.setWebsite(UPDATED_WEBSITE);
        setupClient.setIndustryName(UPDATED_INDUSTRY_NAME);
        setupClient.setClientType(UPDATED_CLIENT_TYPE);
        setupClient.setCustVatChalCode(UPDATED_CUST_VAT_CHAL_CODE);
        setupClient.setCustVatRegNo(UPDATED_CUST_VAT_REG_NO);
        setupClient.setStatus(UPDATED_STATUS);
        setupClient.setCreatedDate(UPDATED_CREATED_DATE);
        setupClient.setUpdatedDate(UPDATED_UPDATED_DATE);
        setupClient.setCreatedBy(UPDATED_CREATED_BY);
        setupClient.setUpdatedBy(UPDATED_UPDATED_BY);

        restSetupClientMockMvc.perform(put("/api/setupClients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupClient)))
                .andExpect(status().isOk());

        // Validate the SetupClient in the database
        List<SetupClient> setupClients = setupClientRepository.findAll();
        assertThat(setupClients).hasSize(databaseSizeBeforeUpdate);
        SetupClient testSetupClient = setupClients.get(setupClients.size() - 1);
        assertThat(testSetupClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSetupClient.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testSetupClient.getIndustryName()).isEqualTo(UPDATED_INDUSTRY_NAME);
        assertThat(testSetupClient.getClientType()).isEqualTo(UPDATED_CLIENT_TYPE);
        assertThat(testSetupClient.getCustVatChalCode()).isEqualTo(UPDATED_CUST_VAT_CHAL_CODE);
        assertThat(testSetupClient.getCustVatRegNo()).isEqualTo(UPDATED_CUST_VAT_REG_NO);
        assertThat(testSetupClient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSetupClient.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSetupClient.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testSetupClient.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSetupClient.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void deleteSetupClient() throws Exception {
        // Initialize the database
        setupClientRepository.saveAndFlush(setupClient);

		int databaseSizeBeforeDelete = setupClientRepository.findAll().size();

        // Get the setupClient
        restSetupClientMockMvc.perform(delete("/api/setupClients/{id}", setupClient.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SetupClient> setupClients = setupClientRepository.findAll();
        assertThat(setupClients).hasSize(databaseSizeBeforeDelete - 1);
    }
}
