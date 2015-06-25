package unnatkrushipranali.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unnatkrushipranali.model.ClusterNodeReading;
import unnatkrushipranali.model.ReadingDataTemp;
import unnatkrushipranali.model.SensorNode;
import unnatkrushipranali.model.SolenoidValve;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(ClusterNode.class)
public class ClusterNode_ { 

    public static volatile SingularAttribute<ClusterNode, Integer> id;
    public static volatile SingularAttribute<ClusterNode, Double> last12Hours;
    public static volatile CollectionAttribute<ClusterNode, SensorNode> sensorNodeCollection;
    public static volatile SingularAttribute<ClusterNode, Double> last6Hours;
    public static volatile CollectionAttribute<ClusterNode, ReadingDataTemp> readingDataTempCollection;
    public static volatile SingularAttribute<ClusterNode, Double> finalReading;
    public static volatile CollectionAttribute<ClusterNode, SolenoidValve> solenoidValveCollection;
    public static volatile SingularAttribute<ClusterNode, Double> today;
    public static volatile SingularAttribute<ClusterNode, Double> lastWeek;
    public static volatile SingularAttribute<ClusterNode, String> clusterId;
    public static volatile CollectionAttribute<ClusterNode, ClusterNodeReading> clusterNodeReadingCollection;

}