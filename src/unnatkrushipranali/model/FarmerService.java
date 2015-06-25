/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.model;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author harshbitss
 */
public class FarmerService extends AbstractService<FarmerTbl, FarmTbl>{

    public FarmerService(String persistenceUnitName) throws IOException {
        super(persistenceUnitName);
    }

    @Override
    public FarmerTbl find(int id) {
        EntityManager em = emf.createEntityManager();
	return em.find(FarmerTbl.class, id);
        
    }
    
     public List<FarmerTbl> findAll() {
	EntityManager em = emf.createEntityManager();
	Query q = em.createQuery("SELECT f FROM FarmerTbl f");
	List<FarmerTbl> results = q.getResultList();
	return results;
    }

    @Override
    public void addChild(FarmerTbl parent, FarmTbl child) {
         EntityManager em = emf.createEntityManager();
         em.getTransaction().begin();
         
         child.setParent(parent);
         parent.addChild(child);
         em.merge(parent);
         em.merge(child);

	 em.getTransaction().commit(); 
    }

    
    
    
    
}
