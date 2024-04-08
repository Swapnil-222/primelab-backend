package com.tech.prime.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tech.prime.IntegrationTest;
import com.tech.prime.domain.Project;
import com.tech.prime.domain.enumeration.Category;
import com.tech.prime.domain.enumeration.Department;
import com.tech.prime.domain.enumeration.Division;
import com.tech.prime.domain.enumeration.Priority;
import com.tech.prime.domain.enumeration.Reason;
import com.tech.prime.domain.enumeration.Type;
import com.tech.prime.repository.ProjectRepository;
import com.tech.prime.service.dto.ProjectDTO;
import com.tech.prime.service.mapper.ProjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectResourceIT {

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final Reason DEFAULT_REASON = Reason.BUSSINESS;
    private static final Reason UPDATED_REASON = Reason.DELERSHIP;

    private static final Type DEFAULT_TYPE = Type.INTERNAL;
    private static final Type UPDATED_TYPE = Type.EXTERNAL;

    private static final Division DEFAULT_DIVISION = Division.COMPRESSER;
    private static final Division UPDATED_DIVISION = Division.FILTERS;

    private static final Category DEFAULT_CATEGORY = Category.QUALITY_A;
    private static final Category UPDATED_CATEGORY = Category.QUALITY_B;

    private static final Priority DEFAULT_PRIORITY = Priority.HIGH;
    private static final Priority UPDATED_PRIORITY = Priority.MEDIUM;

    private static final Department DEFAULT_DEPARTMENT = Department.STARTEGY;
    private static final Department UPDATED_DEPARTMENT = Department.FINANCE;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectMockMvc;

    private Project project;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .projectName(DEFAULT_PROJECT_NAME)
            .reason(DEFAULT_REASON)
            .type(DEFAULT_TYPE)
            .division(DEFAULT_DIVISION)
            .category(DEFAULT_CATEGORY)
            .priority(DEFAULT_PRIORITY)
            .department(DEFAULT_DEPARTMENT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .location(DEFAULT_LOCATION)
            .status(DEFAULT_STATUS);
        return project;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity(EntityManager em) {
        Project project = new Project()
            .projectName(UPDATED_PROJECT_NAME)
            .reason(UPDATED_REASON)
            .type(UPDATED_TYPE)
            .division(UPDATED_DIVISION)
            .category(UPDATED_CATEGORY)
            .priority(UPDATED_PRIORITY)
            .department(UPDATED_DEPARTMENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS);
        return project;
    }

    @BeforeEach
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();
        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);
        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testProject.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testProject.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProject.getDivision()).isEqualTo(DEFAULT_DIVISION);
        assertThat(testProject.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProject.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testProject.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProject.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProject.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createProjectWithExistingId() throws Exception {
        // Create the Project with an existing ID
        project.setId(1L);
        ProjectDTO projectDTO = projectMapper.toDto(project);

        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].division").value(hasItem(DEFAULT_DIVISION.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.division").value(DEFAULT_DIVISION.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getProjectsByIdFiltering() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        Long id = project.getId();

        defaultProjectShouldBeFound("id.equals=" + id);
        defaultProjectShouldNotBeFound("id.notEquals=" + id);

        defaultProjectShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProjectShouldNotBeFound("id.greaterThan=" + id);

        defaultProjectShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProjectShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where projectName equals to DEFAULT_PROJECT_NAME
        defaultProjectShouldBeFound("projectName.equals=" + DEFAULT_PROJECT_NAME);

        // Get all the projectList where projectName equals to UPDATED_PROJECT_NAME
        defaultProjectShouldNotBeFound("projectName.equals=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where projectName in DEFAULT_PROJECT_NAME or UPDATED_PROJECT_NAME
        defaultProjectShouldBeFound("projectName.in=" + DEFAULT_PROJECT_NAME + "," + UPDATED_PROJECT_NAME);

        // Get all the projectList where projectName equals to UPDATED_PROJECT_NAME
        defaultProjectShouldNotBeFound("projectName.in=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where projectName is not null
        defaultProjectShouldBeFound("projectName.specified=true");

        // Get all the projectList where projectName is null
        defaultProjectShouldNotBeFound("projectName.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByProjectNameContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where projectName contains DEFAULT_PROJECT_NAME
        defaultProjectShouldBeFound("projectName.contains=" + DEFAULT_PROJECT_NAME);

        // Get all the projectList where projectName contains UPDATED_PROJECT_NAME
        defaultProjectShouldNotBeFound("projectName.contains=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByProjectNameNotContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where projectName does not contain DEFAULT_PROJECT_NAME
        defaultProjectShouldNotBeFound("projectName.doesNotContain=" + DEFAULT_PROJECT_NAME);

        // Get all the projectList where projectName does not contain UPDATED_PROJECT_NAME
        defaultProjectShouldBeFound("projectName.doesNotContain=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllProjectsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where reason equals to DEFAULT_REASON
        defaultProjectShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the projectList where reason equals to UPDATED_REASON
        defaultProjectShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllProjectsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultProjectShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the projectList where reason equals to UPDATED_REASON
        defaultProjectShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllProjectsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where reason is not null
        defaultProjectShouldBeFound("reason.specified=true");

        // Get all the projectList where reason is null
        defaultProjectShouldNotBeFound("reason.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where type equals to DEFAULT_TYPE
        defaultProjectShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the projectList where type equals to UPDATED_TYPE
        defaultProjectShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProjectsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProjectShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the projectList where type equals to UPDATED_TYPE
        defaultProjectShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProjectsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where type is not null
        defaultProjectShouldBeFound("type.specified=true");

        // Get all the projectList where type is null
        defaultProjectShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByDivisionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where division equals to DEFAULT_DIVISION
        defaultProjectShouldBeFound("division.equals=" + DEFAULT_DIVISION);

        // Get all the projectList where division equals to UPDATED_DIVISION
        defaultProjectShouldNotBeFound("division.equals=" + UPDATED_DIVISION);
    }

    @Test
    @Transactional
    void getAllProjectsByDivisionIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where division in DEFAULT_DIVISION or UPDATED_DIVISION
        defaultProjectShouldBeFound("division.in=" + DEFAULT_DIVISION + "," + UPDATED_DIVISION);

        // Get all the projectList where division equals to UPDATED_DIVISION
        defaultProjectShouldNotBeFound("division.in=" + UPDATED_DIVISION);
    }

    @Test
    @Transactional
    void getAllProjectsByDivisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where division is not null
        defaultProjectShouldBeFound("division.specified=true");

        // Get all the projectList where division is null
        defaultProjectShouldNotBeFound("division.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where category equals to DEFAULT_CATEGORY
        defaultProjectShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the projectList where category equals to UPDATED_CATEGORY
        defaultProjectShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllProjectsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultProjectShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the projectList where category equals to UPDATED_CATEGORY
        defaultProjectShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllProjectsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where category is not null
        defaultProjectShouldBeFound("category.specified=true");

        // Get all the projectList where category is null
        defaultProjectShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where priority equals to DEFAULT_PRIORITY
        defaultProjectShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the projectList where priority equals to UPDATED_PRIORITY
        defaultProjectShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllProjectsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultProjectShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the projectList where priority equals to UPDATED_PRIORITY
        defaultProjectShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllProjectsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where priority is not null
        defaultProjectShouldBeFound("priority.specified=true");

        // Get all the projectList where priority is null
        defaultProjectShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where department equals to DEFAULT_DEPARTMENT
        defaultProjectShouldBeFound("department.equals=" + DEFAULT_DEPARTMENT);

        // Get all the projectList where department equals to UPDATED_DEPARTMENT
        defaultProjectShouldNotBeFound("department.equals=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllProjectsByDepartmentIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where department in DEFAULT_DEPARTMENT or UPDATED_DEPARTMENT
        defaultProjectShouldBeFound("department.in=" + DEFAULT_DEPARTMENT + "," + UPDATED_DEPARTMENT);

        // Get all the projectList where department equals to UPDATED_DEPARTMENT
        defaultProjectShouldNotBeFound("department.in=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllProjectsByDepartmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where department is not null
        defaultProjectShouldBeFound("department.specified=true");

        // Get all the projectList where department is null
        defaultProjectShouldNotBeFound("department.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate equals to DEFAULT_START_DATE
        defaultProjectShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the projectList where startDate equals to UPDATED_START_DATE
        defaultProjectShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultProjectShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the projectList where startDate equals to UPDATED_START_DATE
        defaultProjectShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where startDate is not null
        defaultProjectShouldBeFound("startDate.specified=true");

        // Get all the projectList where startDate is null
        defaultProjectShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate equals to DEFAULT_END_DATE
        defaultProjectShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the projectList where endDate equals to UPDATED_END_DATE
        defaultProjectShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultProjectShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the projectList where endDate equals to UPDATED_END_DATE
        defaultProjectShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllProjectsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where endDate is not null
        defaultProjectShouldBeFound("endDate.specified=true");

        // Get all the projectList where endDate is null
        defaultProjectShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where location equals to DEFAULT_LOCATION
        defaultProjectShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the projectList where location equals to UPDATED_LOCATION
        defaultProjectShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllProjectsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultProjectShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the projectList where location equals to UPDATED_LOCATION
        defaultProjectShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllProjectsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where location is not null
        defaultProjectShouldBeFound("location.specified=true");

        // Get all the projectList where location is null
        defaultProjectShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByLocationContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where location contains DEFAULT_LOCATION
        defaultProjectShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the projectList where location contains UPDATED_LOCATION
        defaultProjectShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllProjectsByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where location does not contain DEFAULT_LOCATION
        defaultProjectShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the projectList where location does not contain UPDATED_LOCATION
        defaultProjectShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllProjectsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where status equals to DEFAULT_STATUS
        defaultProjectShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the projectList where status equals to UPDATED_STATUS
        defaultProjectShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllProjectsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultProjectShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the projectList where status equals to UPDATED_STATUS
        defaultProjectShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllProjectsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where status is not null
        defaultProjectShouldBeFound("status.specified=true");

        // Get all the projectList where status is null
        defaultProjectShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllProjectsByStatusContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where status contains DEFAULT_STATUS
        defaultProjectShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the projectList where status contains UPDATED_STATUS
        defaultProjectShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllProjectsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList where status does not contain DEFAULT_STATUS
        defaultProjectShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the projectList where status does not contain UPDATED_STATUS
        defaultProjectShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectShouldBeFound(String filter) throws Exception {
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].division").value(hasItem(DEFAULT_DIVISION.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectShouldNotBeFound(String filter) throws Exception {
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .projectName(UPDATED_PROJECT_NAME)
            .reason(UPDATED_REASON)
            .type(UPDATED_TYPE)
            .division(UPDATED_DIVISION)
            .category(UPDATED_CATEGORY)
            .priority(UPDATED_PRIORITY)
            .department(UPDATED_DEPARTMENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS);
        ProjectDTO projectDTO = projectMapper.toDto(updatedProject);

        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProject.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testProject.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProject.getDivision()).isEqualTo(UPDATED_DIVISION);
        assertThat(testProject.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProject.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testProject.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProject.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .reason(UPDATED_REASON)
            .division(UPDATED_DIVISION)
            .category(UPDATED_CATEGORY)
            .priority(UPDATED_PRIORITY)
            .department(UPDATED_DEPARTMENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS);

        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testProject.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testProject.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProject.getDivision()).isEqualTo(UPDATED_DIVISION);
        assertThat(testProject.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProject.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testProject.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProject.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .projectName(UPDATED_PROJECT_NAME)
            .reason(UPDATED_REASON)
            .type(UPDATED_TYPE)
            .division(UPDATED_DIVISION)
            .category(UPDATED_CATEGORY)
            .priority(UPDATED_PRIORITY)
            .department(UPDATED_DEPARTMENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS);

        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProject.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testProject.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProject.getDivision()).isEqualTo(UPDATED_DIVISION);
        assertThat(testProject.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProject.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testProject.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProject.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(longCount.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Delete the project
        restProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, project.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
