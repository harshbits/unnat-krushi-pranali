/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author harshbitss
 */
@Entity
@Table(name = "farmer_tbl", catalog = "samplejavafxapplication", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmerTbl.findAll", query = "SELECT f FROM FarmerTbl f"),
    @NamedQuery(name = "FarmerTbl.findById", query = "SELECT f FROM FarmerTbl f WHERE f.id = :id"),   
    @NamedQuery(name = "FarmerTbl.findByFirstName", query = "SELECT f FROM FarmerTbl f WHERE f.firstName = :firstName"),
    @NamedQuery(name = "FarmerTbl.findByMiddleName", query = "SELECT f FROM FarmerTbl f WHERE f.middleName = :middleName"),
    @NamedQuery(name = "FarmerTbl.findByLastName", query = "SELECT f FROM FarmerTbl f WHERE f.lastName = :lastName"),
    @NamedQuery(name = "FarmerTbl.findByBirthDay", query = "SELECT f FROM FarmerTbl f WHERE f.birthDay = :birthDay"),
    @NamedQuery(name = "FarmerTbl.findByPhone", query = "SELECT f FROM FarmerTbl f WHERE f.phone = :phone"),
    @NamedQuery(name = "FarmerTbl.findByStreet1", query = "SELECT f FROM FarmerTbl f WHERE f.street1 = :street1"),
    @NamedQuery(name = "FarmerTbl.findByStreet2", query = "SELECT f FROM FarmerTbl f WHERE f.street2 = :street2"),
    @NamedQuery(name = "FarmerTbl.findByCity", query = "SELECT f FROM FarmerTbl f WHERE f.city = :city"),
    @NamedQuery(name = "FarmerTbl.findByState", query = "SELECT f FROM FarmerTbl f WHERE f.state = :state"),
    @NamedQuery(name = "FarmerTbl.findByPin", query = "SELECT f FROM FarmerTbl f WHERE f.pin = :pin"),
    @NamedQuery(name = "FarmerTbl.findByCountry", query = "SELECT f FROM FarmerTbl f WHERE f.country = :country")})
public class FarmerTbl implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "middleName")
    private String middleName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "birthDay")
    @Temporal(TemporalType.DATE)
    private Date birthDay;
    @Column(name = "phone")
    private String phone;
    @Column(name = "street1")
    private String street1;
    @Column(name = "street2")
    private String street2;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "pin")
    private Integer pin;
    @Column(name = "country")
    private String country;    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "farmerId")
    private FarmTbl farmTbl;
    private static final long serialVersionUID = 1L;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farmerId")
    private Collection<FarmTbl> farmTblCollection;

    public FarmerTbl() {
    }

    public FarmerTbl(Integer id, String firstName){
        this.id=id;
        this.firstName=firstName;
        
    }
    public FarmerTbl(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
  

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        if (!(object instanceof FarmerTbl)) {
            return false;
        }
        FarmerTbl other = (FarmerTbl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.FarmerTbl[ id=" + id + " ]";
    }

    

    public FarmTbl getFarmTbl() {
        return farmTbl;
    }

    public void setFarmTbl(FarmTbl farmTbl) {
        this.farmTbl = farmTbl;
    }

    void addChild(FarmTbl child) {
        this.farmTblCollection.add(child);
        
    }

     @XmlTransient
    public Collection<FarmTbl> getFarmTblCollection() {
        return farmTblCollection;
    }

    public void setFarmTblCollection(Collection<FarmTbl> farmTblCollection) {
        this.farmTblCollection = farmTblCollection;
    }


    
    
}
