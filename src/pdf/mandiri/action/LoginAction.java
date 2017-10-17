package pdf.mandiri.action;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import pdf.mandiri.dao.PenggunaDao;
import pdf.mandiri.domain.Pengguna;
import pdf.mandiri.util.HibernateUtil;

public class LoginAction extends GenericForwardComposer<Component>{

	
	Textbox text_username;
	Textbox text_password;
	
	String username;
	String password;
	
	Label text_confirm;
	boolean status = false;
	
	
	Pengguna pengguna;
	PenggunaDao penggunaDao;
	Session sessionZk;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
	}
	
	private void getUserPass(){
		username = text_username.getText();
		password = text_password.getText();
	}
	
	public void onLogin(){
		getUserPass();
	//	System.out.println("username : "+username);
	//	System.out.println("password : "+password);
		
		penggunaDao = HibernateUtil.getPenggunaDao();
		pengguna = penggunaDao.getPengguna(username, password);
		
		if(pengguna==null)
			showConfirm();
		else{
			session.setAttribute("pengguna", pengguna);
			session.setMaxInactiveInterval(6000);
			if(pengguna.getStatus()==1){
				session.setAttribute("role", new Integer(1));
				Executions.sendRedirect("./kanwil/");
			}
				
			else
				if(pengguna.getStatus()==2){
				//	Integer inte = new Integer(2);
					session.setAttribute("role", new Integer(2));
					Executions.sendRedirect("./pusat/");
					
				}
					else if (pengguna.getStatus()==3){
						session.setAttribute("role", new Integer(3));
						Executions.sendRedirect("./admin/");
						
					}
					
			
			}
	}
	
	private void showConfirm(){
		text_password.setText("");
		text_username.setText("");
		text_confirm.setVisible(true);
		text_username.setFocus(true);
	}
}
