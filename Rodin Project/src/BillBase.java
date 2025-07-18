import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BillBase {// Class to manage bill records
    File fileName;// File to store bill records

    public BillBase(String name) {
        // Constructor to initialize the BillBase with a file name
        fileName = new File(name);
        // Initialize the file with the given name
    }

    public void saveAll(String record) {
        // Method to save all bill records to the file
        try (FileWriter fw = new FileWriter(fileName)) {
            // Create a FileWriter to write to the file
            fw.write(record);// Write the records to the file
            fw.flush();// Ensure all data is written to the file
            
        

           
        } catch (IOException e) {// Handle any IO exceptions that may occur
           
            e.printStackTrace();// Print the stack trace for debugging
            // Show an error message dialog if saving fails
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to load all bill records into a DefaultTableModel
    public void loadAllToModel(DefaultTableModel model) {
        // This method loads all bill records from the file into the provided DefaultTableModel
        Scanner sc = null;// Scanner to read the file
        try {// Initialize the Scanner to read from the file
            model.setRowCount(0); // Clear existing rows

            if (!fileName.exists()) {// Check if the file exists
              
                fileName.createNewFile();
                // If the file does not exist, create a new one
                return;// Exit the method if the file does not exist
            }

            sc = new Scanner(new FileReader(fileName));
            // Create a Scanner to read the file
            while (sc.hasNextLine()) {
                // While there are more lines to read
                String line = sc.nextLine();
                // Read the next line from the file
                if (line.trim().isEmpty()) {// Check if the line is empty
                    continue;// Skip empty lines
                }
                
                String[] parts = line.split("\t", -1);
                // Split the line into parts using tab as the delimiter
                // The -1 argument allows for trailing empty strings to be included
                
                Vector<String> rowData = new Vector<>();
                // Create a Vector to hold the row data
                
                
                for (int i = 0; i < model.getColumnCount(); i++) {
                    // Loop through the expected number of columns
                    if (i < parts.length) {// Check if the part exists
                        rowData.add(parts[i].trim()); 
                        // Add the part to the row data, trimming whitespace
                    } else {// If the part does not exist
                        rowData.add(""); 
                        // Add an empty string to maintain the column structure
                    }
                }
                model.addRow(rowData);// Add the row data to the model
            }
            
        } catch (IOException e) {// Handle any IO exceptions that may occur
            
            e.printStackTrace();// Print the stack trace for debugging
            JOptionPane.showMessageDialog(null, "Error loading data from file: " + e.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
        } finally {// Ensure the Scanner is closed to free resources
            if (sc != null) {// Check if the Scanner was initialized
                sc.close();// Close the Scanner
            }
        }
    }
}