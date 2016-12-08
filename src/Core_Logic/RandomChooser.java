/**
 * 
 */
package Core_Logic;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author arpit2408
 *
 */
public class RandomChooser {
	public static int getRandomInteger(int min,int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}
