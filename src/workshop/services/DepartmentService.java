
package workshop.services;

import java.util.ArrayList;
import java.util.List;
import workshop.dao.DaoFactory;
import workshop.dao.DepartmentDao;
import workshop.entites.Department;

public class DepartmentService {
    private DepartmentDao dao = DaoFactory.createDepartmentDao();
    
    public List<Department> findall(){
        return dao.findAll();
    }
    public void saveOrUpdate(Department obj){
        if (obj.getId()== null) {
            dao.insert(obj);
        }
        else{
            dao.update(obj);
        }
    }
}
