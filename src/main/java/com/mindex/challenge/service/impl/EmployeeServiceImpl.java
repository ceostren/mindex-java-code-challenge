package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public ReportingStructure report(Employee employee) {
        int reports = 0;
        HashMap<String, Employee> map = new HashMap<>();
        map.put(employee.getEmployeeId(),employee);
        Stack<Employee> stack = new Stack<>();
        stack.push(employee);
        while (stack.isEmpty() == false){
            Employee cur = stack.pop();
            List<Employee> employees = cur.getDirectReports();
            if(employees != null) {
                for (Employee e : employees) {
                    stack.push(read(e.getEmployeeId()));
                }
            }
            if (!map.containsKey(cur.getEmployeeId())){
                LOG.debug("Adding report for {}",cur.getFirstName());
                reports++;
                map.put(cur.getEmployeeId(),cur);
            }

            //TODO cycle check
        }
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(reports);
        return reportingStructure;
    }

}
