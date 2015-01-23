package the.locust3;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class ImageThread extends Thread {
	private boolean running = false;
	
	public static int GREEN = 240;
	public static int BLUE = 240;
	public static int XSCALE = 25;
	public static int YSCALE = 5;
	public static int ACCURACY = 25;
	public static int FILTER = 2;
	
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
		running = true;
		
		while (running) {
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
										int rgb = screen.getRGB(x, y);
										int green = (rgb >>8 ) & 0x000000FF;
										int blue = (rgb) & 0x000000FF;
										if (green >= GREEN && blue <= BLUE) {
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
	
	public void kill () {
		running = false;
	}
}
