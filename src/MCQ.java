
public class MCQ {
	  private String mcq;
	  private String option1;
	  private String option2;
	  private String option3;
	  private String option4;
	  public MCQ() {
		  
	  }
	public MCQ(String mcq, String option1, String option2, String option3, String option4) {
		super();
		this.mcq = mcq;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
	}
	public String getMcq() {
		return mcq;
	}
	public void setMcq(String mcq) {
		this.mcq = mcq;
	}
	public String getoption1() {
		return option1;
	}
	public void setoption1(String option1) {
		this.option1 = option1;
	}
	public String getoption2() {
		return option2;
	}
	public void setoption2(String option2) {
		this.option2 = option2;
	}
	public String getoption3() {
		return option3;
	}
	public void setoption3(String option3) {
		this.option3 = option3;
	}
	public String getoption4() {
		return option4;
	}
	public void setoption4(String option4) {
		this.option4 = option4;
	}
	@Override
	public String toString() {
		return "MCQ [mcq=" + mcq + ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4 + "]";
	}
	
	
}

