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
				ps.setDouble(1, salary);
				ps.setInt(2, empId);
				int rows=ps.executeUpdate();
				System.out.println(rows+" Employee Salary Updated");
		}
	}
	
	//Delete Employee
	static void deleteEmployee() throws SQLException{
		String sql="DELETE FROM EMPLOYEES WHERE EMP_ID=?";
		try(PreparedStatement ps= con.prepareStatement(sql)){
				System.out.println("Enter Employee ID:");
				int empId=sc.nextInt();
				ps.setInt(1, empId);
				int rows=ps.executeUpdate();
				
				System.out.println(rows+" Employee Record Deleted");
		}
	}
	
	//filtering by minimum salary
	static void filterBySalary() throws SQLException{
		String sql="SELECT * FROM EMPLOYEES WHERE SALARY > ?";
		try(PreparedStatement ps=con.prepareStatement(sql)){
			System.out.println("Enter Minimum Salary");
			double salary=sc.nextDouble();
			
			ps.setDouble(1, salary);
			
			try(ResultSet rs=ps.executeQuery()){
				while(rs.next()) {
					System.out.println(
							rs.getInt("emp_id")+" "+
							rs.getString("name")+" "+
							rs.getBigDecimal("salary")
							);
				}
			}
		}
	}
	
	public static void main(String[] args) {	
		try {
		con = DriverManager.getConnection(url,user,pass);
		System.out.println("DB CONNECTED SUCCESSFULLY");
		int choice;
		
		do {
			System.out.println("\n------EMPLOYEE MANAGEMENT-------");
			System.out.println("1.INSERT EMPLOYEE");
			System.out.println("2.DISPLAY ALL EMPLOYEES");
			System.out.println("3.SEARCH EMPLOYEES BY ID");
			System.out.println("4.UPDATE SALARY");
			System.out.println("5.DELETE EMPLOYEE");
			System.out.println("6.FILTER BY SALARY");
			System.out.println("0.Exit");
			System.out.println("Enter Choice:");
			choice=sc.nextInt();
			
				switch(choice) {
				case 1:
						insertEmployee();break;
				case 2:
						displayEmployees();break;
				case 3:
						searchEmployee();break;
				case 4:
						updateSalary();break;
				case 5:
						deleteEmployee();break;
				case 6:
						filterBySalary();break;
				case 0:
						System.out.println("Exiting");break;
				default:
						System.out.println("Invalid Choice");
				}

		}while(choice!=0);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(con!=null && !con.isClosed()) {
					con.close();
				}
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		sc.close();
		}
	}
