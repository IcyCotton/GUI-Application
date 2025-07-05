import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class Database {
    File filename=null;
    FileWriter fWrite=null;
    FileReader fRead=null;
    Scanner read=null;

    Database(String name){
        filename=new File(name);
    }

    public void addRecord(String record){
        try {
            fWrite=new FileWriter(filename);
            fWrite.write(record);
            fWrite.flush();
        }catch(Exception e){
            System.err.println("Error 101: Filename...");
        }
    }
    public void displayRecord(DefaultTableModel model){
        try {
           read=new Scanner(new FileReader(filename));
            Vector data;
           while(read.hasNext()){
               data=new Vector<>();
               for (int i = 0; i < model.getColumnCount(); i++) {
                   data.add(read.next());
               }
               model.addRow(data);
           }
           read.close();
        }catch(Exception e){
            System.err.println("Error 101: Filename...");
        }
    }
}
