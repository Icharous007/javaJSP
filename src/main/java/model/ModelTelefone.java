package model;

import java.io.Serializable;
import java.util.Objects;

public class ModelTelefone implements Serializable{

	private static final long serialVersionUID = 5592901800843863075L;
	
	private Long id;
	private String numero;
	private ModelLogin usuario_id;
	private ModelLogin usuario_pai;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public ModelLogin getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(ModelLogin usuario_id) {
		this.usuario_id = usuario_id;
	}
	public ModelLogin getUsuario_pai() {
		return usuario_pai;
	}
	public void setUsuario_pai(ModelLogin usuario_pai) {
		this.usuario_pai = usuario_pai;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelTelefone other = (ModelTelefone) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
