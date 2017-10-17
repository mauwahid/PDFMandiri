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

public class AreaAction extends GenericForwardComposer<Component> implements Action<Area> {

	private AreaDao areaDao;
	private ListModel listModel;
	private Area areaEntity;
	
	//Component
	Listbox list_area;
	Textbox txt_cari;
	
	//Component By Code
	private Window window;
	private Grid grid;
	private Column column;
	private Row row;
	private Columns columns;
	private Rows rows;
	
	private Textbox tbNamaArea;
	private Textbox tbAlamat;	
	private Textbox tbKota;
	private Textbox tbKodePos;
	private Textbox tbPropinsi;
	private Combobox cbKanwil;
	private KanwilDao kanwilDao;
	
	private Button btnSave;
	private Button btnCancel;
	
	private void loadData(){
		areaDao = HibernateUtil.getAreaDao();
		List<Area> list = areaDao.getAll();
		
		listModel = new SimpleListModel<Area>(list);
		//UI Renderer
		list_area.setModel(listModel);
		list_area.setItemRenderer(new AreaRenderer());
		list_area.renderAll();
	
	}
	

	@Override
	public void showForm(Area entity) {
		
		this.areaEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Cabang");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("300px");
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
		row.appendChild(new Label("Nama"));
		tbNamaArea = new Textbox(entity!=null?entity.getNamaArea():"");
		tbNamaArea.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Kanwil"));
		initComboKanwil();
		if(entity!=null)
			if(entity.getKanwil()!=null)
			{
				Comboitem item = new Comboitem();
				item.setValue(entity.getKanwil());
				item.setLabel(entity.getKanwil().getNama());
				item.setParent(cbKanwil);
				cbKanwil.setSelectedItem(item);
				
			}
		cbKanwil.setReadonly(true);
		cbKanwil.setParent(row);
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
		row.appendChild(new Label("Kode Pos"));
		tbKodePos = new Textbox(entity!=null?entity.getKodePos():"");
		tbKodePos.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Propinsi"));
		tbPropinsi = new Textbox(entity!=null?entity.getPropinsi():"");
		tbPropinsi.setParent(row);
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
				AreaDao areaDao = HibernateUtil.getAreaDao();
				Area area;
				
				if(areaEntity==null)
					area = new Area();
				else
					area = areaEntity;
				
				area.setNamaArea(tbNamaArea.getText());
				area.setAlamat(tbAlamat.getText());
				area.setKota(tbKota.getText());
				area.setKodePos(tbKodePos.getText());
				area.setKanwil((Kanwil)cbKanwil.getSelectedItem().getValue());
				area.setPropinsi(tbPropinsi.getText());
				
				if(areaEntity==null)
					areaDao.insert(area);
				else
					areaDao.update(area);
				
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
		areaDao = HibernateUtil.getAreaDao();
		List<Area> list = areaDao.getAllByName(txt_cari.getText());
		
		listModel = new SimpleListModel<Area>(list);
		//UI Renderer
		list_area.setModel(listModel);
		list_area.setItemRenderer(new AreaRenderer());
		list_area.renderAll();
	}

	public void deleteData(Area entity) {
		// TODO Auto-generated method stub
		AreaDao dao = HibernateUtil.getAreaDao();
		dao.delete(entity);
		Messagebox.show("Berhasil dihapus", "Hapus data", Messagebox.OK, null, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				loadData();
			}
		});
		
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		loadData();
	}

	class AreaRenderer implements ListitemRenderer<Area>{

		
		@Override
		public void render(Listitem item, Area area, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final Area dataTemp = area;
			index = index + 1;
		
		//	new Listcell(dataTemp.getId()+"").setParent(item);
			new Listcell(dataTemp.getNamaArea()).setParent(item);
			new Listcell(dataTemp.getKanwil()!=null?dataTemp.getKanwil().getNama():"").setParent(item);
			new Listcell(dataTemp.getAlamat()).setParent(item);
			new Listcell(dataTemp.getKota()).setParent(item);
			new Listcell(dataTemp.getKodePos()).setParent(item);
			new Listcell(dataTemp.getPropinsi()).setParent(item);
			
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
	
	public void detail(Area entity) {
		
		this.areaEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Cabang "+entity.getNamaArea());
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("300px");
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
		row.appendChild(new Label("Nama"));
		row.appendChild(new Label(entity.getNamaArea()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Kanwil"));
		row.appendChild(new Label(entity.getKanwil()!=null?entity.getKanwil().getNama():""));
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
		row.appendChild(new Label("Kode Pos"));
		row.appendChild(new Label(entity.getKodePos()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Propinsi"));
		row.appendChild(new Label(entity.getPropinsi()));
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

	private void initComboKanwil(){
		cbKanwil = new Combobox();
		kanwilDao = HibernateUtil.getKanwilDao();
		List<Kanwil> kanwils = kanwilDao.getAllAsc();
		
		listModel = new ListModelList<>(kanwils);
		cbKanwil.setModel(listModel);
		cbKanwil.setItemRenderer(new KanwilRenderer());
		
	}
	
	class KanwilRenderer implements ComboitemRenderer<Kanwil>{
	
		@Override
		public void render(Comboitem item, Kanwil entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			
			item.setValue(entity);
			item.setLabel(entity.getNama());
			
		}
		
	}
	

}
