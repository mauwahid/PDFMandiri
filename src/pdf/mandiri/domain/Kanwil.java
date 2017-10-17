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
public class Kanwil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5510455786426894923L;
	private Long id;	
	private String noKanwil;
	private String nama;
	private String alamat;
	private String kota;
	private String pic;
	private List<DistributionFinance> dfs;
	private List<Pengguna> penggunas;
	private String emailKanwil;
	private String emailSupervisi1;
	private String emailSupervisi2;
	private List<Area> areas;
	
	
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
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	
	@Column
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	@OneToMany(targetEntity=DistributionFinance.class,mappedBy="kanwil")
	public List<DistributionFinance> getDfs() {
		return dfs;
	}
	public void setDfs(List<DistributionFinance> dfs) {
		this.dfs = dfs;
	}
	
	@OneToMany(mappedBy="kanwil",targetEntity=Pengguna.class)
	public List<Pengguna> getPenggunas() {
		return penggunas;
	}
	public void setPenggunas(List<Pengguna> penggunas) {
		this.penggunas = penggunas;
	}
	
	
	
	@Column
	public String getEmailKanwil() {
		return emailKanwil;
	}
	public void setEmailKanwil(String emailKanwil) {
		this.emailKanwil = emailKanwil;
	}
	
	@Column
	public String getEmailSupervisi1() {
		return emailSupervisi1;
	}
	public void setEmailSupervisi1(String emailSupervisi1) {
		this.emailSupervisi1 = emailSupervisi1;
	}
	
	@Column
	public String getEmailSupervisi2() {
		return emailSupervisi2;
	}
	public void setEmailSupervisi2(String emailSupervisi2) {
		this.emailSupervisi2 = emailSupervisi2;
	}
	
	
	@OneToMany(mappedBy="kanwil",targetEntity=Area.class,fetch=FetchType.LAZY)
	public List<Area> getAreas() {
		return areas;
	}
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	
	@Column
	public String getNoKanwil() {
		return noKanwil;
	}
	public void setNoKanwil(String noKanwil) {
		this.noKanwil = noKanwil;
	}
	
	
	
}
