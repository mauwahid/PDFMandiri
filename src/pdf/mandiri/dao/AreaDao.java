package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.Anchor;
import pdf.mandiri.domain.Area;

public interface AreaDao extends Dao<Area> {

	public List<Area> getByJoinDF();
	public List<Area> getAllAsc();
		
}
