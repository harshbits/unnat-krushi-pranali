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
@Table(name = "reading_data_temp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReadingDataTemp.findAll", query = "SELECT r FROM ReadingDataTemp r"),
    @NamedQuery(name = "ReadingDataTemp.findById", query = "SELECT r FROM ReadingDataTemp r WHERE r.id = :id"),
    @NamedQuery(name = "ReadingDataTemp.findByReading", query = "SELECT r FROM ReadingDataTemp r WHERE r.reading = :reading"),
    @NamedQuery(name = "ReadingDataTemp.findByReadingHour", query = "SELECT r FROM ReadingDataTemp r WHERE r.readingHour = :readingHour"),
    @NamedQuery(name = "ReadingDataTemp.findByReadingTime", query = "SELECT r FROM ReadingDataTemp r WHERE r.readingTime = :readingTime"),
    @NamedQuery(name = "ReadingDataTemp.findByReadingDate", query = "SELECT r FROM ReadingDataTemp r WHERE r.readingDate = :readingDate")})
public class ReadingDataTemp implements Serializable {
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
    @Column(name = "reading_date")
    @Temporal(TemporalType.DATE)
    private Date readingDate;
    @JoinColumn(name = "cluster_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ClusterNode clusterId;

    public ReadingDataTemp() {
    }

    public ReadingDataTemp(Integer id) {
        this.id = id;
    }

    public ReadingDataTemp(Integer id, double reading, int readingHour, Date readingTime, Date readingDate) {
        this.id = id;
        this.reading = reading;
        this.readingHour = readingHour;
        this.readingTime = readingTime;
        this.readingDate = readingDate;
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

    public Date getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(Date readingDate) {
        this.readingDate = readingDate;
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
        if (!(object instanceof ReadingDataTemp)) {
            return false;
        }
        ReadingDataTemp other = (ReadingDataTemp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.ReadingDataTemp[ id=" + id + " ]";
    }
    
}
