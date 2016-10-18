package com.pms.web.rest;

import com.pms.Application;
import com.pms.domain.UmsMenuMain;
import com.pms.repository.UmsMenuMainRepository;
import com.pms.repository.search.UmsMenuMainSearchRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the UmsMenuMainResource REST controller.
 *
 * @see UmsMenuMainResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UmsMenuMainResourceIntTest {

    private static final String DEFAULT_MENU_NAME = "AAAAA";
    private static final String UPDATED_MENU_NAME = "BBBBB";
    private static final String DEFAULT_MENU_TYPE = "AAAAA";
    private static final String UPDATED_MENU_TYPE = "BBBBB";
    private static final String DEFAULT_RUN_FILE_NAME = "AAAAA";
    private static final String UPDATED_RUN_FILE_NAME = "BBBBB";

    private static final Integer DEFAULT_MENU_SL = 1;
    private static final Integer UPDATED_MENU_SL = 2;

    private static final Integer DEFAULT_PARENT_ID = 1;
    private static final Integer UPDATED_PARENT_ID = 2;
    private static final String DEFAULT_HOME_DIR = "AAAAA";
    private static final String UPDATED_HOME_DIR = "BBBBB";
    private static final String DEFAULT_REMARKS = "AAAAA";
    private static final String UPDATED_REMARKS = "BBBBB";

    private static final Integer DEFAULT_MENU_LEVEL = 1;
    private static final Integer UPDATED_MENU_LEVEL = 2;

    private static final byte[] DEFAULT_PREV_ICON_LEVEL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PREV_ICON_LEVEL = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PREV_ICON_LEVEL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PREV_ICON_LEVEL_CONTENT_TYPE = "image/png";

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
    private UmsMenuMainRepository umsMenuMainRepository;

    @Inject
    private UmsMenuMainSearchRepository umsMenuMainSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmsMenuMainMockMvc;

    private UmsMenuMain umsMenuMain;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmsMenuMainResource umsMenuMainResource = new UmsMenuMainResource();
        ReflectionTestUtils.setField(umsMenuMainResource, "umsMenuMainSearchRepository", umsMenuMainSearchRepository);
        ReflectionTestUtils.setField(umsMenuMainResource, "umsMenuMainRepository", umsMenuMainRepository);
        this.restUmsMenuMainMockMvc = MockMvcBuilders.standaloneSetup(umsMenuMainResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umsMenuMain = new UmsMenuMain();
        umsMenuMain.setMenuName(DEFAULT_MENU_NAME);
        umsMenuMain.setMenuType(DEFAULT_MENU_TYPE);
        umsMenuMain.setRunFileName(DEFAULT_RUN_FILE_NAME);
        umsMenuMain.setMenuSl(DEFAULT_MENU_SL);
        umsMenuMain.setParentId(DEFAULT_PARENT_ID);
        umsMenuMain.setHomeDir(DEFAULT_HOME_DIR);
        umsMenuMain.setRemarks(DEFAULT_REMARKS);
        umsMenuMain.setMenuLevel(DEFAULT_MENU_LEVEL);
        umsMenuMain.setPrevIconLevel(DEFAULT_PREV_ICON_LEVEL);
        umsMenuMain.setPrevIconLevelContentType(DEFAULT_PREV_ICON_LEVEL_CONTENT_TYPE);
        umsMenuMain.setStatus(DEFAULT_STATUS);
        umsMenuMain.setCreatedDate(DEFAULT_CREATED_DATE);
        umsMenuMain.setUpdatedDate(DEFAULT_UPDATED_DATE);
        umsMenuMain.setCreatedBy(DEFAULT_CREATED_BY);
        umsMenuMain.setUpdatedBy(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createUmsMenuMain() throws Exception {
        int databaseSizeBeforeCreate = umsMenuMainRepository.findAll().size();

        // Create the UmsMenuMain

        restUmsMenuMainMockMvc.perform(post("/api/umsMenuMains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umsMenuMain)))
                .andExpect(status().isCreated());

        // Validate the UmsMenuMain in the database
        List<UmsMenuMain> umsMenuMains = umsMenuMainRepository.findAll();
        assertThat(umsMenuMains).hasSize(databaseSizeBeforeCreate + 1);
        UmsMenuMain testUmsMenuMain = umsMenuMains.get(umsMenuMains.size() - 1);
        assertThat(testUmsMenuMain.getMenuName()).isEqualTo(DEFAULT_MENU_NAME);
        assertThat(testUmsMenuMain.getMenuType()).isEqualTo(DEFAULT_MENU_TYPE);
        assertThat(testUmsMenuMain.getRunFileName()).isEqualTo(DEFAULT_RUN_FILE_NAME);
        assertThat(testUmsMenuMain.getMenuSl()).isEqualTo(DEFAULT_MENU_SL);
        assertThat(testUmsMenuMain.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testUmsMenuMain.getHomeDir()).isEqualTo(DEFAULT_HOME_DIR);
        assertThat(testUmsMenuMain.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testUmsMenuMain.getMenuLevel()).isEqualTo(DEFAULT_MENU_LEVEL);
        assertThat(testUmsMenuMain.getPrevIconLevel()).isEqualTo(DEFAULT_PREV_ICON_LEVEL);
        assertThat(testUmsMenuMain.getPrevIconLevelContentType()).isEqualTo(DEFAULT_PREV_ICON_LEVEL_CONTENT_TYPE);
        assertThat(testUmsMenuMain.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUmsMenuMain.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUmsMenuMain.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testUmsMenuMain.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUmsMenuMain.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void checkMenuNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = umsMenuMainRepository.findAll().size();
        // set the field null
        umsMenuMain.setMenuName(null);

        // Create the UmsMenuMain, which fails.

        restUmsMenuMainMockMvc.perform(post("/api/umsMenuMains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umsMenuMain)))
                .andExpect(status().isBadRequest());

        List<UmsMenuMain> umsMenuMains = umsMenuMainRepository.findAll();
        assertThat(umsMenuMains).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMenuTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = umsMenuMainRepository.findAll().size();
        // set the field null
        umsMenuMain.setMenuType(null);

        // Create the UmsMenuMain, which fails.

        restUmsMenuMainMockMvc.perform(post("/api/umsMenuMains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umsMenuMain)))
                .andExpect(status().isBadRequest());

        List<UmsMenuMain> umsMenuMains = umsMenuMainRepository.findAll();
        assertThat(umsMenuMains).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUmsMenuMains() throws Exception {
        // Initialize the database
        umsMenuMainRepository.saveAndFlush(umsMenuMain);

        // Get all the umsMenuMains
        restUmsMenuMainMockMvc.perform(get("/api/umsMenuMains?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umsMenuMain.getId().intValue())))
                .andExpect(jsonPath("$.[*].menuName").value(hasItem(DEFAULT_MENU_NAME.toString())))
                .andExpect(jsonPath("$.[*].menuType").value(hasItem(DEFAULT_MENU_TYPE.toString())))
                .andExpect(jsonPath("$.[*].RunFileName").value(hasItem(DEFAULT_RUN_FILE_NAME.toString())))
                .andExpect(jsonPath("$.[*].MenuSl").value(hasItem(DEFAULT_MENU_SL)))
                .andExpect(jsonPath("$.[*].ParentId").value(hasItem(DEFAULT_PARENT_ID)))
                .andExpect(jsonPath("$.[*].homeDir").value(hasItem(DEFAULT_HOME_DIR.toString())))
                .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
                .andExpect(jsonPath("$.[*].menuLevel").value(hasItem(DEFAULT_MENU_LEVEL)))
                .andExpect(jsonPath("$.[*].prevIconLevelContentType").value(hasItem(DEFAULT_PREV_ICON_LEVEL_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].prevIconLevel").value(hasItem(Base64Utils.encodeToString(DEFAULT_PREV_ICON_LEVEL))))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void getUmsMenuMain() throws Exception {
        // Initialize the database
        umsMenuMainRepository.saveAndFlush(umsMenuMain);

        // Get the umsMenuMain
        restUmsMenuMainMockMvc.perform(get("/api/umsMenuMains/{id}", umsMenuMain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umsMenuMain.getId().intValue()))
            .andExpect(jsonPath("$.menuName").value(DEFAULT_MENU_NAME.toString()))
            .andExpect(jsonPath("$.menuType").value(DEFAULT_MENU_TYPE.toString()))
            .andExpect(jsonPath("$.RunFileName").value(DEFAULT_RUN_FILE_NAME.toString()))
            .andExpect(jsonPath("$.MenuSl").value(DEFAULT_MENU_SL))
            .andExpect(jsonPath("$.ParentId").value(DEFAULT_PARENT_ID))
            .andExpect(jsonPath("$.homeDir").value(DEFAULT_HOME_DIR.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.menuLevel").value(DEFAULT_MENU_LEVEL))
            .andExpect(jsonPath("$.prevIconLevelContentType").value(DEFAULT_PREV_ICON_LEVEL_CONTENT_TYPE))
            .andExpect(jsonPath("$.prevIconLevel").value(Base64Utils.encodeToString(DEFAULT_PREV_ICON_LEVEL)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingUmsMenuMain() throws Exception {
        // Get the umsMenuMain
        restUmsMenuMainMockMvc.perform(get("/api/umsMenuMains/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmsMenuMain() throws Exception {
        // Initialize the database
        umsMenuMainRepository.saveAndFlush(umsMenuMain);

		int databaseSizeBeforeUpdate = umsMenuMainRepository.findAll().size();

        // Update the umsMenuMain
        umsMenuMain.setMenuName(UPDATED_MENU_NAME);
        umsMenuMain.setMenuType(UPDATED_MENU_TYPE);
        umsMenuMain.setRunFileName(UPDATED_RUN_FILE_NAME);
        umsMenuMain.setMenuSl(UPDATED_MENU_SL);
        umsMenuMain.setParentId(UPDATED_PARENT_ID);
        umsMenuMain.setHomeDir(UPDATED_HOME_DIR);
        umsMenuMain.setRemarks(UPDATED_REMARKS);
        umsMenuMain.setMenuLevel(UPDATED_MENU_LEVEL);
        umsMenuMain.setPrevIconLevel(UPDATED_PREV_ICON_LEVEL);
        umsMenuMain.setPrevIconLevelContentType(UPDATED_PREV_ICON_LEVEL_CONTENT_TYPE);
        umsMenuMain.setStatus(UPDATED_STATUS);
        umsMenuMain.setCreatedDate(UPDATED_CREATED_DATE);
        umsMenuMain.setUpdatedDate(UPDATED_UPDATED_DATE);
        umsMenuMain.setCreatedBy(UPDATED_CREATED_BY);
        umsMenuMain.setUpdatedBy(UPDATED_UPDATED_BY);

        restUmsMenuMainMockMvc.perform(put("/api/umsMenuMains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umsMenuMain)))
                .andExpect(status().isOk());

        // Validate the UmsMenuMain in the database
        List<UmsMenuMain> umsMenuMains = umsMenuMainRepository.findAll();
        assertThat(umsMenuMains).hasSize(databaseSizeBeforeUpdate);
        UmsMenuMain testUmsMenuMain = umsMenuMains.get(umsMenuMains.size() - 1);
        assertThat(testUmsMenuMain.getMenuName()).isEqualTo(UPDATED_MENU_NAME);
        assertThat(testUmsMenuMain.getMenuType()).isEqualTo(UPDATED_MENU_TYPE);
        assertThat(testUmsMenuMain.getRunFileName()).isEqualTo(UPDATED_RUN_FILE_NAME);
        assertThat(testUmsMenuMain.getMenuSl()).isEqualTo(UPDATED_MENU_SL);
        assertThat(testUmsMenuMain.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testUmsMenuMain.getHomeDir()).isEqualTo(UPDATED_HOME_DIR);
        assertThat(testUmsMenuMain.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testUmsMenuMain.getMenuLevel()).isEqualTo(UPDATED_MENU_LEVEL);
        assertThat(testUmsMenuMain.getPrevIconLevel()).isEqualTo(UPDATED_PREV_ICON_LEVEL);
        assertThat(testUmsMenuMain.getPrevIconLevelContentType()).isEqualTo(UPDATED_PREV_ICON_LEVEL_CONTENT_TYPE);
        assertThat(testUmsMenuMain.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUmsMenuMain.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUmsMenuMain.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testUmsMenuMain.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUmsMenuMain.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void deleteUmsMenuMain() throws Exception {
        // Initialize the database
        umsMenuMainRepository.saveAndFlush(umsMenuMain);

		int databaseSizeBeforeDelete = umsMenuMainRepository.findAll().size();

        // Get the umsMenuMain
        restUmsMenuMainMockMvc.perform(delete("/api/umsMenuMains/{id}", umsMenuMain.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmsMenuMain> umsMenuMains = umsMenuMainRepository.findAll();
        assertThat(umsMenuMains).hasSize(databaseSizeBeforeDelete - 1);
    }
}
