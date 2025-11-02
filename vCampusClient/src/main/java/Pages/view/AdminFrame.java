package Pages.view;
//**@author 唐可成



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import Pages.view.manager.AddClassFrame;
import Pages.view.manager.AllStudentFrame;
import Pages.view.manager.ApproveBookFrame;
import Pages.view.manager.ApproveStudentFrame;
import Pages.view.manager.LessonManage;
import Pages.view.manager.ManageBookFrame;
import Pages.view.manager.ManageShopeFrame;
import Pages.view.manager.ManageUserFrame;
import Pages.view.shared.AuthorFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class AdminFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDesktopPane desktopPane;
	private static ChangePasswordFrame changepasswordframe=null;//static是为了与其他界面互动
	
	private static AuthorFrame authorframe=null;
	private static ManageUserFrame manageuserframe=null;

	private static AllStudentFrame allstudentframe=null;
	private static ApproveStudentFrame approvestudentframe=null;
	private static LessonManage managelessonframe=null;
	private static ManageBookFrame managebookframe=null;
	private static ApproveBookFrame approvebookframe=null;
	private static ManageShopeFrame manageshopeframe=null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame();
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
	public AdminFrame() {
		//setIconImage(Toolkit.getDefaultToolkit().getImage(AdminFrame.class.getResource("/Pages/image/avatar.png")));
		String Interface="尊敬的管理员欢迎您";//传入“尊敬的+身份+欢迎您”
		setTitle(Interface);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1008, 640);
		setLocationRelativeTo(null);//画面居中要写在设置长宽之后
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("系统管理");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("修改密码");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				RevisePassword(ae);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		/*
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("注销账户");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				LeaveForever(ae);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		*/
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("退出");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				LoginOut(ae);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_3 = new JMenu("用户管理");
		menuBar.add(mnNewMenu_3);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("管理用户");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ManageUser(ae);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_5);
		
		JMenu mnNewMenu_5 = new JMenu("学籍管理");
		menuBar.add(mnNewMenu_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("全校学生信息");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				AllStudent(ae);
			}
		});
		mnNewMenu_5.add(mntmNewMenuItem_6);
		/*
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("学生休学退学申请");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ApproveStudent(ae);
			}
		});
		mnNewMenu_5.add(mntmNewMenuItem_7);
		*/
		JMenu mnNewMenu_4 = new JMenu("课程管理");
		menuBar.add(mnNewMenu_4);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("课程信息管理");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ManageLessons(ae);
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem_8);
		
		JMenu mnNewMenu_6 = new JMenu("图书管理");
		menuBar.add(mnNewMenu_6);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("图书信息管理");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ManageBook(ae);
			}
		});
		mnNewMenu_6.add(mntmNewMenuItem_9);
		/*
		JMenuItem mntmNewMenuItem_10 = new JMenuItem("借阅申请审批");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ApproveBook(ae);
			}
		});
		mnNewMenu_6.add(mntmNewMenuItem_10);
		*/
		JMenu mnNewMenu_7 = new JMenu("商店管理");
		menuBar.add(mnNewMenu_7);
		
		JMenuItem mntmNewMenuItem_11 = new JMenuItem("商品信息录入与修改");
		mntmNewMenuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ManageShope(ae);
			}
		});
		mnNewMenu_7.add(mntmNewMenuItem_11);
		
		JMenu mnNewMenu_2 = new JMenu("软件开发人员");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("分工");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Author(ae);
				
			}
			
		});
		mnNewMenu_2.add(mntmNewMenuItem_4);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 994, 580);
		contentPane.add(desktopPane);
		
		JLabel lblNewLabel = new JLabel("");
		//lblNewLabel.setIcon(new ImageIcon(AdminFrame.class.getResource("/Pages/image/sipailou.jpg")));
		lblNewLabel.setBounds(0, 0, 994, 558);
		desktopPane.add(lblNewLabel);
	
		
	}
	protected void ManageShope(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (	manageshopeframe==null)
		{  manageshopeframe=new ManageShopeFrame () ;
	
		desktopPane.add( manageshopeframe);
		}
									
		manageshopeframe.setVisible(true);
		manageshopeframe.setBounds(100, 100, 728, 468);
		
	}

	protected void ApproveBook(ActionEvent ae) {
		// TODO Auto-generated method stub
		 

			if (	approvebookframe==null)
			{  approvebookframe=new ApproveBookFrame () ;
		
			desktopPane.add( approvebookframe);
			}
										
		 approvebookframe.setVisible(true);
		 approvebookframe.setBounds(100, 100, 728, 468);
	}

	protected void ManageBook(ActionEvent ae) {
		// TODO Auto-generated method stub
	
		if (	 managebookframe==null)
		{  managebookframe=new ManageBookFrame() ;
	
		desktopPane.add( managebookframe);
		}
									
		 managebookframe.setVisible(true);
		 managebookframe.setBounds(100, 100, 728, 468);
	}

	protected void ManageLessons(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if (	 managelessonframe==null)
		{ managelessonframe=new LessonManage() ;
	
		desktopPane.add( managelessonframe);
		}
		
		 managelessonframe.setVisible(true);
		 managelessonframe.setBounds(100, 100, 728, 468);
		
	}

	protected void ApproveStudent(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if (	approvestudentframe==null)
		{	approvestudentframe=new ApproveStudentFrame() ;
	
		desktopPane.add(	approvestudentframe);
		}
		
		approvestudentframe.setVisible(true);
		approvestudentframe.setBounds(100, 100, 728, 468);
	}

	protected void AllStudent(ActionEvent ae) {
		// TODO Auto-generated method stub
	

		if (	allstudentframe==null)
		{	allstudentframe=new 	AllStudentFrame();
	
		desktopPane.add(	allstudentframe);
		}
		
		allstudentframe.setVisible(true);
		allstudentframe.setBounds(100, 100, 728, 468);
	}

	protected void ManageUser(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if (manageuserframe==null)
		{manageuserframe=new ManageUserFrame();
	
		desktopPane.add(manageuserframe);
		}
		
		manageuserframe.setVisible(true);
		manageuserframe.setBounds(100, 100, 728, 468);
	}

	protected void Author(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (authorframe==null)
		{authorframe=new AuthorFrame();
	
		desktopPane.add(authorframe);
		}
		
		authorframe.setVisible(true);
		authorframe.setBounds(30, 30, 864, 542);
		
	}

	protected void LeaveForever(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if(JOptionPane.showConfirmDialog(this, "是否确认注销","正在注销...",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
			System.exit(0);//用户选择注销，后台进行相关逻辑写在if里面
			}
		
	}

	protected void LoginOut(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(JOptionPane.showConfirmDialog(this, "是否确认退出?","正在退出...",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
			LoginFrame loginframe=new LoginFrame();//用户确认退出
				//loginframe.setVisible(true);
				this.dispose();}//关闭界面}
	}

//修改密码响应函数
	protected void RevisePassword(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(changepasswordframe==null) {
			changepasswordframe=new ChangePasswordFrame();
			desktopPane.add(changepasswordframe);
		}
		changepasswordframe.setBounds(100, 100, 397, 241);
		 changepasswordframe.setVisible(true);//配合重写密码修改页面x按钮效果，使界面只出现一个修改密码界面
		
		
	}
}
