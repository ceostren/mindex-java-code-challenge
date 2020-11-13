package com.mindex.challenge.data;

import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ReportingStructure {

    @Autowired
    private EmployeeService employeeService;

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructure.class);
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure() {
    }


    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
