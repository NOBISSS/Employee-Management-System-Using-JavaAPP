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
	
	//DISPLAY ALL THE EMPLOYEE
	static void displayEmployees() throws SQLException{
		String sql="SELECT * FROM EMPLOYEES ORDER BY EMP_ID";
		try(Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql)
				){
			while(rs.next()) {
				System.out.println( 
						rs.getInt("emp_id") + " | " +
						rs.getString("name") + " | " +
						rs.getString("department") + " | " +
						rs.getBigDecimal("salary") );
			}
		}
	}
	
	//Search Employee
	static void searchEmployee() throws SQLException{
		String sql="SELECT * FROM EMPLOYEES WHERE EMP_ID=?";
		try(PreparedStatement ps= con.prepareStatement(sql)){
				System.out.println("Enter Employee ID:");
				int empId=sc.nextInt();
				ps.setInt(1, empId);
				
				try(ResultSet rs=ps.executeQuery()){
					if(rs.next()) {
					System.out.println(
						rs.getString("name") + " " +
					    rs.getString("department") + " " +
						rs.getBigDecimal("salary") 
					 );
					}else {
						System.out.println("Employee Not Found.");
					}
				}
		}
	}
	
	//update salary
	static void updateSalary() throws SQLException{
		String sql="UPDATE EMPLOYEES SET SALARY=? WHERE EMP_ID=?";
		try(PreparedStatement ps= con.prepareStatement(sql)){
				System.out.println("Enter Employee ID:");
				int empId=sc.nextInt();
				
				System.out.println("Enter New Salary");
				double salary=sc.nextDouble();
				
				ps.setInt(1, empId);
				ps.setDouble(2, salary);
				
				int rows=ps.executeUpdate();
				
				System.out.println(rows+" Employee Salary Updated");
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
