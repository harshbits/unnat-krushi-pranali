package unnatkrushipranali.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unnatkrushipranali.model.SensorList;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(SensorData.class)
public class SensorData_ { 

    public static volatile SingularAttribute<SensorData, Integer> id;
    public static volatile SingularAttribute<SensorData, Double> reading;
    public static volatile SingularAttribute<SensorData, SensorList> sensorsId;
    public static volatile SingularAttribute<SensorData, Date> readingDate;
    public static volatile SingularAttribute<SensorData, Date> readingTime;
    public static volatile SingularAttribute<SensorData, Integer> readingHour;

}