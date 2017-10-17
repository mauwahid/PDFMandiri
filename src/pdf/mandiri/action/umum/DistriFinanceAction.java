package pdf.mandiri.action.umum;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.theme.Themes;

import pdf.mandiri.action.Action;
import pdf.mandiri.dao.AnchorDao;
import pdf.mandiri.dao.AreaDao;
import pdf.mandiri.dao.BUCAnchorDao;
import pdf.mandiri.dao.DistributionFinanceDao;
import pdf.mandiri.dao.DistributorDao;
import pdf.mandiri.dao.KanwilDao;
import pdf.mandiri.dao.KreditTypeDao;
import pdf.mandiri.dao.StatusDao;
import pdf.mandiri.domain.Anchor;
import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.Distributor;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.KreditType;
import pdf.mandiri.domain.Status;
import pdf.mandiri.util.HibernateUtil;

public class DistriFinanceAction extends GenericForwardComposer<Component> implements Action<DistributionFinance> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3525944168224104697L;
	private DistributionFinanceDao distributionFinanceDao;
	private DistributionFinance distributionFinanceEntity;
	
	//DAO
	private AnchorDao anchorDao;
	private BUCAnchorDao bucDao;
	private DistributorDao distributorDao;
	private KanwilDao kanwilDao;
	private KreditTypeDao kreditTypeDao;
	private StatusDao statusDao;
	private AreaDao areaDao;
	
	private ListModel listModel;
	
	private Listfooter limit_count;
	private Listfooter outstanding_count;
	
	
	private double countLimit = 0;
	private double countOutstanding = 0;
	private DecimalFormatSymbols symbol;
	private DecimalFormat decFormat; 
	private NumberFormat numFormat;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
	  	
	
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
	private Combobox cbKreditType;
	private Combobox cbStatus;
	
	private Textbox tbKeterangan;
	
	private Button btnSave;
	private Button btnCancel;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		symbol = new DecimalFormatSymbols(Locale.GERMAN);
		symbol.setGroupingSeparator('.');
		symbol.setDecimalSeparator(',');
		
		numFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
	 //   decFormat = new DecimalFormat("###,###.###",symbol);
		decFormat = (DecimalFormat) numFormat;
		
		
		loadVCombo();
		loadData();
		
		
		
		Themes.setTheme(Executions.getCurrent(), "sapphire");
	//	Executions.sendRedirect(null);	
		
		
	}

	
	private void loadData(){
		distributionFinanceDao = HibernateUtil.getDistributionFinanceDao();
		List<DistributionFinance> list = distributionFinanceDao.getAll();
		
		listModel = new SimpleListModel<DistributionFinance>(list);
		//UIderer
		list_to_kanwil.setModel(listModel);
		
		
		list_to_kanwil.setItemRenderer(new DistributionFinanceRenderer());
		list_to_kanwil.renderAll();
		showTotal();
	
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
				
		//	new Listcell(dataTemp.getId()+"").setParent(item);
			new Listcell(dataTemp.getKanwil()!=null?dataTemp.getKanwil().getNoKanwil():"").setParent(item);
			new Listcell(dataTemp.getArea()!=null?dataTemp.getArea().getNamaArea():"").setParent(item);
			new Listcell(dataTemp.getAnchor()!=null?dataTemp.getAnchor().getNama():"").setParent(item);
			new Listcell(dataTemp.getDistributor()!=null?dataTemp.getDistributor().getNama():"").setParent(item);
			new Listcell(dataTemp.getBuc()!=null?dataTemp.getBuc().getKodeBuc():"").setParent(item);
			new Listcell(dataTemp.getCurrentStatus()!=null?dataTemp.getCurrentStatus().getStatusName():"").setParent(item);
			new Listcell(dataTemp.getTanggal()!=null?dateFormat.format(dataTemp.getTanggal()):"").setParent(item);
			new Listcell(decFormat.format(dataTemp.getLimitDF())+"").setParent(item);
			new Listcell(decFormat.format(dataTemp.getOutstandingList())+"").setParent(item);
			
			item.setValue(entity);
		
			countLimit = countLimit + dataTemp.getLimitDF();
			countOutstanding = countOutstanding + dataTemp.getOutstandingList();

			
		
			item.addEventListener("onDoubleClick", new EventListener<Event>() {
				
				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					
					detail(dataTemp);
				}
			});
		
		
			Listcell listCell = new Listcell();
			listCell.setParent(item);
			
			
		
		}
		
	}
	
	private void showTotal(){
		limit_count.setLabel(decFormat.format(countLimit)+"");
		outstanding_count.setLabel(decFormat.format(countOutstanding)+"");
		
		countLimit = 0;
		countOutstanding = 0;
		
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
		cbBUC.setItemRenderer(new BUCVRenderer());
		
	}
	
	class AnchorRenderer implements ComboitemRenderer<Anchor>{

		@Override
		public void render(Comboitem item, Anchor entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			//System.out.println("Anchor : "+entity.getNama());
			
			
			
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


	class BUCVRenderer implements ComboitemRenderer<String>{

		@Override
		public void render(Comboitem item, String entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			
			item.setValue(entity);
			item.setLabel(entity);
			
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
	
	void initComboKanwil(){
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
	
	
	
	void loadVCombo(){
		initComboVAnchor();
		initComboVKanwil();

		initComboVArea();
		initComboVBuc();
	}
	
	


	void initComboVAnchor(){
		anchorDao = HibernateUtil.getAnchorDao();
		List<String> anchors = anchorDao.getAllString();
		String semua = "- Semua -";
		anchors.add(0,semua);
		
		Iterator<String> iter = anchors.iterator();
		
		listModel = new ListModelList<>(anchors);
		cb_anchor.setModel(listModel);
		cb_anchor.setItemRenderer(new AnchorVRenderer());
		
	}
	
	class AnchorVRenderer implements ComboitemRenderer<String>{

		@Override
		public void render(Comboitem item, String entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
		//	System.out.println("Anchor : "+entity.getNama());
			
			item.setValue(entity);
			item.setLabel(entity);
			
		}
		
	}


	void initComboVKanwil(){
		kanwilDao = HibernateUtil.getKanwilDao();
		List<String> kanwils = kanwilDao.getAllString();
		String semua = "- Semua -";
		kanwils.add(0,semua);
		
		listModel = new ListModelList<>(kanwils);
		cb_kanwil.setModel(listModel);
		cb_kanwil.setItemRenderer(new KanwilVRenderer());
		
	}
	
	class KanwilVRenderer implements ComboitemRenderer<String>{

		@Override
		public void render(Comboitem item, String entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
		//	System.out.println("Anchor : "+entity.getNama());
			
			item.setValue(entity);
			item.setLabel(entity);
			
		}
		
	}

	public void onSearch() {
		
		Anchor anchor = null;
		Distributor distributor = null;
		Kanwil kanwil = null;
		BUCAnchor buc = null;
		Area area = null;
		String fasilitas = "";
		
		// TODO Auto-generated method stub
		distributionFinanceDao = HibernateUtil.getDistributionFinanceDao();
		
		if(cb_anchor.getSelectedItem()!=null && cb_anchor.getSelectedIndex()!=0)
			anchor = anchorDao.getByName((String)cb_anchor.getSelectedItem().getValue());
			fasilitas = tb_fasilitas.getValue();
		if(cb_kanwil.getSelectedItem()!=null && cb_kanwil.getSelectedIndex()!=0)
			kanwil = kanwilDao.getByName((String) cb_kanwil.getSelectedItem().getValue());
		if(cb_buc.getSelectedItem()!=null && cb_buc.getSelectedIndex()!=0)
			buc = bucDao.getByName((String) cb_buc.getSelectedItem().getValue());
		if(cb_area.getSelectedItem()!=null && cb_area.getSelectedIndex()!=0)
			area = areaDao.getByName((String) cb_area.getSelectedItem().getValue());
		
		List<DistributionFinance> list = distributionFinanceDao.getAnchorFasilitasKanwilBUCArea(anchor, fasilitas, kanwil, buc, area);		
		listModel = new SimpleListModel<DistributionFinance>(list);
		//UI Renderer
		list_to_kanwil.setModel(listModel);
		list_to_kanwil.setItemRenderer(new DistributionFinanceRenderer());
		list_to_kanwil.renderAll();
		//setCheckBox();
	}


	public void detail(DistributionFinance entity) {
		
		this.distributionFinanceEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("DistributionFinance");
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
		row.appendChild(new Label(entity.getKanwil()!=null?entity.getKanwil().getNama():""));
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
		row.appendChild(new Label(entity.getBuc()!=null?entity.getBuc().getNamaUnitKerja():""));
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
		row.appendChild(new Label(entity.getLimitDF()+""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Outstanding"));
		row.appendChild(new Label(entity.getOutstandingList()+""));
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Status Terakhir"));
		row.appendChild(new Label(entity.getCurrentStatus().getStatusName()+""));
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
		btnCancel.setMold("trendy");
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

	void initComboVBuc(){
		bucDao = HibernateUtil.getBucAnchorDao();
		List<String> bucs = bucDao.getAllString();
		String semua = "- Semua -";
		bucs.add(0,semua);
		
		listModel = new ListModelList<>(bucs);
		cb_buc.setModel(listModel);
		cb_buc.setItemRenderer(new BUCVRenderer());
		
	}
	
		
	
	void initComboVArea(){
		areaDao = HibernateUtil.getAreaDao();
		List<String> areas = areaDao.getAllString();
		String semua = "- Semua -";
		areas.add(0,semua);
		
		listModel = new ListModelList<>(areas);
		cb_area.setModel(listModel);
		cb_area.setItemRenderer(new AreaVRenderer());
		
	}

	class AreaVRenderer implements ComboitemRenderer<String>{

		@Override
		public void render(Comboitem item, String entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			//System.out.println("Anchor : "+entity.getNama());
			
			item.setValue(entity);
			item.setLabel(entity);
			
		}
		
	}

	@Override
	public void showForm(DistributionFinance entity) {
		// TODO Auto-generated method stub
		
	}

}
