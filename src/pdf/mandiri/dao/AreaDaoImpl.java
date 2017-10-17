package pdf.mandiri.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pdf.mandiri.domain.Area;

public class AreaDaoImpl implements AreaDao {

	private SessionFactory factory;
	
	
	public AreaDaoImpl(SessionFactory factory){
		this.factory = factory;
	}

	@Override
	public void insert(Area entity) {
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
	public void update(Area entity) {
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
	public void delete(Area entity) {
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
	public Area getById(Long id) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Area anchor = (Area) session.get(Area.class,id);
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
	public List<Area> getAll() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<Area> list = session.createCriteria(Area.class).addOrder(Order.desc("id")).list();
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
	public List<Area> getAllAsc() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			List<Area> list = session.createCriteria(Area.class).addOrder(Order.asc("namaArea")).list();
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
	public List<Area> getAllByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(Area.class);
			crit.add(Restrictions.ilike("namaArea", "%"+name+"%")).addOrder(Order.desc("id"));
			List<Area> list = crit.list();
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
	public List<Area> getByJoinDF() {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery("from Area as area right join	fetch area.distributionFinances");
			List<Area> list = query.list();
			
					
					
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
	public Area getByName(String name) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		try{
			session.beginTransaction();
			Criteria crit = session.createCriteria(Area.class);
			crit.add(Restrictions.like("namaArea", name)).addOrder(Order.desc("id"));
			Area area = (Area)crit.uniqueResult();
			session.getTransaction().commit();
			return area;
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
			Query query = session.createSQLQuery("select distinct namaarea from Area as area inner join distributionfinance on  area.id = distributionfinance.area_id");
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
