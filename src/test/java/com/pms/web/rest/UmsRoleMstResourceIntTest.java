package com.pms.web.rest;

import com.pms.Application;
import com.pms.domain.UmsRoleMst;
import com.pms.repository.UmsRoleMstRepository;
import com.pms.repository.search.UmsRoleMstSearchRepository;

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
 * Test class for the UmsRoleMstResource REST controller.
 *
 * @see UmsRoleMstResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UmsRoleMstResourceIntTest {

    private static final String DEFAULT_ROLE_NAME = "AAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBB";
    private static final String DEFAULT_ROLE_DESC = "AAAAA";
    private static final String UPDATED_ROLE_DESC = "BBBBB";

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
    private UmsRoleMstRepository umsRoleMstRepository;

    @Inject
    private UmsRoleMstSearchRepository umsRoleMstSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmsRoleMstMockMvc;

    private UmsRoleMst umsRoleMst;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmsRoleMstResource umsRoleMstResource = new UmsRoleMstResource();
        ReflectionTestUtils.setField(umsRoleMstResource, "umsRoleMstSearchRepository", umsRoleMstSearchRepository);
        ReflectionTestUtils.setField(umsRoleMstResource, "umsRoleMstRepository", umsRoleMstRepository);
        this.restUmsRoleMstMockMvc = MockMvcBuilders.standaloneSetup(umsRoleMstResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umsRoleMst = new UmsRoleMst();
        umsRoleMst.setRoleName(DEFAULT_ROLE_NAME);
        umsRoleMst.setRoleDesc(DEFAULT_ROLE_DESC);
        umsRoleMst.setStatus(DEFAULT_STATUS);
        umsRoleMst.setCreatedDate(DEFAULT_CREATED_DATE);
        umsRoleMst.setUpdatedDate(DEFAULT_UPDATED_DATE);
        umsRoleMst.setCreatedBy(DEFAULT_CREATED_BY);
        umsRoleMst.setUpdatedBy(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createUmsRoleMst() throws Exception {
        int databaseSizeBeforeCreate = umsRoleMstRepository.findAll().size();

        // Create the UmsRoleMst

        restUmsRoleMstMockMvc.perform(post("/api/umsRoleMsts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umsRoleMst)))
                .andExpect(status().isCreated());

        // Validate the UmsRoleMst in the database
        List<UmsRoleMst> umsRoleMsts = umsRoleMstRepository.findAll();
        assertThat(umsRoleMsts).hasSize(databaseSizeBeforeCreate + 1);
        UmsRoleMst testUmsRoleMst = umsRoleMsts.get(umsRoleMsts.size() - 1);
        assertThat(testUmsRoleMst.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testUmsRoleMst.getRoleDesc()).isEqualTo(DEFAULT_ROLE_DESC);
        assertThat(testUmsRoleMst.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUmsRoleMst.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUmsRoleMst.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testUmsRoleMst.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUmsRoleMst.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void checkRoleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = umsRoleMstRepository.findAll().size();
        // set the field null
        umsRoleMst.setRoleName(null);

        // Create the UmsRoleMst, which fails.

        restUmsRoleMstMockMvc.perform(post("/api/umsRoleMsts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umsRoleMst)))
                .andExpect(status().isBadRequest());

        List<UmsRoleMst> umsRoleMsts = umsRoleMstRepository.findAll();
        assertThat(umsRoleMsts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = umsRoleMstRepository.findAll().size();
        // set the field null
        umsRoleMst.setRoleDesc(null);

        // Create the UmsRoleMst, which fails.

        restUmsRoleMstMockMvc.perform(post("/api/umsRoleMsts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umsRoleMst)))
                .andExpect(status().isBadRequest());

        List<UmsRoleMst> umsRoleMsts = umsRoleMstRepository.findAll();
        assertThat(umsRoleMsts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUmsRoleMsts() throws Exception {
        // Initialize the database
        umsRoleMstRepository.saveAndFlush(umsRoleMst);

        // Get all the umsRoleMsts
        restUmsRoleMstMockMvc.perform(get("/api/umsRoleMsts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umsRoleMst.getId().intValue())))
                .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].roleDesc").value(hasItem(DEFAULT_ROLE_DESC.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void getUmsRoleMst() throws Exception {
        // Initialize the database
        umsRoleMstRepository.saveAndFlush(umsRoleMst);

        // Get the umsRoleMst
        restUmsRoleMstMockMvc.perform(get("/api/umsRoleMsts/{id}", umsRoleMst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umsRoleMst.getId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.roleDesc").value(DEFAULT_ROLE_DESC.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingUmsRoleMst() throws Exception {
        // Get the umsRoleMst
        restUmsRoleMstMockMvc.perform(get("/api/umsRoleMsts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmsRoleMst() throws Exception {
        // Initialize the database
        umsRoleMstRepository.saveAndFlush(umsRoleMst);

		int databaseSizeBeforeUpdate = umsRoleMstRepository.findAll().size();

        // Update the umsRoleMst
        umsRoleMst.setRoleName(UPDATED_ROLE_NAME);
        umsRoleMst.setRoleDesc(UPDATED_ROLE_DESC);
        umsRoleMst.setStatus(UPDATED_STATUS);
        umsRoleMst.setCreatedDate(UPDATED_CREATED_DATE);
        umsRoleMst.setUpdatedDate(UPDATED_UPDATED_DATE);
        umsRoleMst.setCreatedBy(UPDATED_CREATED_BY);
        umsRoleMst.setUpdatedBy(UPDATED_UPDATED_BY);

        restUmsRoleMstMockMvc.perform(put("/api/umsRoleMsts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umsRoleMst)))
                .andExpect(status().isOk());

        // Validate the UmsRoleMst in the database
        List<UmsRoleMst> umsRoleMsts = umsRoleMstRepository.findAll();
        assertThat(umsRoleMsts).hasSize(databaseSizeBeforeUpdate);
        UmsRoleMst testUmsRoleMst = umsRoleMsts.get(umsRoleMsts.size() - 1);
        assertThat(testUmsRoleMst.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testUmsRoleMst.getRoleDesc()).isEqualTo(UPDATED_ROLE_DESC);
        assertThat(testUmsRoleMst.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUmsRoleMst.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUmsRoleMst.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testUmsRoleMst.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUmsRoleMst.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void deleteUmsRoleMst() throws Exception {
        // Initialize the database
        umsRoleMstRepository.saveAndFlush(umsRoleMst);

		int databaseSizeBeforeDelete = umsRoleMstRepository.findAll().size();

        // Get the umsRoleMst
        restUmsRoleMstMockMvc.perform(delete("/api/umsRoleMsts/{id}", umsRoleMst.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmsRoleMst> umsRoleMsts = umsRoleMstRepository.findAll();
        assertThat(umsRoleMsts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
