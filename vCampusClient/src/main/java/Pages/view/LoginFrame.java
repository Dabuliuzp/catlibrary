package Pages.view;
//**@author 唐可成
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Pages.MainApp;
import Pages.view.student.StudentFrame;
import Pages.view.teacher.TeacherFrame;

import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel LoginPane;
	private JTextField username;
	private JPasswordField userpassword;
	private RegisterFrame registerframe;
	private JDesktopPane desktopPane;

	ObjectInputStream in = MainApp.getIn();
	ObjectOutputStream out = MainApp.getOut();
   

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		//setIconImage(Toolkit.getDefaultToolkit().getImage(LoginFrame.class.getResource("src/main/java/Pages/image/avatar.png")));
		setBackground(new Color(192, 192, 192));
		setTitle("虚拟校园系统");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 367);
		LoginPane = new JPanel();
		LoginPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(LoginPane);
		LoginPane.setLayout(null);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 528, 393);
		LoginPane.add(desktopPane);
		
		JLabel lblNewLabel = new JLabel("用户名：");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(105, 92, 107, 37);
		desktopPane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("仿宋", Font.PLAIN, 22));
		
		JLabel lblNewLabel_1 = new JLabel("密码：");
		lblNewLabel_1.setBounds(105, 160, 95, 37);
		desktopPane.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 22));
		
		username = new JTextField();
		username.setBackground(new Color(0, 0, 0));
		username.setBounds(222, 97, 167, 31);
		desktopPane.add(username);
		username.setColumns(10);
		username.setOpaque(false);//设置透明
		//username.setBorder (null);去除边框
		userpassword = new JPasswordField();
		userpassword.setBackground(new Color(255, 255, 255));
		userpassword.setBounds(222, 165, 167, 31);
		userpassword.setOpaque(false);
		desktopPane.add(userpassword);
		
		JButton btnNewButton = new JButton("注册");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setBounds(105, 226, 95, 31);
		//btnNewButton.setBorder (null);//去除边框
		desktopPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Register(ae);
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 22));
		btnNewButton.setFocusable(false);
		btnNewButton.setOpaque(false);//设置透明
		
		JButton btnNewButton_1 = new JButton("登录");
		btnNewButton_1.setBackground(new Color(136,143,150));
		btnNewButton_1.setBounds(290, 226, 105, 31);
		//btnNewButton_1.setBorder (null);//去除边框
		btnNewButton_1.setOpaque(false);//设置透明
		desktopPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				confirmButton(ae);
			}

			
		});
		btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 22));
		btnNewButton_1.setFocusable(false);//去除按钮虚线
		
		JLabel label = new JLabel("");
		label.setBackground(new Color(0, 0, 0));
		label.setFont(new Font("宋体", Font.BOLD, 12));
		//label.setIcon(new ImageIcon(LoginFrame.class.getResource("/main/java/Pages/image/shared.jpg")));
		label.setBounds(0, 0, 544, 347);
		desktopPane.add(label);
	    setLocationRelativeTo(null);//画面居中设置
		 
	}
	protected void Register(ActionEvent ae) {
	
		// TODO Auto-generated method stub
		if(registerframe==null) {
			registerframe=new RegisterFrame();
			desktopPane .add(registerframe);
		}
		registerframe.setBounds(100, 100, 374, 201);
		registerframe.setVisible(true);//配合重写密码修改页面x按钮效果，使界面只出现一个修改密码界面
		
		
	
		
		
	}

	//监听器方法重写
	protected void confirmButton(ActionEvent ae) {
		String name=this.username.getText();//获取输入框用户名
				
		String password=this.userpassword.getText();//密码框.密码

		try {
			out.writeObject("1");
			out.writeObject("Login");
			out.writeObject(name);
			out.writeObject(password);
			out.flush();

			Integer LoginComfirm=(Integer)in.readObject();//登录后界面显示逻辑。后台传入。查无此人0；密码错误1，管理员2，老师3，学生4
			if(LoginComfirm==0) {
				JOptionPane.showMessageDialog(this,"用户不存在");
			}
			else if(LoginComfirm==1) {
				JOptionPane.showMessageDialog(this,"密码错误");
			}
			else if(LoginComfirm==2) {
				MainApp.setCurrentUserId((Long)in.readObject());
				AdminFrame adminframe=new AdminFrame();
				//跳转管理员界面
				adminframe.setVisible(true);
				this.dispose();//关闭登录界面
				int IdentityChange=0;
				if(IdentityChange==1) {
					JOptionPane.showMessageDialog(this,"您的账户已被删除，即将退出系统");
					LoginFrame loginframe=new LoginFrame();
					loginframe.setVisible(true);
					adminframe.dispose();//关闭界面}
				}
				else if(IdentityChange==2) {
					JOptionPane.showMessageDialog(this,"您的权限已变更，请重新登录");
					LoginFrame loginframe=new LoginFrame();
					loginframe.setVisible(true);
					adminframe.dispose();
				}
			}
			else if(LoginComfirm==3) {
				MainApp.setCurrentUserId((Long)in.readObject());
				this.dispose();//关闭登录界面
				TeacherFrame teacherframe=new TeacherFrame();
				//跳转教师界面
				teacherframe.setVisible(true);

				int IdentityChange=0;//检测权限变更，删除1，权限变动2
				if(IdentityChange==1) {
					JOptionPane.showMessageDialog(this,"您的账户已被删除，即将退出系统");
					LoginFrame loginframe=new LoginFrame();
					loginframe.setVisible(true);
					teacherframe.dispose();//关闭界面}
				}
				else if(IdentityChange==2) {
					JOptionPane.showMessageDialog(this,"您的权限已变更，请重新登录");
					LoginFrame loginframe=new LoginFrame();
					loginframe.setVisible(true);
					teacherframe.dispose();
				}
			}
			else if(LoginComfirm==4) {
				Long Id=(Long)in.readObject();
				MainApp.setCurrentUserId(Id);
				System.out.println("StudentId:"+Id);
				StudentFrame studentframe=new StudentFrame();
				//跳转学生界面
				studentframe.setVisible(true);
				this.dispose();//关闭登录界面
				int IdentityChange=0;
				if(IdentityChange==1) {
					JOptionPane.showMessageDialog(this,"您的账户已被删除，即将退出系统");
					LoginFrame loginframe=new LoginFrame();
					loginframe.setVisible(true);
					studentframe.dispose();//关闭界面}
				}
				else if(IdentityChange==2) {
					JOptionPane.showMessageDialog(this,"您的权限已变更，请重新登录");
					LoginFrame loginframe=new LoginFrame();
					loginframe.setVisible(true);
					studentframe.dispose();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}
}
