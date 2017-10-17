package pdf.mandiri.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pdf.mandiri.domain.Anchor;
import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.Distributor;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.KreditType;

public  class DistributionFinanceDaoImpl implements DistributionFinanceDao {
private SessionFactory factory;
	
	
	public DistributionFinanceDaoImpl(SessionFactory factory){
		this.factory = factory;
	}

	@Override
	public void insert(DistributionFinance entity) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	}

	
	@Override
	public void update(DistributionFinance entity) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			session.update(entity);
			session.getTransaction().commit();
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}

	}

	@Override
	public void delete(DistributionFinance entity) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			session.delete(entity);
			session.getTransaction().commit();
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	}

	@Override
	public DistributionFinance getById(Long id) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			DistributionFinance anchor = (DistributionFinance) session.get(DistributionFinance.class,id);
			session.getTransaction().commit();
			return anchor;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	}

	@Override
	public List<DistributionFinance> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<DistributionFinance> list = session.createCriteria(DistributionFinance.class).addOrder(Order.desc("id")).list();
			session.getTransaction().commit();
			return list;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	
	}
	
	@Override
	public List<DistributionFinance> getAllByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(DistributionFinance.class);
			crit.add(Restrictions.ilike("namaArea", name)).addOrder(Order.desc("id"));
			List<DistributionFinance> list = crit.list();
			session.getTransaction().commit();
			return list;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	}

	@Override
	public List<DistributionFinance> getKanwil(Kanwil kanwil) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(DistributionFinance.class);
			crit.add(Restrictions.eq("kanwil", kanwil)).addOrder(Order.desc("id"));
			List<DistributionFinance> list = crit.list();
			session.getTransaction().commit();
			return list;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}

	}

	@Override
	public List<DistributionFinance> getAnchorKanwilDistributor(Anchor anchor,
			Kanwil kanwil, Distributor distributor) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(DistributionFinance.class);
			Criterion critAnc = Restrictions.eq("anchor", anchor);
			Criterion critKan = Restrictions.eq("kanwil", kanwil);
			Criterion critDis = Restrictions.eq("distributor", distributor);
			Criterion critAncKan = Restrictions.or(critAnc,critKan);
			Criterion critAncKanDis = Restrictions.or(critAncKan,critDis);
			crit.add(critAncKanDis).addOrder(Order.desc("id"));
			List<DistributionFinance> list = crit.list();
			session.getTransaction().commit();
			return list;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}

	}

	@Override
	public List<DistributionFinance> getAnchorFasilitasKanwilBUCArea(
			Anchor anchor,String fasilitas, Kanwil kanwil,
			BUCAnchor bucAnchor, Area area) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(DistributionFinance.class);
			Criterion critAnc = Restrictions.eq("anchor", anchor);
			Criterion critKan = Restrictions.eq("kanwil", kanwil);
			Criterion critDis = Restrictions.ilike("fasilitasString", "%"+fasilitas+"%");
			Criterion critBuc = Restrictions.eq("buc", bucAnchor);
			Criterion critArea = Restrictions.eq("area", area);
		/*	Criterion critAncKan = Restrictions.and(critAnc,critKan);
			Criterion critAncKanDis = Restrictions.and(critAncKan,critDis);
			Criterion critAncKanDisBuc = Restrictions.and(critAncKan,critBuc);
			Criterion critAncKanDisBucAr = Restrictions.and(critAncKan,critArea);*/
			
			List<DistributionFinance> list = crit.list();
			
			Conjunction conjunction = Restrictions.conjunction();
			
			if(anchor!=null)
				conjunction.add(critAnc);
			if(kanwil!=null)
				conjunction.add(critKan);
			if(fasilitas!=null)
				conjunction.add(critDis);
			if(bucAnchor!=null)
				conjunction.add(critBuc);
			if(area!=null)
				conjunction.add(critArea);
			
			if(anchor==null&kanwil==null&fasilitas==null&bucAnchor==null&area==null)
				list = crit.list();
			else
				list = crit.add(conjunction).list();
			
			
			
			//crit.add(critAncKanDisBucAr).addOrder(Order.desc("id"));
			session.getTransaction().commit();
			return list;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}

	}

	@Override
	public List<DistributionFinance> getAnchorDistributorKanwilBUCAreaDistinct(
			Anchor anchor, Distributor distributor, Kanwil kanwil,
			BUCAnchor bucAnchor, Area area) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DistributionFinance getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllString() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
