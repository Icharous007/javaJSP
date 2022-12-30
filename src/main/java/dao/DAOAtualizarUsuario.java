package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.ConexaoUnica;
import model.ModelLogin;

public class DAOAtualizarUsuario implements Serializable {

	private static final long serialVersionUID = -8283751754938326282L;
	private Connection conexao;
	private DAOConsultaUmUsuario consultaUmUsuario = new DAOConsultaUmUsuario();

	public DAOAtualizarUsuario() {
		conexao = ConexaoUnica.getConexao();
	}

	public ModelLogin atuliazaUsuarioPorId(ModelLogin modelLogin) throws Exception {
		PreparedStatement preparedStatement = null;
		int update;
		final String sql = "UPDATE public.usuarios SET login_usuario=?,senha_usuario=?,email=?,nome=? WHERE id =?";
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, modelLogin.getLogin());
			preparedStatement.setString(2, modelLogin.getSenha());
			preparedStatement.setString(3, modelLogin.getEmail());
			preparedStatement.setString(4, modelLogin.getNome());
			preparedStatement.setLong(5, modelLogin.getId());
			update = preparedStatement.executeUpdate();
			if (update > 0) {
				return consultaUmUsuario.cosultaUsuarioPorId(modelLogin.getId());
			}
			return null;
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}
}
