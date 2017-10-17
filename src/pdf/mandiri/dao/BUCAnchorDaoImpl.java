package pdf.mandiri.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.BUCAnchor;

public class BUCAnchorDaoImpl implements BUCAnchorDao {

private SessionFactory factory;
	
	
	public BUCAnchorDaoImpl(SessionFactory factory){
		this.factory = factory;
	}

	@Override
	public void insert(BUCAnchor entity) {
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
	public void update(BUCAnchor entity) {
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
	public void delete(BUCAnchor entity) {
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
	public BUCAnchor getById(Long id) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			BUCAnchor anchor = (BUCAnchor) session.get(BUCAnchor.class,id);
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
	public List<BUCAnchor> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<BUCAnchor> list = session.createCriteria(BUCAnchor.class).addOrder(Order.desc("id")).list();
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
	public List<BUCAnchor> getAllAsc() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<BUCAnchor> list = session.createCriteria(BUCAnchor.class).addOrder(Order.asc("kodeBuc")).list();
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
	public List<BUCAnchor> getAllByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(BUCAnchor.class);
			crit.add(Restrictions.ilike("kodeBuc", "%"+name+"%")).addOrder(Order.desc("id"));
			List<BUCAnchor> list = crit.list();
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
	public List<BUCAnchor> getByJoinDF() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery("from BUCAnchor as buc right join	fetch buc.dfs");
			List<BUCAnchor> list = query.list();
			
					
					
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
	public BUCAnchor getByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(BUCAnchor.class);
			crit.add(Restrictions.like("kodeBuc", name)).addOrder(Order.desc("id"));
			BUCAnchor buc = (BUCAnchor) crit.uniqueResult();
			session.getTransaction().commit();
			return buc;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	}

	@Override
	public List<String> getAllString() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Query query = session.createSQLQuery("select distinct kodebuc from bucanchor as bucanchor inner join distributionfinance on  bucanchor.id = distributionfinance.buc_id");
			List<String> list = query.list();
			
					
					
			session.getTransaction().commit();
			return list;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	}

}
