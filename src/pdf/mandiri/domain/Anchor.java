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
public class Anchor implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3613885925197709375L;
	private Long id;
	private String nama;
	private String grupNasabah;
	private BUCAnchor buc;
	private String rm;
	private String sales;
	private String repSalesOfficer;
	private List<DistributionFinance> dfs;
	
	
	
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
	public String getGrupNasabah() {
		return grupNasabah;
	}
	public void setGrupNasabah(String grupNasabah) {
		this.grupNasabah = grupNasabah;
	}
	
	@ManyToOne
	@JoinColumn
	public BUCAnchor getBuc() {
		return buc;
	}
	public void setBuc(BUCAnchor buc)  {
		this.buc = buc;
	}
	
	@Column
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	
	@Column
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	
	@Column
	public String getRepSalesOfficer() {
		return repSalesOfficer;
	}
	public void setRepSalesOfficer(String repSalesOfficer) {
		this.repSalesOfficer = repSalesOfficer;
	}
	
	@Column
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@OneToMany(targetEntity=DistributionFinance.class,mappedBy="anchor",fetch=FetchType.LAZY)
	public List<DistributionFinance> getDfs() {
		return dfs;
	}
	public void setDfs(List<DistributionFinance> dfs) {
		this.dfs = dfs;
	}
	
	
	
	
}
