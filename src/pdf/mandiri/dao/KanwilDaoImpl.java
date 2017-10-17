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
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.Pengguna;

public class KanwilDaoImpl implements KanwilDao{

private SessionFactory factory;
	
	public KanwilDaoImpl(SessionFactory factory){
		this.factory = factory;
	}
	
	@Override
	public void insert(Kanwil entity) {
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
	public void update(Kanwil entity) {
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
	public void delete(Kanwil entity) {
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
	public Kanwil getById(Long id) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Kanwil kanwil = (Kanwil) session.get(Kanwil.class,id);
			session.getTransaction().commit();
			return kanwil;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	}
	
	
	

	@Override
	public List<Kanwil> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<Kanwil> list = session.createCriteria(Kanwil.class).addOrder(Order.desc("id")).list();
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
	public List<Kanwil> getAllAsc() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<Kanwil> list = session.createCriteria(Kanwil.class).addOrder(Order.asc("nama")).list();
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
	public List<Kanwil> getAllByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(Kanwil.class);
			crit.add(Restrictions.ilike("nama", "%"+name+"%")).addOrder(Order.desc("id"));
			List<Kanwil> list = crit.list();
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
	public List<Kanwil> getByJoinDF() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery("from Kanwil as kanwil right join	fetch kanwil.dfs");
			List<Kanwil> list = query.list();
			
					
					
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
	public Kanwil getByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(Kanwil.class);
			crit.add(Restrictions.like("nama", name)).addOrder(Order.desc("id"));
			Kanwil kanwil = (Kanwil)crit.uniqueResult();
			session.getTransaction().commit();
			return kanwil;
		}catch(HibernateException exception){
			session.getTransaction().rollback();
			throw exception;
		}finally{
			session.close();
		}
	}


	@Override
	public Kanwil getByNoKanwil(String noKanwil) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(Kanwil.class);
			crit.add(Restrictions.like("noKanwil", noKanwil)).addOrder(Order.desc("id"));
			Kanwil kanwil = (Kanwil)crit.uniqueResult();
			session.getTransaction().commit();
			return kanwil;
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
			Query query = session.createSQLQuery("select distinct nama from kanwil inner join distributionfinance on kanwil.id = distributionfinance.kanwil_id");
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
