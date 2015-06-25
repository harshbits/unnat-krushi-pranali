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
public class SensorNodeService extends AbstractService<SensorNode,SensorList> {

    public SensorNodeService(String persistenceUnitName) throws IOException {
        super(persistenceUnitName);
    }
       
    @Override
    public SensorNode find(int id)
    {
		EntityManager em = emf.createEntityManager();
		return em.find(SensorNode.class, id);
    }

	public List<SensorNode> findAll() {
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT s FROM SensorNode s");
		List<SensorNode> results = q.getResultList();
		return results;
	}

    @Override
    public void addChild(SensorNode parent, SensorList child) {
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
         
        child.setParent(parent);
        parent.addChild(child);
        em.merge(parent);
        em.merge(child);

	em.getTransaction().commit();         
        
    }

    
   
    
}
