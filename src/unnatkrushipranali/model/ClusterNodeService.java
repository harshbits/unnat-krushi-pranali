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
public class ClusterNodeService extends AbstractService<ClusterNode, SensorNode> {

    public ClusterNodeService(String persistenceUnitName) throws IOException {
        super(persistenceUnitName);
    }

    @Override
    public ClusterNode find(int id) {
        EntityManager em = emf.createEntityManager();
	return em.find(ClusterNode.class, id);
        
    }
    
    public List<ClusterNode> findAll() {
	EntityManager em = emf.createEntityManager();
	Query q = em.createQuery("SELECT c FROM ClusterNode c");
	List<ClusterNode> results = q.getResultList();
	return results;
	}
    
    @Override
    public void addChild(ClusterNode parent, SensorNode child) {
            
         EntityManager em = emf.createEntityManager();
         em.getTransaction().begin();
         
         child.setParent(parent);
         parent.addChild(child);
         em.merge(parent);
         em.merge(child);

	em.getTransaction().commit();                       
    }

    public void addChild(ClusterNode parent, SolenoidValve child) {
        
        EntityManager em = emf.createEntityManager();
         em.getTransaction().begin();
         
         child.setParent(parent);
         parent.addChild(child);
         em.merge(parent);
         em.merge(child);

	em.getTransaction().commit();
       
    }

    
    
}
