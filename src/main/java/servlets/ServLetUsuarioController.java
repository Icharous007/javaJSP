package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import dao.DAOCadastroUsuario;
import dao.DAOConsultaUsuario;
import dao.DAOValidadorDeLogin;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;

@MultipartConfig
@WebServlet(urlPatterns = {"/ServLetUsuarioController"})
public class ServLetUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOCadastroUsuario cadastroUsuario = new DAOCadastroUsuario();
	private DAOValidadorDeLogin daoValidadorDeLogin = new DAOValidadorDeLogin();
	private DAOConsultaUsuario consultaUsuario = new DAOConsultaUsuario();
	private ServLetGenericUtils genericUtils = new ServLetGenericUtils();

	public ServLetUsuarioController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String msg =null;
		ModelLogin modelLogin = null;
		Integer totalDePaginas =0;
		List<ModelLogin> listaDeUsuariosCadastrados = null;
		if(acao!=null && !acao.isEmpty() && !acao.isBlank() && "listarUsuarios".equalsIgnoreCase(acao)) {
			try {
				Long usuarioPai = genericUtils.getUsuarioLogado(request);

				listaDeUsuariosCadastrados= consultaUsuario.consultaUsariosTodos(usuarioPai);
				Double totalDeRgistros = consultaUsuario.consultaTotalDeRegistros(usuarioPai);
				if(totalDeRgistros>0) {
					totalDePaginas = genericUtils.totalDePaginas(totalDeRgistros);				
				}
			} catch (Exception e) {
				e.printStackTrace();
				String erro = e.getMessage();
				RequestDispatcher redirecionador = request.getRequestDispatcher("principal/erro.jsp");
				request.setAttribute("msg", erro);
				redirecionador.forward(request, response);
			}
			
		}
		request.setAttribute("totalDePaginas", totalDePaginas);
		request.setAttribute("listaDeUsuariosCadastrados", listaDeUsuariosCadastrados);
		request.setAttribute("msg", msg);
		RequestDispatcher redirecionador = request.getRequestDispatcher("principal/cadastro.jsp");
		redirecionador.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("idDoUsuario");
		String nome = request.getParameter("nomeDoUsuario");
		String login = request.getParameter("loginDoUsuario");
		String email = request.getParameter("emailDoUsuario");
		String senha = request.getParameter("senhaDoUsuario");
		String perfil = request.getParameter("perfilDoUsuario");
		String sexo = request.getParameter("sexoDoUsuario");
		String cep = request.getParameter("CEPDoUsuario");
		String cidade = request.getParameter("cidadeDoUsuario");
		String endereco = request.getParameter("enderecoDoUsuario");
		String sDataDenascimento = request.getParameter("dataDeNascimentoUsuario");
		String rendaDousuario = request.getParameter("rendaDoUsuario");
		rendaDousuario = rendaDousuario.replace(",", ".");
		Double renda = Double.parseDouble(rendaDousuario);
		String msg= null;
		String fotoDoUsuarioEmBase64 = null;
		String sufixoDaFotoDoUsuario = null;
		final String atributoModelLogin = "modelLogin";
		ModelLogin modelLogin = new ModelLogin();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		modelLogin.setLogin(login);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setSenha(senha);
		modelLogin.setPerfil_usuario(perfil);
		modelLogin.setSexo(sexo);
		modelLogin.setCEP(cep);
		modelLogin.setCidade(cidade);
		modelLogin.setEndereco(endereco);
		modelLogin.setUserAdmin("Administrador".equalsIgnoreCase(perfil)? true : false);
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelLogin.setRenda(renda);
		
		List<ModelLogin> listaDeUsuariosCadastrados = null;
		Long usuarioPai ;
		Integer totalDePaginas = 0;
		//pega foto do request.
		if(ServletFileUpload.isMultipartContent(request)) {
			Part part = request.getPart("arquivoFotoDoUsuario");
			if(part.getSize()>0) {
								
				byte[] fotoDoUsuario = IOUtils.toByteArray(part.getInputStream());
				//new Base64();
				fotoDoUsuarioEmBase64 = "data:" + part.getContentType() + ";base64,"
						+ new Base64().encodeBase64String(fotoDoUsuario) ;
				sufixoDaFotoDoUsuario = part.getContentType().split("\\/")[1];
			}
		}
		
		modelLogin.setFoto_do_usuario(fotoDoUsuarioEmBase64);
		modelLogin.setSufixo_da_foto_do_usuario(sufixoDaFotoDoUsuario);
		
		try {
			
			Date dataDenascimento = simpleDateFormat.parse(sDataDenascimento);
			modelLogin.setDataDeNascimento(dataDenascimento);
			usuarioPai =  genericUtils.getUsuarioLogado(request);
			if (daoValidadorDeLogin.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {

				msg = "Login já existe na base de dados escolha outro por favor.";

			} else {
				if (modelLogin.IsNew()) {
					if(modelLogin.getFoto_do_usuario() != null && modelLogin.getSufixo_da_foto_do_usuario() != null 
							&& !modelLogin.getFoto_do_usuario().isEmpty() && !modelLogin.getSufixo_da_foto_do_usuario().isEmpty()) {
						modelLogin = cadastroUsuario.cadastrarUsuarioComFoto(modelLogin, usuarioPai);
						msg = "Cadastro Realizado com sucesso.";
					}else {
						
						modelLogin = cadastroUsuario.cadastrarUsuario(modelLogin,usuarioPai);
					}

				} else {
					msg = "Para Alterar os Dados do Usuario utilize o Botão ATUALIZAR!.";
					
				}
			}
			
			listaDeUsuariosCadastrados= consultaUsuario.consultaUsariosTodos(usuarioPai);
			Double totalDeRgistros = consultaUsuario.consultaTotalDeRegistros(usuarioPai);
			if(totalDeRgistros>0) {
				totalDePaginas = genericUtils.totalDePaginas(totalDeRgistros);				
			}
			request.setAttribute("totalDePaginas", totalDePaginas);
			request.setAttribute("listaDeUsuariosCadastrados", listaDeUsuariosCadastrados);
			request.setAttribute(atributoModelLogin, modelLogin);
			request.setAttribute("msg", msg);
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/cadastro.jsp");
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
