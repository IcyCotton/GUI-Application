import java.awt.*;
import java.net.URL;
import javax.swing.*; // Important for loading images from the classpath


public class ImagePanel extends JPanel {
    // Custom JPanel that draws a background image
    private Image backgroundImage;// The image to be drawn as the background

    public ImagePanel(String imagePath) {
        // Constructor that takes the image path as a parameter
        try {
           
            // Load the image from the classpath
            URL imageUrl = getClass().getResource(imagePath);
            // Use getResource to find the image in the classpath
            if (imageUrl != null) {// Check if the image was found
                backgroundImage = new ImageIcon(imageUrl).getImage();
                // Create an ImageIcon from the URL and get the Image
            } else {
                // If the image was not found, throw an exception
                throw new IllegalArgumentException("Image not found: " + imagePath);
            }

        } catch (Exception e) {
            // Handle any exceptions that may occur during image loading
            System.err.println("Error loading background image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        // Call the superclass method to ensure proper painting

        if (backgroundImage != null) {
            // Draw the background image
            // Fill the entire panel with the background image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        }
    }
}