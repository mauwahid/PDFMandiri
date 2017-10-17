package pdf.mandiri.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Distributor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4969786959679384783L;
	
	
	private Long id;
	private String nama;
	private String kota;
	private String noTelp;
	private String namaPIC;
	private Date tanggalBerdiri;
	private String npwp;
	private Area area;
	private String alamat;
	private String outlet;
	private List<DistributionFinance> dfs;
	private BUCAnchor buc;
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	
	@Column
	public String getKota() {
		return kota;
	}
	public void setKota(String kota) {
		this.kota = kota;
	}
	
	@Column
	public String getNoTelp() {
		return noTelp;
	}
	public void setNoTelp(String noTelp) {
		this.noTelp = noTelp;
	}
	
	@Column
	public String getNamaPIC() {
		return namaPIC;
	}
	public void setNamaPIC(String namaPIC) {
		this.namaPIC = namaPIC;
	}
	
	@Column
	public Date getTanggalBerdiri() {
		return tanggalBerdiri;
	}
	public void setTanggalBerdiri(Date tanggalBerdiri) {
		this.tanggalBerdiri = tanggalBerdiri;
	}
	
	@Column
	public String getNpwp() {
		return npwp;
	}
	public void setNpwp(String npwp) {
		this.npwp = npwp;
	}
	
	
	@Column
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	
	@OneToMany(targetEntity=DistributionFinance.class,mappedBy="distributor")
	public List<DistributionFinance> getDfs() {
		return dfs;
	}
	public void setDfs(List<DistributionFinance> dfs) {
		this.dfs = dfs;
	}
	
	@Column
	public String getOutlet() {
		return outlet;
	}
	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}
	
	
	@ManyToOne
	@JoinColumn
	public BUCAnchor getBuc() {
		return buc;
	}
	public void setBuc(BUCAnchor buc) {
		this.buc = buc;
	}
	
	
	@ManyToOne
	@JoinColumn
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
	
	

}
