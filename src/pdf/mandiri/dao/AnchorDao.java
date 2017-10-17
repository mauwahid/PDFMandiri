package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.Anchor;

public interface AnchorDao extends Dao<Anchor>{

	public List<Anchor> getByJoinDF();
	public List<String> getAllString();
	public List<Anchor> getAllAsc();
}

