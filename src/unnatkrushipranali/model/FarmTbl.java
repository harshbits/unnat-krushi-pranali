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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author harshbitss
 */
@Entity
@Table(name = "farm_tbl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmTbl.findAll", query = "SELECT f FROM FarmTbl f"),
    @NamedQuery(name = "FarmTbl.findById", query = "SELECT f FROM FarmTbl f WHERE f.id = :id"),
    @NamedQuery(name = "FarmTbl.findByFarmDescription", query = "SELECT f FROM FarmTbl f WHERE f.farmDescription = :farmDescription"),
    @NamedQuery(name = "FarmTbl.findByMappedArea", query = "SELECT f FROM FarmTbl f WHERE f.mappedArea = :mappedArea"),
    @NamedQuery(name = "FarmTbl.findByLegalArea", query = "SELECT f FROM FarmTbl f WHERE f.legalArea = :legalArea"),
    @NamedQuery(name = "FarmTbl.findByTillableArea", query = "SELECT f FROM FarmTbl f WHERE f.tillableArea = :tillableArea"),
    @NamedQuery(name = "FarmTbl.findByKmapLocation", query = "SELECT f FROM FarmTbl f WHERE f.kmapLocation = :kmapLocation")})
public class FarmTbl implements Serializable {
    @Lob
    @Column(name = "farmPhoto")
    private byte[] farmPhoto;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "farmDescription")
    private String farmDescription;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mappedArea")
    private Double mappedArea;
    @Column(name = "legalArea")
    private Double legalArea;
    @Column(name = "tillableArea")
    private Double tillableArea;
    @Column(name = "kmapLocation")
    private String kmapLocation;
    @JoinColumn(name = "farmerId", referencedColumnName = "id")
    @OneToOne(optional = false)
    private FarmerTbl farmerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farmId")
    private Collection<FieldTbl> fieldTblCollection;

    public FarmTbl() {
    }

    public FarmTbl(Integer id) {
        this.id = id;
    }

    public FarmTbl(Integer id, String farmDescription) {
        this.id = id;
        this.farmDescription = farmDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFarmDescription() {
        return farmDescription;
    }

    public void setFarmDescription(String farmDescription) {
        this.farmDescription = farmDescription;
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

    public String getKmapLocation() {
        return kmapLocation;
    }

    public void setKmapLocation(String kmapLocation) {
        this.kmapLocation = kmapLocation;
    }

    public FarmerTbl getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(FarmerTbl farmerId) {
        this.farmerId = farmerId;
    }

    @XmlTransient
    public Collection<FieldTbl> getFieldTblCollection() {
        return fieldTblCollection;
    }

    public void setFieldTblCollection(Collection<FieldTbl> fieldTblCollection) {
        this.fieldTblCollection = fieldTblCollection;
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
        if (!(object instanceof FarmTbl)) {
            return false;
        }
        FarmTbl other = (FarmTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.FarmTbl[ id=" + id + " ]";
    }

    void addChild(FieldTbl child) {
        this.fieldTblCollection.add(child);
    }

     public FarmerTbl getParent(){
        return farmerId;
        
    }
    void setParent(FarmerTbl parent) {
        this.farmerId=parent;
    }

    public byte[] getFarmPhoto() {
        return farmPhoto;
    }

    public void setFarmPhoto(byte[] farmPhoto) {
        this.farmPhoto = farmPhoto;
    }

   
    
}
