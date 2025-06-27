import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LoanWindow implements ActionListener, MouseListener, WindowListener {

    JFrame LoanFrame = new JFrame("Loan Window");

    JLabel lblLender, lblLoanName, lblLoanAmount, lblLoanStartDate, lblReceiver; // Added lblReceiver
    JTextField txtLender, txtLoanName, txtLoanAmount, txtLoanStartDate, txtReceiver; // Added txtReceiver
    JButton btnCreateLoan, btnUpdateLoan, btnDeleteLoan, btnReturnToTransaction;
    JTable tblLoan;
    DefaultTableModel loanTableModel;
    LoanBase Loanbase;

    JPanel panelMain;

    public LoanWindow() {
        System.out.println("LoanWindow: Constructor started.");
        LoanFrame.setSize(1000, 600); // Frame size remains the same
        LoanFrame.setLocationRelativeTo(null);
        LoanFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        LoanFrame.setLayout(null);

        panelMain = new JPanel();
        panelMain.setLayout(null);
        panelMain.setBorder(BorderFactory.createTitledBorder("Loan Information"));
        panelMain.setBounds(10, 10, 1000, 540); // Remains the same
        LoanFrame.add(panelMain);

        // Initialize new label and text field
        lblReceiver = new JLabel("Receiver:"); // NEW
        txtReceiver = new JTextField();        // NEW

        lblLender = new JLabel("Lender:");
        lblLoanName = new JLabel("Loan's Purpose:");
        lblLoanAmount = new JLabel("Loan Amount:");
        lblLoanStartDate = new JLabel("Start Date:");
        
        txtLender = new JTextField();
        txtLoanName = new JTextField();
        txtLoanAmount = new JTextField();
        txtLoanStartDate = new JTextField();

        // Labels and Text Fields (Left Side) - POSITIONS ADJUSTED
        panelMain.add(lblReceiver); // NEW
        lblReceiver.setBounds(20, 30, 100, 25);
        panelMain.add(txtReceiver); // NEW
        txtReceiver.setBounds(170, 30, 150, 25);

        panelMain.add(lblLender);
        lblLender.setBounds(20, 70, 100, 25); // Shifted down
        panelMain.add(txtLender);
        txtLender.setBounds(170, 70, 150, 25); // Shifted down

        panelMain.add(lblLoanName);
        lblLoanName.setBounds(20, 110, 120, 25); // Shifted down
        panelMain.add(txtLoanName);
        txtLoanName.setBounds(170, 110, 150, 25); // Shifted down

        panelMain.add(lblLoanAmount);
        lblLoanAmount.setBounds(20, 150, 120, 25); // Shifted down
        panelMain.add(txtLoanAmount);
        txtLoanAmount.setBounds(170, 150, 150, 25); // Shifted down

        panelMain.add(lblLoanStartDate);
        lblLoanStartDate.setBounds(20, 190, 140, 25); // Shifted down
        panelMain.add(txtLoanStartDate);
        txtLoanStartDate.setBounds(180, 190, 150, 25); // Shifted down

        // Buttons (Stacked on Left) - POSITIONS ADJUSTED
        btnCreateLoan = new JButton("Create Loan");
        btnUpdateLoan = new JButton("Update Loan");
        btnDeleteLoan = new JButton("Delete Loan");
        btnReturnToTransaction = new JButton("Return to Main");

        panelMain.add(btnCreateLoan);
        btnCreateLoan.setBounds(20, 240, 120, 30); // Shifted down

        panelMain.add(btnUpdateLoan);
        btnUpdateLoan.setBounds(20, 280, 120, 30); // Shifted down

        panelMain.add(btnDeleteLoan);
        btnDeleteLoan.setBounds(20, 320, 120, 30); // Shifted down
        
        panelMain.add(btnReturnToTransaction);
        btnReturnToTransaction.setBounds(20, 360, 150, 30); // Shifted down

        // Table Setup (Right Side) - UPDATED COLUMN NAMES
        Vector<String> colName = new Vector<>();
        colName.add("Receiver");        // NEW: Column 0
        colName.add("Lender");          // Column 1
        colName.add("Loan's Purpose");  // Column 2
        colName.add("Loan Amount");     // Column 3
        colName.add("Start Date");        // Column 4

        loanTableModel = new DefaultTableModel();
        loanTableModel.setColumnIdentifiers(colName);
        tblLoan = new JTable(loanTableModel);
        JScrollPane scrollPane = new JScrollPane(tblLoan);

        scrollPane.setBounds(360, 20, 600, 500); // Remains the same
        panelMain.add(scrollPane);

        // Initialize LoanBase and Load Data
        System.out.println("LoanWindow: Initializing LoanBase and loading data...");
        Loanbase = new LoanBase("Loan.txt");
        Loanbase.DisplayAll(loanTableModel);
        System.out.println("LoanWindow: Data loading complete. Table row count after load: " + loanTableModel.getRowCount());

        // Add Event Listeners
        btnCreateLoan.addActionListener(this);
        btnUpdateLoan.addActionListener(this);
        btnDeleteLoan.addActionListener(this);
        btnReturnToTransaction.addActionListener(this);
        
        tblLoan.addMouseListener(this);
        LoanFrame.addWindowListener(this);

        resetForm();

        LoanFrame.setVisible(true);
        System.out.println("LoanWindow: GUI frame made visible.");
    }

    public void resetForm() {
        System.out.println("LoanWindow: resetForm() called.");
        btnCreateLoan.setEnabled(true);
        btnUpdateLoan.setEnabled(false);
        btnDeleteLoan.setEnabled(false);
        
        txtReceiver.setText(""); // NEW: Clear Receiver text field
        txtLender.setText("");
        txtLoanName.setText("");
        txtLoanAmount.setText("");
        txtLoanStartDate.setText("");

        tblLoan.clearSelection();
    }

    public void alter() {
        System.out.println("LoanWindow: alter() called.");
        btnCreateLoan.setEnabled(false);
        btnUpdateLoan.setEnabled(true);
        btnDeleteLoan.setEnabled(true);
    }

    private void saveData() {
        System.out.println("LoanWindow: saveData() called. Preparing data for saving...");
        StringBuilder records = new StringBuilder();
        for (int i = 0; i < loanTableModel.getRowCount(); i++) {
            for (int j = 0; j < loanTableModel.getColumnCount(); j++) {
                Object values = loanTableModel.getValueAt(i, j);
                records.append(values != null ? values.toString() : "").append("\t");
            }
            records.append("\n");
        }

        String SaveAll = records.toString();
        System.out.println("LoanWindow: Data prepared for saving (" + SaveAll.length() + " chars).");

        try {
            Loanbase.saveAll(SaveAll);
            System.out.println("LoanWindow: Data saved successfully to file.");
        } catch (Exception ex) {
            System.err.println("LoanWindow: ERROR during saveData: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(LoanFrame, "Error saving data: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource().equals(btnCreateLoan)) {
            System.out.println("LoanWindow: 'Create Loan' button clicked.");
            // Validate all fields, including receiver
            if (txtReceiver.getText().isEmpty() || txtLender.getText().isEmpty() || txtLoanName.getText().isEmpty() || txtLoanAmount.getText().isEmpty() || txtLoanStartDate.getText().isEmpty()) {
                JOptionPane.showMessageDialog(LoanFrame, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("LoanWindow: Create attempt failed - missing fields.");
                return;
            }
            Vector<String> rows = new Vector<>();
            rows.add(txtReceiver.getText());    // NEW: Add Receiver (index 0)
            rows.add(txtLender.getText());      // Now index 1
            rows.add(txtLoanName.getText());    // Now index 2
            rows.add(txtLoanAmount.getText());  // Now index 3
            rows.add(txtLoanStartDate.getText()); // Now index 4
            loanTableModel.addRow(rows);
            System.out.println("LoanWindow: Added row to table: " + rows + ". Current table row count: " + loanTableModel.getRowCount());
            resetForm();
        } else if (e.getSource().equals(btnUpdateLoan)) {
            System.out.println("LoanWindow: 'Update Loan' button clicked.");
            int i = tblLoan.getSelectedRow();
            if (i >= 0) {
                // Validate all fields, including receiver
                if (txtReceiver.getText().isEmpty() || txtLender.getText().isEmpty() || txtLoanName.getText().isEmpty() || txtLoanAmount.getText().isEmpty() || txtLoanStartDate.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(LoanFrame, "All fields are required for update!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("LoanWindow: Update attempt failed - missing fields.");
                    return;
                }
                loanTableModel.setValueAt(txtReceiver.getText(), i, 0);       // NEW: Update Receiver (column 0)
                loanTableModel.setValueAt(txtLender.getText(), i, 1);       // Lender (column 1)
                loanTableModel.setValueAt(txtLoanName.getText(), i, 2);     // Loan's Purpose (column 2)
                loanTableModel.setValueAt(txtLoanAmount.getText(), i, 3);    // Loan Amount (column 3)
                loanTableModel.setValueAt(txtLoanStartDate.getText(), i, 4);   // Start Date (column 4)
                System.out.println("LoanWindow: Updated row " + i + ". Current table row count: " + loanTableModel.getRowCount());
                resetForm();
            } else {
                JOptionPane.showMessageDialog(LoanFrame, "Please select a loan to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
                System.out.println("LoanWindow: Update attempt failed - no row selected.");
            }
        } else if (e.getSource().equals(btnDeleteLoan)) {
            System.out.println("LoanWindow: 'Delete Loan' button clicked.");
            int i = tblLoan.getSelectedRow();
            if (i >= 0) {
                int confirms = JOptionPane.showConfirmDialog(LoanFrame, "Are you sure you want to delete this loan?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirms == JOptionPane.YES_OPTION) {
                    loanTableModel.removeRow(i);
                    System.out.println("LoanWindow: Deleted row " + i + ". Current table row count: " + loanTableModel.getRowCount());
                    resetForm();
                }
            } else {
                JOptionPane.showMessageDialog(LoanFrame, "Please select a loan to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                System.out.println("LoanWindow: Delete attempt failed - no row selected.");
            }
        } else if (e.getSource().equals(btnReturnToTransaction)) {
            System.out.println("LoanWindow: 'Return to Main' button clicked. Saving data and returning to Transaction window.");
            saveData();
            LoanFrame.dispose();
            new Transaction();
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getSource().equals(tblLoan)) {
            int i = tblLoan.getSelectedRow();
            if (i >= 0) {
                System.out.println("LoanWindow: Table row " + i + " clicked.");
                alter();
                txtReceiver.setText(loanTableModel.getValueAt(i, 0).toString());     // NEW: Populate Receiver text field
                txtLender.setText(loanTableModel.getValueAt(i, 1).toString());     // Lender (column 1)
                txtLoanName.setText(loanTableModel.getValueAt(i, 2).toString());
                txtLoanAmount.setText(loanTableModel.getValueAt(i, 3).toString());
                txtLoanStartDate.setText(loanTableModel.getValueAt(i, 4).toString());
                System.out.println("LoanWindow: Populated text fields from row " + i + ": Receiver '" + txtReceiver.getText() + "', Lender '" + txtLender.getText() + "', Loan Name '" + txtLoanName.getText() + "', Loan Amount '" + txtLoanAmount.getText() + "', Start Date '" + txtLoanStartDate.getText() + "'");
            }
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("WindowListener: windowClosing event triggered. Saving data and exiting.");
        saveData();
        System.out.println("WindowListener: Exiting application.");
        System.exit(0);
    }

    @Override public void windowOpened(WindowEvent e) { System.out.println("WindowListener: windowOpened."); }
    @Override public void windowClosed(WindowEvent e) { System.out.println("WindowListener: windowClosed."); }
    @Override public void windowIconified(WindowEvent e) { System.out.println("WindowListener: windowIconified."); }
    @Override public void windowDeiconified(WindowEvent e) { System.out.println("WindowListener: windowDeiconified."); }
    @Override public void windowActivated(WindowEvent e) { System.out.println("WindowListener: windowActivated."); }
    @Override public void windowDeactivated(WindowEvent e) { System.out.println("WindowListener: windowDeactivated."); }

    @Override public void mousePressed(java.awt.event.MouseEvent e) {}
    @Override public void mouseReleased(java.awt.event.MouseEvent e) {}
    @Override public void mouseEntered(java.awt.event.MouseEvent e) {}
    @Override public void mouseExited(java.awt.event.MouseEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoanWindow::new);
    }
}