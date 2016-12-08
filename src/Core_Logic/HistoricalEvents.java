/**
 * 
 */
package Core_Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author arpit2408
 *
 */
public class HistoricalEvents {

	/**
	 * @param args
	 */
	/**
	 * URL prefix to download history content from Wikipedia.
	 */
	private static final String URL_PREFIX =
			"https://en.wikipedia.org/w/api.php?action=query&prop=extracts"
					+ "&format=json&explaintext=&exsectionformat=plain&redirects=&titles=";

	/**
	 * Constant defining number of events to be read at one time.
	 */
	private static final int PAGINATION_SIZE = 3;

	/**
	 * Length of the delimiter between individual events.
	 */
	private static final int DELIMITER_SIZE = 2;

	/**
	 * Constant defining session attribute key for the event index.
	 */
	private static final String SESSION_INDEX = "index";

	/**
	 * Constant defining session attribute key for the event text key for date of events.
	 */
	private static final String SESSION_TEXT = "text";

	/**
	 * Constant defining session attribute key for the intent slot key for the date of events.
	 */
	private static final String SLOT_DAY = "day";

	/**
	 * Size of events from Wikipedia response.
	 */
	private static final int SIZE_OF_EVENTS = 10;
	private static String pageId;
	public static String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	/**
	 * Array of month names.
	 */
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
	
	public HashMap<String,String> getMonths(){
		HashMap<String,String> hash_MONTH_NAMES = new HashMap<>();
		hash_MONTH_NAMES.put("Jan", "January");
		hash_MONTH_NAMES.put("Feb", "February");
		hash_MONTH_NAMES.put("Mar", "March");
		hash_MONTH_NAMES.put("Apr", "April");
		hash_MONTH_NAMES.put("May", "May");
		hash_MONTH_NAMES.put("Jun", "June");
		hash_MONTH_NAMES.put("Jul", "July");
		hash_MONTH_NAMES.put("Aug", "August");
		hash_MONTH_NAMES.put("Sep", "September");
		hash_MONTH_NAMES.put("Oct", "October");
		hash_MONTH_NAMES.put("Nov", "November");
		hash_MONTH_NAMES.put("Dec", "December");
		return hash_MONTH_NAMES;
	}
		
	public static void main(String[] args) {
			HistoricalEvents he=new HistoricalEvents();
			Calendar calendar = he.getCalendar();
	        String month = MONTH_NAMES[calendar.get(Calendar.MONTH)];
	        String date = Integer.toString(calendar.get(Calendar.DATE));
	        ArrayList<String> events = he.getJsonEventsFromWikipedia(month, date);
	        for(String event:events)
	        {
	        	System.out.println(event);
	        }
	}
	public ArrayList<String> getJsonEventsFromWikipedia(String month, String date) {
		InputStreamReader inputStream = null;
		BufferedReader bufferedReader = null;
		String text = "";
		try {
			String line;
			URL url = new URL(URL_PREFIX + month + "_" + date);
			inputStream = new InputStreamReader(url.openStream(), Charset.forName("US-ASCII"));
			bufferedReader = new BufferedReader(inputStream);
			StringBuilder builder = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
			}
			text = builder.toString();
		} catch (IOException e) {
			// reset text variable to a blank string
			text = "";
		} 
		String[] p=text.split("pageid");
		String[] p1=p[1].split(",");
		pageId=p1[0].substring(2, p1[0].length());
		String pageUrl="https://en.wikipedia.org/?curid="+pageId;
		return parseJson(text);
	}
	
	private ArrayList<String> parseJson(String text) {
        text =
                text.substring(text.indexOf("\\nEvents\\n") + SIZE_OF_EVENTS,
                        text.indexOf("\\n\\n\\nBirths"));
        ArrayList<String> events = new ArrayList<String>();
        if (text.isEmpty()) {
            return events;
        }
        int startIndex = 0, endIndex = 0;
        while (endIndex != -1) {
            endIndex = text.indexOf("\\n", startIndex + DELIMITER_SIZE);
            String eventText =
                    (endIndex == -1 ? text.substring(startIndex) : text.substring(startIndex,
                            endIndex));
            // replace dashes returned in text from Wikipedia's API
            Pattern pattern = Pattern.compile("\\\\u2013\\s*");
            Matcher matcher = pattern.matcher(eventText);
            eventText = matcher.replaceAll("");
            // add comma after year so Alexa pauses before continuing with the sentence
            pattern = Pattern.compile("(^\\d+)");
            matcher = pattern.matcher(eventText);
            if (matcher.find()) {
                eventText = matcher.replaceFirst(matcher.group(1) + ",");
            }
            eventText = "In " + eventText;
            startIndex = endIndex + 2;
            events.add(eventText);
        }
        Collections.reverse(events);
        return events;
}
	public Calendar getCalendar() {
        Long daySlot = Long.parseLong("20161105");
        Date date;
        Calendar calendar = Calendar.getInstance();
        if (daySlot != null && daySlot.longValue() != 0) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
            try {
                date = dateFormat.parse("2016-12-26");
            } catch (ParseException e) {
                date = new Date();
            }
        } else {
            date = new Date();
        }
        calendar.setTime(date);
        return calendar;
}
	

}
