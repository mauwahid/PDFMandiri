package pdf.mandiri.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


// BUC Distributor dan Anchor Sama
@Entity
@Table
public class BUCAnchor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3512154432311136545L;

	private Long id;
	private String kodeBuc;
	private String namaUnitKerja;
	private List<DistributionFinance> dfs;
	private List<Anchor> anchors;
	private List<Distributor> distributors;
	private String deskripsiGrup;
	private String deskripsiDirektorat;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getKodeBuc() {
		return kodeBuc;
	}
	public void setKodeBuc(String kodeBuc) {
		this.kodeBuc = kodeBuc;
	}
	
	@Column
	public String getNamaUnitKerja() {
		return namaUnitKerja;
	}
	public void setNamaUnitKerja(String namaUnitKerja) {
		this.namaUnitKerja = namaUnitKerja;
	}
	
	@OneToMany(targetEntity=DistributionFinance.class,mappedBy="buc",fetch=FetchType.LAZY)
	public List<DistributionFinance> getDfs() {
		return dfs;
	}
	public void setDfs(List<DistributionFinance> dfs) {
		this.dfs = dfs;
	}
	
	
	@OneToMany(mappedBy="buc",targetEntity=Anchor.class,fetch=FetchType.LAZY)
	public List<Anchor> getAnchors() {
		return anchors;
	}
	public void setAnchors(List<Anchor> anchors) {
		this.anchors = anchors;
	}
	
	@OneToMany(mappedBy="buc",targetEntity=Distributor.class,fetch=FetchType.LAZY)
	public List<Distributor> getDistributors() {
		return distributors;
	}
	public void setDistributors(List<Distributor> distributors) {
		this.distributors = distributors;
	}
	
	@Column
	public String getDeskripsiGrup() {
		return deskripsiGrup;
	}
	public void setDeskripsiGrup(String deskripsiGrup) {
		this.deskripsiGrup = deskripsiGrup;
	}
	
	@Column
	public String getDeskripsiDirektorat() {
		return deskripsiDirektorat;
	}
	public void setDeskripsiDirektorat(String deskripsiDirektorat) {
		this.deskripsiDirektorat = deskripsiDirektorat;
	}
	
	
	
}
