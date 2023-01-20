package Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class ReportUtil implements Serializable {

	private static final long serialVersionUID = -2900526389346074616L;
	
	public byte[] gerarRelatórioPDF(List listaDeDados, String nomeDoRelatorio, ServletContext servletContext)
			throws Exception {
		//cria a coleção de dados
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDeDados);
		String caminhoJasper = servletContext.getRealPath("relatorio")+File.separator + nomeDoRelatorio+".jasper";
		JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoJasper, null,dataSource);
		
		return JasperExportManager.exportReportToPdf(jasperPrint);

	}
	
	public byte[] gerarRelatórioPDF(List listaDeDados,String  nomeDoRelatorio, HashMap<String, Object> parametros, ServletContext servletContext)
			throws Exception {
		//cria a coleção de dados
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDeDados);
		String caminhoJasper = servletContext.getRealPath("relatorio")+File.separator + nomeDoRelatorio+".jasper";
		JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoJasper, parametros,dataSource);
		
		return JasperExportManager.exportReportToPdf(jasperPrint);

	}
	
	public byte[] gerarRelatórioECXEL(List listaDeDados,String  nomeDoRelatorio, HashMap<String, Object> parametros, ServletContext servletContext)
			throws Exception {
		//cria a coleção de dados
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDeDados);
		String caminhoJasper = servletContext.getRealPath("relatorio")+File.separator + nomeDoRelatorio+".jasper";
		JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoJasper, parametros,dataSource);
		
		JRExporter exporter = new JRXlsExporter();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.exportReport();
		return baos.toByteArray();

	}

}
