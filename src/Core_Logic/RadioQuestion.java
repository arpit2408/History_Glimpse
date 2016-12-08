/**
 * 
 */
package Core_Logic;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * @author arpit2408
 *
 */
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;



public class RadioQuestion extends JPanelWithBackground implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int correctAns;
	Quiz quiz;	
 
 	boolean count = false;
	boolean wcount = false;
	int selected;
	boolean used = false;
 	JPanel qPanel=new JPanel();
 	JPanel aPanel=new JPanel();
	JRadioButton[] responses;
	ButtonGroup group=new ButtonGroup();
 	JPanel botPanel=new JPanel();
	JButton next=new JButton("Next");
	JButton finish=new JButton("Finish");
 
	
	public RadioQuestion(String q, ArrayList<String> options, int ans, Quiz quiz){
		super("/Users/arpit2408/Documents/histopy/MATHS_IMAGE.jpeg");
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
		
		this.quiz=quiz;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		correctAns=ans;
		final String html1 = "<html><body style='width: ";
	    final String html2 = "px'>";
	    JLabel temp=new JLabel(html1 + "400" + html2 + q);
	    temp.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
	    qPanel.setOpaque(false);
 		qPanel.add(temp);
 		//qPanel.setFont(new Font("Monotype Corsiva", Font.ITALIC, 30));
		add(qPanel);
 		responses=new JRadioButton[options.size()];
		for(int i=0;i<options.size();i++){
			responses[i]=new JRadioButton(options.get(i));
			responses[i].addActionListener(this);
			group.add(responses[i]);
			aPanel.add(responses[i]);
			aPanel.setOpaque(false);
		}
		add(aPanel);
 		next.addActionListener(this);
		finish.addActionListener(this);
		botPanel.add(next);
		botPanel.add(finish);
		botPanel.setOpaque(false);
		add(botPanel);
	}
	
	public void actionPerformed(ActionEvent e){
		Object src=e.getSource();
 		if(src.equals(next) ){
			showResult(); 
 			quiz.next();
		}
 		if(src.equals(finish)){
			quiz.showSummary();
		}
 		for(int i=0;i<responses.length;i++){
			if(src==responses[i]){
				selected=i;
			}
		}
	}
	
	public void showResult(){
		String text=responses[selected].getText();
		quiz.total++;
		if(selected==correctAns && count == false){
			quiz.corrects++;

			count = true;
  		}
  		else if(selected != correctAns && wcount == false)
  		{
			quiz.wrongs++;
			wcount = true;
		}
	}
}

   
