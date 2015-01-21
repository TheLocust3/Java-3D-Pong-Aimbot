package the.locust3;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class Main implements ActionListener {
	private boolean isRunning = false;
	
	ImageThread threads[] = new ImageThread[4];
	
	JButton startButton;
	
	JTextField xScaleInput, yScaleInput, accuracyInput, greenInput, filterInput;
	
	Main () {
		//Set nice looking Nimbus theme
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
        JFrame jFrame = new JFrame("Aimbot");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(230, 200);
        
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(6, 2));
        Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        jPanel.setBorder(padding);
        
        JLabel xScaleLabel = new JLabel("X-Scale: ");
        xScaleInput = new JTextField(4);
        xScaleInput.setText(Integer.toString(ImageThread.XSCALE));

        JLabel yScaleLabel = new JLabel("Y-Scale: ");
        yScaleInput = new JTextField(4);
        yScaleInput.setText(Integer.toString(ImageThread.YSCALE));
        
        JLabel accuracyLabel = new JLabel("Accuracy: ");
        accuracyInput = new JTextField(4);
        accuracyInput.setText(Integer.toString(ImageThread.ACCURACY));

        JLabel greenLabel = new JLabel("Green: ");
        greenInput = new JTextField(4);
        greenInput.setText(Integer.toString(ImageThread.GREEN));
        
        JLabel filterLabel = new JLabel("Filter: ");
        filterInput = new JTextField(4);
        filterInput.setText(Integer.toString(ImageThread.FILTER));

        JLabel startLabel = new JLabel("Press enter to stop");
        
        startButton = new JButton("Start");
        startButton.setActionCommand("StartButton");
        startButton.addActionListener(this);
        
		JRootPane rootPane = SwingUtilities.getRootPane(jFrame); 
		rootPane.setDefaultButton(startButton);
        
		jPanel.add(xScaleLabel);
		jPanel.add(xScaleInput);
		jPanel.add(yScaleLabel);
		jPanel.add(yScaleInput);
		jPanel.add(accuracyLabel);
		jPanel.add(accuracyInput);
		jPanel.add(greenLabel);
		jPanel.add(greenInput);
		jPanel.add(filterLabel);
		jPanel.add(filterInput);
		jPanel.add(startLabel);
		
		jFrame.setContentPane(jPanel);
		jFrame.add(startButton);
		
		jFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getActionCommand().equals("StartButton")) {	
			isRunning = !isRunning;
			
			if (isRunning) {
				startButton.setText("Stop");
				
				ImageThread.XSCALE = Integer.parseInt(xScaleInput.getText());
				ImageThread.YSCALE = Integer.parseInt(yScaleInput.getText());
				ImageThread.ACCURACY = Integer.parseInt(accuracyInput.getText());
				ImageThread.GREEN = Integer.parseInt(greenInput.getText());
				ImageThread.FILTER = Integer.parseInt(filterInput.getText());
				
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Robot robot;
				
				try {
					robot = new Robot();
					
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
			} else {
				startButton.setText("Stop");

				threads[0].kill();
				threads[1].kill();
				threads[2].kill();
				threads[3].kill();
			}
		}
	}
}
