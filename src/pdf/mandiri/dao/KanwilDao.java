package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.Kanwil;

public interface KanwilDao extends Dao<Kanwil>{

	public List<Kanwil> getByJoinDF();
	public List<Kanwil> getAllAsc();
	public Kanwil getByNoKanwil(String noKanwil);

}
