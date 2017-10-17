package pdf.mandiri.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class UploadDF implements Serializable{

	
	private Long id;
	private String fileName;
	private String path;
	private Date tanggalUpload;
	private Pengguna pengguna;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Column
	public Date getTanggalUpload() {
		return tanggalUpload;
	}
	public void setTanggalUpload(Date tanggalUpload) {
		this.tanggalUpload = tanggalUpload;
	}
	
	@ManyToOne
	@JoinColumn
	public Pengguna getPengguna() {
		return pengguna;
	}
	public void setPengguna(Pengguna pengguna) {
		this.pengguna = pengguna;
	}
	
	
	
}
