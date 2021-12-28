import java.util.*;


//One of the main classes, a subclass of superclass "Being"
//This is an object of type Human, that a user can choose to play with.
public class Human extends Being
{

    private String[] weapon = {"Long Sword","Katana", "Bow and Arrow","Poisonous Dagger"};
    private int weapon_no;
    

    public Human(){
        weapon_no = 0;
    }
    
    public Human(String na, int wepno, double at, double def, double hp){   
        super(na,at, def,hp);
        weapon_no = wepno;
    }
    
    public Human(String na, int wepno){   
        super(na,20, 20,100);
        weapon_no = wepno;
    }
    
    
    //This method overrides the superclass method. Where in some cases, depending on the user's weapon
    // an attack can miss the oponent. OR poison the oponen.
    public double getatt(){
        Random dice = new Random();
        
        if(weapon_no == 2 && dice.nextInt(10) < 2){
            //Signifies missed arrow shot!
            return 0;
        } else if (weapon_no == 3 && (dice.nextInt(20) > 10)) { 
            //This number returned will signify whether an opponent is poisoned by the user
            return -1;
        } 
        
        return super.getatt(0);
    }

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
    
    public String[] getwep(){
        return weapon;
    }
}
