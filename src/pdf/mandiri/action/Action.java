package pdf.mandiri.action;

public interface Action<T> {

	public void onAdd();
	public void onRefresh();
	public void onSearch();
	
	public void showForm(T entity);
	public void deleteData(T entitiy);
}
