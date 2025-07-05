import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//PIS has a JFRame - composition

//PIS is a JFrame
public class PetInformationSystem implements ActionListener, MouseListener {
    JLabel lblID, lblName,lblGender,lblAge, lblSearch;
    JTextField txtID, txtName, txtAge, txtSearch;
    JComboBox cboGender; //drop down
    JButton btnAdd, btnClear,btnEdit,btnDelete,btnClose;

    JTable tblPet;
    DefaultTableModel pet;

    JPanel panelInfo, panelTable, panelButtons;
    JFrame myFrame;

    Vector data=new Vector<>(); //row or values
    Vector field=new Vector<>(); //column or attributes

    Database db=new Database("Pet.txt");

    PetInformationSystem(){
        panelInfo=new JPanel();
        panelInfo.setLayout(new GridLayout(4,2));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Pet Information System"));

        lblID=new JLabel("ID: ");
        lblName=new JLabel("Name: ");
        lblGender=new JLabel("Gender: ");
        lblAge=new JLabel("Age: ");

        txtID=new JTextField(); txtName=new JTextField(); txtAge=new JTextField();
        cboGender=new JComboBox<>();
        cboGender.addItem("Male");
        cboGender.addItem("Female");

        panelInfo.add(lblID); panelInfo.add(txtID);
        panelInfo.add(lblName); panelInfo.add(txtName);
        panelInfo.add(lblGender); panelInfo.add(cboGender);
        panelInfo.add(lblAge); panelInfo.add(txtAge);

        btnAdd=new JButton("Add");
        btnClear=new JButton("Clear");
        btnEdit=new JButton("Edit");
        btnDelete=new JButton("Delete");
        btnClose=new JButton("Close");

        //panelTable=new JPanel();
        //panelTable.setBorder(BorderFactory.createTitledBorder(""));
        //panelTable.setBackground(new Color(92, 144, 236));

        panelButtons=new JPanel(new GridLayout(1,6));
        //panelButtons.setBackground(Color.blue);
        panelButtons.add(btnAdd);
        panelButtons.add(btnClear);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(new JLabel("          "));

        panelButtons.add(btnClose);

        myFrame=new JFrame();
        myFrame.setLayout(null);

        myFrame.add(panelInfo).setBounds(10,10,200,150);
        //myFrame.add(panelTable).setBounds(220,37,400,120);
        myFrame.add(panelButtons).setBounds(10,160, 610,40);

        lblSearch=new JLabel("Search: ");
        txtSearch=new JTextField();
        /*
        data.add("101");
        data.add("Anna");
        data.add("Female");
        data.add("20");
        */
        field.add("ID");
        field.add("Name");
        field.add("Gender");
        field.add("Age");
        pet=new DefaultTableModel();
        pet.setColumnIdentifiers(field);
        tblPet=new JTable(pet);
        db.displayRecord(pet);

        myFrame.add(lblSearch).setBounds(220,10,60,30);
        myFrame.add(txtSearch).setBounds(300,10,100,30);
        myFrame.add(new JScrollPane(tblPet)).setBounds(220,40,400,120);

        myFrame.setSize(700,250);
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btnAdd.addActionListener(this);
        btnClear.addActionListener(this);
        btnEdit.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClose.addActionListener(this);

        tblPet.addMouseListener(this);
    }

    public void reset(){
        txtID.setEnabled(true);
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);

        txtID.setText("");
        txtName.setText("");
        cboGender.setSelectedItem("Male");
        txtAge.setText("");
    }
    public void alter(){
        txtID.setEnabled(false);
        btnAdd.setEnabled(false);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        txtID.setEnabled(false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnAdd)){
            data=new Vector<>();
            data.add(txtID.getText());
            data.add(txtName.getText());
            data.add(cboGender.getSelectedItem());
            data.add(txtAge.getText());
            pet.addRow(data);
            reset();
        }else if(e.getSource().equals(btnClear)){
            reset();
        }else if(e.getSource().equals(btnEdit)){
            int i=tblPet.getSelectedRow();
            pet.setValueAt(txtName.getText(), i, 1);
            pet.setValueAt(cboGender.getSelectedItem(), i, 2);
            pet.setValueAt(txtAge.getText(), i, 3);
            reset();
        }else if(e.getSource().equals(btnDelete)){
            int i=tblPet.getSelectedRow();
            pet.removeRow(i);
        }else if(e.getSource().equals(btnClose)){
            String records="";

            for (int r = 0; r < pet.getRowCount(); r++) {
                for (int c = 0; c < pet.getColumnCount(); c++) {
                    records+=pet.getValueAt(r,c)+"\t";
                }
                records+="\n";
            }
            db.addRecord(records);
            System.exit(0);
        }
    } //end of actionperformed

    @Override
    public void mouseClicked(MouseEvent e) {
        int i=tblPet.getSelectedRow();
        if(e.getSource().equals(tblPet)){
            alter();
            txtID.setText(pet.getValueAt(i,0 )+"");
            txtName.setText(pet.getValueAt(i,1 )+"");
            cboGender.setSelectedItem(pet.getValueAt(i,2 )+"");
            txtAge.setText(pet.getValueAt(i,3 )+"");

            //JOptionPane.showMessageDialog(myFrame, i+"h");
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
