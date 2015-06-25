/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "field_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FieldTbl.findAll", query = "SELECT f FROM FieldTbl f"),
    @NamedQuery(name = "FieldTbl.findById", query = "SELECT f FROM FieldTbl f WHERE f.id = :id"),
    @NamedQuery(name = "FieldTbl.findByFieldDescription", query = "SELECT f FROM FieldTbl f WHERE f.fieldDescription = :fieldDescription"),
    @NamedQuery(name = "FieldTbl.findByFarmRegion", query = "SELECT f FROM FieldTbl f WHERE f.farmRegion = :farmRegion"),
    @NamedQuery(name = "FieldTbl.findByMappedArea", query = "SELECT f FROM FieldTbl f WHERE f.mappedArea = :mappedArea"),
    @NamedQuery(name = "FieldTbl.findByLegalArea", query = "SELECT f FROM FieldTbl f WHERE f.legalArea = :legalArea"),
    @NamedQuery(name = "FieldTbl.findByTillableArea", query = "SELECT f FROM FieldTbl f WHERE f.tillableArea = :tillableArea")})
public class FieldTbl implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mappedArea")
    private Double mappedArea;
    @Column(name = "legalArea")
    private Double legalArea;
    @Column(name = "tillableArea")
    private Double tillableArea;
    @Lob
    @Column(name = "fieldPhoto")
    private byte[] fieldPhoto;
    @OneToMany(mappedBy = "fieldId")
    private Collection<Cropdata> cropdataCollection;
    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fieldDescription")
    private String fieldDescription;
    @Basic(optional = false)
    @Column(name = "farmRegion")
    private String farmRegion;
    @JoinColumn(name = "farmId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FarmTbl farmId;

    public FieldTbl() {
    }

    public FieldTbl(Integer id) {
        this.id = id;
    }

    public FieldTbl(Integer id, String fieldDescription, String farmRegion, double mappedArea, double legalArea, double tillableArea, byte[] fieldPhoto) {
        this.id = id;
        this.fieldDescription = fieldDescription;
        this.farmRegion = farmRegion;
        this.mappedArea = mappedArea;
        this.legalArea = legalArea;
        this.tillableArea = tillableArea;
        this.fieldPhoto = fieldPhoto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getFarmRegion() {
        return farmRegion;
    }

    public void setFarmRegion(String farmRegion) {
        this.farmRegion = farmRegion;
    }

    public FarmTbl getFarmId() {
        return farmId;
    }

    public void setFarmId(FarmTbl farmId) {
        this.farmId = farmId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FieldTbl)) {
            return false;
        }
        FieldTbl other = (FieldTbl) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.FieldTbl[ id=" + getId() + " ]";
    }

    void setParent(FarmTbl parent) {
        this.setFarmId(parent);
    }
    
    public FarmTbl getParent(){
        return getFarmId();
        
    }

    public Double getMappedArea() {
        return mappedArea;
    }

    public void setMappedArea(Double mappedArea) {
        this.mappedArea = mappedArea;
    }

    public Double getLegalArea() {
        return legalArea;
    }

    public void setLegalArea(Double legalArea) {
        this.legalArea = legalArea;
    }

    public Double getTillableArea() {
        return tillableArea;
    }

    public void setTillableArea(Double tillableArea) {
        this.tillableArea = tillableArea;
    }

    public byte[] getFieldPhoto() {
        return fieldPhoto;
    }

    public void setFieldPhoto(byte[] fieldPhoto) {
        this.fieldPhoto = fieldPhoto;
    }

    @XmlTransient
    public Collection<Cropdata> getCropdataCollection() {
        return cropdataCollection;
    }

    public void setCropdataCollection(Collection<Cropdata> cropdataCollection) {
        this.cropdataCollection = cropdataCollection;
    }

    /**
     * @param fieldPhoto the fieldPhoto to set
     */
    
    
}
