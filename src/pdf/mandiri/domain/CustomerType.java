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
public class CustomerType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801950456136959708L;
	
	private Long id;
	private String custormerType;
	private String description;
	private List<DistributionFinance> distributionFinances;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getCustormerType() {
		return custormerType;
	}
	public void setCustormerType(String custormerType) {
		this.custormerType = custormerType;
	}
	
	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@OneToMany(mappedBy="customerType",fetch=FetchType.LAZY,targetEntity=DistributionFinance.class)
	public List<DistributionFinance> getDistributionFinances() {
		return distributionFinances;
	}
	public void setDistributionFinances(
			List<DistributionFinance> distributionFinances) {
		this.distributionFinances = distributionFinances;
	}
	
	
}
