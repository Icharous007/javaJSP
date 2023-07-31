package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.ConexaoUnica;

public class DAOVersionadorBanco implements Serializable {

	private static final long serialVersionUID = -6603977017152158038L;
	
	private Connection conexao;
	
	public DAOVersionadorBanco() {
		conexao = ConexaoUnica.getConexao();
	}
	
	public boolean arquivoRodado (String arquivo) throws Exception{
		boolean rodou = false;
		String sql = "select count (1) > 0 as arquivo_processado FROM public.versionador_banco WHERE arquivo_sql = ?";
		PreparedStatement preparedStatement =conexao.prepareStatement(sql);
		preparedStatement.setString(1, arquivo);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			rodou = resultSet.getBoolean(1);
		}
		
		return rodou;
	}
	
	public void rodaArquivo (String arquivo) throws Exception{
		String sql = "INSERT INTO public.versionador_banco(arquivo_sql)VALUES (?);";
		PreparedStatement preparedStatement = conexao.prepareStatement(sql);
		preparedStatement.setString(1, arquivo);
		try {
			preparedStatement.execute();
		} finally {
			if(preparedStatement!=null)
				preparedStatement.close();
		}
	}
}
