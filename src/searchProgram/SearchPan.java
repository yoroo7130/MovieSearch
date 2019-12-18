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



public class SearchPan extends JPanel implements MouseListener {
	JTextField searchText;
	JComboBox<String> searchCombo;
	DefaultTableModel model;
	JTable table;
	//JScrollPane scroll;
	String[] cols = {"영화번호","영화명","장르","감독","대표배우","개봉날짜","러닝타임","제작국가"};
	String[] comboStr = {"영화번호","영화명","장르명"};
	String[] colNames = {"movie_number","movie_name","category_name","director","actor","day","country"};	//Combobox와 검색어를 같이 이용해 도서검색
	int[] movie_number;
	String[] movie_name,category,director,actor,day,time,country;
	String searchWord="";
	String colName = "movie_name";
	
	ImageIcon[] images = { new ImageIcon("imgs/A01.jpg"),
							new ImageIcon("imgs/A02.jpg"),
							new ImageIcon("imgs/A03.jpg"),
							new ImageIcon("imgs/B01.jpg"),
							new ImageIcon("imgs/B02.jpg"),
							new ImageIcon("imgs/B03.jpg"),
							new ImageIcon("imgs/B04.jpg"),
							new ImageIcon("imgs/C01.jpg"),
							new ImageIcon("imgs/C02.jpg"),
							new ImageIcon("imgs/C03.jpg"),
							new ImageIcon("imgs/D01.jpg"),
							new ImageIcon("imgs/D02.jpg"),
							new ImageIcon("imgs/D03.jpg"),
							new ImageIcon("imgs/D04.jpg"),
							new ImageIcon("imgs/E01.jpg"),
							new ImageIcon("imgs/E02.jpg"),
							new ImageIcon("imgs/E03.jpg"),
							new ImageIcon("imgs/F01.jpg"),
							new ImageIcon("imgs/F02.jpg"),
							new ImageIcon("imgs/F03.jpg"),
							new ImageIcon("imgs/F04.jpg"),
							new ImageIcon("imgs/F05.jpg"),
							new ImageIcon("imgs/G01.jpg"),
							new ImageIcon("imgs/G02.jpg"),
							new ImageIcon("imgs/H01.jpg"),
							new ImageIcon("imgs/H02.jpg"),
							new ImageIcon("imgs/H03.jpg"),
							new ImageIcon("imgs/H04.jpg"),
							new ImageIcon("imgs/H05.jpg"),
							new ImageIcon("imgs/I01.jpg"),
							new ImageIcon("imgs/I02.jpg"),
							new ImageIcon("imgs/I03.jpg"),
	};
	JLabel imgLabel = new JLabel(images[0]);
	

	public SearchPan() {
		setLayout(new BorderLayout());
		JPanel nPan = new JPanel();
		JLabel lbl = new JLabel("검색어 : ");
		searchText = new JTextField(20);
		searchCombo = new JComboBox<>(comboStr);
		JButton searchBut = new JButton("검색");
		searchBut.addActionListener(butHandler);

		nPan.add(lbl);
		nPan.add(searchText);
		nPan.add(searchCombo);
		nPan.add(searchBut);
		
		add(nPan,BorderLayout.NORTH);
		add(imgLabel);
		
		
		
		
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
		cPan.setPreferredSize(new java.awt.Dimension(700, 300));
		
		table.getColumnModel().getColumn(1).setPreferredWidth(200);//칼럼의 크기설정 200px로 
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
		String sql = "select count(*) from movie m, category c "
						+"where m.category_id = c.category_id "
						+"and "+colName+" like ? ";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchWord + "%");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			
			movie_number = new int[count];
			movie_name = new String[count];
			category = new String[count];
			director = new String[count];
			actor = new String[count];
			day = new String[count];
			time = new String[count];
			country = new String[count];
			
			sql = "select * from movie m, category c " +
					"where m.category_id = c.category_id "
					+ "and " + colName +" like ? order by movie_number";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchWord + "%");
			rs = pstmt.executeQuery();
			
			int i = 0;
			while(rs.next()) {
				movie_number[i] = rs.getInt("movie_number");
				movie_name[i] = rs.getString("movie_name");
				category[i] = rs.getString("category_name");
				director[i] = rs.getString("director");
				actor[i] = rs.getString("actor");
				day[i] = rs.getString("day");
				time[i] = rs.getString("time");
				country[i] = rs.getString("country");
				i++;
			}
			if(con!=null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setTable() {
		model.setRowCount(movie_number.length);
		for (int i = 0; i < movie_number.length; i++) {
			table.setValueAt(movie_number[i], i, 0);
			table.setValueAt(movie_name[i], i, 1);
			table.setValueAt(category[i], i, 2);
			table.setValueAt(director[i], i, 3);
			table.setValueAt(actor[i], i, 4);
			table.setValueAt(day[i], i, 5);
			table.setValueAt(time[i], i, 6);
			table.setValueAt(country[i], i, 7);
		}
	}	
	
	
	// 익명(Anonymous) 클래스로 Event Handler 클래스를 구현하고 객체 생성까지 함
	ActionListener butHandler = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			searchWord = searchText.getText();//입력창에 입력된 문자열을 반환해서 searchword 변수에 저장
			colName = colNames[searchCombo.getSelectedIndex()];
			execSQL();
			setTable();
			}
			
		
	};


	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
		int row = table.getSelectedRow();
		int r = (int) table.getValueAt(row, 0);
		switch(r-1) {
		case 0:
			imgLabel.setIcon(images[0]);
			break;
		case 1:
			imgLabel.setIcon(images[1]);
			break;
		case 2:
			imgLabel.setIcon(images[2]);
			break;
		case 3:
			imgLabel.setIcon(images[3]);
			break;
		case 4:
			imgLabel.setIcon(images[4]);
			break;
		case 5:
			imgLabel.setIcon(images[5]);
			break;
		case 6:
			imgLabel.setIcon(images[6]);
			break;
		case 7:
			imgLabel.setIcon(images[7]);
			break;
		case 8:
			imgLabel.setIcon(images[8]);
			break;
		case 9:
			imgLabel.setIcon(images[9]);
			break;
		case 10:
			imgLabel.setIcon(images[10]);
			break;
		case 11:
			imgLabel.setIcon(images[11]);
			break;
		case 12:
			imgLabel.setIcon(images[12]);
			break;
		case 13:
			imgLabel.setIcon(images[13]);
			break;
		case 14:
			imgLabel.setIcon(images[14]);
			break;
		case 15:
			imgLabel.setIcon(images[15]);
			break;
		case 16:
			imgLabel.setIcon(images[16]);
			break;
		case 17:
			imgLabel.setIcon(images[17]);
			break;
		case 18:
			imgLabel.setIcon(images[18]);
			break;
		case 19:
			imgLabel.setIcon(images[19]);
			break;
		case 20:
			imgLabel.setIcon(images[20]);
			break;
		case 21:
			imgLabel.setIcon(images[21]);
			break;
		case 22:
			imgLabel.setIcon(images[22]);
			break;
		case 23:
			imgLabel.setIcon(images[23]);
			break;
		case 24:
			imgLabel.setIcon(images[24]);
			break;
		case 25:
			imgLabel.setIcon(images[25]);
			break;
		case 26:
			imgLabel.setIcon(images[26]);
			break;
		case 27:
			imgLabel.setIcon(images[27]);
			break;
		case 28:
			imgLabel.setIcon(images[28]);
			break;
		case 29:
			imgLabel.setIcon(images[29]);
			break;
		case 30:
			imgLabel.setIcon(images[30]);
			break;	
		case 31:
			imgLabel.setIcon(images[31]);
			break;
		
		}
	}
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
