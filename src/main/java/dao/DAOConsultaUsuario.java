package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import conexao.ConexaoUnica;
import model.ModelLogin;

public class DAOConsultaUsuario implements Serializable {

	private static final long serialVersionUID = -5970524841866713709L;
	private Connection conexao = null;

	public DAOConsultaUsuario() {
		conexao = ConexaoUnica.getConexao();
	}

	public Double consultaTotalDeRegistros(Long usurioPai)throws Exception{
		Double totalDeRegistros= -1.0;
		String sql = " SELECT COUNT(1) as totalDeRegistros FROM public.usuarios WHERE usurario_pai = ?";
		PreparedStatement preparedStatement= null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setLong(1, usurioPai);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				totalDeRegistros = resultSet.getDouble(1);
			}
			
		} finally {
			
			if (preparedStatement != null)
				preparedStatement.close();
			if (resultSet != null)
				resultSet.close();
		}
		
		return totalDeRegistros;	
	}
	
	public Double consultaTotalDeRegistrosPorNomeAJAX(String nome,Long usurioPai)throws Exception{
		Double totalDeRegistros= -1.0;
		String sql = " SELECT COUNT(1) as totalDeRegistros FROM public.usuarios WHERE UPPER(nome) LIKE UPPER(?) and usurario_pai = ?;";
		PreparedStatement preparedStatement= null;
		ResultSet resultSet = null;
		nome = "%" + nome + "%";
		
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, nome);
			preparedStatement.setLong(2, usurioPai);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				totalDeRegistros = resultSet.getDouble(1);
			}
			
		} finally {
			
			if (preparedStatement != null)
				preparedStatement.close();
			if (resultSet != null)
				resultSet.close();
		}
		
		return totalDeRegistros;	
	}
	
	public List<ModelLogin> consultaUsuariosPorNome_ajax(String nome, Long usuarioPai) throws Exception {

		List<ModelLogin> listaDeUsuariosRetornada = new ArrayList<>();
		if (nome != null && !nome.isEmpty() && !nome.trim().isEmpty()) {
			String sql = "SELECT  id, nome, email FROM public.usuarios WHERE UPPER(nome) LIKE UPPER(?) and usurario_pai = ?;";
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {
				preparedStatement = conexao.prepareStatement(sql);
				nome = "%" + nome + "%";
				preparedStatement.setString(1, nome);
				preparedStatement.setLong(2, usuarioPai);
				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					ModelLogin modelLogin = new ModelLogin();
					modelLogin.setNome(resultSet.getString("nome"));
					modelLogin.setId(resultSet.getLong("id"));
					modelLogin.setEmail(resultSet.getString("email"));
					
					listaDeUsuariosRetornada.add(modelLogin);
				}
			} finally {
				if (preparedStatement != null)
					preparedStatement.close();
				if (resultSet != null)
					resultSet.close();
			}
		}		
		return listaDeUsuariosRetornada;
	}

	public List<ModelLogin> consultaUsuariosPorNomePaginada_ajax(String nome, Long usuarioPai, Integer offset) throws Exception {

		List<ModelLogin> listaDeUsuariosRetornada = new ArrayList<>();
		if (nome != null && !nome.isEmpty() && !nome.trim().isEmpty()) {
			String sql = "SELECT  id, nome, email FROM public.usuarios WHERE UPPER(nome) LIKE UPPER(?) and usurario_pai = ? ORDER BY nome OFFSET ? LIMIT 3;";
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {
				preparedStatement = conexao.prepareStatement(sql);
				nome = "%" + nome + "%";
				preparedStatement.setString(1, nome);
				preparedStatement.setLong(2, usuarioPai);
				preparedStatement.setInt(3, offset);
				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					ModelLogin modelLogin = new ModelLogin();
					modelLogin.setNome(resultSet.getString("nome"));
					modelLogin.setId(resultSet.getLong("id"));
					modelLogin.setEmail(resultSet.getString("email"));
					listaDeUsuariosRetornada.add(modelLogin);
				}
			} finally {
				if (preparedStatement != null)
					preparedStatement.close();
				if (resultSet != null)
					resultSet.close();
			}
		}		
		return listaDeUsuariosRetornada;
	}

	public ModelLogin consultaUsuarioPorId(Long idDoUsuario) throws Exception {
		String sql = "SELECT * FROM public.usuarios WHERE id = ?;";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ModelLogin modelLogin = new ModelLogin();
		String nome = "nome";
		String email = "email";
		String idd = "id";
		String loginDoUsuario = "login_usuario";
		String senha = "senha_usuario";
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setLong(1, idDoUsuario);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				modelLogin.setNome(resultSet.getString(nome));
				modelLogin.setEmail(resultSet.getString(email));
				modelLogin.setLogin(resultSet.getString(loginDoUsuario));
				modelLogin.setSenha(resultSet.getString(senha));
				modelLogin.setId(resultSet.getLong(idd));
				modelLogin.setUserAdmin(resultSet.getBoolean("useradmin"));
				modelLogin.setPerfil_usuario(resultSet.getString("perfil_usuario"));
				modelLogin.setSexo(resultSet.getString("sexo_usuario"));
				modelLogin.setFoto_do_usuario(resultSet.getString("foto_user"));
				modelLogin.setSufixo_da_foto_do_usuario(resultSet.getString("sufixo_foto_user"));
				modelLogin.setCEP(resultSet.getString("cep"));
				modelLogin.setCidade(resultSet.getString("cidade"));
				modelLogin.setEndereco(resultSet.getString("endereco"));
				modelLogin.setRenda(resultSet.getDouble("renda"));

				java.sql.Date data = resultSet.getDate("data_de_nascimento");
				
				if(data!=null) {
					modelLogin.setDataDeNascimento(new Date(data.getTime()));		
					modelLogin.setDataDeNascimentoString();
				}else {
					modelLogin.setDataDeNascimento(null);
				}
				
			}
			return modelLogin;
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

	public List<ModelLogin> consultaUsariosTodosFiltroData(Long usurioPai, Date dataInicial, Date dataFinal) throws Exception {

		List<ModelLogin> listaDeUsuariosParaRetornar = new ArrayList<>();
		String sql = "SELECT * FROM public.usuarios WHERE useradmin is false and usurario_pai = ?"
				+"and data_de_nascimento>= ? and data_de_nascimento<= ?"
				+ " ORDER BY nome;";
		PreparedStatement preparedStatement = conexao.prepareStatement(sql);
		ResultSet resultSet = null;

		try {
			preparedStatement.setLong(1, usurioPai);
			preparedStatement.setDate(2, new java.sql.Date(dataInicial.getTime()));
			preparedStatement.setDate(3, new java.sql.Date(dataFinal.getTime()));
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setNome(resultSet.getString("nome"));
				modelLogin.setEmail(resultSet.getString("email"));
				modelLogin.setLogin(resultSet.getString("login_usuario"));
				modelLogin.setId(resultSet.getLong("id"));
				modelLogin.setUserAdmin(resultSet.getBoolean("useradmin"));
				modelLogin.setPerfil_usuario(resultSet.getString("perfil_usuario"));
				modelLogin.setSexo(resultSet.getString("sexo_usuario"));
				modelLogin.setCEP(resultSet.getString("cep"));
				modelLogin.setCidade(resultSet.getString("cidade"));
				modelLogin.setEndereco(resultSet.getString("endereco"));
				java.sql.Date data = resultSet.getDate("data_de_nascimento");
				modelLogin.setRenda(resultSet.getDouble("renda"));
				
				if(data!=null) {
					modelLogin.setDataDeNascimento(new Date(data.getTime()));					
				}else {
					modelLogin.setDataDeNascimento(null);
				}
				modelLogin.setListaDeTelefones(DAOTelefoneRepository.telefonesPorUsuarioId(modelLogin.getId(),this.conexao));
				listaDeUsuariosParaRetornar.add(modelLogin);
			}

		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return listaDeUsuariosParaRetornar;
	}
	public List<ModelLogin> consultaUsariosTodos(Long usurioPai) throws Exception {

		List<ModelLogin> listaDeUsuariosParaRetornar = new ArrayList<>();
		String sql = "SELECT * FROM public.usuarios WHERE useradmin is false and usurario_pai = ? ORDER BY nome;";
		PreparedStatement preparedStatement = conexao.prepareStatement(sql);
		ResultSet resultSet = null;

		try {
			preparedStatement.setLong(1, usurioPai);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setNome(resultSet.getString("nome"));
				modelLogin.setEmail(resultSet.getString("email"));
				modelLogin.setLogin(resultSet.getString("login_usuario"));
				modelLogin.setId(resultSet.getLong("id"));
				modelLogin.setUserAdmin(resultSet.getBoolean("useradmin"));
				modelLogin.setPerfil_usuario(resultSet.getString("perfil_usuario"));
				modelLogin.setSexo(resultSet.getString("sexo_usuario"));
				modelLogin.setCEP(resultSet.getString("cep"));
				modelLogin.setCidade(resultSet.getString("cidade"));
				modelLogin.setEndereco(resultSet.getString("endereco"));
				java.sql.Date data = resultSet.getDate("data_de_nascimento");
				modelLogin.setRenda(resultSet.getDouble("renda"));
				
				if(data!=null) {
					modelLogin.setDataDeNascimento(new Date(data.getTime()));					
				}else {
					modelLogin.setDataDeNascimento(null);
				}
				modelLogin.setListaDeTelefones(DAOTelefoneRepository.telefonesPorUsuarioId(modelLogin.getId(),this.conexao));
				listaDeUsuariosParaRetornar.add(modelLogin);
			}

		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return listaDeUsuariosParaRetornar;
	}
	public List<ModelLogin> consultaUsariosPaginada(Long usurioPai, Integer offset) throws Exception {

		List<ModelLogin> listaDeUsuariosParaRetornar = new ArrayList<>();
		String sql = "SELECT * FROM public.usuarios WHERE usurario_pai = ? ORDER BY nome OFFSET ? LIMIT 3;";
		PreparedStatement preparedStatement = conexao.prepareStatement(sql);
		ResultSet resultSet = null;

		try {
			preparedStatement.setLong(1, usurioPai);
			preparedStatement.setInt(2, offset);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setNome(resultSet.getString("nome"));
				modelLogin.setEmail(resultSet.getString("email"));
				modelLogin.setLogin(resultSet.getString("login_usuario"));
				modelLogin.setId(resultSet.getLong("id"));
				modelLogin.setUserAdmin(resultSet.getBoolean("useradmin"));
				modelLogin.setPerfil_usuario(resultSet.getString("perfil_usuario"));
				modelLogin.setSexo(resultSet.getString("sexo_usuario"));
				modelLogin.setCEP(resultSet.getString("cep"));
				modelLogin.setCidade(resultSet.getString("cidade"));
				modelLogin.setEndereco(resultSet.getString("endereco"));
				java.sql.Date data = resultSet.getDate("data_de_nascimento");
				modelLogin.setRenda(resultSet.getDouble("renda"));
				
				if(data!=null) {
					modelLogin.setDataDeNascimento(new Date(data.getTime()));					
				}else {
					modelLogin.setDataDeNascimento(null);
				}
				listaDeUsuariosParaRetornar.add(modelLogin);
			}

		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return listaDeUsuariosParaRetornar;
	}
	
	public ModelLogin consultaUsuarioPorLogin(String login) throws Exception {
		String sql = "SELECT * FROM public.usuarios WHERE login_usuario = ?;";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ModelLogin modelLogin = new ModelLogin();
		String nome = "nome";
		String email = "email";
		String idd = "id";
		String loginDoUsuario = "login_usuario";
		String senha = "senha_usuario";
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, login);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				modelLogin.setNome(resultSet.getString(nome));
				modelLogin.setEmail(resultSet.getString(email));
				modelLogin.setLogin(resultSet.getString(loginDoUsuario));
				modelLogin.setSenha(resultSet.getString(senha));
				modelLogin.setId(resultSet.getLong(idd));
				modelLogin.setSexo(resultSet.getString("sexo_usuario"));
				modelLogin.setUserAdmin(resultSet.getBoolean("useradmin"));
				modelLogin.setPerfil_usuario(resultSet.getString("perfil_usuario"));
				modelLogin.setFoto_do_usuario(resultSet.getString("foto_user"));
				modelLogin.setSufixo_da_foto_do_usuario(resultSet.getString("sufixo_foto_user"));
				modelLogin.setCEP(resultSet.getString("cep"));
				modelLogin.setCidade(resultSet.getString("cidade"));
				modelLogin.setEndereco(resultSet.getString("endereco"));
				modelLogin.setRenda(resultSet.getDouble("renda"));
				java.sql.Date data = resultSet.getDate("data_de_nascimento");
				if(data!=null) {
					modelLogin.setDataDeNascimento(new Date(data.getTime()));					
				}else {
					modelLogin.setDataDeNascimento(null);
				}
				
			}
			return modelLogin;
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

}
