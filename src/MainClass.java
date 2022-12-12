import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainClass {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void HomeScreen() {
		System.out.println("welcome to the main portal " + "\n" + "-------");
		System.out.println("1.Adminstrator login" + "\n" + "2. Student Portal" + "\n" + "3.exit from portal" + "\n"
				+ "Press 1 or 2 or 3 to continue");
		while (true) {

			int input = 0;
			try {
				input = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {

			} catch (IOException e) {

				e.printStackTrace();
			}
			if (input == 1) {
				System.out.println("\n" + "--------" + " welcome to Administration login ------- ");
				DataBase.admindetails();
			} else if (input == 2) {
				System.out.println(" welcome to student portal " + "\n" + "===========");
				 
				DataBase.studentportal();
				HomeScreen();
				break;
				
			} else if (input >=3) {
				
			System.out.println(" your are exited " + "\n" + "=======" + "\n" + "Thank you visit again ");
				break;
				
			} else {
				System.out.println(" ====please enter valid input===== ");

			}
			break;
		}

	}
	// =============================================
	// main method

	public static void main(String[] args) {
		HomeScreen();
	}

}
