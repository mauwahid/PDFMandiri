package pdf.mandiri.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import pdf.mandiri.dao.AnchorDao;
import pdf.mandiri.dao.AreaDao;
import pdf.mandiri.dao.BUCAnchorDao;
import pdf.mandiri.dao.DistributorDao;
import pdf.mandiri.dao.KanwilDao;
import pdf.mandiri.dao.KreditTypeDao;
import pdf.mandiri.dao.StatusDao;
import pdf.mandiri.domain.Anchor;
import pdf.mandiri.domain.Area;
import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.Distributor;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.KreditType;
import pdf.mandiri.util.HibernateUtil;

public class LaporanAction extends GenericForwardComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7148701988700353075L;
	Combobox cb_anchor;
	Combobox cb_distributor;
	Combobox cb_kanwil;
	Combobox cb_buc;
	Combobox cb_area;
	Combobox cb_saveAs;
	Textbox tb_fasilitas;
	
	private AnchorDao anchorDao;
	private KanwilDao kanwilDao;
	private DistributorDao distributorDao;
	private AreaDao areaDao;
	private KreditTypeDao kreditTypeDao;
	private BUCAnchorDao bucDao;
	
	private ListModelList listModel;
	private Long anchorId = null;
	private Long distributorId = null;
	private Long kanwilId = null;
	private Long areaId = null;
	private Long bucId  = null;
	private String fasilitasId = "";
	
	
	private String pathGambar = "/images/header.png";
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		loadCombo();
	}
	
	public void onCetak() throws JRException, IOException{
		
		SessionFactory sessFact = HibernateUtil.getSessionFactory();
		Session sess = sessFact.openSession();
		
		
		Map params = new HashMap();
	
			Jasperreport report = new Jasperreport();
           // report.setParent(box);
            report.setHeight("500px");
            Map parameters = new HashMap();
            
            if(cb_anchor.getSelectedItem()!=null)
            	anchorId = cb_anchor.getSelectedItem().getValue();
           // if(cb_fasilitas.getSelectedItem()!=null)
            //    fasilitasId = cb_fasilitas.getSelectedItem().getValue();
            fasilitasId = tb_fasilitas.getValue();
            
                if(cb_kanwil.getSelectedItem()!=null)
                kanwilId = cb_kanwil.getSelectedItem().getValue();
            if(cb_area.getSelectedItem()!=null)
                areaId = cb_area.getSelectedItem().getValue();
            if(cb_buc.getSelectedItem()!=null)
                bucId = cb_buc.getSelectedItem().getValue();
        //    System.out.println("Anchor :"+anchorId+" Dist "+distributorId+" kan "+kanwilId);
            
            
            parameters.put("anc_id",anchorId);
            parameters.put("fasilitas_id",fasilitasId);
            parameters.put("kan_id",kanwilId);
            parameters.put("buc_id",bucId);
            parameters.put("area_id",areaId);
            parameters.put("header", application.getRealPath("/images/header.png"));
            parameters.put("SUBREPORT_DIR", application.getRealPath("/laporan/"));
            
       //     System.out.println("Anchor :"+anchorId+" Dist "+distributorId+" kan "+kanwilId);
            
           
            Connection conn = HibernateUtil.getSessionFactory().openSession().connection();
            if(conn==null)
            	System.out.println("conn null");
            JasperPrint jasperPrint = null;
            if(parameters==null)
            	System.out.println("paramters null");
            
            	
				jasperPrint = JasperFillManager.fillReport(application.getRealPath("/laporan/DFreport.jasper"),
				        parameters, conn);
				
		//		JasperViewer jasperView = new JasperViewer(jasperPrint, true);
		//		jasperView.show();
		//		jasperPrint = JasperFillManager.fillReport(application.getRealPath("/laporan/DFreport.jasper"),
		//		        parameters, conn);
				File tempFile = File.createTempFile("tmp", ".pdf");
				FileOutputStream fos = new FileOutputStream(tempFile);
				
				JasperExportManager.exportReportToPdfStream(jasperPrint, fos);
				
				System.out.println("FIle path "+tempFile.getAbsolutePath());
				
				Executions.getCurrent().sendRedirect("http://localhost:8081/PDFMandiri/pusat/"+tempFile.getName(),"_BLANK");
				Executions.getCurrent().sendRedirect("www.google.com","_BLANK");
				
				
		
		/*		
			int random = new Random().hashCode();
		
            File myFile = new File(application
                    .getRealPath("/laporan/laporanDistributionFunction"+random+".pdf"));
            File myFile2 = new File(application
                    .getRealPath("/laporan/laporanDistributionFunction"+random+".xls"));
         
            myFile.getParentFile().mkdirs();
            myFile2.getParentFile().mkdirs();
            JRAbstractExporter exporterPDF = new JRPdfExporter();
            JRAbstractExporter exporterExcel = new JRXlsExporter();
            
          if(cb_saveAs.getSelectedIndex()==0){
        	  exporterPDF.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                      jasperPrint);
              
              exporterPDF.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
  				        new FileOutputStream(myFile));
              
              
  			
              exporterPDF.setParameter(JRExporterParameter.OUTPUT_FILE,
                      myFile);
             try {
  				exporterPDF.exportReport();
  			} catch (JRException e1) {
  				// TODO Auto-generated catch block
  				e1.printStackTrace();
  			}
        
            
            try {
  			//	Filedownload.save(myFile, "pdf");
            	System.out.println("Absoulut "+myFile.getAbsolutePath());
            	System.out.println("Name "+myFile.getName());
  				Executions.getCurrent().sendRedirect(myFile.getAbsolutePath(),"_BLANK");

  			} catch (Exception e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
        
          } */
	}
	
	public void onDownload() throws JRException, FileNotFoundException{
		
		SessionFactory sessFact = HibernateUtil.getSessionFactory();
		Session sess = sessFact.openSession();
		
		
		Map params = new HashMap();
	
			Jasperreport report = new Jasperreport();
           // report.setParent(box);
            report.setHeight("500px");
            Map parameters = new HashMap();
            
            if(cb_anchor.getSelectedItem()!=null)
            	anchorId = cb_anchor.getSelectedItem().getValue();
           // if(cb_fasilitas.getSelectedItem()!=null)
                fasilitasId = tb_fasilitas.getValue();
            if(cb_kanwil.getSelectedItem()!=null)
                kanwilId = cb_kanwil.getSelectedItem().getValue();
            if(cb_area.getSelectedItem()!=null)
                areaId = cb_area.getSelectedItem().getValue();
            if(cb_buc.getSelectedItem()!=null)
                bucId = cb_buc.getSelectedItem().getValue();
          //  System.out.println("Anchor :"+anchorId+" Dist "+distributorId+" kan "+kanwilId);
            
            
            parameters.put("anc_id",anchorId);
            parameters.put("fasilitas_id",fasilitasId);
            parameters.put("kan_id",kanwilId);
            parameters.put("buc_id",bucId);
            parameters.put("area_id",areaId);
            parameters.put("header", application.getRealPath("/images/header.png"));
           // parameters.put("SUBREPORT_DIR", application.getRealPath("/laporan/DFreport_subreport.jasper"));
            parameters.put("SUBREPORT_DIR","");
            
       //     System.out.println("Anchor :"+anchorId+" Dist "+distributorId+" kan "+kanwilId);
            
           
            Connection conn = HibernateUtil.getSessionFactory().openSession().connection();
            if(conn==null)
            	System.out.println("conn null");
            JasperPrint jasperPrint = null;
            if(parameters==null)
            	System.out.println("paramters null");
            
           
				jasperPrint = JasperFillManager.fillReport(application.getRealPath("/laporan/DFreport.jasper"),
				        parameters, conn);
    	//	jasperPrint = JasperFillManager.fillReport(getClass().getResource("pdf/mandiri/report/DFreport.jasper"),parameters, conn);
    						
				//jasperPrint = JasperFillManager.f
				
			int random = new Random().hashCode();
		
            File myFile = new File(application
                    .getRealPath("/laporan/laporanValueChain"+random+".pdf"));
            File myFile2 = new File(application
                    .getRealPath("/laporan/laporanValueChain"+random+".xls"));
         
            myFile.getParentFile().mkdirs();
            myFile2.getParentFile().mkdirs();
            JRAbstractExporter exporterPDF = new JRPdfExporter();
            JRAbstractExporter exporterExcel = new JRXlsExporter();
            
          if(cb_saveAs.getSelectedIndex()==0){
        	  exporterPDF.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                      jasperPrint);
              
              exporterPDF.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
  				        new FileOutputStream(myFile));
              
              
  			
              exporterPDF.setParameter(JRExporterParameter.OUTPUT_FILE,
                      myFile);
             try {
  				exporterPDF.exportReport();
  			} catch (JRException e1) {
  				// TODO Auto-generated catch block
  				e1.printStackTrace();
  			}
        
            
            try {
  				Filedownload.save(myFile, "pdf");
  			} catch (FileNotFoundException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
        
          }else{
        	  System.out.println("Simpan Excel");
        	  
        	  exporterExcel.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                      jasperPrint);
              
              exporterExcel.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
  				        new FileOutputStream(myFile2));
  			
              exporterExcel.setParameter(JRExporterParameter.OUTPUT_FILE,
                      myFile2);
              try {
  				exporterExcel.exportReport();
  			} catch (JRException e1) {
  				// TODO Auto-generated catch block
  				e1.printStackTrace();
  			}
              try {
  				Filedownload.save(myFile2, "xls");
  			} catch (FileNotFoundException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
      
          }
            
                     
             
	}
	
	
	void loadCombo(){
		initComboAnchor();
		initComboKanwil();
		initComboArea();
		initComboBuc();
		initComboSaveAs();
	}
	
	
	class FasilitasRenderer implements ComboitemRenderer<KreditType>{

		@Override
		public void render(Comboitem item, KreditType entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
		//	System.out.println("Anchor : "+entity.getNama());
			
			item.setValue(entity.getId());
			item.setLabel(entity.getKreditType());
			
		}
		
	}


	void initComboAnchor(){
		anchorDao = HibernateUtil.getAnchorDao();
		List<Anchor> anchors = anchorDao.getAll();
		Anchor anchor = new Anchor();
		anchor.setNama("- Semua -");
		anchor.setId(null);
		anchors.add(0, anchor);
		listModel = new ListModelList<>(anchors);
		cb_anchor.setModel(listModel);
		cb_anchor.setItemRenderer(new AnchorRenderer());
		cb_anchor.setReadonly(true);
	}
	
	class AnchorRenderer implements ComboitemRenderer<Anchor>{

		@Override
		public void render(Comboitem item, Anchor entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
		//	System.out.println("Anchor : "+entity.getNama());
			
			item.setValue(entity.getId());
			item.setLabel(entity.getNama());
			
		}
		
	}


	void initComboKanwil(){
		kanwilDao = HibernateUtil.getKanwilDao();
		List<Kanwil> kanwils = kanwilDao.getAll();
		Kanwil kanwil = new Kanwil();
		kanwil.setNama("- Semua -");
		kanwil.setId(null);
		kanwils.add(0, kanwil);
		listModel = new ListModelList<>(kanwils);
		cb_kanwil.setModel(listModel);
		cb_kanwil.setItemRenderer(new KanwilRenderer());
		cb_kanwil.setReadonly(true);
	}
	
	class KanwilRenderer implements ComboitemRenderer<Kanwil>{

		@Override
		public void render(Comboitem item, Kanwil entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
		//	System.out.println("Anchor : "+entity.getNama());
			
			item.setValue(entity.getId());
			item.setLabel(entity.getNama());
			
		}
		
	}
	
	void initComboBuc(){
		bucDao = HibernateUtil.getBucAnchorDao();
		List<BUCAnchor> bucs = bucDao.getAll();
		BUCAnchor buc = new BUCAnchor();
		buc.setNamaUnitKerja("- Semua -");
		buc.setId(null);
		bucs.add(0, buc);
		listModel = new ListModelList<>(bucs);
		cb_buc.setModel(listModel);
		cb_buc.setItemRenderer(new BUCRenderer());
		cb_buc.setReadonly(true);
	}
	
	class BUCRenderer implements ComboitemRenderer<BUCAnchor>{

		@Override
		public void render(Comboitem item, BUCAnchor entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			//System.out.println("Anchor : "+entity.getStatus());
			
			item.setValue(entity.getId());
			item.setLabel(entity.getNamaUnitKerja());
			
		}
		
	}
	
	
	void initComboArea(){
		areaDao = HibernateUtil.getAreaDao();
		List<Area> areas = areaDao.getAll();
		Area area = new Area();
		area.setNamaArea("- Semua -");
		area.setId(null);
		areas.add(0, area);
		listModel = new ListModelList<>(areas);
		cb_area.setModel(listModel);
		cb_area.setItemRenderer(new AreaRenderer());
		cb_area.setReadonly(true);
	}
	
	class AreaRenderer implements ComboitemRenderer<Area>{

		@Override
		public void render(Comboitem item, Area entity, int idx)
				throws Exception {
			// TODO Auto-generated method stub
			//System.out.println("Anchor : "+entity.getStatus());
			item.setValue(entity.getId());
			item.setLabel(entity.getNamaArea());
			//System.out.println("index area : "+idx);
			
		}
		
	}
	
	private void initComboSaveAs(){
		cb_saveAs.setReadonly(true);
		cb_saveAs.setSelectedIndex(0);
	}
	
	

}