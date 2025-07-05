import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter; 

public class BillWindow implements ActionListener, MouseListener, WindowListener {// Class to manage the Bill Window

    JFrame BillFrame = new JFrame("Bill Window");
    // Create the main frame for the BillWindow

    JLabel lblBillName, lblBillAmount, lblBillDueDate, lblBillType, lblSearch;
    // Labels for bill details and search
    JTextField txtBillName, txtBillAmount, txtBillDueDate, txtSearch;
    // Text fields for bill details
    

    JButton btnCreateBill, btnUpdateBill, btnDeleteBill, btnReturnToTransaction, btnSearch, btnClearSearch;
    // Buttons for bill operations
    JTable tblBill;
    // Table to display bills
    JComboBox<String> cmbBillType;
    // ComboBox for selecting bill type
    DefaultTableModel billTableModel;
    // Table model for the bill table
    BillBase billbase;
    // BillBase instance for managing bill data

    private TableRowSorter<DefaultTableModel> sorter;
    // TableRowSorter for filtering the table

    JPanel panelMain;// Main panel for the BillWindow

    // Reference to the main Transaction window
    private Transaction transactionWindow; 

    // Constructor now accepts the Transaction instance
    public BillWindow(Transaction transactionWindow) {
        this.transactionWindow = transactionWindow;
        // Store the reference to the main Transaction window
    
        BillFrame.setSize(1000, 630);
        // Set the size of the BillFrame
        BillFrame.setLocationRelativeTo(null);
        // Center the BillFrame on the screen
        BillFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Set to DO_NOTHING_ON_CLOSE to handle window closing manually
        BillFrame.setLayout(null);// Set the layout for the BillFrame

        panelMain = new JPanel();// Create the main panel for the BillWindow
        panelMain.setLayout(null);// Set the layout for the main panel
        panelMain.setBorder(BorderFactory.createTitledBorder("Bill Information"));
        // Create a titled border for the main panel
        panelMain.setBounds(10, 10, 980, 570);
        // Set the main panel's layout and bounds
        BillFrame.add(panelMain);
        // Add the main panel to the frame

        lblBillName = new JLabel("Name:");
        // Label for bill name input
        lblBillAmount = new JLabel("Bill Amount:");
        // Label for bill amount input
        lblBillDueDate = new JLabel("Due Date");
        // Label for due date input
        lblBillType = new JLabel("Bill Type:");
        // Label for bill type selection

        txtBillName = new JTextField();
        // Text field for bill name input
        txtBillAmount = new JTextField();
        // Text field for bill amount input
        txtBillDueDate = new JTextField();
        // Text field for due date input

        String[] billTypes = {"Electricity", "Water", "Internet", "Rent", "Credit Card", "Other"};
        // Define the bill types
        cmbBillType = new JComboBox<>(billTypes);
        // Initialize the combo box with bill types

        // Labels and Text Fields (Left Side)
        panelMain.add(lblBillName);
        // Add the label for bill name
        lblBillName.setBounds(20, 30, 120, 25);
        panelMain.add(txtBillName);
        // Add the bill name text field to the panel
        txtBillName.setBounds(170, 30, 150, 25);
        // Set bounds for the bill name label and text field

        panelMain.add(lblBillAmount);
        lblBillAmount.setBounds(20, 70, 120, 25);
        // Set bounds for the bill amount label
        panelMain.add(txtBillAmount);
        // Add the bill amount text field to the panel
        txtBillAmount.setBounds(170, 70, 150, 25);
        // Set bounds for the bill amount text field

        panelMain.add(lblBillDueDate);
        // Add the due date label to the panel
        lblBillDueDate.setBounds(20, 110, 140, 25);
        // Set bounds for the due date label
        panelMain.add(txtBillDueDate);
        // Add the due date text field to the panel
        txtBillDueDate.setBounds(180, 110, 140, 25);
        // Set bounds for the due date text field

        panelMain.add(lblBillType);
        // Add the label for bill type
        lblBillType.setBounds(20, 150, 120, 25);
        // Set bounds for the label
        panelMain.add(cmbBillType);
        // Add the combo box to the panel
        cmbBillType.setBounds(170, 150, 150, 25);
        // Set bounds for the combo box

        // Buttons (Stacked on Left)
        btnCreateBill = new JButton("Create Bill");
        // Button to create a new bill
        btnUpdateBill = new JButton("Update Bill");
        // Button to update an existing bill
        btnDeleteBill = new JButton("Delete Bill");
        // Button to delete a bill
        btnReturnToTransaction = new JButton("Return to Main");
        // Button to return to the main Transaction window
        
        panelMain.add(btnCreateBill);
        // Add the create button to the panel
        btnCreateBill.setBounds(20, 200, 120, 30);
        // Set bounds for the create button

        panelMain.add(btnUpdateBill);
        // Add the update button to the panel
        btnUpdateBill.setBounds(20, 240, 120, 30);
        // Set bounds for the update button

        panelMain.add(btnDeleteBill);
        // Add the delete button to the panel
        btnDeleteBill.setBounds(20, 280, 120, 30);
        // Set bounds for the delete button
        
        panelMain.add(btnReturnToTransaction);
        // Add the return button to the panel
        btnReturnToTransaction.setBounds(20, 320, 150, 30);
        // Set bounds for the return button

        // Table Setup (Right Side)
        Vector<String> columnNames = new Vector<>();
        // Initialize the column names for the table
        columnNames.add("Bill Type");
        // Initialize the column names for the table
        columnNames.add("Name");
        // Initialize the column names for the table
        columnNames.add("Bill Amount");
        // Initialize the column names for the table
        columnNames.add("Due Date");
        // Initialize the column names for the table

        billTableModel = new DefaultTableModel();
        // Initialize the table model
        billTableModel.setColumnIdentifiers(columnNames);
        // Initialize the table model with column names
        tblBill = new JTable(billTableModel);
        // Initialize the table with the model

        sorter = new TableRowSorter<>(billTableModel);
        // Initialize the TableRowSorter with the table model
        tblBill.setRowSorter(sorter);// Set the row sorter for the table

        JScrollPane scrollPane = new JScrollPane(tblBill);
        // Initialize the scroll pane with the table
        scrollPane.setBounds(360, 20, 600, 480);
        // Set bounds for the scroll pane containing the table
        panelMain.add(scrollPane);// Add the scroll pane containing the table

        
        lblSearch = new JLabel("Search:");// Initialize the search label
        lblSearch.setBounds(360, 505, 60, 25);
        // Initialize setbounds search label
        panelMain.add(lblSearch);// Add the search label

        txtSearch = new JTextField();
        // Initialize the search text field
        txtSearch.setBounds(420, 505, 200, 25);
        // Set bounds for the search text field
        panelMain.add(txtSearch);// Add the search text field

        btnSearch = new JButton("Search");
        // Initialize the search button
        btnSearch.setBounds(630, 505, 100, 25);
        // Set bounds for the search button
        panelMain.add(btnSearch);
        // Initialize the search button

        btnClearSearch = new JButton("Clear Search");
        // Initialize the clear search button
        btnClearSearch.setBounds(740, 505, 120, 25);
        // Set bounds for the clear search button
        panelMain.add(btnClearSearch);// Add the clear search button

        // Initialize BillBase and Load Data
        billbase = new BillBase("bills.txt");
        // Initialize BillBase with the file name
        billbase.loadAllToModel(billTableModel); 
       // Load all existing bills into the table model

        // Add Event Listeners
        btnCreateBill.addActionListener(this);
        // Add action listeners for buttons
        btnUpdateBill.addActionListener(this);
        // Add action listeners for buttons
        btnDeleteBill.addActionListener(this);
        // Add action listener for return button
        btnReturnToTransaction.addActionListener(this);
        // Add action listener for return button
        
        tblBill.addMouseListener(this);
        // Add mouse listener to the table for row selection
        BillFrame.addWindowListener(this); 
        // Register BillWindow as the listener for its own frame

        btnSearch.addActionListener(this);
        // Add action listener for search button
        btnClearSearch.addActionListener(this);
        // Add action listeners for search buttons

        resetForm();// Reset the form to initial state

        BillFrame.setVisible(true);
        // Set the BillFrame(GUI) to be visible
    }

   

    public void resetForm() {
        
        btnCreateBill.setEnabled(true);
        // Enable Create button when resetting the form
        btnUpdateBill.setEnabled(false);
        // Disable Update button when resetting the form
        btnDeleteBill.setEnabled(false);
        // Disable Update and Delete buttons when resetting the form
        
        txtBillName.setText("");// Clear all text fields
        txtBillAmount.setText("");// Clear all text fields
        txtBillDueDate.setText("");// Clear all text fields
        cmbBillType.setSelectedIndex(0);
        // Reset to the first item in the combo box

        tblBill.clearSelection();// Clear any selected row in the table
    }

    public void alter() {
        btnCreateBill.setEnabled(false);
        // Disable Create button when editing
        btnUpdateBill.setEnabled(true);
        // Enable Update button when editing
        btnDeleteBill.setEnabled(true);
        // Enable Delete button when editing
        
    }

    private void saveData() {

        StringBuilder record = new StringBuilder();
        // Use StringBuilder for efficient string concatenation

        for (int i = 0; i < billTableModel.getRowCount(); i++) {
            // Iterate through each row in the table model
            for (int j = 0; j < billTableModel.getColumnCount(); j++) {

                Object value = billTableModel.getValueAt(i, j);
                // Get the value at the current row and column
                record.append(value != null ? value.toString() : "").append("\t");
                // Append a tab character after each value
            }
            // Remove the last tab character and add a newline
            record.append("\n");
        }

        String dataToSave = record.toString();
        // Log the data to be saved

        try {
            billbase.saveAll(dataToSave);// Save the data to the file
           
        } catch (Exception ex) {
          
            ex.printStackTrace();
            // Show error message to the user
            JOptionPane.showMessageDialog(BillFrame, "Error saving data: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applySearchFilter(String filterText) {
        if (filterText.trim().length() == 0) {
            // No filter applied, show all rows
            sorter.setRowFilter(null);
        } else {
            // Apply a case-insensitive regex filter
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filterText));
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource().equals(btnCreateBill)) {
           // Create a new bill
            if (txtBillName.getText().isEmpty() || txtBillAmount.getText().isEmpty() || txtBillDueDate.getText().isEmpty()) {
                JOptionPane.showMessageDialog(BillFrame, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
                // Log the error
                return;
            }

            double billAmount;// Parse the bill amount from the text field
            try {
                billAmount = Double.parseDouble(txtBillAmount.getText());
                if (billAmount < 0) {
                    // Log the error
                    JOptionPane.showMessageDialog(BillFrame, "Bill Amount cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(BillFrame, "Bill Amount must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
               // Log the error
                return;
            }

            Vector<String> row = new Vector<>();// Create a new row for the table
            row.add((String) cmbBillType.getSelectedItem());// Add the selected bill type to the row
            row.add(txtBillName.getText());// Add the bill name to the row
            row.add(String.format("%.2f", billAmount));// Format the amount to 2 decimal places
            row.add(txtBillDueDate.getText());// Add the due date to the row
            billTableModel.addRow(row);// Add the new row to the table model
           
            
            saveData();// Save the new bill data
            applySearchFilter(txtSearch.getText());// Apply search filter after creating a bill
            resetForm();// Reset the form after creating a bill
        } else if (e.getSource().equals(btnUpdateBill)) {
           // Update the selected bill
            
            int selectedRowInView = tblBill.getSelectedRow();
            
            if (selectedRowInView >= 0) {
                int modelRowIndex = tblBill.convertRowIndexToModel(selectedRowInView);
                // Check if all fields are filled
                if (txtBillName.getText().isEmpty() || txtBillAmount.getText().isEmpty() || txtBillDueDate.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(BillFrame, "All fields are required for update!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    // Log the error
                    return;
                }
                
                double billAmount;
                try {
                    billAmount = Double.parseDouble(txtBillAmount.getText());
                    if (billAmount < 0) {
                        // Log the error
                        JOptionPane.showMessageDialog(BillFrame, "Bill Amount cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BillFrame, "Bill Amount must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    // Log the error
                    return;
                }
                // Update the selected row in the table model
                billTableModel.setValueAt(cmbBillType.getSelectedItem(), modelRowIndex, 0);
                billTableModel.setValueAt(txtBillName.getText(), modelRowIndex, 1);
                billTableModel.setValueAt(String.format("%.2f", billAmount), modelRowIndex, 2);
                billTableModel.setValueAt(txtBillDueDate.getText(), modelRowIndex, 3);
               
                
                saveData();// Save the updated data
                applySearchFilter(txtSearch.getText());// Apply search filter after update
                resetForm();// Reset the form after update
            } else {
                JOptionPane.showMessageDialog(BillFrame, "Please select a bill to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
                // No row selected for update
            }
        } else if (e.getSource().equals(btnDeleteBill)) {
        
            int selectedRowInView = tblBill.getSelectedRow();
            if (selectedRowInView >= 0) {
                // Convert selected row index from view to model
                int modelRowIndex = tblBill.convertRowIndexToModel(selectedRowInView);
                // Confirm deletion?
                int confirm = JOptionPane.showConfirmDialog(BillFrame, "Are you sure you want to delete this bill?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    billTableModel.removeRow(modelRowIndex);
                    // Log the deletion
                    
                    saveData();
                    applySearchFilter(txtSearch.getText());
                    resetForm();
                }
            } else {
                // No row selected for deletion
                JOptionPane.showMessageDialog(BillFrame, "Please select a bill to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
               
            }
        } else if (e.getSource().equals(btnReturnToTransaction)) {
           //returns to main Transaction window and saves data
            saveData();
            BillFrame.dispose(); // Dispose the BillWindow
            if (transactionWindow != null) {
                transactionWindow.getMyFrame().setVisible(true); // Make the main Transaction window visible
            } else {
                System.err.println("BillWindow: Cannot return to Transaction window, reference is null.");
       
            }
        } else if (e.getSource().equals(btnSearch)) {
           //search feature
            applySearchFilter(txtSearch.getText());
        } else if (e.getSource().equals(btnClearSearch)) {
           //clear search feature
            txtSearch.setText("");
            applySearchFilter("");
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getSource().equals(tblBill)) {
            int selectedRowInView = tblBill.getSelectedRow();
            if (selectedRowInView >= 0) {
                System.out.println("BillWindow: Table row " + selectedRowInView + " clicked.");
                alter();
                String selectedType = tblBill.getValueAt(selectedRowInView, 0).toString(); 
                cmbBillType.setSelectedItem(selectedType);
                txtBillName.setText(tblBill.getValueAt(selectedRowInView, 1).toString());
                txtBillAmount.setText(tblBill.getValueAt(selectedRowInView, 2).toString());
                txtBillDueDate.setText(tblBill.getValueAt(selectedRowInView, 3).toString());
               
            }
        }
    }

    // WindowListener Methods 
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("WindowListener: windowClosing event triggered. Saving data and disposing BillWindow.");
        saveData(); 
        BillFrame.dispose(); // Dispose the BillWindow
        if (transactionWindow != null) {
            transactionWindow.getMyFrame().setVisible(true); // Make Transaction visible
        } else {
            System.exit(0); // Exit if no Transaction window to return to
        }
    }

    @Override public void windowOpened(WindowEvent e) { }
    @Override public void windowClosed(WindowEvent e) { } 
    @Override public void windowIconified(WindowEvent e) {  }
    @Override public void windowDeiconified(WindowEvent e) {  }
    @Override public void windowActivated(WindowEvent e) {  }
    @Override public void windowDeactivated(WindowEvent e) {  }

    // Other MouseListener methods (must be implemented even if unused)
    @Override public void mousePressed(java.awt.event.MouseEvent e) {}
    @Override public void mouseReleased(java.awt.event.MouseEvent e) {}
    @Override public void mouseEntered(java.awt.event.MouseEvent e) {}
    @Override public void mouseExited(java.awt.event.MouseEvent e) {}

    public static void main(String[] args) {
       
    }
}