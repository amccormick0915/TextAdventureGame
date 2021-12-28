// The superclass for the important subclasses, Monster, Human, and Magical
import java.util.*;
import java.text.DecimalFormat;


//For Saving and Loading the this superclass and its subclasses!
import java.io.Serializable;

public class Being implements Serializable 
{  
    private String name;
    
    private double attack;
    private double defense;
    private double health;
    
    private boolean status;
    private boolean poisonstatus;
    
    
    
    public Being(){
        name = "Unknown";
        attack = 10;
        defense = 50;
    }
    
    public Being(String na, double att, double def, double hp){
        name = na;
        defense = def;
        attack = att;
        health = hp;
        status = true;
    }
    
    public String getName(){
        return name;
    }
    
    //Getters for the attack! TWO types made! 
    //AS the subclasses OVERRIDE the normal getatt() method, getatt(int o) was made
    //TO RETURN THE BASIC ATTACK POINT OF THE SUBCLASS CHARACTER!
    //THE OVERWRITTEN getatt() methods return certain doubles that tell the 
    //System game something else!
    public double getatt(int o){
        double newattack;
        DecimalFormat df = new DecimalFormat("#.##");      
        newattack = Double.valueOf(df.format(attack));
        return newattack;
    }
    
    public double getatt(){
        double newattack;
        DecimalFormat df = new DecimalFormat("#.##");      
        newattack = Double.valueOf(df.format(attack));
        return newattack;
    }
    
    public double getdef(){
        double newdefense;
        DecimalFormat df = new DecimalFormat("#.##");      
        newdefense = Double.valueOf(df.format(defense));
        return newdefense;
    }
    
    public double gethp(){
        double newhealth;
        DecimalFormat df = new DecimalFormat("#.##");      
        newhealth = Double.valueOf(df.format(health));
        return newhealth;
    }
    
    
    //CHECKS whether the Being is poisoned!
    //Only the MONSTER subclass uses it in the whole program, if checked int CLASS FILE 'GAMEFRAME'!
    public void poisoncheck(){
        if (poisonstatus){
            health -= (health * .1);
        }
    }
    
    
    //THIS METHOD USED IN BATTLE! 
    //SETS the TARGET'S HP, depending on the OPPONENT TYPE! AND ATTACK!
    //IT returns a String, A String that holds all TEXT data of the BATTLE'S RESULT!
    public String sethp(double en_attack, Being character){ 
        
        DecimalFormat df = new DecimalFormat("#.##");  
        String message = "";
        String endmessage = "";
        double damage = 0;
        
            //IF opponent is HUMAN, there are three possible events that occur!
            //If they have a "Bow and Arrow" for a weapon, they can miss their attack and thus DO NO DAMAGE TO TARGET!
            //If they use "Poisonous Dagger" they can POISON the TARGET and set the target poison status to TRUE!
            if(character instanceof Human && (en_attack == 0 || en_attack == -1) && !poisonstatus  ){
                
                //CHECKS whether opponent has dagger, whether target is NOT poisoned and if the attack is -1 VALUE!
                //VALUE -1 means they POISON the TARGET!
                if((  ( (Human)character).getwep_name().equals("Poisonous Dagger") && (en_attack == -1)) ){
                        poisonstatus = true;
                        message += name + " has been poisoned by " + character.getName() + "!<br>";
                        damage = character.getatt(0);
                        
                        
                 //CHECKS whether Opponent holds Arrows and if ATTACK VALUE IS 0!
                 //VALUE 0 means they miss the shot!
                } else if (((Human)character).getwep_name().equals("Bow and Arrow") && (en_attack == 0) ) {
                        message += character.getName() + " missed thier shot with their " + ((Human)character).getwep_name() + " !<br>No damage was dealt!";
                        damage = 0;
                }
                
                
            // IF its a Magical OPPONENT with an attack that returns VALUE -1, then
            //A MAGIC ATTACK WOULD BE USED!
            } else if (character instanceof Magical && (en_attack == -1) ){
                    message += character.getName() + " uses a MAGIC ATTACK!"; 
                    damage = (((Magical)character).getmagic_att());
                
            
            //If the Opponent is a Monster and their ATTACK value is -1, they use their special RAGE ATTACK!
            //AND they inflict damage on themselves 
            }else if (character instanceof Monster && (en_attack == -1)){
                double self_damage = ((Monster)character).getinfl();
                message += character.getName() + " uses RAGE ATTACK on " + name + " while losing "+ self_damage +"!";
                
                    if(self_damage > character.gethp()){
                        ((Monster)character).sethp(character.gethp());
                    } else {
                         ((Monster)character).sethp(self_damage);
                    }
                
                damage = (((Monster)character).getrage());
                
            } else if ((health - en_attack) <= 0){
                damage = health;
                health = 0;
                status = false;
                
            } else {
                damage = en_attack + (defense * .2);
            }
        
            //End message checks whether the Target is UNCONSCIOUS!
            endmessage = damageCHECK(damage, endmessage);
            
        return message + "<br>" + character.getName() +" deals " + Double.valueOf(df.format(damage)) + " to " + name + "<br>" + endmessage;
    }
    
    
    //Checks the latest health of the Target character and returns a String denoting their LIFE STATUS
    public String damageCHECK(double damage, String message){
        if (damage > health){
            message += "<br>" + name + " is now UNCONCIOUS!";
            health = 0;
            status = false;
        } else {
            health = health -damage;
        }
        return message;
    }
    
    
    //The following are the methods used in accordance with Attribute Items and Armor
    //THEY UPDATE CHARACTER STATUSES!
    public void addHP(int points){
        health += points;
    }    
    
    public void addATT(int points){
        attack += points;
    }    
    
    public void addDEF(int points){
        defense += points;
    }    
    
    
    //SETTERS FOR HP andthe statuses!
    public void sethp(double newhealth){
       health = newhealth;
    }

    public void setstatus(boolean newstat){
        status = newstat;
    }
    
    public void setpstatus(boolean newstat){
        poisonstatus = newstat;
    }
    
    
    //Getters for the LIFE status and POISON status!
    public boolean getstatus(){
        return status;
    }
    
    public String getstatusStr(){
        if(status){
            return "CONSCIOUS";
        }
        return "UNCONSCIOUS";
    }
    
    public boolean getpstatus(){
        return poisonstatus;
    }
    
}
