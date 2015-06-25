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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author harshbitss
 */
@Entity
@Table(name = "cropdata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cropdata.findAll", query = "SELECT c FROM Cropdata c"),
    @NamedQuery(name = "Cropdata.findById", query = "SELECT c FROM Cropdata c WHERE c.id = :id"),
    @NamedQuery(name = "Cropdata.findByCropAtmosphere", query = "SELECT c FROM Cropdata c WHERE c.cropAtmosphere = :cropAtmosphere"),
    @NamedQuery(name = "Cropdata.findByCropDuration", query = "SELECT c FROM Cropdata c WHERE c.cropDuration = :cropDuration"),
    @NamedQuery(name = "Cropdata.findByCropMoisture", query = "SELECT c FROM Cropdata c WHERE c.cropMoisture = :cropMoisture"),
    @NamedQuery(name = "Cropdata.findByCropName", query = "SELECT c FROM Cropdata c WHERE c.cropName = :cropName"),
    @NamedQuery(name = "Cropdata.findByCropRegion", query = "SELECT c FROM Cropdata c WHERE c.cropRegion = :cropRegion"),
    @NamedQuery(name = "Cropdata.findByCropSeason", query = "SELECT c FROM Cropdata c WHERE c.cropSeason = :cropSeason"),
    @NamedQuery(name = "Cropdata.findByCropSoilType", query = "SELECT c FROM Cropdata c WHERE c.cropSoilType = :cropSoilType"),
    @NamedQuery(name = "Cropdata.findBySuggestedFertilizer", query = "SELECT c FROM Cropdata c WHERE c.suggestedFertilizer = :suggestedFertilizer")})
public class Cropdata implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cropAtmosphere")
    private String cropAtmosphere;
    @Column(name = "cropDuration")
    private String cropDuration;
    @Column(name = "cropMoisture")
    private String cropMoisture;
    @Column(name = "cropName")
    private String cropName;
    @Column(name = "cropRegion")
    private String cropRegion;
    @Column(name = "cropSeason")
    private String cropSeason;
    @Column(name = "cropSoilType")
    private String cropSoilType;
    @Column(name = "suggestedFertilizer")
    private String suggestedFertilizer;
    @JoinColumn(name = "fieldId", referencedColumnName = "id")
    @ManyToOne
    private FieldTbl fieldId;

    public Cropdata() {
    }

    public Cropdata(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCropAtmosphere() {
        return cropAtmosphere;
    }

    public void setCropAtmosphere(String cropAtmosphere) {
        this.cropAtmosphere = cropAtmosphere;
    }

    public String getCropDuration() {
        return cropDuration;
    }

    public void setCropDuration(String cropDuration) {
        this.cropDuration = cropDuration;
    }

    public String getCropMoisture() {
        return cropMoisture;
    }

    public void setCropMoisture(String cropMoisture) {
        this.cropMoisture = cropMoisture;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropRegion() {
        return cropRegion;
    }

    public void setCropRegion(String cropRegion) {
        this.cropRegion = cropRegion;
    }

    public String getCropSeason() {
        return cropSeason;
    }

    public void setCropSeason(String cropSeason) {
        this.cropSeason = cropSeason;
    }

    public String getCropSoilType() {
        return cropSoilType;
    }

    public void setCropSoilType(String cropSoilType) {
        this.cropSoilType = cropSoilType;
    }

    public String getSuggestedFertilizer() {
        return suggestedFertilizer;
    }

    public void setSuggestedFertilizer(String suggestedFertilizer) {
        this.suggestedFertilizer = suggestedFertilizer;
    }

    public FieldTbl getFieldId() {
        return fieldId;
    }

    public void setFieldId(FieldTbl fieldId) {
        this.fieldId = fieldId;
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
        if (!(object instanceof Cropdata)) {
            return false;
        }
        Cropdata other = (Cropdata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.Cropdata[ id=" + id + " ]";
    }
    
}
