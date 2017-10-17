package pdf.mandiri.action.kanwil;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pdf.mandiri.action.Action;
import pdf.mandiri.action.FasilitasAction;
import pdf.mandiri.dao.AnchorDao;
import pdf.mandiri.dao.AreaDao;
import pdf.mandiri.dao.BUCAnchorDao;
import pdf.mandiri.dao.DistributionFinanceDao;
import pdf.mandiri.dao.DistributorDao;
import pdf.mandiri.dao.KanwilDao;
import pdf.mandiri.dao.KreditTypeDao;
import pdf.mandiri.dao.LogStatusDao;
import pdf.mandiri.dao.PenggunaDao;
import pdf.mandiri.dao.StatusDao;
import pdf.mandiri.domain.Anchor;
import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.Distributor;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.KreditType;
import pdf.mandiri.domain.LogStatus;
import pdf.mandiri.domain.Pengguna;
import pdf.mandiri.domain.Status;
import pdf.mandiri.util.HibernateUtil;

public class DataKirimanAction extends GenericForwardComposer<Component> implements Action<DistributionFinance> {
	private DistributionFinanceDao distributionFinanceDao;
	private DistributionFinance distributionFinanceEntity;
	private DateFormat dateFormat;
	private Pengguna pengguna;
	private Kanwil kanwil;
	
	//DAO
	private AnchorDao anchorDao;
	private BUCAnchorDao bucDao;
	private DistributorDao distributorDao;
	private KanwilDao kanwilDao;
	private KreditTypeDao kreditTypeDao;
	private StatusDao statusDao;
	private LogStatusDao logStatusDao;
	private LogStatus logStatus;
	private AreaDao areaDao;
	//private KreditTypeDao fasilitasDao;
	
	ListModel listModel;
	Listbox listLogStatus;
	Listhead listHead;
	Listitem listItem;
	Listhead listHeadLog;
	
	//Component
	Listbox list_to_kanwil;
	Textbox txt_cari;
	Combobox cb_distributor;
	Combobox cb_anchor;
	Combobox cb_kanwil;
	Combobox cb_buc;
	Combobox cb_area;
	Textbox tb_fasilitas;
	
	//Component By Code
	private Window window;
	private Grid grid;
	private Column column;
	private Row row;
	private Columns columns;
	private Rows rows;
	
	private Combobox cbAnchor;
	private Combobox cbDistributor;
	private Combobox cbKanwil;
	private Combobox cbBUC;
	private Textbox tbLimit;
	private Textbox tbOutstanding;
	private Datebox dbTanggal;
	private Combobox cbKreditType;
	private Combobox cbStatus;
	
	private Textbox tbKeterangan;
	 private DecimalFormatSymbols symbol;
	  
	
	private Button btnSave;
	private Button btnCancel;
	private NumberFormat f = NumberFormat.getInstance();
	private NumberFormat numFormat;
	
	DecimalFormat df = new DecimalFormat("#");
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		
		
	 
		
		loadSession();
		loadVCombo();
		loadData();
		
	}
	
	private void loadSession(){
		pengguna = (Pengguna)session.getAttribute("pengguna");
		PenggunaDao penggunaDao = HibernateUtil.getPenggunaDao();
		Pengguna penggunaEntity = penggunaDao.getById(pengguna.getId());
		Kanwil kanwilSementara = penggunaEntity.getKanwil();
		KanwilDao kanwilDao = HibernateUtil.getKanwilDao();
		kanwil = kanwilDao.getById(kanwilSementara.getId());
	}

	
	private void loadData(){
		
		
		distributionFinanceDao = HibernateUtil.getDistributionFinanceDao();
		List<DistributionFinance> list = distributionFinanceDao.getKanwil(kanwil);
		System.out.println("Kanwil "+kanwil.getNama());
		
		listModel = new SimpleListModel<DistributionFinance>(list);
		//UI Renderer
		list_to_kanwil.setModel(listModel);
		
			
		list_to_kanwil.setItemRenderer(new DistributionFinanceRenderer());
		list_to_kanwil.renderAll();
	
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
	
	
	
	public void deleteData(DistributionFinance entity) {
		// TODO Auto-generated method stub
		DistributionFinanceDao dao = HibernateUtil.getDistributionFinanceDao();
		dao.delete(entity);
		Messagebox.show("Berhasil dihapus", "Hapus data", Messagebox.OK, null, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				loadData();
			}
		});
		
	}

	
	class DistributionFinanceRenderer implements ListitemRenderer<DistributionFinance>{

		
		@Override
		public void render(Listitem item, DistributionFinance entity, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final DistributionFinance dataTemp = entity;
			index = index + 1;
				
		
			new Listcell(dataTemp.getId()+"").setParent(item);
			new Listcell(dataTemp.getAnchor().getNama()).setParent(item);
			new Listcell(dataTemp.getDistributor().getNama()).setParent(item);
			new Listcell(dataTemp.getBuc().getKodeBuc()).setParent(item);
			new Listcell(dataTemp.getArea()!=null?dataTemp.getArea().getNamaArea():"").setParent(item);
			new Listcell(dataTemp.getFasilitasString()).setParent(item);
			if(dataTemp.getCurrentStatus()!=null)
				new Listcell(dataTemp.getCurrentStatus().getStatusName()).setParent(item);
			else
				new Listcell("Belum diubah oleh Kanwil").setParent(item);
			
			if(dataTemp.getTanggal()!=null)
				new Listcell(dateFormat.format(dataTemp.getTanggal())).setParent(item);
			else
				new Listcell("Belum Diisi").setParent(item);
			new Listcell(dataTemp.getKeterangan()).setParent(item);
			
			Box box = new Hbox();
			Button btnLog = new Button("Log");
			btnLog.setMold("trendy");
			btnLog.addEventListener("onClick", new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					showLog(dataTemp);
				}
			
			});
			

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
			
			btnLog.setParent(box);
			btnUpdate.setParent(box);
			
			Listcell listCell = new Listcell();
			box.setParent(listCell);
			listCell.setParent(item);
		}
		
	}


	@Override
	public void showForm(DistributionFinance entity) {
		
		this.distributionFinanceEntity = entity;
		df.setMaximumFractionDigits(0);

		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Value Chain");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("450px");
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
		row.appendChild(new Label("Anchor"));
		row.appendChild(new Label(entity.getAnchor()!=null?entity.getAnchor().getNama():""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Distributor"));
		row.appendChild(new Label(entity.getDistributor()!=null?entity.getDistributor().getNama():""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Kanwil"));
		row.appendChild(new Label(entity.getKanwil()!=null?entity.getKanwil().getNama():""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("BUC"));
		row.appendChild(new Label(entity.getBuc()!=null?entity.getBuc().getNamaUnitKerja():""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Area"));
		row.appendChild(new Label(entity.getArea()!=null?entity.getArea().getNamaArea():""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Outlet"));
		row.appendChild(new Label(entity.getOutlet()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Jenis Fasilitas"));
		row.appendChild(new Label(entity.getFasilitasString()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("CIF Number"));
		row.appendChild(new Label(entity.getCifNumber()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Acc Number"));
		row.appendChild(new Label(entity.getAccNumber()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Limit"));
		row.appendChild(new Label(f.format(entity.getLimitDF())+""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Outstanding List"));
		tbOutstanding = new Textbox(distributionFinanceEntity!=null?df.format(distributionFinanceEntity.getOutstandingList())+"":"");
		tbOutstanding.setParent(row);

		row.setParent(rows);
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Status"));
		initComboStatus();
		if(entity!=null)
			if(entity.getCurrentStatus()!=null)
			{
				Comboitem item = new Comboitem();
				item.setValue(entity.getCurrentStatus());
				item.setLabel(entity.getCurrentStatus().getStatusName());
				item.setParent(cbStatus);
				cbStatus.setSelectedItem(item);
				
			}
		cbStatus.setReadonly(true);
		cbStatus.setParent(row);
		row.setParent(rows);
		
	
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Tanggal"));
		dbTanggal = new Datebox(entity!=null?entity.getTanggal():null);
		dbTanggal.setReadonly(true);
		dbTanggal.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Keterangan"));
		tbKeterangan = new Textbox(distributionFinanceEntity!=null?distributionFinanceEntity.getKeterangan():"");
		tbKeterangan.setHeight("80px");
		tbKeterangan.setWidth("200px");
		tbKeterangan.setMultiline(true);
		
		tbKeterangan.setParent(row);
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
				DistributionFinanceDao distributionFinanceDao = HibernateUtil.getDistributionFinanceDao();
				DistributionFinance distributionFinance;
				logStatusDao = HibernateUtil.getLogStatusDao();
				logStatus = new LogStatus();
				
		/*		try{
					double limit = 0;
					if(!tbLimit.getValue().contentEquals(""))
					 limit = Double.parseDouble(tbLimit.getText());
					
				}catch(NumberFormatException e){
					Messagebox.show("Input Limit Harus Integer", "Validasi", Messagebox.OK, null, new EventListener<Event>() {
						
						@Override
						public void onEvent(Event event) throws Exception {
							// TODO Auto-generated method stub
							return;
						}
					});
					
				}*/
				
				try{
					double outStanding = 0;
					if(!tbOutstanding.getValue().contentEquals(""))
						outStanding = Double.parseDouble(tbOutstanding.getText());
					
				}catch(NumberFormatException e){
					Messagebox.show("Input Outstanding Harus Integer", "Validasi", Messagebox.OK, null, new EventListener<Event>() {
						
						@Override
						public void onEvent(Event event) throws Exception {
							// TODO Auto-generated method stub
							return;
						}
					});
					
				}
				
				
				
				if(distributionFinanceEntity==null)
					distributionFinance = new DistributionFinance();
				else
					distributionFinance = distributionFinanceEntity;
				
				distributionFinance.setTanggal(dbTanggal.getValue());
			//	distributionFinance.setLimitDF(Integer.parseInt(tbLimit.getText()));
				distributionFinance.setOutstandingList(Integer.parseInt(tbOutstanding.getText()));
				distributionFinance.setKeterangan(tbKeterangan.getValue());
				if(cbStatus.getSelectedItem()!=null)
					distributionFinance.setCurrentStatus((Status)cbStatus.getSelectedItem().getValue());
				
				String out = tbOutstanding.getText();
				String ket =  tbKeterangan.getText();
			// 	Status statusS = null;
				
				logStatus.setKanwil(distributionFinance.getKanwil()!=null?distributionFinance.getKanwil().getNama():"");
				logStatus.setAnchor(distributionFinance.getAnchor()!=null?distributionFinance.getAnchor().getNama():"");
				logStatus.setDistributor(distributionFinance.getDistributor()!=null?distributionFinance.getDistributor().getNama():"");
				logStatus.setKreditType(distributionFinance.getFasilitasString());
				logStatus.setOutstandingList(out);
				logStatus.setLimitDF(df.format(distributionFinance.getLimitDF())+"");
				logStatus.setBUC(distributionFinance.getBuc()!=null?distributionFinance.getBuc().getNamaUnitKerja():"");
				logStatus.setKeterangan(ket);
				logStatus.setStatusDF(cbStatus.getSelectedItem()!=null?cbStatus.getSelectedItem().getLabel():"");
				logStatus.setTanggalDiubah(new Date().toString());
				logStatus.setTanggal(new Date().toString());
				logStatus.setDistriFinance(distributionFinance);
				logStatus.setPengguna(pengguna.getUsername());
				
				
			
				logStatusDao.insert(logStatus);
				System.out.println("Simpan Log");
				
				if(distributionFinanceEntity==null){
					distributionFinanceDao.insert(distributionFinance);
				//	status = (Status) cbStatus.getSelectedItem().getValue();
					
				}
					else
					distributionFinanceDao.update(distributionFinance);
				
				
				
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
	
	
	void initComboAnchor(){
		
		System.out.println("Init combo anchor");
		cbAnchor = new Combobox();
		anchorDao = HibernateUtil.getAnchorDao();
		List<Anchor> anchorList = anchorDao.getAll();
		
		
		listModel = new ListModelList<>(anchorList);
		cbAnchor.setModel(listModel);
		cbAnchor.setItemRenderer(new AnchorRenderer());
		
	}
	
	void initComboBuc(){
		cbBUC = new Combobox();
		bucDao = HibernateUtil.getBucAnchorDao();
		List<BUCAnchor> bucList = bucDao.getAll();
		listModel = new ListModelList<>(bucList);
		cbBUC.setModel(listModel);
		cbBUC.setItemRenderer(new BUCRenderer());
		
	}
	
	class AnchorRenderer implements ComboitemRenderer<Anchor>{

		@Override
		public void render(Comboitem item, Anchor entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
		//	System.out.println("Anchor : "+entity.getNama());
			
			item.setValue(entity);
			item.setLabel(entity.getNama());
			
		}
		
	}
	
	class BUCRenderer implements ComboitemRenderer<BUCAnchor>{

		@Override
		public void render(Comboitem item, BUCAnchor entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			
			item.setValue(entity);
			item.setLabel(entity.getNamaUnitKerja());
			
		}
		
	}


	void initComboDistributor(){
		cbDistributor = new Combobox();
		distributorDao = HibernateUtil.getDistributorDao();
		List<Distributor> DistributorList = distributorDao.getAll();
		listModel = new ListModelList<>(DistributorList);
		cbDistributor.setModel(listModel);
		cbDistributor.setItemRenderer(new DistributorRenderer());
		
	}
	
	class DistributorRenderer implements ComboitemRenderer<Distributor>{

		@Override
		public void render(Comboitem item, Distributor entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			
			item.setValue(entity);
			item.setLabel(entity.getNama());
			
		}
		
	}
	
	void initComboKanwil(Pengguna entity){
		cbKanwil = new Combobox();
		kanwilDao = HibernateUtil.getKanwilDao();
		List<Kanwil> KanwilList = kanwilDao.getAll();
		listModel = new ListModelList<>(KanwilList);
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
	

		void initComboKreditType(){
		cbKreditType = new Combobox();
		kreditTypeDao = HibernateUtil.getKreditTypeDao();
		List<KreditType> KreditTypeList = kreditTypeDao.getAll();
		listModel = new ListModelList<>(KreditTypeList);
		cbKreditType.setModel(listModel);
		cbKreditType.setItemRenderer(new KreditTypeRenderer());
		
	}
	
	class KreditTypeRenderer implements ComboitemRenderer<KreditType>{

		@Override
		public void render(Comboitem item, KreditType entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			
			item.setValue(entity);
			item.setLabel(entity.getKreditType());
			
		}
		
	}


	void initComboStatus(){
	cbStatus = new Combobox();
	statusDao = HibernateUtil.getStatusDao();
	List<Status> StatusList = statusDao.getAll();
	listModel = new ListModelList<>(StatusList);
	cbStatus.setModel(listModel);
	cbStatus.setItemRenderer(new StatusRenderer());
	
	}

	class StatusRenderer implements ComboitemRenderer<Status>{
	
		@Override
		public void render(Comboitem item, Status entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			
			item.setValue(entity);
			item.setLabel(entity.getStatusName());
			
		}
	
	}
	
	
	void showLog(DistributionFinance entity) {
		
		this.distributionFinanceEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Log DistributionFinance");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("1200px");
		window.setHeight("450px");
		window.setVisible(true);
		
		listLogStatus = new Listbox();
		listLogStatus.setVisible(true);
		listLogStatus.setStyle("z");
		listLogStatus.setMold("paging");
		listLogStatus.setPageSize(5);
		
		listLogStatus.setParent(window);
		
		listHeadLog = new Listhead();
		listHeadLog.setParent(listLogStatus);
		
		listItem = new Listitem();
		
		new Listheader("Tanggal Status").setParent(listHeadLog);
		new Listheader("Tanggal Diubah").setParent(listHeadLog);
		new Listheader("User").setParent(listHeadLog);
		new Listheader("Anchor").setParent(listHeadLog);
		new Listheader("Kanwil").setParent(listHeadLog);
		new Listheader("Area").setParent(listHeadLog);
		new Listheader("Distributor").setParent(listHeadLog);
		new Listheader("BUC").setParent(listHeadLog);
		new Listheader("Limit").setParent(listHeadLog);
		new Listheader("Outstanding List").setParent(listHeadLog);
		new Listheader("Jenis Fasilitas").setParent(listHeadLog);
		new Listheader("Keterangan").setParent(listHeadLog);
		new Listheader("Status").setParent(listHeadLog);
		
		logStatusDao = HibernateUtil.getLogStatusDao();
		List<LogStatus> logStatuses = logStatusDao.getByDF(entity);
		listModel = new SimpleListModel<LogStatus>(logStatuses);
		listLogStatus.setModel(listModel);
		
		listLogStatus.setItemRenderer(new LogStatusRenderer());
		
				
		window.onModal();
		
	}
	
	class LogStatusRenderer implements ListitemRenderer<LogStatus>{

		
		@Override
		public void render(Listitem item, LogStatus entity, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final LogStatus dataTemp = entity;
			index = index + 1;
				
		
			new Listcell(dataTemp.getTanggal()).setParent(item);
			new Listcell(dataTemp.getTanggalDiubah()).setParent(item);
			new Listcell(dataTemp.getPengguna()).setParent(item);
			new Listcell(dataTemp.getAnchor()).setParent(item);
			new Listcell(dataTemp.getKanwil()).setParent(item);
			new Listcell(dataTemp.getArea()).setParent(item);
			new Listcell(dataTemp.getDistributor()).setParent(item);
			new Listcell(dataTemp.getBUC()).setParent(item);
			new Listcell(dataTemp.getLimitDF()).setParent(item);
			new Listcell(dataTemp.getOutstandingList()).setParent(item);
			new Listcell(dataTemp.getKreditType()).setParent(item);
			new Listcell(dataTemp.getKeterangan()).setParent(item);
			new Listcell(dataTemp.getStatusDF()).setParent(item);
		}
	}

	public void onSearch() {
		
		Anchor anchor = null;
		Distributor distributor = null;
		BUCAnchor buc = null;
		Area area = null;
		String fasilitas = "";
		
		
		// TODO Auto-generated method stub
		distributionFinanceDao = HibernateUtil.getDistributionFinanceDao();
		if(cb_anchor.getSelectedItem()!=null)
			anchor = (Anchor)cb_anchor.getSelectedItem().getValue();
		
		fasilitas = tb_fasilitas.getValue();
		
		if(cb_buc.getSelectedItem()!=null)
			buc = (BUCAnchor) cb_buc.getSelectedItem().getValue();
		if(cb_area.getSelectedItem()!=null)
			area = (Area) cb_area.getSelectedItem().getValue();
		
		List<DistributionFinance> list = distributionFinanceDao.getAnchorFasilitasKanwilBUCArea(anchor, fasilitas, kanwil, buc, area);		
		listModel = new SimpleListModel<DistributionFinance>(list);
		//UI Renderer
		list_to_kanwil.setModel(listModel);
		list_to_kanwil.setItemRenderer(new DistributionFinanceRenderer());
		list_to_kanwil.renderAll();
		//setCheckBox();
	}

	void loadVCombo(){
		initComboVAnchor();
	
		initComboVArea();
		initComboVBuc();
	}
	

	void initComboVAnchor(){
		anchorDao = HibernateUtil.getAnchorDao();
		List<Anchor> anchors = anchorDao.getAll();
		Anchor anchor = new Anchor();
		anchor.setNama("- Semua -");
		anchors.add(0,anchor);
		listModel = new ListModelList<>(anchors);
		cb_anchor.setModel(listModel);
		cb_anchor.setItemRenderer(new AnchorVRenderer());
		
		
	}
	
	class AnchorVRenderer implements ComboitemRenderer<Anchor>{

		@Override
		public void render(Comboitem item, Anchor entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
		//	System.out.println("Anchor : "+entity.getNama());
			
			if(idx == 0)
			{
				item.setValue(null);
				item.setLabel(entity.getNama());
				//System.out.println("Index ini 0");
			}
			else
				{
					item.setValue(entity);
					item.setLabel(entity.getNama());
					//System.out.println("index ini "+idx);
				}
			
		}
		
	}
		
	void initComboVArea(){
		areaDao = HibernateUtil.getAreaDao();
		List<Area> areas = areaDao.getAll();
		Area area = new Area();
		area.setNamaArea("- Semua -");
		areas.add(0,area);
		listModel = new ListModelList<>(areas);
		cb_area.setModel(listModel);
		cb_area.setItemRenderer(new AreaVRenderer());
		
	}
	
	class AreaVRenderer implements ComboitemRenderer<Area>{

		@Override
		public void render(Comboitem item, Area entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			//System.out.println("Anchor : "+entity.getNama());
			if(idx == 0)
			{
				item.setValue(null);
				item.setLabel(entity.getNamaArea());
				//System.out.println("Index ini 0");
			}
			else
				{
					item.setValue(entity);
					item.setLabel(entity.getNamaArea());
					//System.out.println("index ini "+idx);
				}
			
			
		}
	
	}
	
	void initComboVBuc(){
		bucDao = HibernateUtil.getBucAnchorDao();
		List<BUCAnchor> bucs = bucDao.getAll();
		BUCAnchor buc = new BUCAnchor();
		buc.setNamaUnitKerja("- Semua -");
		bucs.add(0,buc);
		listModel = new ListModelList<>(bucs);
		
		cb_buc.setModel(listModel);
		cb_buc.setItemRenderer(new BUCVRenderer());
		
	}
	
	class BUCVRenderer implements ComboitemRenderer<BUCAnchor>{

		@Override
		public void render(Comboitem item, BUCAnchor entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			if(idx == 0){
				item.setValue(null);
				item.setLabel(entity.getNamaUnitKerja());
			}else
			{
				item.setValue(entity);
				item.setLabel(entity.getNamaUnitKerja());
				
				
			}
		}
		
	}



		
	public void detail(DistributionFinance entity) {
		
		this.distributionFinanceEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Value Chain");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("400px");
		window.setHeight("470px");
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
		row.appendChild(new Label("Kanwil"));
		row.appendChild(new Label(entity.getKanwil()!=null?entity.getKanwil().getNoKanwil()+" "+entity.getKanwil().getNama():" "));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Anchor"));
		row.appendChild(new Label(entity.getAnchor()!=null?entity.getAnchor().getNama():""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Distributor"));
		row.appendChild(new Label(entity.getDistributor()!=null?entity.getDistributor().getNama():""));
		row.setParent(rows);
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("BUC"));
		row.appendChild(new Label(entity.getBuc()!=null?entity.getBuc().getKodeBuc()+" "+entity.getBuc().getNamaUnitKerja():""));
		row.setParent(rows);

		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Area"));
		row.appendChild(new Label(entity.getArea()!=null?entity.getArea().getNamaArea():""));
		row.setParent(rows);
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Fasilitas"));
		row.appendChild(new Label(entity.getFasilitasString()));
		row.setParent(rows);
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("CIF Number"));
		row.appendChild(new Label(entity.getCifNumber()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Acc Number"));
		row.appendChild(new Label(entity.getAccNumber()));
		row.setParent(rows);
		
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Outlet"));
		row.appendChild(new Label(entity.getOutlet()));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Potensi"));
		row.appendChild(new Label(entity.getPotensi()+""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Limit"));
		row.appendChild(new Label(f.format(entity.getLimitDF())+""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Outstanding List"));
		row.appendChild(new Label(f.format(entity.getOutstandingList())+""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Keterangan"));
		row.appendChild(new Label(entity.getKeterangan()));
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
