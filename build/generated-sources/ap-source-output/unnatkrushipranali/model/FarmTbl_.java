package unnatkrushipranali.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unnatkrushipranali.model.FarmerTbl;
import unnatkrushipranali.model.FieldTbl;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(FarmTbl.class)
public class FarmTbl_ { 

    public static volatile SingularAttribute<FarmTbl, Integer> id;
    public static volatile SingularAttribute<FarmTbl, FarmerTbl> farmerId;
    public static volatile SingularAttribute<FarmTbl, String> kmapLocation;
    public static volatile SingularAttribute<FarmTbl, String> farmDescription;
    public static volatile SingularAttribute<FarmTbl, Double> legalArea;
    public static volatile SingularAttribute<FarmTbl, byte[]> farmPhoto;
    public static volatile SingularAttribute<FarmTbl, Double> mappedArea;
    public static volatile CollectionAttribute<FarmTbl, FieldTbl> fieldTblCollection;
    public static volatile SingularAttribute<FarmTbl, Double> tillableArea;

}