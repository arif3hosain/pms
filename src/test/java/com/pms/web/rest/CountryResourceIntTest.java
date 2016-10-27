package com.pms.web.rest;

import com.pms.Application;
import com.pms.domain.Country;
import com.pms.repository.CountryRepository;
import com.pms.repository.search.CountrySearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CountryResource REST controller.
 *
 * @see CountryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CountryResourceIntTest {

    private static final String DEFAULT_COUNTRY_CODE = "AAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBB";
    private static final String DEFAULT_ISO_CODE2 = "AAAAA";
    private static final String UPDATED_ISO_CODE2 = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CONTINENT = "AAAAA";
    private static final String UPDATED_CONTINENT = "BBBBB";
    private static final String DEFAULT_REGION = "AAAAA";
    private static final String UPDATED_REGION = "BBBBB";
    private static final String DEFAULT_SURFACE_AREA = "AAAAA";
    private static final String UPDATED_SURFACE_AREA = "BBBBB";
    private static final String DEFAULT_CAPITAL = "AAAAA";
    private static final String UPDATED_CAPITAL = "BBBBB";
    private static final String DEFAULT_HEAD_OF_STATE = "AAAAA";
    private static final String UPDATED_HEAD_OF_STATE = "BBBBB";
    private static final String DEFAULT_CALLING_CODE = "AAAAA";
    private static final String UPDATED_CALLING_CODE = "BBBBB";

    @Inject
    private CountryRepository countryRepository;

    @Inject
    private CountrySearchRepository countrySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCountryMockMvc;

    private Country country;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CountryResource countryResource = new CountryResource();
        ReflectionTestUtils.setField(countryResource, "countrySearchRepository", countrySearchRepository);
        ReflectionTestUtils.setField(countryResource, "countryRepository", countryRepository);
        this.restCountryMockMvc = MockMvcBuilders.standaloneSetup(countryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        country = new Country();
        country.setCountryCode(DEFAULT_COUNTRY_CODE);
        country.setIsoCode2(DEFAULT_ISO_CODE2);
        country.setName(DEFAULT_NAME);
        country.setContinent(DEFAULT_CONTINENT);
        country.setRegion(DEFAULT_REGION);
        country.setSurfaceArea(DEFAULT_SURFACE_AREA);
        country.setCapital(DEFAULT_CAPITAL);
        country.setHeadOfState(DEFAULT_HEAD_OF_STATE);
        country.setCallingCode(DEFAULT_CALLING_CODE);
    }

    @Test
    @Transactional
    public void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country

        restCountryMockMvc.perform(post("/api/countrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(country)))
                .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countrys.get(countrys.size() - 1);
        assertThat(testCountry.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testCountry.getIsoCode2()).isEqualTo(DEFAULT_ISO_CODE2);
        assertThat(testCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountry.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testCountry.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testCountry.getSurfaceArea()).isEqualTo(DEFAULT_SURFACE_AREA);
        assertThat(testCountry.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testCountry.getHeadOfState()).isEqualTo(DEFAULT_HEAD_OF_STATE);
        assertThat(testCountry.getCallingCode()).isEqualTo(DEFAULT_CALLING_CODE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setName(null);

        // Create the Country, which fails.

        restCountryMockMvc.perform(post("/api/countrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(country)))
                .andExpect(status().isBadRequest());

        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountrys() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countrys
        restCountryMockMvc.perform(get("/api/countrys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
                .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
                .andExpect(jsonPath("$.[*].isoCode2").value(hasItem(DEFAULT_ISO_CODE2.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT.toString())))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
                .andExpect(jsonPath("$.[*].surfaceArea").value(hasItem(DEFAULT_SURFACE_AREA.toString())))
                .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.toString())))
                .andExpect(jsonPath("$.[*].headOfState").value(hasItem(DEFAULT_HEAD_OF_STATE.toString())))
                .andExpect(jsonPath("$.[*].callingCode").value(hasItem(DEFAULT_CALLING_CODE.toString())));
    }

    @Test
    @Transactional
    public void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc.perform(get("/api/countrys/{id}", country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(country.getId().intValue()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.isoCode2").value(DEFAULT_ISO_CODE2.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.continent").value(DEFAULT_CONTINENT.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.surfaceArea").value(DEFAULT_SURFACE_AREA.toString()))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL.toString()))
            .andExpect(jsonPath("$.headOfState").value(DEFAULT_HEAD_OF_STATE.toString()))
            .andExpect(jsonPath("$.callingCode").value(DEFAULT_CALLING_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get("/api/countrys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

		int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        country.setCountryCode(UPDATED_COUNTRY_CODE);
        country.setIsoCode2(UPDATED_ISO_CODE2);
        country.setName(UPDATED_NAME);
        country.setContinent(UPDATED_CONTINENT);
        country.setRegion(UPDATED_REGION);
        country.setSurfaceArea(UPDATED_SURFACE_AREA);
        country.setCapital(UPDATED_CAPITAL);
        country.setHeadOfState(UPDATED_HEAD_OF_STATE);
        country.setCallingCode(UPDATED_CALLING_CODE);

        restCountryMockMvc.perform(put("/api/countrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(country)))
                .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countrys.get(countrys.size() - 1);
        assertThat(testCountry.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testCountry.getIsoCode2()).isEqualTo(UPDATED_ISO_CODE2);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testCountry.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testCountry.getSurfaceArea()).isEqualTo(UPDATED_SURFACE_AREA);
        assertThat(testCountry.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testCountry.getHeadOfState()).isEqualTo(UPDATED_HEAD_OF_STATE);
        assertThat(testCountry.getCallingCode()).isEqualTo(UPDATED_CALLING_CODE);
    }

    @Test
    @Transactional
    public void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

		int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Get the country
        restCountryMockMvc.perform(delete("/api/countrys/{id}", country.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Country> countrys = countryRepository.findAll();
        assertThat(countrys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
