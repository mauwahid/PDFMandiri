package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.BUCAnchor;

public interface BUCAnchorDao extends Dao<BUCAnchor> {

	public List<BUCAnchor> getByJoinDF();
	public List<BUCAnchor> getAllAsc();
		
}
