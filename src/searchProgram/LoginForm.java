package searchProgram;

import java.awt.BorderLayout;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends JFrame {
	JButton LoginBtn, MadeBtn;
	boolean Check = false; // 아이디 비밀번호 맞는지 체크할 변수 LoginCheck 클래스에서 확인함
	int Check_num = 0; // execSQL() 처리후 아이디 비밀번호 맞으면 1로 설정 틀리면 0으로 설정 , LoginCheck 클래스에서 처리 용
	JTextField IdText;
	JPasswordField PwdText;

	public LoginForm() {
		setLayout(new GridLayout(4, 1));

		Font font = new Font("맑은 고딕", Font.BOLD, 25);
		JLabel[] lbls = new JLabel[3];
		lbls[0] = new JLabel("Movie Search");
		lbls[0].setFont(font);
		lbls[1] = new JLabel("아이디 : ");
		lbls[2] = new JLabel("비밀번호 : ");
		IdText = new JTextField(20);
		PwdText = new JPasswordField(19);

		LoginBtn = new JButton("로그인");
		MadeBtn = new JButton("회원가입");
		LoginBtn.addActionListener(btnHandler);
		MadeBtn.addActionListener(btnHandler);

		JPanel[] pans = new JPanel[4];
		for (int i = 0; i < pans.length; i++) {
			pans[i] = new JPanel(new FlowLayout(FlowLayout.CENTER));
			add(pans[i]);
		}

		pans[0].add(lbls[0]);
		pans[1].add(lbls[1]);
		pans[1].add(IdText);
		pans[2].add(lbls[2]);
		pans[2].add(PwdText);
		pans[3].add(LoginBtn);
		pans[3].add(MadeBtn);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();

		setSize(400, 200);

		setTitle("영화 시스템");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(screenSize.width / 2 - this.getWidth() / 2, screenSize.height / 2 - this.getHeight() / 2);
		setVisible(true);

	}

	@SuppressWarnings("deprecation")
	public void execSQL() // 아이디 비밀번호 확인할 함수
	{
		Connection con = DBConnector.getCon();
		String sql = "SELECT id,password FROM member where id= ? and password = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, IdText.getText());
			pstmt.setString(2, PwdText.getText());
			rs = pstmt.executeQuery();

			if (rs.next()) { // 아이디 비밀번호 맞으면 실행 함수 , 없으면 초기 값인 0 인 상태
				Check_num = 1;
			}
			rs.close();

			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new LoginForm();
	}

	ActionListener btnHandler = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object eBtn = e.getSource();
			if (eBtn == LoginBtn) { // 로그인 버튼
				new LoginCheck(LoginForm.this); // 아이디 비밀번호 맞는지 확인할 클래스
				if (Check == true) { // 아이디 비밀번호 맞을 시
					dispose();
					// 띄울 창 입력하면됨
					new MovieFrame(IdText.getText());
				}
			} else { // 회원 가입 버튼
				dispose();
				new MadeIdForm();
			}
		}
	};

}
