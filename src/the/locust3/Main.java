package the.locust3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	static final int REST = 10;
	static final int THREADS = 1;

	public static void main (String args[]) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Robot robot;
		
		try {
			robot = new Robot();
			
			Thread.sleep(5000);
			
			Thread threads[] = new Thread[THREADS];
			
			for (int i = 0; i < threads.length; i++) {
				threads[i] = new Thread(new ImageThread(robot, screenSize));
				threads[i].start();
				Thread.sleep(REST);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
