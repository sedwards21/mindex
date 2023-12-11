package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateAndRead() {

        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId("testCreateRead");
        testCompensation.setSalary(BigDecimal.valueOf(140000));
        testCompensation.setEffectiveDate(new Date());

        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation.getEmployeeId());
    }


    @Test
    public void testUpdate() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId("testUpdate");
        testCompensation.setSalary(BigDecimal.valueOf(100000));
        testCompensation.setEffectiveDate(new Date());
        System.out.println(testCompensation.getEffectiveDate());
//      Create
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        // Update checks
        createdCompensation.setSalary(BigDecimal.valueOf(200000));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation updatedCompensation = restTemplate.exchange(compensationIdUrl,
                HttpMethod.PUT,
                new HttpEntity<Compensation>(createdCompensation, headers),
                Compensation.class,
                createdCompensation.getEmployeeId()).getBody();


        assertCompensationEquivalence(createdCompensation, updatedCompensation);

    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
    }
}
