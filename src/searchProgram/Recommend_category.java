package searchProgram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;

public class Recommend_category extends JFrame {
	JButton searchBut, closeBut;
	JComboBox<String> combo;
	JTable table;
	DefaultTableModel model;
	String[] movie_name, category_name;

	public Recommend_category() {
		setLayout(new BorderLayout());
		JPanel Pan = new JPanel();
		String s = "장르를 선택해주세요.        "; // 멘트
		Font font = new Font("맑은 고딕", Font.BOLD, 25); // 폰트설정
		JLabel lbl = new JLabel(s);
		lbl.setFont(font);

		searchBut = new JButton("검색"); // 검색버튼구현
		searchBut.setPreferredSize(new Dimension(70, 30));
		searchBut.setBackground(Color.GRAY); // 검색버튼 배경색
		searchBut.setForeground(Color.WHITE); // 검색버튼 글씨색
		closeBut = new JButton("닫기"); // 닫기버튼구현
		closeBut.setPreferredSize(new Dimension(70, 30));
		closeBut.setBackground(Color.GRAY); // 닫기버튼 배경색
		closeBut.setForeground(Color.WHITE); // 닫기버튼 글씨색
		searchBut.addActionListener(btnHandler);
		closeBut.addActionListener(btnHandler);
		String[] genreComboName = { "멜로/로멘스", "공포/스릴러", "판타지", "가족", "드라마", "범죄", "액션", "코미디" }; // genre 콤보박스 속성 값 선언

		combo = new JComboBox<String>(genreComboName);
		combo.setPreferredSize(new Dimension(120, 30)); // 콤보박스 사이즈조절

		String[] colsName = { "영화명", "장르" }; // 속성 값 선언

		model = new DefaultTableModel(colsName, 0) {// 기본 테이블 모델 생성(열,행)
			public boolean isCellEditable(int arg0, int arg1) {
				return false; // 테이블 내 데이터 수정 불가 설정
			}
		};

		table = new JTable(model); // 테이블 선언
		table.setRowHeight(25); // 높이 조절하는 부분
		JScrollPane cPan = new JScrollPane(table);

		JScrollPane scrollPane = new JScrollPane(table, // 스크롤바 구현
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		table.getColumn("영화명").setPreferredWidth(800); // 가로너비
		table.getColumn("장르").setPreferredWidth(200); // 가로너비
        table.getTableHeader().setReorderingAllowed(false); // 칼럼순서변경불가 
        table.getTableHeader().setResizingAllowed(false); // 칼럼크기변경불가 
        
		Pan.add(lbl);
		Pan.add(combo);
		Pan.add(searchBut);
		Pan.add(closeBut);
		Pan.add(table);
		add(Pan, "North");

		setTitle("장르");
		setSize(650, 600);
		setResizable(false); // 창 크기 조절 불가
		setLocationRelativeTo(null); // 화면 가운데 생성
		add(new JScrollPane(table));
		setVisible(true);
		
	}

	public void SQL() {
		Connection con = DBConnector.getCon();
		String sql = "select count(*) from movie m,category c "
				+ "where m.category_id = c.category_id and c.category_id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (combo.getSelectedIndex() + 1) * 10);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);

			movie_name = new String[count];
			category_name = new String[count];

			sql = "select movie_name,category_name from movie m,category c where m.category_id = c.category_id and c.category_id = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (combo.getSelectedIndex() + 1) * 10);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				movie_name[i] = rs.getString(1);
				category_name[i] = rs.getString(2);
				i++;
			}
			if (con != null)
				con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setTable() {
		model.setRowCount(movie_name.length);
		for (int i = 0; i < movie_name.length; i++) {
			table.setValueAt(movie_name[i], i, 0);
			table.setValueAt(category_name[i], i, 1);
		}
	}

//	public static void main(String[] args) {
//		new Recommend_category();
//	}

	ActionListener btnHandler = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object eBut = e.getSource();

			if (eBut == searchBut) {
				SQL();
				setTable();
			} else {
				dispose();
			}

		}
	};
}