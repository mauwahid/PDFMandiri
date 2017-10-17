package pdf.mandiri.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.zkoss.zul.Datebox;
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
import pdf.mandiri.dao.BUCAnchorDao;
import pdf.mandiri.dao.DistributorDao;
import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.Distributor;
import pdf.mandiri.util.HibernateUtil;

public class DistributorAction extends GenericForwardComposer<Component> implements Action<Distributor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7001634480882166268L;
	private DistributorDao distributorDao;
	private ListModel listModel;
	private Distributor distributorEntity;
	private BUCAnchorDao bucAnchorDao;
	private AreaDao areaDao;
	
	
	//Component
	Listbox list_distributor;
	Textbox txt_cari;
	
	//Component By Code
	private Window window;
	private Grid grid;
	private Column column;
	private Row row;
	private Columns columns;
	private Rows rows;
	
	private Textbox tbNama;
	private Textbox tbKota;
	private Textbox tbNoTelp;
	private Textbox tbNamaPIC;
	private Textbox tbNPWP;
	private Textbox tbAlamat;
	private Textbox tbOutlet;
	private Combobox cbBUCAnchor;
	private Datebox dateBerdiri;
	private Combobox cbArea;
	
	private Button btnSave;
	private Button btnCancel;
	private DateFormat dateFormat;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		formatDate();
		loadData();
	}
	
	private void formatDate(){
		dateFormat = new SimpleDateFormat("dd/MM/YYYY");
	}

	
	private void loadData(){
		distributorDao = HibernateUtil.getDistributorDao();
		List<Distributor> list = distributorDao.getAll();
		
		listModel = new SimpleListModel<Distributor>(list);
		//UI Renderer
		
		list_distributor.setModel(listModel);
		list_distributor.setItemRenderer(new DistributorRenderer());
		list_distributor.renderAll();
	
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
		distributorDao = HibernateUtil.getDistributorDao();
		List<Distributor> list = distributorDao.getAllByName(txt_cari.getText());
		
		listModel = new SimpleListModel<Distributor>(list);
		//UI Renderer
		list_distributor.setModel(listModel);
		list_distributor.setItemRenderer(new DistributorRenderer());
		list_distributor.renderAll();
	}

	public void deleteData(Distributor entity) {
		// TODO Auto-generated method stub
		DistributorDao dao = HibernateUtil.getDistributorDao();
		dao.delete(entity);
		Messagebox.show("Berhasil dihapus", "Hapus data", Messagebox.OK, null, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				loadData();
			}
		});
		
	}

	
	class DistributorRenderer implements ListitemRenderer<Distributor>{

		
		@Override
		public void render(Listitem item, Distributor entity, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final Distributor dataTemp = entity;
			index = index + 1;
		
		//	new Listcell(dataTemp.getId()+"").setParent(item);
			new Listcell(dataTemp.getNama()).setParent(item);
			new Listcell(dataTemp.getOutlet()).setParent(item);
			new Listcell(dataTemp.getBuc()!=null?dataTemp.getBuc().getKodeBuc():"").setParent(item);
			new Listcell(dataTemp.getArea()!=null?dataTemp.getArea().getNamaArea():"").setParent(item);
			new Listcell(dataTemp.getAlamat()).setParent(item);
			new Listcell(dataTemp.getKota()).setParent(item);
			new Listcell(dataTemp.getNoTelp()).setParent(item);
			new Listcell(dataTemp.getNamaPIC()).setParent(item);
			new Listcell(dataTemp.getTanggalBerdiri()!=null?dateFormat.format(dataTemp.getTanggalBerdiri()):"").setParent(item);
			new Listcell(dataTemp.getNpwp()).setParent(item);
				
			Box box = new Hbox();
			Button btnUpdate = new Button("Update");
			btnUpdate.setMold("trendy");
			btnUpdate.addEventListener("onClick", new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					showForm(dataTemp);
				}
			
			});
			
			item.addEventListener("onDoubleClick", new EventListener<Event>() {
				
				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					
					detail(dataTemp);
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
	public void showForm(Distributor entity) {
		
		this.distributorEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Distributor");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("410px");
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
		tbNama = new Textbox(entity!=null?entity.getNama():"");
		tbNama.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Outlet"));
		tbOutlet = new Textbox(entity!=null?entity.getNama():"");
		tbOutlet.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("BUC Distributor"));
		initComboBUCAnchor();
		if(entity!=null)
			if(entity.getBuc()!=null)
			{
				Comboitem item = new Comboitem();
				item.setValue(entity.getBuc());
				item.setLabel(entity.getBuc().getNamaUnitKerja());
				item.setParent(cbBUCAnchor);
				cbBUCAnchor.setSelectedItem(item);
				
			}
		cbBUCAnchor.setReadonly(true);
		cbBUCAnchor.setAutocomplete(true);
		
		cbBUCAnchor.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Area"));
		initComboArea();
		if(entity!=null)
			if(entity.getArea()!=null)
			{
				Comboitem item = new Comboitem();
				item.setValue(entity.getArea());
				item.setLabel(entity.getArea().getNamaArea());
				item.setParent(cbArea);
				cbArea.setSelectedItem(item);
				
			}
		cbArea.setReadonly(true);
		cbArea.setAutocomplete(true);
		
		cbArea.setParent(row);
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
		row.appendChild(new Label("Telp"));
		tbNoTelp = new Textbox(entity!=null?entity.getNoTelp():"");
		tbNoTelp.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Nama PIC"));
		tbNamaPIC = new Textbox(entity!=null?entity.getNamaPIC():"");
		tbNamaPIC.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Tanggal Berdiri"));
		dateBerdiri = new Datebox(entity==null?null:entity.getTanggalBerdiri());
		dateBerdiri.setReadonly(true);
		dateBerdiri.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("NPWP"));
		tbNPWP = new Textbox(entity!=null?entity.getNpwp():"");
		tbNPWP.setParent(row);
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
				DistributorDao distributorDao = HibernateUtil.getDistributorDao();
				Distributor distributor;
				
				if(distributorEntity==null)
					distributor = new Distributor();
				else
					distributor = distributorEntity;
				
				distributor.setNama(tbNama.getText());
				distributor.setKota(tbKota.getText());
				distributor.setNoTelp(tbNoTelp.getText());
				distributor.setNamaPIC(tbNamaPIC.getText());
				distributor.setOutlet(tbOutlet.getText());
				distributor.setTanggalBerdiri(dateBerdiri.getValue());
				distributor.setNpwp(tbNPWP.getText());
				if(cbArea.getSelectedItem()!=null)
					distributor.setArea((Area)cbArea.getSelectedItem().getValue());
				distributor.setAlamat(tbAlamat.getText());

				if(cbBUCAnchor.getSelectedItem()!=null)
					distributor.setBuc((BUCAnchor)cbBUCAnchor.getSelectedItem().getValue());
				
				if(distributorEntity==null)
					distributorDao.insert(distributor);
				else
					distributorDao.update(distributor);
				
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
	
	public void detail(Distributor entity) {
		
		this.distributorEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Distributor");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("410px");
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
		row.appendChild(new Label(entity.getNama()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Outlet"));
		row.appendChild(new Label(entity.getNama()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("BUC Distributor"));
		row.appendChild(new Label(entity.getBuc().getNamaUnitKerja()));
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
		row.appendChild(new Label("Telp"));
		row.appendChild(new Label(entity.getNoTelp()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Nama PIC"));
		row.appendChild(new Label(entity.getNamaPIC()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Tanggal Berdiri"));
		row.appendChild(new Label(dateFormat.format(entity.getTanggalBerdiri())));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("NPWP"));
		row.appendChild(new Label(entity.getNpwp()));
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
	
	private void initComboBUCAnchor(){
		cbBUCAnchor = new Combobox();
		bucAnchorDao = HibernateUtil.getBucAnchorDao();
		List<BUCAnchor> bucAnchors = bucAnchorDao.getAllAsc();
		
		listModel = new ListModelList<>(bucAnchors);
		cbBUCAnchor.setModel(listModel);
		cbBUCAnchor.setItemRenderer(new BUCAnchorRenderer());
		
	}
	
	class BUCAnchorRenderer implements ComboitemRenderer<BUCAnchor>{

		@Override
		public void render(Comboitem item, BUCAnchor entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			
			item.setValue(entity);
			item.setLabel(entity.getKodeBuc());
			
		}
		
	}

	private void initComboArea(){
		cbArea = new Combobox();
		areaDao = HibernateUtil.getAreaDao();
		List<Area> areas = areaDao.getAllAsc();
		
		listModel = new ListModelList<>(areas);
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
