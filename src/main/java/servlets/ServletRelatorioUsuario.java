package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import Util.ReportUtil;
import dao.DAOConsultaUsuario;
import dao.DAOGrafico;
import dto.GraficoMediaSalarioDTO;

@MultipartConfig
@WebServlet("/ServletRelatorioUsuario")
public class ServletRelatorioUsuario extends ServLetGenericUtilsEntendendoServlet {
	private static final long serialVersionUID = 1L;
	String dataFinal = null;
	String dataInicial = null;
	SimpleDateFormat formatDataInicial = null;
	SimpleDateFormat formatDataFinal = null;
	Date dataFinalDate=null;
	Date dataInicialDate=null;
	String msg=null;
	ModelLogin modelLogin = null;
	List<ModelLogin> modelLoings = null;
	DAOConsultaUsuario consultaUsuario = new DAOConsultaUsuario();
	DAOGrafico daoGrafico = new DAOGrafico();
	public ServletRelatorioUsuario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		dataFinal = request.getParameter("dataFinal");
		dataInicial= request.getParameter("dataInicial");
		formatDataFinal = new SimpleDateFormat("yyyy-MM-dd");
		formatDataInicial = new SimpleDateFormat("yyyy-MM-dd");
		String acao = request.getParameter("acao");
		
		try {		
			if("grafico".equalsIgnoreCase(acao)) {
				List<GraficoMediaSalarioDTO> graficoDTO = null;
				graficoDTO = daoGrafico.dadosDoGrafico(super.getUsuarioLogado(request));
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(graficoDTO);
				response.getWriter().write(json);
				return;
			}
			if(dataInicial!=null && !dataInicial.isEmpty()) {
				
				dataInicialDate = formatDataInicial.parse(dataInicial);
			}
			if(dataFinal!=null && !dataFinal.isEmpty()) {
				
				dataFinalDate = formatDataFinal.parse(dataFinal);
			}
			if((dataInicial == null || dataInicial.isEmpty())&& (dataFinal== null || dataFinal.isEmpty())) {
				
				modelLoings = consultaUsuario.consultaUsariosTodos(super.getUsuarioLogado(request));
				if(acao!=null && !acao.isEmpty()&& "imprimirPDF".equalsIgnoreCase(acao)) {
					HashMap<String, Object> paramentros = new HashMap<>();
					paramentros.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio")+File.separator);
					byte[] relatorio = new ReportUtil().gerarRelatórioPDF(modelLoings, "relatorio_usuarios",paramentros, getServletContext());
					response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
					response.getOutputStream().write(relatorio);
					return;
				}
				else if("imprimirECXEL".equalsIgnoreCase(acao)&&acao!=null && !acao.isEmpty()) {
					HashMap<String, Object> paramentros = new HashMap<>();
					paramentros.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio")+File.separator);
					byte[] relatorio = new ReportUtil().gerarRelatórioECXEL(modelLoings, "relatorio_usuarios",paramentros, getServletContext());
					response.setHeader("Content-Disposition", "attachment;filename=arquivo.xls");
					response.getOutputStream().write(relatorio);
					return;
				}
			}
			else if(dataInicialDate!=null && dataFinalDate!=null && dataFinalDate.getTime()>dataInicialDate.getTime()){
				modelLoings = consultaUsuario.consultaUsariosTodosFiltroData(super.getUsuarioLogado(request), dataInicialDate, dataFinalDate);
				if(acao!=null && !acao.isEmpty()&& "imprimirPDF".equalsIgnoreCase(acao)) {
					HashMap<String, Object> paramentros = new HashMap<>();
					paramentros.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio")+File.separator);
					byte[] relatorio = new ReportUtil().gerarRelatórioPDF(modelLoings, "relatorio_usuarios",paramentros, getServletContext());
					response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
					response.getOutputStream().write(relatorio);
					return;
				}
				else if("imprimirECXEL".equalsIgnoreCase(acao)&&acao!=null && !acao.isEmpty()) {
					HashMap<String, Object> paramentros = new HashMap<>();
					paramentros.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio")+File.separator);
					byte[] relatorio = new ReportUtil().gerarRelatórioECXEL(modelLoings, "relatorio_usuarios",paramentros, getServletContext());
					response.setHeader("Content-Disposition", "attachment;filename=arquivo.xls");
					response.getOutputStream().write(relatorio);
					return;
				}
			}
			else {
				msg = "datas incorretas";
			}
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/relatorioUsuarios.jsp");
			request.setAttribute("listaDeUsuarios", modelLoings);
			request.setAttribute("msg", msg);
			redirecionador.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			String erro = e.getMessage();
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/erro.jsp");
			request.setAttribute("msg", erro);
			redirecionador.forward(request, response);
		}
	}

}
