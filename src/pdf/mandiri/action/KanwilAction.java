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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
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

import pdf.mandiri.dao.AreaDao;
import pdf.mandiri.dao.KanwilDao;
import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.util.HibernateUtil;

public class KanwilAction extends GenericForwardComposer<Component> implements Action<Kanwil>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4410704648418240046L;
	private KanwilDao kanwilDao;
	private ListModel listModel;
	private Kanwil kanwilEntity;
	private AreaDao areaDao;
	
	//Component
	Listbox list_kanwil;
	Textbox txt_cari;
	
	//Component By Code
	private Window window;
	private Grid grid;
	private Column column;
	private Row row;
	private Columns columns;
	private Rows rows;
	private Combobox cbArea;
	
	private Textbox tbNoKanwil;
	private Textbox tbNama;
	private Textbox tbAlamat;
	private Textbox tbKota;
	private Textbox tbPIC;
	private Textbox tbEmailKanwil;
	private Textbox tbEmailSupervisi1;
	private Textbox tbEmailSupervisi2;
	
	private Button btnSave;
	private Button btnCancel;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		loadData();
	}

	
	private void loadData(){
		kanwilDao = HibernateUtil.getKanwilDao();
		List<Kanwil> list = kanwilDao.getAll();
		
		listModel = new SimpleListModel<Kanwil>(list);
		//UI Renderer
		list_kanwil.setModel(listModel);
		list_kanwil.setItemRenderer(new KanwilRenderer());
		list_kanwil.renderAll();
	
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
		kanwilDao = HibernateUtil.getKanwilDao();
		List<Kanwil> list = kanwilDao.getAllByName(txt_cari.getText());
		
		listModel = new SimpleListModel<Kanwil>(list);
		//UI Renderer
		list_kanwil.setModel(listModel);
		list_kanwil.setItemRenderer(new KanwilRenderer());
		list_kanwil.renderAll();
	}


	public void deleteData(Kanwil entity) {
		// TODO Auto-generated method stub
		KanwilDao dao = HibernateUtil.getKanwilDao();
		dao.delete(entity);
		Messagebox.show("Berhasil dihapus", "Hapus data", Messagebox.OK, null, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				loadData();
			}
		});
		
	}

	
	class KanwilRenderer implements ListitemRenderer<Kanwil>{

		
		@Override
		public void render(Listitem item, Kanwil entity, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final Kanwil dataTemp = entity;
			index = index + 1;
		
			new Listcell(dataTemp.getNoKanwil()).setParent(item);
			new Listcell(dataTemp.getNama()).setParent(item);
			new Listcell(dataTemp.getAlamat()).setParent(item);
			new Listcell(dataTemp.getKota()).setParent(item);
			new Listcell(dataTemp.getPic()).setParent(item);
			new Listcell(dataTemp.getEmailKanwil()).setParent(item);
			new Listcell(dataTemp.getEmailSupervisi1()).setParent(item);
				
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
	public void showForm(Kanwil entity) {
		
		this.kanwilEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Kanwil");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("370px");
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
		row.appendChild(new Label("No Kanwil"));
		tbNoKanwil = new Textbox(entity!=null?entity.getNoKanwil():"");
		tbNoKanwil.setParent(row);
		row.setParent(rows);
		
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Nama"));
		tbNama = new Textbox(entity!=null?entity.getNama():"");
		tbNama.setParent(row);
		row.setParent(rows);
		
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Alamat"));
		tbAlamat = new Textbox(entity!=null?entity.getAlamat():"");
		tbAlamat.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Kota"));
		tbKota = new Textbox(entity!=null?entity.getKota():"");
		tbKota.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("PIC"));
		tbPIC = new Textbox(entity!=null?entity.getPic():"");
		tbPIC.setParent(row);
		row.setParent(rows);
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Email Kanwil"));
		tbEmailKanwil = new Textbox(entity!=null?entity.getEmailKanwil():"");
		tbEmailKanwil.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Email Supervisi I"));
		tbEmailSupervisi1 = new Textbox(entity!=null?entity.getEmailSupervisi1():"");
		tbEmailSupervisi1.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Email Supervisi 2"));
		tbEmailSupervisi2 = new Textbox(entity!=null?entity.getEmailSupervisi2():"");
		tbEmailSupervisi2.setParent(row);
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
				KanwilDao kanwilDao = HibernateUtil.getKanwilDao();
				Kanwil kanwil;
				
				if(kanwilEntity==null)
					kanwil = new Kanwil();
				else
					kanwil = kanwilEntity;
				
				kanwil.setNama(tbNama.getText());
				kanwil.setKota(tbKota.getText());
				kanwil.setAlamat(tbAlamat.getText());
				kanwil.setNoKanwil(tbNoKanwil.getText());
				kanwil.setPic(tbPIC.getText());
				kanwil.setEmailSupervisi1(tbEmailSupervisi1.getText());
				kanwil.setEmailKanwil(tbEmailKanwil.getText());
				kanwil.setEmailSupervisi2(tbEmailSupervisi2.getText());
				
				if(kanwilEntity==null)
					kanwilDao.insert(kanwil);
				else
					kanwilDao.update(kanwil);
				
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

	public void detail(Kanwil entity) {
		
		this.kanwilEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Kanwil");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("370px");
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
		row.appendChild(new Label("No Kanwil"));
		row.appendChild(new Label(entity.getNoKanwil()));
		row.setParent(rows);
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Nama"));
		row.appendChild(new Label(entity.getNama()));
		row.setParent(rows);
		
			
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Alamat"));
		row.appendChild(new Label(entity.getAlamat()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Kota"));
		row.appendChild(new Label(entity.getKota()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("PIC"));
		row.appendChild(new Label(entity.getPic()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Email Kanwil"));
		row.appendChild(new Label(entity.getEmailKanwil()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Email Supervisi 1"));
		row.appendChild(new Label(entity.getEmailSupervisi1()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Email Supervisi 2"));
		row.appendChild(new Label(entity.getEmailSupervisi2()));
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

	private void initComboArea(){
		cbArea = new Combobox();
		areaDao = HibernateUtil.getAreaDao();
		List<Area> areas = areaDao.getAll();
		
		listModel = new ListModelList<Area>(areas);
		cbArea.setModel(listModel);
		cbArea.setItemRenderer(new AreaRenderer());
		
	}
	
	class AreaRenderer implements ComboitemRenderer<Area>{

		@Override
		public void render(Comboitem item, Area entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			
			item.setValue(entity);
			item.setLabel(entity.getNamaArea());
			
		}
		
	}
		

}
