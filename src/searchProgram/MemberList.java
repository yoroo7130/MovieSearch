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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class MemberList extends JPanel {
	JTable table;
	DefaultTableModel model;
	String[] id, name, gender;
	int[] age;

	public MemberList() {
		setLayout(new BorderLayout());
		JPanel Pan = new JPanel();
		String s = "사용자 목록"; // 멘트
		Font font = new Font("맑은 고딕", Font.BOLD, 25); // 폰트설정
		JLabel lbl = new JLabel(s);
		lbl.setFont(font);

		String[] colsName = { "아이디", "이름", "나이", "성별" }; // 속성 값 선언

		model = new DefaultTableModel(colsName, 0) {// 기본 테이블 모델 생성(열,행)
			public boolean isCellEditable(int arg0, int arg1) {
				return false; // 테이블 내 데이터 수정 불가 설정
			}
		};

		table = new JTable(model); // 테이블 선언
		table.setRowHeight(25); // 높이 조절하는 부분.
		table.getTableHeader().setReorderingAllowed(false); // 테이블 위치를 바꿀수없도록 설정
		table.getTableHeader().setResizingAllowed(false); // 칼럼크기 변경 불가

		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = table.getColumnModel();
		
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		
		JScrollPane cPan = new JScrollPane(table);

		JScrollPane scrollPane = new JScrollPane(table, // 스크롤바 구현
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		Pan.add(lbl);
		Pan.add(table);
		add(Pan, "North");

		add(new JScrollPane(table));

		SQL();
		setTable();
	}

	public void SQL() {
		Connection con = DBConnector.getCon();
		String sql = "select count(*) from member";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);

			id = new String[count];
			name = new String[count];
			age = new int[count];
			gender = new String[count];

			sql = "select id, name, age, gender from member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			int i = 0;
			while (rs.next()) {
				id[i] = rs.getString(1);
				name[i] = rs.getString(2);
				age[i] = rs.getInt(3);
				gender[i] = rs.getString(4);
				i++;
			}

			if (con != null)
				con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setTable() {
		model.setRowCount(id.length);
		for (int i = 0; i < id.length; i++) {
			table.setValueAt(id[i], i, 0);
			table.setValueAt(name[i], i, 1);
			table.setValueAt(age[i], i, 2);
			table.setValueAt(gender[i], i, 3);
		}
	}
}