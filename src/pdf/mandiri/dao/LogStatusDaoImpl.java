package pdf.mandiri.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.LogStatus;

public class LogStatusDaoImpl implements LogStatusDao{
private SessionFactory factory;
	
	
	public LogStatusDaoImpl(SessionFactory factory){
		this.factory = factory;
	}

	@Override
	public void insert(LogStatus entity) {
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
	public void update(LogStatus entity) {
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
	public void delete(LogStatus entity) {
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
	public LogStatus getById(Long id) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			LogStatus anchor = (LogStatus) session.get(LogStatus.class,id);
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
	public List<LogStatus> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<LogStatus> list = session.createCriteria(LogStatus.class).list();
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
	public List<LogStatus> getAllByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogStatus> getByDF(DistributionFinance distributionFinance) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(LogStatus.class);
			crit.add(Restrictions.eq("distriFinance", distributionFinance)).addOrder(Order.desc("id"));
			List<LogStatus> list = crit.list();
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
	public LogStatus getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllString() {
		// TODO Auto-generated method stub
		return null;
	}


}
