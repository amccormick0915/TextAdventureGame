//Imports the needed codes for the gridBagLayout! 
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.*;       
import javax.swing.*;
import java.util.*;


//Import stuff we need for our Load and Save!
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



//This Object is an added utility for the GUI and the Game, such as creating a GridBagLayout and adjusting the Constraints and the Components!
//Also handles Save and Load!
public class GUIOpt{
    
    //Creates an instance to be called! 
    public GUIOpt(){
    };

    //iInstead of making the whole code, 
        public void addobjects(Component componente, Container contain, GridBagLayout layout, GridBagConstraints Constraint, int x, int y, int gwidth, int gheigth, int padx, int pady){
    
        //Adds a cort of padding between the components!
        Constraint.ipadx = padx;
        Constraint.ipady = pady;
        
        //Denotes HOW MUCH ROWS or COLUMNS one component can take!
        Constraint.gridwidth = gwidth;
        Constraint.gridheight = gheigth;
            
        
        //Denotes the position of the copmponent in the Layout! ( 0,0: 1,0: 1,1: 0,1: etc, ...)
        Constraint.gridx = x;
        Constraint.gridy = y;

        layout.setConstraints(componente, Constraint);
        contain.add(componente);
    }
    
    //This Adjustes the padding between components in the GridBagLayout
    public void padd(GridBagConstraints gbc, int padx, int pady){
        gbc.ipadx = padx;
        gbc.ipady = pady;
    }
    
    
    //THis saves Game data!
    public void SaveSession(ArrayList<ArrayList<MapRec>> Matrix, ArrayList<Being> chars, int LM, int RM){  
    
        try{
                //An object Array that stores the IMPORTANT GAME DATA!
                //The MAP, THE CHARACTERS AND THEIR STATES, THE LEVEL AND THE ROOM LAST POSITIONED IN!
                Object[] SaveData = {Matrix, chars, LM, RM};
        
                FileOutputStream fos = new FileOutputStream("SaveData.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(SaveData);
                oos.close();
            
        }catch (FileNotFoundException err) {
                err.printStackTrace();
        }catch (IOException err) {
                err.printStackTrace();
        }
        
    }
    
    
    //This Loads the LAST saved game data!
    public Object[] LoadSession() 
    {
        //Initialise the Array with an empty version of the MAP , Characters, and Room Positions!
        ArrayList<ArrayList<MapRec>> Matrix =  new ArrayList<ArrayList<MapRec>>();
        ArrayList<Being> chars = new ArrayList<Being>();
        
        Object[] LoadData = {Matrix, chars,0,0};
        
            try {
            
                    FileInputStream fis = new FileInputStream("SaveData.ser");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    LoadData = (Object[]) ois.readObject();
                    ois.close();
            
                        return LoadData;
                        
            } catch (FileNotFoundException e) { 
            }catch (IOException e) { 
            }
            catch (ClassNotFoundException e) { 
            }
            
        //If error occurs, we return the EMPTY version!    
        return LoadData;
    }
}