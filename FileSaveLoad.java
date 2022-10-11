import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FileSaveLoad extends JFrame implements ActionListener{
	public JPanel panel;
	public JMenu menu;
	public JMenuItem save;
	public JMenuItem load;
	public JMenuBar bar;
	public JFileChooser fc; 
	public ListModel listmodel;
	public JList<String> text;
	ArrayList<String> selectedValuesList;
	File file = new File ("myfile.txt");
	BufferedWriter bw = null;
	
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
		selectedValuesList = new ArrayList<String>();
		text = new JList<String>(listmodel);
		panel.add(new JScrollPane(text), BorderLayout.CENTER);
		panel.add(bar, BorderLayout.NORTH);
		bar.add(menu);
		menu.add(save);
		menu.add(load);
		text.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		text.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
				int index = text.locationToIndex(e.getPoint());
				Object item = listmodel.getElementAt(index);
				selectedValuesList.add(item.toString());
				}
			}	
		});
		save.addActionListener(this);
		load.addActionListener(this);
	}
	
	
    public static void main(String [] args) {
		FileSaveLoad example = new FileSaveLoad();
	}

    
    public void actionPerformed(ActionEvent e){
    
    	BufferedReader objReader = null;
    	
    	if (e.getSource() == load) {
    		int returnvalue = fc.showOpenDialog(FileSaveLoad.this);
    		
    		if (returnvalue == JFileChooser.APPROVE_OPTION) {
    			File file = fc.getSelectedFile();
    			try {
    			String currentline;
    			String filepathway = file.toString();
    			objReader = new BufferedReader(new FileReader(filepathway));
    			while ((currentline = objReader.readLine()) != null) {
    				((DefaultListModel<Object>) listmodel).addElement(currentline);
    			}
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}	
    		}
    	}

    	if (e.getSource() == save) {
    		int returnvalue = fc.showOpenDialog(FileSaveLoad.this);
    		if (returnvalue == JFileChooser.APPROVE_OPTION) {
    			File file = fc.getSelectedFile();
    			try {
					FileWriter fw = new FileWriter(file);
					bw = new BufferedWriter(fw);
	    		for (int i=0; i < selectedValuesList.size(); i++) {
	    			String lines = selectedValuesList.get(i);
	    			bw.write(lines);
	    		} 
	    		} catch (IOException e2) {
	    			e2.printStackTrace();	
	    		}	
    		}
    	}
    }
}
