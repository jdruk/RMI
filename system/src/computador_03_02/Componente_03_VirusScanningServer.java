package computador_03_02;


import computador_03_01.*;
import viruscanningpackage.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author Airton 
 * 07/03/2018
 */
public class Componente_03_VirusScanningServer{
	

	private String[] signatureDB;
	private static int SIGNATURE_SIZE = 1348;
	private static String VIRUS_FOLDER_TO_SCAN = "virusFolderToScan/";
        private Componente_02_VirusSignaturesCreator signatures;

        
       Componente_03_VirusScanningServer(String[] sign, String CaminhoScanning, int Tamanho){
            signatureDB = sign;
            VIRUS_FOLDER_TO_SCAN = VIRUS_FOLDER_TO_SCAN = CaminhoScanning;
            SIGNATURE_SIZE = SIGNATURE_SIZE = Tamanho;   
        }

        
	private static void cleanDirContent(){
		File folder = new File(VIRUS_FOLDER_TO_SCAN);  
		if (folder.isDirectory()) {  
		    File[] sun = folder.listFiles();  
		    for (File toDelete : sun) {  
		        toDelete.delete();  
		    }  
		}  
	}

	public int localScanFolder() {


		int nrVirusesFound = 0;
		int cloneId = -1;

		System.out.println("Scanning folder: " + VIRUS_FOLDER_TO_SCAN);
		File folderToScan = new File(VIRUS_FOLDER_TO_SCAN);
		File[] filesToScan = folderToScan.listFiles();

		// int howManyFiles = (int) ( filesToScan.length / nrClones ); //
		// Integer division, some files may be not considered
		int howManyFiles = (int) (filesToScan.length); // Integer division,
															// some files may be
															// not considered
		int start = (cloneId + 1) * howManyFiles; // Since cloneId starts from
													// -1 (the main clone)
		int end = start + howManyFiles;

		// If this is the clone with the highest id let him take care
		// of the files not considered due to the integer division.

		System.out.println("Nr signatures: " + signatureDB.length
				+ ". Nr files to scan: " + howManyFiles);
		System.out.println("Checking files: " + start + "-" + end);

		for (int i = start; i < end; i++) {
//			 System.out.println( "Checking file: " + i);
			if (heavyCheckIfFileVirus(filesToScan[i])) {
				// if (checkIfFileVirus(filesToScan[i])) {
				// System.out.println( "Virus found");
				nrVirusesFound++;
			}
		}

		return nrVirusesFound;
	}

	/**
	 * When having more than one clone running the method there will be partial
	 * results which should be combined to get the total result. This will be
	 * done automatically by the main clone by calling this method.
	 * 
	 * @param params
	 *            Array of partial results.
	 * @return The total result.
	 */
	public int localScanFolderReduce(int[] params) {
		int nrViruses = 0;
		for (int i = 0; i < params.length; i++) {
			nrViruses += params[i];
		}
		return nrViruses;
	}
	
	private boolean heavyCheckIfFileVirus(File virus) {
	MessageDigest md;

	try {
		md = MessageDigest.getInstance("SHA-1");

		int length = (int) virus.length();

		char[] buffer = new char[length];
//		Log.i(TAG, "Checking file " + virus.getName());
//		Log.i(TAG, "Length of file " + length);
		FileReader currentFile = new FileReader(virus);
		int totalRead =	 0;
		int read = 0;
		do {
			totalRead += read;
			read = currentFile.read(buffer, totalRead, length - totalRead);
		} while ( read > 0 );
		currentFile.close();
		if (totalRead > 0) {
			for (int i = 0; i < (length - SIGNATURE_SIZE); i++) {
				char[] tempBuff = new char[SIGNATURE_SIZE];
				System.arraycopy(buffer, i, tempBuff, 0, SIGNATURE_SIZE);
				String signature = new String(tempBuff);
				signature = signatures.byteArrayToHexString(md.digest(signature.getBytes()));
				if ( isInVirusDB(signature) )
					return  true;
			}
		}
		currentFile.close();
	} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return false;
}



	private boolean isInVirusDB(String signature) {
		for (int i = 0; i < signatureDB.length; i++) {
			// System.out.println("vir signature " + signature);
			// System.out.println("signatureDB[i] " + signatureDB[i]);
			if (signature.equals(signatureDB[i]))
				return true;
		}
		return false;
	}
}
