/**
 * 
 */
package Core_Logic;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

	
	public class Quiz extends JFrame{
		
		private static final long serialVersionUID = 1L;
		
		public static final String[] MONTH_NAMES = {
				"January",
				"February",
				"March",
				"April",
				"May",
				"June",
				"July",
				"August",
				"September",
				"October",
				"November",
				"December"
		};
		JPanel p;
		CardLayout cards=new CardLayout();
		int num = 1;
		boolean v = false;
		int i=0; 

		int wrongs = 0;
		int corrects=0;
		int total=9;

		public static void main(String args[]){
			new Quiz();
		}
		
		public Quiz(){
			super("History Quiz");
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
			
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
			setResizable(true);
			setSize(600,400);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			p=new JPanelWithBackground("/Users/arpit2408/Documents/histopy/MATHS_IMAGE.jpeg");
			
			p.setLayout(cards);
			ArrayList<RadioQuestion> arrQuestions=new ArrayList<RadioQuestion>();
			HistoricalEvents he=new HistoricalEvents();
			Calendar calendar = he.getCalendar();
	        String month = MONTH_NAMES[calendar.get(Calendar.MONTH)];
	        String date = Integer.toString(calendar.get(Calendar.DATE));
	        ArrayList<String> events =new ArrayList<>();
	        try {
				events = (ArrayList<String>) Files.lines(Paths.get("src/Resources/Questions.txt"))
					    .map(line -> line.split("\n")).flatMap(Arrays::stream)
					    .map(String::valueOf)
					    .collect(Collectors.toList());
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        ArrayList<String> AYears =new ArrayList<>();
	        for(String eve:events){
	        	String[] parts=eve.split(",");
	        	String[] years=parts[0].split(" ");
	        	String ans=years[1].toString();
	        	AYears.add(ans);
	        }
	        for(int i=0;i<10;i++)
	        {
	        	int randomIndex=RandomChooser.getRandomInteger(0, events.size()-1);
	        	int randomAnswerOption=RandomChooser.getRandomInteger(0, 3);
	        	StringBuilder qFrame=new StringBuilder();
	        	String[] parts=events.get(randomIndex).split(",");
	        	String[] years=parts[0].split(" ");
	        	String ans=years[1].toString();
	        	qFrame.append("When was,");
	        	qFrame.append(parts[1]);
	        	qFrame.append("?");
	        	ArrayList<String> answerOptions =new ArrayList<>();
	        	int counter=0;
	        	while(counter<randomAnswerOption){
	        		answerOptions.add(counter,AYears.get(RandomChooser.getRandomInteger(0, AYears.size()-1)));
	        		counter++;
	        	}
	        	answerOptions.add(randomAnswerOption,ans);
	        	for(int j=counter;j<3;j++)
	        		answerOptions.add(j,AYears.get(RandomChooser.getRandomInteger(0, AYears.size()-1)));
	        	RadioQuestion tempQues=new RadioQuestion(
	        			qFrame.toString(),
	        			answerOptions,
	    				randomAnswerOption,this
	    			);
	        	arrQuestions.add(tempQues);
	        }
			
	        for(int i=0;i<arrQuestions.size();i++)
	        {
	        	p.setFont(new Font("Monotype Corsiva", Font.ITALIC, 30));
	        	p.add(arrQuestions.get(i),"q"+i);
	        	
	        }
			add(p);
			setVisible(true);
		}
		
		public void next(){
			if((corrects+wrongs)==total){
				showSummary();
			}else{
	 
	 			cards.show(p,"q"+i);
				i = i + 1;
	 		 
			}
		}
		
		public void showSummary(){
			if(corrects>7){
				JOptionPane.showMessageDialog(null,"Here are your results"+
			 			
	 			"\nScore: \t"+corrects+
	 			
	 			"\nTotal: 10"+
	 			
	 			"\nResult: Congratulations you passed..!!"
	 			
	 				
			);
 			}
 			else{
 				JOptionPane.showMessageDialog(null,"Here are your results"+
 			 			
	 			"\nScore: \t"+corrects+
	 			
	 			"\nTotal: 10"+
	 			
	 			"\nResult: You Failed..!!"
	 				
			);
 				
 			}
			
			//System.exit(0);
		}
	}

