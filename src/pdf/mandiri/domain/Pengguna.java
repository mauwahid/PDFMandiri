package pdf.mandiri.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Pengguna implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5114054276349426043L;
	
	private Long id;
	private String username;
	private String nama;
	private String password;
	private String passwordEmail;
	private String email;
	private String alamat;
	private String noTelp;
	private Kanwil kanwil;
	private int status; // 1 Kanwil, 2 Pusat, 3 Admin
	private List<UploadDF> uploades;
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	
	@Column
	public String getNoTelp() {
		return noTelp;
	}
	public void setNoTelp(String noTelp) {
		this.noTelp = noTelp;
	}
	
	
	@ManyToOne
	@JoinColumn
	public Kanwil getKanwil() {
		return kanwil;
	}
	public void setKanwil(Kanwil kanwil) {
		this.kanwil = kanwil;
	}
	
	@Column
	public int getStatus() {
		return status; 
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	
	
	@OneToMany(mappedBy="pengguna",targetEntity=UploadDF.class,fetch=FetchType.LAZY)
	public List<UploadDF> getUploades() {
		return uploades;
	}
	public void setUploades(List<UploadDF> uploades) {
		this.uploades = uploades;
	}
	
	
	@Column
	public String getPasswordEmail() {
		return passwordEmail;
	}
	public void setPasswordEmail(String passwordEmail) {
		this.passwordEmail = passwordEmail;
	}
	
	
	
	
}
