package pdf.mandiri.domain;

import java.io.Serializable;
import java.util.Date;
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
public class DistributionFinance implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1239438743818457502L;
	/**
	 * 
	 */
	private Long id;
	private Anchor anchor;
	private BUCAnchor buc;
	private double outstandingList;
	private double limitDF;
	private Status currentStatus;
	private Date tanggal;
	private Kanwil kanwil;
	private String keterangan;
	private Distributor distributor;
	private List<LogStatus> logStatus;
	private double potensi;
	private Area area;
	private String outlet;
	private CustomerType customerType;
	private String cifNumber;
	private String accNumber;
	private int statusKirim; // 0 : belum dikirim, 1 : sudah dikirim, 2 : dibalas
	private String fasilitasString;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@ManyToOne
	@JoinColumn
	public Anchor getAnchor() {
		return anchor;
	}
	public void setAnchor(Anchor anchor) {
		this.anchor = anchor;
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
	public Kanwil getKanwil() {
		return kanwil;
	}
	public void setKanwil(Kanwil kanwil) {
		this.kanwil = kanwil;
	}
	
	
	@Column
	public String getKeterangan() {
		return keterangan;
	}
	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
	
	
	@ManyToOne
	@JoinColumn
	public Distributor getDistributor() {
		return distributor;
	}
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}
	
	@Column
	public int getStatusKirim() {
		return statusKirim;
	}
	public void setStatusKirim(int statusKirim) {
		this.statusKirim = statusKirim;
	}
	
	@Column
	public double getOutstandingList() {
		return outstandingList;
	}
	public void setOutstandingList(double outstandingList) {
		this.outstandingList = outstandingList;
	}
	
	@Column
	public double getLimitDF() {
		return limitDF;
	}
	public void setLimitDF(double limitDF) {
		this.limitDF = limitDF;
	}
	
	@ManyToOne(targetEntity=Status.class)
	@JoinColumn
	public Status getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	
	@Column
	public Date getTanggal() {
		return tanggal;
	}
	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}
	
	@OneToMany(mappedBy="distriFinance",targetEntity=LogStatus.class,fetch=FetchType.LAZY)
	public List<LogStatus> getLogStatus() {
		return logStatus;
	}
	public void setLogStatus(List<LogStatus> logStatus) {
		this.logStatus = logStatus;
	}

	@ManyToOne
	@JoinColumn
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
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
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
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
	public String getFasilitasString() {
		return fasilitasString;
	}
	
	public void setFasilitasString(String fasilitasString) {
		this.fasilitasString = fasilitasString;
	}
	
	@Column
	public double getPotensi() {
		return potensi;
	}
	public void setPotensi(double potensi) {
		this.potensi = potensi;
	}
	
	
	
	
}
