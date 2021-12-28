import java.awt.*;  
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;

class CharacterGUI extends JPanel {
    private static final String[] CHOICES = {"Human", "Magical", "Monster"};
    
    private GridBagConstraints constraint;
    private GridBagLayout thelayout;
    private GUIOpt opt;
    
    private JTextField input;
    private JLabel output;
    private JLabel output2;
    private JLabel output3;
    private JPanel main;
    
    private JPanel[] CharacterPanels;
    private ArrayList<Being> Char;
    private String[] weapon;
    private int i;
    
    public CharacterGUI(ArrayList<Being> chars, int MAX_CHAR){
        Char = chars;
        weapon = (new Human()).getwep();
        i = 0;
        
        //initialise Layout and Panels!
        main = new JPanel();
        opt = new GUIOpt();
        constraint = new GridBagConstraints();
        thelayout = new GridBagLayout();
        main.setLayout(thelayout);
       
        JButton ad = new JButton("ADD");
        
        input = new JTextField(10);
        output = new JLabel("<html>You can make  total of " + MAX_CHAR + " characters!<br>Enter Name for Character number " + (i+1) + ": </html>");
        output2 = new JLabel("Please choose character type!:");
        output3 = new JLabel("Choose weapons:");
        
        JComboBox<String> Drop = new JComboBox<String>(CHOICES);
        JComboBox<String> Drop2 = new JComboBox<String>(weapon);
        
        Drop.addActionListener( new ActionListener() { @Override public void actionPerformed(ActionEvent evt) { 
                                                             
                                                                String ans = Drop.getSelectedItem().toString();
                                                                ad.setVisible(true);
                                                                
                                                                     if(ans.equals("Magical")){
                                                                         weapon = (new Magical()).getwep();
                                                                         Drop2.setModel(new DefaultComboBoxModel(weapon));
                                                                         
                                                                     } else if(ans.equals("Monster")){
                                                                         weapon = (new Monster()).getwep();
                                                                         Drop2.setModel(new DefaultComboBoxModel(weapon));
                                                                         
                                                                     } else {
                                                                         weapon = (new Human()).getwep();
                                                                         Drop2.setModel(new DefaultComboBoxModel(weapon));
                                                                     }
                                                                     
                                                                revalidate();
                                                                repaint();
                                                          }
                                                         });
        
        opt.addobjects(output, main, thelayout, constraint, 0,0,1,1, 25,25);
        opt.addobjects(input, main, thelayout, constraint, 2,0,1 ,1,0,0);
        
        opt.addobjects(output2, main, thelayout, constraint, 0,1,1,1,25,25);
        opt.addobjects(Drop, main, thelayout, constraint, 2,1,1 ,1,0 ,0);
        
        opt.addobjects(output3, main, thelayout, constraint, 0,2,1,1, 25,5);
        opt.addobjects(Drop2, main, thelayout, constraint, 2,2,1 ,1, 0,0);
        
        opt.addobjects(new JLabel("    "), main, thelayout, constraint, 1,3,1,1,0,0);
        opt.addobjects(ad, main, thelayout, constraint, 1,4,1,1,0,0);
        
        
        ad.addActionListener( new ActionListener() { @Override public void actionPerformed(ActionEvent evt) {
                                                            if(i < MAX_CHAR) {
                                                            String name = input.getText().trim();
                                                            
                                                                if(name.equals("")){
                                                                    output.setText("PLEASE ENTER CHARACTER NAME!");
                                                                }else {
                                                                    Char.add(AskChar(name,Drop.getSelectedItem().toString(), CHOICES, Drop2.getSelectedIndex()));
                                                                    output.setText("Character number " + (i+1) + " added! enter next Character name!");
                                                                    i++;
                                                                }
                                                                
                                                            } else {
                                                                output.setText("<html>You've reached maximum character limit!<br>PLease click DONE to finish!</html>");
                                                            }
                                                         }
                                                      });
          
        ad.setVisible(true); 
        main.setBackground(new Color(236, 255, 196));
        main.setVisible(true);
    }
    
    //This method creates the whole Character panels with all CHARACTERS
    public void CharacterPanels(){ 
        main = new JPanel();
        opt = new GUIOpt();
        constraint = new GridBagConstraints();
        thelayout = new GridBagLayout();
        
        main.setLayout(thelayout);
        int k = 0;
        
        CharacterPanels = CreateCharPanels();
        
            for(int j = 0; j < CharacterPanels.length ; j++){
                if(j%2 == 0){
                    opt.addobjects( CharacterPanels[j], main, thelayout, constraint, 0,k,1,1,0,0);
                } else {
                   opt.addobjects( CharacterPanels[j], main, thelayout, constraint, 1,k,1,1,0,0);
                    k++;
                }
            }
    }
    
    public CharacterGUI(){
    }
    
    //This method is used for LOADING SAVED DATA.
    //This method updates the ArrayList of characters inside this instance
    //AND creates the character panels
    public void CharacterPanels(ArrayList<Being> players){ 
        Char = players;
        CharacterPanels();
    }
    
    //Updates the character panel after using an ITEM!
    public void changeCharPanel(int index){
            JPanel temp = updateCharacter(index);
            CharacterPanels[index] = temp;
            CharacterPanels();
    }
    
    //This updates the panel of ONE character
    public JPanel updateCharacter(int CharIndex){
        JPanel pantemp = new JPanel();
        Being character = Char.get(CharIndex);
        
                pantemp.setBorder(BorderFactory.createTitledBorder(character.getName()));
                BoxLayout blayout = new BoxLayout(pantemp, BoxLayout.Y_AXIS);
                pantemp.setLayout(blayout);
                
                int chartype = CheckCharType(character);
                
                    pantemp.add( new JLabel("TYPE: " + CHOICES[chartype] ));
                    pantemp.add( new JLabel("HP: " + character.gethp()) );
                
                    pantemp.add( new JLabel("ATT: " + character.getatt(0)) );
                if(chartype == 1){
                    pantemp.add( new JLabel("MAG ATT: " +( (Magical)character).getmagic_att()) );
                    pantemp.add( new JLabel("MANA: " + ((Magical)character).getmana()) );
                    pantemp.add( new JLabel("WEP: " + ((Magical)character).getwep_name()) );
                    
                } else if(chartype == 2){
                    pantemp.add( new JLabel("RAGE ATT: " +( (Monster)character).getrage()) );
                    pantemp.add( new JLabel("WEP: " + ((Monster)character).getwep_name()) );
                } else {
                    pantemp.add( new JLabel("WEP: " + ((Human)character).getwep_name()) );
                }
                
                pantemp.add( new JLabel("DEF: " + character.getdef()) );
                pantemp.add( new JLabel("STATUS: " + character.getstatusStr()) );
        
        return pantemp;
    }
    
    
    
    public JPanel[] CreateCharPanels(){
        JPanel[] CharacterPanels;
        JPanel pantemp;    
        
        CharacterPanels = new JPanel[Char.size()];
                for(int j = 0; j < Char.size(); j++){
                    pantemp = updateCharacter(j);
                    CharacterPanels[j] = pantemp;
                }
        return CharacterPanels;
    }
    
    
    //Returns the CHARACTER PANEL
    public JPanel getPan(){
        return main;
    }
        
    
    //Returns an int denoting which instance the character is
    public int CheckCharType(Being character){
        
            if( character instanceof Monster ){
                        return 2;
            } else if( character instanceof Magical ){
                        return 1;
            } else if( character instanceof Human ){
                        return 0;
            } else {
                        return -1;
            }
    }
    
    //Returns the total number of health in all characters!
    public double getTotalHealth(){
        double totalHealth = 0;
            for(int i = 0; i < Char.size(); i++){
                    totalHealth = totalHealth +    Char.get(i).gethp();        
            }
        return totalHealth;
    }
    
    //This method used for SUBSTITUTION! We are returning a TYPE being, but 
    // the instances have different types, depending on User choice!
    public static Being AskChar(String name, String type, String[] char_type, int wep){
        Being being;
        
            if(type.equals(char_type[0])){
                    being = new Human(name,wep);
            } else if(type.equals(char_type[1])){
                    being = new Magical(name,wep);
            } else {
                    being = new Monster(name,wep);
            } 
        return being;
    }
}
