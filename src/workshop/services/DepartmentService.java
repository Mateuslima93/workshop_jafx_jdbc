
package workshop.services;

import java.util.ArrayList;
import java.util.List;
import workshop.entites.Department;

public class DepartmentService {
    public List<Department> findall(){
        List<Department> list = new ArrayList<>();
        list.add(new Department(1, "Books"));
        list.add(new Department(2, "Computers"));
        list.add(new Department(3, "Electronics"));
        return list;
    }
}