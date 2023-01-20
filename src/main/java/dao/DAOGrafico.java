package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexao.ConexaoUnica;
import dto.GraficoMediaSalarioDTO;

public class DAOGrafico  implements Serializable{

	private static final long serialVersionUID = -262598153895004124L;
	private Connection conexao = null;
	
	public DAOGrafico() {
		conexao=ConexaoUnica.getConexao();
	}
	
	public List<GraficoMediaSalarioDTO> dadosDoGrafico(Long usurioPaiId)throws Exception {
		String sql = "SELECT avg(renda) as renda_media_por_perfil, perfil_usuario FROM public.usuarios WHERE usurario_pai=? GROUP BY perfil_usuario";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List< GraficoMediaSalarioDTO> listaDeDados = new ArrayList<>();
		try {
			preparedStatement= conexao.prepareStatement(sql);
			preparedStatement.setLong(1, usurioPaiId);
			resultSet= preparedStatement.executeQuery();
			while(resultSet.next()) {
				GraficoMediaSalarioDTO grafico = new GraficoMediaSalarioDTO();
				grafico.setMedia(resultSet.getDouble(1));
				grafico.setPerfil( resultSet.getString(2));
				listaDeDados.add(grafico);				
			}
			return listaDeDados;
		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
			if (resultSet != null)
				resultSet.close();
		}		
	}
}
