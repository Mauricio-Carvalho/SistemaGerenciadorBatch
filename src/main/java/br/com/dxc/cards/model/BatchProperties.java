package br.com.dxc.cards.model;

public class BatchProperties {

	private static String ambiente;
	private static String outputCmsap;
	private static String outputCfg;
	private static String outputCmsacqr;
	private static String outputCmsissr;

	public static String getAmbiente() {
		return ambiente;
	}

	public static void setAmbiente(String ambiente) {
		BatchProperties.ambiente = ambiente;
	}

	public static String getOutputCmsap() {
		return outputCmsap;
	}

	public static void setOutputCmsap(String outputCmsap) {
		BatchProperties.outputCmsap = outputCmsap;
	}

	public static String getOutputCfg() {
		return outputCfg;
	}

	public static void setOutputCfg(String outputCfg) {
		BatchProperties.outputCfg = outputCfg;
	}

	public static String getOutputCmsacqr() {
		return outputCmsacqr;
	}

	public static void setOutputCmsacqr(String outputCmsacqr) {
		BatchProperties.outputCmsacqr = outputCmsacqr;
	}

	public static String getOutputCmsissr() {
		return outputCmsissr;
	}

	public static void setOutputCmsissr(String outputCmsissr) {
		BatchProperties.outputCmsissr = outputCmsissr;
	}
}
