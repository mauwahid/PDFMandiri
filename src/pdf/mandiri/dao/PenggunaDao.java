package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.Pengguna;

public interface PenggunaDao extends Dao<Pengguna>{

	public List<Kanwil> getKanwilByPengguna(Pengguna entity);
	public Pengguna getPengguna(String username, String password);
		
}
