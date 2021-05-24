package main;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import main.sql.Connection_PostgresSQL;

@SuppressWarnings("serial")
public class Interface extends JFrame{
	
	// Создаем переменные и элементы  
	Integer true_answer = 1;
	JButton button = new JButton("Ответить");
	
	JLabel  label = new JLabel("");
	JLabel  label2 = new JLabel("");
	JRadioButton answer1  = new JRadioButton("");
	JRadioButton answer2  = new JRadioButton("");
	JRadioButton answer3  = new JRadioButton("");
	JRadioButton answer4  = new JRadioButton("");	
		
	
	public Interface () {
		super("Вопросник");
		Update_data();
		
		// Задаем размеры окна и отображение форм 1 столбец 6 строк
		this.setBounds(300,300,450,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = this.getContentPane();
		container.setLayout(new GridLayout(6,1));
		
	
		container.add(label);
		
		// Добавляем RadioButton в одну группу
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
		
		// Создаем событие которое будет выполняться при нажатии на кнопку
		button.addActionListener(new ButtonEvenListener ());
		container.add(button);
		
	}
	
	/* Метод выполняет Select запрос и
	 * добавляет полученные данные к элементам  
	 *  */
	public void Update_data() {	
		Connection_PostgresSQL conn =  new Connection_PostgresSQL();
		ResultSet rs = conn.select(""
				+ "SELECT quest,answer_one, answer_two, answer_three, answer_four, true_answer "
				+ "FROM list_questions LIMIT 1"
				+ "");
		try {
			// Т.к. у нас всё го одна строка не делаем цикл
			
			rs.next();
			String quest = rs.getString("quest");
			label =  new JLabel(quest);
			answer1 = new JRadioButton(rs.getString("answer_one"));
			answer2 = new JRadioButton(rs.getString("answer_two"));
			answer3 = new JRadioButton(rs.getString("answer_three"));
			answer4 = new JRadioButton(rs.getString("answer_four"));
			this.true_answer = rs.getInt("true_answer");
			
		} catch (SQLException e2) {
			System.out.println("При выполнении запроса возникли ошибки");
			e2.printStackTrace();
		}
	}

	class ButtonEvenListener implements ActionListener  {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// Проверяем как ответил пользователь с правильным ответом
			// Сообщаем ему об этом
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
	        	String message = "Не правильный ответ";
				JOptionPane.showMessageDialog(null, message,"OutPut",JOptionPane.CANCEL_OPTION);
	        }
			
		}
		// Метод если пользователь ответил верно 
		public void TrueAnswer(String ans) {
			String message = "Правильно, ответ был '";
			message += ans + "'";
			JOptionPane.showMessageDialog(null, message,"OutPut",JOptionPane.CANCEL_OPTION);
		}
	}
}
