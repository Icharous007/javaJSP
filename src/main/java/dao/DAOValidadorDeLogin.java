package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.ConexaoUnica;

public class DAOValidadorDeLogin  implements Serializable {

	private static final long serialVersionUID = -7692263444731735401L;
	
	private Connection conexao;
	public DAOValidadorDeLogin() {
		conexao = ConexaoUnica.getConexao();
	}
	
	public boolean validarLogin(String login) throws Exception{
		String sql = "SELECT  COUNT(1) > 0 as existe FROM public.usuarios Where upper(login_usuario) = upper (?);";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet  = null;
		
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1,login);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getBoolean("existe");
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
