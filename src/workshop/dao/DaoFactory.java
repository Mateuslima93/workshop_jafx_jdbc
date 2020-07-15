
package workshop.dao;

import workshop.dao.implement.DepartmentDaoJdbc;
import workshop.dao.implement.SellerDaoJdbc;
import workshop.db.Conexao;

public class DaoFactory {
    public static SellerDao createSellerDao(){
        return new SellerDaoJdbc(Conexao.getConnection());
    }
    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJdbc(Conexao.getConnection());
    }
}
