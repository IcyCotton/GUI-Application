import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class LoanBase {
    File fileName;
    Scanner fread; // This isn't actually used, can be removed.
    Scanner sc;    // This is used for reading.

    LoanBase(String name) {
        fileName = new File(name);
    }

    public void saveAll(String records) {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(records);
            fw.flush();
        } catch (Exception e) {
            System.err.println("Error 100: File not found or write error: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for more details
        }
    }

    public void DisplayAll(DefaultTableModel model) {
        try {
            // Clear existing data in the table model before loading new data
            model.setRowCount(0); 

            sc = new Scanner(new FileReader(fileName));
            while (sc.hasNextLine()) { // Read line by line
                String line = sc.nextLine();
                if (line.trim().isEmpty()) { // Skip empty lines
                    continue;
                }
                
                // Split the line by tab delimiter. -1 keeps trailing empty strings.
                // Using "\\t" for tab character as a regex.
                String[] parts = line.split("\t", -1); 
                
                Vector<String> rowData = new Vector<>();
                // Ensure we add exactly the number of columns expected by the model
                for (int i = 0; i < model.getColumnCount(); i++) {
                    if (i < parts.length) {
                        // Add the part, trimming any leading/trailing whitespace
                        rowData.add(parts[i].trim()); 
                    } else {
                        // If a line has fewer columns than expected, add empty string for missing cells
                        rowData.add(""); 
                    }
                }
                model.addRow(rowData);
            }
            sc.close();
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}