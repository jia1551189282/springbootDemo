package springbootdemo.demo.service;

import com.sun.corba.se.pept.transport.InboundConnectionCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import springbootdemo.demo.entities.Department;
import springbootdemo.demo.mapper.DepartmentMapper;

@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;
    @Cacheable(cacheNames = "dp")
    public Department getDepartmentById(Integer id){
        return departmentMapper.getDepartmentById(id);
    }
}
