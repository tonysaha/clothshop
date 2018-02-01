

import com.lowagie.text.DocumentException;
import com.mxrck.autocompleter.TextAutoCompleter;
import com.sun.media.jfxmediaimpl.MetadataParserImpl;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;
import net.proteanit.sql.DbUtils;
import java.text.*;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.components.barcode4j.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
//import net.proteanit.sql.DbUtils;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tushar
 */
public class Mainf extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    
    
   
    String pid;
    String pname;
    String prate;
    String srate;
    String ctg;
   public String qty;
    String bcode;
    Connection connection;
    Statement st;
    ResultSet rs;
    public double mynetamount;
     //DB_Connection dbc=new DB_Connection();
    public Mainf() {
        
            initComponents();
            defaultClose();
    
           
            
          try {
             
             
             Class.forName("com.mysql.jdbc.Driver");
             //connection=DriverManager.getConnection("jdbc:mysql://db4free.net:3306/tony_cloth","tonysaha","14193169");
             connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tony_cloth","root","");
             st = connection.createStatement();
             JOptionPane.showMessageDialog(null, "Connection ok");
         
	}catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Connection not ok Check Again");
	}
       
        table1();
       // tableStock();
        itemauto(jTextField6,"master",1);
        itemauto(jTextField6, "master",3);
        itemauto(jTextField8, "barcode", 1);
        //itemauto(ppname_TF, "master", 3);
         itemauto(ppname_TF, "master", 3);
        autoId(pproduct_id);
        autoPurchaseId(ppurchaseid_TF);
        autoSalesId(salesid_TF);
        Date date =new Date();
        jDateChooser2.setDate(date);
        jDateChooser3.setDate(date);
        jDateChooser1.setDate(date);
        jDateChooser6.setDate(date);
        
        FillCombo(scat_CB);
        FillCombo(pcat_CB);
        FillCombo(jComboBox1);
        
        
   
      
        
    }
public void defaultClose(){
    
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent we) {
                     //To change body of generated methods, choose Tools | Templates.
                     if(JOptionPane.showConfirmDialog(null, "Are you sure close it? "
                             + "lost Data","Warning",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                     
                         try {
                        
                             if(jPanel3.isDisplayable()||jPanel4.isDisplayable()||jPanel5.isDisplayable()||jPanel16.isDisplayable()||jPanel27.isDisplayable()||jPanel31.isDisplayable()||jPanel34.isDisplayable()){
                             panaleShow(jPanel23);
                             //System.out.println("okk");
                             
                                   if(jTable3.getRowCount()>0){
                             
                                 canclePurchase();
                                 //jTable3.removeAll();
                                 
                                 //jPanel5.setVisible(false);
                             }   
                                   
                                      if(jTable2.getRowCount()>0){
                             
                                 cacleSales();
                                 //jTable3.removeAll();
                                 
                                 //jPanel5.setVisible(false);
                             }
                         
                         }
                         else
                             System.exit(0);
                             
                             
                    } catch (Exception e) {
                    }
                       
              
                     }    
                
                }     
                
                
});
}

public void panaleShow(JPanel jpanel){

                 jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jpanel);
        jPanel2.repaint();
        jPanel2.revalidate();

}

        public void canclePurchase(){
        
                               try {
            
        
        String sql="select Product_id,Quantity from purchase_product where p_id='"+ppurchaseid_TF.getText()+"'";
        List<String>list=new ArrayList<String>();
        List<String>list2=new ArrayList<String>();
         rs=st.executeQuery(sql);
            
            while(rs.next()){
                list.add(rs.getString(1));
                list2.add(rs.getString("Quantity"));
                
            }
             for(int i = 0 ; i < list.size(); i++){
                 String dbquantity="";
                 String masterqty="select qty from master where ProductId='"+list.get(i)+"'";
                rs=st.executeQuery(masterqty);
                  while(rs.next()){
                
           
                dbquantity=(rs.getString("qty"));
                     // System.out.println(dbquantity);
                
            }
                 String newqty=String.valueOf((Integer.valueOf(dbquantity))-(Integer.valueOf(list2.get(i))));
                 //System.out.println(newqty);
        String sql1="update master set qty='"+newqty+"' where ProductId='"+list.get(i)+"'";
        st.executeUpdate(sql1);
    }
            
            } catch (Exception e) {
                //System.out.println(e);
        }
        
        //Delete product when cancle.................................................................................
        String sql2="delete from purchase_product where p_id='"+ppurchaseid_TF.getText()+"'";
        try {
            st.executeUpdate(sql2);
            //JOptionPane.showMessageDialog(null,"deleted....");
            RemoveTableRow(jTable3);
               autoPurchaseId(ppurchaseid_TF);
        psupplier_TF.setText("");
        pscontact_TF.setText("");
        jTextField38.setText("");
        jTextField37.setText("");
        psubtotal_TF.setText("0.0");
        ptotal_TF.setText("0.0");
          
        } catch (Exception ex) {
           // JOptionPane.showMessageDialog(null,"Not deleted....");
            JOptionPane.showMessageDialog(null, ex);
        }
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTextField14 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        spid_TF = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        spname_TF = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        ssprice_TF = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        sbuyprice_TF = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        sqty_TF = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        sdis_TF = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        scat_CB = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        totalitem_TF = new javax.swing.JTextField();
        stotalquantity_TF = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        spaidcash_TF = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        sreturncash_TF = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        subtotal_TF = new javax.swing.JTextField();
        svat_TF = new javax.swing.JTextField();
        sdiscount_TF = new javax.swing.JTextField();
        snetamount_TF = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        salesid_TF = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        Sale_cancel = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTextField27 = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        pproduct_id = new javax.swing.JTextField();
        ppname_TF = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        psaleprice_TF = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        pbuyprice_TF = new javax.swing.JTextField();
        pcat_CB = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        pbarcode_TF = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        pquantity_TF = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        ppurchaseid_TF = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        psupplier_TF = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        pscontact_TF = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel51 = new javax.swing.JLabel();
        jTextField38 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        psubtotal_TF = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        pdiscount_TF = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        pvat_TF = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        ptotal_TF = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jTextField13 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel86 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        Sales_value = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        PurchaseReport_TB = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        pdate2 = new com.toedter.calendar.JDateChooser();
        pdate1 = new com.toedter.calendar.JDateChooser();
        jTextField15 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        pdate_Search = new javax.swing.JButton();
        jLabel84 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        purchasevalue_TF = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btn_outStock = new javax.swing.JLabel();
        btn_scanStock = new javax.swing.JLabel();
        btn_lowStock = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        stock_LB = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel83 = new javax.swing.JLabel();
        Dcost_TF = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        Dcost_Details_TF = new javax.swing.JTextField();
        jPanel36 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        daily_cash_TF = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        Today_othercost_TF = new javax.swing.JTextField();
        Today_Cash_TF = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        jPanel2.setLayout(new java.awt.CardLayout());

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jPanel24.setBackground(new java.awt.Color(54, 33, 89));
        jPanel24.setToolTipText("");

        jLabel40.setBackground(new java.awt.Color(255, 255, 255));
        jLabel40.setFont(new java.awt.Font("Times New Roman", 3, 36)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Ujjal Emporium");

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Management Software");

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Copy Right By © Ujjal Emporium");

        jTextField14.setBackground(new java.awt.Color(54, 33, 89));
        jTextField14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(255, 255, 255));
        jTextField14.setBorder(null);
        jTextField14.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField14.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_Search_32px_2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(631, 631, 631))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addGap(288, 288, 288)
                        .addComponent(jLabel74)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(415, 415, 415))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jTextField14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel74))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addContainerGap())
        );

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(54, 33, 89));
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_Settings_32px_1.png"))); // NOI18N
        jLabel68.setText("Settings");
        jLabel68.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel68.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel68.setOpaque(true);
        jLabel68.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel68MousePressed(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(54, 33, 89));
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_Report_Card_32px.png"))); // NOI18N
        jLabel69.setText("Report");
        jLabel69.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel69.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel69.setOpaque(true);
        jLabel69.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel69.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel69MouseClicked(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(54, 33, 89));
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_Sale_32px.png"))); // NOI18N
        jLabel70.setText("Sales");
        jLabel70.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel70.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel70.setOpaque(true);
        jLabel70.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel70.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel70MousePressed(evt);
            }
        });

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(54, 33, 89));
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_Product_32px.png"))); // NOI18N
        jLabel71.setText("Stock");
        jLabel71.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel71.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel71.setOpaque(true);
        jLabel71.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel71MouseClicked(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(54, 33, 89));
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_Buying_32px.png"))); // NOI18N
        jLabel72.setText("Purchase");
        jLabel72.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel72.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel72.setOpaque(true);
        jLabel72.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel72MousePressed(evt);
            }
        });

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(54, 33, 89));
        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_Sign_Out_32px.png"))); // NOI18N
        jLabel73.setText("Log Out");
        jLabel73.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel73.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel73.setOpaque(true);
        jLabel73.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel73MousePressed(evt);
            }
        });
        jLabel73.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel73KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(862, 862, 862)
                        .addComponent(jLabel67))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(292, 292, 292)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(89, 89, 89)))
                .addGap(256, 256, 256))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addGap(69, 69, 69)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                .addGap(116, 116, 116))
        );

        jPanel2.add(jPanel23, "card6");

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Master_From", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Product_ID:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Catagory:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("ProductName:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Purch_Rate:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Sale_Rate:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("                Date:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("BarcodeNo:");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/print_deffalt.png"))); // NOI18N
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel14MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel14MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel14MouseReleased(evt);
            }
        });

        jDateChooser1.setDateFormatString("yyyy,MM,dd");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Quantity");

        jTextField7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel16)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(99, 99, 99))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addGap(94, 94, 94))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 870, 180));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product_Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel10.setText("Search:");

        jTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField6FocusGained(evt);
            }
        });
        jTextField6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField6MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField6MousePressed(evt);
            }
        });
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField6KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBackground(new java.awt.Color(204, 255, 204));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Product_ID", "BarcodeNo", "Cat:", "Product_Name", "Purch_Rate", "Sale_Rate", "Date"
            }
        ));
        jTable1.setGridColor(new java.awt.Color(204, 204, 0));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 870, 260));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/button_deffalt.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel11MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel11MouseReleased(evt);
            }
        });
        jLabel11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel11KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jLabel11KeyReleased(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/update_deffalt.png"))); // NOI18N
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel13MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel13MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel13MouseReleased(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/delete_deffalt.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel12MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel12MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel12MouseReleased(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/submit_deffalt.png"))); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel15MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel15MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(408, 408, 408)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 870, 60));

        jPanel2.add(jPanel3, "card2");

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));

        jTextField8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField8FocusGained(evt);
            }
        });
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField8KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField8KeyReleased(evt);
            }
        });

        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel13.setLayout(new java.awt.CardLayout());

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 204)));

        jLabel28.setText("Product_id");

        jLabel29.setText("P_Name");

        jLabel30.setText("Sales_Price");

        jLabel31.setText("Buy_Price");

        sbuyprice_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sbuyprice_TFActionPerformed(evt);
            }
        });

        jLabel32.setText("Quantity");

        sqty_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sqty_TFActionPerformed(evt);
            }
        });

        jLabel33.setText("Discount");

        jLabel34.setText("Catagory");

        scat_CB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sbuyprice_TF, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spname_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(ssprice_TF)
                            .addComponent(sdis_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(sqty_TF)
                            .addComponent(spid_TF)
                            .addComponent(scat_CB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField26, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spid_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spname_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ssprice_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sbuyprice_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sqty_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sdis_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scat_CB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel14, "card2");

        jLabel35.setText("Produt_Name");

        jLabel36.setText("Prine");

        jLabel37.setText("Quantity");

        jLabel38.setText("Total_Price");

        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField10)
                            .addComponent(jTextField11)
                            .addComponent(jTextField12, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(234, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel15, "card3");

        jLabel26.setBackground(new java.awt.Color(51, 255, 102));
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/info.png"))); // NOI18N
        jLabel26.setText("Product Info");
        jLabel26.setToolTipText("Product Details");
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel26MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel26MousePressed(evt);
            }
        });

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/edit.png"))); // NOI18N
        jLabel27.setText("Sales Edit");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField8)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 153, 204));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setAutoscrolls(true);

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Price", "Quantity", "Toatal Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setGridColor(new java.awt.Color(255, 255, 255));
        jTable2.setMinimumSize(new java.awt.Dimension(10, 0));
        jTable2.setRowHeight(25);
        jTable2.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jTable2ComponentAdded(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        totalitem_TF.setEditable(false);
        totalitem_TF.setBackground(new java.awt.Color(255, 255, 255));
        totalitem_TF.setText("Total Item 0");
        totalitem_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalitem_TFActionPerformed(evt);
            }
        });

        stotalquantity_TF.setText("Total Quantity 0");
        stotalquantity_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stotalquantity_TFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(totalitem_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(173, 173, 173)
                .addComponent(stotalquantity_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalitem_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stotalquantity_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
        );

        jPanel11.setBackground(new java.awt.Color(204, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Cash Transaction"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Paid Cash");

        spaidcash_TF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        spaidcash_TF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                spaidcash_TFFocusGained(evt);
            }
        });
        spaidcash_TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                spaidcash_TFKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Return Cash");

        sreturncash_TF.setBackground(new java.awt.Color(0, 0, 0));
        sreturncash_TF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sreturncash_TF.setForeground(new java.awt.Color(51, 255, 51));
        sreturncash_TF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sreturncash_TF.setText("0.0");
        sreturncash_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sreturncash_TFActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(0, 204, 204));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Sub Total");
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel19.setBackground(new java.awt.Color(153, 0, 255));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Vat");
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Discount");
        jLabel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Net Amount");
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        svat_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                svat_TFActionPerformed(evt);
            }
        });
        svat_TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                svat_TFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                svat_TFKeyReleased(evt);
            }
        });

        sdiscount_TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sdiscount_TFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sdiscount_TFKeyReleased(evt);
            }
        });

        snetamount_TF.setBackground(new java.awt.Color(0, 0, 0));
        snetamount_TF.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        snetamount_TF.setForeground(new java.awt.Color(255, 255, 0));
        snetamount_TF.setText("0.0");
        snetamount_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                snetamount_TFActionPerformed(evt);
            }
        });

        jComboBox2.setBackground(new java.awt.Color(204, 255, 204));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Payment Method", "Cash", "Credit Card" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spaidcash_TF)
                    .addComponent(sreturncash_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(subtotal_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(svat_TF)
                    .addComponent(sdiscount_TF)
                    .addComponent(snetamount_TF))
                .addGap(39, 39, 39))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spaidcash_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sreturncash_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(subtotal_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(svat_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sdiscount_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(snetamount_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))))
        );

        sreturncash_TF.getAccessibleContext().setAccessibleDescription("");

        jPanel12.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Sales Transiction");
        jLabel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel23.setText("Bill No");

        jLabel24.setText("Customer Name");

        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });

        jLabel25.setText("Contact info");

        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });
        jTextField19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField19KeyTyped(evt);
            }
        });

        jDateChooser2.setDateFormatString("yyyy,MM,dd");

        Sale_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/cancel_deffolt.png"))); // NOI18N
        Sale_cancel.setText("jLabel36");
        Sale_cancel.setPreferredSize(new java.awt.Dimension(85, 40));
        Sale_cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Sale_cancelMousePressed(evt);
            }
        });

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/submit_deffalt.png"))); // NOI18N
        jLabel39.setText("jLabel39");
        jLabel39.setPreferredSize(new java.awt.Dimension(85, 40));
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel39MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel39MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel39MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel39MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(144, 144, 144))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(salesid_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113)
                        .addComponent(Sale_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(salesid_TF)
                                .addComponent(jLabel25)
                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Sale_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel4, "card3");

        jTextField27.setEditable(false);
        jTextField27.setBackground(new java.awt.Color(0, 153, 255));
        jTextField27.setFont(new java.awt.Font("Wide Latin", 1, 18)); // NOI18N
        jTextField27.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField27.setText("Purchase Transection");
        jTextField27.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jTextField27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField27ActionPerformed(evt);
            }
        });

        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 255, 204), null));

        jLabel43.setText("Catagory");

        jLabel44.setText("Quantity");

        jLabel45.setText("Product_id");

        jLabel46.setText("Product_Name");

        pproduct_id.setEditable(false);
        pproduct_id.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pproduct_idFocusGained(evt);
            }
        });
        pproduct_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pproduct_idActionPerformed(evt);
            }
        });

        ppname_TF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ppname_TFFocusGained(evt);
            }
        });
        ppname_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppname_TFActionPerformed(evt);
            }
        });
        ppname_TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ppname_TFKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ppname_TFKeyTyped(evt);
            }
        });

        jLabel47.setBackground(new java.awt.Color(51, 255, 102));
        jLabel47.setText("Product Info");
        jLabel47.setToolTipText("Product Details");
        jLabel47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel47MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel47MousePressed(evt);
            }
        });

        jLabel48.setText("Purchase Edit");

        jLabel49.setText("Sales_Price");

        psaleprice_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                psaleprice_TFActionPerformed(evt);
            }
        });

        jLabel50.setText("Order By");

        jLabel52.setText("Buy Price");

        pcat_CB.setEditable(true);
        pcat_CB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        jButton2.setText("ADD");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        pbarcode_TF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pbarcode_TFMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pbarcode_TFMouseEntered(evt);
            }
        });
        pbarcode_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pbarcode_TFActionPerformed(evt);
            }
        });
        pbarcode_TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pbarcode_TFKeyPressed(evt);
            }
        });

        jLabel57.setText("Barcode:");

        pquantity_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pquantity_TFActionPerformed(evt);
            }
        });
        pquantity_TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pquantity_TFKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43)
                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pbuyprice_TF)
                            .addComponent(pcat_CB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pquantity_TF)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(35, 35, 35)
                                .addComponent(jButton2))
                            .addComponent(psaleprice_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ppname_TF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pproduct_id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField37)
                            .addComponent(pbarcode_TF))))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ppname_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pproduct_id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(pquantity_TF))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(pcat_CB))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pbuyprice_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psaleprice_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pbarcode_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel53.setText("Purchase_ID:");

        jLabel54.setText("Supplier_Name:");

        psupplier_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                psupplier_TFActionPerformed(evt);
            }
        });

        jLabel55.setText("Contact_NO:");

        pscontact_TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pscontact_TFKeyTyped(evt);
            }
        });

        jLabel56.setText("Date:");

        jDateChooser3.setDateFormatString("yyyy,MM,dd");

        jLabel51.setText("Approved By");

        jTextField38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField38ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel53))
                    .addComponent(jLabel51))
                .addGap(10, 10, 10)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ppurchaseid_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(jTextField38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(psupplier_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pscontact_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ppurchaseid_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(psupplier_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pscontact_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel56))
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Buy Price", "Quantity", "Amount"
            }
        ));
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable3KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel20.setPreferredSize(new java.awt.Dimension(529, 120));

        jLabel58.setText("Sub Total");

        psubtotal_TF.setText("0.0");

        jLabel59.setText("Shiping");

        pdiscount_TF.setEditable(false);
        pdiscount_TF.setText("0.0");

        jLabel60.setText("Tax");

        pvat_TF.setText("0.0");
        pvat_TF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pvat_TFKeyReleased(evt);
            }
        });

        jLabel61.setText("Discount");

        jTextField47.setEditable(false);
        jTextField47.setText("0.0");

        jLabel62.setText("Total");

        ptotal_TF.setBackground(new java.awt.Color(0, 0, 0));
        ptotal_TF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ptotal_TF.setForeground(new java.awt.Color(255, 255, 0));
        ptotal_TF.setText("0.0");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addGap(18, 18, 18)
                .addComponent(psubtotal_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel59)
                .addGap(26, 26, 26)
                .addComponent(jTextField47, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(jLabel60)
                .addGap(18, 18, 18)
                .addComponent(pvat_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ptotal_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pdiscount_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addGap(28, 28, 28))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(psubtotal_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59)
                    .addComponent(jLabel60)
                    .addComponent(pvat_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61)
                    .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pdiscount_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(ptotal_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(153, 204, 255), null));

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/button_deffalt.png"))); // NOI18N

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/update_deffalt.png"))); // NOI18N

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/submit_deffalt.png"))); // NOI18N
        jLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel65MousePressed(evt);
            }
        });

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button/delete_deffalt.png"))); // NOI18N
        jLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel66MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(561, 561, 561)
                .addComponent(jLabel63)
                .addGap(18, 18, 18)
                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel65)
                .addGap(34, 34, 34)
                .addComponent(jLabel66)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField27)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)))
            .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5, "card4");

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setToolTipText("");

        jPanel22.setBackground(new java.awt.Color(204, 204, 255));

        jTable4.setBackground(new java.awt.Color(255, 255, 51));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton5.setText("Print");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel25.setBackground(new java.awt.Color(54, 33, 89));
        jPanel25.setToolTipText("");

        jLabel75.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Sales Report");

        jLabel76.setFont(new java.awt.Font("Bookman Old Style", 2, 14)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("Ujjal Emporium");

        jDateChooser5.setBackground(new java.awt.Color(54, 33, 89));
        jDateChooser5.setToolTipText("");
        jDateChooser5.setDateFormatString("yyyy,MM,dd");

        jDateChooser4.setBackground(new java.awt.Color(54, 33, 89));
        jDateChooser4.setToolTipText("");
        jDateChooser4.setDateFormatString("yyyy,MM.dd");

        jTextField13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });

        jButton4.setText("Search By invoice No");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("Search By Date");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel86.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(255, 255, 255));
        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setText("  To");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75))
                .addGap(198, 198, 198)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser5, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                    .addComponent(jTextField13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                .addGap(327, 327, 327))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel75)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel76)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jDateChooser5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jPanel26.setBackground(new java.awt.Color(124, 85, 227));
        jPanel26.setToolTipText("");

        jLabel77.setBackground(new java.awt.Color(54, 33, 89));
        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setText("Sales Value");
        jLabel77.setOpaque(true);

        Sales_value.setBackground(new java.awt.Color(54, 33, 89));
        Sales_value.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Sales_value.setForeground(new java.awt.Color(255, 255, 0));
        Sales_value.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Sales_value.setText("0.0");
        Sales_value.setBorder(null);
        Sales_value.setCaretColor(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Sales_value, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Sales_value, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(363, Short.MAX_VALUE))
        );

        jLabel77.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel2.add(jPanel16, "card5");

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setToolTipText("");

        jPanel28.setBackground(new java.awt.Color(204, 204, 255));

        PurchaseReport_TB.setBackground(new java.awt.Color(255, 255, 51));
        PurchaseReport_TB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(PurchaseReport_TB);

        jButton7.setBackground(new java.awt.Color(51, 51, 255));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton7.setText("Print");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel29.setBackground(new java.awt.Color(54, 33, 89));
        jPanel29.setToolTipText("");

        jLabel78.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("Purchase Report");

        jLabel79.setFont(new java.awt.Font("Bookman Old Style", 2, 14)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setText("Ujjal Emporium");

        pdate2.setBackground(new java.awt.Color(54, 33, 89));
        pdate2.setToolTipText("");
        pdate2.setDateFormatString("yyyy,MM,dd");

        pdate1.setBackground(new java.awt.Color(54, 33, 89));
        pdate1.setToolTipText("");
        pdate1.setDateFormatString("yyyy,MM.dd");

        jTextField15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });

        jButton8.setText("Search By invoice No");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        pdate_Search.setText("Search By Date");
        pdate_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdate_SearchActionPerformed(evt);
            }
        });

        jLabel84.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setText("To");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78))
                .addGap(130, 130, 130)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField15, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(pdate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pdate2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(pdate_Search, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addGap(330, 330, 330))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel79)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(pdate_Search)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pdate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pdate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );

        jTextField15.getAccessibleContext().setAccessibleName("");

        jPanel30.setBackground(new java.awt.Color(124, 85, 227));
        jPanel30.setToolTipText("");

        jLabel80.setBackground(new java.awt.Color(54, 33, 89));
        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel80.setText("Purchase Value");
        jLabel80.setOpaque(true);

        purchasevalue_TF.setBackground(new java.awt.Color(54, 33, 89));
        purchasevalue_TF.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        purchasevalue_TF.setForeground(new java.awt.Color(255, 255, 0));
        purchasevalue_TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        purchasevalue_TF.setText("0.0");
        purchasevalue_TF.setBorder(null);
        purchasevalue_TF.setCaretColor(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(purchasevalue_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(purchasevalue_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(363, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel2.add(jPanel27, "card5");

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));

        jPanel32.setBackground(new java.awt.Color(55, 38, 91));
        jPanel32.setToolTipText("");

        jLabel81.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("Stock");
        jLabel81.setToolTipText("");

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("Ujjal Emporium");

        btn_outStock.setBackground(new java.awt.Color(87, 70, 120));
        btn_outStock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_outStock.setForeground(new java.awt.Color(255, 255, 255));
        btn_outStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_outStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_Out_of_Stock_32px.png"))); // NOI18N
        btn_outStock.setText("Out Of Stock");
        btn_outStock.setToolTipText("");
        btn_outStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_outStock.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_outStock.setInheritsPopupMenu(false);
        btn_outStock.setOpaque(true);
        btn_outStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_outStockMouseClicked(evt);
            }
        });

        btn_scanStock.setBackground(new java.awt.Color(87, 70, 120));
        btn_scanStock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_scanStock.setForeground(new java.awt.Color(255, 255, 255));
        btn_scanStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_scanStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_Scan_Stock_32px.png"))); // NOI18N
        btn_scanStock.setText("Scan Stock");
        btn_scanStock.setToolTipText("");
        btn_scanStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_scanStock.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_scanStock.setInheritsPopupMenu(false);
        btn_scanStock.setOpaque(true);
        btn_scanStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_scanStockMouseClicked(evt);
            }
        });

        btn_lowStock.setBackground(new java.awt.Color(87, 70, 120));
        btn_lowStock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_lowStock.setForeground(new java.awt.Color(255, 255, 255));
        btn_lowStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_lowStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_Low_Battery_32px.png"))); // NOI18N
        btn_lowStock.setText("Low Stock");
        btn_lowStock.setToolTipText("");
        btn_lowStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_lowStock.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_lowStock.setInheritsPopupMenu(false);
        btn_lowStock.setOpaque(true);
        btn_lowStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_lowStockMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel81)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel82)
                        .addGap(0, 101, Short.MAX_VALUE))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_outStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_lowStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel32Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btn_scanStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(jLabel82))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(btn_lowStock, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_outStock, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel32Layout.createSequentialGroup()
                    .addGap(150, 150, 150)
                    .addComponent(btn_scanStock, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(469, Short.MAX_VALUE)))
        );

        jPanel33.setBackground(new java.awt.Color(124, 85, 227));
        jPanel33.setToolTipText("");

        stock_LB.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        stock_LB.setForeground(new java.awt.Color(255, 255, 255));
        stock_LB.setText("Welcome Stock");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(stock_LB, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(82, Short.MAX_VALUE))
                    .addComponent(jSeparator3)))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(stock_LB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable5);

        jButton9.setBackground(new java.awt.Color(102, 102, 255));
        jButton9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton9.setText("Print");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel31, "card8");

        jPanel35.setBackground(new java.awt.Color(124, 85, 227));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select One", "Bank Deposit", "Supplier", "Employee", "Other", " " }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel83.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel83.setText("Cost:");

        jLabel85.setText("Cost Details:");

        jButton10.setText("ADD");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Update");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox3, 0, 267, Short.MAX_VALUE)
                    .addComponent(Dcost_TF)
                    .addComponent(Dcost_Details_TF)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel83)
                            .addComponent(jLabel85))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Dcost_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Dcost_Details_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane7.setViewportView(jTable6);

        jLabel89.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel89.setText("Todays Sales Cash Value");

        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel90.setText("Todays Other Cost");

        Today_Cash_TF.setBackground(new java.awt.Color(0, 0, 0));
        Today_Cash_TF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Today_Cash_TF.setForeground(new java.awt.Color(51, 255, 51));
        Today_Cash_TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Today_Cash_TF.setText("0.0");

        jLabel91.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel91.setText("Todays Cash");

        jButton12.setBackground(new java.awt.Color(0, 204, 51));
        jButton12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton12.setText("Cash");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Today_othercost_TF, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(daily_cash_TF)
                            .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Today_Cash_TF)
                            .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(daily_cash_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Today_othercost_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(Today_Cash_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel37.setBackground(new java.awt.Color(54, 33, 89));

        jLabel87.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setText("Daily Report");

        jLabel88.setFont(new java.awt.Font("Bookman Old Style", 2, 14)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 255, 255));
        jLabel88.setText("Ujjal Emporium");

        jDateChooser6.setBackground(new java.awt.Color(54, 33, 89));
        jDateChooser6.setDateFormatString("yyyy,MM,dd");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel88)
                    .addComponent(jLabel87)
                    .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel87)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel34, "card9");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 204, 204));

        jMenu1.setText("File");
        jMenu1.setToolTipText("");
        jMenu1.setFocusable(false);
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu1.setMargin(new java.awt.Insets(5, 5, 5, 5));

        jMenuItem9.setText("Refresh");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuItem7.setText("Home");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Master");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu2.setHideActionText(true);
        jMenu2.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem2.setText("Item");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Sales");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Purchase");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Report");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu3.setMargin(new java.awt.Insets(5, 5, 5, 5));

        jMenuItem5.setText("Sales Report");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem6.setText("Purchase report");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Stock");
        jMenu4.setAutoscrolls(true);
        jMenu4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu4.setMargin(new java.awt.Insets(5, 5, 5, 5));

        jMenuItem8.setText("View Stock");
        jMenuItem8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Others");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Others Daily Cost");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed

      
    }//GEN-LAST:event_jMenu2ActionPerformed

    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
       jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel3);
        jPanel2.repaint();
        jPanel2.revalidate();
        table1();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        
           jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel4);
        
        jPanel2.repaint();
        jPanel2.revalidate();
       // itemauto(jTextField8, "barcode", 1);
       
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jLabel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseEntered
     ImageIcon II = new ImageIcon(getClass().getResource("/button/print_Hover.png"));
        jLabel14.setIcon(II);
    }//GEN-LAST:event_jLabel14MouseEntered

    private void jLabel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseExited
        ImageIcon II = new ImageIcon(getClass().getResource("/button/print_deffalt.png"));
        jLabel14.setIcon(II);
    }//GEN-LAST:event_jLabel14MouseExited

    private void jLabel14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MousePressed
        try {
            ImageIcon II = new ImageIcon(getClass().getResource("/button/print_pressed.png"));
            jLabel14.setIcon(II);
            
            m_component();
            

           
            barcode bcd=new barcode(jTextField2.getText());
            
            Map<String,Object> param=new HashMap<>();
            param.put("price", jTextField5.getText()+" TK");
        
     JasperPrint jasperprint = JasperFillManager.fillReport(pathDetect("Barcode.jasper").toString()+"\\src\\Barcode\\Barcode_1.jasper", param, new JREmptyDataSource());
           JasperViewer.viewReport(jasperprint,false);
           
            
//            barcode bcd=new barcode(bcode, srate);
//          if(Desktop.isDesktopSupported()){
//              try {
//                  File myFile = new File("src\\Barcode\\Java4s_BarCode_128.pdf");
//                  Desktop.getDesktop().open(myFile);
//              } catch (IOException ex) {
//                  JOptionPane.showMessageDialog(null, ex);
//              }
//          
//          }
//            
//            
       } catch (Exception ex) {
            System.out.println(ex);
       } 
//      catch (FileNotFoundException ex) {
//            Logger.getLogger(Mainf.class.getName()).log(Level.SEVERE, null, ex);
//        }
      
    }//GEN-LAST:event_jLabel14MousePressed

    private void jLabel14MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseReleased
        ImageIcon II = new ImageIcon(getClass().getResource("/button/print_deffalt.png"));
        jLabel14.setIcon(II);
    }//GEN-LAST:event_jLabel14MouseReleased

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked

   
    }//GEN-LAST:event_jLabel11MouseClicked
    
    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
      ImageIcon II = new ImageIcon(getClass().getResource("/button/button_Hover.png"));
        jLabel11.setIcon(II);
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        ImageIcon II = new ImageIcon(getClass().getResource("/button/button_deffalt.png"));
        jLabel11.setIcon(II);
    }//GEN-LAST:event_jLabel11MouseExited

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
        ImageIcon II = new ImageIcon(getClass().getResource("/button/button_pressed.png"));
        jLabel11.setIcon(II);
        
       
        
        //............insert.........
        m_component();
       // String sql="INSERT INTO master(ProductId,Catagory,ProductName,Purch_rate,Sale_rate,qty,Date,Barcode)"+"values"+"('"+pid+"','"+ctg+"','"+pname+"','"+prate+"','"+srate+"''"+qty+"','"+bcode+"')";
        String sql="INSERT INTO master(ProductId,Catagory,productName,Purch_rate,Sale_rate,qty,Barcode)"+"VALUES"+"('"+pid+"','"+ctg+"','"+pname+"','"+prate+"','"+srate+"','"+qty+"','"+bcode+"')";
        String sql2="INSERT INTO barcode(Barcode,Sale_rate,ProductId)"+"VALUES"+"('"+bcode+"','"+srate+"','"+pid+"')";
        try {
            
            
            st.executeUpdate(sql);
            st.executeUpdate(sql2);
            JOptionPane.showMessageDialog(null, "Added Sucessfull");
            mainf_clear();
        } catch (Exception e) {
           // System.out.println(e);
            
            JOptionPane.showMessageDialog(null, "NotSucessfull ");
        }
    }//GEN-LAST:event_jLabel11MousePressed

    private void jLabel11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseReleased
        ImageIcon II = new ImageIcon(getClass().getResource("/button/button_deffalt.png"));
        jLabel11.setIcon(II);
    }//GEN-LAST:event_jLabel11MouseReleased

    private void jLabel11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11KeyPressed

    private void jLabel11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel11KeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel11KeyReleased

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
      
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseEntered
        ImageIcon II = new ImageIcon(getClass().getResource("/button/update_Hover.png"));
        jLabel13.setIcon(II);
    }//GEN-LAST:event_jLabel13MouseEntered

    private void jLabel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseExited
       ImageIcon II = new ImageIcon(getClass().getResource("/button/update_deffalt.png"));
        jLabel13.setIcon(II);
    }//GEN-LAST:event_jLabel13MouseExited

    private void jLabel13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MousePressed
      ImageIcon II = new ImageIcon(getClass().getResource("/button/update_pressed.png"));
        jLabel13.setIcon(II);
        
        String sqlup = "update master set Catagory='"+jComboBox1.getSelectedItem()+"',productName='"+jTextField3.getText()+"',Purch_rate='"+jTextField4.getText()+"',Sale_rate='"+jTextField5.getText()+"',qty='"+jTextField7.getText()+"',Barcode='"+jTextField2.getText()+"' where ProductId='"+jTextField1.getText()+"'";
        try {
            st.executeUpdate(sqlup);
            JOptionPane.showMessageDialog(null,"Updated s...");
        } catch (SQLException ex) {
            Logger.getLogger(Mainf.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Not Updated...");
        }
        table1();
        mainf_clear();
    }//GEN-LAST:event_jLabel13MousePressed

    private void jLabel13MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseReleased
        ImageIcon II = new ImageIcon(getClass().getResource("/button/update_deffalt.png"));
        jLabel13.setIcon(II);
    }//GEN-LAST:event_jLabel13MouseReleased

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
  

    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
        ImageIcon II = new ImageIcon(getClass().getResource("/button/delete_Hover.png"));
        jLabel12.setIcon(II);
    }//GEN-LAST:event_jLabel12MouseEntered

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
        ImageIcon II = new ImageIcon(getClass().getResource("/button/delete_deffalt.png"));
        jLabel12.setIcon(II);
    }//GEN-LAST:event_jLabel12MouseExited

    private void jLabel12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MousePressed
        ImageIcon II = new ImageIcon(getClass().getResource("/button/delet_pressed.png"));
        jLabel12.setIcon(II);
        
        int w = JOptionPane.showConfirmDialog(null,"Do you really want to delete..","Warnning!!!",JOptionPane.YES_NO_OPTION);
        if(w==0){        
        String sqldl="delete from master where ProductId='"+jTextField1.getText()+"'";
        //String sqldl2="delete from barcode where(Barcode,Sale_rate,ProductId)"+"VALUES"+"('"+bcode+"','"+srate+"','"+pid+"')";
        
        try {
            st.executeUpdate(sqldl);
            //st.executeUpdate(sqldl2);
            JOptionPane.showMessageDialog(null, "Delete Sucessfull");
        } catch (SQLException ex) {
            Logger.getLogger(Mainf.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Not Sucessfull");
        }
        

        
        table1();
        mainf_clear();
        }
    }//GEN-LAST:event_jLabel12MousePressed

    private void jLabel12MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseReleased
        ImageIcon II = new ImageIcon(getClass().getResource("/button/delete_deffalt.png"));
        jLabel12.setIcon(II);
    }//GEN-LAST:event_jLabel12MouseReleased

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        ImageIcon II = new ImageIcon(getClass().getResource("/button/submit_Hover.png"));
        jLabel15.setIcon(II);
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        ImageIcon II = new ImageIcon(getClass().getResource("/button/submit_deffalt.png"));
        jLabel15.setIcon(II);
    }//GEN-LAST:event_jLabel15MouseExited

    private void jLabel15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MousePressed
       ImageIcon II = new ImageIcon(getClass().getResource("/button/submit_pressed.png"));
        jLabel15.setIcon(II);
    }//GEN-LAST:event_jLabel15MousePressed

    private void jLabel15MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseReleased
        ImageIcon II = new ImageIcon(getClass().getResource("/button/submit_deffalt.png"));
        jLabel15.setIcon(II);
    }//GEN-LAST:event_jLabel15MouseReleased

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        // TODO add your handling code here:
              
   
    }//GEN-LAST:event_jTextField6KeyReleased

    private void jTextField6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField6MousePressed
        // TODO add your handling code here:
     
    }//GEN-LAST:event_jTextField6MousePressed

    private void jTextField6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField6MouseClicked
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jTextField6MouseClicked

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
                
       try {
           String Sql="Select *from master where ProductId = '"+jTextField6.getText()+"' or ProductName='"+jTextField6.getText()+"'";
           rs=st.executeQuery(Sql);
           
          
           jTable1.setModel(DbUtils.resultSetToTableModel(rs));
           
           
       } catch (Exception e) {
          
           JOptionPane.showMessageDialog(null, "No Product In List");
       }
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusGained

          try {
              if(jTextField6.getText()==""){
                  table1();
              }
           String Sql="Select *from master where ProductId = '"+jTextField6.getText()+"' or ProductName='"+jTextField6.getText()+"'";
           rs=st.executeQuery(Sql);
           
          
           jTable1.setModel(DbUtils.resultSetToTableModel(rs));
           
           
       } catch (Exception e) {
          
           JOptionPane.showMessageDialog(null, "No Product In List");
       }
        
        
// TODO add your handling code here:
        
//                String Sql="Select *from master where ProductId = '"+jTextField6.getText()+"'";
//       try {
//           rs=st.executeQuery(Sql);
//           jTable1.setModel(DbUtils.resultSetToTableModel(rs));
//           
//           
//       } catch (Exception e) {
//           JOptionPane.showMessageDialog(null, "No Product In List");
//       }
    }//GEN-LAST:event_jTextField6FocusGained

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel5);
        jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jTable2ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jTable2ComponentAdded
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jTable2ComponentAdded
double eachItem_STprice;
String squantity;

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
          
      NewJDialog jd=new NewJDialog(this, rootPaneCheckingEnabled);
      jd.setVisible(true);
      
       squantity=jd.dquantity;
       
        int sqt=Integer.valueOf(squantity);
        int stockqt=Integer.parseInt(sqty_TF.getText());
        
        if(sqt<=stockqt){
//     
       List<String> list=new ArrayList<String>();
            DefaultTableModel model=(DefaultTableModel) jTable2.getModel();
      
       list.add(spname_TF.getText());
       list.add(ssprice_TF.getText());
       list.add(squantity);
//     
       eachItem_STprice=((Double.valueOf(ssprice_TF.getText()))*(Double.valueOf(jd.dquantity))-(Double.valueOf(jd.ddiscount))*(Double.valueOf(jd.dquantity)));
        list.add(String.valueOf(eachItem_STprice));

    
      model.addRow(list.toArray());
      
      tabledata(jTable2,"Total Quantity ", stotalquantity_TF, 2);
      tabledata(jTable2,"", subtotal_TF, 3);
            snetamount_TF.setText(subtotal_TF.getText());
      sales_product();
//        
      totalitem_TF.setText("Total Item "+String.valueOf(jTable2.getRowCount()));
          
      
      
     // Clear ar Code Ar por hoba.......................
        jTextField8.setText("");
      spid_TF.setText("");
      spname_TF.setText("");
      ssprice_TF.setText("");
      sbuyprice_TF.setText("");
      sqty_TF.setText("");
      sdis_TF.setText("");
      jTextField26.setText("");
      scat_CB.setSelectedIndex(0);
      
        }
        
        else{  
               
      JOptionPane.showMessageDialog(null, "Quantity Not Available...Stock "+stockqt);
        
        }
        }
        
        
        catch(Exception e){
          // System.out.println(e);
        
        }
        
       
        //qt.setEnabled(true);
       
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    public void sales(){
        try {
            String sql="INSERT INTO sales(Sales_Id)"+"VALUES"+"('"+salesid_TF.getText()+"')";
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Mainf.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     }
    public void sales_product(){
        try {
            NewJDialog jd=new NewJDialog(this, rootPaneCheckingEnabled);
            String sql="INSERT INTO sales_product(Sales_Id,Product_id,Product_Name,sales_price,Quantity,Discount,Total_amount,Date)"+"VALUES"+"('"+salesid_TF.getText()+"','"+spid_TF.getText()+"','"+spname_TF.getText()+"','"+ssprice_TF.getText()+"','"+squantity+"','"+jd.ddiscount+"','"+String.valueOf(eachItem_STprice)+"','"+((JTextField)jDateChooser2.getDateEditor().getUiComponent()).getText()+"')";
            st.executeUpdate(sql);
           qty=String.valueOf(Integer.valueOf(qty)-Integer.valueOf(squantity));
           String sql2="update master set qty='"+qty+"' where ProductId='"+spid_TF.getText()+"'";
           st.executeUpdate(sql2);
        // System.out.println(qty);
        } catch (SQLException ex) {
            //Logger.getLogger(Mainf.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, ex);
        }
    
    }
    public void purchase_productAdd(){
      try {
            //double each_producttotal=(Double.valueOf(pbuyprice_TF.getText())*Double.valueOf(pquantity_TF.getText()));
           // System.out.println(eachItem_PTprice);
            String sql="INSERT INTO purchase_product(p_id,Product_id,Product_Name,Quantity,Buy_price,Total_price,Date)"+"VALUES"+"('"+ppurchaseid_TF.getText()+"','"+pproduct_id.getText()+"','"+ppname_TF.getText()+"','"+pquantity_TF.getText()+"','"+pbuyprice_TF.getText()+"','"+String.valueOf(eachItem_PTprice)+"','"+((JTextField)jDateChooser3.getDateEditor().getUiComponent()).getText()+"')";
        
             String sql2="INSERT INTO master(ProductId,Catagory,productName,Purch_rate,Sale_rate,qty,Barcode)"+"VALUES"+"('"+pproduct_id.getText()+"','"+pcat_CB.getSelectedItem()+"','"+ppname_TF.getText()+"','"+pbuyprice_TF.getText()+"','"+psaleprice_TF.getText()+"','"+pquantity_TF.getText()+"','"+pbarcode_TF.getText()+"')";
        String sql3="INSERT INTO barcode(Barcode,Sale_rate,ProductId)"+"VALUES"+"('"+pbarcode_TF.getText()+"','"+psaleprice_TF.getText()+"','"+pproduct_id.getText()+"')";
      
            
            st.executeUpdate(sql3);
            st.executeUpdate(sql);
            st.executeUpdate(sql2);
            
            JOptionPane.showMessageDialog(null, "Added Sucessfull");
            
    
        
//           qty=String.valueOf(Integer.valueOf(qty)-Integer.valueOf(squantity));
//           String sql2="update master set qty='"+qty+"' where ProductId='"+spid_TF.getText()+"'";
//           st.executeUpdate(sql2);
         
        } catch (SQLException ex) {
            //Logger.getLogger(Mainf.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, ex);
        }
    
    }
    
    public void purchase_productUpdate(){
        try {
            String sql="INSERT INTO purchase_product(p_id,Product_id,Product_Name,Quantity,Buy_price,Total_price,Date)"+"VALUES"+"('"+ppurchaseid_TF.getText()+"','"+pproduct_id.getText()+"','"+ppname_TF.getText()+"','"+pquantity_TF.getText()+"','"+pbuyprice_TF.getText()+"','"+String.valueOf(eachItem_PTprice)+"','"+((JTextField)jDateChooser3.getDateEditor().getUiComponent()).getText()+"')";
            
             qty=String.valueOf(Integer.valueOf(qty)+Integer.valueOf(pquantity_TF.getText()));
                String sql2="update master set qty='"+qty+"',Sale_rate='"+psaleprice_TF.getText()+"',Purch_rate='"+pbuyprice_TF.getText()+"' where ProductId='"+pproduct_id.getText()+"'";
         st.executeUpdate(sql2);
         st.executeUpdate(sql);
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
               
    
    }
    private void jLabel26MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel26MouseEntered

    private void jLabel26MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel26MousePressed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusGained
       
        spid_TF.setText("");
      spname_TF.setText("");
      ssprice_TF.setText("");
      sbuyprice_TF.setText("");
      sqty_TF.setText("");
      sdis_TF.setText("");
      jTextField26.setText("");
      scat_CB.setSelectedIndex(0);
        String bpid="";
        try {
            // TODO add your handling code here:
            String src1="Select ProductId from barcode where Barcode='"+jTextField8.getText()+"'";
            
             rs=st.executeQuery(src1);
             
            
            while(rs.next()){
                
           
                bpid=(rs.getString(1));
                
            }
            
            String src2="Select * from master where ProductId='"+bpid+"'";
            
            
            
            rs=st.executeQuery(src2);
            
            while(rs.next()){
                
           
                spid_TF.setText(rs.getString(1));
                spname_TF.setText(rs.getString(3));
                ssprice_TF.setText(rs.getString(5));
                sbuyprice_TF.setText(rs.getString(4));
                sqty_TF.setText(rs.getString(6));
                qty=rs.getString(6);
                scat_CB.setSelectedItem(rs.getString(2));
                
            }   
        } catch (SQLException ex) {
            Logger.getLogger(Mainf.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }//GEN-LAST:event_jTextField8FocusGained

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel model=(DefaultTableModel) jTable1.getModel();
        int selectRowindex=jTable1.getSelectedRow();
        jTextField1.setText(model.getValueAt(selectRowindex, 0).toString());
        jTextField2.setText(model.getValueAt(selectRowindex, 7).toString());
        jTextField3.setText(model.getValueAt(selectRowindex, 2).toString());
        jTextField4.setText(model.getValueAt(selectRowindex, 3).toString());
        jTextField5.setText(model.getValueAt(selectRowindex, 4).toString());
        jTextField7.setText(model.getValueAt(selectRowindex, 5).toString());
        jComboBox1.setSelectedItem(model.getValueAt(selectRowindex, 1));
        
        //jTextField6.setText(model.getValueAt(selectRowindex, 4).toString());
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void sbuyprice_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sbuyprice_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sbuyprice_TFActionPerformed

    private void sqty_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sqty_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sqty_TFActionPerformed

    private void totalitem_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalitem_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalitem_TFActionPerformed

    private void stotalquantity_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stotalquantity_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stotalquantity_TFActionPerformed

    private void spaidcash_TFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spaidcash_TFFocusGained
        // TODO add your handling code here:
        
        //sreturncash_TF.setText(String.valueOf(Double.valueOf(snetamount_TF.getText())-Double.valueOf(spaidcash_TF.getText())));
    }//GEN-LAST:event_spaidcash_TFFocusGained

    private void spaidcash_TFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spaidcash_TFKeyReleased
        // TODO add your handling code here:
        try {
           
           double returncash=(Double.valueOf(snetamount_TF.getText())-Double.valueOf(spaidcash_TF.getText()));
            sreturncash_TF.setText(String.valueOf(returncash));
        } catch (Exception e) {
            //System.out.println("0.0");
            sreturncash_TF.setText("0.0");
        }
    }//GEN-LAST:event_spaidcash_TFKeyReleased

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        // TODO add your handling code here:
        try {
            
      
        DefaultTableModel model=(DefaultTableModel) jTable2.getModel();
        
        if(evt.getKeyCode()==KeyEvent.VK_DELETE)
            if(JOptionPane.showConfirmDialog(null, "Are you sure delete item?","Warning",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                String productname=(String) (jTable2.getValueAt(jTable2.getSelectedRow(), 0));
                String quantity=(String) (jTable2.getValueAt(jTable2.getSelectedRow(), 2));
                String bpid="";
                String getQuntity="";
                model.removeRow(jTable2.getSelectedRow());
      jTable2.repaint();
       tabledata(jTable2,"Toatl Quantity ", stotalquantity_TF, 2);
      tabledata(jTable2,"", subtotal_TF, 3);
                try {
                    
               
 
              String src1="Select ProductId,qty from master where productName='"+productname+"'";
            
             rs=st.executeQuery(src1);
             
            
            while(rs.next()){
                
           
                bpid=(rs.getString(1));
                getQuntity=(rs.getString("qty"));
               // System.out.println(bpid);
                
               // System.out.println("ok"+bpid);
            }
            
            String updateqty=String.valueOf((Integer.valueOf(getQuntity))+(Integer.valueOf(quantity)));
            String updatesql="update master set qty='"+updateqty+"' where ProductId='"+bpid+"'";
            st.executeUpdate(updatesql);
            
            String sql2="delete from sales_product where Product_Id='"+bpid+"' and Sales_Id='"+salesid_TF.getText()+"' and Quantity='"+quantity+"' ";
            st.executeUpdate(sql2);
             } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
      //sales_product();
//        
      totalitem_TF.setText("Total Item "+String.valueOf(jTable2.getRowCount()));
       snetamount_TF.setText(subtotal_TF.getText());
      if(jTable2.getRowCount()==0){
          stotalquantity_TF.setText("Total Quantity 0");
          subtotal_TF.setText("0.0");
          snetamount_TF.setText("0.0");
      }
            }
          } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void svat_TFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_svat_TFKeyPressed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_svat_TFKeyPressed

    private void svat_TFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_svat_TFKeyReleased
        // TODO add your handling code here:
         try {
            double ssamount=(Double.valueOf(subtotal_TF.getText()));
            double svat=(Double.valueOf(svat_TF.getText()));
            double totalvat=(ssamount*svat)/100;
           // System.out.println(totalvat);
            double snetamount=ssamount+totalvat;
            //System.out.println(snetamount);
            snetamount_TF.setText(String.valueOf(snetamount));
            mynetamount=snetamount;
        } catch (Exception e) {
            snetamount_TF.setText(subtotal_TF.getText());
            // System.out.println("");
            mynetamount=(Double.valueOf(subtotal_TF.getText()));
        } 
      
    }//GEN-LAST:event_svat_TFKeyReleased
    private void Sale_cancelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Sale_cancelMousePressed
       
        //Update quantity when Cancle...............................................................................
        try {
            
        
        String sql="select Product_Id,Quantity from sales_product where Sales_Id='"+salesid_TF.getText()+"'";
        List<String>list=new ArrayList<String>();
        List<String>list2=new ArrayList<String>();
         rs=st.executeQuery(sql);
            
            while(rs.next()){
                list.add(rs.getString(1));
                list2.add(rs.getString("Quantity"));
                
            }
             for(int i = 0 ; i < list.size(); i++){
                 String dbquantity="";
                 String masterqty="select qty from master where ProductId='"+list.get(i)+"'";
                rs=st.executeQuery(masterqty);
                  while(rs.next()){
                
           
                dbquantity=(rs.getString("qty"));
                      //System.out.println(dbquantity);
                
            }
                 String newqty=String.valueOf((Integer.valueOf(dbquantity))+(Integer.valueOf(list2.get(i))));
                 //System.out.println(qty);
        String sql1="update master set qty='"+newqty+"' where ProductId='"+list.get(i)+"'";
        st.executeUpdate(sql1);
    }
            
            } catch (Exception e) {
                //System.out.println(e);
        }
        
        //Delete product when cancle.................................................................................
        String sql2="delete from sales_product where Sales_Id='"+salesid_TF.getText()+"'";
        try {
            st.executeUpdate(sql2);
            //JOptionPane.showMessageDialog(null,"deleted....");
            RemoveTableRow(jTable2);
          
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null,"Not deleted....");
            JOptionPane.showMessageDialog(null, ex);
        }
        sale_cancel_clear();
        //table2();
        
    }//GEN-LAST:event_Sale_cancelMousePressed

    public void cacleSales(){
                try {
            
        
        String sql="select Product_Id,Quantity from sales_product where Sales_Id='"+salesid_TF.getText()+"'";
        List<String>list=new ArrayList<String>();
        List<String>list2=new ArrayList<String>();
         rs=st.executeQuery(sql);
            
            while(rs.next()){
                list.add(rs.getString(1));
                list2.add(rs.getString("Quantity"));
                
            }
             for(int i = 0 ; i < list.size(); i++){
                 String dbquantity="";
                 String masterqty="select qty from master where ProductId='"+list.get(i)+"'";
                rs=st.executeQuery(masterqty);
                  while(rs.next()){
                
           
                dbquantity=(rs.getString("qty"));
                      //System.out.println(dbquantity);
                
            }
                 String newqty=String.valueOf((Integer.valueOf(dbquantity))+(Integer.valueOf(list2.get(i))));
                 //System.out.println(qty);
        String sql1="update master set qty='"+newqty+"' where ProductId='"+list.get(i)+"'";
        st.executeUpdate(sql1);
    }
            
            } catch (Exception e) {
                //System.out.println(e);
        }
        
        //Delete product when cancle.................................................................................
        String sql2="delete from sales_product where Sales_Id='"+salesid_TF.getText()+"'";
        try {
            st.executeUpdate(sql2);
            //JOptionPane.showMessageDialog(null,"deleted....");
            RemoveTableRow(jTable2);
          
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null,"Not deleted....");
            JOptionPane.showMessageDialog(null, ex);
        }
        sale_cancel_clear();
    
    }
    
    private void jLabel39MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MousePressed
       
        
        ImageIcon II = new ImageIcon(getClass().getResource("/button/submit_pressed.png"));
        jLabel11.setIcon(II);
       
        if(jComboBox2.getSelectedItem().toString()=="Select Payment Method"){
            JOptionPane.showMessageDialog(jComboBox2, "Select Payment Method");
        }
        else{
        String sql2="INSERT INTO sales(Sales_ID,Cust_Name,Contact,Date,Total_amount,Sub_amount,Vat,Discount,Paid_cash,return_cash,Payment_Type)"+"VALUES"+"('"+salesid_TF.getText()+"','"+jTextField18.getText()+"','"+jTextField19.getText()+"','"+((JTextField)jDateChooser2.getDateEditor().getUiComponent()).getText()+"','"+snetamount_TF.getText()+"','"+subtotal_TF.getText()+"','"+svat_TF.getText()+"','"+sdiscount_TF.getText()+"','"+spaidcash_TF.getText()+"','"+sreturncash_TF.getText()+"','"+jComboBox2.getSelectedItem().toString()+"')";
        try {
            st.executeUpdate(sql2);
            JOptionPane.showMessageDialog(null,"submited....");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Not submited...."+ex);
           // System.out.println(ex);
        }
        if("".equals(svat_TF.getText())){
            svat_TF.setText("0");
        }
        if("".equals(sdiscount_TF.getText())){
            sdiscount_TF.setText("0.0");
        }
//     Invoice_Make invoice=new Invoice_Make();
//      invoice.saleprint(jTable2, salesid_TF,subtotal_TF,svat_TF,sdiscount_TF,snetamount_TF, jDateChooser2);



 



           SalesInvoice();



        sale_submit_clear();
        RemoveTableRow(jTable2);
        autoSalesId(salesid_TF);
        }
    }//GEN-LAST:event_jLabel39MousePressed
    public void SalesInvoice(){
            try {
                
            String sql="SELECT * FROM sales_product WHERE Sales_Id = '"+salesid_TF.getText()+"'";
             
            rs= st.executeQuery(sql);
                //System.out.println("ok");
            Map<String,Object> param=new HashMap<>();
            param.put("Invoice_No", salesid_TF.getText());
            param.put("sub_total", subtotal_TF.getText());
             param.put("vat", svat_TF.getText());
              param.put("discount", sdiscount_TF.getText());
               param.put("total_price", snetamount_TF.getText());
                JasperPrint jasperprint = JasperFillManager.fillReport("src\\Report\\Sales_invoice.jasper", param, new JRResultSetDataSource(rs));
               JasperViewer.viewReport(jasperprint,false);
               
               
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
            
    }
    
     public void PurchaseInvoice(){
            try {
            String sql="SELECT * FROM purchase_product WHERE p_id = '"+ppurchaseid_TF.getText()+"'";
             
            rs= st.executeQuery(sql);
                System.out.println("ok");
            Map<String,Object> param=new HashMap<>();
            param.put("Invoice_No",ppurchaseid_TF.getText());
            param.put("sub_total", psubtotal_TF.getText());
             param.put("vat", pvat_TF.getText());
              param.put("discount", pdiscount_TF.getText());
               param.put("total_price", ptotal_TF.getText());
                JasperPrint jasperprint = JasperFillManager.fillReport("src\\Report\\Purchase_invoice.jasper", param, new JRResultSetDataSource(rs));
               JasperViewer.viewReport(jasperprint,false);
               
               
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
            
    }
    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18ActionPerformed

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField19ActionPerformed

    private void jLabel39MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseEntered
        ImageIcon II = new ImageIcon(getClass().getResource("/button/submit_deffalt.png"));
        jLabel11.setIcon(II);
    }//GEN-LAST:event_jLabel39MouseEntered

    private void jLabel39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseClicked
        ImageIcon II = new ImageIcon(getClass().getResource("/button/submit_pressed.png"));
        jLabel11.setIcon(II);
    }//GEN-LAST:event_jLabel39MouseClicked

    private void jLabel39MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseExited
        ImageIcon II = new ImageIcon(getClass().getResource("/button/submit_deffalt.png"));
        jLabel11.setIcon(II);
    }//GEN-LAST:event_jLabel39MouseExited

    private void jTextField27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField27ActionPerformed

    private void pproduct_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pproduct_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pproduct_idActionPerformed

    private void jLabel47MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel47MouseEntered

    private void jLabel47MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel47MousePressed

    private void psupplier_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_psupplier_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_psupplier_TFActionPerformed

    private void pbarcode_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pbarcode_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pbarcode_TFActionPerformed

    private void sreturncash_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sreturncash_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sreturncash_TFActionPerformed

    private void pproduct_idFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pproduct_idFocusGained
        // TODO add your handling code here:
      
//        
//    
                                   
        
    }//GEN-LAST:event_pproduct_idFocusGained

    private void ppname_TFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ppname_TFFocusGained
          //itemauto(ppname_TF, "master", 3);
        String bpid="";
        autoId(pproduct_id);
        jButton2.setEnabled(true);
        jButton3.setEnabled(false);
        
        pbuyprice_TF.setText("");
        pcat_CB.setSelectedItem("Select one");
        psaleprice_TF.setText("");
        pbarcode_TF.setText("");
      try{
            
            String src2="Select * from master where ProductName='"+ppname_TF.getText()+"'";
            
            boolean check=false;
            
            rs=st.executeQuery(src2);
            
            while(rs.next()){
                
           
                pproduct_id.setText(rs.getString(1));
                jButton2.setEnabled(false);
                jButton3.setEnabled(true);
                 
                psaleprice_TF.setText(rs.getString(5));
                pbuyprice_TF.setText(rs.getString(4));
                //sqty_TF.setText(rs.getString(6));
                qty=rs.getString(6);
                pcat_CB.setSelectedItem(rs.getString(2));
                pbarcode_TF.setText(rs.getString("Barcode"));
              
               
                
                
            }   
            
           
            
        } catch (SQLException ex) {
            
        }
       
        
    }//GEN-LAST:event_ppname_TFFocusGained
double eachItem_PTprice;
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      
        try {
             
    
      
//     
       List<String> list=new ArrayList<String>();
            DefaultTableModel model=(DefaultTableModel) jTable3.getModel();
      
       list.add(ppname_TF.getText());
       list.add(pbuyprice_TF.getText());
       list.add(pquantity_TF.getText());
//     
       eachItem_PTprice=(Double.valueOf(pbuyprice_TF.getText()))*(Double.valueOf(pquantity_TF.getText()));
        list.add(String.valueOf(eachItem_PTprice));

    
      model.addRow(list.toArray());
            tabledata(jTable3,"", psubtotal_TF, 3);
            ptotal_TF.setText(psubtotal_TF.getText());
               purchase_productAdd();
               autoId(pproduct_id);
               
               
               
               ///clear...............
      pbuyprice_TF.setText("");
        pcat_CB.setSelectedItem("Select one");
        psaleprice_TF.setText("");
        pbarcode_TF.setText("");
        pcat_CB.setSelectedItem("select");
        pquantity_TF.setText("");
        ppname_TF.setText("");
        itemauto(ppname_TF, "master", 3);
        itemauto(jTextField6, "master", 3);
        itemauto(jTextField8, "barcode", 1);
        
              
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void psaleprice_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_psaleprice_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_psaleprice_TFActionPerformed

    private void jTextField38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField38ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
             try {
        
       List<String> list=new ArrayList<String>();
            DefaultTableModel model=(DefaultTableModel) jTable3.getModel();
      
       list.add(ppname_TF.getText());
       list.add(pbuyprice_TF.getText());
       list.add(pquantity_TF.getText());
//     
       eachItem_PTprice=(Double.valueOf(pbuyprice_TF.getText()))*(Double.valueOf(pquantity_TF.getText()));
        list.add(String.valueOf(eachItem_PTprice));
    
      
        //String sql="insert into purchase_product(p_id,Product_Id,Quantity)"+"VALUES"+"('"+ppurchaseid_TF.getText()+"','"+pproduct_id.getText()+"','"+pquantity_TF.getText()+"')";
        
        
        model.addRow(list.toArray());      
      tabledata(jTable3,"", psubtotal_TF, 3);
      ptotal_TF.setText(psubtotal_TF.getText());
      purchase_productUpdate();
      autoId(pproduct_id);
      
      
      //clear.............
      
       pbuyprice_TF.setText("");
        pcat_CB.setSelectedItem("Select one");
        psaleprice_TF.setText("");
        pbarcode_TF.setText("");
        //pcat_CB.setSelectedItem("select");
        pquantity_TF.setText("");
        ppname_TF.setText("");     
        
//        itemauto(ppname_TF, "master", 3);
//        itemauto(jTextField6, "master", 3);
//        itemauto(jTextField8, "barcode", 1);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel16);
        jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed
private void formWindowClosing(java.awt.event.WindowEvent evt)
{
    this.hide();
    Login login=new Login();
    login.setVisible(true);
}
    
    String saleReportSql;
String purchaseReportSql;
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
       try {
            String sql="select * from sales_product where Sales_Id='"+jTextField13.getText()+"'";
           saleReportSql=sql;
            rs=st.executeQuery(sql);
           jTable4.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
       
       tabledata(jTable4,"", Sales_value, 6);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
//          MessageFormat header=new MessageFormat("Report Print");
//        MessageFormat footer=new MessageFormat("page[0,number,integer]");
//       
//        try {
//            jTable4.print(JTable.PrintMode.NORMAL,header,footer);
//        } catch (Exception e) {
//        }

 try {
            String sql=saleReportSql;
            rs=st.executeQuery(sql);
           //jTable4.setModel(DbUtils.resultSetToTableModel(rs));
           Map<String,Object> param=new HashMap<>();
            param.put("totalamount", Sales_value.getText());
           
           JasperPrint jasperprint = JasperFillManager.fillReport(pathDetect("Sales_Report.jasper").toString()+"\\src\\Report\\Sales_Report.jasper", param, new JRResultSetDataSource(rs));
           JasperViewer.viewReport(jasperprint,false);
                //JasperPrintManager.printReport(jasperprint, false);
    


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jLabel65MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MousePressed
        // TODO add your handling code here:
        
        
               try {
                    String sql2="INSERT INTO purchase(Purchase_Id,Supplier_Name,Contact,Date,Total_amount,Sub_amount,Vat,Discount)"+"VALUES"+"('"+ppurchaseid_TF.getText()+"','"+psupplier_TF.getText()+"','"+pscontact_TF.getText()+"','"+((JTextField)jDateChooser3.getDateEditor().getUiComponent()).getText()+"','"+ptotal_TF.getText()+"','"+psubtotal_TF.getText()+"','"+pvat_TF.getText()+"','"+pdiscount_TF.getText()+"')";
     
            st.executeUpdate(sql2);
            JOptionPane.showMessageDialog(null,"submited....");
        
        if("".equals(pvat_TF.getText())){
            svat_TF.setText("0");
        }
        if("".equals(pdiscount_TF.getText())){
            sdiscount_TF.setText("0.0");
        }
        Invoice_Make invoice=new Invoice_Make();
        //invoice.saleprint(jTable3, ppurchaseid_TF,psubtotal_TF,pvat_TF,pdiscount_TF,ptotal_TF, jDateChooser2);
        //sale_submit_clear();
        PurchaseInvoice();
        
        RemoveTableRow(jTable3);
        autoPurchaseId(ppurchaseid_TF);
        psupplier_TF.setText("");
        pscontact_TF.setText("");
        jTextField38.setText("");
        jTextField37.setText("");
        psubtotal_TF.setText("0.0");
        ptotal_TF.setText("0.0");
        
               }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Not submited...."+ex);
            //JOptionPane.showMessageDialog(null, ex);
        }
        
    }//GEN-LAST:event_jLabel65MousePressed
            public void FillCombo(JComboBox jcb){
                try {
                    String sql="select distinct Catagory as ctg from master";
                    rs=st.executeQuery(sql);
                    while(rs.next()){
                    
                        String catagory=rs.getString("ctg");
                        jcb.addItem(catagory);
                    }
                    AutoCompleteDecorator.decorate(jcb);
                } catch (Exception e) {
                }
            
            }
    
    
    private void pvat_TFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pvat_TFKeyReleased
        // TODO add your handling code here:
        
          try {
            double ssamount=(Double.valueOf(psubtotal_TF.getText()));
            double svat=(Double.valueOf(pvat_TF.getText()));
            double totalvat=(ssamount*svat)/100;
           // System.out.println(totalvat);
            double snetamount=ssamount+totalvat;
            //System.out.println(snetamount);
            ptotal_TF.setText(String.valueOf(snetamount));
        } catch (Exception e) {
            ptotal_TF.setText(psubtotal_TF.getText());
            // System.out.println("");
        } 
    }//GEN-LAST:event_pvat_TFKeyReleased

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed
        // TODO add your handling code here:
        
         try {
            
      
        DefaultTableModel model=(DefaultTableModel) jTable3.getModel();
        
        if(evt.getKeyCode()==KeyEvent.VK_DELETE)
            if(JOptionPane.showConfirmDialog(null, "Are you sure delete item?","Warning",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                String productname=(String) (jTable3.getValueAt(jTable3.getSelectedRow(), 0));
                String quantity=(String) (jTable3.getValueAt(jTable3.getSelectedRow(), 2));
                String bpid="";
                String getQuntity="";
                model.removeRow(jTable3.getSelectedRow());
      jTable3.repaint();
       //tabledata(jTable2,"Toatl Quantity ", stotalquantity_TF, 2);
      tabledata(jTable3,"", psubtotal_TF, 3);
                try {
                    
               
 
              String src1="Select ProductId,qty from master where productName='"+productname+"'";
            
             rs=st.executeQuery(src1);
             
            
            while(rs.next()){
                
           
                bpid=(rs.getString(1));
                getQuntity=(rs.getString("qty"));
//               System.out.println(bpid);
//                
              // System.out.println("ok"+getQuntity);
            }
            
            String updateqty=String.valueOf((Integer.valueOf(getQuntity))-(Integer.valueOf(quantity)));
             //System.out.println("update ok"+updateqty);
            String updatesql="update master set qty='"+updateqty+"' where ProductId='"+bpid+"'";
            st.executeUpdate(updatesql);
            
            String sql2="delete from purchase_product where Product_id='"+bpid+"' and p_id='"+ppurchaseid_TF.getText()+"' and Quantity='"+quantity+"' ";
            st.executeUpdate(sql2);
             } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
      //sales_product();
//        
      //totalitem_TF.setText("Total Item "+String.valueOf(jTable2.getRowCount()));
       ptotal_TF.setText(psubtotal_TF.getText());
      if(jTable3.getRowCount()==0){
          //stotalquantity_TF.setText("Total Quantity 0");
          psubtotal_TF.setText("0.0");
          ptotal_TF.setText("0.0");
      }
            }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jTable3KeyPressed

    private void pscontact_TFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pscontact_TFKeyTyped
        // TODO add your handling code here:
        try {
            
            if(pscontact_TF.getText().length()>10){
            evt.consume();
            JOptionPane.showMessageDialog(null, "please Enter valid Number");
            }
        } catch (Exception e) {
           
        }
    }//GEN-LAST:event_pscontact_TFKeyTyped

    private void jTextField19KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField19KeyTyped
        // TODO add your handling code here:
           try {
            
            if(jTextField19.getText().length()>10){
            evt.consume();
            JOptionPane.showMessageDialog(null, "please Enter valid Number");
            }
        } catch (Exception e) {
           
        }
    }//GEN-LAST:event_jTextField19KeyTyped

    private void ppname_TFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ppname_TFKeyTyped
        // TODO add your handling code here:
        
         String bpid="";
        autoId(pproduct_id);
        jButton2.setEnabled(true);
        jButton3.setEnabled(false);
        
        pbuyprice_TF.setText("");
        pcat_CB.setSelectedItem("Select one");
        psaleprice_TF.setText("");
        pbarcode_TF.setText("");
      try{
            
            String src2="Select * from master where ProductName='"+ppname_TF.getText()+"'";
            
            boolean check=false;
            
            rs=st.executeQuery(src2);
            
            while(rs.next()){
                
           
                pproduct_id.setText(rs.getString(1));
                jButton2.setEnabled(false);
                jButton3.setEnabled(true);
                 
                psaleprice_TF.setText(rs.getString(5));
                pbuyprice_TF.setText(rs.getString(4));
                //sqty_TF.setText(rs.getString(6));
                qty=rs.getString(6);
                pcat_CB.setSelectedItem(rs.getString(2));
                pbarcode_TF.setText(rs.getString("Barcode"));
              
               
                
                
            }   
            
           
            
        } catch (SQLException ex) {
            
        }
    }//GEN-LAST:event_ppname_TFKeyTyped

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
        // TODO add your handling code here:
              try {
           String Sql="Select *from master where ProductId = '"+jTextField6.getText()+"' or ProductName='"+jTextField6.getText()+"'";
           rs=st.executeQuery(Sql);
          
           jTable1.setModel(DbUtils.resultSetToTableModel(rs));
          
           
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "No Product In List");
       }
        
    }//GEN-LAST:event_jTextField6KeyTyped

    private void jTextField8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyReleased
        // TODO add your handling code here:
        
        // itemauto(jTextField8, "barcode", 1);
        
        String bpid="";
        try {
            // TODO add your handling code here:
            String src1="Select ProductId from barcode where Barcode='"+jTextField8.getText()+"'";
            
             rs=st.executeQuery(src1);
             
            
            while(rs.next()){
                
           
                bpid=(rs.getString(1));
                
            }
            
            String src2="Select * from master where ProductId='"+bpid+"'";
            
            
            
            rs=st.executeQuery(src2);
            
            while(rs.next()){
                
           
                spid_TF.setText(rs.getString(1));
                spname_TF.setText(rs.getString(3));
                ssprice_TF.setText(rs.getString(5));
                sbuyprice_TF.setText(rs.getString(4));
                sqty_TF.setText(rs.getString(6));
                qty=rs.getString(6);
                scat_CB.setSelectedItem(rs.getString(2));
                
            }   
        } catch (SQLException ex) {
            Logger.getLogger(Mainf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField8KeyReleased

    private void jTextField8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyPressed
        // TODO add your handling code here:
         String bpid="";
        try {
            // TODO add your handling code here:
            String src1="Select ProductId from barcode where Barcode='"+jTextField8.getText()+"'";
            
             rs=st.executeQuery(src1);
             
            
            while(rs.next()){
                
           
                bpid=(rs.getString(1));
                
            }
            
            String src2="Select * from master where ProductId='"+bpid+"'";
            
            
            
            rs=st.executeQuery(src2);
            
            while(rs.next()){
                
           
                spid_TF.setText(rs.getString(1));
                spname_TF.setText(rs.getString(3));
                ssprice_TF.setText(rs.getString(5));
                sbuyprice_TF.setText(rs.getString(4));
                sqty_TF.setText(rs.getString(6));
                qty=rs.getString(6);
                scat_CB.setSelectedItem(rs.getString(2));
                
            }   
        } catch (SQLException ex) {
            Logger.getLogger(Mainf.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
               
            
                                  try{
          
      NewJDialog jd=new NewJDialog(this, rootPaneCheckingEnabled);
      jd.setVisible(true);
      
       squantity=jd.dquantity;
       
        int sqt=Integer.valueOf(squantity);
        int stockqt=Integer.parseInt(sqty_TF.getText());
        
        if(sqt<=stockqt){
//     
       List<String> list=new ArrayList<String>();
            DefaultTableModel model=(DefaultTableModel) jTable2.getModel();
      
       list.add(spname_TF.getText());
       list.add(ssprice_TF.getText());
       list.add(squantity);
//     
       eachItem_STprice=((Double.valueOf(ssprice_TF.getText()))*(Double.valueOf(jd.dquantity))-(Double.valueOf(jd.ddiscount))*(Double.valueOf(jd.dquantity)));
        list.add(String.valueOf(eachItem_STprice));

    
      model.addRow(list.toArray());
      
      tabledata(jTable2,"Total Quantity ", stotalquantity_TF, 2);
      tabledata(jTable2,"", subtotal_TF, 3);
            snetamount_TF.setText(subtotal_TF.getText());
      sales_product();
//        
      totalitem_TF.setText("Total Item "+String.valueOf(jTable2.getRowCount()));
          
      
      
     // Clear ar Code Ar por hoba.......................
        jTextField8.setText("");
      spid_TF.setText("");
      spname_TF.setText("");
      ssprice_TF.setText("");
      sbuyprice_TF.setText("");
      sqty_TF.setText("");
      sdis_TF.setText("");
      jTextField26.setText("");
      scat_CB.setSelectedIndex(0);
      
        }
        
        else{  
               
      JOptionPane.showMessageDialog(null, "Quantity Not Available...Stock "+stockqt);
        
        }
        }
        
        
        catch(Exception e){
          // System.out.println(e);
        
        }
        
        }
        
        
    }//GEN-LAST:event_jTextField8KeyPressed

    private void ppname_TFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ppname_TFKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppname_TFKeyPressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
         try {
            String sql="select * from sales_product where Date>='"+((JTextField)jDateChooser4.getDateEditor().getUiComponent()).getText()+"'and Date<='"+((JTextField)jDateChooser5.getDateEditor().getUiComponent()).getText()+"'";
            
            saleReportSql=sql;
            rs=st.executeQuery(sql);
           jTable4.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
         
         tabledata(jTable4,"", Sales_value, 6);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void snetamount_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_snetamount_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_snetamount_TFActionPerformed

    private void jLabel68MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MousePressed
        // TODO add your handling code here:
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel3);
        
        jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_jLabel68MousePressed

    private void jLabel70MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel70MousePressed
        // TODO add your handling code here:
         jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel4);
        
        jPanel2.repaint();
        jPanel2.revalidate();
                                             
       
    }//GEN-LAST:event_jLabel70MousePressed

    private void jLabel73KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel73KeyPressed
        // TODO add your handling code here:
        
       
    }//GEN-LAST:event_jLabel73KeyPressed

    private void jLabel73MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel73MousePressed
        // TODO add your handling code here:
         Login log=new Login();
//            jTextField1.setText(null);
//            jPasswordField1.setText(null);
            log.setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_jLabel73MousePressed

    private void jLabel72MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel72MousePressed
        // TODO add your handling code here:
          jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel5);
        
        jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_jLabel72MousePressed

    private void ppname_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppname_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppname_TFActionPerformed

    private void jLabel69MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel69MouseClicked
        // TODO add your handling code here:
         NewJDialog1 jd=new NewJDialog1(this, rootPaneCheckingEnabled);
      jd.setVisible(true);
      
      if(jd.viewreport=="sales"){
            jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel16);
        
        jPanel2.repaint();
        jPanel2.revalidate();
      }
      
           else if(jd.viewreport=="purchase"){
            jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel27);
        
        jPanel2.repaint();
        jPanel2.revalidate();
      }
      else
           {  jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel23);
        
        jPanel2.repaint();
        jPanel2.revalidate();    } 
    }//GEN-LAST:event_jLabel69MouseClicked

    public String pathDetect(String fileName){
        File myFile = new File("Purchase_Report.jasper");
            
            String path=myFile.getAbsolutePath();
           
    File file = new File(path);

    String filePath = file.getAbsolutePath().substring(0,path.lastIndexOf(File.separator));
    return filePath;
    
    }
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        
         try {
            String sql=purchaseReportSql;
            rs=st.executeQuery(sql);
           //jTable4.setModel(DbUtils.resultSetToTableModel(rs));
           
            Map<String,Object> param=new HashMap<>();
            param.put("total", purchasevalue_TF.getText());
            
             //System.out.println(pathDetect("Purchase_Report.jasper").toString()); 
//            File myFile = new File("Purchase_Report.jasper");
//            
//            String path=myFile.getAbsolutePath();
//           
//    File file = new File(path);
//
//    String filePath = file.getAbsolutePath().substring(0,path.lastIndexOf(File.separator));
//    System.out.println(filePath);
    
    
    
    
    
            //System.out.println(filePath+"\\src\\Report\\Purchase_Report.jasper");
           JasperPrint jasperprint = JasperFillManager.fillReport(pathDetect("Purchase_Report.jasper").toString()+"\\src\\Report\\Purchase_Report.jasper", param, new JRResultSetDataSource(rs));
           JasperViewer.viewReport(jasperprint,false);
           
                //JasperPrintManager.printReport(jasperprint, false);
    


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void pdate_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdate_SearchActionPerformed
        // TODO add your handling code here:
        
         try {
             purchasevalue_TF.setText("0.0");
            String sql="select * from purchase_product where Date>='"+((JTextField)pdate1.getDateEditor().getUiComponent()).getText()+"'and Date<='"+((JTextField)pdate2.getDateEditor().getUiComponent()).getText()+"'";
            
            purchaseReportSql=sql;
            rs=st.executeQuery(sql);
           PurchaseReport_TB.setModel(DbUtils.resultSetToTableModel(rs));
            tabledata(PurchaseReport_TB,"",purchasevalue_TF,5);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
       
//         double sum=0;
//    int m=PurchaseReport_TB.getRowCount();
//    int n=PurchaseReport_TB.getColumnCount();
//    String[][] table=new String[m][n];
//    for (int i=0;i<m;i++){
//        for(int j=0;j<n;j++){
//        
//            table[i][j]=PurchaseReport_TB.getValueAt(i, j).toString();
//            System.out.println(PurchaseReport_TB.getValueAt(i, j).toString());
//        
//        }
//    }
//    for (int i=0;i<m;i++){
//        
//        String tb=table[i][6];
//       // System.out.println(tb);
//        double tbt=Double.parseDouble(tb);
//        sum=sum+tbt;
//        System.out.println(sum);
//        purchasevalue_TF.setText(String.format(st+"%.2f",sum));
//    }
      
    }//GEN-LAST:event_pdate_SearchActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        
         jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel27);
        jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
         jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        jPanel2.add(jPanel23);
        jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void pbarcode_TFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pbarcode_TFKeyPressed
        // TODO add your handling code here:
        autoId(jTextField1);
    }//GEN-LAST:event_pbarcode_TFKeyPressed

    private void pbarcode_TFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pbarcode_TFMouseClicked
        // TODO add your handling code here:
        autoBarcode(pbarcode_TF);
    }//GEN-LAST:event_pbarcode_TFMouseClicked

    private void pbarcode_TFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pbarcode_TFMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pbarcode_TFMouseEntered

    private void pquantity_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pquantity_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pquantity_TFActionPerformed

    private void pquantity_TFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pquantity_TFKeyTyped
        // TODO add your handling code here:
          char c=evt.getKeyChar();
        if(!(Character.isDigit(c)||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE))){
        getToolkit().beep();
        evt.consume();
        
        
        }
    }//GEN-LAST:event_pquantity_TFKeyTyped

    private void jLabel71MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel71MouseClicked
        // TODO add your handling code here:
        panaleShow(jPanel31);
        
    }//GEN-LAST:event_jLabel71MouseClicked

    private void btn_scanStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_scanStockMouseClicked
        // TODO add your handling code here:
        stock_LB.setText("");
        btn_lowStock.setBackground(new Color(87, 70, 120));
        btn_outStock.setBackground(new Color(87, 70, 120));
        stock_LB.setText(btn_scanStock.getText());
        btn_scanStock.setBackground(new Color(66, 48, 103));
        tableStock();
        
    }//GEN-LAST:event_btn_scanStockMouseClicked

    private void btn_lowStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_lowStockMouseClicked
        // TODO add your handling code here:
         stock_LB.setText("");
        btn_scanStock.setBackground(new Color(87, 70, 120));
        btn_outStock.setBackground(new Color(87, 70, 120));
        stock_LB.setText(btn_lowStock.getText());
        btn_lowStock.setBackground(new Color(66, 48, 103));
        
        
         String Sql="Select *from master where cast(qty as INT)<6";
       try {
           rs=st.executeQuery(Sql);
           jTable5.setModel(DbUtils.resultSetToTableModel(rs));
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
    }//GEN-LAST:event_btn_lowStockMouseClicked

    private void btn_outStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_outStockMouseClicked
        // TODO add your handling code here:
        
        stock_LB.setText("");
        btn_scanStock.setBackground(new Color(87, 70, 120));
        btn_lowStock.setBackground(new Color(87, 70, 120));
        stock_LB.setText(btn_outStock.getText());
        btn_outStock.setBackground(new Color(66, 48, 103));
        
        
         String Sql="Select *from master where cast(qty as INT)<1";
       try {
           rs=st.executeQuery(Sql);
           jTable5.setModel(DbUtils.resultSetToTableModel(rs));
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
    }//GEN-LAST:event_btn_outStockMouseClicked

    private void jLabel66MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MousePressed
        // TODO add your handling code here:
         try {
            
        
        String sql="select Product_id,Quantity from purchase_product where p_id='"+ppurchaseid_TF.getText()+"'";
        List<String>list=new ArrayList<String>();
        List<String>list2=new ArrayList<String>();
         rs=st.executeQuery(sql);
            
            while(rs.next()){
                list.add(rs.getString(1));
                list2.add(rs.getString("Quantity"));
                
            }
             for(int i = 0 ; i < list.size(); i++){
                 String dbquantity="";
                 String masterqty="select qty from master where ProductId='"+list.get(i)+"'";
                rs=st.executeQuery(masterqty);
                  while(rs.next()){
                
           
                dbquantity=(rs.getString("qty"));
                      System.out.println(dbquantity);
                
            }
                 String newqty=String.valueOf((Integer.valueOf(dbquantity))-(Integer.valueOf(list2.get(i))));
                 System.out.println(newqty);
        String sql1="update master set qty='"+newqty+"' where ProductId='"+list.get(i)+"'";
        st.executeUpdate(sql1);
    }
            
            } catch (Exception e) {
                System.out.println(e);
        }
        
        //Delete product when cancle.................................................................................
        String sql2="delete from purchase_product where p_id='"+ppurchaseid_TF.getText()+"'";
        try {
            st.executeUpdate(sql2);
            JOptionPane.showMessageDialog(null,"deleted....");
            RemoveTableRow(jTable3);
          
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Not deleted....");
            JOptionPane.showMessageDialog(null, ex);
        }
        //sale_cancel_clear();
        //table2();
        ptotal_TF.setText("0.0");
        psubtotal_TF.setText("0.0");
    
    }//GEN-LAST:event_jLabel66MousePressed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        panaleShow(jPanel31);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null, "Are you sure close it? "
                             + "lost Data","Warning",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
        try {
              if(jTable3.getRowCount()>0){
                  
                             
                                 canclePurchase();
                                 //jTable3.removeAll();
                                 
                                 //jPanel5.setVisible(false);
                             }   
                                   
                                      if(jTable2.getRowCount()>0){
                             
                                 cacleSales();
                                 //jTable3.removeAll();
                                 
                                 //jPanel5.setVisible(false);
                             }
                                      Refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyPressed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jTextField6KeyPressed

    private void svat_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_svat_TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_svat_TFActionPerformed

    private void sdiscount_TFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sdiscount_TFKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sdiscount_TFKeyPressed

    private void sdiscount_TFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sdiscount_TFKeyReleased
      
        
        try {
          double snetamountt=mynetamount;
            double sdis=(Double.valueOf(sdiscount_TF.getText()));
            
           double finalamt=(snetamountt-sdis);
            
            
            snetamount_TF.setText(String.valueOf(finalamt));
        } catch (Exception e) {
            snetamount_TF.setText(String.valueOf(mynetamount));
            // System.out.println("");
        } 
    }//GEN-LAST:event_sdiscount_TFKeyReleased

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
List<String> list=new ArrayList<String>();
            DefaultTableModel model=(DefaultTableModel) jTable6.getModel();
      
       list.add(jComboBox3.getSelectedItem().toString());
       list.add(Dcost_TF.getText());
       list.add(Dcost_Details_TF.getText());
//     
       

    
      model.addRow(list.toArray());
        try {
            String dsql="insert into others_cost(Cost_Type,Cost,Cost_Details,Date)"+"values"+"('"+jComboBox3.getSelectedItem()+"','"+Dcost_TF.getText()+"','"+Dcost_Details_TF.getText()+"','"+((JTextField)jDateChooser6.getDateEditor().getUiComponent()).getText()+"')";
            st.executeUpdate(dsql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
        }
        dclear();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        // TODO add your handling code here:
        panaleShow(jPanel34);
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
          try {
             daily_cash_TF.setText("0.0");
             Today_othercost_TF.setText("0.0");
            String sql="select sum(cast(Total_amount as double)) as 'Todays_Sales_Cash' from sales where Date='"+((JTextField)jDateChooser6.getDateEditor().getUiComponent()).getText()+"'and Payment_Type='Cash'";
            
           
            rs=st.executeQuery(sql);
          while(rs.next()){
              daily_cash_TF.setText(rs.getString("Todays_Sales_Cash"));
          
          }
             String sql2="select sum(cast(Cost as double)) as 'Todays_Oters_Cost' from others_cost where Date='"+((JTextField)jDateChooser6.getDateEditor().getUiComponent()).getText()+"'";
            
           
            rs=st.executeQuery(sql2);
          if(rs.next()){
              Today_othercost_TF.setText(rs.getString("Todays_Oters_Cost"));
            
          }
          
          
          
          
          
          try{
          double todaycash=(Double.parseDouble(daily_cash_TF.getText())-(Double.parseDouble(Today_othercost_TF.getText())));
          Today_Cash_TF.setText(String.valueOf(todaycash));
          }
          catch(Exception e){
              
              if(daily_cash_TF.getText().trim().isEmpty()){
                  
                  daily_cash_TF.setText("0.0");
              
              }
               if(Today_othercost_TF.getText().trim().isEmpty()){
                  
                  Today_othercost_TF.setText("0.0");
              
              }
              
             
            double todaycash=(Double.parseDouble(daily_cash_TF.getText())-(Double.parseDouble(Today_othercost_TF.getText())));
          Today_Cash_TF.setText(String.valueOf(todaycash));
          
          }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton12ActionPerformed
    
    
    public void dclear(){
        jComboBox3.setSelectedItem("Select One");
        Dcost_TF.setText("");
        Dcost_Details_TF.setText("");
    }
    
    
    public void Refresh(){
    this.setVisible(false);
        this.setVisible(true);
        sale_submit_clear();
        sale_cancel_clear();
        mainf_clear();
        
        RemoveTableRow(jTable2);
        RemoveTableRow(jTable3);

        Sales_value.setText("0.0");
        RemoveTableRow(jTable4);        
        
       purchasevalue_TF.setText("0.0");
       RemoveTableRow(PurchaseReport_TB);
       
       psubtotal_TF.setText("0.0");
       ptotal_TF.setText("0.0");
        
      jTextField8.setText("");
      spid_TF.setText("");
      spname_TF.setText("");
      ssprice_TF.setText("");
      sbuyprice_TF.setText("");
      sqty_TF.setText("");
      sdis_TF.setText("");
      jTextField26.setText("");
      scat_CB.setSelectedIndex(0);
        
        psupplier_TF.setText("");
        pscontact_TF.setText("");
        jTextField38.setText("");
        jTextField37.setText("");
        
        pbuyprice_TF.setText("");
        pcat_CB.setSelectedItem("Select one");
        psaleprice_TF.setText("");
        pbarcode_TF.setText("");
        pcat_CB.setSelectedItem("select");
        pquantity_TF.setText("");
        ppname_TF.setText("");}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Mainf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mainf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mainf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mainf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mainf().setVisible(true);
               new Mainf().setEnabled(true);
               
               
              
                
                
            }
        });
         
        
  
  

    }

    public void m_component(){
    pid=jTextField1.getText();
     pname=jTextField3.getText();
     prate=jTextField4.getText();
     srate=jTextField5.getText();
     qty=jTextField7.getText();
     bcode=jTextField2.getText();
    ctg=(String) jComboBox1.getSelectedItem();
    
    }
    
    public void tabledata(JTable tbl,String st,JTextComponent tx,int c){
    //tb1=table name for value we get,tx=textfield where we show the sum value,and c=column name where valu we add
    
    double sum=0;
    int m=tbl.getRowCount();
    int n=tbl.getColumnCount();
    String[][] table=new String[m][n];
    for (int i=0;i<m;i++){
        for(int j=0;j<n;j++){
        
            table[i][j]=tbl.getValueAt(i, j).toString();
          //  System.out.println(tbl.getValueAt(i, j).toString());
        
        }
    }
    for (int i=0;i<m;i++){
        
        String tb=table[i][c];
       // System.out.println(tb);
        double tbt=Double.parseDouble(tb);
        sum=sum+tbt;
      //  System.out.println(sum);
        tx.setText(String.format(st+"%.2f",sum));
        
        
    }
    
    }
    
    public void mainf_clear(){
        
        jTextField1.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField2.setText("");
        jTextField7.setText("");
        jDateChooser1.setDateFormatString("");
        jComboBox1.setSelectedIndex(0);
    }
    
    public void sale_submit_clear(){
    jTextField8.setText("");
    jTextField18.setText("");
    jTextField19.setText("");
    totalitem_TF.setText("Total Item 0");
    stotalquantity_TF.setText("Total Quantity 0");
    spaidcash_TF.setText("");
    subtotal_TF.setText("");
    svat_TF.setText("");
    sdiscount_TF.setText("");
    snetamount_TF.setText("0.0");
    sreturncash_TF.setText("0.0");
    jComboBox2.setSelectedItem("Select Payment Method");
    }
    
    public void sale_cancel_clear(){
         jTextField8.setText("");
    jTextField18.setText("");
    jTextField19.setText("");
    totalitem_TF.setText("Total Item 0");
    stotalquantity_TF.setText("Total Quantity 0");
    spaidcash_TF.setText("");
    subtotal_TF.setText("");
    svat_TF.setText("");
    sdiscount_TF.setText("");
    snetamount_TF.setText("0.0");
    sreturncash_TF.setText("0.0");
    jComboBox2.setSelectedItem("Select Payment Method");
    
    }
    public void RemoveTableRow(JTable table){
    
      DefaultTableModel model=(DefaultTableModel) table.getModel();
            
            int row=table.getRowCount();
            
            
            for(int i = row - 1; i >= 0; i--){
               model.removeRow(i);
            }
            
    
    }
    public void table2(){
           String Sql="Select *from sales";
       try {
           rs=st.executeQuery(Sql);
           jTable1.setModel(DbUtils.resultSetToTableModel(rs));
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
    }
    
      public void table1(){
       
       String Sql="Select *from master";
       try {
           rs=st.executeQuery(Sql);
           jTable1.setModel(DbUtils.resultSetToTableModel(rs));
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
   
   }
         public void tableStock(){
       
       String Sql="Select *from master order by  cast(qty as INT)";
       try {
           rs=st.executeQuery(Sql);
           jTable5.setModel(DbUtils.resultSetToTableModel(rs));
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
   
   }
      
      
       public void autoId(JTextField textfield){
   Statement st2;
   
            String src3="select  MAX(ProductId) AS no from master";
            
        try {
            st2=connection.createStatement();
            
            rs=st2.executeQuery(src3);
            
             while(rs.next()){
            
                 //System.out.println(rs.getString(1));
                 
               int idd=Integer.parseInt(rs.getString(1));
               int lid=idd+1;
                 //System.out.println(lid);
                 textfield.setText("0"+String.valueOf(lid));
           
             
            }
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
 
   }
       
       public void autoBarcode(JTextField textfield){
       
           Statement st2;
   
            String src3="select  MAX(cast(Barcode as INT)) AS no from barcode";
            
        try {
            st2=connection.createStatement();
            
            rs=st2.executeQuery(src3);
            
             while(rs.next()){
            
                 //System.out.println(rs.getString(1));
                 
               int idd=Integer.parseInt(rs.getString(1));
               int lid=idd+1;
                 //System.out.println(lid);
                 textfield.setText("0"+String.valueOf(lid));
           
             
            }
           
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
       }
       public void autoPurchaseId(JTextField textfield){
       
        Statement st2;
   
            String src3="select  MAX(Purchase_id) AS no from purchase";
            
        try {
            st2=connection.createStatement();
            
            rs=st2.executeQuery(src3);
            
             while(rs.next()){
            
                 //System.out.println(rs.getString(1));
                 
               int idd=Integer.parseInt(rs.getString(1));
               int lid=idd+1;
                 //System.out.println(lid);
                 textfield.setText("0"+String.valueOf(lid));
           
             
            }
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
       } 
        
        
        public void autoSalesId(JTextField textfield){
       
        Statement st2;
   
            String src3="select  MAX(Sales_Id) AS no from sales";
            
        try {
            st2=connection.createStatement();
            
            rs=st2.executeQuery(src3);
            
             while(rs.next()){
            
                 //System.out.println(rs.getString(1));
                 
               int idd=Integer.parseInt(rs.getString(1));
               int lid=idd+1;
                 //System.out.println(lid);
                 textfield.setText("0"+String.valueOf(lid));
           
             
            }
            
            
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
       
       
       
        }
      
      public void itemauto(JTextComponent tx,String tableName,int columNumber) {
   
       
        try {
            ArrayList<Object> list = new ArrayList<Object>();
            
            Vector<String> tableRoomsData = new Vector<String>();
            
            JList<String> list2 = new JList<>();
                
                list2.setBounds(100,100, 75,75);
           
            rs = st.executeQuery("SELECT * FROM "+tableName);
           
            while (rs.next()) {
                
                list.add(rs.getString(columNumber));
                
                
               
            }
            
            
             TextAutoCompleter texta=new TextAutoCompleter(tx);
             
            texta.addItems(list);
           
        } catch (SQLException ex) {
            Logger.getLogger(Mainf.class.getName()).log(Level.SEVERE, null, ex);
        }
    
   }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Dcost_Details_TF;
    private javax.swing.JTextField Dcost_TF;
    private javax.swing.JTable PurchaseReport_TB;
    private javax.swing.JLabel Sale_cancel;
    private javax.swing.JTextField Sales_value;
    private javax.swing.JTextField Today_Cash_TF;
    private javax.swing.JTextField Today_othercost_TF;
    private javax.swing.JLabel btn_lowStock;
    private javax.swing.JLabel btn_outStock;
    private javax.swing.JLabel btn_scanStock;
    private javax.swing.JTextField daily_cash_TF;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField pbarcode_TF;
    private javax.swing.JTextField pbuyprice_TF;
    private javax.swing.JComboBox<String> pcat_CB;
    private com.toedter.calendar.JDateChooser pdate1;
    private com.toedter.calendar.JDateChooser pdate2;
    private javax.swing.JButton pdate_Search;
    private javax.swing.JTextField pdiscount_TF;
    private javax.swing.JTextField ppname_TF;
    private javax.swing.JTextField pproduct_id;
    private javax.swing.JTextField ppurchaseid_TF;
    private javax.swing.JTextField pquantity_TF;
    private javax.swing.JTextField psaleprice_TF;
    private javax.swing.JTextField pscontact_TF;
    private javax.swing.JTextField psubtotal_TF;
    private javax.swing.JTextField psupplier_TF;
    private javax.swing.JTextField ptotal_TF;
    private javax.swing.JTextField purchasevalue_TF;
    private javax.swing.JTextField pvat_TF;
    private javax.swing.JTextField salesid_TF;
    private javax.swing.JTextField sbuyprice_TF;
    private javax.swing.JComboBox<String> scat_CB;
    private javax.swing.JTextField sdis_TF;
    private javax.swing.JTextField sdiscount_TF;
    private javax.swing.JTextField snetamount_TF;
    private javax.swing.JTextField spaidcash_TF;
    private javax.swing.JTextField spid_TF;
    private javax.swing.JTextField spname_TF;
    private javax.swing.JTextField sqty_TF;
    private javax.swing.JTextField sreturncash_TF;
    private javax.swing.JTextField ssprice_TF;
    private javax.swing.JLabel stock_LB;
    private javax.swing.JTextField stotalquantity_TF;
    private javax.swing.JTextField subtotal_TF;
    private javax.swing.JTextField svat_TF;
    private javax.swing.JTextField totalitem_TF;
    // End of variables declaration//GEN-END:variables
}
