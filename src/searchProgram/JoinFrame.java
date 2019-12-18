package searchProgram;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JoinFrame extends JFrame {
	JButton OkBtn,CnlBtn,idtyBtn; 
	public JoinFrame() {
		
		setLayout(new GridLayout(8, 1));
		Font font = new Font("맑은 고딕", Font.BOLD, 25);
		JLabel[] lbls= new JLabel[7];
		lbls[0]= new JLabel("Movie Search");
		lbls[0].setFont(font);
		lbls[1]= new JLabel("아이디           : ");
		lbls[2]= new JLabel("비밀번호        : ");
		lbls[3]= new JLabel("비밀번호 확인 : ");
		lbls[4]= new JLabel("이름              : ");
		lbls[5]= new JLabel("나이              : ");
		lbls[6]= new JLabel("성별              : ");
		
		
		OkBtn= new JButton("완료");
		CnlBtn = new JButton("취소");
		idtyBtn = new JButton("중복 확인");
		OkBtn.addActionListener(btnHandler);
		idtyBtn.addActionListener(btnHandler);
		CnlBtn.addActionListener(btnHandler);
		JPanel[] pans = new JPanel[8];
		pans[0]= new JPanel(new FlowLayout(FlowLayout.CENTER));
		add(pans[0]);
		for (int i = 1; i < pans.length-1; i++) {
			pans[i]= new JPanel(new FlowLayout(FlowLayout.LEFT));
			add(pans[i]);
		}
		pans[7]= new JPanel(new FlowLayout(FlowLayout.CENTER));
		add(pans[7]);
		
		JTextField[] Texts= new JTextField[6];
		for(int i= 0;i<Texts.length;i++) {
			Texts[i] = new JTextField(19);
		}
		
		pans[0].add(lbls[0]);
		pans[1].add(lbls[1]);
		pans[1].add(Texts[0]);
		pans[1].add(idtyBtn);
		pans[2].add(lbls[2]);
		pans[2].add(Texts[1]);
		pans[3].add(lbls[3]);
		pans[3].add(Texts[2]);
		pans[4].add(lbls[4]);
		pans[4].add(Texts[3]);
		pans[5].add(lbls[5]);
		pans[5].add(Texts[4]);
		pans[6].add(lbls[6]);
		pans[6].add(Texts[5]);
		pans[7].add(OkBtn);
		pans[7].add(CnlBtn);
		
	
		Toolkit tk = Toolkit.getDefaultToolkit(); 
	    Dimension screenSize = tk.getScreenSize();

	    setSize(450,450);
		setTitle("회원 가입");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(screenSize.width / 2 - this.getWidth() / 2, screenSize.height/2 - this.getHeight()/2);
		setVisible(true);
	}
	
	ActionListener btnHandler = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object eBtn = e.getSource();
			if(eBtn == CnlBtn) {// 취소 버튼 
				dispose();
				new LoginForm();
			}else if(eBtn == OkBtn){	//확인 버튼
				dispose();
				new LoginForm();
			}else {		//중복 확인 버튼
				
			}
		}
	};
}
