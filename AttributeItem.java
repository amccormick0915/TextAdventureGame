import java.util.*;

//Serializable for Save and Load!
import java.io.Serializable;
    
    public class AttributeItem implements Item, Serializable
{
    private String item_name;
    
    //The points, THE ITEM instance created holds!
    private double points;
    
    //Names and Points! points are in order, of the ITEM Names!
    private String[] items = {"fruit", "golden apple", "apple", "rotten meat", "ATTCK herb", "DEF herb"};
    private int[] pts = {20, 25, 15, 1, 15, 15};
    
    //Index of the Item instance created!
    private int index;
    
    //Denotes whether the Item has been used in the game!
    private boolean used;
    
    
    
    //Randomly Generate the ITEM!
    public AttributeItem()
    {
        //Generates the random index!
        Random dice = new Random();
        index = dice.nextInt(items.length);
        
        //Store Item Name and Points depending on the randomly generated Index!
        //And setting the used to false!
        item_name = items[index];
        points = pts[index];
        used = false;
    }
    
   public double getItemPoint(){
        return points;
    }
    
    public String getItemName(){
        return item_name;
    }
    
    
    //Once the Item is used, The 'character' it is used upon gets Added HEALTH, ATTACK OR DEFENSE!
    //The boolean used is set true! So the characters won't be able to re-use again!
    public void setUse(Being character){
        
            if(index < 4){
                character.addHP(pts[index]);
                
                //SETSTATUS TRUE, sets the LIFE status of character to CONSCIOUS immediately anytime
                //An HP+ Item is used!
                character.setstatus(true);
            } else if (item_name.equals("ATTCK herb")){
                character.addATT(pts[index]);
            } else {
                character.addDEF(pts[index]);
            }
            
        used = true;
    }
    
    public boolean getState(){
        return used;
    }

}
