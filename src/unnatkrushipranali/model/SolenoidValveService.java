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
public class SolenoidValveService extends ChildAbstractService<SolenoidValve> {

    public SolenoidValveService(String persistenceUnitName) throws IOException {
        super(persistenceUnitName);
    }

    @Override
    public SolenoidValve find(int id) {
        EntityManager em = emf.createEntityManager();
	return em.find(SolenoidValve.class, id);
    }
    
    
    public List<SolenoidValve> findAll() {
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT s FROM SolenoidValve s");
		List<SolenoidValve> results = q.getResultList();
		return results;
	}
    public List<String> findAllbyName() {
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT s.valveId FROM SolenoidValve s");
		List<String> results = q.getResultList();
		return results;
	}
    
}
