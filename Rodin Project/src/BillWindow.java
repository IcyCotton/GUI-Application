import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent; // Import for WindowEvent
import java.awt.event.WindowListener; // Import for WindowListener
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BillWindow implements ActionListener, MouseListener, WindowListener { // Added WindowListener

    JFrame BillFrame = new JFrame("Bill Window");

    JLabel lblBillName, lblBillAmount, lblBillDueDate, lblBillType;
    JTextField txtBillName, txtBillAmount, txtBillDueDate;
    JButton btnCreateBill, btnUpdateBill, btnDeleteBill, btnReturnToTransaction; // Added btnReturnToTransaction
    JTable tblBill;
    JComboBox<String> cmbBillType;
    DefaultTableModel billTableModel;
    BillBase billbase;

    JPanel panelMain;

    public BillWindow() {
        System.out.println("BillWindow: Constructor started.");
        BillFrame.setSize(1000, 600);
        BillFrame.setLocationRelativeTo(null);
        BillFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Changed to DO_NOTHING_ON_CLOSE to handle closing manually
        BillFrame.setLayout(null);

        panelMain = new JPanel();
        panelMain.setLayout(null);
        panelMain.setBorder(BorderFactory.createTitledBorder("Bill Information"));
        panelMain.setBounds(10, 10,980, 540);
        BillFrame.add(panelMain);

        lblBillName = new JLabel("Account Name:");
        lblBillAmount = new JLabel("Bill Amount:");
        lblBillDueDate = new JLabel("Due Date");
        lblBillType = new JLabel("Bill Type:");

        txtBillName = new JTextField();
        txtBillAmount = new JTextField();
        txtBillDueDate = new JTextField();

        String[] billTypes = {"Electricity", "Water", "Internet", "Rent", "Credit Card", "Other"};
        cmbBillType = new JComboBox<>(billTypes); //instantiate JComboBox with bill types

        // Labels and Text Fields (Left Side)
        panelMain.add(lblBillName);
        lblBillName.setBounds(20, 30, 120, 25);
        panelMain.add(txtBillName);
        txtBillName.setBounds(170, 30, 150, 25);

        panelMain.add(lblBillAmount);
        lblBillAmount.setBounds(20, 70, 120, 25);
        panelMain.add(txtBillAmount);
        txtBillAmount.setBounds(170, 70, 150, 25);

        panelMain.add(lblBillDueDate);
        lblBillDueDate.setBounds(20, 110, 140, 25);
        panelMain.add(txtBillDueDate);
        txtBillDueDate.setBounds(180, 110, 150, 25);

        panelMain.add(lblBillType);
        lblBillType.setBounds(20, 150, 120, 25); // Placed below Due Date
        panelMain.add(cmbBillType);
        cmbBillType.setBounds(170, 150, 150, 25);

        // Buttons (Stacked on Left)
        btnCreateBill = new JButton("Create Bill");
        btnUpdateBill = new JButton("Update Bill");
        btnDeleteBill = new JButton("Delete Bill");
        btnReturnToTransaction = new JButton("Return to Main"); // Initialize the new button
        
        panelMain.add(btnCreateBill);
        btnCreateBill.setBounds(20, 200, 120, 30);

        panelMain.add(btnUpdateBill);
        btnUpdateBill.setBounds(20, 240, 120, 30);

        panelMain.add(btnDeleteBill);
        btnDeleteBill.setBounds(20, 280, 120, 30);
        
        panelMain.add(btnReturnToTransaction); // Add the new button to the panel
        btnReturnToTransaction.setBounds(20, 320, 150, 30); // Set its bounds

        // Table Setup (Right Side)
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Bill Type");
        columnNames.add("Account Name");
        columnNames.add("Bill Amount");
        columnNames.add("Due Date");

        billTableModel = new DefaultTableModel();
        billTableModel.setColumnIdentifiers(columnNames);
        tblBill = new JTable(billTableModel);
        JScrollPane scrollPane = new JScrollPane(tblBill);

        scrollPane.setBounds(360, 20, 600, 500);
        panelMain.add(scrollPane);

        // Initialize BillBase and Load Data
        System.out.println("BillWindow: Initializing BillBase and loading data...");
        billbase = new BillBase("bills.txt");
        billbase.DisplayAll(billTableModel);
        System.out.println("BillWindow: Data loading complete. Table row count after load: " + billTableModel.getRowCount());

        // Add Event Listeners
        btnCreateBill.addActionListener(this);
        btnUpdateBill.addActionListener(this);
        btnDeleteBill.addActionListener(this);
        btnReturnToTransaction.addActionListener(this); // Add listener for the new button
        
        tblBill.addMouseListener(this);
        BillFrame.addWindowListener(this); // Add the window listener to the frame

        resetForm();

        BillFrame.setVisible(true);
        System.out.println("BillWindow: GUI frame made visible.");
    }

    public void resetForm() {
        System.out.println("BillWindow: resetForm() called.");
        btnCreateBill.setEnabled(true);
        btnUpdateBill.setEnabled(false);
        btnDeleteBill.setEnabled(false);
        
        txtBillName.setText("");
        txtBillAmount.setText("");
        txtBillDueDate.setText("");
        cmbBillType.setSelectedIndex(0);

        tblBill.clearSelection();
    }

    public void alter() {
        System.out.println("BillWindow: alter() called.");
        btnCreateBill.setEnabled(false);
        btnUpdateBill.setEnabled(true);
        btnDeleteBill.setEnabled(true);
        
    }

    // New method to handle saving data
    private void saveData() {
        System.out.println("BillWindow: saveData() called. Preparing data for saving...");
        StringBuilder record = new StringBuilder();
        for (int i = 0; i < billTableModel.getRowCount(); i++) {
            for (int j = 0; j < billTableModel.getColumnCount(); j++) {
                Object value = billTableModel.getValueAt(i, j);
                record.append(value != null ? value.toString() : "").append("\t");
            }
            record.append("\n");
        }

        String dataToSave = record.toString();
        System.out.println("BillWindow: Data prepared for saving (" + dataToSave.length() + " chars).");

        try {
            billbase.saveAll(dataToSave);
            System.out.println("BillWindow: Data saved successfully to file.");
        } catch (Exception ex) {
            System.err.println("BillWindow: ERROR during saveData: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(BillFrame, "Error saving data: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource().equals(btnCreateBill)) {
            System.out.println("BillWindow: 'Create Bill' button clicked.");
            if (txtBillName.getText().isEmpty() || txtBillAmount.getText().isEmpty() || txtBillDueDate.getText().isEmpty()) {
                JOptionPane.showMessageDialog(BillFrame, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("BillWindow: Create attempt failed - missing fields.");
                return;
            }
            Vector<String> row = new Vector<>();
            row.add((String) cmbBillType.getSelectedItem());
            row.add(txtBillName.getText());
            row.add(txtBillAmount.getText());
            row.add(txtBillDueDate.getText());
            billTableModel.addRow(row);
            System.out.println("BillWindow: Added row to table: " + row + ". Current table row count: " + billTableModel.getRowCount());
            resetForm();
        } else if (e.getSource().equals(btnUpdateBill)) {
            System.out.println("BillWindow: 'Update Bill' button clicked.");
            int i = tblBill.getSelectedRow();
            if (i >= 0) {
                if (txtBillName.getText().isEmpty() || txtBillAmount.getText().isEmpty() || txtBillDueDate.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(BillFrame, "All fields are required for update!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("BillWindow: Update attempt failed - missing fields.");
                    return;
                }
                billTableModel.setValueAt(cmbBillType.getSelectedItem(), i, 0);
                billTableModel.setValueAt(txtBillName.getText(), i, 1);
                billTableModel.setValueAt(txtBillAmount.getText(), i, 2);
                billTableModel.setValueAt(txtBillDueDate.getText(), i, 3);
                System.out.println("BillWindow: Updated row " + i + ". Current table row count: " + billTableModel.getRowCount());
                resetForm();
            } else {
                JOptionPane.showMessageDialog(BillFrame, "Please select a bill to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
                System.out.println("BillWindow: Update attempt failed - no row selected.");
            }
        } else if (e.getSource().equals(btnDeleteBill)) {
            System.out.println("BillWindow: 'Delete Bill' button clicked.");
            int i = tblBill.getSelectedRow();
            if (i >= 0) {
                int confirm = JOptionPane.showConfirmDialog(BillFrame, "Are you sure you want to delete this bill?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    billTableModel.removeRow(i);
                    System.out.println("BillWindow: Deleted row " + i + ". Current table row count: " + billTableModel.getRowCount());
                    resetForm();
                }
            } else {
                JOptionPane.showMessageDialog(BillFrame, "Please select a bill to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                System.out.println("BillWindow: Delete attempt failed - no row selected.");
            }
        } else if (e.getSource().equals(btnReturnToTransaction)) { // Handle the new button's action
            System.out.println("BillWindow: 'Return to Main' button clicked. Saving data and returning to Transaction window.");
            saveData(); // Call the new saveData method
            BillFrame.dispose(); // Close the current BillWindow
            new Transaction(); // Open a new Transaction window
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getSource().equals(tblBill)) {
            int i = tblBill.getSelectedRow();
            if (i >= 0) {
                System.out.println("BillWindow: Table row " + i + " clicked.");
                alter();
                String selectedType = billTableModel.getValueAt(i, 0).toString(); // Get value from new Bill Type column (0)
                cmbBillType.setSelectedItem(selectedType);

                txtBillName.setText(billTableModel.getValueAt(i, 1).toString());
                txtBillAmount.setText(billTableModel.getValueAt(i, 2).toString());
                txtBillDueDate.setText(billTableModel.getValueAt(i, 3).toString());
                System.out.println("BillWindow: Populated text fields from row " + i + ": '" + txtBillName.getText() + "', '" + txtBillAmount.getText() + "', '" + txtBillDueDate.getText() + "'");
            }
        }
    }

    // --- WindowListener Methods ---
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("WindowListener: windowClosing event triggered. Saving data and exiting.");
        saveData(); // Call the new saveData method
        System.out.println("WindowListener: Exiting application.");
        System.exit(0); // Always exit when the frame's close button is used
    }

    // Other WindowListener methods (must be implemented even if unused)
    @Override public void windowOpened(WindowEvent e) { 
        System.out.println("WindowListener: windowOpened."); }

    @Override public void windowClosed(WindowEvent e) { 
        System.out.println("WindowListener: windowClosed."); } // This is called *after* the window has been disposed

    @Override public void windowIconified(WindowEvent e) { 
        System.out.println("WindowListener: windowIconified."); }

    @Override public void windowDeiconified(WindowEvent e) {
          System.out.println("WindowListener: windowDeiconified."); }

    @Override public void windowActivated(WindowEvent e) {
          System.out.println("WindowListener: windowActivated."); }
          
    @Override public void windowDeactivated(WindowEvent e) { 
        System.out.println("WindowListener: windowDeactivated."); }

    // Other MouseListener methods (must be implemented even if unused)
    @Override public void mousePressed(java.awt.event.MouseEvent e) {}
    @Override public void mouseReleased(java.awt.event.MouseEvent e) {}
    @Override public void mouseEntered(java.awt.event.MouseEvent e) {}
    @Override public void mouseExited(java.awt.event.MouseEvent e) {}

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BillWindow::new);
    }
}