package searchProgram;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginCheck extends JDialog
{
	JButton btnOK = new JButton("확인");
	int Check_id =0;
	LoginForm  pan;

	
	public LoginCheck(LoginForm pan)
	{
		 this.pan=pan;
		
		JLabel lbl =null ;
		
		JPanel panB = new JPanel();
		pan.execSQL();
		
		if(pan.Check_num == 0) {
			lbl = new JLabel("아이디 비밀 번호 일치 하지 않습니다.",JLabel.CENTER);
			panB.add(btnOK);
			add(lbl, "Center");
			add(panB, "South");
			btnOK.addActionListener(btnHandler);
			
			Toolkit tk = Toolkit.getDefaultToolkit(); 
		    Dimension screenSize = tk.getScreenSize();
		    setTitle("로그인 확인 창");
		    setSize(300,150);
			setLocation(screenSize.width / 2 - this.getWidth() / 2, screenSize.height/2 - this.getHeight()/2);
			setVisible(true);
			
			pan.Check = false;
		}else {
			pan.Check = true;
		}
		
		
		
		
	}
	
	ActionListener btnHandler = new ActionListener()
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object eBtn = e.getSource();
			
			if(eBtn == btnOK)
			{
				
				LoginCheck.this.dispose();
			
			}
			
		}
	};		
}
