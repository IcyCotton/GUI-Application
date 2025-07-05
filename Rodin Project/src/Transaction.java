import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Transaction implements ActionListener {

    JFrame MyFrame; // Main frame for the Transaction window
    JLabel lblTitle, lblCategory;// Labels for title and category
    JButton btnLoan, btnBill;// Buttons for Loan and Bill options

    
    private static Transaction instance; 
    // This ensures that only one instance of Transaction can exist at a time

    public Transaction() {
       
        instance = this; // Set the static instance to this object
        // This constructor initializes the Transaction frame and its components
        // Set up the Transaction frame and its components

        MyFrame = new JFrame();
        // Create a new JFrame for the Transaction window
        lblTitle = new JLabel("Loan & Bill System");
        // Title label for the Transaction window
        lblCategory = new JLabel("Pick Category");
        // Category label for the Transaction window
        btnLoan = new JButton("Loan");// Button for Loan option
        btnBill = new JButton("Bill");// Button for Bill option
        MyFrame.setTitle(lblTitle.getText());
        // Set the title of the JFrame to the text of lblTitle

     
        ImagePanel backgroundPanel = new ImagePanel("/Images/transactbsg.png");
        // Create a custom panel with a background image
        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        // Set the layout of the background panel to FlowLayout with centered alignment and spacing
        
        lblCategory.setForeground(java.awt.Color.WHITE); // for visibility

        // Set the components and bsg properties to show in the JFrame.
        backgroundPanel.add(lblCategory);
        backgroundPanel.add(btnLoan);
        backgroundPanel.add(btnBill);

        // Add the backgroundPanel to the JFrame.
        MyFrame.add(backgroundPanel);

        // Frame Main Settings
        MyFrame.setSize(400, 300);
        MyFrame.setLocationRelativeTo(null);
        MyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the default close operation to exit the application
        MyFrame.setVisible(true);

        // ActionListener for buttons
        btnLoan.addActionListener(this);
        btnBill.addActionListener(this);
    }

   
    public static Transaction getInstance() {
        // Static method to get the single instance of Transaction
       
        if (instance == null) {
           
        }
        return instance; // instance will be set by the invokeLater in main
    }

    // Getter for the JFrame
    public JFrame getMyFrame() {
       
        return MyFrame;// Return the main frame of the Transaction window
    }

    public static void main(String[] args) {
        // Main method to run the Transaction application
        SwingUtilities.invokeLater(() -> {
            // Ensure that the GUI is created on the Event Dispatch Thread
            // Instantiate the Transaction here. Its constructor will set the 'instance' static field.
            new Transaction(); 
        });
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // Handle button clicks here
        if (e.getSource() == btnLoan) {
            // Create a new LoanWindow, passing the Transaction frame to it
            new LoanWindow(this); // Transaction instance
            MyFrame.setVisible(false); // Hide the Transaction frame
        } else if (e.getSource() == btnBill) {
            // Create a new BillWindow, passing the Transaction frame to it
           
            new BillWindow(this); // the Transaction instance
            MyFrame.setVisible(false); // Hide the Transaction frame
        }
    }
}