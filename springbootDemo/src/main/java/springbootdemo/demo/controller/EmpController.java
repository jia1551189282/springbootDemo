package springbootdemo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springbootdemo.demo.bean.Department;
import springbootdemo.demo.bean.Employee;
import springbootdemo.demo.dao.DepartmentDao;
import springbootdemo.demo.dao.EmployeeDao;


import java.util.Collection;
import java.util.List;

@Controller
public class EmpController {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private EmployeeDao employeeDao;
    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> employeeList = employeeDao.getAll();


        model.addAttribute("emps",employeeList);

        return "emps/list";
    }
    @GetMapping("/emp")
    public String toAddEmpolyee(Model model){
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emps/add";
    }

    @PostMapping("/emp")
    public String addEmployee(Employee employee){
        //System.out.println(employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }
    /*
    根据id查询员工信息
     */
    @GetMapping("/emp/{id}")
    public String toEditEmployee(@PathVariable("id")Integer id ,Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);

        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);

        return "emps/add";
    }
    /*
    修改员工信息
     */

    @PutMapping("/emp")
    public String editEmployee(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    /*
    删除员工
     */
    @DeleteMapping("/emp/{id}")
    public String deleteEmp(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";

    }
}
