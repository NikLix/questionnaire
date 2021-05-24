package main.sql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;



public class Connection_PostgresSQL {
	// ������ ��� ������������ � ��
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
			+ "('�� ���������� �����','�� ���������� �����','���������� �����','�� ���������� �����',3,'������')";
			
    

	private static Connection connect() {
    	Connection connection = null;
    	try {
    		// ��������� ������� �������� JDBC
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL Driver �� ������");
			e.printStackTrace();
		}
    	try {
    		// ����������� � ��
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			System.out.println("�� �������� ����������� � DataBase PostgreSQL");
			e.printStackTrace();
		}
    	
    	return connection;
    }
	
	public  ResultSet select(String sql) {
	    try {
	    	// ����������� � ��
			Connection conn = connect();
			Statement statement = conn.createStatement();
			// ��������� ���� �� ������� ���� ��� �� ������� � � ��������� � �� ������
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "list_questions", null);
			if (!tables.next()){
				statement = conn.createStatement(); 
				statement.execute(createTableSQL); 
				statement.executeUpdate(insertTableSQL); 
			}
			// ��������� ������ �� ������� ������
			ResultSet rs = statement.executeQuery(selectSQL);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
	    }
		
		
	}
    
    
}

