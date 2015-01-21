package the.locust3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class FindGame {
	public static int BORDER = 300;
	
	private Robot robot;
	private Dimension screenDimension;

	public FindGame (Robot robot, Dimension screenDimesion) {
		this.robot = robot;
		this.screenDimension = screenDimesion;
	}
	
	public int[] start () {
		BufferedImage screen = robot.createScreenCapture(new Rectangle((int) screenDimension.getWidth(), (int) screenDimension.getHeight()));
		
		int gameX = 0, gameY = 0;
		int blackX = 0, blackY = 0;
		int gameWidth = 0, gameHeight = 0;
		
		for (int y = (int) screenDimension.getHeight() - 1; y > 0; y--) {
			int count = 0;
			for (int x = (int) screenDimension.getWidth() - 1; x > 0; x--) {
				Color color = new Color(screen.getRGB(x, y));
				
				if ((color.getRed() == 116 && color.getGreen() == 255 && color.getBlue() == 252) || (color.getRed() == 82 && color.getGreen() == 179 && color.getBlue() == 176)) {
					count++;
					blackX = x;
					blackY = y;
				}
				
				if (count > BORDER) {
					gameX = blackX + 1;
					gameY = blackY + 1;
				}
			}
		}
		
		blackX = 0;
		blackY = 0;
		
		for (int y = 0; y < screenDimension.getHeight(); y++) {
			int count = 0;
			for (int x = 0; x < (int) screenDimension.getWidth() - 1; x++) {
				Color color = new Color(screen.getRGB(x, y));
				
				if ((color.getRed() == 116 && color.getGreen() == 255 && color.getBlue() == 252) || (color.getRed() == 82 && color.getGreen() == 179 && color.getBlue() == 176)) {
					count++;
					blackX = x;
					blackY = y;
				}
				
				if (count > BORDER) {
					gameWidth = Math.abs(blackX - gameX - 2);
					gameHeight = Math.abs(blackY - gameY - 1);
				}
			}
		}
		
		return new int[] {gameX, gameY, gameWidth, gameHeight};
	}
}
