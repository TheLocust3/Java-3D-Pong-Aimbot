package the.locust3;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;

public class Main {
	public static void main (String args[]) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Robot robot;
		
		try {
			robot = new Robot();
			
			Thread.sleep(5000);
			
			ImageThread threads[] = new ImageThread[4];
			
			FindGame findGame = new FindGame(robot, screenSize);
			int dimensions[] = findGame.start();
			
			int x = dimensions[0];
			int y = dimensions[1];
			int width = dimensions[2];
			int height = dimensions[3];
			
			threads[0] = new ImageThread(robot, x, y, width / 2, height / 2);
			threads[0].start();

			threads[1] = new ImageThread(robot, x + width / 2, y, width / 2, height / 2);
			threads[1].start();
			
			threads[2] = new ImageThread(robot, x, y + height / 2, width / 2, height / 2);
			threads[2].start();

			threads[3] = new ImageThread(robot, x + width / 2, y + height / 2, width / 2, height / 2);
			threads[3].start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
