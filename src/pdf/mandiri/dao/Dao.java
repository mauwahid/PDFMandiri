package pdf.mandiri.dao;

import java.io.Serializable;
import java.util.List;

import pdf.mandiri.domain.DistributionFinance;

public interface Dao <T extends Serializable> {

	void insert(T entity);
	void update(T entity);
	void delete(T entity);
	
	T getById(Long id);
	List<T> getAllByName(String name);
	List<T> getAll();
	T getByName(String name);
	List<String> getAllString();

}
