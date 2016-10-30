package com.pms.web.rest;

import com.pms.Application;
import com.pms.domain.SetupCurrency;
import com.pms.repository.SetupCurrencyRepository;
import com.pms.repository.search.SetupCurrencySearchRepository;

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
 * Test class for the SetupCurrencyResource REST controller.
 *
 * @see SetupCurrencyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SetupCurrencyResourceIntTest {

    private static final String DEFAULT_CUR_CODE = "AAAAA";
    private static final String UPDATED_CUR_CODE = "BBBBB";
    private static final String DEFAULT_CURRENCY_NAME = "AAAAA";
    private static final String UPDATED_CURRENCY_NAME = "BBBBB";
    private static final String DEFAULT_CURR_SYMBOL = "AAAAA";
    private static final String UPDATED_CURR_SYMBOL = "BBBBB";
    private static final String DEFAULT_SYMBOL = "AAAAA";
    private static final String UPDATED_SYMBOL = "BBBBB";

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
    private SetupCurrencyRepository setupCurrencyRepository;

    @Inject
    private SetupCurrencySearchRepository setupCurrencySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSetupCurrencyMockMvc;

    private SetupCurrency setupCurrency;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SetupCurrencyResource setupCurrencyResource = new SetupCurrencyResource();
        ReflectionTestUtils.setField(setupCurrencyResource, "setupCurrencySearchRepository", setupCurrencySearchRepository);
        ReflectionTestUtils.setField(setupCurrencyResource, "setupCurrencyRepository", setupCurrencyRepository);
        this.restSetupCurrencyMockMvc = MockMvcBuilders.standaloneSetup(setupCurrencyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        setupCurrency = new SetupCurrency();
        setupCurrency.setCurCode(DEFAULT_CUR_CODE);
        setupCurrency.setCurrencyName(DEFAULT_CURRENCY_NAME);
        setupCurrency.setCurrSymbol(DEFAULT_CURR_SYMBOL);
        setupCurrency.setSymbol(DEFAULT_SYMBOL);
        setupCurrency.setStatus(DEFAULT_STATUS);
        setupCurrency.setCreatedDate(DEFAULT_CREATED_DATE);
        setupCurrency.setUpdatedDate(DEFAULT_UPDATED_DATE);
        setupCurrency.setCreatedBy(DEFAULT_CREATED_BY);
        setupCurrency.setUpdatedBy(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createSetupCurrency() throws Exception {
        int databaseSizeBeforeCreate = setupCurrencyRepository.findAll().size();

        // Create the SetupCurrency

        restSetupCurrencyMockMvc.perform(post("/api/setupCurrencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupCurrency)))
                .andExpect(status().isCreated());

        // Validate the SetupCurrency in the database
        List<SetupCurrency> setupCurrencys = setupCurrencyRepository.findAll();
        assertThat(setupCurrencys).hasSize(databaseSizeBeforeCreate + 1);
        SetupCurrency testSetupCurrency = setupCurrencys.get(setupCurrencys.size() - 1);
        assertThat(testSetupCurrency.getCurCode()).isEqualTo(DEFAULT_CUR_CODE);
        assertThat(testSetupCurrency.getCurrencyName()).isEqualTo(DEFAULT_CURRENCY_NAME);
        assertThat(testSetupCurrency.getCurrSymbol()).isEqualTo(DEFAULT_CURR_SYMBOL);
        assertThat(testSetupCurrency.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testSetupCurrency.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSetupCurrency.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSetupCurrency.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testSetupCurrency.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSetupCurrency.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void checkCurCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = setupCurrencyRepository.findAll().size();
        // set the field null
        setupCurrency.setCurCode(null);

        // Create the SetupCurrency, which fails.

        restSetupCurrencyMockMvc.perform(post("/api/setupCurrencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupCurrency)))
                .andExpect(status().isBadRequest());

        List<SetupCurrency> setupCurrencys = setupCurrencyRepository.findAll();
        assertThat(setupCurrencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = setupCurrencyRepository.findAll().size();
        // set the field null
        setupCurrency.setCurrencyName(null);

        // Create the SetupCurrency, which fails.

        restSetupCurrencyMockMvc.perform(post("/api/setupCurrencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupCurrency)))
                .andExpect(status().isBadRequest());

        List<SetupCurrency> setupCurrencys = setupCurrencyRepository.findAll();
        assertThat(setupCurrencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSetupCurrencys() throws Exception {
        // Initialize the database
        setupCurrencyRepository.saveAndFlush(setupCurrency);

        // Get all the setupCurrencys
        restSetupCurrencyMockMvc.perform(get("/api/setupCurrencys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(setupCurrency.getId().intValue())))
                .andExpect(jsonPath("$.[*].curCode").value(hasItem(DEFAULT_CUR_CODE.toString())))
                .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME.toString())))
                .andExpect(jsonPath("$.[*].currSymbol").value(hasItem(DEFAULT_CURR_SYMBOL.toString())))
                .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void getSetupCurrency() throws Exception {
        // Initialize the database
        setupCurrencyRepository.saveAndFlush(setupCurrency);

        // Get the setupCurrency
        restSetupCurrencyMockMvc.perform(get("/api/setupCurrencys/{id}", setupCurrency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(setupCurrency.getId().intValue()))
            .andExpect(jsonPath("$.curCode").value(DEFAULT_CUR_CODE.toString()))
            .andExpect(jsonPath("$.currencyName").value(DEFAULT_CURRENCY_NAME.toString()))
            .andExpect(jsonPath("$.currSymbol").value(DEFAULT_CURR_SYMBOL.toString()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingSetupCurrency() throws Exception {
        // Get the setupCurrency
        restSetupCurrencyMockMvc.perform(get("/api/setupCurrencys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetupCurrency() throws Exception {
        // Initialize the database
        setupCurrencyRepository.saveAndFlush(setupCurrency);

		int databaseSizeBeforeUpdate = setupCurrencyRepository.findAll().size();

        // Update the setupCurrency
        setupCurrency.setCurCode(UPDATED_CUR_CODE);
        setupCurrency.setCurrencyName(UPDATED_CURRENCY_NAME);
        setupCurrency.setCurrSymbol(UPDATED_CURR_SYMBOL);
        setupCurrency.setSymbol(UPDATED_SYMBOL);
        setupCurrency.setStatus(UPDATED_STATUS);
        setupCurrency.setCreatedDate(UPDATED_CREATED_DATE);
        setupCurrency.setUpdatedDate(UPDATED_UPDATED_DATE);
        setupCurrency.setCreatedBy(UPDATED_CREATED_BY);
        setupCurrency.setUpdatedBy(UPDATED_UPDATED_BY);

        restSetupCurrencyMockMvc.perform(put("/api/setupCurrencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupCurrency)))
                .andExpect(status().isOk());

        // Validate the SetupCurrency in the database
        List<SetupCurrency> setupCurrencys = setupCurrencyRepository.findAll();
        assertThat(setupCurrencys).hasSize(databaseSizeBeforeUpdate);
        SetupCurrency testSetupCurrency = setupCurrencys.get(setupCurrencys.size() - 1);
        assertThat(testSetupCurrency.getCurCode()).isEqualTo(UPDATED_CUR_CODE);
        assertThat(testSetupCurrency.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
        assertThat(testSetupCurrency.getCurrSymbol()).isEqualTo(UPDATED_CURR_SYMBOL);
        assertThat(testSetupCurrency.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testSetupCurrency.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSetupCurrency.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSetupCurrency.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testSetupCurrency.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSetupCurrency.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void deleteSetupCurrency() throws Exception {
        // Initialize the database
        setupCurrencyRepository.saveAndFlush(setupCurrency);

		int databaseSizeBeforeDelete = setupCurrencyRepository.findAll().size();

        // Get the setupCurrency
        restSetupCurrencyMockMvc.perform(delete("/api/setupCurrencys/{id}", setupCurrency.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SetupCurrency> setupCurrencys = setupCurrencyRepository.findAll();
        assertThat(setupCurrencys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
