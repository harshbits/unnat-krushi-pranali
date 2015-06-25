/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author harshbitss
 */
@Entity
@Table(name = "sensor_list", catalog = "samplejavafxapplication", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SensorList.findAll", query = "SELECT s FROM SensorList s"),
    @NamedQuery(name = "SensorList.findById", query = "SELECT s FROM SensorList s WHERE s.id = :id"),
    @NamedQuery(name = "SensorList.findByName", query = "SELECT s FROM SensorList s WHERE s.name = :name")})
public class SensorList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sensorsId")
    private Collection<SensorData> sensorDataCollection;
    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "node_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private SensorNode nodeId;

    public SensorList() {
    }

    public SensorList(Integer id) {
        this.id = id;
    }

    public SensorList(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorNode getNodeId() {
        return nodeId;
    }

    public void setNodeId(SensorNode nodeId) {
        this.nodeId = nodeId;
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
        if (!(object instanceof SensorList)) {
            return false;
        }
        SensorList other = (SensorList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.SensorList[ id=" + id + " ]";
    }

     public SensorNode getParent(){
        return nodeId;
        
    }
    void setParent(SensorNode parent) {
        this.nodeId=parent;
    }

  

    @XmlTransient
    public Collection<SensorData> getSensorDataCollection() {
        return sensorDataCollection;
    }

    public void setSensorDataCollection(Collection<SensorData> sensorDataCollection) {
        this.sensorDataCollection = sensorDataCollection;
    }

    

  
    
}
