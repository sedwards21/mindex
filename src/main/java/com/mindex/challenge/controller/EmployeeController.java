package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ReportingStructureService reportingStructService;
    @Autowired
    private CompensationService compensationService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);
        return employeeService.create(employee);
    }

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for employee id [{}]", compensation);
        return compensationService.create(compensation);
    }

    @GetMapping(path = "/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);
        return employeeService.read(id);
    }

    @GetMapping("/employee/{id}/reportingStructure")
    public ReportingStructure getEmployeeReports(@PathVariable String id) {
        LOG.debug("Received employee reporting structure get request for id [{}]", id);
        return reportingStructService.create(employeeService.read(id));

    }

    @GetMapping("/compensation/{id}/")
    public Compensation readCompensation(@PathVariable String id) {
        LOG.debug("Received employee compensation get request for id [{}]", id);
        return compensationService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    @PutMapping("/compensation/{id}")
    public Compensation update(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for employee id [{}]", compensation);

        return compensationService.update(compensation);
    }
}
