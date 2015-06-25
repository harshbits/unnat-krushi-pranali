package unnatkrushipranali.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unnatkrushipranali.model.SensorNode;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(SensorNodeData.class)
public class SensorNodeData_ { 

    public static volatile SingularAttribute<SensorNodeData, Integer> id;
    public static volatile SingularAttribute<SensorNodeData, Double> reading;
    public static volatile SingularAttribute<SensorNodeData, SensorNode> sensorId;
    public static volatile SingularAttribute<SensorNodeData, Date> data;
    public static volatile SingularAttribute<SensorNodeData, Date> readingTime;
    public static volatile SingularAttribute<SensorNodeData, Integer> readingHour;

}