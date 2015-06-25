package unnatkrushipranali.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unnatkrushipranali.model.ClusterNode;
import unnatkrushipranali.model.SensorList;
import unnatkrushipranali.model.SensorNodeData;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(SensorNode.class)
public class SensorNode_ { 

    public static volatile SingularAttribute<SensorNode, Integer> id;
    public static volatile CollectionAttribute<SensorNode, SensorList> sensorListCollection;
    public static volatile CollectionAttribute<SensorNode, SensorNodeData> sensorNodeDataCollection;
    public static volatile SingularAttribute<SensorNode, String> sensorId;
    public static volatile SingularAttribute<SensorNode, ClusterNode> clusterId;

}