package unnatkrushipranali.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unnatkrushipranali.model.FieldTbl;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(Cropdata.class)
public class Cropdata_ { 

    public static volatile SingularAttribute<Cropdata, Integer> id;
    public static volatile SingularAttribute<Cropdata, String> cropDuration;
    public static volatile SingularAttribute<Cropdata, String> cropMoisture;
    public static volatile SingularAttribute<Cropdata, String> cropSoilType;
    public static volatile SingularAttribute<Cropdata, String> cropRegion;
    public static volatile SingularAttribute<Cropdata, String> suggestedFertilizer;
    public static volatile SingularAttribute<Cropdata, String> cropSeason;
    public static volatile SingularAttribute<Cropdata, String> cropName;
    public static volatile SingularAttribute<Cropdata, FieldTbl> fieldId;
    public static volatile SingularAttribute<Cropdata, String> cropAtmosphere;

}