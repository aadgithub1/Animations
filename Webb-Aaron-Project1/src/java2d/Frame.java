package java2d;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/*
 * Filename: Frame
 * Author: Aaron Webb
 * Date: Aug 27, 2024
 * Description: Create a JFrame object, to hold the Animations
 * panel.
 */

public class Frame extends JFrame{
	
	private Frame() {
		Animations animations = new Animations();
		setContentPane(animations);
		setTitle("Java2D Animation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End program when window closes.
        pack();  // Set window size based on the preferred sizes of its contents.
        setResizable(false); // Don't let user resize window.
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation( // Center window on screen.
                (screen.width - getWidth()) / 2,
                (screen.height - getHeight()) / 2);
        setVisible(true);
	}
	
	public static void main(String[] args) {
		Frame frame = new Frame();
	}
}
