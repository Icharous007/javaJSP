package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.ConexaoUnica;
import model.ModelLogin;

public class DAOLoginRepository implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private Connection conexao;
	
	public DAOLoginRepository() {
		conexao = ConexaoUnica.getConexao();
	}
	
	public ModelLogin autenticacao(ModelLogin modelLogin) throws Exception {
		String sql = "SELECT * FROM public.usuarios WHERE upper(login_usuario)=  upper(?) and upper(senha_usuario) = upper(?)";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet  = null;
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, modelLogin.getLogin());
			preparedStatement.setString(2, modelLogin.getSenha());
			
			resultSet = preparedStatement.executeQuery();
			
			while( resultSet.next()) {
				modelLogin.setNome(resultSet.getString("nome"));
				modelLogin.setEmail(resultSet.getString("email"));
				modelLogin.setLogin(resultSet.getString("login_usuario"));
				modelLogin.setSenha(resultSet.getString("senha_usuario"));
				modelLogin.setId(resultSet.getLong("id"));
				modelLogin.setUserAdmin(resultSet.getBoolean("useradmin"));
				modelLogin.setPerfil_usuario(resultSet.getString("perfil_usuario"));
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
