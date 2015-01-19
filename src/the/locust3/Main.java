package the.locust3;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;

public class Main {
	static final int THREADS = 4;
	static final double GAMEWIDTH = 1.8;
	static final double GAMEHEIGHT = 1.5;
	static final int XOFFSET = 25;
	static final int YOFFSET = 50;

	public static void main (String args[]) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Robot robot;
		
		try {
			robot = new Robot();
			
			Thread.sleep(5000);
			
			Thread threads[] = new Thread[THREADS];
			
			int width = (int) (screenSize.getWidth() / GAMEWIDTH + XOFFSET);
			int height = (int) (screenSize.getHeight() / GAMEHEIGHT + YOFFSET);
			
			threads[0] = new Thread(new ImageThread(robot, 0, 0, width / 2 , height / 2));
			threads[0].start();

			threads[1] = new Thread(new ImageThread(robot, width / 2, 0, width, height / 2));
			threads[1].start();
			
			threads[2] = new Thread(new ImageThread(robot, 0, height / 2, width / 2 , height));
			threads[2].start();

			threads[3] = new Thread(new ImageThread(robot, width / 2, height / 2, width, height));
			threads[3].start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
