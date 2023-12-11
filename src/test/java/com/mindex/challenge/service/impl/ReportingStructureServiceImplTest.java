package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String employeeUrl;

    private String reportingStructureUrl;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureUrl = "http://localhost:" + port + "/employee/{id}/reportingStructure";
    }

    @Test
    public void testReportingStructureTwoReports() {
        Employee employeeOne = new Employee();
        employeeOne.setFirstName("John");
        employeeOne.setLastName("Jacob");
        employeeOne.setDepartment("Engineering");
        employeeOne.setPosition("Software Engineer ii");

        Employee employeeTwo = new Employee();
        employeeTwo.setFirstName("Jingleheimer");
        employeeTwo.setLastName("Smith");
        employeeTwo.setDepartment("Management");
        employeeTwo.setPosition("Engineering Manager");

        Employee employeeThree = new Employee();
        employeeThree.setFirstName("Jerry");
        employeeThree.setLastName("Smith");
        employeeThree.setDepartment("Product");
        employeeThree.setPosition("Product Manager");

        List<Employee> employees = new ArrayList<>();
        employees.add(employeeTwo);
        employees.add(employeeThree);

        employeeOne.setDirectReports(employees);

        restTemplate.postForEntity(employeeUrl, employeeTwo, Employee.class).getBody();
        restTemplate.postForEntity(employeeUrl, employeeThree, Employee.class).getBody();

        //Create member with memberTwo and memberThree in structured reports
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, employeeOne, Employee.class).getBody();

        ReportingStructure reports = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, createdEmployee.getEmployeeId()).getBody();

        assertEquals(2, reports.getNumberOfReports());
    }
}