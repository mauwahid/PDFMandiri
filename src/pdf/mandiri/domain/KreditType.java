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

@Entity
@Table
public class KreditType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7622192559944642013L;
	private Long id;
	private String kreditType;
	private String description;
//	private List<DistributionFinance> dfs;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getKreditType() {
		return kreditType;
	}
	public void setKreditType(String kreditType) {
		this.kreditType = kreditType;
	}
	
	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
/*	@OneToMany(targetEntity=DistributionFinance.class,mappedBy="kreditType",fetch=FetchType.LAZY)
	public List<DistributionFinance> getDfs() {
		return dfs;
	}
	public void setDfs(List<DistributionFinance> dfs) {
		this.dfs = dfs;
	}
*/	
}
