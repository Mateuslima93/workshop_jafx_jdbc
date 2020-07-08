
package workshop.dao.implement;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import workshop.dao.SellerDao;
import workshop.db.Conexao;
import workshop.db.DbException;
import workshop.entites.Department;
import workshop.entites.Seller;


public class SellerDaoJdbc implements SellerDao {
    private Connection conn;
    
    public SellerDaoJdbc(Connection conn){
        this.conn = conn;
    }
    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dep = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
        return dep;
    }
    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{
        Seller obj = new Seller (rs.getInt("Id"),rs.getString("Name"),rs.getString("Email"),
                rs.getDate("BirthDate"), rs.getDouble("BaseSalary"),dep);
        return obj;
    }
    
    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getEmail());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected > 0 ) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                Conexao.closeResultSet(rs);
            }
            else{
                throw new DbException("Erro inesperado. Nenhuma linha afetada");
            }
        }
        catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }
        finally{
            Conexao.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE seller "
                    + "SET Name = ?, Email = ?, BirthDate = ?, "
                    + "BaseSalay = ?, DepartmentId = ? "
                    + "WHERE Id = ?");
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());
            
            st.executeUpdate();
        }
        catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }
        finally{
            Conexao.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM seller "
                    + "WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        }
        catch(SQLException ex){
            throw new DbException(ex.getMessage());
        }
        finally{
            Conexao.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs,dep);
                return obj;
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
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "ORDER BY Name");
            
            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }
                list.add(instantiateSeller(rs,dep));
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

    @Override
    public List<Seller> findByDepartment(Department department) {
     PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name");
            st.setInt(1, department.getId());
            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }
                list.add(instantiateSeller(rs,dep));
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
