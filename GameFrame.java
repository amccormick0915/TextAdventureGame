import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;

//Main class for the GUI
class GameFrame extends JFrame implements  ActionListener{
    private final static int MAX_CHAR = 6;
    
    private GUIOpt SaveLoad;
    private Object[] LoadedData;

    private Container cp;

    private CharacterGUI gg; //HOLDS ALL CHARACTER INFO, TO BE MANIPULATED
    
    private JLabel GameOutput;
    private ArrayList<Being> chars;
    private Monster monmon;
    private ArrayList<ArrayList<JPanel>> Rooms;
    
    private BoardMap bp; //HOLDS ALL MAP INFO, TO BE MANIPULATED
    private ArrayList<ArrayList<MapRec>> RoomEntities;
    private MapRec RoomEntity;
    private MaxiMap Matrix;
    
    private JButton fight;
    private JButton UseItem;
    private JButton UseArmor;
    
    private JPanel RefreshCharPanel;
    private JPanel RefreshRoomPanel;
    
    private JPanel NorthPanel;
    private JPanel CenterPanel;
    private JPanel SouthPanel;
    private JPanel EastPanel;
    private JPanel WestPanel;
    
    private JButton btnSave;
    private JButton btnLoad;
    private JButton fin;
    private JButton btnEXIT;
    private JButton btnSTART;
    
    private int LevelMover;
    private int RoomMover;
    
    private JButton MAIN = new JButton("MOVE FORWARD");
    private JButton LEFT= new JButton("LEFT");
    private JButton RIGHT= new JButton("RIGHT");
    private JButton BACK= new JButton("BACK TO MAIN");


    public GameFrame(){
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        
        //Initialise the Panels and their Layouts in the FRAME!
        NorthPanel = new JPanel(new FlowLayout());
        CenterPanel = new JPanel(new BorderLayout());
        SouthPanel = new JPanel(new FlowLayout());
        EastPanel = new JPanel(new FlowLayout());
        WestPanel = new JPanel();
        
        cp.add(NorthPanel, BorderLayout.NORTH);
        cp.add(CenterPanel, BorderLayout.CENTER);
        cp.add(EastPanel, BorderLayout.EAST);
        cp.add(WestPanel, BorderLayout.WEST);
        cp.add(SouthPanel, BorderLayout.SOUTH);
        
        
        //Initialise the buttons!
        btnSTART = new JButton("START GAME");
        btnLoad = new JButton("LOAD GAME"); 
        btnEXIT = new JButton("EXIT");
        
        SouthPanel.add(btnSTART);
        SouthPanel.add(btnLoad);
        SouthPanel.add(btnEXIT);
        
        fin = new JButton("DONE!");
        fin.setVisible(false);
        
            fin.addActionListener(this);
            btnLoad.addActionListener(this);
            btnSTART.addActionListener(this);
            btnEXIT.addActionListener(this);
              
        //Set the Frame Details!
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setTitle("TEST"); 
        setSize(900, 600);  
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //Starts the Adventure part of the game
    public void StartGame(){
        btnSave = new JButton("SAVE"); 
            btnSave.addActionListener(this);
            
        //Set THE TOP PANEL FOR THE SAVE BUTTON    
        JPanel AdditionalPanel = new JPanel();
        AdditionalPanel.add(btnSave);
        NorthPanel.add(AdditionalPanel);
        NorthPanel.setBackground(Color.white);
        
        
        //INITIALISING character panels! and Initialising the WESTPANEL!
        gg.CharacterPanels();
        WestPanel.removeAll();
        WestPanel.add(gg.getPan()); 
        WestPanel.setBackground(new Color(252, 109, 109));
        
        
        LevelBattle();
        
        revalidate();
        repaint();
    }

    
    //Method for Battling
    public void LevelBattle(){
            JPanel TOPpanel = new JPanel();
            JPanel MIDpanel = new JPanel();
            
            //Initialise and add ActionListener to the movement buttons!
            TOPpanel.add(LEFT);
                LEFT.addActionListener(this);
            TOPpanel.add(MAIN);
                MAIN.addActionListener(this);
            TOPpanel.add(RIGHT);
                RIGHT.addActionListener(this);
            TOPpanel.add(BACK);
                BACK.addActionListener(this);
                BACK.setVisible(false);
            TOPpanel.setBackground(new Color(244, 194, 252));
            
            
            //Add Figth button
            fight = new JButton("FIGHT");
                fight.addActionListener(this);
                
            UseItem = new JButton("Use Room Item");
                UseItem.addActionListener(this);
                
            UseArmor = new JButton("Equip Armor");
                UseArmor.addActionListener(this);
            
            MIDpanel.add(fight);
            MIDpanel.add(UseItem);
            MIDpanel.add(UseArmor);
                MIDpanel.setBackground(new Color(252, 224, 194));
            
            CenterPanel.add(TOPpanel, BorderLayout.NORTH);
            CenterPanel.add(MIDpanel, BorderLayout.CENTER);
            CenterPanel.add(GameOutput, BorderLayout.SOUTH);
            
            RefreshRoom();
            EastPanel.setBackground(new Color(185, 240, 207));
            
            print("<html>You are currently at Level 1<br>You can choose to use the Item in the Room<br>Equip the Armor<br>Or Battle the Room Monster!<br>You can't move to another room unless you've defeated the Monster!<br>Once you pass through the main oassage, you can't get back out again!</html>");
               
            CenterPanel.setBackground(new Color(176, 221, 245));
    }

    //This method refreshes the Room Panel!
    public void RefreshRoom(){
        EastPanel.removeAll();
        RefreshRoomPanel = Rooms.get(LevelMover).get(RoomMover);
        RoomEntity = bp.mapMoving(LevelMover, RoomMover); //the data moves along as the panel move!!
        monmon = RoomEntity.monmon;
        EastPanel.add(RefreshRoomPanel);
        EastPanel.revalidate();
        EastPanel.repaint();
    }

    
    //This method Refreshers the Characters Panel!
    public void RefreshChars(){
       WestPanel.removeAll();
       WestPanel.add(gg.getPan());
       WestPanel.revalidate();
       WestPanel.repaint();
    }
    
    
    //This Generates and stores the MATRIX MAP
    public void MapGenerate(){
        Matrix = new MaxiMap(chars.size()); //Generating and storing the Map!
        
        
        //The arraylist<arraylist<maprec>> is returned!
        RoomEntities = Matrix.getMatMap();
        bp = new BoardMap(RoomEntities);
        
        //The arraylist<arraylist<JPanels>> is returned!
        Rooms = bp.getPanels();
    }
    
    
    
    @Override public void actionPerformed(ActionEvent e) {    
            //fin is the BUTTON that is called "DONE", the button that enables you to play after you added a character or chaaracters 
        if(e.getSource() == fin){
             if(chars.size()==0){
                    print("PLEASE ADD AT LEAST ONE CHARACTER!");
            } else {
                CenterPanel.removeAll();
                fin.setVisible(false);
                MapGenerate();
                LevelMover = 0;
                RoomMover = 0;
                StartGame();
                monmon = RoomEntity.monmon;
                WestPanel.revalidate();
                WestPanel.repaint();
            }
            
            //START is for starting the game!
        } else if (e.getSource() == btnSTART){
                SouthPanel.remove(btnSTART);
                SouthPanel.remove(btnEXIT);
                SouthPanel.remove(btnLoad);
                NorthPanel.removeAll();
                GameOutput = new JLabel();
                SouthPanel.add(GameOutput);
                
                chars = new ArrayList<>();
                gg = new CharacterGUI(chars, MAX_CHAR);
                CenterPanel.add(gg.getPan());
                SouthPanel.add(fin);
                
                fin.setVisible(true);
                cp.revalidate();
                cp.repaint();
                
            //This closes the Frame
        }else if (e.getSource() == btnEXIT){
                dispose();
                
                
             //This button enables the player tomove forward the MAP and LEVEL   
        } else if (e.getSource() == MAIN){
             int maximumRooms = Rooms.size();
             
            if(monmon.gethp() > 0){
                print("You cannot MOVE until you battle the Monster in this room first!");
            }else if(LevelMover == (maximumRooms -1)){
                print("CONGRATS! YOU'VE BEATEN THE LAST MAIN MONSTER! :><br>You can close and play again!<br>Thanks!");
            } else {
                LevelMover++;
                RoomMover = 0;
                RefreshRoom();
            }
            
            
            //Moves to the left room
        }else if (e.getSource() == LEFT){
            
            if(monmon.gethp() > 0){
                print("You cannot MOVE until you battle the Monster in this room first!");
            }else {
                checkRoomAvail("LEFT");
            }
            
            
            //moves to the right room
        }else if (e.getSource() == RIGHT){
        
            if(monmon.gethp() > 0){
                print("You cannot MOVE until you battle the Monster in this room first!");
            }else {
                checkRoomAvail("RIGHT");
            }
            
            
            //Starts the battle between the characters and the Room momnster!
        }else if (e.getSource() == fight){
            if(gg.getTotalHealth() == 0){
                print("No Conscious Characters<br> GAME OVER");
            } else if (!RoomEntity.monmon.getstatus()){
                print("Monster is UNCONSCIOUS!<br>Please move forward!");
            } else {
                 BattleCommence();
            }
            
            
            //Returns back to the Main Room!
        } else if (e.getSource() == BACK){
            print("Going back to MAIN passage!");
            RoomMover = 0;
            BACK.setVisible(false);
            MAIN.setVisible(true);
            LEFT.setVisible(true);
            RIGHT.setVisible(true);
            RefreshRoom();
            
            
            //Button for using Room Item!
        } else if (e.getSource() == UseItem){
            ItemArmorUse("ITEM");
            RefreshRoom();
            RefreshChars();
            
            
        } else if (e.getSource() == UseArmor){
            ItemArmorUse("ARMOR");
            RefreshRoom();
            RefreshChars();
            
            
        } else if(e.getSource() == btnSave){
            SaveLoad = new GUIOpt();
            SaveLoad.SaveSession(RoomEntities, chars, LevelMover, RoomMover);
            
        } else if(e.getSource() == btnLoad){
            
                SaveLoad = new GUIOpt();
                LoadedData = SaveLoad.LoadSession();
                
                if( ((ArrayList<ArrayList<MapRec>>) LoadedData[0]).size() == 0){
                    NorthPanel.removeAll();
                    NorthPanel.add(new Label("NO GAME WAS SAVED! PLEASE CLICK START GAME!"));
                    cp.revalidate();
                    cp.repaint();
                } else {
                    RoomEntities = (ArrayList<ArrayList<MapRec>>) LoadedData[0];
                    chars = (ArrayList<Being>) LoadedData[1];
                    LevelMover = (int) LoadedData[2];
                    RoomMover = (int) LoadedData[3];
                    
                    gg = new CharacterGUI();
                    gg.CharacterPanels(chars);
                    bp = new BoardMap(RoomEntities);
                    Rooms = bp.getPanels();
                    CenterPanel.removeAll();
                    GameOutput = new JLabel();
                    SouthPanel.add(GameOutput);
                    SouthPanel.removeAll();
                    RefreshRoom();
                    RefreshChars();
                    
                    StartGame();
                    revalidate();
                    repaint();
                }
        }
    }

    //Method for USING ARMOR OF THE ROOM!
    public void ItemArmorUse(String Path){
        
        int indexChosen = returnCharacterChosen();
        if(indexChosen >= 0){
            if(Path.equals("ITEM")){
                if (RoomEntity.roomItem.getState()){
                    print("----Item in this room has been used!---");
                } else {
                    RoomEntity.roomItem.setUse(chars.get(indexChosen));
                    JPanel temppanel = bp.UpdateRoom(LevelMover, RoomMover);
                    bp.setRoomPanel(temppanel,LevelMover, RoomMover);
                    gg.changeCharPanel(indexChosen);
                }
            } else {
                if (RoomEntity.armore.getState()){
                    print("----Item in this room has been used!---");
                } else {
                    RoomEntity.armore.setUse(chars.get(indexChosen));
                    JPanel temppanel = bp.UpdateRoom(LevelMover, RoomMover);
                    bp.setRoomPanel(temppanel,LevelMover, RoomMover);
                    gg.changeCharPanel(indexChosen);
                }
            }
        } 
            
    }

    
    //This pops out a DROP DOWN for when PLayer is choosing which character to give the ITEM or AROR to!
    //This returns the index of the character chosen!
    public int returnCharacterChosen(){
        int indexChosen = 0;
        DefaultComboBoxModel model = new DefaultComboBoxModel<Being>();
        
            if(chars.size() > 0){
                for(int n = 0; n < chars.size(); n++){
                    model.addElement(chars.get(n).getName());
                }
                
                JComboBox cb = new JComboBox(model);
                int result = JOptionPane.showConfirmDialog(null, cb, "Select which character to give!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                    if (result == JOptionPane.OK_OPTION) {
                         indexChosen = cb.getSelectedIndex();
                    } else {
                        print("---You chose not to use item on anyone!---");
                        return -1;
                    }
            }
        return indexChosen;
    }
    
    
    //Checks whether the Room the player has chosen to enter is available or not!
    public void checkRoomAvail(String LRchoose){
        
            if(Rooms.get(LevelMover).size() == 1){
                print("The LEFT and RIGHT rooms are LOCKED!<br>You can only continue through the MAIN passage!");
            } else if (Rooms.get(LevelMover).size() == 3){
                if(LRchoose.equals("LEFT")){
                    RoomMover = 1;
                } else {
                    RoomMover = 2;
                }
                    BACK.setVisible(true);
                    MAIN.setVisible(false);
                    LEFT.setVisible(false);
                    RIGHT.setVisible(false);
                    
                        RefreshRoom();
                    
            } else {
                if(RoomEntities.get(LevelMover).get(1).RoomName.equals(LRchoose)){
                        print("     ENTERING ROOM TO THE " + LRchoose);
                        RoomMover = 1;
                        
                    BACK.setVisible(true);
                    MAIN.setVisible(false);
                    LEFT.setVisible(false);
                    RIGHT.setVisible(false);
                    
                        RefreshRoom();
                } else {
                    print("THIS ROOM IS LOCKED! CHECK THE OTHER ROOM OR CONTINUE THROUGH MAIN PASSAGE!");
                }
            }
    }
    
    
    //Battle between player characters and the Monster
    public void BattleCommence(){
        int counter ;
        Monster monmon = RoomEntity.monmon;
        String OutputMessage = "";
        
            //Starts BATTLE with characters dealing damage to Monster!
            for(counter= 0; counter < chars.size(); counter++){
                Being hero = chars.get(counter);
                
                if(hero.getstatus() && monmon.getstatus()){
                    
                                            //HERE: DYNAMIC BINDING USED! depending of the instance of 'hero', the getatt() output will be different!
                        OutputMessage +=  monmon.sethp(hero.getatt(), hero);
                } else if (!hero.getstatus()){
                        OutputMessage += "<br>" + hero.getName() + " is Unconscious! They skip the battle!"  ;
                }
            }
        
            //Battle continues where Monster attacks the players!
            if ((gg.getTotalHealth()  > 0) && monmon.getstatus()){
                 int random = RandomInt(chars.size(),0);
                 boolean attack = false;
                 
                while( monmon.getstatus() && (attack == false)){
                        if(chars.get(random).getstatus()){
                            OutputMessage += chars.get(random).sethp(monmon.getatt(), monmon);
                            
                                if(monmon.getpstatus()){
                                    monmon.poisoncheck();
                                    OutputMessage += "<br>" + monmon.getName() + " is poisoned! Losing 10% of their health!";
                                }
                                
                             attack = true;
                             gg.changeCharPanel(random);
                        } 
                    random = RandomInt(chars.size(),0);
                }
            }
        
        JPanel temppanel = bp.UpdateRoom(LevelMover, RoomMover);
        bp.setRoomPanel(temppanel,LevelMover, RoomMover);
        RefreshRoom();
        RefreshChars();
        
        print(OutputMessage);
    }
   
   
    //This sets the message onto the Otput 
   public void print(String m){
        GameOutput.setText("<html>" + m + "</html>");
    }
   
   public static void main(){
       GameFrame gui = new GameFrame();
    }
    
   public static int RandomInt(int max, int add){
            Random dice = new Random();
            int numbergenerated = dice.nextInt(max) + add;
            return numbergenerated;
    }
}