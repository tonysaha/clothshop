
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfWriter;
import com.onbarcode.barcode.Code128;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageWriter;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class barcode {
    public barcode(String code) throws DocumentException, FileNotFoundException{
//     float left = 5;
//        float right = 5;
//        float top = 5;
//        float bottom = 5;
//    Document document = new Document(PageSize.A10, left, right, top, bottom);    
//    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src\\Barcode\\Java4s_BarCode_128.pdf"));    
//   
//    document.open();
//    document.setMargins(100, 100, 0, 0);
//	    document.add(new Paragraph("welcome #"));
// 
//		    Barcode128 code128 = new Barcode128();
//		    code128.setGenerateChecksum(true);
//		    code128.setCode(code);    
//                            document.add(new Paragraph("Price :-"+price+" TK"));
//	    document.add(code128.createImageWithBarcode(writer.getDirectContent(), null, null));
//                
//             
//            document.close();
    
 
    //System.out.println("Document Generated...!!!!!!");
    
    
    
    
    
    
    
    
    
      try {
            Code128 barcode=new Code128();
           
            
            String bardata;
           
            barcode.setData(code);
           
           barcode.setX(2);
   barcode.setY(60);
   barcode.setAutoResize(false);
   barcode.setShowText(true);

   //Ajust Code 128 barcode background color, bar color and text color
   barcode.setForeColor(Color.red);
  
    //Document document = new Document(PageSize.A1, left, right, top, bottom); 
            
           
           barcode.drawBarcode(".\\src\\Barcode\\"+"barcode.png");
           
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(Barcode.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  }
}
