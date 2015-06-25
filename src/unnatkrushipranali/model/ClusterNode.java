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
@Table(name = "cluster_node", catalog = "samplejavafxapplication", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cluster_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClusterNode.findAll", query = "SELECT c FROM ClusterNode c"),
    @NamedQuery(name = "ClusterNode.findById", query = "SELECT c FROM ClusterNode c WHERE c.id = :id"),
    @NamedQuery(name = "ClusterNode.findByClusterId", query = "SELECT c FROM ClusterNode c WHERE c.clusterId = :clusterId")})

public class ClusterNode implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "last_6_hours")
    private Double last6Hours;
    @Column(name = "last_12_hours")
    private Double last12Hours;
    @Column(name = "today")
    private Double today;
    @Column(name = "last_week")
    private Double lastWeek;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clusterId")
    private Collection<ReadingDataTemp> readingDataTempCollection;
    @Basic(optional = false)
    @Column(name = "final_reading")
    private double finalReading;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clusterId")
    private Collection<ClusterNodeReading> clusterNodeReadingCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cluster_id")
    private String clusterId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clusterId")
    private Collection<SensorNode> sensorNodeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clusterId")
    private Collection<SolenoidValve> solenoidValveCollection;
   

    public ClusterNode() {
    }

    public ClusterNode(Integer id) {
        this.id = id;
    }

    public ClusterNode(Integer id, String clusterId) {
        this.id = id;
        this.clusterId = clusterId;
    }
    
     public ClusterNode(String clusterId,Double finalReading) {
        this.clusterId = clusterId;
        this.finalReading= finalReading;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    @XmlTransient
    public Collection<SensorNode> getSensorNodeCollection() {
        return sensorNodeCollection;
    }

    public void setSensorNodeCollection(Collection<SensorNode> sensorNodeCollection) {
        this.sensorNodeCollection = sensorNodeCollection;
    }
    
     @XmlTransient
    public Collection<SolenoidValve> getSolenoidValveCollection() {
        return solenoidValveCollection;
    }

    public void setSolenoidValveCollection(Collection<SolenoidValve> solenoidValveCollection) {
        this.solenoidValveCollection = solenoidValveCollection;
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
        if (!(object instanceof ClusterNode)) {
            return false;
        }
        ClusterNode other = (ClusterNode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.ClusterNode[ id=" + id + " ]";
    }
    
    void addChild(SensorNode child) {
       this.sensorNodeCollection.add(child);
    }

    void addChild(SolenoidValve child) {
        this.solenoidValveCollection.add(child);
    }

    public double getFinalReading() {
        return finalReading;
    }

    public void setFinalReading(double finalReading) {
        this.finalReading = finalReading;
    }

    @XmlTransient
    public Collection<ClusterNodeReading> getClusterNodeReadingCollection() {
        return clusterNodeReadingCollection;
    }

    public void setClusterNodeReadingCollection(Collection<ClusterNodeReading> clusterNodeReadingCollection) {
        this.clusterNodeReadingCollection = clusterNodeReadingCollection;
    }

    public Double getLast6Hours() {
        return last6Hours;
    }

    public void setLast6Hours(Double last6Hours) {
        this.last6Hours = last6Hours;
    }

    public Double getLast12Hours() {
        return last12Hours;
    }

    public void setLast12Hours(Double last12Hours) {
        this.last12Hours = last12Hours;
    }

    public Double getToday() {
        return today;
    }

    public void setToday(Double today) {
        this.today = today;
    }

    public Double getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(Double lastWeek) {
        this.lastWeek = lastWeek;
    }

    @XmlTransient
    public Collection<ReadingDataTemp> getReadingDataTempCollection() {
        return readingDataTempCollection;
    }

    public void setReadingDataTempCollection(Collection<ReadingDataTemp> readingDataTempCollection) {
        this.readingDataTempCollection = readingDataTempCollection;
    }


   


  
    
}
