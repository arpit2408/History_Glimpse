/**
 * 
 */
package Core_Logic;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author arpit2408
 *
 */


public class JPanelWithBackground extends JPanel {

  private Image backgroundImage;

  // Some code to initialize the background image.
  // Here, we use the constructor to load the image. This
  // can vary depending on the use case of the panel.
  public JPanelWithBackground(String fileName)  {
    try {
		backgroundImage = ImageIO.read(new File(fileName));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  public BufferedImage animateCircle(BufferedImage originalImage, int type){

      //The opacity exponentially decreases
	  int initialWidth=originalImage.getWidth();
	  int initialHeight=originalImage.getHeight();
	  float opacity = 0.5f;
      initialWidth += 10;
      initialHeight += 10;

      BufferedImage resizedImage = new BufferedImage(initialWidth, initialHeight, type);
      Graphics2D g = resizedImage.createGraphics();
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
      g.drawImage(originalImage, 0, 0, initialWidth, initialHeight, null);
      g.dispose();

      return resizedImage;

}
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw the background image.
    g.drawImage(backgroundImage, 0, 0, this);
  }
}

