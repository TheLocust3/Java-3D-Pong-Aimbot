package the.locust3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageThread implements Runnable {
	private static final int SCALE = 60;
	private static final int GREEN = 245;
	private static final int RED = 200;
	private static final int BLUE = 200;
	private static final int ACCURACY = 70;
	private static final int FILTER = 10;

	private Robot robot;
	private Dimension screenSize;
	
	public ImageThread (Robot robot, Dimension screenSize) {
		this.robot = robot;
		this.screenSize = screenSize;
	}
	
	@Override
	public void run() {
		while (true) {
			BufferedImage screen = robot.createScreenCapture(new Rectangle((int) (screenSize.getWidth() / 1.5), (int) (screenSize.getHeight() / 1.25)));
			
			int large = 0;
			int ballX = 0, ballY = 0;
			
			for (int w = 0; w <= screen.getWidth() / ACCURACY; w++) {
				int count = 0;
				for (int h = 0; h <= screen.getHeight() / ACCURACY; h++) {
					for (int x = ACCURACY * w; x <= ACCURACY * (w + 1); x++) {
						if (x % SCALE == 0) {
							for (int y = ACCURACY * h; y <= ACCURACY * (h + 1); y++) {
								try {
									Color color = new Color(screen.getRGB(x, y));
									if (color.getGreen() >= GREEN && color.getRed() <= RED && color.getBlue() <= BLUE) {
										count++;
										//screen.setRGB(x, y, 16777215);
									} else {
										//screen.setRGB(x, y, 0);
									}
								} catch (Exception e) {}
							}
						} else {
							/*for (int y = ACCURACY * h; y <= ACCURACY * (h + 1); y++) {
								try {
									screen.setRGB(x, y, 0);
								} catch (Exception e) {}
							}*/
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
			
			/*for (int x = ballX - ACCURACY; x < ballX; x++) {
				for (int y = ballY - ACCURACY; y < ballY; y++) {
					screen.setRGB(x, y, 16777215);
				}
			}*/
			
			if (large > FILTER) {
				robot.mouseMove(ballX, ballY);
			}

			/*File output = new File("/Users/jakekinsella/Desktop/output.png");
			try {
				ImageIO.write(screen, "png", output);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			robot.mousePress(InputEvent.BUTTON1_MASK);
		}
	}
}
