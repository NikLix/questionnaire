package main.sql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;



public class Connection_PostgresSQL {
	// Данные для подключенния к БД
	static final String DB_URL = "jdbc:postgresql://localhost:5432/db";
    static final String USER = "postgres";
    static final String PASS = "postgres";
    static final String selectSQL = "";
    static String createTableSQL = "CREATE TABLE IF NOT EXISTS list_questions"
			+ "    (id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 )"
			+ "    ,answer_one text"
			+ "    ,answer_two text"
			+ "    ,answer_three text"
			+ "    ,answer_four text"
			+ "    ,true_answer integer"
			+ "    ,quest text"
			+ "    ,CONSTRAINT list_questions_pkey PRIMARY KEY (id)"
			+ ") ";
    static String insertTableSQL = "INSERT INTO list_questions"
			+ "(answer_one, answer_two, answer_three, answer_four,true_answer,quest) " 
    		+ "VALUES"
			+ "('Не правильный ответ','Не правильный ответ','Правильный ответ','Не правильный ответ',3,'Вопрос')";
			
    

	private static Connection connect() {
    	Connection connection = null;
    	try {
    		// Проверяем наличие Драйвера JDBC
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL Driver не найден");
			e.printStackTrace();
		}
    	try {
    		// Соединяемся с БД
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			System.out.println("Не возможно соединиться с DataBase PostgreSQL");
			e.printStackTrace();
		}
    	
    	return connection;
    }
	
	public  ResultSet select(String sql) {
	    try {
	    	// Соединяемся с БД
			Connection conn = connect();
			Statement statement = conn.createStatement();
			// Проверяем есть ли Таблица если нет то создаем её и добавляем в неё данные
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "list_questions", null);
			if (!tables.next()){
				statement = conn.createStatement(); 
				statement.execute(createTableSQL); 
				statement.executeUpdate(insertTableSQL); 
			}
			// выполняем запрос на выборку данных
			ResultSet rs = statement.executeQuery(selectSQL);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
	    }
		
		
	}
    
    
}

