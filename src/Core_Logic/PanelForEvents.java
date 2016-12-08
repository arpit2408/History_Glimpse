/**
 * 
 */
package Core_Logic;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author arpit2408
 *
 */
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import View.JPanelWithBackground;
public class PanelForEvents {

	static int delayInQuiz=500000;
	static boolean flagforQuizPopup=false;
	static Timer timer;
	static ArrayList<String> events=new ArrayList<>();
	/**
	 * @param args
	 */
	
	public static boolean RIGHT_TO_LEFT = false;

	public static void addComponentsToPane(JPanel pane,ArrayList<String> events) {

		if (!(pane.getLayout() instanceof BorderLayout)) {
			pane.add(new JLabel("Container doesn't use BorderLayout!"));
			return;
		}

		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(
					java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}


		//Make the center component big, since that's the
		//typical usage of BorderLayout.
		String categories[] = new String[100]; ;
		
		int i=0;
		for(String event:events)
			categories[i++]=event;
		
		JList list = new JList(categories);
		JScrollPane scrollpane = new JScrollPane(list);
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		pane.add(scrollpane, BorderLayout.CENTER);

		JList list1 = new JList(categories);
		ListSelectionModel listSelectionModel = list.getSelectionModel();
		listSelectionModel.addListSelectionListener(
				new SharedListSelectionHandler());
		JScrollPane scrollpane1 = new JScrollPane(list1);
		scrollpane1.setBorder(raisedbevel);
		scrollpane.setBorder(loweredbevel);
		listSelectionModel.setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		
		timer = new Timer(delayInQuiz, new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	ImageIcon img = new ImageIcon("/Users/arpit2408/Downloads/firefox_downloads/IconQuiz.png");
		    	
		    	JPanel panel = new JPanelWithBackground("/Users/arpit2408/Downloads/firefox_downloads/49604.gif");
		    	panel.setPreferredSize(new Dimension(200,200));
		    	panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
		    	int option=JOptionPane.showConfirmDialog(null,panel, "Quiz Time",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,img);
		        if (option == JOptionPane.OK_OPTION) {
		        	timer.stop();
		        	new Quiz();
		        }
		        else {
		        	delayInQuiz=delayInQuiz+500000;
		        }
		    }
		});
		
		timer.setRepeats(true);
			
		timer.start();
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event dispatch thread.
	 */
	public static void createAndShowGUI(ArrayList<String> events) {

		Browser browser = new Browser();
		BrowserView view = new BrowserView(browser);
		JFrame frame = new JFrame("List of Events");
		frame.setPreferredSize(new Dimension(600,600));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.add(view, BorderLayout.CENTER);
		JPanel p = new JPanel(new BorderLayout()); 
		frame.add(p);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addComponentsToPane(p,events);
		
		frame.pack();
		frame.setVisible(true);

	}
	public static void main(String[] args) {
		/* Use an appropriate Look and Feel */
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(events);
			}
		});
	}

}
