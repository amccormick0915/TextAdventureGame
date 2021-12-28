//Import serialization used for Saving the Objects later on!
import java.io.Serializable;


//A Record of the Room Properties! 
class MapRec implements Serializable{ 
    int RoomNo;
    String RoomName;
    AttributeItem roomItem;
    Armor armore;
    Monster monmon;
}