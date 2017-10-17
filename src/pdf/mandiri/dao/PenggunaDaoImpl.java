package pdf.mandiri.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.Pengguna;

public class PenggunaDaoImpl implements PenggunaDao{
private SessionFactory factory;
	
	public PenggunaDaoImpl(SessionFactory factory){
		this.factory = factory;
	}
	
	@Override
	public void insert(Pengguna entity) {
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
	public void update(Pengguna entity) {
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
	public void delete(Pengguna entity) {
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
	public Pengguna getById(Long id) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Pengguna pengguna = (Pengguna) session.get(Pengguna.class,id);
			session.getTransaction().commit();
			return pengguna;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	}

	public List<Kanwil> getKanwilByPengguna(Pengguna entity){
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<Kanwil> list = session.createCriteria(Pengguna.class).add(Restrictions.eq("kanwil", entity)).list();
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
	public List<Pengguna> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<Pengguna> list = session.createCriteria(Pengguna.class).addOrder(Order.desc("id")).list();
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
	public Pengguna getPengguna(String username, String password) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Pengguna.class);
			Criterion criteria1 = Restrictions.ilike("username", username);
			Criterion criteria2 = Restrictions.ilike("password", password);
			Criterion and = Restrictions.and(criteria1,criteria2);
			criteria.add(and);
			Pengguna pengguna = (Pengguna) criteria.uniqueResult();
			session.getTransaction().commit();
			return pengguna;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	}

	@Override
	public List<Pengguna> getAllByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(Pengguna.class);
			crit.add(Restrictions.ilike("nama", "%"+name+"%")).addOrder(Order.desc("id"));
			List<Pengguna> list = crit.list();
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
	public Pengguna getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllString() {
		// TODO Auto-generated method stub
		return null;
	}


}
