package java2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Filename: Animations
 * Author: Aaron Webb
 * Date: Aug 27, 2024
 * Description: Create a JPanel object, alters transforms,
 * draws images, and maintains timer for animation execution.
 */

public class Animations extends JPanel {

    // A counter that increases by one in each frame.
    private int frameNumber;
    private float pixelSize;

    static int translateX = 0;
    static int translateY = 0;
    static double rotation = 0.0;
    static double scaleX = 1.0;
    static double scaleY = 1.0;
    
    Images myImages = new Images();
    BufferedImage beaker = myImages.getImage(Images.beaker);
    BufferedImage cross = myImages.getImage(Images.cross);
    BufferedImage welderMask = myImages.getImage(Images.welderMask);
    
    public Animations() {
        setPreferredSize(new Dimension(800, 600));
        Timer animationTimer;  // A Timer that will emit events to drive the animation.
        
        // Taken from AnimationStarter
        animationTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (frameNumber >= 4) {
                    frameNumber = 0;
                } else {
                    frameNumber++;
                }
                repaint();
            }
        });
        animationTimer.start();  // Start the animation running.
    }

    //Taken from AnimationStarter, but with added loop
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight()); //From the old graphics API!

        //Create new coordinate system
        //Controls your zoom and area you are looking at
        applyWindowToViewportTransformation(g2, -75, 75, -75, 75, true);

        AffineTransform savedTransform = g2.getTransform();
        
        System.out.println("Frame is " + frameNumber);
        
        switch (frameNumber) {
	        case 0: // Reset transforms
	            translateX = 0;
	            translateY = 0;
	            scaleX = 1.0;
	            scaleY = 1.0;
	            rotation = 0;
	            break;
            case 1: // First frame translates -5 in x direction, 7 in y
                 translateX = -5;
                 translateY = 7;
                 scaleX = 1.0;
                 scaleY = 1.0;
                 rotation = 0;
                break;
            case 2: // Second frame rotates all images 45 degrees counterclockwise.
                rotation += 45*Math.PI / 180.0;
                break;
            case 3: // Third frame rotates each image by 90 degrees clockwise.
                rotation += -90;
                break;
            case 4: // Fourth frame scales each image by 2 in the x direction, .5 in the y.
                scaleX = 2;
                scaleY = 0.5;
                break;
            default:
                break;
        } // End switch
        
        //Beaker image
        g2.translate(translateX, translateY); // Move image.
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(beaker, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);
        
        //Cross image
        g2.translate(translateX, translateY); // Move image.
        g2.translate(-30,30);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(cross, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);
        
        //Welder Mask image
        g2.translate(translateX, translateY);
        g2.translate(30,-30);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(welderMask, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);

    }

    /**
     * Sets up a new coordinate system
     *
     * @param Graphics g2 object, allows rendering
     * @param double left (left of Viewport), used to calculate width
     * @param double right (right of Viewport), used to calculate width
     * @param double bottom (bottom of Viewport), used to calculate height
     * @param double top (top of Viewport), used to calculate height
     * @param preserveAspect boolean to determine if Aspect Ratio should be maintained
     */
    
    private void applyWindowToViewportTransformation(Graphics2D g2,
            double left, double right, double bottom, double top,
            boolean preserveAspect) {
        int width = getWidth();   // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double) height / width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
                bottom += excess / 2;
                top -= excess / 2;
            } else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess / 2;
            }
        }
        
        g2.scale(width / (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        
        double pixelWidth = Math.abs((right - left) / width);
        double pixelHeight = Math.abs((bottom - top) / height);
        
        pixelSize = (float) Math.max(pixelWidth, pixelHeight);
    }
}
