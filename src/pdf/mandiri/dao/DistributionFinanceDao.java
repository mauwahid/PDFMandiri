package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.Anchor;
import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.Distributor;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.KreditType;

public interface DistributionFinanceDao extends Dao<DistributionFinance> {

	public List<DistributionFinance> getKanwil(Kanwil kanwil);
	public List<DistributionFinance> getAnchorKanwilDistributor(Anchor anchor,Kanwil kanwil,Distributor distributor);
	public List<DistributionFinance> getAnchorFasilitasKanwilBUCArea(Anchor anchor,String fasilitas, Kanwil kanwil,BUCAnchor bucAnchor,Area area);
	public List<DistributionFinance> getAnchorDistributorKanwilBUCAreaDistinct(Anchor anchor,Distributor distributor, Kanwil kanwil,BUCAnchor bucAnchor,Area area);
	
}

