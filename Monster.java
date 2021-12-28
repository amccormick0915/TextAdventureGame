import java.util.*;
import java.text.DecimalFormat;


//One of the main classes, a subclass of superclass "Being"
//This is an object of type Monster, that a user can choose to play with.

public class Monster extends Being
{
    //A weapion name bank
    private String[] weapon = {"Monster Fists","Wolves", "Monster Army", "Magical Sword"};
    
    //A monster name bank, that will be used for naming randomly generated monsters in the map
    private String[] namebank = {"Goblin", "Zombie", "Vampire", "Hulk"};
    
    private int weapon_no;
    
    private double self_inf;
    
    //A special attack only used by monsters, used for polymorphism in getatt();
    private double rage_attack;
    
    public Monster(){
        weapon_no = 0;
    }
    
    public Monster(String na, int wepno){   
        super(na,40, 10,200);
        weapon_no = wepno;
        rage_attack = 60;
        super.setstatus(true);
    }
    
    public Monster( double att, double def, double hp, String na, int wep, double rage) //int totalRooms, int roomNo
    {
        super(na,att, def, hp);
        weapon_no =wep;
        rage_attack = rage;
        super.setstatus(true);
    }

    public Monster(String na, int wepno,double at, double def, double hp, double rage){
        super(na,at, def,hp);
        weapon_no = wepno;
        rage_attack = rage;
    }
    
    public int getwep_no(){
        return weapon_no;
    }
    
    public double getrage(){
        double newattack;
        DecimalFormat df = new DecimalFormat("#.##");      
        newattack = Double.valueOf(df.format(rage_attack));
        return newattack;
    }
    
    //MEthod that reduces Monster health
    public void sethp(double self_inflict){
        super.sethp(super.gethp() - self_inflict);
    }
    
    public String getwep_name(int weno){
        return weapon[weno];
    }
    
    public String getwep_name(){
        return weapon[weapon_no];
    }
    
    public double getinfl(){
        DecimalFormat df = new DecimalFormat("#.##");      
        return Double.valueOf(df.format(self_inf));
    }
    
    //This method overrides the superclass method. Rather than returning the normal "attack points" 
    //the method return a special attack after meeting certain conditions AND reduces the 
    //monster's health 
    public double getatt(){
        Random dice = new Random();
        self_inf = super.getatt(0) * .03;
        
       if( dice.nextInt(18) > 2 && super.gethp() < (super.getatt(0) * .25)){
           return -1;
        } else {
            return super.getatt(0);
        } 
    }

    public double getattDISP(){
        return super.getatt(0);
    }
    
    public String[] getwep(){
        return weapon;
    }
    
    public String[] listweplength(){
        return weapon;
    }
    
    public String[] namebanklength(){
        return namebank;
    }
}
