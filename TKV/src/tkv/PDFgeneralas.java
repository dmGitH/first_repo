package tkv;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;

public class PDFgeneralas {
    
    PDFgeneralas() {
        
    }

    //<editor-fold defaultstate="collapsed" desc="PDF generálás nem teljes az itext 5.5.5.jar kellene hozzá">
    public void pdfgenerator(String fajlnev, ObservableList<Ember> adat) {
        Document mentes = new Document();
        try {
            PdfWriter.getInstance(mentes, new FileOutputStream(fajlnev + ".pdf"));
            mentes.open();
            Image image1 = Image.getInstance(getClass().getResource("/a.png"));
            mentes.add(image1);
//            image1.scaleToFit(320,50);
//            image1.setAbsolutePosition(200f, 750f);
            mentes.addTitle("Generált dokumentum");
            mentes.addAuthor("TKV JavaFx alkalmazás");
            mentes.addCreationDate();
            mentes.addSubject("Név és email címek listája");
            mentes.addCreator("pdf generator");
            mentes.addKeywords("címlista");
            mentes.add(new Paragraph("\n-----------------------------------------------   Név és  email-címlista    ------------------------------------------------ \n"
                    + "----------------------------------------------------------------------------------------------------------------------------------\n", FontFactory.getFont("Betütípus", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
            String tmp = null;
            String tmp2 = null;
            if (adat.size() > 0) {
                for (int i = 0; i < adat.size(); i++) {
                    tmp2 = String.valueOf(i + 1) + ". >>     " + adat.get(i).getVezeteknev() + " " + adat.get(i).getKeresztnev();
                    int a = tmp2.length();
                    if (a < 80) {
                        for (int x = a; x < 80; x++) {
                            tmp2 += " ";
                        }
                    }
                    tmp = tmp2 + "email: " + adat.get(i).getEmailcim();
                    mentes.add(new Paragraph(tmp, FontFactory.getFont("Betütípus", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
                    
                }
            }
            Chunk alairas = new Chunk("\n\n Generálva a címlista alkalmazással. ");
            Paragraph base = new Paragraph(alairas);
            mentes.add(base);
            
        } catch (Exception e) {
            
        }
        mentes.close();
    }
//</editor-fold>

    public PDFgeneralas(String fajlnev, String tesztszoveg) {
        
    }
    
}
