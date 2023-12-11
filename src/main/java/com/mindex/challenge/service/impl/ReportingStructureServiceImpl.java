package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private final EmployeeService employeeService;

    public ReportingStructureServiceImpl(EmployeeService empService) {
        this.employeeService = empService;
    }

    public ReportingStructure create(Employee employee) {
        return new ReportingStructure(employee, getEmployeeReportCount(employee));
    }

    public int getEmployeeReportCount(Employee employee) {
        LOG.debug("Getting report count for employee [{}]", employee);

        List<Employee> directReports = employee.getDirectReports();
        int result = directReports.size();
        for (Employee emp : directReports) {
            if (emp.getEmployeeId() != null) {
                Employee report = employeeService.read(emp.getEmployeeId());
                result += emp.getDirectReports() != null ? emp.getDirectReports().size() : 0;
            }
        }
        return result;
    }
}
