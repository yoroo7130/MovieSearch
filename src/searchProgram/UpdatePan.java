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
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class UpdatePan extends JPanel {
	JTextField text_id,textmoive_number , textid, textcomment;
	JComboBox<String> combograde;
	String[] grade = {"1","2","3","4","5","6","7","8","9","10"};
	SearchPan sPan = new SearchPan();
	JButton butreview = new JButton("리뷰작성");
	JTable table;
	String id;
	
	public UpdatePan(String id) {
		this.id = id;
		setLayout(new BorderLayout());
		
		table = sPan.table;
		table.addMouseListener(tableHandler);
				
		JPanel inPan = new JPanel(new GridLayout(3, 1));	//3행 1열로 그리드레이아웃설정
		add(sPan,"Center");
		add(inPan,"South");
		
		//inPan에 들어갈 3개의 패널 생성
		JPanel[] pans = new JPanel[3];
		for (int i = 0; i < pans.length; i++) {
			pans[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));	//왼쪽정렬 설정
			inPan.add(pans[i]);
		}
		JLabel[] lbls = new JLabel[4];
		String[] lblStrs = {"아이디","영화번호","평점","리뷰"};
		for (int i = 0; i < lblStrs.length; i++) {
			lbls[i] = new JLabel(lblStrs[i]+": ");
		}
		text_id = new JTextField(20);
		textmoive_number = new JTextField(20);
		textid = new JTextField(20);
		textcomment = new JTextField(50);
		combograde = new JComboBox<>(grade);

		text_id.setEnabled(false);
		textmoive_number.setEnabled(false);
		butreview.addActionListener(butHandler);
		text_id.setText(id);

		
		pans[0].add(lbls[0]);
		pans[0].add(text_id);
		pans[0].add(lbls[1]);
		pans[0].add(textmoive_number);
		
		pans[0].add(lbls[2]);
		pans[0].add(combograde);
		
		pans[1].add(lbls[3]);
		pans[1].add(textcomment);
		pans[2].add(butreview);
		
	}
	
	public void UpdateSQL() {
		Connection con = DBConnector.getCon();
		
		String sql = "insert into review (id, movie_number, comment, grade) values(?,?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, text_id.getText());	//첫번째 ?값 반환
			pstmt.setString(2, textmoive_number.getText());
			pstmt.setString(3, textcomment.getText());
			pstmt.setInt(4, (combograde.getSelectedIndex()+1));
			
			
			pstmt.executeUpdate();	//이것으로 실행해야 INSERT 가능
			
			sPan.execSQL();
			sPan.setTable();
			if(con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void initValue() {	// 초기값설정
		textmoive_number.setText("");
		textcomment.setText("");
		combograde.setSelectedItem(0);
	}
	ActionListener butHandler = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Object eBut = e.getSource();
			
			if (eBut == butreview) {
				UpdateSQL();
				initValue();
			}
		}
	};
	//익명클래스 구현, 객체생성
	//마우스 어댑터로 설정해야 나머지 메소드 지워도 에러 안남
	MouseListener tableHandler = new MouseAdapter() {	
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			if (e.getClickCount()==2) {//마우스 클릭 횟수
				int row = table.getSelectedRow();	//선택한행번호 반환
				textmoive_number.setText(table.getValueAt(row, 0).toString()); // 반환받고싶은 행번호,열번호
				//getValueAt는 Object를 반환 
			}
			
		}
	};
}
