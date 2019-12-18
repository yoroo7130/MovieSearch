package searchProgram;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MovieFrame extends JFrame {
	JTabbedPane tabs = new JTabbedPane();
	Recommend rePan = new Recommend();
	MemberList mlPan = new MemberList();
	Mypage mPan;
	String id;
	
	public MovieFrame(String id) {
		this.id = id;
		mPan = new Mypage(id);
		tabs.addChangeListener(tabHandler);
		tabs.add("회원", mPan);
		tabs.addTab("검색", new UpdatePan(id));
		tabs.addTab("추천", rePan);
		tabs.addTab("관리", mlPan);
		add(tabs);
		tabs.setTabPlacement(JTabbedPane.RIGHT);
		
		
		setTitle("영화 검색 및 리뷰 프로그램");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280, 800);
		setResizable(false); // 창 크기 조절 불가
		setLocationRelativeTo(null); // 화면 가운데 생성
		setVisible(true);
	}
	
//	public static void main(String[] args) {
//		new MovieFrame();
//	}
	
	ChangeListener tabHandler = new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			switch (tabs.getSelectedIndex()) {
			case 0:
				mPan.execSQL();
				break;
			}
		}
	};
}
