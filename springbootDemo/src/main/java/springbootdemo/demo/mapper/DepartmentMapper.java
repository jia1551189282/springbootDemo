package springbootdemo.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import springbootdemo.demo.entities.Department;

@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id = #{id}")
    public Department getDepartmentById(Integer id);
}
