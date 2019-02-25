package springbootdemo.demo.mapper;

import org.apache.ibatis.annotations.*;
import springbootdemo.demo.entities.Employee;

@Mapper
public interface EmployeeMapper {
    @Select("select * from employee where id = #{id}")
    public Employee getEmployeeById(Integer id);

    @Update("update employee set lastName = #{lastName},email = #{email},gender = {gender} ,d_id = #{dId}")
    public void updateEmployee(Employee employee);

    @Delete("delete from employee where id = #{id}")
    public void deleteEmpById(Integer id);

    @Insert("insert into employee(lastName,email,gender,d_id values(#{lastName},#{email},#{gender},#{dId})")
    public void insertEmployee(Employee employee);

    @Select("select * from employee where lastName = #{lastName}")
    public Employee getEmpByLastName(String lastName);

}
