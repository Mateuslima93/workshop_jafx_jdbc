
package workshop.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import workshop.dao.DepartmentDao;
import workshop.db.Conexao;
import workshop.db.DbException;
import workshop.entites.Department;
import workshop.entites.Seller;

public class DepartmentDaoJdbc implements DepartmentDao {
    private Connection conn;
    
    public DepartmentDaoJdbc(Connection conn){
        this.conn = conn;
    }
    
    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dep = new Department(rs.getInt("Id"), rs.getString("Name"));
        return dep;
    }
            
    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO department "
                    + "(Name) "
                    + "VALUES "
                    + "(?)",
                    + Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                Conexao.closeResultSet(rs);
            }
            else{
                throw new DbException("Erro inexperado. Nenhuma linha afetada.");
            }
        }
        catch(SQLException e){
             throw new DbException(e.getMessage());
        }
        finally{
            Conexao.closeStatement(st);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE department "
                    + "SET Name = ? "
                    + "WHERE Id = ?");
            st.setString(1,obj.getName());
            st.setInt(2, obj.getId());
            
            st.executeUpdate();
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            Conexao.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "DELETE FROM department "
                    + "WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            Conexao.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
       PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT * FROM department "
                    + "WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                return dep;
            }
            else{
                return null;
            }
        }
        catch(SQLException ex){
            throw new DbException(ex.getMessage()); 
        }
        finally{
            Conexao.closeStatement(st);
            Conexao.closeResultSet(rs);
        } 
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT * "
                    + "FROM department "
                    + "ORDER BY Name");
            
            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateDepartment(rs));
            }
            return list;
        }
        catch(SQLException ex){
            throw new DbException(ex.getMessage()); 
        }
        finally{
            Conexao.closeStatement(st);
            Conexao.closeResultSet(rs);
        }
    }
    
}
