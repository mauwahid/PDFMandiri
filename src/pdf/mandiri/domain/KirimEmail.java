package pdf.mandiri.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class KirimEmail implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1194672605955785871L;
	private Long id;
	private String subject;
	private String dari;
	private String kepada;
	private String body;
	private String cc;
	private Date tanggalKirim;
	private String password;
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column
	public String getDari() {
		return dari;
	}
	public void setDari(String dari) {
		this.dari = dari;
	}
	
	@Column
	public String getKepada() {
		return kepada;
	}
	public void setKepada(String kepada) {
		this.kepada = kepada;
	}
	
	@Column(columnDefinition="text")
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@Column
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	
	
	@Column
	public Date getTanggalKirim() {
		return tanggalKirim;
	}
	public void setTanggalKirim(Date tanggalKirim) {
		this.tanggalKirim = tanggalKirim;
	}
	
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
