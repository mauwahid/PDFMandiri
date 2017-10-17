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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table
public class Status implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1009991852846182481L;
	private Long id;
	private String statusName;
	private String description;
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
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="currentStatus",targetEntity=DistributionFinance.class)
	public List<DistributionFinance> getDfs() {
		return dfs;
	}
	public void setDfs(List<DistributionFinance> dfs) {
		this.dfs = dfs;
	}
	
	
}
