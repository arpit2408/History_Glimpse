package View;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
// I must import these packages 
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import Core_Logic.HistoricalEvents;
import Core_Logic.PanelForEvents;
// This class will be used to create an interactive home screen
public class HomeScreen extends JPanel {

	public JPanel panel;
	MyButton buttonArtsAndCulture;
	MyButton buttonEducation;
	MyButton buttonGeography;
	MyButton buttonMilitaryAndWar;
	MyButton buttonScienceAndTechnology;
	MyButton buttonEventsNearMe;
	MyButton buttonGo;
	Color colorGreen;
	Color darkerColorGreen;
	Color colorYellow;
	Color brighterColorYellow;
	static ArrayList<String> categories;
	static HashMap<String,ArrayList<String>> hashMapCategories;
	static ArrayList<String> events=new ArrayList<>();
	JFrame frame ;
	public HomeScreen () {

		setSize(700,700);

		// Add the main panel
		JPanelWithBackground panel = new JPanelWithBackground("/Users/arpit2408/Downloads/firefox_downloads/QuizIcons/scroll.png");


		// Use a box layout to organize textfield and buttons from top to bottom
		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxLayout);
		Color colorYellow = new Color(255,255,0);
		Color brighterColorYellow = colorYellow.brighter();
		//panel.setBackground(brighterColorYellow);

		// Create a new frame for our app
		frame = new JFrame("One Day in History");
		frame.setPreferredSize(new Dimension(735,680));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame, 
						"Are you sure to close this window?", "Really Closing?", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					try {
						FileWriter fwOb = new FileWriter("src/Resources/Questions.txt", false); 
						PrintWriter pwOb = new PrintWriter(fwOb, false);
						pwOb.flush();
						pwOb.close();
						fwOb.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		});
		// Here I want to first add the vintage scroll image, along with the words: One Day in History...

		JLabel titleOfApp = new JLabel("     One Day in History...     ");
		titleOfApp.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font titleFont = new Font("Apple Chancery", Font.ITALIC, 65);
		titleOfApp.setFont(titleFont);
		panel.add(titleOfApp);

		// Add a text box for the user to use for their search to the first panel
		/*JTextField searchBox = new JTextField("Enter a date in the form MM/DD/YYYY");
		searchBox.setSize(100,50);
		searchBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(searchBox);*/

		UtilDateModel model = new UtilDateModel();
		//model.setDate(20,04,2014);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		model.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if(e.getPropertyName().equals("value") && e.getNewValue()!=null)
				{
					String[] dateElements=e.getNewValue().toString().split(" ");
					HistoricalEvents he=new HistoricalEvents();
					HashMap<String,String> hash_MONTH_NAMES=he.getMonths();
					String selectedMonth=hash_MONTH_NAMES.get(dateElements[1]);
					String selectedDay=dateElements[2];
					events = he.getJsonEventsFromWikipedia(selectedMonth, selectedDay);
				}
			}
		});
		panel.add(datePicker);

		JLabel spaceA = new JLabel("               ");
		spaceA.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font spaceFontA = new Font("Arial", Font.PLAIN, 25);
		spaceA.setFont(spaceFontA);
		panel.add(spaceA);

		JLabel selectYourCategories = new JLabel("Select categories of interest:");
		selectYourCategories.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font selectFont = new Font("Cambria", Font.PLAIN, 25);
		selectYourCategories.setFont(selectFont);
		panel.add(selectYourCategories);

		JLabel spaceB = new JLabel("               ");
		spaceB.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font spaceFontB = new Font("Arial", Font.PLAIN, 25);
		spaceB.setFont(spaceFontB);
		panel.add(spaceB);

		// Make an instance of class ButtonActionListener
		ButtonActionListener listener = new ButtonActionListener();

		MyButton buttonForAll= new MyButton("Display All Events");
		buttonForAll.addActionListener(listener);
		buttonForAll.setAlignmentX(Component.CENTER_ALIGNMENT);
		//buttonForAll.setBackground(Color.orange);
		Font buttonforAllFont = new Font("Monotype Corsiva", Font.PLAIN, 25);
		buttonForAll.setFont(buttonforAllFont);
		//setIconsforButtons(buttonForAll,"/Images/7caXpdAcA.gif");
		panel.add(buttonForAll);

		// Add buttons to the panel, each of which represents a category of our historical events
		MyButton buttonArtsAndCulture= new MyButton("Arts & Culture");
		buttonArtsAndCulture.addActionListener(listener);
		buttonArtsAndCulture.setAlignmentX(Component.CENTER_ALIGNMENT);
		//buttonArtsAndCulture.setBackground(Color.MAGENTA);
		Font artsFont = new Font("Bradley Hand", Font.PLAIN, 25);
		buttonArtsAndCulture.setFont(artsFont);
		setIconsforButtons(buttonArtsAndCulture,"/Images/7caXpdAcA.gif");
		panel.add(buttonArtsAndCulture);

		MyButton buttonEducation = new MyButton("Education");
		buttonEducation.addActionListener(listener);
		buttonEducation.setAlignmentX(Component.CENTER_ALIGNMENT);
		//buttonEducation.setBackground(Color.BLUE);
		Font educationFont = new Font("Chalkduster", Font.PLAIN, 25);
		buttonEducation.setFont(educationFont);
		setIconsforButtons(buttonEducation,"/Images/graduation-cap-variant_318-47155.jpg");
		panel.add(buttonEducation);

		Color colorGreen = new Color(0,255,0);
		Color darkerColorGreen = colorGreen.darker();
		MyButton buttonGeography = new MyButton("Geography");
		buttonGeography.addActionListener(listener);
		buttonGeography.setAlignmentX(Component.CENTER_ALIGNMENT);
		//buttonGeography.setBackground(darkerColorGreen);
		Font geographyFont = new Font("Papyrus", Font.PLAIN, 25);
		buttonGeography.setFont(geographyFont);
		setIconsforButtons(buttonGeography,"/Images/world-globe-icon.jpg");
		panel.add(buttonGeography);


		MyButton buttonMilitaryAndWar = new MyButton("Military & War");
		buttonMilitaryAndWar.addActionListener(listener);
		buttonMilitaryAndWar.setAlignmentX(Component.CENTER_ALIGNMENT);
		//buttonMilitaryAndWar.setBackground(Color.RED);
		Font militaryFont = new Font("Impact", Font.PLAIN, 25);
		buttonMilitaryAndWar.setFont(militaryFont);
		setIconsforButtons(buttonMilitaryAndWar,"/Images/tanque-comic.png");
		panel.add(buttonMilitaryAndWar);

		MyButton buttonScienceAndTechnology = new MyButton("Science & Technology");
		buttonScienceAndTechnology.addActionListener(listener);
		buttonScienceAndTechnology.setAlignmentX(Component.CENTER_ALIGNMENT);
		//buttonScienceAndTechnology.setBackground(Color.CYAN);
		Font scienceFont = new Font("Herculanum", Font.PLAIN, 25);
		buttonScienceAndTechnology.setFont(scienceFont);
		setIconsforButtons(buttonScienceAndTechnology,"/Images/Atom.jpg");
		panel.add(buttonScienceAndTechnology);



		JLabel spaceC = new JLabel("               ");
		spaceC.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font spaceFontC = new Font("Arial", Font.PLAIN, 25);
		spaceC.setFont(spaceFontC);
		panel.add(spaceC);

		// Add a Go button at the end to begin the search
		/*MyButtonForGo buttonGo = new MyButtonForGo("Go!");
		buttonGo.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonGo.setBackground(Color.GREEN);
		Font goFont = new Font("Arial Black", Font.ITALIC, 28);
		buttonGo.setFont(goFont);
		buttonGo.setBorder(new RoundedBorder(10));
		panel.add(buttonGo);*/

		JLabel spaceD = new JLabel("               ");
		spaceD.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font spaceFontD = new Font("Arial", Font.PLAIN, 25);
		spaceD.setFont(spaceFontD);
		panel.add(spaceD);

		// Add panel to frame and make frame visible to user
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

	}
	public BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) throws IOException {  
		BufferedImage resizedImage = new BufferedImage(width, height, type);  
		Graphics2D g = resizedImage.createGraphics();  
		g.drawImage(originalImage, 0, 0, width, height, null);  
		g.dispose();  
		return resizedImage;  
	} 
	public class RoundedBorder implements Border {
		int radius;
		RoundedBorder(int radius) {
			this.radius = radius;
		}
		public Insets getBorderInsets(Component c) {
			return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
		}
		public boolean isBorderOpaque() {
			return true;
		}
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.drawRoundRect(x,y,width-1,height-1,radius,radius);
		}
	}

	// Main method accepting String[]
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// handle exception
		}
		new HomeScreen();
		categories =new ArrayList<>();
		try {
			categories = (ArrayList<String>) Files.lines(Paths.get("src/Resources/KeywordList.txt"))
					.map(line -> line.split("\n")).flatMap(Arrays::stream)
					.map(String::valueOf)
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> words=categories.parallelStream()
				.map(p->p.split(":"))
				.collect(Collectors.toList())
				.parallelStream()
				.map(p->p[0])
				.collect(Collectors.toList());
		List<String[]> categoryWords=categories.parallelStream()
				.map(p->p.split(":"))
				.collect(Collectors.toList())
				.parallelStream()
				.map(p->p[1].split(","))
				.collect(Collectors.toList());
		hashMapCategories=new HashMap<>();
		for(int i=0;i<words.size();i++){
			if(words.get(i)!=null)
				hashMapCategories.put(words.get(i),new ArrayList<String>(Arrays.asList(categoryWords.get(i))));
		}

	}


	public ArrayList<String> getListOfEvents(String source){
		ArrayList<String> filteredEvents=new ArrayList<>();
		ArrayList<String> keyWordsToCheck=hashMapCategories.get(source);
		for(String event:events){
			for(String keyWord:keyWordsToCheck){
				String key=keyWord.toLowerCase();
				if(event.toLowerCase().contains(key))
					filteredEvents.add(event);
			}
		}
		return filteredEvents;
	}
	public void setIconsforButtons(MyButton myButton,String path){
		try{
			BufferedImage img = ImageIO.read(getClass().getResource(path));
			BufferedImage imgresized=resizeImage(img,30,30,BufferedImage.TYPE_INT_ARGB);
			myButton.setIcon(new ImageIcon(imgresized));
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
	}
	// We have imported the class so our buttons can perform actions when clicked
	class ButtonActionListener implements ActionListener {

		// The goal of this method is to highlight a button in yellow if it is clicked
		public void actionPerformed(ActionEvent click) {
			Object[] options = {"OK"};
			// print buttonName if clicked
			if(click.getSource() instanceof JButton )
			{
				if(events.isEmpty())
				{

					JOptionPane.showOptionDialog(frame,
							"Please select a date to start..!!" ,"Select a date..!!",
							JOptionPane.PLAIN_MESSAGE,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]);
				}
				else
				{
					((MyButton)click.getSource()).setPressedBackgroundColor(Color.WHITE);
					((JButton)click.getSource()).setBackground(Color.ORANGE); 
					((JButton)click.getSource()).setOpaque(false);
					((JButton)click.getSource()).setContentAreaFilled(true);
					((JButton)click.getSource()).setBorderPainted(false);

					javax.swing.SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							ArrayList<String> eventsSelected=new ArrayList<>();
							if(click.getActionCommand().toString().equals("Display All Events"))
								eventsSelected=events;
							else{
								eventsSelected=getListOfEvents(click.getActionCommand().toString());
							}
							if(eventsSelected.isEmpty())
							{
								JOptionPane.showOptionDialog(frame,
										"There are no "+click.getActionCommand().toString()+" events for this date..!!" ,"Events",
										JOptionPane.PLAIN_MESSAGE,
										JOptionPane.QUESTION_MESSAGE,
										null,
										options,
										options[0]);
							}
							else
							{
								URI uri = null;
								
								    try {
										uri = new URI("Questions.txt");
									} catch (URISyntaxException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}  
								             
								//Path file=Paths.get(uri);
								Path file = Paths.get("src/Resources/Questions.txt");
								try {
									Files.write(file, eventsSelected, Charset.forName("UTF-8"),StandardOpenOption.APPEND);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								PanelForEvents.createAndShowGUI(eventsSelected);
							}

						}
					});
				}
			}


		} 
	}
}
