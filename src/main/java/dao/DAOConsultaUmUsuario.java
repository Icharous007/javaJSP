package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.ConexaoUnica;
import model.ModelLogin;

public class DAOConsultaUmUsuario  implements Serializable {
	
	private static final long serialVersionUID = 5754533451391647613L;
	private Connection conexao;
	
	public DAOConsultaUmUsuario() {
		conexao = ConexaoUnica.getConexao();
	}
	
	public ModelLogin cosultaUsuarioPorLogin(String login) throws Exception{
		final String sql = "SELECT * FROM public.usuarios WHERE upper(login_usuario) = upper( ? )";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet  = null;
		ModelLogin modelLogin= new ModelLogin();
		String nome = "nome";
		String email = "email";
		String id = "id";
		String loginDoUsuario = "login_usuario";
		String senha = "senha_usuario";
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1,login);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				modelLogin.setNome(resultSet.getString(nome));
				modelLogin.setEmail(resultSet.getString(email));
				modelLogin.setLogin(resultSet.getString(loginDoUsuario));
				modelLogin.setSenha(resultSet.getString(senha));
				modelLogin.setId(resultSet.getLong(id));				
			}			
			return modelLogin;
		} finally {
			
			if (preparedStatement!=null) {
				preparedStatement.close();
			}
			if(resultSet!=null) {
				resultSet.close();				
			}
		}		
	}
	public ModelLogin cosultaUsuarioPorId(Long id) throws Exception{
		final String sql = "SELECT * FROM public.usuarios WHERE id = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet  = null;
		ModelLogin modelLogin= new ModelLogin();
		String nome = "nome";
		String email = "email";
		String idd = "id";
		String loginDoUsuario = "login_usuario";
		String senha = "senha_usuario";
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setLong(1,id);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				modelLogin.setNome(resultSet.getString(nome));
				modelLogin.setEmail(resultSet.getString(email));
				modelLogin.setLogin(resultSet.getString(loginDoUsuario));
				modelLogin.setSenha(resultSet.getString(senha));
				modelLogin.setId(resultSet.getLong(idd));				
			}			
			return modelLogin;
		} finally {
			
			if (preparedStatement!=null) {
				preparedStatement.close();
			}
			if(resultSet!=null) {
				resultSet.close();				
			}
		}		
	}
}
