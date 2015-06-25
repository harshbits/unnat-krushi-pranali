/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.model;

import java.io.IOException;
import javax.persistence.EntityManager;

/**
 *
 * @author harshbitss
 */
public class CropdataService extends ChildAbstractService<Cropdata> {

    public CropdataService(String persistenceUnitName) throws IOException {
        super(persistenceUnitName);
    }

    @Override
    public Cropdata find(int id) {
         EntityManager em = emf.createEntityManager();
	return em.find(Cropdata.class, id);
    }
    
    
    
    
}
