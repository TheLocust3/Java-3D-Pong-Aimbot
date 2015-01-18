package the.locust3;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class ImageThread implements Runnable{
	private static final int SCALE = 60;
	private static final int GREEN = 245;
	private static final int RED = 200;
	private static final int BLUE = 200;
	private static final int ACCURACY = 80;
	private static final int FILTER = 15;
	
	private int startX = 0, startY = 0;
	private int endX = 0, endY = 0;

	private Robot robot;
	
	public ImageThread (Robot robot, int startX, int startY, int endX, int endY) {
		this.robot = robot;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
	@Override
	public void run() {
		while (true) {
			BufferedImage screen = robot.createScreenCapture(new Rectangle(startX, startY, Math.abs(endX - startX), Math.abs(endY - startY)));
			
			int large = 0;
			int ballX = 0, ballY = 0;
			
			for (int w = endX / ACCURACY; w >= 0; w--) {
				int count = 0;
				for (int h = endY / ACCURACY; h >= 0; h--) {
					for (int x = ACCURACY * w; x <= ACCURACY * (w + 1); x++) {
						if (x % SCALE == 0) {
							for (int y = ACCURACY * h; y <= ACCURACY * (h + 1); y++) {
								try {
									Color color = new Color(screen.getRGB(x, y));
									if (color.getGreen() >= GREEN && color.getRed() <= RED && color.getBlue() <= BLUE) {
										count++;
									}
								} catch (Exception e) {}
							}
						}
						
						if (count > large && count > FILTER) {
							ballX = ((w + 1) * ACCURACY) - ACCURACY / 2;
							ballY = ((h + 1) * ACCURACY) - ACCURACY / 2;
							large = count;
							break;
						}
					}
					
					if (large > FILTER) {
						break;
					}
				}
				
				if (large > FILTER) {
					break;
				}
			}
			
			if (large > FILTER) {
				robot.mouseMove(ballX + startX, ballY + startY);
			}

			robot.mousePress(InputEvent.BUTTON1_MASK);
		}
	}
}
