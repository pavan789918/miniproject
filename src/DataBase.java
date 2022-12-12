import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class DataBase extends Invalid_User_Name_Or_PasswordException{
	
	public DataBase(String name) {
		super(name);
		
	}

	//======================================
	static Connection connection = null;
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "root");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return connection;

	}

	 // data stored related to student
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static String name;
	private static String std_mail;
	private static int std_marks;
	private static String grades;
	static String query1 = "select*from Student_info";
	static String query2 = "insert into  Student_info(std_name,std_mail,std_marks,std_grade)values(?,?,?,?)";
	static String query3 = "delete from Student_info where std_id=?";
	static String query4 ="delete from Student_info where std_id";
	static String query5="TRUNCATE TABLE questions";
	
	
	
	
	//=============================================================
	// method for all student
	static public void get_All_Std_Result() {
		Connection con = getConnection();

		try {

			Statement stm = con.createStatement();
			ResultSet res = stm.executeQuery(query1);
			while (res.next()) {
				int id = res.getInt(1);
				name = res.getString(2);
				std_mail = res.getString(3);
				std_marks = res.getInt(4);
				grades = res.getString(5);
				// only admin can see
				System.out.print("stdId=" + id + " " + "std_name = " + name + "  " + " std_mail = " + std_mail + " "
						+ "Total marks scored =" + std_marks + " " + "Grade = " + grades + "\n");
				System.out.println("-----------------------");
			}
		} catch (SQLException e) {

		}

	}

	// method for only student to display result 
	static void result_student_Acess() {
		String stdname;
		try {

			Connection con = getConnection();
			Statement stm = con.createStatement();
			ResultSet res = stm.executeQuery(query1);
			System.out.println("=============="+"\n"+" Enter your name currectly as per hall ticket to access the details ");
			 stdname = br.readLine();
			System.out.println(" Enter your registered mail to acees the result ");

			// System.out.println(" Best of luck ");

			String mail = br.readLine();

			while (res.next()) {
				name = res.getString(2);
				std_mail = res.getString(3);
				std_marks = res.getInt(4);
				grades = res.getString(5);
				if (stdname.equalsIgnoreCase(name) && mail.equalsIgnoreCase(std_mail)) {

					System.out.println("Name = " + res.getString(2) + " registerd mail = " + res.getString(3)
							+ " total marks scored = " + res.getString(4) + " your grade is " + res.getString(5));
					
				    System.out.println("================================");
					MainClass.HomeScreen();
				
				} 
				/* else { System.out.println("enter valid name or email"); break; } */
					 

			}
		} catch (SQLException | IOException e) {

			e.printStackTrace();
		}
		
		

	}
	//=========================================================================
	// method to add students 
	public static void Add_Student_Data() {
		Connection con = getConnection();
		Scanner br =new Scanner(System.in);
		System.out.println(" Enter the data u want to insert");
		System.out.println(" enter name ");
		//String name = br.readLine();
		   String name= br.nextLine();
		   
		System.out.println(" enter mail ");
		//String mail = br.readLine();
		 String mail= br.nextLine();
		System.out.println(" enter enter marks ");
		// int marks = Integer.parseInt(br.readLine());
		int marks =br.nextInt();
		System.out.println(" enter grade  ");
		// String grade = br.readLine();
		String grade=br.nextLine();
		try {
			PreparedStatement prd = con.prepareStatement(query2);
			prd.setString(1, name);
			prd.setString(2, mail);
			prd.setInt     (3, marks);
			prd.setString(4, grade);
			prd.executeUpdate();
			System.out.println("Data added Successfully");//
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
public static void delete_Student_Record() {
	Connection con = getConnection();
	Scanner sc =new Scanner(System.in);
	System.out.println(" Enter the student id u want to delete");
	int id =sc.nextInt();
	try {
		PreparedStatement prd = con.prepareStatement(query3);
		prd.setInt(1, id);
		prd.executeUpdate();
		System.out.println("Record deleted succesfully ");
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
}
	
	//========================================================================
	static void updateInfo() {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		

		while (true) {
			System.out.println(" select one of this  " + "\n" + "---------------");
			System.out.println("1. SHOW ALL STUDENT RESULT");
			System.out.println("2. ADD NEW STUDENT");
			System.out.println("3. DELETE STUDENT RECORD ");
			System.out.println("4. Set UP NEW QUESTIONS ");
			System.out.println("5. Conduct exam ");
			System.out.println("6. Return home Screen  ");
			System.out.println("7. To exit prom portal ");
			int input=0;
			try {
				input = Integer.parseInt(br.readLine());
				 
				switch (input) {
				case 1:
					get_All_Std_Result();
					updateInfo();
					break;
				case 2:
					
					Add_Student_Data();
					updateInfo();
					break;
				case 3:
					delete_Student_Record();
					updateInfo();
					
				case 4:
			   Exam.questionsSetUp();
					Connection con = getConnection();
					try {
						Statement stm=con.createStatement();
						stm.execute(query5);
						Exam.questionsSetUp();
						updateInfo();
						
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					break;
				case 5:
					System.out.println(" Enter total students attempting the exams");
					int total_Std=Integer.parseInt(br.readLine());
					for(int i=0;i<total_Std;i++) {
						System.out.println(" enter your name ");
						String name=br.readLine();
						System.out.println(" enter mail");
						
						String mail=br.readLine();
						
						Exam.conductExam(name, mail);
					
					}
					MainClass.HomeScreen();
					
				}

			} catch (NumberFormatException e) {
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			if(input==6) {
				System.out.println("Return to home page "+"\n"+"=============");
				MainClass.HomeScreen();
			
		}   else if(input>=7) {
			   System.out.println("Exited from portal ");
			       break;
		}
			
		break;	
		
		}
		
	}
// ========================================================
	// call this method from main class for admin login 
	static void admindetails() {
		final String user="pavan";
		final int  password=962090;
		try {
			System.out.println("Enter your username ");
			String name =br.readLine();
			System.out.println("Enter your password ");
			int pass = Integer.parseInt(br.readLine());
			// System.out.println(pass+""+name);
			
			if(user.equals(name)&&password==pass) {
				
				 updateInfo();
				 
			}
			else {
		  System.out.println("\n"+" Invalid Id/password");
		         
			}
		} catch (Exception e) {
			Invalid_User_Name_Or_PasswordException obj= new Invalid_User_Name_Or_PasswordException("please enter valid user id or password");
		      obj.printStackTrace();
			admindetails();
			
		}
				
	} 
	
	public static void studentportal() {
		try {
			System.out.println("=============="+"\n"+"1.new user regisration "+"\n"+"2.Existing user"+"\n "+"3. Return to home screen");
			
			System.out.println("press options to perform operations");
			int input =Integer.parseInt(br.readLine());
		if(input==1) {
			System.out.println(" wecome to registation portal "+"\n"+"=====================");
			System.out.println(" please Enter your name  ");
			String name= br.readLine();
			System.out.println("please enter your mailId"+"\n"+"=======================");
			String mail= br.readLine();
			System.out.println("your rgistration was complited "+"\n"+"would you like to proceed with exam "+"\n"+"====="+"\n"+"Press 1 / 2"+"\n"+"1.yes "+"\n "+"2.NO");
			int input2 =Integer.parseInt(br.readLine());
			 if(input2==1) {
				 Exam.conductExam(name,mail);
			}else {
				MainClass.HomeScreen();
			}
		
		}
		else if(input==2) {
			System.out.println("welcome to result portal ");
			result_student_Acess();
			
			
		}
		else {
			return;
			
		}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
