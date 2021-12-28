import java.awt.*;      
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;


//GUI FOR MAPS AND ENEMY MONSTERS!
class BoardMap extends JPanel {
    
    //Stores the ArrayList matrix of MapRec Rooms
    private ArrayList<ArrayList<MapRec>> MAP;
    
    //GUIOpt that deals with the Layout!
    private GUIOpt opt;
    private GridBagConstraints constraint;
    private GridBagLayout thelayout;
    
    //Integers thatdenote WHICH Level and which Room to move to through the ARRAYLIST MATRIX
    private JPanel RoomNo;
    private String LRroom;
    
    //ArrayList of THE MATRIX ROOM BUT, GUI VERSION FOR DISPLAY ONLY!
    private ArrayList<ArrayList<JPanel>> Rooms;
    
    
    //Once called, initialises most class variables
    public BoardMap(ArrayList<ArrayList<MapRec>> map){
        MAP = map;
      
        //initialise Layout values!
        opt = new GUIOpt();
        constraint = new GridBagConstraints();
        thelayout = new GridBagLayout();
        
        //Initialise the PANELS OF THE ROOMS
        Rooms = new ArrayList<>(); 
        
        
        //This method creates the JPanel ROOMS!
        RoomToPanelsConv();
    }
     
    
    //Where we make JPanels with ROOM DETAILS!
    //Creation is contolled by MAP size! (SIZE of the generated MAP in the beginning of the game!)
    public void RoomToPanelsConv(){ 
        
        for(int i = 0; i < MAP.size(); i++){
            //Begin with a LINEAR arraylist. 
            //Code is completely similar to the MAP generation from MaxiMap class
            ArrayList<JPanel> roompanel = new ArrayList<>();
            Rooms.add(roompanel);
            
            for(int j = 0; j< MAP.get(i).size(); j++){
                //We store, the JPanel created by UpdateRoom
                JPanel temppanel = UpdateRoom(i,j);
                //Add this JPanel to the Linear ArrayList. Thus creating a Matrix, similar to MAP generation
                roompanel.add(temppanel);
             }
        }
    }
    
    
    //Method that creates the Monster Panel!
    public JPanel MonsterDetails(Monster mon){
            JPanel MonDeets = new JPanel();
                MonDeets.setBorder(BorderFactory.createTitledBorder(mon.getName()));
                BoxLayout blayout = new BoxLayout(MonDeets, BoxLayout.Y_AXIS);
                MonDeets.setLayout(blayout);
                
                MonDeets.add( new JLabel("HP: " + mon.gethp()) );
                MonDeets.add( new JLabel("ATT: " + mon.getattDISP()) );
                MonDeets.add( new JLabel("DEF: " + mon.getdef()) );
                MonDeets.add( new JLabel("RAGE ATT: " + mon.getrage()) );
                
            return MonDeets;
    }
    
    //Returns the matrix PANEL ROOMS
    public ArrayList<ArrayList<JPanel>> getPanels(){
        return Rooms;
    }
    
    
    //Creates a JPanel of the room MapRec depending on the index positions
    //in the ArrayList<ArrayList<MapRec>>
    //Also used in updating GUI PAELS AFTER 'COMBAT'
    public JPanel UpdateRoom(int LevelMove, int RoomMove){
            //Create a temporary JPanel
            JPanel temppanel = new JPanel();
            
            //GET THE MAPREC OBJECT AT THE TWO INDEX POSITIONS: LEVELMOVE AND ROOMMOVE
            MapRec mr = MAP.get(LevelMove).get(RoomMove);
            
            
                //The list of IF statements are there to name the ROOMS, whether LEFT, RIGHT, or MAIN
                if(RoomMove == 1){
                    LRroom = ((new Random()).nextInt(10) > 5)? "LEFT" : "RIGHT";
                } else if(RoomMove == 0){
                    LRroom = "MAIN";
                } else {
                    if(RoomMove==1){
                        LRroom = "LEFT";
                    } else {
                        LRroom = "RIGHT";
                    }
                }
                
                //NAming the RoomName: left,right,or main!
                mr.RoomName = LRroom;
                
                
                //Set GridBagLayout first
                temppanel.setLayout(thelayout);
                
                
                //Top of the Room Panel will show Room and Level DETAILS!
                opt.addobjects(new JLabel("<html>Total No. of Levels: " + MAP.size() + "<br>Level: " + (LevelMove+1) + "<br>Room:" + LRroom + "</html>"), temppanel, thelayout, constraint, 0,0,3,1, 0,0);
                
                
                //We create a JPanel for the ITEM IN THE ROOM
                JPanel ItemDeets = new JPanel();
                ItemDeets.setBorder(BorderFactory.createTitledBorder("ITEM"));
                ItemDeets.setLayout(new BoxLayout(ItemDeets, BoxLayout.Y_AXIS));
                
                    if(!mr.roomItem.getState()){
                        ItemDeets.add(new JLabel("Item Name: " + mr.roomItem.getItemName()));
                        ItemDeets.add(new JLabel("Item pts: " + mr.roomItem.getItemPoint()));
                   } else {
                        ItemDeets.add(new JLabel("ITEM USED"));
                   }
                   
                   
                 //We create a JPanel for the ARMOR IN THE ROOM  
                JPanel ArmorDeets = new JPanel();
                ArmorDeets.setBorder(BorderFactory.createTitledBorder("ARMOR"));
                ArmorDeets.setLayout(new BoxLayout(ArmorDeets, BoxLayout.Y_AXIS));
                
                     if(!mr.armore.getState()){
                            ArmorDeets.add(new JLabel("Armor Name: " + mr.armore.getItemName()));
                            ArmorDeets.add(new JLabel("Armor pts: " + mr.armore.getItemPoint()));
                    } else {
                            ArmorDeets.add(new JLabel("ARMOR USED"));
                    }
                
                //Adding the Armor and Ite panels onto the Temporary panel!
                opt.addobjects(ItemDeets,temppanel ,thelayout, constraint, 0,1,1,1,0 ,0);
                opt.addobjects(ArmorDeets,temppanel, thelayout, constraint, 0,2,1,1,0,0);
                
                //Create and store JPanel holding all Monster information!
                //We pass the Monster of MapRec mr (The curent ROOM in the Matrix ArrayList)
                JPanel MonDeets = MonsterDetails(mr.monmon);
                
                //Add the Monster Panel!
                opt.addobjects(MonDeets, temppanel, thelayout, constraint, 1,1,2,2,0,0);
                
            return temppanel;
    } 
    
    
    //Tis UPDATES the MAPREC ROOM of the ARRAYLIST in this CLASS!
    public void setRoomPanel(JPanel tempp, int LevelMove, int RoomMove){
        Rooms.get(LevelMove).set(RoomMove, tempp);
    }
    
    //Updates the MapRec Room and returns it back to the method that called it!
    public MapRec mapMoving(int LevelMove, int RoomMove ){
        MapRec mr = MAP.get(LevelMove).get(RoomMove);
        return mr;
    }
}