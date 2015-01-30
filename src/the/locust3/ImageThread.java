package the.locust3;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class ImageThread extends Thread {
	private boolean running = false;
	
	public static int GREEN = 245;
	public static int BLUE = 240;
	public static int XSCALE = 25;
	public static int YSCALE = 5;
	public static int ACCURACY = 25;
	public static int FILTER = 3;
	
	private int startX = 0, startY = 0;
	private int width = 0, height = 0;
	
	private int side = 1;

	private Robot robot;
	
	public ImageThread (Robot robot, int startX, int startY, int width, int height, int side) {
		this.robot = robot;
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;
		this.side = side;
	}
	
	@Override
	public void run() {
		running = true;
		
		while (running) {
			BufferedImage screen = robot.createScreenCapture(new Rectangle(startX, startY, width, height));
			
			if (side == 1) {
				rightTop(screen);
			} else if (side == 3) {
				rightBottom(screen);
			} else if (side == 2) { 
				leftTop(screen);
			} else {
				leftBottom(screen);
			}

			robot.mousePress(InputEvent.BUTTON1_MASK);
		}
	}
	
	public void kill () {
		running = false;
	}
	
	private int[] innerProcess (int w, int h, BufferedImage screen, int count) {
		int ballX = 0, ballY = 0, large = 0;
		for (int x = ACCURACY * w; x <= ACCURACY * (w + 1); x++) {
			if (x % XSCALE == 0) {
				for (int y = ACCURACY * h; y <= ACCURACY * (h + 1); y++) {
					if (y % YSCALE == 0) {
						try {
							int rgb = screen.getRGB(x, y);
							int green = (rgb >> 8) & 0x000000FF;
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
		
		return new int[]{ballX, ballY, large};
	}
	
	private void rightTop (BufferedImage screen) {
		int ballX = 0;
		int ballY = 0;
		
		int large = 0;
		
		double wGoTo = width / ACCURACY;
		double hGoTo = height / ACCURACY;
		
		for (int w = 0; w <= wGoTo; w++) {
			int count = 0;
			for (int h = 0; h <= hGoTo; h++) {
				
				int[] output = innerProcess(w, h, screen, count);
				ballX = output[0];
				ballY = output[1];
				large = output[2];
				
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
	}
	
	private void rightBottom (BufferedImage screen) {
		int ballX = 0;
		int ballY = 0;
		
		int large = 0;
		
		double wGoTo = (width / ACCURACY) - 1;
		double hGoTo = (height / ACCURACY) - 1;
		
		for (int w = 0; w <= wGoTo; w++) {
			int count = 0;
			for (int h = (int) hGoTo; h >= 0; h--) {
				for (int x = ACCURACY * w; x <= ACCURACY * (w + 1); x++) {
					
					int[] output = innerProcess(w, h, screen, count);
					ballX = output[0];
					ballY = output[1];
					large = output[2];
					
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
	}
	
	private void leftTop (BufferedImage screen) {
		int ballX = 0;
		int ballY = 0;
		
		int large = 0;
		
		double wGoTo = (width / ACCURACY) - 1;
		double hGoTo = (height / ACCURACY) - 1;
		
		for (int w = (int) wGoTo; w >= 0; w--) {
			int count = 0;
			for (int h = 0; h <= hGoTo; h++) {
				for (int x = ACCURACY * w; x <= ACCURACY * (w + 1); x++) {
					
					int[] output = innerProcess(w, h, screen, count);
					ballX = output[0];
					ballY = output[1];
					large = output[2];
					
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
	}
	
	private void leftBottom (BufferedImage screen) {
		int ballX = 0;
		int ballY = 0;
		
		int large = 0;
		
		double wGoTo = (width / ACCURACY) - 1;
		double hGoTo = (height / ACCURACY) - 1;
		
		for (int w = (int) wGoTo; w >= 0; w--) {
			int count = 0;
			for (int h = (int) hGoTo; h >= 0; h--) {
				for (int x = ACCURACY * w; x <= ACCURACY * (w + 1); x++) {
					
					int[] output = innerProcess(w, h, screen, count);
					ballX = output[0];
					ballY = output[1];
					large = output[2];
					
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
	}
}
