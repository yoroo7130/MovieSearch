package searchProgram;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MadeIdForm extends JFrame {
	JButton OkBtn,CnlBtn,idtyBtn; 
	JTextField[] Texts;
	int Check_id = 0;	//아이디 값이 null 인지 중복된 아이디 인지 생성 가능 아이디 인지 확인 용 변수
	boolean Check = false; //중복확인 했는지 확인 용 변수 , 확인 후 생성 가능 한 아이디이면 true
	
	public MadeIdForm() {
		
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
		
		Texts= new JTextField[6];
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
	public void insertSQL()
	{
		Connection con = DBConnector.getCon();
		String sql = "Insert Into member values(?, ?, ?, ?, ?)";
		try
		{
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Texts[0].getText());
			pstmt.setString(2, Texts[1].getText());
			pstmt.setString(3, Texts[3].getText());
			pstmt.setInt(4, Integer.parseInt(Texts[4].getText()));
			pstmt.setString(5, Texts[5].getText());
		
			pstmt.executeUpdate();
			
			if(con != null)
			{
				con.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void CheckId()
	{
		Connection con = DBConnector.getCon();
		String sql = "Select id from member where id = ?";
		ResultSet rs = null;
	try
		{
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, Texts[0].getText());
		rs = pstmt.executeQuery();
		if(rs.next()){	// db 안에 있는 아이디인 경우 
		     Check_id = 1;

		}else{	// db 안에 없는 아이디인 경우 
		     Check_id = 2;
		}
		rs.close();
		if(con != null)
		{
			con.close();
		}
	}
	catch (SQLException e)
	{
		e.printStackTrace();
	}
	if(Texts[0].getText().equals("")) { //아이디 칸이 공백 일경우 
		Check_id = 0;
	}
		
	}
	
	ActionListener btnHandler = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object eBtn = e.getSource();
			if(eBtn == CnlBtn) {// 취소 버튼 
				dispose();
				new LoginForm();
			}else if(eBtn == OkBtn){	//확인 버튼
				if(Check == false) {	//중복 확인 완료 했는지 여부  , 확인 안했을 시 
					new CheckInsert(MadeIdForm.this);	//중복 확인 하라고 보여줄 창
				}else {	//중복 확인 완료 했으면
				dispose();
				new LoginForm();
				insertSQL();
				}
			}else {	//중복 확인 버튼 
				new CheckDialog(MadeIdForm.this);
			}
		}
	};
}
