package searchProgram;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mysql.cj.xdevapi.Result;

public class Mypage extends JPanel {
	JTextField textid,textname,textage,textgender;
	ReviewPan rPan;
	JButton btnlogout = new JButton("로그아웃");
	JButton btndelete = new JButton("삭제");
	int[] age;
	String[] name,gender;
	JTable table;
	String id;
	
	public Mypage(String id) {
		this.id = id;
		rPan = new ReviewPan(id);
		setLayout(new BorderLayout());
		
		table = rPan.table;
		JPanel inPan = new JPanel(new GridLayout(3, 1));	//3행 1열로 그리드레이아웃설정
		JPanel inPan2 = new JPanel(new BorderLayout());
		
		add(rPan,"Center");
		inPan2.add(inPan,"Center");
		add(inPan2,"North");
		
		
		
		//inPan에 들어갈 3개의 패널 생성
		JPanel[] pans = new JPanel[5];
		for (int i = 0; i < pans.length; i++) {
			pans[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));	//왼쪽정렬 설정
			inPan.add(pans[i]);
		}
		JLabel[] lbls = new JLabel[4];
		String[] lblStrs = {"아이디","이름","나이","성별"};
		for (int i = 0; i < lblStrs.length; i++) {
			lbls[i] = new JLabel(lblStrs[i]+": ");
		}
		textid = new JTextField(20);
		textname = new JTextField(20);
		textage = new JTextField(20);
		textgender = new JTextField(20);

		
		textid.setEnabled(false);
		textname.setEnabled(false);
		textage.setEnabled(false);
		textgender.setEnabled(false);
		btnlogout.addActionListener(btnHandler);
		btndelete.addActionListener(btnHandler);

		
		pans[0].add(lbls[0]);
		pans[0].add(textid);
		pans[0].add(btnlogout);
		pans[1].add(lbls[1]);
		pans[1].add(textname);
		pans[2].add(lbls[2]);
		pans[2].add(textage);
		pans[3].add(lbls[3]);
		pans[3].add(textgender);
		
		execSQL();
		
	}
	
	public void execSQL() {
		Connection con = DBConnector.getCon();
		String sql = "select * from member where id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				textid.setText(rs.getString("id"));
				textname.setText(rs.getString("name"));
				textage.setText(rs.getString("age"));
				textgender.setText(rs.getString("gender"));
			}
			
			
			
			pstmt.executeQuery();	//이것으로 실행해야 INSERT 가능
			
			rPan.execSQL();
			rPan.setTable();
			if(con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void initValue() {	// 초기값설정
		textid.setText("");
		textname.setText("");
		textage.setText("");
		textgender.setText("");
	}
	
	ActionListener btnHandler = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Object eBtn = e.getSource();
			if(eBtn == btnlogout) { // 로그아웃 버튼 클릭시
				System.exit(0);
			}
			if(eBtn == btndelete) {
				// 삭제 버튼 클릭시
			}
		}
	};

}
