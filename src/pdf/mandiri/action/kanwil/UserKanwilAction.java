package pdf.mandiri.action.kanwil;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import pdf.mandiri.dao.PenggunaDao;
import pdf.mandiri.domain.Pengguna;
import pdf.mandiri.util.HibernateUtil;

public class UserKanwilAction extends GenericForwardComposer<Component> {

	private Pengguna pengguna;
	private PenggunaDao penggunaDao;
	
	Textbox text_username;
	Textbox text_nama;
	Textbox text_password;
	Textbox text_email;
	Label lbl_kanwil;
	Textbox text_notelp;
	Textbox text_alamat;
	Textbox text_pass_email;
	
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		getData();
		renderToView();
		
	}

	
	private void getData(){
		Pengguna penggunaTemp = (Pengguna) session.getAttribute("pengguna");
		penggunaDao = HibernateUtil.getPenggunaDao();
		pengguna = penggunaDao.getById(penggunaTemp.getId());
	}
	
	private void renderToView(){
		text_email.setText(pengguna.getEmail());
		text_nama.setText(pengguna.getNama());
		text_notelp.setText(pengguna.getNoTelp());
		text_alamat.setText(pengguna.getAlamat());
		lbl_kanwil.setValue(pengguna.getKanwil().getNama());
		text_username.setText(pengguna.getUsername());
		text_password.setText(pengguna.getPassword());
	//	text_pass_email.setText(pengguna.getPasswordEmail());
		
	}
	
	public void onSave(){
		pengguna.setAlamat(text_alamat.getText());
		pengguna.setEmail(text_email.getText());
		pengguna.setNama(text_nama.getText());
		pengguna.setNoTelp(text_notelp.getText());
		pengguna.setUsername(text_username.getText());
		pengguna.setPasswordEmail("");
		
		if(!text_password.getText().equalsIgnoreCase("")){
			pengguna.setPassword(text_password.getText());
		}
		
		penggunaDao.update(pengguna);
		Messagebox.show("Berhasil disimpan", "Simpan data", Messagebox.OK, null, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
			}
		});
		
	}
}
