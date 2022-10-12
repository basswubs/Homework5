//java classes imported for later usage
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//main class declared, labeled "FileSaveLoad", and extends & implements "JFrame" (to avoid having to make a new class object) and "ActionListener" (so you don't need to put it in via the "addActionListener" method)
public class FileSaveLoad extends JFrame implements ActionListener{
	//GUI  components declared and labeled for latter assignment/calling/initialization
	public JPanel panel; 
	public JMenu menu;
	public JMenuItem save;
	public JMenuItem load;
	public JMenuBar bar;
	public JFileChooser fc; 
	public ListModel listmodel;
	public JList<String> text;
	public JList<String> text2;
	public ListModel selectedValuesList;
	
	//public constructor made where these GUI components are initialized (assigned/called methods) for later usage, 
	//or the JFrame the class is extending has methods called to make it visible, have the window be larger, etc.
	public FileSaveLoad() {
		panel = new JPanel();
		menu = new JMenu("File");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		bar = new JMenuBar();
		fc = new JFileChooser();
		this.setVisible(true);
		this.setSize(700,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		listmodel = new DefaultListModel<String>();
		selectedValuesList = new DefaultListModel<String>();
		//this Jlist class objects has a listmodel class object in it's parameters for it to display
		text = new JList<String>(listmodel);
		text2 = new JList<String>(selectedValuesList);
		//the called "add" method has new classes in it that have the Jlists "text" and "text2" in it- to give it some scrollbars- and a centered "BorderLayout" calling "CENTER" for a "center location" to put the GUI components.
		panel.add(new JScrollPane(text), BorderLayout.CENTER);
		panel.add(new JScrollPane(text2), BorderLayout.SOUTH);
		//panel GUI also calls the add method to center the "bar" GUI inside it with the "BorderLayout" inside it's parameters
		panel.add(bar, BorderLayout.NORTH);
		//more GUI components are added to another
		bar.add(menu);
		menu.add(save);
		menu.add(load);
		//the "text" JList has it's selection mode set to single via the called "setSelectionMode" method having "ListSelectionModel" interface calling the "SINGLE_SELECTION" value in it's parameters
		text.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//"text" also calls the method that adds/implements a "MouseListener" interface to it; w/ new abstract empty class "MouseAdapter" in it constructing a bunch of new code from it's empty method headers.
		text.addMouseListener(new MouseAdapter() {
			//"mouseClicked" overriding one of those methods in side MouseAdapter with it's code whenever event "MouseEvent" is declared and labeled "e"
			public void mouseClicked(MouseEvent e) {
				//if statement that checks the source event "e" if it's someone clicking 2 times in a row on the "text" JList
				if (e.getClickCount() == 2) {
					//integer declared, labeled "index", assigned "text" JList calling method "LocationToIndex" that gets the JList cell closest to whatever's in it's parameters
					//case in point, the x, y coordinates returned by the event "e" calling the "getPoint" method that returns whatever coordinates your mouse is at
					int index = text.locationToIndex(e.getPoint());
					//with that integer of the JList cell index, "index", inside the called "listmodel" ListModel class object declared, labeled, and initialized from earlier, the string representation of the
					//cell located by the cell in the "getElementAt"'s parameter index  will be assigned to the Object "item"
					Object item = listmodel.getElementAt(index);
					//it will then be converted to a string via called "toString" method and added to the "selectedValesList" ArrayList
					((DefaultListModel<Object>) selectedValuesList).addElement(item);
				}
			}	
		});
		//save and load GUIs also added to actionlistener interface for later usage
		save.addActionListener(this);
		load.addActionListener(this);
	}
	
	//main method with class object that I think might invoke all the code in the class barring this
    public static void main(String [] args) {
		FileSaveLoad example = new FileSaveLoad();
	}

    //public (everyone can use it) void (doesn't "return" anything with a return keyword) ActionListener method-overriding method "actionPerformed" method declared w/ new "ActionEvent" declared and labeled "e"
    //in it's parameters
    public void actionPerformed(ActionEvent e){
    	//object class "BufferedReader" declared and labeled "objReader", asssigned "null" to show it has no value to have just yet 
    	BufferedReader objReader = null;
    	
    	//if statement checking if the "e" event's source was the "load" JMenuItem
    	if (e.getSource() == load) {
    		//integer declared, called "returnvalue", assigned "fc" class object calling the "showOpenDialouge" method that opens the fileexplorer window to whatever GUI in it's parameters,
    		//like the extended JFrame "FileSaveLoad" has when it's put in the parameters- the popup opens whatever file it selects
    		int returnvalue = fc.showOpenDialog(FileSaveLoad.this);
    		//abstract class File declared and labeled file, assigned other class object "fc" calling the "getSelectedFile" method that gets what's selected for the file's assignment 
    		File file = fc.getSelectedFile();
    		//if statement checking if you approved the selection in the popup window
    		if (returnvalue == JFileChooser.APPROVE_OPTION) {
    			//try catch statements that check for any IOexception errors
    			try {
    			//string, labeled "currentline", declared
    			String currentline;
    			//string declared and labeled "filepathway" to assign class "file" calling the "tostring" method to convert it's abstract file pathway to a string for assigning to "filepathway"
    			String filepathway = file.toString();
    			//objReader class object from before assigned the new "BufferedReader" class w/ the new "FileReader" class inside it's parameters, reading the "filepathway" string in it's parameters
    			//for the BufferedReader to fix
    			objReader = new BufferedReader(new FileReader(filepathway));
    			//assinging string "currentline" with the read line from "objReader" per it calling the "readLine" method
    			//after that, the while loop checks if the assigned "currentline" has any values assigned to it ("!= null")
    			//runs "ListModel" (w/ a bunch of syntax converting it to a DefaultListModel object) calling the "addElement" method giving it the "currentline" string 
    			while ((currentline = objReader.readLine()) != null) {
    				((DefaultListModel<Object>) listmodel).addElement(currentline);
    			}
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}	
    		}
    	}

    	if (e.getSource() == save) {
    		//integer declared, called "returnvalue", assigned "fc" class object calling the "showSaveDialouge" method that opens the fileexplorer window to whatever GUI in it's parameters,
    		//like the extended JFrame "FileSaveLoad" has when it's put in the parameters- the popup saves whatever file it selects
    		int returnvalue = fc.showSaveDialog(FileSaveLoad.this);
    		//if statement checking if you approved the selection in the popup window
    		if (returnvalue == JFileChooser.APPROVE_OPTION) {
    			//abstract class File declared and labeled file, assigned other class object "fc" calling the "getSelectedFile" method that gets what's selected for the file's assignment 
    			File file = fc.getSelectedFile();
    			//object class "BufferedWriter" declared and labeled "bw", asssigned "null" to show it has no value to have just yet 
    			BufferedWriter bw = null;
    			//try catch statements checking if there's a IOException error in it's parameters
    			try {
    				//FileWriter declared, labeled "fw", assigned new "FileWriter" class with the abstract class "file" in it's parameters to write to 
    				FileWriter fw = new FileWriter(file);
    				//assigns the BufferWriter class object "bw" new class "BufferedWriter" with filewriter class object "fw" in it's parameters (written stuff from fw's own parameters) for it to buffer out
					bw = new BufferedWriter(fw);
					//checks if the file's abstract class even leads to a existing file, if not makes a new one
    				if (!file.exists()) {
    					file.createNewFile();
    				}
    				//for loop that has new Object delcared and labeled "content", has the colon ":" work as a  
    				for (Object content : ((DefaultListModel<String>) (text2.getModel())).toArray()) {
    					bw.write(content + "\n");
    					System.out.println(content);
    				}
    				
	    		} catch (IOException e2) {
	    			e2.printStackTrace();	
	    		}	
    		}
    	}
    }
}
