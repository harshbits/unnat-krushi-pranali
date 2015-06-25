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
public class FarmService extends AbstractService<FarmTbl, FieldTbl>{

    public FarmService(String persistenceUnitName) throws IOException {
        super(persistenceUnitName);
    }

    @Override
    public FarmTbl find(int id) {
         EntityManager em = emf.createEntityManager();
	return em.find(FarmTbl.class, id);
    }
    
    public List<FarmTbl> findAll() {
	EntityManager em = emf.createEntityManager();
	Query q = em.createQuery("SELECT f FROM FarmTbl f");
	List<FarmTbl> results = q.getResultList();
	return results;
	}

    @Override
    public void addChild(FarmTbl parent, FieldTbl child) {
        
         EntityManager em = emf.createEntityManager();
         em.getTransaction().begin();
         
         child.setParent(parent);
         parent.addChild(child);
         em.merge(parent);
         em.merge(child);

	em.getTransaction().commit();
    }

  
}
