
package workshop.services;

import java.util.List;
import workshop.dao.DaoFactory;
import workshop.dao.SellerDao;
import workshop.entites.Seller;

public class SellerService {
    private final SellerDao dao = DaoFactory.createSelletDao();
    
    public List<Seller> findAll(){
        return dao.findAll();
    }
    public void saveOrUpdate(Seller obj){
        if (obj.getId()== null) {
            dao.insert(obj);
        }
        else{
            dao.update(obj);
        }
    }
    public void removeSeller(Seller obj){
        dao.deleteById(obj.getId());
    }
}
