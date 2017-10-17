package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.Distributor;

public interface DistributorDao extends Dao<Distributor> {

	public List<Distributor> getByJoinDF();
	public List<Distributor> getAllAsc();

}
