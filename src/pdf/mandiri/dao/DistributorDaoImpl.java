package pdf.mandiri.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.Distributor;
import pdf.mandiri.domain.Distributor;

public class DistributorDaoImpl implements DistributorDao{
	
	private SessionFactory factory;
	
	
	public DistributorDaoImpl(SessionFactory factory){
		this.factory = factory;
	}

	@Override
	public void insert(Distributor entity) {
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
	public void update(Distributor entity) {
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
	public void delete(Distributor entity) {
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
	public Distributor getById(Long id) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Distributor anchor = (Distributor) session.get(Distributor.class,id);
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
	public List<Distributor> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<Distributor> list = session.createCriteria(Distributor.class).addOrder(Order.desc("id")).list();
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
	public List<Distributor> getAllAsc() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<Distributor> list = session.createCriteria(Distributor.class).addOrder(Order.asc("nama")).list();
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
	public List<Distributor> getAllByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(Distributor.class);
			crit.add(Restrictions.ilike("nama", "%"+name+"%")).addOrder(Order.desc("id"));
			List<Distributor> list = crit.list();
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
	public List<Distributor> getByJoinDF() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery("from Distributor as distributor right join fetch distributor.dfs");
			List<Distributor> list = query.list();
			
					
					
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
	public Distributor getByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(Distributor.class);
			crit.add(Restrictions.like("nama", name)).addOrder(Order.desc("id"));
			Distributor dist = (Distributor)crit.uniqueResult();
			session.getTransaction().commit();
			return dist;
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
			Query query = session.createSQLQuery("select distinct nama from distributor inner join distributionfinance on  distributor.id = distributionfinance.distributor_id");
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
