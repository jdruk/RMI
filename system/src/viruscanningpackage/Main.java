package viruscanningpackage;

public class Main {

	public static void main(String[] args) {
		// Aqui voc� deve chamar os tr�s componentes em sequ�ncia.

		String CaminhoSignature = "C:\\pc2";
		int tamanho = 1258;
		String CaminhoScarnning = "C:\\VirusScanning\\virusFolderToScan/";
		String destino = "C:\\pc2/virus.zip";

		Componente_01_Descompactador descompactador = new Componente_01_Descompactador(destino, CaminhoSignature);
		Componente_02_VirusSignaturesCreator signatures = new Componente_02_VirusSignaturesCreator(CaminhoSignature,
				tamanho);
		Componente_03_VirusScanningServer virusScanner = new Componente_03_VirusScanningServer(
				signatures.getSignatureDB(), CaminhoScarnning, tamanho);
		long startTime = System.currentTimeMillis();
		int result = virusScanner.localScanFolder();
		System.out.println("Number of viruses found: " + result);
		System.out.println("Time: " + (System.currentTimeMillis() - startTime) + " ms");

	}

}
