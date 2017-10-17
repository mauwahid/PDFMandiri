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

import pdf.mandiri.action.AreaAction.AreaRenderer;
import pdf.mandiri.dao.AnchorDao;
import pdf.mandiri.dao.BUCAnchorDao;
import pdf.mandiri.domain.Anchor;
import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.util.HibernateUtil;

public class AnchorAction extends GenericForwardComposer<Component> implements Action<Anchor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2009065044486602670L;
	private AnchorDao anchorDao;
	private ListModel listModel;
	private Anchor anchorEntity;
	private BUCAnchorDao bucAnchorDao;
	//Component
	Listbox list_anchor;
	Textbox txt_cari;
	//Component By Code
	private Window window;
	private Grid grid;
	private Column column;
	private Row row;
	private Columns columns;
	private Rows rows;
	
	private Textbox tbNama;
	private Textbox tbGrupNasabah;
	private Textbox tbRm;
	private Textbox tbSales;
	private Textbox tbRepSalesOfficer;
	private Button btnSave;
	private Button btnCancel;
	
	private Combobox cbBUCAnchor;
	
	
	private void loadData(){
		anchorDao = HibernateUtil.getAnchorDao();
		List<Anchor> list = anchorDao.getAll();
		
		listModel = new SimpleListModel<Anchor>(list);
		//UI Renderer
		list_anchor.setModel(listModel);
		list_anchor.setItemRenderer(new AnchorRenderer());
		list_anchor.renderAll();
	
	}
	

	@Override
	public void showForm(Anchor entity) {
		
		this.anchorEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Anchor");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("275px");
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
		row.appendChild(new Label("Grup Nasabah"));
		tbGrupNasabah = new Textbox(entity!=null?entity.getGrupNasabah():"");
		tbGrupNasabah.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("BUC"));
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
		cbBUCAnchor.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("RM"));
		tbRm = new Textbox(entity!=null?entity.getRm():"");
		tbRm.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Sales"));
		tbSales = new Textbox(entity!=null?entity.getSales():"");
		tbSales.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Rep Sales Off"));
		tbRepSalesOfficer = new Textbox(entity!=null?entity.getRepSalesOfficer():"");
		tbRepSalesOfficer.setParent(row);
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
				AnchorDao anchorDao = HibernateUtil.getAnchorDao();
				Anchor anchor;
				
				if(anchorEntity==null)
					anchor = new Anchor();
				else
					anchor = anchorEntity;
				
				anchor.setNama(tbNama.getText());
				anchor.setGrupNasabah(tbGrupNasabah.getText());
				anchor.setBuc((BUCAnchor)cbBUCAnchor.getSelectedItem().getValue());
				anchor.setRepSalesOfficer(tbRepSalesOfficer.getText());
				anchor.setRm(tbRm.getText());
				anchor.setSales(tbSales.getText());
				
				if(anchorEntity==null)
					anchorDao.insert(anchor);
				else
					anchorDao.update(anchor);
				
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
		anchorDao = HibernateUtil.getAnchorDao();
		List<Anchor> list = anchorDao.getAllByName(txt_cari.getText());
		
		listModel = new SimpleListModel<Anchor>(list);
		//UI Renderer
		list_anchor.setModel(listModel);
		list_anchor.setItemRenderer(new AnchorRenderer());
		list_anchor.renderAll();
	}

	public void deleteData(Anchor entity) {
		// TODO Auto-generated method stub
		AnchorDao dao = HibernateUtil.getAnchorDao();
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

	class AnchorRenderer implements ListitemRenderer<Anchor>{

		
		@Override
		public void render(Listitem item, Anchor anchor, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final Anchor dataTemp = anchor;
			index = index + 1;
		
		//	new Listcell(dataTemp.getId()+"").setParent(item);
			new Listcell(dataTemp.getNama()).setParent(item);
			new Listcell(dataTemp.getGrupNasabah()).setParent(item);
			new Listcell(dataTemp.getBuc().getKodeBuc()).setParent(item);
			new Listcell(dataTemp.getRm()).setParent(item);
			new Listcell(dataTemp.getSales()).setParent(item);
			new Listcell(dataTemp.getRepSalesOfficer()).setParent(item);
			
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

	public void detail(Anchor entity) {
		
		this.anchorEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Anchor "+entity.getNama());
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("270px");
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
		row.appendChild(new Label("Grup Nasabah"));
		row.appendChild(new Label(entity.getGrupNasabah()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Grup Pengelola"));
		row.appendChild(new Label(entity.getBuc().getNamaUnitKerja()));
		
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("RM"));
		row.appendChild(new Label(entity.getRm()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Sales"));
		row.appendChild(new Label(entity.getSales()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Rep Sales Off"));
		row.appendChild(new Label(entity.getRepSalesOfficer()));
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

