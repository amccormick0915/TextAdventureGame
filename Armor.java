import java.util.*;

//Serializable for Save and Load!
import java.io.Serializable;

public class Armor implements Item, Serializable 
{               
    private String armor_name;
    
    //The points, THE ARMOR instance created holds!
    private double points; 
    
    //Names and Points! points are in order, of the Armor Names!
    private String[] ArmorNameBank = {"silver plate", "golden plate", "cursed plate", "fabric"};
    private int[] ptsBank = {20, 35, -5, 0};
    
    //Index of the Armor instance created!
    private int index;
    
    //Denotes whether the Armor has been used in the game!
    private boolean used;
    
    
    //Randomly Generate the Armor!
    public Armor()
    {
        //Generates the random index!
        Random dice = new Random();
        index = dice.nextInt(ArmorNameBank.length);
        
        //Store Armor Name and Points depending on the randomly generated Index!
        //And setting the used to false!
        armor_name = ArmorNameBank[index];
        points = ptsBank[index];
        used = false;
    }

    public double getItemPoint(){
        return points;
    }
    
    public String getItemName(){
        return armor_name;
    }

    public boolean getState(){
        return used;
    }
    
    //Once the Armor is used, The character it is used upon gets Added DEFENSE!
    //The boolean used is set true! So the characters won't be able to re-use again!
    public void setUse(Being character){
        character.addDEF(ptsBank[index]);
        used = true;
    }
    
}
