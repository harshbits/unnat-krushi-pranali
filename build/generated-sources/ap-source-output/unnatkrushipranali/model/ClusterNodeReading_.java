package unnatkrushipranali.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unnatkrushipranali.model.ClusterNode;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(ClusterNodeReading.class)
public class ClusterNodeReading_ { 

    public static volatile SingularAttribute<ClusterNodeReading, Integer> id;
    public static volatile SingularAttribute<ClusterNodeReading, Double> reading;
    public static volatile SingularAttribute<ClusterNodeReading, Date> readingDate;
    public static volatile SingularAttribute<ClusterNodeReading, Date> readingTime;
    public static volatile SingularAttribute<ClusterNodeReading, ClusterNode> clusterId;
    public static volatile SingularAttribute<ClusterNodeReading, Integer> readingHour;

}