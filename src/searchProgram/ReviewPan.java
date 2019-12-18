package searchProgram;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;



public class ReviewPan extends JPanel implements MouseListener {
	JComboBox<String> searchCombo;
	DefaultTableModel model;
	JTable table;
	String[] cols = {"리뷰번호","영화명","장르","감독","대표배우","개봉날짜","러닝타임","제작국가","평점","리뷰"};
	String[] colNames = {"review_number","movie_name","category_name","director","actor","day","country, grade, review"};
	int[] review_number, grade;
	String[] movie_name,category,director,actor,day,time,country, review;
	String searchWord="";
	String colName = "movie_name";
	String id;
	String review_num;
	JButton btnDelete;

	public ReviewPan(String id) {
		this.id = id;
		setLayout(new BorderLayout());
		JPanel nPan = new JPanel();
		JLabel lbl = new JLabel("나의리뷰");
		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(btnHandler);

		nPan.add(lbl);
		nPan.add(btnDelete);
		
		add(nPan,BorderLayout.NORTH);
		
		model = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {	//테이블이 편집이 안되도록 설정
				return false;
			}
		};
		
		table = new JTable(model);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//단일선택
		table.addMouseListener(this);
		JScrollPane cPan = new JScrollPane(table);
		cPan.setPreferredSize(new java.awt.Dimension(1200, 300));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(50);//칼럼의 크기설정 200px로 
		table.getColumnModel().getColumn(1).setPreferredWidth(150); // 영화명
		table.getColumnModel().getColumn(2).setPreferredWidth(100); // 장르
		table.getColumnModel().getColumn(3).setPreferredWidth(100); // 감독
 		table.getColumnModel().getColumn(4).setPreferredWidth(100); // 대표배우
		table.getColumnModel().getColumn(5).setPreferredWidth(100); // 개봉일자
		table.getColumnModel().getColumn(6).setPreferredWidth(50); // 러닝타임
		table.getColumnModel().getColumn(7).setPreferredWidth(50); // 제작국가
		table.getColumnModel().getColumn(8).setPreferredWidth(50); // 평점
		table.getColumnModel().getColumn(9).setPreferredWidth(450);
		table.setRowHeight(20);
		table.getTableHeader().setReorderingAllowed(false);	//테이블 위치를 바꿀수없도록 설정
		table.getTableHeader().setResizingAllowed(false);	//칼럼크기 변경 불가
		
		add(cPan,BorderLayout.WEST);
		execSQL();
		setTable();
		
		
	}
	
	//	DB연결전 후 문장객체로 sql문을 실행시키는 메소드
	public void execSQL(){
		Connection con=DBConnector.getCon();
		String sql = "select count(*) "
				+ "from review r, movie m, member u , category c "
				+ "where r.movie_number = m.movie_number and r.id = u.id and c.category_id = m.category_id and u.id = ?;";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			
			review_number = new int[count];
			movie_name = new String[count];
			category = new String[count];
			director = new String[count];
			actor = new String[count];
			day = new String[count];
			time = new String[count];
			country = new String[count];
			grade = new int[count];
			review = new String[count];
			
			sql = "select r.review_number, m.movie_name, c.category_name, m.director, m.actor, m.day, m.time, m.country, r.grade, r.comment "
					+ "from review r, movie m, member u , category c "
					+ "where r.movie_number = m.movie_number and r.id = u.id and c.category_id = m.category_id and u.id = ?;";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			int i = 0;
			while(rs.next()) {
				review_number[i] = rs.getInt("review_number");
				movie_name[i] = rs.getString("movie_name");
				category[i] = rs.getString("category_name");
				director[i] = rs.getString("director");
				actor[i] = rs.getString("actor");
				day[i] = rs.getString("day");
				time[i] = rs.getString("time");
				country[i] = rs.getString("country");
				grade[i] = rs.getInt("grade");
				review[i] = rs.getString("comment");
				i++;
			}
			if(con!=null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteSQL() {
		Connection con = DBConnector.getCon();
		String sql = "delete from review where review_number = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(review_num));
			
			pstmt.executeUpdate();
			
			execSQL();
			setTable();
			
			if (con!=null) {
				con.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setTable() {
		model.setRowCount(review_number.length);
		for (int i = 0; i < review_number.length; i++) {
			table.setValueAt(review_number[i], i, 0);
			table.setValueAt(movie_name[i], i, 1);
			table.setValueAt(category[i], i, 2);
			table.setValueAt(director[i], i, 3);
			table.setValueAt(actor[i], i, 4);
			table.setValueAt(day[i], i, 5);
			table.setValueAt(time[i], i, 6);
			table.setValueAt(country[i], i, 7);
			table.setValueAt(grade[i], i, 8);
			table.setValueAt(review[i], i, 9);
		}
	}	
	
	// 익명(Anonymous) 클래스로 Event Handler 클래스를 구현하고 객체 생성까지 함
	ActionListener btnHandler = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
				Object ebtn = e.getSource();
				if(ebtn == btnDelete) {
					deleteSQL();
				}
			}
			
	};


	@Override
	public void mouseClicked(MouseEvent arg0) {
		int row = table.getSelectedRow();
		review_num = table.getValueAt(row, 0).toString();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	}


	
