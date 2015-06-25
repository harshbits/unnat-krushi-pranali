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
public class FieldService extends ChildAbstractService<FieldTbl> {

    public FieldService(String persistenceUnitName)throws IOException {
       super(persistenceUnitName);
    }

    @Override
    public FieldTbl find(int id) {
        EntityManager em = emf.createEntityManager();
	return em.find(FieldTbl.class, id);
    }
    
    
    
    
}
