import java.util.*;


//One of the main classes, a subclass of superclass "Being"
//This is an object of type Magical, that a user can choose to play with.

public class Magical extends Being
{
    //Weapon name bank for magical users
    private String[] weapon = {"Long Staff","Broom", "Scythe", "Magical Familiar"};
    
    private int weapon_no;
    
    
    //both used to override getatt(), as magical users also have magic attacks
    private double magic_att;
    private double mana;
   
    public Magical()
    {
        weapon_no = 0;
    }
    
    public Magical(String na, int wepno)
    {   
        super(na,15, 25,180);
        magic_att = 30;
        mana =  300;
        weapon_no = wepno;
    }
    
    public Magical(String na, int wepno,double at, double def, double hp, double mag_att, double mana)
    {
        super(na,at, def,hp);
        weapon_no = wepno;
        magic_att = mag_att;
        mana =  mana;
    }
    

    
    //This method overrides the superclass method. Rather than returning the normal "attack points" 
    //the method return a magical attack after meeting certain conditions AND reduces the 
    //user's mana
    public double getatt(){
       Random dice = new Random();
        
       if( dice.nextInt(10) > 5 && mana > (magic_att)  ){
           mana = mana - magic_att;
           return -1;
        } else {
            return super.getatt(0);
        } 
    }
    
    //Used for diplaying the normal attack rather than the special one!
    public double getattDISP(){
        return super.getatt(0);
    }

    public int getwep_no(){
        return weapon_no;
    }
    
    public String getwep_name(int weno){
        return weapon[weno];
    }
    
    public String getwep_name(){
        return weapon[weapon_no];
    }
    
    public double getmagic_att(){
        return magic_att;
    }
    
    public double getmana(){
        return mana;
    }
   
    public String[] getwep(){
        return weapon;
    }
}