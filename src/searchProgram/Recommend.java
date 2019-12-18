package searchProgram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Recommend extends JPanel{
	
	JButton reGrade, reReview, reCate, reDay;
	
	public Recommend() {
		setLayout(new BorderLayout());
		JPanel pan = new JPanel();
		JPanel rePan = new JPanel();
		
		String s = "관심사를 선택해 주세요.";
		Font font = new Font("맑은 고딕", Font.BOLD, 30);
		JLabel lbl = new JLabel(s);
		lbl.setFont(font);
		
		reGrade = new JButton("평점");
		reGrade.setBackground(Color.GRAY);
		reGrade.setForeground(Color.WHITE);
		reGrade.setFont(font);
		reReview = new JButton("리뷰");
		reReview.setBackground(Color.GRAY);
		reReview.setForeground(Color.WHITE);
		reReview.setFont(font);
		reCate = new JButton("장르");
		reCate.setBackground(Color.GRAY);
		reCate.setForeground(Color.WHITE);
		reCate.setFont(font);
		reDay = new JButton("개봉일");
		reDay.setBackground(Color.GRAY);
		reDay.setForeground(Color.WHITE);
		reDay.setFont(font);

		reGrade.addActionListener(btnHandelr);
		reCate.addActionListener(btnHandelr);
		reReview.addActionListener(btnHandelr);
		reDay.addActionListener(btnHandelr);
		
		pan.add(lbl);
		rePan.add(reGrade);
		rePan.add(reReview);
		rePan.add(reCate);
		rePan.add(reDay);
		GridLayout grid = new GridLayout(2, 2);
		grid.setHgap(10);
		grid.setVgap(10);
		rePan.setLayout(grid);
		
		add(pan,"North");
		add(rePan,"Center");
	}
	
	ActionListener btnHandelr = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Object eBtn = e.getSource();
			if(eBtn == reGrade) {
				new Recommend_grade();
			} else if(eBtn == reReview) {
				new Recommend_review();
			} else if(eBtn == reCate) {
				new Recommend_category();
			} else {
				new Recommend_day();
			}
		}
	};
}
