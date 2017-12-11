
import com.toedter.calendar.JDateChooser;
import javax.swing.JTable;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class Invoice_Make {
    public void saleprint(JTable tables,JTextField billNO,JTextField totalprice,JTextField vat,JTextField discount,JTextField netamount,JDateChooser date){
        
   String table_data_print = "Product Name\tSale Price\tQuantity\t      \tTotal Price\n_____________________________________________________________________";
        String tb;
           int m=tables.getRowCount();
        int n=tables.getColumnCount();
        String table[][]= new String[m][n];
        for (int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                        

            table[i][j]=tables.getValueAt(i, j).toString();
            
            
          
            
            }
          
        }
        
        
      for (int i=0;i<m;i++){
            
                table_data_print= table_data_print+"\n"+table[i][0]+"\t"+table[i][1]+"\t"+table[i][2]+"\t\t"+table[i][3]+"\t"+"\n";
               // System.out.println(table_data_print);
      }
          String inv="\t\tBill No :"+billNO.getText()+"\n Date :"+date.getDate()+"\n_____________________________________________________________________"+"\n"+table_data_print+"\n"+"_____________________________________________________________________"+
                  "\n"+"\t"+"\t"+"\t  "+"Sub Total   =     "+totalprice.getText()+" TK"+"\n"+
                  "\n"+"\t"+"\t"+"\t  "+"Vat Include =     "+vat.getText()+"%"+
                  "\n"+"\t"+"\t"+"\t  "+"Discount    =     "+discount.getText()+
                  "\n"+"\t"+"\t"+"\t  "+"Total Price =     "+netamount.getText()+
                  
                  " TK"+"\n"+"copy Right By Sec :B(Eve)";
      
        new invoice(inv).setVisible(true);
       
    } 
}
