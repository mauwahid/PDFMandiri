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

import pdf.mandiri.action.CustomerTypeAction.CustomerTypeRenderer;
import pdf.mandiri.action.PenggunaAction.PenggunaRenderer;
import pdf.mandiri.action.ToKanwilAction.KanwilRenderer;
import pdf.mandiri.common.CommonCode;
import pdf.mandiri.dao.KanwilDao;
import pdf.mandiri.dao.PenggunaDao;
import pdf.mandiri.domain.CustomerType;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.Pengguna;
import pdf.mandiri.util.HibernateUtil;

public class PenggunaAction extends GenericForwardComposer<Component> implements Action<Pengguna>{

	private PenggunaDao penggunaDao;
	private ListModel listModel;
	private Pengguna penggunaEntity;
	private KanwilDao kanwilDao;
	
	//Component
	Listbox list_pengguna;
	Textbox txt_cari;
	
	//Component By Code
	private Window window;
	private Grid grid;
	private Column column;
	private Row row;
	private Columns columns;
	private Rows rows;
	
	private Textbox tbUsername;
	private Textbox tbNama;
	private Textbox tbAlamat;
	private Textbox tbPassword;
	private Textbox tbEmail;
	private Textbox tbNoTelp;
	private Textbox tbPassEmail;
	private Combobox cbKanwil;
	private Combobox cbStatus;
	private Button btnSave;
	private Button btnCancel;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		loadData();
	}

	
	private void loadData(){
		penggunaDao = HibernateUtil.getPenggunaDao();
		List<Pengguna> list = penggunaDao.getAll();
		
		listModel = new SimpleListModel<Pengguna>(list);
		//UI Renderer
		list_pengguna.setModel(listModel);
		list_pengguna.setItemRenderer(new PenggunaRenderer());
		list_pengguna.renderAll();
	
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
		penggunaDao = HibernateUtil.getPenggunaDao();
		List<Pengguna> list = penggunaDao.getAllByName(txt_cari.getText());
		
		listModel = new SimpleListModel<Pengguna>(list);
		//UI Renderer
		list_pengguna.setModel(listModel);
		list_pengguna.setItemRenderer(new PenggunaRenderer());
		list_pengguna.renderAll();
	}

	public void deleteData(Pengguna entity) {
		// TODO Auto-generated method stub
		PenggunaDao dao = HibernateUtil.getPenggunaDao();
		dao.delete(entity);
		Messagebox.show("Berhasil dihapus", "Hapus data", Messagebox.OK, null, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				loadData();
			}
		});
		
	}

	
	class PenggunaRenderer implements ListitemRenderer<Pengguna>{

		String statusString = "Kanwil";
		@Override
		public void render(Listitem item, Pengguna entity, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final Pengguna dataTemp = entity;
			index = index + 1;
		
		//	new Listcell(dataTemp.getId()+"").setParent(item);
			new Listcell(dataTemp.getUsername()).setParent(item);
			new Listcell(dataTemp.getNama()).setParent(item);
			new Listcell(dataTemp.getAlamat()).setParent(item);
			new Listcell(dataTemp.getEmail()).setParent(item);
			new Listcell(dataTemp.getKanwil()!=null?dataTemp.getKanwil().getNama():"").setParent(item);
			if(dataTemp.getStatus()==1)
				statusString = "Kanwil";
			else if(dataTemp.getStatus()==2)
				statusString = "Pusat";
			else if(dataTemp.getStatus()==3)
				statusString = "Administrator";
			new Listcell(statusString).setParent(item);
				
			Box box = new Hbox();
			Button btnUpdate = new Button("Update");
			
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
	public void showForm(Pengguna entity) {
		
		this.penggunaEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Pengguna");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("350px");
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
		row.appendChild(new Label("Alamat"));
		tbAlamat = new Textbox(entity!=null?entity.getAlamat():"");
		tbAlamat.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Username"));
		tbUsername = new Textbox(entity!=null?entity.getUsername():"");
		tbUsername.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Password"));
		tbPassword = new Textbox();
		tbPassword.setType("password");
		tbPassword.setText(entity!=null?entity.getPassword():"");
		tbPassword.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Email"));
		tbEmail = new Textbox(entity!=null?entity.getEmail():"");
		tbEmail.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("No Telp"));
		tbNoTelp = new Textbox(entity!=null?entity.getNoTelp():"");
		tbNoTelp.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Rule"));
		initComboStatus();
		if(entity!=null)
			if(entity.getStatus()!=0)
			{
				if(entity.getStatus()==1)
					cbStatus.setSelectedIndex(0);
				else if(entity.getStatus()==2)
					cbStatus.setSelectedIndex(1);
				else 
					cbStatus.setSelectedIndex(2);
				
				
				
			}
		cbStatus.setReadonly(true);
		cbStatus.setParent(row);
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
		row.appendChild(new Label(""));
		Box box = new Hbox();
		btnSave = new Button("Simpan");
		btnCancel = new Button("Batal");
		row.setParent(rows);
		
		btnSave.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				PenggunaDao penggunaDao = HibernateUtil.getPenggunaDao();
				Pengguna pengguna;
				
				if(penggunaEntity==null)
					pengguna = new Pengguna();
				else
					pengguna = penggunaEntity;
				
				pengguna.setNama(tbNama.getText());
				pengguna.setAlamat(tbAlamat.getText());
				pengguna.setNoTelp(tbNoTelp.getText());
				pengguna.setUsername(tbUsername.getText());
				pengguna.setPassword(tbPassword.getText());
				pengguna.setEmail(tbEmail.getText());
				
				pengguna.setStatus((Integer)cbStatus.getSelectedItem().getValue());
				if(cbKanwil.getSelectedItem()!=null)
					pengguna.setKanwil((Kanwil)cbKanwil.getSelectedItem().getValue());
				
				if(penggunaEntity==null)
					penggunaDao.insert(pengguna);
				else
					penggunaDao.update(pengguna);
				
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
	
	
	public void detail(Pengguna entity) {
		
		this.penggunaEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Pengguna");
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
		row.appendChild(new Label(entity.getNama()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Alamat"));
		row.appendChild(new Label(entity.getAlamat()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Username"));
		row.appendChild(new Label(entity.getUsername()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Email"));
		row.appendChild(new Label(entity.getEmail()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("No Telp"));
		row.appendChild(new Label(entity.getNoTelp()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Rule"));
		String status = "PUSAT";
		if(entity.getStatus()==CommonCode.KANWIL){
			status = "Kanwil "+entity.getKanwil().getNama();
			
		}
		
		row.appendChild(new Label(status));
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
	
	void initComboKanwil(){
		cbKanwil = new Combobox();
		kanwilDao = HibernateUtil.getKanwilDao();
		List<Kanwil> KanwilList = kanwilDao.getAll();
		listModel = new ListModelList<>(KanwilList);
		cbKanwil.setModel(listModel);
		cbKanwil.setItemRenderer(new KanwilRenderer());
		
	}
	
	void initComboStatus(){
		cbStatus = new Combobox();
		Comboitem item;
		item = new Comboitem();
		item.setValue(1);
		item.setLabel("Kanwil");
		item.setParent(cbStatus);
		
		item = new Comboitem();
		item.setValue(2);
		item.setLabel("Pusat");
		item.setParent(cbStatus);
		
		item = new Comboitem();
		item.setValue(3);
		item.setLabel("Admin");
		item.setParent(cbStatus);
		
		cbStatus.setSelectedIndex(0);
		
		cbStatus.addEventListener("onChange", new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub
				if(cbStatus.getSelectedIndex()==0)
					cbKanwil.setVisible(true);
				else
					cbKanwil.setVisible(false);
			}
		});
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
