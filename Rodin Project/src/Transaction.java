import java.awt.FlowLayout;        // For FlowLayout
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;



public class Transaction implements ActionListener {

    JFrame MyFrame;
    JLabel lblTitle, lblCategory;
    JButton btnLoan, btnBill;

    public Transaction() {
        // Instantiate the JFrame and JLabel
        MyFrame = new JFrame();
        lblTitle = new JLabel("Loan & Bill System");
        lblCategory = new JLabel("Pick Category");
        btnLoan = new JButton("Loan");
        btnBill = new JButton("Bill");
        MyFrame.setTitle(lblTitle.getText());

        // --- Corrected Code for background image ---

        // Create an instance path for ImagePanel with the correct path to the image.
        
        ImagePanel backgroundPanel = new ImagePanel("/Images/transactbsg.png");
        
        // Set the layout manager for the background panel.
        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        
        
        lblCategory.setForeground(java.awt.Color.WHITE);// for visibility

        

       


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
        MyFrame.setVisible(true);

        // ActionListener for buttons
        btnLoan.addActionListener(this);
        btnBill.addActionListener(this);
    }

    public static void main(String[] args) {
        // IMPORTANT: Always create Swing UI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(Transaction::new);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // Handle button clicks here
        if (e.getSource() == btnLoan) {
            // Assuming LoanWindow and BillWindow classes exist
            new LoanWindow();
            MyFrame.dispose();
        } else if (e.getSource() == btnBill) {
            new BillWindow();
            MyFrame.dispose();
        }
    }
}