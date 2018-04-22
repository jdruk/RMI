package computador_03_01;

import viruscanningpackage.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Componente_02_VirusSignaturesCreator {

	//Aqui voc� deve extrair da classe  Componente_03_VirusScanningServer.java a parte que se refere � cria��o de assinaturas.
    
    	private String[] signatureDB;
	private int SIGNATURE_SIZE = 1348;
	private String VIRUS_DB_PATH = "virusDB";

     Componente_02_VirusSignaturesCreator(String CaminhoSignature,int Tamanho) {
        
        VIRUS_DB_PATH = CaminhoSignature;
	SIGNATURE_SIZE = Tamanho;
        initSignatureDB(VIRUS_DB_PATH );
        
    }


    private void initSignatureDB(String pathToSignatures) {
        
        System.out.println("Started signature initialization on folder: "
				+ VIRUS_DB_PATH);
		System.out.println("Finished signature initialization");
        
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			File signatureFolder = new File(pathToSignatures);
			File[] demoViruses = signatureFolder.listFiles();

			signatureDB = new String[demoViruses.length];
			char[] buffer = new char[SIGNATURE_SIZE];

			int i = 0;
			for (File virus : demoViruses) {

				FileReader signatureFile = new FileReader(virus);
				int totalRead = 0;
				int read = signatureFile.read(buffer, totalRead, buffer.length - totalRead);
				signatureDB[i++] = byteArrayToHexString(md.digest(new String(
						buffer).getBytes()));
				signatureFile.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
		}
	}
     public static String byteArrayToHexString(byte[] b) {
         
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
 
     public String[] getSignatureDB() {
        return signatureDB;
    }
}
    
