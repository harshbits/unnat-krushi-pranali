/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author harshbitss
 */
@Entity
@Table(name = "sensor_node_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SensorNodeData.findAll", query = "SELECT s FROM SensorNodeData s"),
    @NamedQuery(name = "SensorNodeData.findById", query = "SELECT s FROM SensorNodeData s WHERE s.id = :id"),
    @NamedQuery(name = "SensorNodeData.findByReading", query = "SELECT s FROM SensorNodeData s WHERE s.reading = :reading"),
    @NamedQuery(name = "SensorNodeData.findByReadingHour", query = "SELECT s FROM SensorNodeData s WHERE s.readingHour = :readingHour"),
    @NamedQuery(name = "SensorNodeData.findByReadingTime", query = "SELECT s FROM SensorNodeData s WHERE s.readingTime = :readingTime"),
    @NamedQuery(name = "SensorNodeData.findByData", query = "SELECT s FROM SensorNodeData s WHERE s.data = :data")})
public class SensorNodeData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "reading")
    private double reading;
    @Basic(optional = false)
    @Column(name = "reading_hour")
    private int readingHour;
    @Basic(optional = false)
    @Column(name = "reading_time")
    @Temporal(TemporalType.TIME)
    private Date readingTime;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "sensor_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private SensorNode sensorId;

    public SensorNodeData() {
    }

    public SensorNodeData(Integer id) {
        this.id = id;
    }

    public SensorNodeData(Integer id, double reading, int readingHour, Date readingTime, Date data) {
        this.id = id;
        this.reading = reading;
        this.readingHour = readingHour;
        this.readingTime = readingTime;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getReading() {
        return reading;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }

    public int getReadingHour() {
        return readingHour;
    }

    public void setReadingHour(int readingHour) {
        this.readingHour = readingHour;
    }

    public Date getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(Date readingTime) {
        this.readingTime = readingTime;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public SensorNode getSensorId() {
        return sensorId;
    }

    public void setSensorId(SensorNode sensorId) {
        this.sensorId = sensorId;
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
        if (!(object instanceof SensorNodeData)) {
            return false;
        }
        SensorNodeData other = (SensorNodeData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.SensorNodeData[ id=" + id + " ]";
    }
    
}
