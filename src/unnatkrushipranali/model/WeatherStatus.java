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
@Table(name = "weather_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeatherStatus.findAll", query = "SELECT w FROM WeatherStatus w"),
    @NamedQuery(name = "WeatherStatus.findById", query = "SELECT w FROM WeatherStatus w WHERE w.id = :id"),
    @NamedQuery(name = "WeatherStatus.findByTemperature", query = "SELECT w FROM WeatherStatus w WHERE w.temperature = :temperature"),
    @NamedQuery(name = "WeatherStatus.findByHumidity", query = "SELECT w FROM WeatherStatus w WHERE w.humidity = :humidity"),
    @NamedQuery(name = "WeatherStatus.findByPressure", query = "SELECT w FROM WeatherStatus w WHERE w.pressure = :pressure"),
    @NamedQuery(name = "WeatherStatus.findByWindSpeed", query = "SELECT w FROM WeatherStatus w WHERE w.windSpeed = :windSpeed"),
    @NamedQuery(name = "WeatherStatus.findByWindDirection", query = "SELECT w FROM WeatherStatus w WHERE w.windDirection = :windDirection"),
    @NamedQuery(name = "WeatherStatus.findBySunLight", query = "SELECT w FROM WeatherStatus w WHERE w.sunLight = :sunLight"),
    @NamedQuery(name = "WeatherStatus.findByReadingTime", query = "SELECT w FROM WeatherStatus w WHERE w.readingTime = :readingTime"),
    @NamedQuery(name = "WeatherStatus.findByReadingDate", query = "SELECT w FROM WeatherStatus w WHERE w.readingDate = :readingDate")})
public class WeatherStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "temperature")
    private Double temperature;
    @Column(name = "humidity")
    private Double humidity;
    @Column(name = "pressure")
    private Double pressure;
    @Column(name = "windSpeed")
    private Double windSpeed;
    @Column(name = "windDirection")
    private String windDirection;
    @Column(name = "sunLight")
    private Double sunLight;
    @Basic(optional = false)
    @Column(name = "readingTime")
    @Temporal(TemporalType.TIME)
    private Date readingTime;
    @Basic(optional = false)
    @Column(name = "readingDate")
    @Temporal(TemporalType.DATE)
    private Date readingDate;

    public WeatherStatus() {
    }

    public WeatherStatus(Integer id) {
        this.id = id;
    }

    public WeatherStatus(Integer id, Date readingTime, Date readingDate) {
        this.id = id;
        this.readingTime = readingTime;
        this.readingDate = readingDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public Double getSunLight() {
        return sunLight;
    }

    public void setSunLight(Double sunLight) {
        this.sunLight = sunLight;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeatherStatus)) {
            return false;
        }
        WeatherStatus other = (WeatherStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unnatkrushipranali.model.WeatherStatus[ id=" + id + " ]";
    }
    
}
