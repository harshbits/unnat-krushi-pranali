package unnatkrushipranali.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unnatkrushipranali.model.FarmTbl;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-03T03:49:05")
@StaticMetamodel(FarmerTbl.class)
public class FarmerTbl_ { 

    public static volatile SingularAttribute<FarmerTbl, Integer> id;
    public static volatile SingularAttribute<FarmerTbl, String> street2;
    public static volatile SingularAttribute<FarmerTbl, String> middleName;
    public static volatile SingularAttribute<FarmerTbl, String> street1;
    public static volatile SingularAttribute<FarmerTbl, String> lastName;
    public static volatile SingularAttribute<FarmerTbl, String> phone;
    public static volatile SingularAttribute<FarmerTbl, FarmTbl> farmTbl;
    public static volatile SingularAttribute<FarmerTbl, Integer> pin;
    public static volatile SingularAttribute<FarmerTbl, String> state;
    public static volatile SingularAttribute<FarmerTbl, Date> birthDay;
    public static volatile SingularAttribute<FarmerTbl, String> firstName;
    public static volatile CollectionAttribute<FarmerTbl, FarmTbl> farmTblCollection;
    public static volatile SingularAttribute<FarmerTbl, String> country;
    public static volatile SingularAttribute<FarmerTbl, String> city;

}