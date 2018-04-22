package computador_03_01;

import computador_02.*;
import viruscanningpackage.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Componente_01_Descompactador {

	//Aqui voc� deve colocar um c�digo de descompacta��o de arquivos onde dever� descompactar um zip que conter� os v�rus dentro do diret�rio virusDB.
	//N�o esque�a de apagar o zip de dentro da pasta virusDB   
    
    	//public static void main(String[] args) {
    	//public static void main(String[] args) {
        
   String OUTPUT_FOLDER;
   String FILE_PATH;
    
    
   public Componente_01_Descompactador(String origem, String destino){
       
        OUTPUT_FOLDER= origem;
        FILE_PATH = destino;
        
        OUTPUT_FOLDER = "C:\\pc3";
        FILE_PATH = "C:\\pc3/virus.zip";
        
        // Create Output folder if it does not exists.
        File folder = new File(OUTPUT_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // Create a buffer.
        byte[] buffer = new byte[1024];
        
        ZipInputStream zipIs = null;
        try {
            // Create ZipInputStream object to read a file from path.
            zipIs = new ZipInputStream(new FileInputStream(FILE_PATH));
            
            ZipEntry entry = null;
            // Read ever Entry (From top to bottom until the end)
            while ((entry = zipIs.getNextEntry()) != null) {
                String entryName = entry.getName();
                String outFileName = OUTPUT_FOLDER + File.separator + entryName;
                System.out.println("Unzip: " + outFileName);
                
                if (entry.isDirectory()) {
                    // Make directories.
                    new File(outFileName).mkdirs();
                } else {
                    // Create Stream to write file.
                    FileOutputStream fos = new FileOutputStream(outFileName);
                    
                    int len;
                    // Read the data on the current entry.
                    while ((len = zipIs.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    
                    fos.close();
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                zipIs.close();
            } catch (Exception e) {
            }
        }
    }
    
}
