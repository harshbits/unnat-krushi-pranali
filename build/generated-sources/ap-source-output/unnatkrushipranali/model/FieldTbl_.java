package unnatkrushipranali.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unnatkrushipranali.model.Cropdata;
import unnatkrushipranali.model.FarmTbl;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(FieldTbl.class)
public class FieldTbl_ { 

    public static volatile SingularAttribute<FieldTbl, Integer> id;
    public static volatile SingularAttribute<FieldTbl, FarmTbl> farmId;
    public static volatile SingularAttribute<FieldTbl, Double> legalArea;
    public static volatile SingularAttribute<FieldTbl, byte[]> fieldPhoto;
    public static volatile SingularAttribute<FieldTbl, Double> mappedArea;
    public static volatile CollectionAttribute<FieldTbl, Cropdata> cropdataCollection;
    public static volatile SingularAttribute<FieldTbl, String> fieldDescription;
    public static volatile SingularAttribute<FieldTbl, Double> tillableArea;
    public static volatile SingularAttribute<FieldTbl, String> farmRegion;

}