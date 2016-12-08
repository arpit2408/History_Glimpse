/**
 * 
 */
package Core_Logic;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

/**
 * @author arpit2408
 *
 */
class SharedListSelectionHandler implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) { 
        if(e.getValueIsAdjusting()){
        	Browser browser = new Browser();
            BrowserView view = new BrowserView(browser);
             
            JFrame frame = new JFrame();
            frame.add(view, BorderLayout.CENTER);
            frame.setSize(700, 500);
            frame.setVisible(true);
            String pageId=HistoricalEvents.getPageId();
            browser.loadURL("https://en.wikipedia.org/?curid="+pageId);
        }
    }
}
