package unnatkrushipranali.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(WeatherStatus.class)
public class WeatherStatus_ { 

    public static volatile SingularAttribute<WeatherStatus, Integer> id;
    public static volatile SingularAttribute<WeatherStatus, Double> windSpeed;
    public static volatile SingularAttribute<WeatherStatus, Double> humidity;
    public static volatile SingularAttribute<WeatherStatus, Double> pressure;
    public static volatile SingularAttribute<WeatherStatus, Double> sunLight;
    public static volatile SingularAttribute<WeatherStatus, String> windDirection;
    public static volatile SingularAttribute<WeatherStatus, Date> readingDate;
    public static volatile SingularAttribute<WeatherStatus, Date> readingTime;
    public static volatile SingularAttribute<WeatherStatus, Double> temperature;

}