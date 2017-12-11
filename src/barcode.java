
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    public barcode(String code,String price) throws DocumentException, FileNotFoundException{
     float left = 5;
        float right = 5;
        float top = 5;
        float bottom = 5;
    Document document = new Document(PageSize.A10, left, right, top, bottom);    
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src\\Barcode\\Java4s_BarCode_128.pdf"));    
   
    document.open();
    document.setMargins(100, 100, 0, 0);
	    document.add(new Paragraph("welcome #"));
 
		    Barcode128 code128 = new Barcode128();
		    code128.setGenerateChecksum(true);
		    code128.setCode(code);    
                            document.add(new Paragraph("Price :-"+price+" TK"));
	    document.add(code128.createImageWithBarcode(writer.getDirectContent(), null, null));
                
             
            document.close();
    
 
    //System.out.println("Document Generated...!!!!!!");
  }
}
