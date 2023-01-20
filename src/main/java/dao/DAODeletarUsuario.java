package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;

import conexao.ConexaoUnica;

public class DAODeletarUsuario implements Serializable {

	private static final long serialVersionUID = 9022764259511486705L;

	private Connection conexao;

	public DAODeletarUsuario() {
		conexao = ConexaoUnica.getConexao();
	}

	public boolean deletarUsuarioPorId(Long id) throws Exception {

		String sql = "DELETE FROM public.usuarios WHERE id = ? and useradmin is false;";
		boolean deletado = false;
		int resultado = -1;
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			resultado = preparedStatement.executeUpdate();
			if (resultado > 0) {
				
				deletado = true;
				conexao.commit();
			}
			return deletado;

		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}

	}
}
