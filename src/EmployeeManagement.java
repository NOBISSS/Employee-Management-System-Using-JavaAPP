import java.sql.*;
import java.util.Scanner;
public class EmployeeManagement {
	static final String url="jdbc:postgresql://localhost:5432/postgres";
	static final String user="postgres";
	static final String pass="root";
	static Connection con;
	static Scanner sc=new Scanner(System.in);
	
	//INSERT EMPLOYEE 
	static void insertEmployee() throws SQLException{
			String sql="INSERT INTO Employees VALUES(?,?,?,?)";
			try(PreparedStatement ps=con.prepareStatement(sql)){
				System.out.println("Enter ID:");
				int id=sc.nextInt();
				sc.nextLine();
				
				System.out.println("Enter Name:");
				String name=sc.nextLine();
				
				System.out.println("Enter Department:");
				String dept=sc.nextLine();
				
				System.out.println("Enter Salary:");
				double salary=sc.nextDouble();
				
				ps.setInt(1,id);
				ps.setString(2,name);
				ps.setString(3, dept);
				ps.setDouble(4, salary);
				
				int rows=ps.executeUpdate();
				System.out.println(rows+" employee inserted");
			}
	}
	
	public static void main(String[] args) {	
		try {
		con = DriverManager.getConnection(url,user,pass);
		System.out.println("DB CONNECTED SUCCESSFULLY");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
