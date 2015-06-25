package unnatkrushipranali.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unnatkrushipranali.model.SensorData;
import unnatkrushipranali.model.SensorNode;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(SensorList.class)
public class SensorList_ { 

    public static volatile SingularAttribute<SensorList, Integer> id;
    public static volatile SingularAttribute<SensorList, SensorNode> nodeId;
    public static volatile CollectionAttribute<SensorList, SensorData> sensorDataCollection;
    public static volatile SingularAttribute<SensorList, String> name;

}