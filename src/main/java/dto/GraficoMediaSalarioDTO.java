package dto;

import java.io.Serializable;

public class GraficoMediaSalarioDTO implements Serializable{

	private static final long serialVersionUID = -4396851164544149759L;
	
	
	private Double media;
	private String perfil;
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public void setMedia(Double media) {
		this.media = media;
	}
	
	public Double getMedia() {
		return media;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
}
