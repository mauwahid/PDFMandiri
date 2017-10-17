package pdf.mandiri.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.KreditType;

public class KreditTypeDaoImpl implements KreditTypeDao {
	
	private SessionFactory factory;
	
	public KreditTypeDaoImpl(SessionFactory factory){
		this.factory = factory;
	}
	
	@Override
	public void insert(KreditType entity) {
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
	public void update(KreditType entity) {
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
	public void delete(KreditType entity) {
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
	public KreditType getById(Long id) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			KreditType anchor = (KreditType) session.get(KreditType.class,id);
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
	public List<KreditType> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<KreditType> list = session.createCriteria(KreditType.class).list();
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
	public List<KreditType> getAllAsc() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<KreditType> list = session.createCriteria(KreditType.class).addOrder(Order.asc("kreditType")).list();
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
	public List<KreditType> getAllByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(KreditType.class);
			crit.add(Restrictions.ilike("kreditType", "%"+name+"%")).addOrder(Order.desc("id"));
			List<KreditType> list = crit.list();
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
	public KreditType getByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(KreditType.class);
			crit.add(Restrictions.ilike("kreditType", name)).addOrder(Order.desc("id"));
			KreditType kreditType = (KreditType)crit.uniqueResult();
			session.getTransaction().commit();
			return kreditType;
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
			Query query = session.createSQLQuery("select distinct kredittype from kredittype inner join distributionfinance on  kredittype.id = distributionfinance.kredittype_id");
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
