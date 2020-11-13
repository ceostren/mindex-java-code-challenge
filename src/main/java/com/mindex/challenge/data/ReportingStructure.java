package com.mindex.challenge.data;

import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ReportingStructure {

    @Autowired
    private EmployeeService employeeService;

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructure.class);
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure() {
    }

    private void calcReports(Employee employee){
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
                    stack.push(employeeService.read(e.getEmployeeId()));
                }
            }
            if (!map.containsKey(cur.getEmployeeId())){
                LOG.debug("Adding report for {}",cur.getFirstName());
                reports++;
                map.put(cur.getEmployeeId(),cur);
            }

            //TODO cycle check
        }
        numberOfReports = reports;
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
