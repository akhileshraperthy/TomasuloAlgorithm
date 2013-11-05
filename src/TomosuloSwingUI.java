
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author araperth
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

@SuppressWarnings("serial")
public class TomosuloSwingUI extends JFrame implements ActionListener {

    private JButton jb[] = new JButton[5];
    private JLabel jl[] = new JLabel[10];
    private JLabel jlabel_msg;
    private JPanel jp[] = new JPanel[2];
    private JScrollPane[] js = new JScrollPane[4];
    private JTable jT[] = new JTable[4];
    private JTextField jTF[] = new JTextField[4];
    static TomosuloAlgorithm tomosuloAlgorithm;
    // headers for the tables
    String heading[][] = {new String[]{"Instruction", "Issue", "Complete", "Result"},
        new String[]{"Reg", "loc"},
        new String[]{"", "Busy", "Addr"},
        new String[]{"", "Busy", "Op", "Vj", "Qj", "Vk", "Qk",}
    };
    String message = "Message:";

    public static void main(String args[]) {

        TomosuloSwingUI.tomosuloAlgorithm = new TomosuloAlgorithm();
        TomosuloSwingUI tomosuloSwingUI = new TomosuloSwingUI();

        tomosuloSwingUI.initElements();
        tomosuloSwingUI.setVisible(true);
        tomosuloSwingUI.setResizable(false);

    }

    private void initElements() {
        //initializing elements
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 0; i < 10; i++) {
            if (i < jp.length) {
                jp[i] = new JPanel();

            }

            if (i < jb.length) {
                jb[i] = new JButton();
            }

            if (i < jl.length) {
                jl[i] = new JLabel();
            }

            if (i < jT.length) {
                jT[i] = new JTable();

            }
            if (i < js.length) {
                js[i] = new JScrollPane();
            }

            if (i < jTF.length) {
                jTF[i] = new JTextField();
            }
        }

        jlabel_msg = new JLabel();

        jb[0].setText("Open");
        jb[0].addActionListener(this);

        jb[1].setText("Last");
        jb[1].addActionListener(this);

        jb[2].setText("Next");
        jb[2].addActionListener(this);

        jl[6].setText("clk =");

        jl[7].setText("0");

        jlabel_msg.setText(message);



        //drawing tables with default vales in it . 

        //drawing table 1 : instruction table
        drawTable(jT[0], new Object[][]{
        		{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},
        		{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},
        		{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},
        		{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},{null,null,null,null},}, heading[0]);
        js[0].setViewportView(jT[0]);
        js[1].setViewportView(jT[2]);
        js[2].setViewportView(jT[3]);
        js[3].setViewportView(jT[1]);
        //drawing table 2 :
        drawTable(jT[1], new Object[][]{
                    {"F0", null},
                    {"F1", null},
                    {"F2", null},
                    {"F3", null},
                    {"F4", null},
                    {"F5", null},
                    {"F6", null},
                    {"F7", null},
                    {"F8", null},
                    {"F9", null},
                    {"F10", null},
                    {"F11", null},}, heading[1]);

        //drawing table 3 :
        drawTable(jT[2], new Object[][]{
                    {"Load1", null, null},
                    {"Load2", null, null},
                    {"Store1", null, null},
                    {"Store2", null, null},}, heading[2]);

        //drawing table 4 :
        drawTable(jT[3], new Object[][]{
                    {"Add1", null, null, null, null, null, null},
                    {"Add2", null, null, null, null, null, null},
                    
                    {"Mul1", null, null, null, null, null, null},
                    {"Mul2", null, null, null, null, null, null},
                    {"Div1", null, null, null, null, null, null},
                    {"Div2", null, null, null, null, null, null},
                }, heading[3]);

        

        jl[2].setText("Instruction status");
        jl[3].setText("Load/Store Queue");
        jl[4].setText("Register Result status");
        jl[5].setText("Reservation Station");

        drawMenu();

        drawLayout();

    }

    private void drawMenu() {
        // setting and drawing menu elements : Open , next , Last
        jlabel_msg.setBackground(Color.BLUE);
        repaint();

        GroupLayout layout2 = new GroupLayout(jp[1]);
        jp[1].setLayout(layout2);
        layout2.setHorizontalGroup(layout2.createParallelGroup(1)
                .add(layout2
                .createSequentialGroup()
                .add(jl[6])
                .add(jl[7])
                .add(50, 50, 50)
               
                .add(jl[9])
                .add(60, 60, 60)
                .add(jlabel_msg)
                .add(70, 70, 70)
                .add(jb[0])
                .add(20, 20, 20)
                .add(jb[1])
                .add(30, 30, 30)
                .add(jb[2])
                .add(40, 40, 40)));


        layout2.setVerticalGroup(layout2.createParallelGroup(
                1).add(
                layout2.createParallelGroup(
                3).add(jb[0])
                .add(jb[2]).add(jb[1]).add(jl[6]).add(jl[7])
                
                .add(jl[9]).add(jlabel_msg)));


        jlabel_msg.setBackground(Color.BLUE);
        repaint();

    }

    public void drawLayout() {
        // setting and drawing tables and planes
        GroupLayout layout1 = new GroupLayout(jp[0]);
        jp[0].setLayout(layout1);
        layout1.setHorizontalGroup(layout1.createParallelGroup(1).add(
                layout1.createSequentialGroup()
                .add(161, 161, 161)
                .add(jl[2])
                .add(232, 232, 232)
                .add(jl[3])
                .addPreferredGap(LayoutStyle.RELATED, 213, Short.MAX_VALUE)
                .add(50, 50, 50))
                .add(layout1.createSequentialGroup()
                .addContainerGap()
                .add(layout1.createParallelGroup(1)
                .add(layout1.createSequentialGroup()
                .add(js[2], -2, 510, -2)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(js[3], -2, 350, -2)
                .addContainerGap())
                .add(layout1.createSequentialGroup()
                .add(70, 70, 70)
                .add(jl[4])
                .addPreferredGap(LayoutStyle.RELATED, 269, Short.MAX_VALUE)
                .add(jl[5])
                .add(225, 225, 225))))
                .add(layout1.createSequentialGroup()
                .add(layout1.createParallelGroup(1)
                .add(layout1.createSequentialGroup()
                .add(10, 10, 10)
                .add(layout1.createParallelGroup(1)
                .add(jp[1], -1, -1, Short.MAX_VALUE)
                .add(layout1.createSequentialGroup()
                .add(layout1.createParallelGroup(2)
                .add(js[0], -2, 318, -2))
                .addPreferredGap(LayoutStyle.UNRELATED)
                .add(layout1.createParallelGroup(1)
                .add(layout1.createSequentialGroup()
                .addPreferredGap(LayoutStyle.RELATED)
                .addPreferredGap(LayoutStyle.RELATED)
                .addPreferredGap(LayoutStyle.RELATED))
                .add(js[1], 0, 0, Short.MAX_VALUE))
                .add(18, 18, 18)
                .add(layout1.createParallelGroup(1, false)
                .add(layout1.createSequentialGroup()
                .addPreferredGap(LayoutStyle.RELATED).add(jl[1]).addPreferredGap(LayoutStyle.RELATED)
                .addPreferredGap(LayoutStyle.RELATED)))
                .addPreferredGap(LayoutStyle.RELATED, 4, Short.MAX_VALUE))))
                .add(layout1.createSequentialGroup()
                .addContainerGap()))
                .add(18, 18, 18)));
        layout1
                .setVerticalGroup(layout1.createParallelGroup(1)
                .add(layout1.createSequentialGroup()
                .addContainerGap()
                .add(jp[1], -2, -1, -2)
                .add(21, 21, 21)
                .add(layout1.createParallelGroup(3)
                .add(jl[2]).add(jl[3]))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(layout1.createParallelGroup(1, false)
                .add(js[1], 0, 0, Short.MAX_VALUE)
                .add(js[0], -1, 221, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.RELATED, -1, Short.MAX_VALUE)
                .add(layout1.createParallelGroup(3)
                .add(jl[1]))
                .add(18, 18, 18)
                .add(layout1.createParallelGroup(3)
                .add(jl[4])
                .add(jl[5]))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(layout1.createParallelGroup(1, false)
                .add(js[3], 0, 0, Short.MAX_VALUE)
                .add(js[2], -1, 152, Short.MAX_VALUE))
                .addContainerGap()));

        GroupLayout layout = new GroupLayout(
                getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(1).add(jp[0], -2, -1, -2));
        layout.setVerticalGroup(layout.createParallelGroup(1)
                .add(2, jp[0], -1, -1, Short.MAX_VALUE));
        pack();
    }
    Object[][] table_data;

    public void drawTable1contents() {
        // setting contents of Instruction status table after processing 
        table_data = new Object[tomosuloAlgorithm.QueInst.size()][4];

        for (int i = 0; i < tomosuloAlgorithm.QueInst.size(); i++) {

            table_data[i][0] = tomosuloAlgorithm.QueInst.get(i).Name;

            if (tomosuloAlgorithm.QueInst.get(i).taskval == 0) {
                table_data[i][1] = "";
            } else {
                table_data[i][1] = tomosuloAlgorithm.QueInst.get(i).taskval;
            }

            if (tomosuloAlgorithm.QueInst.get(i).execute == 0) {
                table_data[i][2] = "";
            } else {
                table_data[i][2] = tomosuloAlgorithm.QueInst.get(i).execute;
            }

            if (tomosuloAlgorithm.QueInst.get(i).rewrite == 0) {
                table_data[i][3] = "";
            } else {
                table_data[i][3] = tomosuloAlgorithm.QueInst.get(i).rewrite;
            }
        }
       

        drawTable(jT[0], table_data, heading[0]);
    }

    private void drawTable(JTable o_Table, Object[][] table_data, String[] strings) {

       // drawing table
        o_Table.setModel(new javax.swing.table.DefaultTableModel(
                table_data, strings));

    }

    public void drawTable2contents() { //// setting contents of Register Result status table after processing 
        @SuppressWarnings("unused")
		float data;
        int station;
        for (int i = 0; i < Const.RegNum; i++) {
            data = tomosuloAlgorithm.reg_Float.Instr_read(i);

            if ((station = tomosuloAlgorithm.reg_Float.getStation(i)) != -1) {
                jT[1].setValueAt(Const.getInstructionType(station), i, 1);
            }
        }
    }

    public void drawTable3contents() { 	// setting contents of Load/Store Queue table after processing 

   
        for (int i = 0; i < Const.ListQueNum - 2; i++) {
            if (tomosuloAlgorithm.queList.isBusy(i)) {
                jT[2].setValueAt("yes", i, 1);
                int id = tomosuloAlgorithm.queList.getAddr(i);
                jT[2].setValueAt(Const.getInstructionType(id), i, 2);

            } else {
                jT[2].setValueAt("no", i, 1);
                jT[2].setValueAt("", i, 2);
            }

        }
    }

    public void clearAllTable() {
//clearing data in all tables
        for (int i = 0; i < Const.RegNum; i++) {
            for (int j = 1; j < 2; j++) {
                jT[1].setValueAt("", i, j);
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 2; j++) {
                jT[2].setValueAt("", i, j);
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                jT[3].setValueAt("", i, j);
            }
        }
    }

    public void drawTable4contents() {
// setting contents of Reservation Station table after processing 
        float d1, d2;
        int st1, st2;
        for (int i = 0; i < Const.ResvNum; i++) {

            if (tomosuloAlgorithm.resv_station._isBusy(i)) {

                d1 = tomosuloAlgorithm.resv_station.getData1(i);
                d2 = tomosuloAlgorithm.resv_station.getData2(i);
                st1 = tomosuloAlgorithm.resv_station.getStation1(i);
                st2 = tomosuloAlgorithm.resv_station.getStation2(i);
                jT[3].setValueAt("yes", i, 1);
                System.out.println(tomosuloAlgorithm.resv_station.getOp(i));
        
              
              //  else
                    jT[3].setValueAt(tomosuloAlgorithm.resv_station.getOp(i), i, 2);

                if (st1 == -1) {
                    jT[3].setValueAt(d1, i, 3);
                    jT[3].setValueAt("", i, 4);

                } else {
                    jT[3].setValueAt(Const.getInstructionType(st1), i, 4);
                }

                if (st2 == -1) {
                    jT[3].setValueAt(d2, i, 5);
                    jT[3].setValueAt("", i, 6);

                } else {
                    jT[3].setValueAt(Const.getInstructionType(st2), i, 6);
                }
            } else {
                for (int k = 2; k < 7; k++) {
                    jT[3].setValueAt("", i, k);
                }
                jT[3].setValueAt("no", i, 1);
            }
        }
    }

    @SuppressWarnings("unused")
	private void jb4ActionPerformed(ActionEvent evt) {
// action event associated with button 4 
        Object[][] table_data = new Object[tomosuloAlgorithm.QueInst.size()][4];

        for (int i = 0; i < tomosuloAlgorithm.QueInst.size(); i++) {

            table_data[i][0] = tomosuloAlgorithm.QueInst.get(i).Name;
            if (tomosuloAlgorithm.QueInst.get(i).taskval == 0) {
                table_data[i][1] = "";
            } else {
                table_data[i][1] = tomosuloAlgorithm.QueInst.get(i).taskval;
            }

            if (tomosuloAlgorithm.QueInst.get(i).execute == 0) {
                table_data[i][2] = "";
            } else {
                table_data[i][2] = tomosuloAlgorithm.QueInst.get(i).execute;
            }

            if (tomosuloAlgorithm.QueInst.get(i).rewrite == 0) {
                table_data[i][3] = "";
            } else {
                table_data[i][3] = tomosuloAlgorithm.QueInst.get(i).rewrite;
            }

        }

        drawTable(jT[0], table_data, heading[0]);
    }

    private void jb3ActionPerformed(ActionEvent evt) {

		// action event associated with button "Next" 
        tomosuloAlgorithm.nextInstr();

        drawTable2contents();
        drawTable3contents();
        drawTable4contents();

        int addr = tomosuloAlgorithm.store.loc;

        if (addr != -1) {

            tomosuloAlgorithm.store.loc = -1;
        }
        jl[7].setText(((Integer) tomosuloAlgorithm.clk).toString());
       
        drawTable1contents();
        jl[7].setText(((Integer) tomosuloAlgorithm.clk).toString());
       
    }

    private void jb2ActionPerformed(ActionEvent evt) {
// action event associated with button "Last"
        // Last instruction of instruction cycle 

        tomosuloAlgorithm.executeInst();
        drawTable1contents();
        drawTable2contents();
        drawTable3contents();
        drawTable4contents();
        jl[7].setText(((Integer) tomosuloAlgorithm.clk).toString());
       
    }

    @SuppressWarnings("unused")
	private void jb5ActionPerformed(ActionEvent evt) {
    }

    @SuppressWarnings("unused")
	private void jTF1ActionPerformed(ActionEvent evt) {
    }

    @SuppressWarnings("unused")
	private void jTF2ActionPerformed(ActionEvent evt) {
    }

    @SuppressWarnings("unused")
	private void jTF3ActionPerformed(ActionEvent evt) {
    }

    private void jTF4ActionPerformed(ActionEvent evt) {
    }

    private void jb1ActionPerformed(ActionEvent evt) {
        // used to Instr_read a instruction file from particular location.
// action event associated with button "Open"
        JFileChooser jf = new JFileChooser();
        try {
            jf.setCurrentDirectory(new File("."));

            if (jf.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = jf.getSelectedFile();

                if (!file.getName().contains(".in")) { 
                    System.out.println("Wrong file format "); //// on file load fail 
                  jlabel_msg.setText("Message :Wrong file format . please select a .in file");
                   return;
                } else { //// instructions should end with string literal "END"
                    System.out.println("Message :FileLoaded");

                    jlabel_msg.setBackground(Color.BLUE);

                    jlabel_msg.setText("Message :FileLoaded");
                    jlabel_msg.setBackground(Color.BLUE);

                }
////// on success 

                tomosuloAlgorithm = new TomosuloAlgorithm();
                clearAllTable();
                jl[7].setText("0");
     
                BufferedReader bin = new BufferedReader(new FileReader(file));
                String str = "";
 //reading file contents .. ends up on encountering string END
                while (!(str = bin.readLine()).equals("END")) {
                    tomosuloAlgorithm.QueInst.add(new InstItemList(str));
                }
                bin.close();
                Object[][] table_data = new Object[tomosuloAlgorithm.QueInst.size()][4];

                for (int i = 0; i < tomosuloAlgorithm.QueInst.size(); i++) {
// setting file contents to the table
                    table_data[i][0] = tomosuloAlgorithm.QueInst.get(i).Name;
                    table_data[i][1] = "";
                    table_data[i][2] = "";
                    table_data[i][3] = "";

                }
                drawTable(jT[0], table_data, heading[0]);
            }


        } catch (Exception e) {
            System.out.println("Exception here");

            e.printStackTrace();

        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
// Action events associated with different UI components

        if (ae.getSource() == jb[0]) {
            jb1ActionPerformed(ae);
        }

        if (ae.getSource() == jb[1]) {
            jb2ActionPerformed(ae);
        }

        if (ae.getSource() == jb[2]) {
            jb3ActionPerformed(ae);
        }

        if (ae.getSource() == jTF[3]) {
            jTF4ActionPerformed(ae);
        }

    }
}