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

public class CheckInsert extends JDialog
{
	JButton btnOK = new JButton("확인");
	int Check_id =0;
	MadeIdForm  pan;
	
	public CheckInsert(MadeIdForm pan)
	{
		 this.pan=pan;
		
		JLabel lbl = new JLabel("아이디 중복 검사 필요합니다.",JLabel.CENTER);;
		
		JPanel panB = new JPanel();
		
		panB.add(btnOK);
	
		add(lbl, "Center");
		add(panB, "South");
		
		btnOK.addActionListener(btnHandler);
		
		Toolkit tk = Toolkit.getDefaultToolkit(); 
	    Dimension screenSize = tk.getScreenSize();
	    
	    setSize(200,150);
		setLocation(screenSize.width / 2 - this.getWidth() / 2, screenSize.height/2 - this.getHeight()/2);
		setVisible(true);
	}
	
	ActionListener btnHandler = new ActionListener()
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object eBtn = e.getSource();
			
			if(eBtn == btnOK)
			{
				
				CheckInsert.this.dispose();
			
			}
			
		}
	};		
}
