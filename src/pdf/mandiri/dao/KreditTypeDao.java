package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.KreditType;

public interface KreditTypeDao extends Dao<KreditType> {

	public List<KreditType> getAllAsc();

}
