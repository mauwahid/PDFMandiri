package pdf.mandiri.action;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.East;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Layout;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import pdf.mandiri.common.CommonCode;
import pdf.mandiri.dao.AnchorDao;
import pdf.mandiri.dao.AreaDao;
import pdf.mandiri.dao.BUCAnchorDao;
import pdf.mandiri.dao.CustomerTypeDao;
import pdf.mandiri.dao.DistributionFinanceDao;
import pdf.mandiri.dao.DistributorDao;
import pdf.mandiri.dao.KanwilDao;
import pdf.mandiri.dao.KirimEmailDao;
import pdf.mandiri.dao.KreditTypeDao;
import pdf.mandiri.dao.LogStatusDao;
import pdf.mandiri.dao.PenggunaDao;
import pdf.mandiri.dao.StatusDao;
import pdf.mandiri.domain.Anchor;
import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.CustomerType;
import pdf.mandiri.domain.DistributionFinance;
import pdf.mandiri.domain.Distributor;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.KirimEmail;
import pdf.mandiri.domain.KreditType;
import pdf.mandiri.domain.LogStatus;
import pdf.mandiri.domain.Pengguna;
import pdf.mandiri.domain.Status;
import pdf.mandiri.util.HibernateUtil;

public class ToKanwilAction extends GenericForwardComposer<Component> implements Action<DistributionFinance> {
	private DistributionFinanceDao distributionFinanceDao;
	private DistributionFinance distributionFinanceEntity;
	private Status status;
	
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
	private CustomerTypeDao customerTypeDao;
	private Pengguna pengguna;
	private Kanwil kanwil;
//	private KreditTypeDao fasilitasDao;
	PenggunaDao penggunaDao;
	Pengguna pengirim;
	Kanwil penerima;
	
	private KreditType fasilitas;
	private KirimEmail kirimEmailEntity;
	private ListModelList listModel;
//	private ListModel listModel;
	
	
	private String messageTengah = "";
	
	
	private Long idAnchor;
	private Long idDistributor;
	private Long idKanwil;
	private Long idBUC;
	private Long idKreditType;
	
	
	//Component
	Listbox list_to_kanwil;
	Textbox txt_cari;
	Combobox cb_distributor;
	Combobox cb_anchor;
	Combobox cb_kanwil;
	Combobox cb_area;
	Combobox cb_buc;
	Textbox tb_fasilitas;
	Listfooter limit_count;
	Listfooter outstanding_count;
	
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
	private Combobox cbArea;
	private Combobox cbCustomerType;
	
	private Textbox tbLimit;
	private Textbox tbOutstanding;
	private Textbox tbFasilitas;
	private Combobox cbKreditType;
	private Combobox cbStatus;
	
	private Textbox tbKeterangan;
	private Textbox tbPotensi;
	private Textbox tbOutlet;
	private Textbox tbCifNumber;
	private Textbox tbAccNumber;
	
	//email form
	String messageAtas = CommonCode.BODY_ATAS;
    String messageBawah = CommonCode.BODY_BAWAH;
    
    
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
    private double countLimit = 0;
    private double countOutstanding = 0;
    private DecimalFormatSymbols symbol;
    private DecimalFormat decFormat; 
    private NumberFormat numFormat;
    
    private Listcell lc;
	
    
	
	private Button btnSave;
	private Button btnCancel;
	NumberFormat f = NumberFormat.getInstance();
	String refinedNumber;
	
	Listbox listLogStatus;
	Listhead listHead;
	Listitem listItem;
	Listhead listHeadLog;
	
	Borderlayout borderLayout;
	West west;
	East east;
	
	
	
	
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
		pengguna = (Pengguna) session.getAttribute("pengguna");
		setCheckBox();
		
		loadData();
		loadVCombo();
		
		
	}

	
	private void loadData(){
		
		
		distributionFinanceDao = HibernateUtil.getDistributionFinanceDao();
		List<DistributionFinance> list = distributionFinanceDao.getAll();
		
		listModel = new ListModelList(list);
	//	listModel = new SimpleListModel<DistributionFinance>(list);
		//UI Renderer
		listModel.setMultiple(true);
		list_to_kanwil.setModel(listModel);
		list_to_kanwil.setItemRenderer(new DistributionFinanceRenderer());
		list_to_kanwil.renderAll();
		//setCheckBox();
		
		showTotal();
		setCheckBox();
		
	}
	
	private void showTotal(){
		limit_count.setLabel(decFormat.format(countLimit)+"");
		outstanding_count.setLabel(decFormat.format(countOutstanding)+"");
		
		countLimit = 0;
		countOutstanding = 0;
		
	}

	public void onAdd(){
		showForm(null);
		System.out.println("on Add");
		
	}
	
	
	public void onUpdate(){
		System.out.println("halooo");
	}
	
	public void onRefresh(){
			loadVCombo();
			loadData();
			
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
	
		fasilitas = tb_fasilitas.getText();
		
		if(cb_kanwil.getSelectedItem()!=null && cb_kanwil.getSelectedIndex()!=0)
			kanwil = kanwilDao.getByName((String) cb_kanwil.getSelectedItem().getValue());
		if(cb_buc.getSelectedItem()!=null && cb_buc.getSelectedIndex()!=0)
			buc = bucDao.getByName((String) cb_buc.getSelectedItem().getValue());
		if(cb_area.getSelectedItem()!=null && cb_area.getSelectedIndex()!=0)
			area = areaDao.getByName((String) cb_area.getSelectedItem().getValue());
		
		List<DistributionFinance> list = distributionFinanceDao.getAnchorFasilitasKanwilBUCArea(anchor, fasilitas, kanwil, buc, area);		
		listModel = new ListModelList<DistributionFinance>(list);
		
		//UI Renderer
	//	setCheckBox();
			
		list_to_kanwil.setModel(listModel);
		list_to_kanwil.setItemRenderer(new DistributionFinanceRenderer());
		list_to_kanwil.setMultiple(true);
		list_to_kanwil.renderAll();
		showTotal();	
	//	setCheckBox();
		
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
		//	lc = new Listcell(dataTemp.getAnchor()!=null?dataTemp.getAnchor().getNama():"");
		//	lc.setStyle("font-size:10px");
		//	lc.setParent(item);
			
			new Listcell(dataTemp.getAnchor()!=null?dataTemp.getAnchor().getNama():"").setParent(item);
		/*	lc = new Listcell(dataTemp.getDistributor()!=null?dataTemp.getDistributor().getNama():"");
			lc.setStyle("font-size:10px");
			lc.setParent(item);*/
			new Listcell(dataTemp.getDistributor()!=null?dataTemp.getDistributor().getNama():"").setParent(item);
			new Listcell(dataTemp.getBuc()!=null?dataTemp.getBuc().getKodeBuc():"").setParent(item);
			new Listcell(dataTemp.getCurrentStatus()!=null?dataTemp.getCurrentStatus().getStatusName():"").setParent(item);
			new Listcell(dataTemp.getTanggal()!=null?dateFormat.format(dataTemp.getTanggal()):"").setParent(item);
			new Listcell(decFormat.format(dataTemp.getLimitDF())+"").setParent(item);
			new Listcell(decFormat.format(dataTemp.getOutstandingList())+"").setParent(item);
			
			item.setValue(entity);
		
			countLimit = countLimit + dataTemp.getLimitDF();
			countOutstanding = countOutstanding + dataTemp.getOutstandingList();
			
			Box box = new Hbox();
			//box.setWidth("100%");
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
			
		//	setCheckBox();
		}
		
		
		
	}

	private void setCheckBox(){
	//	list_to_kanwil.setCheckmark(true);
		list_to_kanwil.setMultiple(true);
		
		
	}
	
	@Override
	public void showForm(DistributionFinance entity) {
		
		this.distributionFinanceEntity = entity;
		f.setGroupingUsed(false);
		
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Form Value Chain");
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
		row.appendChild(new Label("Anchor"));
		initComboAnchor();
		if(entity!=null)
			if(entity.getAnchor()!=null)
			{
				Comboitem item = new Comboitem();
				item.setValue(entity.getAnchor());
				item.setLabel(entity.getAnchor().getNama());
				item.setParent(cbAnchor);
				cbAnchor.setSelectedItem(item);
				
			}
		cbAnchor.setReadonly(true);
		cbAnchor.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Distributor"));
		initComboDistributor();
		if(entity!=null)
			if(entity.getDistributor()!=null)
			{
				Comboitem item = new Comboitem();
				item.setValue(entity.getDistributor());
				item.setLabel(entity.getDistributor().getNama());
				item.setParent(cbDistributor);
				cbDistributor.setSelectedItem(item);
				
			}
		cbDistributor.setReadonly(true);
		cbDistributor.setParent(row);
		row.setParent(rows);
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("BUC"));
		initComboBuc();
		if(entity!=null)
			if(entity.getBuc()!=null)
			{
				Comboitem item = new Comboitem();
				item.setValue(entity.getBuc());
				item.setLabel(entity.getBuc().getNamaUnitKerja());
				item.setParent(cbBUC);
				cbBUC.setSelectedItem(item);
				
			}
		cbBUC.setReadonly(true);
		cbBUC.setParent(row);
		row.setParent(rows);
		

		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Cabang"));
		cbArea = new Combobox();
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
		cbArea.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Customer Type"));
		initComboCustomerType();
		if(entity!=null)
			if(entity.getCustomerType()!=null)
			{
				Comboitem item = new Comboitem();
				item.setValue(entity.getCustomerType());
				item.setLabel(entity.getCustomerType().getCustormerType());
				item.setParent(cbCustomerType);
				cbCustomerType.setSelectedItem(item);
				
			}
		cbCustomerType.setReadonly(true);
		cbCustomerType.setParent(row);
		row.setParent(rows);
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Fasilitas"));
		tbFasilitas = new Textbox(distributionFinanceEntity!=null?distributionFinanceEntity.getFasilitasString():"");
		tbFasilitas.setParent(row);
		row.setParent(rows);
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("CIF Number"));
		tbCifNumber = new Textbox(distributionFinanceEntity!=null?distributionFinanceEntity.getCifNumber()+"":"");
		tbCifNumber.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Acc Number"));
		tbAccNumber = new Textbox(distributionFinanceEntity!=null?distributionFinanceEntity.getAccNumber()+"":"");
		tbAccNumber.setParent(row);
		row.setParent(rows);
		
		
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Outlet"));
		tbOutlet = new Textbox(distributionFinanceEntity!=null?distributionFinanceEntity.getOutlet()+"":"");
		tbOutlet.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Potensi"));
		tbPotensi = new Textbox(distributionFinanceEntity!=null?distributionFinanceEntity.getPotensi()+"":"");
		tbPotensi.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Limit"));
		tbLimit = new Textbox(distributionFinanceEntity!=null?f.format(distributionFinanceEntity.getLimitDF())+"":"");
		tbLimit.setParent(row);
		row.setParent(rows);
		
		row = new Row();
		row.setParent(rows);
		row.appendChild(new Label("Outstanding List"));
		tbOutstanding = new Textbox(distributionFinanceEntity!=null?f.format(distributionFinanceEntity.getOutstandingList())+"":"");
		tbOutstanding.setParent(row);
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
				
				if(distributionFinanceEntity==null){
					distributionFinance = new DistributionFinance();
					distributionFinance.setStatusKirim(0);
				
						
				}
				else
					distributionFinance = distributionFinanceEntity;
				
				if(cbKanwil.getSelectedItem()!=null)
					distributionFinance.setKanwil((Kanwil)cbKanwil.getSelectedItem().getValue());
				if(cbBUC.getSelectedItem()!=null)
					distributionFinance.setBuc((BUCAnchor)cbBUC.getSelectedItem().getValue());
				if(cbDistributor.getSelectedItem()!=null)
					distributionFinance.setDistributor((Distributor)cbDistributor.getSelectedItem().getValue());
				if(cbAnchor.getSelectedItem()!=null){
					Anchor achor = (Anchor) cbAnchor.getSelectedItem().getValue();
					System.out.println("Anchor ob "+cbAnchor.toString());
					distributionFinance.setAnchor((Anchor)cbAnchor.getSelectedItem().getValue());
						
				}
				
					
				try{
					distributionFinance.setLimitDF(Double.parseDouble(tbLimit.getText()));
				}catch(NumberFormatException ex){
					
				}
				
				try{
					distributionFinance.setPotensi(Double.parseDouble(tbPotensi.getText()));
				}catch(NumberFormatException ex){
					
				}

				
				distributionFinance.setFasilitasString(tbFasilitas.getText());
				distributionFinance.setKeterangan(tbKeterangan.getText());
			//	distributionFinance.setPotensi(tbPotensi.getText());
				distributionFinance.setAccNumber(tbAccNumber.getText());
				distributionFinance.setCifNumber(tbCifNumber.getText());
				distributionFinance.setOutlet(tbOutlet.getText());
				if(cbCustomerType.getSelectedItem()!=null)
					distributionFinance.setCustomerType((CustomerType)cbCustomerType.getSelectedItem().getValue());
				
				if(cbArea.getSelectedItem()!=null)
					distributionFinance.setArea((Area)cbArea.getSelectedItem().getValue());
				
				distributionFinance.setFasilitasString(tbFasilitas.getText());
				
				Kanwil kanwil = null;
				BUCAnchor buc = null;
				Distributor dist = null;
				Anchor anc = null;
				KreditType kreditT = null;
				Area area = null;
				CustomerType cType = null;
				
				if(cbKanwil.getSelectedItem()!=null)
					 kanwil = (Kanwil)cbKanwil.getSelectedItem().getValue();
				if(cbAnchor.getSelectedItem()!=null)
					buc = (BUCAnchor)cbBUC.getSelectedItem().getValue();
				if(cbDistributor.getSelectedItem()!=null)
					dist = (Distributor)cbDistributor.getSelectedItem().getValue();
				if(cbAnchor.getSelectedItem()!=null)
					anc = (Anchor)cbAnchor.getSelectedItem().getValue();
				if(cbArea.getSelectedItem()!=null)
					area = (Area)cbArea.getSelectedItem().getValue();
				if(cbCustomerType.getSelectedItem()!=null)
					cType = (CustomerType)cbCustomerType.getSelectedItem().getValue();
				
				String out = tbOutstanding.getText();
				String lim = tbLimit.getText();
				String ket =  tbKeterangan.getText();
				
				
				logStatus.setKanwil(kanwil!=null?kanwil.getNama():"");
				logStatus.setAnchor(anc!=null?anc.getNama():"");
				logStatus.setDistributor(dist!=null?dist.getNama():"");
				logStatus.setKreditType(tbFasilitas.getText());
				logStatus.setCustomerType(cType!=null?cType.getCustormerType():"");
				logStatus.setOutstandingList(out);
				logStatus.setLimitDF(lim);
				logStatus.setBUC(buc!=null?buc.getNamaUnitKerja():"");
				logStatus.setKeterangan(ket);
				logStatus.setStatusDF("Dikirim Ke Kanwil");
				logStatus.setTanggalDiubah(new Date().toString());
				logStatus.setTanggal(new Date().toString());
				logStatus.setDistriFinance(distributionFinance);
				logStatus.setPotensi(tbPotensi.getText());
				logStatus.setAccNumber(tbAccNumber.getText());
				logStatus.setCifNumber(tbCifNumber.getText());
				logStatus.setOutlet(tbOutlet.getText());
				logStatus.setArea(area!=null?area.getNamaArea():"");
				logStatus.setPengguna(pengguna.getUsername());
				
				
				if(distributionFinanceEntity==null){
					distributionFinanceDao.insert(distributionFinance);
					
					
			//		kirimEmail(distributionFinance.getId());
				}
					else
					distributionFinanceDao.update(distributionFinance);
				
				logStatusDao.insert(logStatus);
				System.out.println("Log Status Insert");
				
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
	
	void showLog(DistributionFinance entity) {
		
		this.distributionFinanceEntity = entity;
		// TODO Auto-generated method stub
	/*	window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Log DistributionFinance");
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("1000px");
		window.setHeight("450px");
		window.setVisible(true);*/
	//	east = new East();
		east.setVisible(true);
		listLogStatus = new Listbox();
		listLogStatus.setParent(east);
		east.setParent(borderLayout);
		
		listLogStatus.setVisible(true);
		listLogStatus.setStyle("z");
		listLogStatus.setMold("paging");
		listLogStatus.setPageSize(5);
		
	//	listLogStatus.setParent(window);
		
		listHeadLog = new Listhead();
		listHeadLog.setParent(listLogStatus);
		
		listItem = new Listitem();
		Listheader header;
		header =  new Listheader("Tanggal Status");
		header.setStyle("font-size:10px");
		header.setParent(listHeadLog);
		
		header = new Listheader("Status");
		header.setStyle("font-size:10px");
		header.setParent(listHeadLog);

		
		header = new Listheader("Pengguna");
		header.setStyle("font-size:10px");
		header.setParent(listHeadLog);

		
		
		header = new Listheader("Limit");
		header.setStyle("font-size:10px");
		header.setParent(listHeadLog);

		header = new Listheader("Outstanding");
		header.setStyle("font-size:10px");
		header.setParent(listHeadLog);

		header = new Listheader("Jenis Fasilitas");
		header.setStyle("font-size:10px");
		header.setParent(listHeadLog);

		header = new Listheader("Keterangan");
		header.setStyle("font-size:10px");
		header.setParent(listHeadLog);

		
		header = new Listheader("Tanggal Diubah");
		header.setStyle("font-size:10px");
		header.setParent(listHeadLog);

		logStatusDao = HibernateUtil.getLogStatusDao();
		List<LogStatus> logStatuses = logStatusDao.getByDF(entity);
		listModel = new ListModelList<LogStatus>(logStatuses);
		listLogStatus.setModel(listModel);
		
		listLogStatus.setItemRenderer(new LogStatusRenderer());
		
				
		window.onModal();
		
	}

	
	
	public void detail(DistributionFinance entity) {
		
		this.distributionFinanceEntity = entity;
		// TODO Auto-generated method stub
		window = new Window();
		window.setContentStyle("overflow:auto");
		
		window.setParent(self);
		window.setTitle("Detail Value Chain");
		borderLayout = new Borderlayout();
		window.setMode("popup");
		window.setPosition("center,top");
		window.setClosable(true);
		window.setWidth("1200px");
		window.setHeight("470px");
		window.setVisible(true);
		west = new West();
		west.setSize("20%");
		west.setAutoscroll(true);
		east = new East();
		east.setSize("80%");
		
		grid = new Grid();
		columns = new Columns();
		rows = new Rows();
		rows.setParent(grid);
		
	//	grid.setParent(window);
		grid.setParent(west);
		west.setParent(borderLayout);
		borderLayout.setParent(window);
		
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
		row.appendChild(new Label("Cabang"));
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
		
		showLog(entity);
	//	window.onModal();
		
	}

	
	void initComboAnchor(){
		
		System.out.println("Init combo anchor");
		cbAnchor = new Combobox();
		anchorDao = HibernateUtil.getAnchorDao();
		List<Anchor> anchorList = anchorDao.getAllAsc();
		
		
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
		List<Distributor> DistributorList = distributorDao.getAllAsc();
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
		List<Kanwil> KanwilList = kanwilDao.getAllAsc();
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
		List<KreditType> KreditTypeList = kreditTypeDao.getAllAsc();
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
	List<Status> StatusList = statusDao.getAllAsc();
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

	
	class FasilitasVRenderer implements ComboitemRenderer<String>{

		@Override
		public void render(Comboitem item, String entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
		//	System.out.println("Anchor : "+entity.getNama());
			
			item.setValue(entity);
			item.setLabel(entity);
			
		}
		
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


	void initComboArea(){
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
			//System.out.println("Anchor : "+entity.getNama());
					item.setValue(entity);
					item.setLabel(entity.getNamaArea());
			
			
		}
		
	}

	
	
	class FasilitasRenderer implements ComboitemRenderer<KreditType>{

		@Override
		public void render(Comboitem item, KreditType entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			//System.out.println("Anchor : "+entity.getNama());
			
			item.setValue(entity);
			item.setLabel(entity.getKreditType());
			
		}
		
	}

	void initComboCustomerType(){
		cbCustomerType = new Combobox();
		customerTypeDao = HibernateUtil.getCustomerTypeDao();
		List<CustomerType> customerTypes = customerTypeDao.getAllAsc();
		CustomerType cust = new CustomerType();
		customerTypes.add(0,cust);
		listModel = new ListModelList<>(customerTypes);
		cbCustomerType.setModel(listModel);
		cbCustomerType.setItemRenderer(new CustomerTypeRenderer());
		
	}
	
	class CustomerTypeRenderer implements ComboitemRenderer<CustomerType>{

		@Override
		public void render(Comboitem item, CustomerType entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			//System.out.println("Anchor : "+entity.getNama());
			
			if(idx == 0)
			{
				item.setValue(null);
				item.setLabel(entity.getCustormerType());
				//System.out.println("Index ini 0");
			}
			else
				{
					item.setValue(entity);
					item.setLabel(entity.getCustormerType());
					//System.out.println("index ini "+idx);
				}
			
		}
		
	}

	
	public void onSendMail(){
		
		messageTengah = "";
		
		Set<Listitem> items = list_to_kanwil.getSelectedItems();
		Iterator iter = items.iterator();
		
		
		Listitem item;
		
		DistributionFinance df;
		List<DistributionFinance> listDF;
		Set<String> valueChain = new HashSet<>();
		
		listDF = new ArrayList<DistributionFinance>();
		int index = 1;
		String dfAnchor = "";
		
		while(iter.hasNext()){
			item  = (Listitem) iter.next();
			df = (DistributionFinance) item.getValue();
			dfAnchor = df.getAnchor()!=null?df.getAnchor().getNama():"";
			valueChain.add("%0D%0A"+index+". "+dfAnchor);
			listDF.add(df);
			index = index + 1;
			if(kanwil==null)
				kanwil = df.getKanwil();
		}
		
		
		Iterator iterVC = valueChain.iterator();
		String vcString = "";
		
		while(iterVC.hasNext()){
			vcString = (String)iterVC.next();
			messageTengah = messageTengah + vcString;
		}
		
		if(list_to_kanwil.getSelectedItem()==null){
			Messagebox.show("Mohon Cheklist Data yang dipilih", "WARNING", Messagebox.OK, null, new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					return;
				}
			});
			
		}
		else
		//	emailForm();
			showOutlook();
		
		
	}
	
	public void showOutlook(){
	
		
		penggunaDao = HibernateUtil.getPenggunaDao();
	//	pengirim = penggunaDao.getById(pengguna.getId());
		kanwilDao = HibernateUtil.getKanwilDao();
		if(kanwil!=null)
			penerima = kanwilDao.getById(kanwil.getId());
		String cc2 = "";
		String mailPenerima = penerima!=null?penerima.getEmailKanwil():"";
		String cc1 = penerima!=null?penerima.getEmailSupervisi1():"";
		System.out.println("S1 : "+penerima.getEmailSupervisi1());
		if(penerima!=null){
				String supervisi2 = penerima.getEmailSupervisi2();
			if(!supervisi2.equalsIgnoreCase("")){
				cc2 =";"+penerima.getEmailSupervisi2();
				System.out.println("S2 : "+penerima.getEmailSupervisi2());
			}
		}
			
		String ccMailPenerima = cc1 + cc2;
		String subject = CommonCode.SUBJECT;
		String namaPenerima = penerima!=null?penerima.getNama():"";
		String body = CommonCode.BODY_ATAS + namaPenerima + ","+CommonCode.BODY_ATAS_2 + messageTengah + CommonCode.BODY_BAWAH;
		String mailMessage = "mailto:"+mailPenerima+"?cc="+ccMailPenerima+"&subject="+subject+"&body="+body;
	//	String mailMessage = "mailto:"+mailPenerima+"?subject="+subject+"&body="+body;
		Executions.getCurrent().sendRedirect(mailMessage);
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
	
	void initComboVBuc(){
		bucDao = HibernateUtil.getBucAnchorDao();
		List<String> bucs = bucDao.getAllString();
		String semua = "- Semua -";
		bucs.add(0,semua);
		
		listModel = new ListModelList<>(bucs);
		cb_buc.setModel(listModel);
		cb_buc.setItemRenderer(new BUCVRenderer());
		
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
	
class LogStatusRenderer implements ListitemRenderer<LogStatus>{

		
		@Override
		public void render(Listitem item, LogStatus entity, int index)
				throws Exception {
			// TODO Auto-generated method stub
			final LogStatus dataTemp = entity;
			index = index + 1;
				
		
			new Listcell(dataTemp.getTanggal()).setParent(item);
			new Listcell(dataTemp.getStatusDF()).setParent(item);
			new Listcell(dataTemp.getPengguna()).setParent(item);
			new Listcell(dataTemp.getLimitDF()).setParent(item);
			new Listcell(dataTemp.getOutstandingList()).setParent(item);
			new Listcell(dataTemp.getKreditType()).setParent(item);
			new Listcell(dataTemp.getKeterangan()).setParent(item);
			new Listcell(dataTemp.getTanggalDiubah()).setParent(item);
			
		}
	}


}
