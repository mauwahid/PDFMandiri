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

import pdf.mandiri.dao.BUCAnchorDao;
import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.util.HibernateUtil;

public class BUCAction extends GenericForwardComposer<Component> implements Action<BUCAnchor>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9151676331229686414L;
	private BUCAnchorDao bucDao;
	private SimpleListModel<BUCAnchor> listModel;
	private BUCAnchor bucEntity;
	
	//Component
	Listbox list_buc;
	Textbox txt_cari;
	
	//Component By Code
	private Window window;
	private Grid grid;
	private Column column;
	private Row row;
	private Columns columns;
	private Rows rows;
	
	private Textbox tbKodeBuc;
	private Textbox tbNamaUnitKerja;
	private Textbox tbDeskripsiGrup;
	private Textbox tbDeskripsiDirektorat;
	
	private Button btnSave;
	private Button btnCancel;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		loadData();
	}

	
	private void loadData(){
		bucDao = HibernateUtil.getBucAnchorDao();
		List<BUCAnchor> list = bucDao.getAll();
		
		listModel = new SimpleListModel<BUCAnchor>(list);
		//UI Renderer
		list_buc.setModel(listModel);
		list_buc.setItemRenderer(new BUCRenderer());
		list_buc.renderAll();
	
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
		bucDao = HibernateUtil.getBucAnchorDao();
		List<BUCAnchor> list = bucDao.getAllByName(txt_cari.getText());
		
		listModel = new SimpleListModel<BUCAnchor>(list);
		//UI Renderer
		list_buc.setModel(listModel);
		list_buc.setItemRenderer(new BUCRenderer());
		list_buc.renderAll();
	}

	public void deleteData(BUCAnchor entity) {
		// TODO Auto-generated method stub
		BUCAnchorDao dao = HibernateUtil.getBucAnchorDao();
		dao.delete(entity);
		Messagebox.show("Berhasil dihapus", "Hapus data", Messagebox.OK, null, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				loadData();
			}
		});
		
	}

	
	class BUCRenderer implements ListitemRenderer<BUCAnchor>{

		
		@Override
		public void render(Listitem item, BUCAnchor entity, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final BUCAnchor dataTemp = entity;
			index = index + 1;
		
		//	new Listcell(dataTemp.getId()+"").setParent(item);
			new Listcell(dataTemp.getKodeBuc()).setParent(item);
			new Listcell(dataTemp.getNamaUnitKerja()).setParent(item);
			new Listcell(dataTemp.getDeskripsiGrup()).setParent(item);
			new Listcell(dataTemp.getDeskripsiDirektorat()).setParent(item);
			
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

			item.addEventListener("onDoubleClick", new EventListener<Event>() {
				
				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					
					detail(dataTemp);
				}
			});
			

			btnDelete.setParent(box);
		
			Listcell listCell = new Listcell();
			box.setParent(listCell);
			listCell.setParent(item);
		}
		
	}


	@Override
	public void showForm(BUCAnchor entity) {
		
		this.bucEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form BUC");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("220px");
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
		row.appendChild(new Label("Kode BUC"));
		tbKodeBuc = new Textbox(entity!=null?entity.getKodeBuc():"");
		tbKodeBuc.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Nama Unit Kerja"));
		tbNamaUnitKerja = new Textbox(entity!=null?entity.getNamaUnitKerja():"");
		tbNamaUnitKerja.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Deskripsi Grup"));
		tbDeskripsiGrup = new Textbox(entity!=null?entity.getDeskripsiGrup():"");
		tbDeskripsiGrup.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Deskripsi Direktorat"));
		tbDeskripsiDirektorat = new Textbox(entity!=null?entity.getDeskripsiDirektorat():"");
		tbDeskripsiDirektorat.setParent(row);
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
				BUCAnchorDao bucDao = HibernateUtil.getBucAnchorDao();
				BUCAnchor buc;
				
				if(bucEntity==null)
					buc = new BUCAnchor();
				else
					buc = bucEntity;
				
				buc.setKodeBuc(tbKodeBuc.getText());
				buc.setNamaUnitKerja(tbNamaUnitKerja.getText());
				buc.setDeskripsiGrup(tbDeskripsiGrup.getText());
				buc.setDeskripsiDirektorat(tbDeskripsiDirektorat.getText());
				
				if(bucEntity==null)
					bucDao.insert(buc);
				else
					bucDao.update(buc);
				
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

	
	public void detail(BUCAnchor entity) {
		
		this.bucEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Detail BUC "+entity.getNamaUnitKerja());
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("220px");
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
		row.appendChild(new Label("Kode BUC"));
		row.appendChild(new Label(entity.getKodeBuc()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Nama Unit Kerja"));
		row.appendChild(new Label(entity.getNamaUnitKerja()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Deskripsi Grup"));
		row.appendChild(new Label(entity.getDeskripsiGrup()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Deskripsi Direktorat"));
		row.appendChild(new Label(entity.getDeskripsiDirektorat()));
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
