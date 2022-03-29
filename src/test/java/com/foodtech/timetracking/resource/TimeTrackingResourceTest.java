package com.foodtech.timetracking.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.foodtech.timetracking.TimeTrackingApplication;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        classes = {TimeTrackingApplication.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TimeTrackingResourceTest {

    @Autowired WebApplicationContext context;

    @Autowired MockMvc mockMvc;

    @Autowired DataSource dataSource;

    @BeforeAll
    public void setup() {

        ResourceDatabasePopulator resourceDatabasePopulator =
                new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(
                new ClassPathResource("/testCases/sql/employees.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }

    @AfterAll
    public void tearDown() {
        ResourceDatabasePopulator resourceDatabasePopulator =
                new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(
                new ClassPathResource("/testCases/sql/cleanup.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }

    @Test
    @Order(1)
    void testCheckInForPunchInProcess() throws Exception {
        mockMvc
                .perform(
                        post("/v1/time-trackings/check-in/employees/1")
                                .content(
                                        "{\"time\":\"2021-01-25T22:34:55\",\"timeTrackingEnum\":\"PUNCH\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.time").value("2021-01-25T22:34:55"))
                .andExpect(jsonPath("$.timeTrackingEnum").value("PUNCH"));
    }

    @Test
    @Order(2)
    void testCheckOutForPunchInProcess() throws Exception {
        mockMvc
                .perform(
                        post("/v1/time-trackings/check-out/employees/1")
                                .content(
                                        "{\"time\":\"2021-01-25T23:34:55\",\"timeTrackingEnum\":\"PUNCH\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.time").value("2021-01-25T23:34:55"))
                .andExpect(jsonPath("$.timeTrackingEnum").value("PUNCH"));
    }

    @Test
    @Order(3)
    void testReportForSpecificEmployeeWithDate() throws Exception {
        mockMvc
                .perform(
                        get("/v1/time-trackings/employees/1/2021-01-25/2021-01-26")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.summary[0].date").value("2021-01-25"))
                .andExpect(jsonPath("$.summary[0].punchIn")
                                   .value("2021-01-25T22:34:55"))
                .andExpect(jsonPath("$.summary[0].punchOut")
                                   .value("2021-01-25T23:34:55"));

    }

    @Test
    @Order(4)
    void testReportForEmployeeListForManager() throws Exception {
        //PunchIn for second user

        mockMvc
                .perform(
                        post("/v1/time-trackings/check-in/employees/2")
                                .content(
                                        "{\"time\":\"2021-01-25T20:34:55\",\"timeTrackingEnum\":\"PUNCH\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(2))
                .andExpect(jsonPath("$.time").value("2021-01-25T20:34:55"))
                .andExpect(jsonPath("$.timeTrackingEnum").value("PUNCH"));

        //PunchOut for the second user
        mockMvc
                .perform(
                        post("/v1/time-trackings/check-out/employees/2")
                                .content(
                                        "{\"time\":\"2021-01-25T21:34:55\",\"timeTrackingEnum\":\"PUNCH\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(2))
                .andExpect(jsonPath("$.time").value("2021-01-25T21:34:55"))
                .andExpect(jsonPath("$.timeTrackingEnum").value("PUNCH"));


        //Report for the manager about employee 1 , and employee 2
        mockMvc
                .perform(
                        get("/v1/time-trackings/employees?searchDate=2021-01-25")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].employeeId").value(1))
                .andExpect(
                        jsonPath("$.[0].summary[0].date").value("2021-01-25"))
                .andExpect(jsonPath("$.[0].summary[0].punchIn")
                                   .value("2021-01-25T22:34:55"))
                .andExpect(jsonPath("$.[0].summary[0].punchOut")
                                   .value("2021-01-25T23:34:55"))
                .andExpect(jsonPath("$.[1].employeeId").value(2))
                .andExpect(
                        jsonPath("$.[1].summary[0].date").value("2021-01-25"))
                .andExpect(jsonPath("$.[1].summary[0].punchIn")
                                   .value("2021-01-25T20:34:55"))
                .andExpect(jsonPath("$.[1].summary[0].punchOut")
                                   .value("2021-01-25T21:34:55"));

    }

    //Some exceptional cases

    @Test
    @Order(5)
    void testPunchInNotExistingEmployee() throws Exception {
        mockMvc
                .perform(
                        post("/v1/time-trackings/check-in/employees/5")
                                .content(
                                        "{\"time\":\"2021-01-25T22:34:55\",\"timeTrackingEnum\":\"PUNCH\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @Order(6)
    void testPunchOutForNotPunchedInEmployee() throws Exception {
        mockMvc
                .perform(
                        post("/v1/time-trackings/check-out/employees/3")
                                .content(
                                        "{\"time\":\"2021-01-25T22:34:55\",\"timeTrackingEnum\":\"PUNCH\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                                   .value("There is no open punchIn to punchOut"));

    }

}
