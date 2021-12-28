import java.util.*;

//A Class that creates the MAP!
public class MaxiMap 
{
    //An array of the ROOMS!
    private MapRec[] mr;
    
    //An ArrayList that acts like a Matrix  for the MAP! 
    private ArrayList<ArrayList<MapRec>> matrixmap;
    
    //int that holds the maximum number of Characters!
    //Helps in controling the strength and health of the Monsters in each Room!
    //The higher the number of characters, thr stronger the monster!
    private int no_characters;
    
    
    public MaxiMap(int characters){
        no_characters = characters;
        generateMR();
    }
    
    public ArrayList<ArrayList<MapRec>> getMatMap(){
        return matrixmap;
    }
    
    
    //GENERATES THE MAP!
    //Creates a Linear Map first, then for each index of the Linear map, there will be at least ONE
    //MAPREC ROOM and at most 3 ROOMS!
    public void generateMR(){
        Random dice = new Random();
        
        //Creates the empty LINEAR ArrayList
        matrixmap = new ArrayList<>();
        
            for (int i = 0; i < (dice.nextInt(5)+5); i++) {
                //Creates the inner LIST that can hold either 1,2 or 3 MAPREC ROOMS!
                ArrayList<MapRec> linearMap = new ArrayList<>(); 
            
                //SIZE depends on the dice! 
                for (int k = 0; k < ( ((dice.nextInt(15)+1)>10)? 1: ((dice.nextInt(10)+1)>6)? 2 : 3) ; k++) {  
                    linearMap.add(makeMR(i+1, k));
                }
                
                //The INNER Arraylist is added to the LINEAR map!
                matrixmap.add(linearMap);
            }
    }
    
    
    //Creating the Room!
    public MapRec makeMR(int totalRooms, int roomNo){
            MapRec rec = new MapRec();
            rec.RoomNo = roomNo + 1;
            
            //Setting a randomly generated item, armor, and monster in the room!
            rec.roomItem = setAttItem();  
            rec.armore = setArm();
            rec.monmon = generateMONMON(totalRooms, roomNo);
            
        return rec;
    }
    
    //Creates an AttributeItem instance and returns it! RANDOMLY GENERATED!
    public AttributeItem setAttItem(){
        AttributeItem Ai = new AttributeItem();
        return Ai;
    }
    
    //Creates an ARMOR instance and returns it! RANDOMLY GENERATED!
    public Armor setArm(){
        Armor Arm = new Armor();
        return Arm;
    }
    
    //Randomly generates the Monster for the room!
    //It's values and states depend on the MAP size and number of Characters!
    public Monster generateMONMON(int totalRooms, int roomNo){
        Random dice = new Random();
        
        //Create a simple monster, used to get the Name!
        Monster monmon = new Monster();
        String[] namebank = monmon.namebanklength();
        String name = namebank[dice.nextInt(namebank.length)];
        
        //Setting the stats of the Monster!
        double attack = (((totalRooms * 5) + (roomNo * (roomNo/2))) * no_characters)/2;
        double defense = (totalRooms) * 5 + 15;
        double health = (totalRooms * 2 ) + (roomNo * .5) + (attack * 4) ;
        double rage = attack + attack * .5;
        
        //Creating the monster and passing it!
        Monster Realmonmon = new Monster(attack, defense, health, name, 0, rage); 
        return Realmonmon;
    }
}
