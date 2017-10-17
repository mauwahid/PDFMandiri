package pdf.mandiri.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import pdf.mandiri.domain.KirimEmail;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.KirimEmail;
import pdf.mandiri.domain.KirimEmail;


public class KirimEmailDaoImpl implements KirimEmailDao {

	private SessionFactory factory;
	
	
	public KirimEmailDaoImpl(SessionFactory factory){
		this.factory = factory;
	}

	@Override
	public void insert(KirimEmail entity) {
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
	public void update(KirimEmail entity) {
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
	public void delete(KirimEmail entity) {
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
	public KirimEmail getById(Long id) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			KirimEmail anchor = (KirimEmail) session.get(KirimEmail.class,id);
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
	public List<KirimEmail> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<KirimEmail> list = session.createCriteria(KirimEmail.class).addOrder(Order.desc("id")).list();
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
	public List<KirimEmail> getAllByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(KirimEmail.class);
			crit.add(Restrictions.ilike("subject", name)).addOrder(Order.desc("id"));
			List<KirimEmail> list = crit.list();
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
	public KirimEmail getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllString() {
		// TODO Auto-generated method stub
		return null;
	}

}
