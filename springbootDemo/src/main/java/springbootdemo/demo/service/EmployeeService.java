package springbootdemo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import springbootdemo.demo.entities.Employee;
import springbootdemo.demo.mapper.EmployeeMapper;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    @Cacheable(cacheNames="emp")
    public Employee getEmployeeById(Integer id){
        System.out.println("查询"+id+"号员工");
        Employee employee = employeeMapper.getEmployeeById(id);
        return employee;
    }
}
