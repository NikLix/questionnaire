package main;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import main.sql.Connection_PostgresSQL;

@SuppressWarnings("serial")
public class Interface extends JFrame{
	
	// ������� ���������� � ��������  
	Integer true_answer = 1;
	Integer count = 1;
	Integer count_true_ans = 0; 
	JButton button = new JButton("��������");
	
	JLabel  label_quest = new JLabel("");
	JLabel  label_count_true_ans = new JLabel("");
	JRadioButton answer1  = new JRadioButton("");
	JRadioButton answer2  = new JRadioButton("");
	JRadioButton answer3  = new JRadioButton("");
	JRadioButton answer4  = new JRadioButton("");	
		
	
	public Interface () {
		super("���������");
		Update_data(count);
		
		// ������ ������� ���� � ����������� ���� 1 ������� 7 �����
		this.setBounds(300,300,450,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = this.getContentPane();
		container.setLayout(new GridLayout(7,1));
		
	
		container.add(label_quest);
		container.add(label_count_true_ans);
		// ��������� RadioButton � ���� ������
		ButtonGroup group = new ButtonGroup();
		group.add(answer1);
		group.add(answer2);
		group.add(answer3);
		group.add(answer4);
		group.getSelection();
		
		container.add(answer1);
		container.add(answer2);
		container.add(answer3);
		container.add(answer4);
		
		// ������� ������� ������� ����� ����������� ��� ������� �� ������
		button.addActionListener(new ButtonEvenListener ());
		container.add(button);
	}
	
	/* ����� ��������� Select ������ �
	 * ��������� ���������� ������ � ���������  
	 *  */
	public void Update_data(Integer count) {	
		Connection_PostgresSQL conn =  new Connection_PostgresSQL();
		ResultSet rs = conn.select(""
				+ "SELECT quest,answer_one, answer_two, answer_three, answer_four, true_answer "
				+ "FROM list_questions WHERE id ="+count
				+ "");
		try {
			// ��������� ���� �� ��� ������ �� �������
			if(!rs.isBeforeFirst()) {
				label_quest.setText("�������� ������ ���" );
				double res = count_true_ans / (count-1);
				// ������� ������� ���������� ������� ���� ������ 70% �� ������� ��� ������������ ���� ����
				if (res>0.7) {
					label_count_true_ans.setText("���������� ���������� ������� "+count_true_ans+" ���� ����");
				}
				else {
					label_count_true_ans.setText("���������� ���������� ������� "+count_true_ans+" ���� �� ����");
				}
				answer1.setVisible(false);
				answer2.setVisible(false);
				answer3.setVisible(false);
				answer4.setVisible(false);
				button.setVisible(false);
				}
			
			
			while (rs.next()) {
			String quest = rs.getString("quest");
			label_quest.setText(quest);
			label_count_true_ans.setText("���������� ���������� ������� "+count_true_ans);
			answer1.setText(rs.getString("answer_one"));
			answer2.setText(rs.getString("answer_two"));
			answer3.setText(rs.getString("answer_three"));
			answer4.setText(rs.getString("answer_four"));
			this.true_answer = rs.getInt("true_answer");
			
			}
			
			
		} catch (SQLException e2) {
			System.out.println("��� ���������� ������� �������� ������");
			e2.printStackTrace();
		}
	}

	class ButtonEvenListener implements ActionListener  {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// ��������� ��� ������� ������������ � ���������� �������
			// �������� ��� �� ����
			if(answer1.isSelected() && true_answer == 1){
				TrueAnswer(answer1.getText());
			}
	        else if(answer2.isSelected() && true_answer == 2 ){
	        	TrueAnswer(answer2.getText());
	        }
	        else if(answer3.isSelected() && true_answer == 3 ){
	        	TrueAnswer(answer3.getText());
	        }
	        else if(answer4.isSelected() && true_answer == 4 ){
	        	TrueAnswer(answer4.getText());
	        }
	        else {
	        	count += 1;
	        	Update_data(count);
	        	String message = "�� ���������� �����";
				JOptionPane.showMessageDialog(null, message,"OutPut",JOptionPane.CANCEL_OPTION);
	        }
			
		}
		// ����� ���� ������������ ������� ����� 
		public void TrueAnswer(String ans) {
			count += 1;
			count_true_ans += 1;
			Update_data(count);
			
			
			String message = "���������, ����� ��� '";
			message += ans + "'";
			JOptionPane.showMessageDialog(null, message,"OutPut",JOptionPane.CANCEL_OPTION);
		}
	}
}
