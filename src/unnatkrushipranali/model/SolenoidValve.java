/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author harshbitss
 */
@Entity
@Table(name = "solenoid_valve", catalog = "samplejavafxapplication", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"valve_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolenoidValve.findAll", query = "SELECT s FROM SolenoidValve s"),
    @NamedQuery(name = "SolenoidValve.findById", query = "SELECT s FROM SolenoidValve s WHERE s.id = :id"),
    @NamedQuery(name = "SolenoidValve.findByValveId", query = "SELECT s FROM SolenoidValve s WHERE s.valveId = :valveId")})
public class SolenoidValve implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "valve_id", nullable = false, length = 10)
    private String valveId;
    @JoinColumn(name = "cluster_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private ClusterNode clusterId;
    

    public SolenoidValve() {
    }

    public SolenoidValve(Integer id) {
        this.id = id;
    }

    public SolenoidValve(Integer id, String valveId) {
        this.id = id;
        this.valveId = valveId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValveId() {
        return valveId;
    }

    public void setValveId(String valveId) {
        this.valveId = valveId;
    }

    public ClusterNode getClusterId() {
        return clusterId;
    }

    public void setClusterId(ClusterNode clusterId) {
        this.clusterId = clusterId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolenoidValve)) {
            return false;
        }
        SolenoidValve other = (SolenoidValve) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.SolenoidValve[ id=" + id + " ]";
    }
    
    public ClusterNode getParent(){
        return clusterId;
        
    }
    void setParent(ClusterNode parent) {
        this.clusterId=parent;
    }
    
}
