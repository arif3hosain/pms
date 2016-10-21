package com.pms.web.rest;

import com.pms.Application;
import com.pms.domain.SetupEmployee;
import com.pms.repository.SetupEmployeeRepository;
import com.pms.repository.search.SetupEmployeeSearchRepository;

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
 * Test class for the SetupEmployeeResource REST controller.
 *
 * @see SetupEmployeeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SetupEmployeeResourceIntTest {

    private static final String DEFAULT_EMP_CODE = "AAAAA";
    private static final String UPDATED_EMP_CODE = "BBBBB";
    private static final String DEFAULT_EMPLOYEE_TITLE = "AAAAA";
    private static final String UPDATED_EMPLOYEE_TITLE = "BBBBB";
    private static final String DEFAULT_EMPLOYEE_NAME = "AAAAA";
    private static final String UPDATED_EMPLOYEE_NAME = "BBBBB";

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STD_WORKING_HOUR = 1;
    private static final Integer UPDATED_STD_WORKING_HOUR = 2;

    private static final Integer DEFAULT_OVER_TIME = 1;
    private static final Integer UPDATED_OVER_TIME = 2;
    private static final String DEFAULT_TIN_NO = "AAAAA";
    private static final String UPDATED_TIN_NO = "BBBBB";

    private static final Integer DEFAULT_DEPT_ID = 1;
    private static final Integer UPDATED_DEPT_ID = 2;

    private static final Integer DEFAULT_APPLICATION_ID = 1;
    private static final Integer UPDATED_APPLICATION_ID = 2;

    private static final byte[] DEFAULT_PROFILE_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROFILE_PICTURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PROFILE_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROFILE_PICTURE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_SUPERIOR_ID = 1;
    private static final Integer UPDATED_SUPERIOR_ID = 2;
    private static final String DEFAULT_DEG_CODE = "AAAAA";
    private static final String UPDATED_DEG_CODE = "BBBBB";
    private static final String DEFAULT_EMPLOYEE_TYPE = "AAAAA";
    private static final String UPDATED_EMPLOYEE_TYPE = "BBBBB";
    private static final String DEFAULT_BLOOD_GROUP = "AAAAA";
    private static final String UPDATED_BLOOD_GROUP = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final Integer DEFAULT_HOURLY_RATE = 1;
    private static final Integer UPDATED_HOURLY_RATE = 2;

    private static final Integer DEFAULT_FIXED_SALARY = 1;
    private static final Integer UPDATED_FIXED_SALARY = 2;

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

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
    private SetupEmployeeRepository setupEmployeeRepository;

    @Inject
    private SetupEmployeeSearchRepository setupEmployeeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSetupEmployeeMockMvc;

    private SetupEmployee setupEmployee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SetupEmployeeResource setupEmployeeResource = new SetupEmployeeResource();
        ReflectionTestUtils.setField(setupEmployeeResource, "setupEmployeeSearchRepository", setupEmployeeSearchRepository);
        ReflectionTestUtils.setField(setupEmployeeResource, "setupEmployeeRepository", setupEmployeeRepository);
        this.restSetupEmployeeMockMvc = MockMvcBuilders.standaloneSetup(setupEmployeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        setupEmployee = new SetupEmployee();
        setupEmployee.setEmpCode(DEFAULT_EMP_CODE);
        setupEmployee.setEmployeeTitle(DEFAULT_EMPLOYEE_TITLE);
        setupEmployee.setEmployeeName(DEFAULT_EMPLOYEE_NAME);
        setupEmployee.setJoiningDate(DEFAULT_JOINING_DATE);
        setupEmployee.setStdWorkingHour(DEFAULT_STD_WORKING_HOUR);
        setupEmployee.setOverTime(DEFAULT_OVER_TIME);
        setupEmployee.setTinNo(DEFAULT_TIN_NO);
        setupEmployee.setDeptId(DEFAULT_DEPT_ID);
        setupEmployee.setApplicationId(DEFAULT_APPLICATION_ID);
        setupEmployee.setProfilePicture(DEFAULT_PROFILE_PICTURE);
        setupEmployee.setProfilePictureContentType(DEFAULT_PROFILE_PICTURE_CONTENT_TYPE);
        setupEmployee.setSuperiorId(DEFAULT_SUPERIOR_ID);
        setupEmployee.setDegCode(DEFAULT_DEG_CODE);
        setupEmployee.setEmployeeType(DEFAULT_EMPLOYEE_TYPE);
        setupEmployee.setBloodGroup(DEFAULT_BLOOD_GROUP);
        setupEmployee.setPhone(DEFAULT_PHONE);
        setupEmployee.setAddress(DEFAULT_ADDRESS);
        setupEmployee.setHourlyRate(DEFAULT_HOURLY_RATE);
        setupEmployee.setFixedSalary(DEFAULT_FIXED_SALARY);
        setupEmployee.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
        setupEmployee.setStatus(DEFAULT_STATUS);
        setupEmployee.setCreatedDate(DEFAULT_CREATED_DATE);
        setupEmployee.setUpdatedDate(DEFAULT_UPDATED_DATE);
        setupEmployee.setCreatedBy(DEFAULT_CREATED_BY);
        setupEmployee.setUpdatedBy(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createSetupEmployee() throws Exception {
        int databaseSizeBeforeCreate = setupEmployeeRepository.findAll().size();

        // Create the SetupEmployee

        restSetupEmployeeMockMvc.perform(post("/api/setupEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupEmployee)))
                .andExpect(status().isCreated());

        // Validate the SetupEmployee in the database
        List<SetupEmployee> setupEmployees = setupEmployeeRepository.findAll();
        assertThat(setupEmployees).hasSize(databaseSizeBeforeCreate + 1);
        SetupEmployee testSetupEmployee = setupEmployees.get(setupEmployees.size() - 1);
        assertThat(testSetupEmployee.getEmpCode()).isEqualTo(DEFAULT_EMP_CODE);
        assertThat(testSetupEmployee.getEmployeeTitle()).isEqualTo(DEFAULT_EMPLOYEE_TITLE);
        assertThat(testSetupEmployee.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
        assertThat(testSetupEmployee.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testSetupEmployee.getStdWorkingHour()).isEqualTo(DEFAULT_STD_WORKING_HOUR);
        assertThat(testSetupEmployee.getOverTime()).isEqualTo(DEFAULT_OVER_TIME);
        assertThat(testSetupEmployee.getTinNo()).isEqualTo(DEFAULT_TIN_NO);
        assertThat(testSetupEmployee.getDeptId()).isEqualTo(DEFAULT_DEPT_ID);
        assertThat(testSetupEmployee.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testSetupEmployee.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
        assertThat(testSetupEmployee.getProfilePictureContentType()).isEqualTo(DEFAULT_PROFILE_PICTURE_CONTENT_TYPE);
        assertThat(testSetupEmployee.getSuperiorId()).isEqualTo(DEFAULT_SUPERIOR_ID);
        assertThat(testSetupEmployee.getDegCode()).isEqualTo(DEFAULT_DEG_CODE);
        assertThat(testSetupEmployee.getEmployeeType()).isEqualTo(DEFAULT_EMPLOYEE_TYPE);
        assertThat(testSetupEmployee.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testSetupEmployee.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSetupEmployee.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSetupEmployee.getHourlyRate()).isEqualTo(DEFAULT_HOURLY_RATE);
        assertThat(testSetupEmployee.getFixedSalary()).isEqualTo(DEFAULT_FIXED_SALARY);
        assertThat(testSetupEmployee.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testSetupEmployee.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSetupEmployee.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSetupEmployee.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testSetupEmployee.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSetupEmployee.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void checkEmpCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = setupEmployeeRepository.findAll().size();
        // set the field null
        setupEmployee.setEmpCode(null);

        // Create the SetupEmployee, which fails.

        restSetupEmployeeMockMvc.perform(post("/api/setupEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupEmployee)))
                .andExpect(status().isBadRequest());

        List<SetupEmployee> setupEmployees = setupEmployeeRepository.findAll();
        assertThat(setupEmployees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSetupEmployees() throws Exception {
        // Initialize the database
        setupEmployeeRepository.saveAndFlush(setupEmployee);

        // Get all the setupEmployees
        restSetupEmployeeMockMvc.perform(get("/api/setupEmployees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(setupEmployee.getId().intValue())))
                .andExpect(jsonPath("$.[*].empCode").value(hasItem(DEFAULT_EMP_CODE.toString())))
                .andExpect(jsonPath("$.[*].employeeTitle").value(hasItem(DEFAULT_EMPLOYEE_TITLE.toString())))
                .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME.toString())))
                .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
                .andExpect(jsonPath("$.[*].stdWorkingHour").value(hasItem(DEFAULT_STD_WORKING_HOUR)))
                .andExpect(jsonPath("$.[*].overTime").value(hasItem(DEFAULT_OVER_TIME)))
                .andExpect(jsonPath("$.[*].tinNo").value(hasItem(DEFAULT_TIN_NO.toString())))
                .andExpect(jsonPath("$.[*].deptId").value(hasItem(DEFAULT_DEPT_ID)))
                .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID)))
                .andExpect(jsonPath("$.[*].profilePictureContentType").value(hasItem(DEFAULT_PROFILE_PICTURE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE_PICTURE))))
                .andExpect(jsonPath("$.[*].superiorId").value(hasItem(DEFAULT_SUPERIOR_ID)))
                .andExpect(jsonPath("$.[*].degCode").value(hasItem(DEFAULT_DEG_CODE.toString())))
                .andExpect(jsonPath("$.[*].employeeType").value(hasItem(DEFAULT_EMPLOYEE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].hourlyRate").value(hasItem(DEFAULT_HOURLY_RATE)))
                .andExpect(jsonPath("$.[*].fixedSalary").value(hasItem(DEFAULT_FIXED_SALARY)))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
                .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void getSetupEmployee() throws Exception {
        // Initialize the database
        setupEmployeeRepository.saveAndFlush(setupEmployee);

        // Get the setupEmployee
        restSetupEmployeeMockMvc.perform(get("/api/setupEmployees/{id}", setupEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(setupEmployee.getId().intValue()))
            .andExpect(jsonPath("$.empCode").value(DEFAULT_EMP_CODE.toString()))
            .andExpect(jsonPath("$.employeeTitle").value(DEFAULT_EMPLOYEE_TITLE.toString()))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME.toString()))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.stdWorkingHour").value(DEFAULT_STD_WORKING_HOUR))
            .andExpect(jsonPath("$.overTime").value(DEFAULT_OVER_TIME))
            .andExpect(jsonPath("$.tinNo").value(DEFAULT_TIN_NO.toString()))
            .andExpect(jsonPath("$.deptId").value(DEFAULT_DEPT_ID))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID))
            .andExpect(jsonPath("$.profilePictureContentType").value(DEFAULT_PROFILE_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.profilePicture").value(Base64Utils.encodeToString(DEFAULT_PROFILE_PICTURE)))
            .andExpect(jsonPath("$.superiorId").value(DEFAULT_SUPERIOR_ID))
            .andExpect(jsonPath("$.degCode").value(DEFAULT_DEG_CODE.toString()))
            .andExpect(jsonPath("$.employeeType").value(DEFAULT_EMPLOYEE_TYPE.toString()))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.hourlyRate").value(DEFAULT_HOURLY_RATE))
            .andExpect(jsonPath("$.fixedSalary").value(DEFAULT_FIXED_SALARY))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingSetupEmployee() throws Exception {
        // Get the setupEmployee
        restSetupEmployeeMockMvc.perform(get("/api/setupEmployees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetupEmployee() throws Exception {
        // Initialize the database
        setupEmployeeRepository.saveAndFlush(setupEmployee);

		int databaseSizeBeforeUpdate = setupEmployeeRepository.findAll().size();

        // Update the setupEmployee
        setupEmployee.setEmpCode(UPDATED_EMP_CODE);
        setupEmployee.setEmployeeTitle(UPDATED_EMPLOYEE_TITLE);
        setupEmployee.setEmployeeName(UPDATED_EMPLOYEE_NAME);
        setupEmployee.setJoiningDate(UPDATED_JOINING_DATE);
        setupEmployee.setStdWorkingHour(UPDATED_STD_WORKING_HOUR);
        setupEmployee.setOverTime(UPDATED_OVER_TIME);
        setupEmployee.setTinNo(UPDATED_TIN_NO);
        setupEmployee.setDeptId(UPDATED_DEPT_ID);
        setupEmployee.setApplicationId(UPDATED_APPLICATION_ID);
        setupEmployee.setProfilePicture(UPDATED_PROFILE_PICTURE);
        setupEmployee.setProfilePictureContentType(UPDATED_PROFILE_PICTURE_CONTENT_TYPE);
        setupEmployee.setSuperiorId(UPDATED_SUPERIOR_ID);
        setupEmployee.setDegCode(UPDATED_DEG_CODE);
        setupEmployee.setEmployeeType(UPDATED_EMPLOYEE_TYPE);
        setupEmployee.setBloodGroup(UPDATED_BLOOD_GROUP);
        setupEmployee.setPhone(UPDATED_PHONE);
        setupEmployee.setAddress(UPDATED_ADDRESS);
        setupEmployee.setHourlyRate(UPDATED_HOURLY_RATE);
        setupEmployee.setFixedSalary(UPDATED_FIXED_SALARY);
        setupEmployee.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        setupEmployee.setStatus(UPDATED_STATUS);
        setupEmployee.setCreatedDate(UPDATED_CREATED_DATE);
        setupEmployee.setUpdatedDate(UPDATED_UPDATED_DATE);
        setupEmployee.setCreatedBy(UPDATED_CREATED_BY);
        setupEmployee.setUpdatedBy(UPDATED_UPDATED_BY);

        restSetupEmployeeMockMvc.perform(put("/api/setupEmployees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(setupEmployee)))
                .andExpect(status().isOk());

        // Validate the SetupEmployee in the database
        List<SetupEmployee> setupEmployees = setupEmployeeRepository.findAll();
        assertThat(setupEmployees).hasSize(databaseSizeBeforeUpdate);
        SetupEmployee testSetupEmployee = setupEmployees.get(setupEmployees.size() - 1);
        assertThat(testSetupEmployee.getEmpCode()).isEqualTo(UPDATED_EMP_CODE);
        assertThat(testSetupEmployee.getEmployeeTitle()).isEqualTo(UPDATED_EMPLOYEE_TITLE);
        assertThat(testSetupEmployee.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testSetupEmployee.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testSetupEmployee.getStdWorkingHour()).isEqualTo(UPDATED_STD_WORKING_HOUR);
        assertThat(testSetupEmployee.getOverTime()).isEqualTo(UPDATED_OVER_TIME);
        assertThat(testSetupEmployee.getTinNo()).isEqualTo(UPDATED_TIN_NO);
        assertThat(testSetupEmployee.getDeptId()).isEqualTo(UPDATED_DEPT_ID);
        assertThat(testSetupEmployee.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testSetupEmployee.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
        assertThat(testSetupEmployee.getProfilePictureContentType()).isEqualTo(UPDATED_PROFILE_PICTURE_CONTENT_TYPE);
        assertThat(testSetupEmployee.getSuperiorId()).isEqualTo(UPDATED_SUPERIOR_ID);
        assertThat(testSetupEmployee.getDegCode()).isEqualTo(UPDATED_DEG_CODE);
        assertThat(testSetupEmployee.getEmployeeType()).isEqualTo(UPDATED_EMPLOYEE_TYPE);
        assertThat(testSetupEmployee.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testSetupEmployee.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSetupEmployee.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSetupEmployee.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testSetupEmployee.getFixedSalary()).isEqualTo(UPDATED_FIXED_SALARY);
        assertThat(testSetupEmployee.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testSetupEmployee.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSetupEmployee.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSetupEmployee.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testSetupEmployee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSetupEmployee.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void deleteSetupEmployee() throws Exception {
        // Initialize the database
        setupEmployeeRepository.saveAndFlush(setupEmployee);

		int databaseSizeBeforeDelete = setupEmployeeRepository.findAll().size();

        // Get the setupEmployee
        restSetupEmployeeMockMvc.perform(delete("/api/setupEmployees/{id}", setupEmployee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SetupEmployee> setupEmployees = setupEmployeeRepository.findAll();
        assertThat(setupEmployees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
