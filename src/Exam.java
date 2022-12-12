
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;



public class Exam {
	static String Std_name;
	static String mail;
	static String grade;
	static int correctAns = 0;

	
	static Connection c = DataBase.getConnection();

	public static void conductExam(String name ,String std_mail) {
		Std_name= name;
		mail  =std_mail;
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome To exams Best of Luck");
		System.out.println("...............................");
		System.out.println(" Please enter Appropriate Ans ");
		//Std_name = sc.nextLine();
		//System.out.println(" Please enter your Mail ");
		// mail = sc.nextLine();

		String query = "SELECT * FROM questions ORDER BY RAND()";
		Statement st;
		try {
			st = c.createStatement();

			ResultSet set = st.executeQuery(query);
			while (set.next()) {
				String s = set.getString(2);
				String s1 = set.getString(3);
				String s2 = set.getString(4);
				String s3 = set.getString(5);
				String s4 = set.getString(6);
				int s5 = set.getInt(7);
				try {
					System.out.println(s + "\n" + s1 + "\n" + s2 + "\n" + s3 + "\n" + s4);
					System.out.println("-------------");
					System.out.println(" Enter your Ans ");
					int ans = sc.nextInt();
					if (ans == s5) {
						correctAns++;

					}
				} catch (InputMismatchException e) {
					e.printStackTrace();

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("==================");
		
		// method will get called if this method will get called or student attempting the exam
		
		studentDataInsertion();

	}

	// method for inserting the data in data base
	public static void studentDataInsertion() {
		String sql = "insert into Student_info(std_name,std_mail,std_marks,std_grade) values(?,?,?,?) ";
		try {
			PreparedStatement prd = c.prepareStatement(sql);
			prd.setString(1, Std_name);
			prd.setString(2, mail);
			prd.setInt(3, correctAns);
			if (correctAns >= 8) {
				grade = "A : FCD";
			} else if (correctAns >= 6) {
				grade = "B: pass " ;
			} else {
				grade = "C:fail";
			}
			prd.setString(4, grade);
			prd.execute();

		} catch (SQLException e) {
			UserAlredyExistException exp =new UserAlredyExistException("mail  alredy exist please try with diff mail id");
			   exp.printStackTrace();
			   DataBase.studentportal();
		}

		System.out.println("exmam got complited wait for result "+"\n"+"=============");
		
		System.out.println("your have scored "+correctAns+"/10"+"\n"+"your grade is ="+grade);
	}
	
	// logic for 
	static public void  questionsSetUp() {
		Map<MCQ, Integer> hmap = new HashMap<>();
		// how to create all question and ans
		// we already created constructor then pass que and ans to the constructor
		MCQ mcq1 = new MCQ("1.what is the extension of java code files ", "1. .txt", "2. .pdf", "3. .class",
				"4. .java");
		MCQ mcq2 = new MCQ("2.Which of the following option leads to the portability and security of Java",
				"1.Bytecode is executed by JVM", "2.The applet makes the Java code secure and portable",
				"3.Use of exception handling", "4.Dynamic binding between objects");
		/*
		 * MCQ mcq3= new MCQ(mcq, option1, option2, option3, option4); MCQ mcq4= new
		 * MCQ(mcq, option1, option2, option3, option4); MCQ mcq5= new MCQ(mcq, option1,
		 * option2, option3, option4); MCQ mcq6= new MCQ(mcq, option1, option2, option3,
		 * option4); MCQ mcq7= new MCQ(mcq, option1, option2, option3, option4); MCQ
		 * mcq8= new MCQ(mcq, option1, option2, option3, option4); MCQ mcq9= new
		 * MCQ(mcq, option1, option2, option3, option4); MCQ mcq10= new MCQ(mcq,
		 * option1, option2, option3, option4);
		 */
		 MCQ mcq3= new MCQ("A good way to debug JDBC-related problems is to enable?" , "JDBC tracing",  "Exception handling", "Both a and b", "Only b");
		 MCQ mcq4=new MCQ( "How many stages are used by Java programmers while using JDBC in their programs?", "3", "2", "5", "6"); 
             MCQ mcq5= new MCQ("Which data type is used to store files in the database table?" , "BLOB","CLOB", "File", "Both a and b");
             MCQ mcq6= new MCQ("JDBC API supports_____ and _____ architecture model for accessing the database." ,  "Two-tier", "Three-tier", "Both a and b","Only b"); 
             MCQ mcq7= new MCQ("Which of the following is not a valid statement in JDBC?" ,  "Statement",  "PreparedStatement", "QueryStatement", "CallableStatement"); 
             MCQ mcq8= new MCQ("Which of the following driver is the fastest one?" ,"JDBC-ODBC Bridge Driver", "Native API Partly Java Driver","Network Protocol Driver", "JDBC Net Pure Java Driver"); 
             MCQ mcq9= new MCQ("Which of the following method is static and synchronized in JDBC API?" , "getConnection()", "prepareCall()", " executeUpdate()", " executeQuery()");
             MCQ mcq10= new MCQ(" How many transaction isolation levels provide the JDBC through the Connection interface ?", "3", "4", "7", "3");
		               hmap.put(mcq1, 4);
		               hmap.put(mcq2, 1);
		               hmap.put(mcq3, 3);
		               hmap.put(mcq4, 3);
		               hmap.put(mcq5, 1);
		               hmap.put(mcq6, 3);
		               hmap.put(mcq7, 3);
		               hmap.put(mcq8, 4);
		               hmap.put(mcq9, 1);
		               hmap.put(mcq10, 2);

		for (Entry<MCQ, Integer> map : hmap.entrySet()) {
			
			// just storing the result 
			map.getKey().getMcq();
			map.getKey().getoption1();
			map.getKey().getoption3();
			map.getKey().getoption4();
			try {
							
				String sql = "insert into Questions(question,Opt1,Opt2,Opt3,Opt4,result) values(?,?,?,?,?,?)";
				PreparedStatement prd = c.prepareStatement(sql);
				prd.setString(1, map.getKey().getMcq());
				prd.setString(2, map.getKey().getoption1());
				prd.setString(3, map.getKey().getoption2());
				prd.setString(4, map.getKey().getoption3());
				prd.setString(5, map.getKey().getoption4());
				
				// in this result will  get  stored database as correct ans 
				prd.setInt(6, map.getValue());
				
				
				prd.execute();
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		System.out.println("Questions set up got completed  ");
}
	public static void main(String[] args) {
		/*
		 * conductExam("pavan", "pavan@gmail.com"); System.out.println(Std_name+ mail );
		 */
	}
}

