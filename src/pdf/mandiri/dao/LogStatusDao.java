package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.LogStatus;

public interface LogStatusDao extends Dao<LogStatus> {
	List<LogStatus> getByDF(DistributionFinance distributionFinance);

}
