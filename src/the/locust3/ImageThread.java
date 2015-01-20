package the.locust3;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class ImageThread extends Thread{
	private static final int GREEN = 245;
	private static final int XSCALE = 25;
	private static final int YSCALE = 5;
	private static final int ACCURACY = 25;
	private static final int FILTER = 7;
	
	private int startX = 0, startY = 0;
	private int width = 0, height = 0;

	private Robot robot;
	
	public ImageThread (Robot robot, int startX, int startY, int width, int height) {
		this.robot = robot;
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void run() {
		while (true) {
			BufferedImage screen = robot.createScreenCapture(new Rectangle(startX, startY, width, height));
			
			int large = 0;
			int ballX = 0, ballY = 0;
			
			for (int w = width / ACCURACY; w >= 0; w--) {
				int count = 0;
				for (int h = height / ACCURACY; h >= 0; h--) {
					for (int x = ACCURACY * w; x <= ACCURACY * (w + 1); x++) {
						if (x % XSCALE == 0) {
							for (int y = ACCURACY * h; y <= ACCURACY * (h + 1); y++) {
								if (y % YSCALE == 0) {
									try {
										Color color = new Color(screen.getRGB(x, y));
										if (color.getGreen() >= GREEN) {
											count++;
										}
									} catch (Exception e) {}
								}
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
