package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.CustomerType;

public interface CustomerTypeDao extends Dao<CustomerType> {

	public List<CustomerType> getAllAsc();

}
