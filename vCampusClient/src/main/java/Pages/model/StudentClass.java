package Pages.model;

public class StudentClass {
	private String id;
	private String grade;
	private String name;
	private String college;
	private String major;
	private String info;
	public StudentClass() {}
	public StudentClass(String id, String grade, String name, String college, String major, String info) {
		super();
		this.id = id;
		this.grade = grade;
		this.name = name;
		this.college = college;
		this.major = major;
		this.info = info;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

}
