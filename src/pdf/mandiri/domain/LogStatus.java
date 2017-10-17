package pdf.mandiri.domain;

import java.io.Serializable;
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
public class LogStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7163068489282096295L;
	private Long id;
	private String pengguna;
	private DistributionFinance distriFinance;
	private String Anchor;
	private String Distributor;
	private String BUC;
	private String outstandingList;
	private String limitDF;
	private String kanwil;
	private String keterangan;
	private String kreditType;
	private String statusDF;
	private String tanggal;
	private String tanggalDiubah;
	
	
	private String potensi;
	private String outlet;
	private String area;
	private String cifNumber;
	private String accNumber;
	private String customerType;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getPengguna() {
		return pengguna;
	}
	public void setPengguna(String pengguna) {
		this.pengguna = pengguna;
	}
	
	@Column
	public String getAnchor() {
		return Anchor;
	}
	public void setAnchor(String anchor) {
		Anchor = anchor;
	}
	
	@Column
	public String getDistributor() {
		return Distributor;
	}
	public void setDistributor(String distributor) {
		Distributor = distributor;
	}
	
	@Column
	public String getBUC() {
		return BUC;
	}
	public void setBUC(String bUC) {
		BUC = bUC;
	}
	
	@Column
	public String getOutstandingList() {
		return outstandingList;
	}
	public void setOutstandingList(String outstandingList) {
		this.outstandingList = outstandingList;
	}
	
	@Column
	public String getLimitDF() {
		return limitDF;
	}
	public void setLimitDF(String limitDF) {
		this.limitDF = limitDF;
	}
	
	@Column
	public String getKanwil() {
		return kanwil;
	}
	public void setKanwil(String kanwil) {
		this.kanwil = kanwil;
	}
	
	@Column
	public String getKeterangan() {
		return keterangan;
	}
	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
	
	@Column
	public String getKreditType() {
		return kreditType;
	}
	public void setKreditType(String kreditType) {
		this.kreditType = kreditType;
	}
	
	@Column
	public String getTanggal() {
		return tanggal;
	}
	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}
	
	@ManyToOne
	@JoinColumn
	public DistributionFinance getDistriFinance() {
		return distriFinance;
	}
	public void setDistriFinance(DistributionFinance distriFinance) {
		this.distriFinance = distriFinance;
	}
	
	@Column
	public String getStatusDF() {
		return statusDF;
	}
	public void setStatusDF(String statusDF) {
		this.statusDF = statusDF;
	}
	
	@Column
	public String getTanggalDiubah() {
		return tanggalDiubah;
	}
	public void setTanggalDiubah(String tanggalDiubah) {
		this.tanggalDiubah = tanggalDiubah;
	}
	
	@Column
	public String getPotensi() {
		return potensi;
	}
	
	public void setPotensi(String potensi) {
		this.potensi = potensi;
	}
	
	@Column
	public String getOutlet() {
		return outlet;
	}
	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}
	
	@Column
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	@Column
	public String getCifNumber() {
		return cifNumber;
	}
	public void setCifNumber(String cifNumber) {
		this.cifNumber = cifNumber;
	}
	
	@Column
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	
	@Column
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	
	
	
}
