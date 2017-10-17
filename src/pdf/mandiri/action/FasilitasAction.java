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

import pdf.mandiri.dao.KreditTypeDao;
import pdf.mandiri.domain.KreditType;
import pdf.mandiri.util.HibernateUtil;

public class FasilitasAction extends GenericForwardComposer<Component> implements Action<KreditType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4993165207438014340L;
	private KreditTypeDao kreditTypeDao;
	private SimpleListModel<KreditType> listModel;
	private KreditType kreditTypeEntity;
	
	//Component
	Listbox list_kreditType;
	Textbox txt_cari;
	
	//Component By Code
	private Window window;
	private Grid grid;
	private Column column;
	private Row row;
	private Columns columns;
	private Rows rows;
	
	private Textbox tbKreditType;
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
		kreditTypeDao = HibernateUtil.getKreditTypeDao();
		List<KreditType> list = kreditTypeDao.getAll();
		
		listModel = new SimpleListModel<KreditType>(list);
		//UI Renderer
		list_kreditType.setModel(listModel);
		list_kreditType.setItemRenderer(new KreditTypeRenderer());
		list_kreditType.renderAll();
	
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
		kreditTypeDao = HibernateUtil.getKreditTypeDao();
		List<KreditType> list = kreditTypeDao.getAllByName(txt_cari.getText());
		
		listModel = new SimpleListModel<KreditType>(list);
		//UI Renderer
		list_kreditType.setModel(listModel);
		list_kreditType.setItemRenderer(new KreditTypeRenderer());
		list_kreditType.renderAll();
	}


	public void deleteData(KreditType entity) {
		// TODO Auto-generated method stub
		KreditTypeDao dao = HibernateUtil.getKreditTypeDao();
		dao.delete(entity);
		Messagebox.show("Berhasil dihapus", "Hapus data", Messagebox.OK, null, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				loadData();
			}
		});
		
	}

	
	class KreditTypeRenderer implements ListitemRenderer<KreditType>{

		
		@Override
		public void render(Listitem item, KreditType entity, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final KreditType dataTemp = entity;
			index = index + 1;
		
		//	new Listcell(dataTemp.getId()+"").setParent(item);
			new Listcell(dataTemp.getKreditType()).setParent(item);
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
	public void showForm(KreditType entity) {
		
		this.kreditTypeEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Jenis Fasilitas");
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
		row.appendChild(new Label("Jenis Fasilitas"));
		tbKreditType = new Textbox(entity!=null?entity.getKreditType():"");
		tbKreditType.setParent(row);
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
		btnSave.setMold("trendy");
		btnCancel = new Button("Batal");
		btnCancel.setMold("trendy");
		row.setParent(rows);
		
		btnSave.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				KreditTypeDao kreditTypeDao = HibernateUtil.getKreditTypeDao();
				KreditType kreditType;
				
				if(kreditTypeEntity==null)
					kreditType = new KreditType();
				else
					kreditType = kreditTypeEntity;
				
				kreditType.setKreditType(tbKreditType.getText());
				kreditType.setDescription(tbDescription.getText());
				
				if(kreditTypeEntity==null)
					kreditTypeDao.insert(kreditType);
				else
					kreditTypeDao.update(kreditType);
				
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

	public void detail(KreditType entity) {
		
		this.kreditTypeEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Fasilitas");
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
		row.appendChild(new Label("Tipe Kredit"));
		row.appendChild(new Label(entity.getKreditType()));
		
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Deskripsi"));
		row.appendChild(new Label(entity.getDescription()));

		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label(""));
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
