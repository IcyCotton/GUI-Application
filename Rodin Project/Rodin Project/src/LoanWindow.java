import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter; 

public class LoanWindow implements ActionListener, MouseListener, WindowListener {

    JFrame LoanFrame = new JFrame("Loan Window");

    JLabel lblLender, lblLoanName, lblLoanAmount, lblLoanStartDate, lblReceiver;
    JTextField txtLender, txtLoanName, txtLoanAmount, txtLoanStartDate, txtReceiver;

    JLabel lblInterestAmount;
    JTextField txtInterestAmount; 

    JLabel lblDueDate;
    JTextField txtDueDate;

    JLabel lblSearch;
    JTextField txtSearch;
    JButton btnSearch, btnClearSearch;

    JButton btnCreateLoan, btnUpdateLoan, btnDeleteLoan, btnReturnToTransaction;
    JTable tblLoan;
    DefaultTableModel loanTableModel;
    LoanBase Loanbase; 

    JPanel panelMain;

    private TableRowSorter<DefaultTableModel> sorter;

    private Transaction transactionWindow; 
    

    public LoanWindow(Transaction transactionWindow) {
        this.transactionWindow = transactionWindow;
        System.out.println("LoanWindow: Constructor started.");
        // Adjusted frame size slightly
        LoanFrame.setSize(1250, 600); 
        LoanFrame.setLocationRelativeTo(null);
        LoanFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        LoanFrame.setLayout(null);

        
        panelMain = new JPanel();
        panelMain.setLayout(null);
        panelMain.setBorder(BorderFactory.createTitledBorder("Loan Information"));
        panelMain.setBounds(10, 10, 1230, 540);
        LoanFrame.add(panelMain);

        lblReceiver = new JLabel("Receiver:");
        txtReceiver = new JTextField();

        lblLender = new JLabel("Lender:");
        lblLoanName = new JLabel("Loan's Purpose:");
        lblLoanAmount = new JLabel("Loan Amount:");
        lblLoanStartDate = new JLabel("Start Date:");
        
        txtLender = new JTextField();
        txtLoanName = new JTextField();
        txtLoanAmount = new JTextField();
        txtLoanStartDate = new JTextField();

        lblInterestAmount = new JLabel("Interest Rate (%):"); 
        txtInterestAmount = new JTextField();
        
        lblDueDate = new JLabel("Due date:");
        txtDueDate = new JTextField();

        int yPos = 30;

        panelMain.add(lblReceiver);
        lblReceiver.setBounds(20, yPos, 100, 25);
        panelMain.add(txtReceiver);
        txtReceiver.setBounds(170, yPos, 150, 25);
        yPos += 40; 

        panelMain.add(lblLender);
        lblLender.setBounds(20, yPos, 100, 25);
        panelMain.add(txtLender);
        txtLender.setBounds(170, yPos, 150, 25);
        yPos += 40;

        panelMain.add(lblLoanName);
        lblLoanName.setBounds(20, yPos, 120, 25);
        panelMain.add(txtLoanName);
        txtLoanName.setBounds(170, yPos, 150, 25);
        yPos += 40;

        panelMain.add(lblLoanAmount);
        lblLoanAmount.setBounds(20, yPos, 120, 25);
        panelMain.add(txtLoanAmount);
        txtLoanAmount.setBounds(170, yPos, 150, 25);
        yPos += 40;

        panelMain.add(lblInterestAmount);
        lblInterestAmount.setBounds(20, yPos, 120, 25);
        panelMain.add(txtInterestAmount);
        txtInterestAmount.setBounds(170, yPos, 150, 25);
        yPos += 40; 

        panelMain.add(lblLoanStartDate);
        lblLoanStartDate.setBounds(20, yPos, 140, 25);
        panelMain.add(txtLoanStartDate);
        txtLoanStartDate.setBounds(170, yPos, 150, 25);
        yPos += 40; 

        panelMain.add(lblDueDate);
        lblDueDate.setBounds(20, yPos, 140, 25);
        panelMain.add(txtDueDate);
        txtDueDate.setBounds(170, yPos, 150, 25);
        yPos += 50;

        btnCreateLoan = new JButton("Create Loan");
        btnUpdateLoan = new JButton("Update Loan");
        btnDeleteLoan = new JButton("Delete Loan");
        btnReturnToTransaction = new JButton("Return to Main");

        panelMain.add(btnCreateLoan);
        btnCreateLoan.setBounds(20, yPos, 120, 30);
        yPos += 40;

        panelMain.add(btnUpdateLoan);
        btnUpdateLoan.setBounds(20, yPos, 120, 30);
        yPos += 40;

        panelMain.add(btnDeleteLoan);
        btnDeleteLoan.setBounds(20, yPos, 120, 30);
        yPos += 40;
        
        panelMain.add(btnReturnToTransaction);
        btnReturnToTransaction.setBounds(20, yPos, 150, 30);

        Vector<String> colName = new Vector<>();
        colName.add("Receiver");
        colName.add("Lender");
        colName.add("Loan Purpose");
        colName.add("Principal Amount");
        colName.add("Interest Rate");       
        colName.add("Calculated");
        colName.add("Total Loan");
        colName.add("Start Date");
        colName.add("Due Date");

        loanTableModel = new DefaultTableModel();
        //will manage the data for the JTable.
        loanTableModel.setColumnIdentifiers(colName);
        tblLoan = new JTable(loanTableModel);
        //Creates a new JTable instance

       

        sorter = new TableRowSorter<>(loanTableModel);
        //enabling sorting and filtering of the JTable.
        tblLoan.setRowSorter(sorter);
        //to control the table's display.
        JScrollPane scrollPane = new JScrollPane(tblLoan);
        //adding scrollbars if the table content exceeds visible area.
        
        scrollPane.setBounds(360, 20, 850, 470); 
        panelMain.add(scrollPane);

        lblSearch = new JLabel("Search:");
        lblSearch.setBounds(360, 495, 60, 25);
        panelMain.add(lblSearch);

        txtSearch = new JTextField();
        // Initializes the text field used for entering search queries.
        txtSearch.setBounds(420, 495, 200, 25);
        panelMain.add(txtSearch);
        
        btnSearch = new JButton("Search");
        btnSearch.setBounds(630, 495, 100, 25);
        panelMain.add(btnSearch);

        btnClearSearch = new JButton("Clear Search");
        btnClearSearch.setBounds(740, 495, 120, 25);
        panelMain.add(btnClearSearch);

        Loanbase = new LoanBase("Loan.txt");
        Loanbase.loadAllToModel(loanTableModel); 
       

        btnCreateLoan.addActionListener(this);
        btnUpdateLoan.addActionListener(this);
        btnDeleteLoan.addActionListener(this);
        btnReturnToTransaction.addActionListener(this);
        
        tblLoan.addMouseListener(this);
        LoanFrame.addWindowListener(this); 

        btnSearch.addActionListener(this);
        btnClearSearch.addActionListener(this);

        resetForm();

        LoanFrame.setVisible(true);
        
    }

  

    public LoanWindow() {
        this(null);

    }

    public void resetForm() {
       
        btnCreateLoan.setEnabled(true);
        btnUpdateLoan.setEnabled(false);
        btnDeleteLoan.setEnabled(false);
        
        txtReceiver.setText("");
        txtLender.setText("");
        txtLoanName.setText("");
        txtLoanAmount.setText("");
        txtInterestAmount.setText(""); 
        txtLoanStartDate.setText("");
        txtDueDate.setText("");

        tblLoan.clearSelection();
    }

    public void alter() {
        System.out.println("LoanWindow: alter() called.");
        btnCreateLoan.setEnabled(false);
        btnUpdateLoan.setEnabled(true);
        btnDeleteLoan.setEnabled(true);
    }

    private void saveData() {
       // Initializes a StringBuilder to efficiently build the string of all table records.
        StringBuilder records = new StringBuilder();
        for (int i = 0; i < loanTableModel.getRowCount(); i++) {
            for (int j = 0; j < loanTableModel.getColumnCount(); j++) { 
                Object values = loanTableModel.getValueAt(i, j);
                records.append(values != null ? values.toString() : "").append("\t");
                // Appends the value to the StringBuilder. If the value is null, an empty string is appended.
            // A tab character ("\t") is appended after each value to act as a delimiter.
            }
            records.append("\n");
        }

        String SaveAll = records.toString();
        //This String 'SaveAll' is now ready to be written to a file.

        try {
            Loanbase.saveAll(SaveAll);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Prints the full stack trace of the exception to the console for debugging purposes.
            JOptionPane.showMessageDialog(LoanFrame, "Error saving data: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applySearchFilter(String filterText) {
        if (filterText.trim().length() == 0) {
            //Checks if the search text is empty or just whitespace.
            sorter.setRowFilter(null);
            //If empty, it clears any active filter, showing all rows.
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filterText)); 
            //Applies a case-insensitive regex filter using the input text across all columns.
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource().equals(btnCreateLoan)) {
           
            if (txtReceiver.getText().isEmpty() || txtLender.getText().isEmpty() ||
                txtLoanName.getText().isEmpty() || txtLoanAmount.getText().isEmpty() ||
                txtInterestAmount.getText().isEmpty() || 
                txtLoanStartDate.getText().isEmpty() || txtDueDate.getText().isEmpty()) {
                JOptionPane.showMessageDialog(LoanFrame, "All fields (Receiver, Lender, Purpose, Amount, Interest Rate, Start Date, Due Date) are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
                
                return;
            }

            double principalAmount;
            double interestRatePercentage; 
            double calculatedInterestAmount; 
            double totalLoanAmount;

            try {
                principalAmount = Double.parseDouble(txtLoanAmount.getText());
                if (principalAmount < 0) {
                    JOptionPane.showMessageDialog(LoanFrame, "Loan Amount cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String interestText = txtInterestAmount.getText().trim();// Get interest rate text, remove spaces.
                if (interestText.endsWith("%")) {
                    interestText = interestText.substring(0, interestText.length() - 1);
                    //delete percentage sign
                }
                interestRatePercentage = Double.parseDouble(interestText);
                // Convert the cleaned text to a number.
                if (interestRatePercentage < 0) {
                    JOptionPane.showMessageDialog(LoanFrame, "Interest Rate cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                calculatedInterestAmount = principalAmount * (interestRatePercentage / 100.0);
                totalLoanAmount = principalAmount + calculatedInterestAmount; 
            } catch (NumberFormatException ex) {
                //dialog error
                JOptionPane.showMessageDialog(LoanFrame, "Loan Amount and Interest Rate must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                
                return;
            }

            Vector<String> rows = new Vector<>();
            // Creates a new temporary list to hold data for one row.
            rows.add(txtReceiver.getText());
            rows.add(txtLender.getText());
            rows.add(txtLoanName.getText());
            rows.add(String.format("%.2f", principalAmount));           
            // Adds the principal loan amount to the row data.
            rows.add(String.format("%.2f%%", interestRatePercentage));      
             // Adds the interest rate to the row data.
            rows.add(String.format("%.2f", calculatedInterestAmount));      
             // Adds the calculated interest amount to the row data.
            rows.add(String.format("%.2f", totalLoanAmount));             
            // Adds the total loan amount to the row data.
            rows.add(txtLoanStartDate.getText()); 
            // Adds the loan's start date to the list of row data.
            rows.add(txtDueDate.getText());
            // Adds the loan's due date to the list of row data.
            
            loanTableModel.addRow(rows);//add the vector row to the table.
            
            saveData();
            applySearchFilter(txtSearch.getText());//
            resetForm();//bring back beginning
        } else if (e.getSource().equals(btnUpdateLoan)) {

            int selectedRowInView = tblLoan.getSelectedRow();
            // Gets the index of the row currently selected by the user in the table.
            if (selectedRowInView >= 0) {
                int modelRowIndex = tblLoan.convertRowIndexToModel(selectedRowInView);
                // Gets the true row index in the data model.

                if (txtReceiver.getText().isEmpty() || txtLender.getText().isEmpty() ||
                    txtLoanName.getText().isEmpty() || txtLoanAmount.getText().isEmpty() ||
                    txtInterestAmount.getText().isEmpty() || 
                    txtLoanStartDate.getText().isEmpty() || txtDueDate.getText().isEmpty())
                     // If any field is empty, show an error message to the user.
                      {
                    JOptionPane.showMessageDialog(LoanFrame, "All fields (Receiver, Lender, Purpose, Amount, Interest Rate, Start Date, Due Date) are required for update!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                double principalAmount;
                // Declares a variable to hold the main loan amount.
                double interestRatePercentage; 
                // Declares a variable for the interest rate.
                double calculatedInterestAmount;
                // Declares a variable for the calculated interest.
                double totalLoanAmount;// Declares a variable for the total loan amount 

                try {
                    principalAmount = Double.parseDouble(txtLoanAmount.getText());
                    //convert to double
                    if (principalAmount < 0) {
                        //check if negative
                        JOptionPane.showMessageDialog(LoanFrame, "Loan Amount cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    String interestText = txtInterestAmount.getText().trim();
                    // Gets the text from the interest rate field and removes extra spaces.
                    if (interestText.endsWith("%")) {// Removes the percentage sign if it's there.
                        interestText = interestText.substring(0, interestText.length() - 1);
                    }
                    interestRatePercentage = Double.parseDouble(interestText);
                    // Converts the (cleaned) interest text into a number.

                    if (interestRatePercentage < 0) {
                        //dialog error
                        JOptionPane.showMessageDialog(LoanFrame, "Interest Rate cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    calculatedInterestAmount = principalAmount * (interestRatePercentage / 100.0);// Calculates the interest amount based on principal and rate.
                    totalLoanAmount = principalAmount + calculatedInterestAmount; // Calculates the total loan amount.
                } catch (NumberFormatException ex) {// Catches an error if text cannot be turned into a number.
                    JOptionPane.showMessageDialog(LoanFrame, "Loan Amount and Interest Rate must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                loanTableModel.setValueAt(txtReceiver.getText(), modelRowIndex, 0);
                // Updates the 'Receiver' column in the table.
                loanTableModel.setValueAt(txtLender.getText(), modelRowIndex, 1);
                // Updates the 'Lender' column in the table.
                loanTableModel.setValueAt(txtLoanName.getText(), modelRowIndex, 2);
                // Updates the 'Loan Purpose' column in the table.
                loanTableModel.setValueAt(String.format("%.2f", principalAmount), modelRowIndex, 3);
                loanTableModel.setValueAt(String.format("%.2f%%", interestRatePercentage), modelRowIndex, 4); 
                // Updates the 'Interest Rate' column.
                loanTableModel.setValueAt(String.format("%.2f", calculatedInterestAmount), modelRowIndex, 5); 
                // Updates the 'Calculated Interest' (hidden) column.
                loanTableModel.setValueAt(String.format("%.2f", totalLoanAmount), modelRowIndex, 6);         
                // Updates the 'Total Loan' column.
                loanTableModel.setValueAt(txtLoanStartDate.getText(), modelRowIndex, 7);
                loanTableModel.setValueAt(txtDueDate.getText(), modelRowIndex, 8);
                
                saveData(); //saves data
                applySearchFilter(txtSearch.getText()); 
                // Updates the table's display based on the current text in the search box.
                resetForm();//reset to beginning
            } else {
                JOptionPane.showMessageDialog(LoanFrame, "Please select a loan to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
                //Dialogbox
            }
        } else if (e.getSource().equals(btnDeleteLoan)) {
            // if it clicks
            int selectedRowInView = tblLoan.getSelectedRow();
            // Gets the index of the currently selected row in the visible table.
            if (selectedRowInView >= 0) {
                // Checks if a row is actually selected.
                int modelRowIndex = tblLoan.convertRowIndexToModel(selectedRowInView);
                // Converts the view row index to the actual model row index
                // (important with sorting/filtering).
                int confirms = JOptionPane.showConfirmDialog(LoanFrame, "Are you sure you want to delete this loan?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                //Dialog box
                if (confirms == JOptionPane.YES_OPTION) {
                    //if selected yes, remove cell
                    loanTableModel.removeRow(modelRowIndex);
                    // Removes the selected row from the table's data model.
                    
                    saveData();// Saves the updated (deleted) data to the file.
                    applySearchFilter(txtSearch.getText());
                    // Re-applies any search filter to update the table display.
                    resetForm();// Clears input fields and resets button states.
                }
            } else {
                JOptionPane.showMessageDialog(LoanFrame, "Please select a loan to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                //gives a window error
            }
        } else if (e.getSource().equals(btnSearch)) {
            //if it clicks
            applySearchFilter(txtSearch.getText());
            // Filters the table based on the text entered in the search box.
        } else if (e.getSource().equals(btnClearSearch)) {
            // If the "Clear Search" button was clicked.
            txtSearch.setText("");// Clears the text in the search box.
            applySearchFilter("");// Removes any active search filter, showing all table rows.
        } else if (e.getSource().equals(btnReturnToTransaction)) { 
            // If the "Return to Main" button was clicked.
            
            saveData(); // Save data before returning
            LoanFrame.dispose(); 
            if (transactionWindow != null) {
                // Checks if there's a valid link back to the main (Transaction) window.
                transactionWindow.getMyFrame().setVisible(true);
                // If yes, shows the main window again.
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getSource().equals(tblLoan)) {
            // Checks if the JTable (tblLoan) was clicked.
            int selectedRowInView = tblLoan.getSelectedRow();
            // Gets the index of the row the user clicked in the table.
            if (selectedRowInView >= 0) {
                alter();// Changes button states
                txtReceiver.setText(tblLoan.getValueAt(selectedRowInView, 0).toString());
                // Fills the Receiver field from the selected table row.
                txtLender.setText(tblLoan.getValueAt(selectedRowInView, 1).toString());
                // Fills the Lender field from the selected table row.
                txtLoanName.setText(tblLoan.getValueAt(selectedRowInView, 2).toString());
                // Fills the Loan Purpose field from the selected table row.
                String principalStr = tblLoan.getValueAt(selectedRowInView, 3).toString();
                // Get interest rate from its column (index 4), which is now visible
                String interestStr = tblLoan.getValueAt(selectedRowInView, 4).toString(); 

                
                if (interestStr.endsWith("%")) {
                    interestStr = interestStr.substring(0, interestStr.length() - 1).trim();
                }
                // Removes a trailing '%' sign from the interest rate string if present,
                // before converting it to a number.
                
                txtLoanAmount.setText(principalStr); 
                // Sets the text field for loan amount with the retrieved principal string.
                txtInterestAmount.setText(interestStr); 
                // Sets the text field for interest rate with the retrieved interest string.
                txtLoanStartDate.setText(tblLoan.getValueAt(selectedRowInView, 7).toString()); 
                // Fills the Start Date field from the table.
                txtDueDate.setText(tblLoan.getValueAt(selectedRowInView, 8).toString());
                // Fills the Due Date field from the table.
            }
        }
    }

    
    @Override
    public void windowClosing(WindowEvent e) {
        saveData(); // save data before closing
        LoanFrame.dispose(); //close the loanwindow
        if (transactionWindow != null) {
            transactionWindow.getMyFrame().setVisible(true);
            //returning to transaction window
        } else {
            System.exit(0); 
            //The system exits the application
        }
    }

    @Override public void windowOpened(WindowEvent e) { }
    @Override public void windowClosed(WindowEvent e) { }
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) { }
    @Override public void windowActivated(WindowEvent e) { }
    @Override public void windowDeactivated(WindowEvent e) { }

    @Override public void mousePressed(java.awt.event.MouseEvent e) {}
    @Override public void mouseReleased(java.awt.event.MouseEvent e) {}
    @Override public void mouseEntered(java.awt.event.MouseEvent e) {}
    @Override public void mouseExited(java.awt.event.MouseEvent e) {}
    
   
}