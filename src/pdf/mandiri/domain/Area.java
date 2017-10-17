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


// Area diganti dengan Cabang
@Entity
@Table
public class Area implements Serializable{

	private Long id;
	private String namaArea;
	private String kota;
	private String kodePos;
	private String propinsi;
	private String alamat;
	private List<Distributor> distributors;
	private List<DistributionFinance> distributionFinances;
	private Kanwil kanwil;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getNamaArea() {
		return namaArea;
	}
	public void setNamaArea(String namaArea) {
		this.namaArea = namaArea;
	}
	
	
	@Column
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	

	@Column
	public String getKota() {
		return kota;
	}
	public void setKota(String kota) {
		this.kota = kota;
	}
	
	@Column
	public String getKodePos() {
		return kodePos;
	}
	public void setKodePos(String kodePos) {
		this.kodePos = kodePos;
	}
	
	@Column
	public String getPropinsi() {
		return propinsi;
	}
	public void setPropinsi(String propinsi) {
		this.propinsi = propinsi;
	}
	
	@OneToMany(mappedBy="area",fetch=FetchType.LAZY,targetEntity=DistributionFinance.class)
	public List<DistributionFinance> getDistributionFinances() {
		return distributionFinances;
	}
	public void setDistributionFinances(List<DistributionFinance> distributionFinances) {
		this.distributionFinances = distributionFinances;
	}
	
	@OneToMany(mappedBy="area",fetch=FetchType.LAZY,targetEntity=Distributor.class)
	public List<Distributor> getDistributors() {
		return distributors;
	}
	public void setDistributors(List<Distributor> distributors) {
		this.distributors = distributors;
	}
	
	@ManyToOne
	@JoinColumn
	public Kanwil getKanwil() {
		return kanwil;
	}
	public void setKanwil(Kanwil kanwil) {
		this.kanwil = kanwil;
	}
	
	
	
}
