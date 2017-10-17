package pdf.mandiri.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import pdf.mandiri.dao.AnchorDao;
import pdf.mandiri.dao.AnchorDaoImpl;
import pdf.mandiri.dao.AreaDao;
import pdf.mandiri.dao.AreaDaoImpl;
import pdf.mandiri.dao.BUCAnchorDao;
import pdf.mandiri.dao.BUCAnchorDaoImpl;
import pdf.mandiri.dao.CustomerTypeDao;
import pdf.mandiri.dao.CustomerTypeDaoImpl;
import pdf.mandiri.dao.DistributionFinanceDao;
import pdf.mandiri.dao.DistributionFinanceDaoImpl;
import pdf.mandiri.dao.DistributorDao;
import pdf.mandiri.dao.DistributorDaoImpl;
import pdf.mandiri.dao.KanwilDao;
import pdf.mandiri.dao.KanwilDaoImpl;
import pdf.mandiri.dao.KirimEmailDao;
import pdf.mandiri.dao.KirimEmailDaoImpl;
import pdf.mandiri.dao.KreditTypeDao;
import pdf.mandiri.dao.KreditTypeDaoImpl;
import pdf.mandiri.dao.LogStatusDao;
import pdf.mandiri.dao.LogStatusDaoImpl;
import pdf.mandiri.dao.PenggunaDao;
import pdf.mandiri.dao.PenggunaDaoImpl;
import pdf.mandiri.dao.StatusDao;
import pdf.mandiri.dao.StatusDaoImpl;
import pdf.mandiri.dao.UploadDao;
import pdf.mandiri.dao.UploadDaoImpl;


public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	
	//Dao Object
	private static final AnchorDao anchorDao;
	private static final DistributorDao distributorDao;
	private static final KanwilDao kanwilDao;
	private static final KreditTypeDao kreditTypeDao;
	private static final CustomerTypeDao customerTypeDao;
	private static final StatusDao statusDao;
	private static final DistributionFinanceDao distributionFinanceDao;
	private static final PenggunaDao penggunaDao;
	private static final LogStatusDao logStatusDao;
	private static final AreaDao areaDao;
	private static final BUCAnchorDao bucAnchorDao;
	private static final KirimEmailDao kirimEmailDao;
	private static final UploadDao uploadDao;
	
	
	
	
	static{
		sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		anchorDao = new AnchorDaoImpl(sessionFactory);
		distributorDao = new DistributorDaoImpl(sessionFactory);
		kanwilDao = new KanwilDaoImpl(sessionFactory);
		kreditTypeDao = new KreditTypeDaoImpl(sessionFactory);
		customerTypeDao = new CustomerTypeDaoImpl(sessionFactory);
		statusDao = new StatusDaoImpl(sessionFactory);
		distributionFinanceDao = new DistributionFinanceDaoImpl(sessionFactory);
		penggunaDao =  new PenggunaDaoImpl(sessionFactory);
		logStatusDao =  new LogStatusDaoImpl(sessionFactory);
		areaDao = new AreaDaoImpl(sessionFactory);
		bucAnchorDao = new BUCAnchorDaoImpl(sessionFactory);
		kirimEmailDao = new KirimEmailDaoImpl(sessionFactory);
		uploadDao = new UploadDaoImpl(sessionFactory);
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public static AnchorDao getAnchorDao(){
		return anchorDao;
	}
	
	public static DistributorDao getDistributorDao(){
		return distributorDao;
	}
	
	public static KanwilDao getKanwilDao(){
		return kanwilDao;
	}
	
	
	public static KreditTypeDao getKreditTypeDao(){
		return kreditTypeDao;
	}
	
	public static StatusDao getStatusDao(){
		return statusDao;
	}
	
	public static CustomerTypeDao getCustomerTypeDao(){
		return customerTypeDao;
	}
	
	
	public static DistributionFinanceDao getDistributionFinanceDao(){
		return distributionFinanceDao;
	}
	
	public static PenggunaDao getPenggunaDao(){
		return penggunaDao;
	}

	public static LogStatusDao getLogStatusDao(){
		return logStatusDao;
	}
	
	public static AreaDao getAreaDao(){
		return areaDao;
	}
	
	public static BUCAnchorDao getBucAnchorDao(){
		return bucAnchorDao;
	}
	
	public static KirimEmailDao getKirimEmailDao(){
		return kirimEmailDao;
	}
	
	public static UploadDao getUploadDao(){
		return uploadDao;
	}
}
