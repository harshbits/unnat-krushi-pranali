/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.model;

import java.io.Serializable;

/**
 *
 * @author harshbitss
 * @param <T>
 */
public interface ChildService<T extends Serializable> {
    
    T find(int id);

	void remove(T item);

	void persist(T item);

	T merge(T item);
    
}
