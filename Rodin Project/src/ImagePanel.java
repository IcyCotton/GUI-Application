import java.awt.*;
import java.net.URL;
import javax.swing.*; // Important for loading images from the classpath

// This is a new class you will create, usually in its own file (e.g., ImagePanel.java)
public class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        try {
           
            // Load the image from the classpath
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
            } else {
                throw new IllegalArgumentException("Image not found: " + imagePath);
            }

        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Always call super.paintComponent for proper rendering

        if (backgroundImage != null) {
            // Draw the image stretched to fill the panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            // You can choose other drawing options here (e.g., tiling, centering)
            // based on what looks best for your image and window.
        }
    }
}