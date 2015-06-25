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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author harshbitss
 */
@Entity
@Table(name = "sensor_node", catalog = "samplejavafxapplication", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"sensor_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SensorNode.findAll", query = "SELECT s FROM SensorNode s"),
    @NamedQuery(name = "SensorNode.findById", query = "SELECT s FROM SensorNode s WHERE s.id = :id"),
    @NamedQuery(name = "SensorNode.findBySensorId", query = "SELECT s FROM SensorNode s WHERE s.sensorId = :sensorId")})

public class SensorNode implements Serializable{
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sensorId")
    private Collection<SensorNodeData> sensorNodeDataCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "sensor_id")
    private String sensorId;
    @JoinColumn(name = "cluster_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClusterNode clusterId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nodeId")
    private Collection<SensorList> sensorListCollection;

    public SensorNode() {
    }

    public SensorNode(Integer id) {
        this.id = id;
    }

    public SensorNode(Integer id, String sensorId) {
        this.id = id;
        this.sensorId = sensorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public ClusterNode getClusterId() {
        return clusterId;
    }

    public void setClusterId(ClusterNode clusterId) {
        this.clusterId = clusterId;
    }

    @XmlTransient
    public Collection<SensorList> getSensorListCollection() {
        return sensorListCollection;
    }

    public void setSensorListCollection(Collection<SensorList> sensorListCollection) {
        this.sensorListCollection = sensorListCollection;
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
        if (!(object instanceof SensorNode)) {
            return false;
        }
        SensorNode other = (SensorNode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.SensorNode[ id=" + id + " ]";
    }

    public ClusterNode getParent(){
        return clusterId;
        
    }
    void setParent(ClusterNode parent) {
        this.clusterId=parent;
    }

    void addChild(SensorList child) {
       this.sensorListCollection.add(child);
    }

    @XmlTransient
    public Collection<SensorNodeData> getSensorNodeDataCollection() {
        return sensorNodeDataCollection;
    }

    public void setSensorNodeDataCollection(Collection<SensorNodeData> sensorNodeDataCollection) {
        this.sensorNodeDataCollection = sensorNodeDataCollection;
    }

   
    

  
}
