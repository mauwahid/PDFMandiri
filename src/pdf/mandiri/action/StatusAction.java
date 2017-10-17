package pdf.mandiri.action;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pdf.mandiri.action.PenggunaAction.PenggunaRenderer;
import pdf.mandiri.action.StatusAction.StatusRenderer;
import pdf.mandiri.dao.StatusDao;
import pdf.mandiri.domain.Pengguna;
import pdf.mandiri.domain.Status;
import pdf.mandiri.domain.Status;
import pdf.mandiri.util.HibernateUtil;

public class StatusAction extends GenericForwardComposer<Component> implements Action<Status> {

	private StatusDao statusDao;
	private SimpleListModel<Status> listModel;
	private Status statusEntity;
	
	//Component
	Listbox list_status;
	Textbox txt_cari;
	
	//Component By Code
	private Window window;
	private Grid grid;
	private Column column;
	private Row row;
	private Columns columns;
	private Rows rows;
	
	private Textbox tbStatus;
	private Textbox tbDescription;
	
	private Button btnSave;
	private Button btnCancel;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		loadData();
	}

	
	private void loadData(){
		statusDao = HibernateUtil.getStatusDao();
		List<Status> list = statusDao.getAll();
		
		listModel = new SimpleListModel<Status>(list);
		//UI Renderer
		list_status.setModel(listModel);
		list_status.setItemRenderer(new StatusRenderer());
		list_status.renderAll();
	
	}

	public void onAdd(){
		showForm(null);
		System.out.println("on Add");
	}
	
	
	public void onUpdate(){
		System.out.println("halooo");
	}
	
	public void onRefresh(){
		loadData();
	}
	
	@Override
	public void onSearch() {
		// TODO Auto-generated method stub
		statusDao = HibernateUtil.getStatusDao();
		List<Status> list = statusDao.getAllByName(txt_cari.getText());
		
		listModel = new SimpleListModel<Status>(list);
		//UI Renderer
		list_status.setModel(listModel);
		list_status.setItemRenderer(new StatusRenderer());
		list_status.renderAll();
	}


	public void deleteData(Status entity) {
		// TODO Auto-generated method stub
		StatusDao dao = HibernateUtil.getStatusDao();
		dao.delete(entity);
		Messagebox.show("Berhasil dihapus", "Hapus data", Messagebox.OK, null, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				loadData();
			}
		});
		
	}

	
	class StatusRenderer implements ListitemRenderer<Status>{

		
		@Override
		public void render(Listitem item, Status entity, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final Status dataTemp = entity;
			index = index + 1;
		
		//	new Listcell(dataTemp.getId()+"").setParent(item);
			new Listcell(dataTemp.getStatusName()).setParent(item);
			new Listcell(dataTemp.getDescription()).setParent(item);
			
			
			Box box = new Hbox();
			Button btnUpdate = new Button("Update");
			btnUpdate.setMold("trendy");
			
			
			item.addEventListener("onDoubleClick", new EventListener<Event>() {
				
				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					
					detail(dataTemp);
				}
			});
			

			btnUpdate.addEventListener("onClick", new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					showForm(dataTemp);
				}
			
			});
			
			btnUpdate.setParent(box);
			Button btnDelete = new Button("Delete");
			btnDelete.setMold("trendy");
			btnDelete.addEventListener("onClick", new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					deleteData(dataTemp);
				}
			
			});

			btnDelete.setParent(box);
		
			Listcell listCell = new Listcell();
			box.setParent(listCell);
			listCell.setParent(item);
		}
		
	}


	@Override
	public void showForm(Status entity) {
		
		this.statusEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Status");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("150px");
		window.setVisible(true);
		
		grid = new Grid();
		columns = new Columns();
		rows = new Rows();
		rows.setParent(grid);
		
		grid.setParent(window);
		columns.setParent(grid);
	
		column = new Column();
		column.setWidth("35%");
		column.setParent(columns);
		
		column = new Column();
		column.setParent(columns);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Nama Status"));
		tbStatus = new Textbox(entity!=null?entity.getStatusName():"");
		tbStatus.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Deskripsi"));
		tbDescription = new Textbox(entity!=null?entity.getDescription():"");
		tbDescription.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label(""));
		Box box = new Hbox();
		btnSave = new Button("Simpan");
		btnCancel = new Button("Batal");
		row.setParent(rows);
		
		btnSave.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				StatusDao statusDao = HibernateUtil.getStatusDao();
				Status status;
				
				if(statusEntity==null)
					status = new Status();
				else
					status = statusEntity;
				
				status.setStatusName(tbStatus.getText());
				status.setDescription(tbDescription.getText());
				
				if(statusEntity==null)
					statusDao.insert(status);
				else
					statusDao.update(status);
				
				Messagebox.show("Berhasil disimpan", "Simpan data", Messagebox.OK, null, new EventListener<Event>() {
					
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						window.onClose();
					}
				});
				
				loadData();
			}
		
		});
		
		btnCancel.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				
				window.onClose();
			}
		
		});
		
		btnSave.setParent(box);
		btnCancel.setParent(box);
		box.setParent(row);
		row.setParent(rows);
		
		window.onModal();
		
	}

	public void detail(Status entity) {
		
		this.statusEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Status");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("150px");
		window.setVisible(true);
		
		grid = new Grid();
		columns = new Columns();
		rows = new Rows();
		rows.setParent(grid);
		
		grid.setParent(window);
		columns.setParent(grid);
	
		column = new Column();
		column.setWidth("35%");
		column.setParent(columns);
		
		column = new Column();
		column.setParent(columns);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Nama Status"));
		row.appendChild(new Label(entity.getStatusName()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Deskripsi"));
		row.appendChild(new Label(entity.getDescription()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Aksi"));
		Box box = new Hbox();
		btnCancel = new Button("Close");
		row.setParent(rows);
		
		btnCancel.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				
				window.onClose();
			}
		
		});
		
		btnCancel.setParent(box);
		box.setParent(row);
		row.setParent(rows);
		
		window.onModal();
		
	}

}
