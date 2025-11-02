package Pages.view.teacher;
//**   @author 唐可成

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Pages.view.ChangePasswordFrame;
import Pages.view.LoginFrame;
import Pages.view.manager.AddClassFrame;
import Pages.view.shared.AuthorFrame;
import Pages.view.student.BookList;
import Pages.view.student.BorrowedList;
import Pages.view.student.BorrowingList;
import Pages.view.student.ShoppingList;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class TeacherFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDesktopPane desktopPane;
	private static ChangePasswordFrame changepasswordframe=null;//static是为了与其他界面互动

	private static AuthorFrame authorframe=null;
	private static  ShoppingList shoppinglist=null;
	private static  BookList booklist=null;
	private static  BorrowedList borrowedlist=null;
	private static  BorrowingList borrowinglist=null;
	private static MyStudentFrame mystudentframe=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherFrame frame = new TeacherFrame();
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
	public TeacherFrame() {
		//setIconImage(Toolkit.getDefaultToolkit().getImage(TeacherFrame.class.getResource("/Pages/image/avatar.png")));
		String Interface="尊敬的老师欢迎您";//后台传入“尊敬的+身份+欢迎您”
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
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("注销账户");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				LeaveForever(ae);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("退出");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				LoginOut(ae);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);
		/*
		JMenu mnNewMenu_1 = new JMenu("课程教学管理");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("学生名单");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				CheckMyStudent(ae);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		*/
		JMenu mnNewMenu_5 = new JMenu("商店");
		menuBar.add(mnNewMenu_5);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("购物");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Purchase(ae);
			}
		});
		mnNewMenu_5.add(mntmNewMenuItem_7);
		
		JMenu mnNewMenu_5_1 = new JMenu("图书馆");
		menuBar.add(mnNewMenu_5_1);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("借阅图书");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				BorrowNewBook(ae);
			}
		});
		mnNewMenu_5_1.add(mntmNewMenuItem_9);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("还书");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ReturnBook(ae);
			}
		});
		mnNewMenu_5_1.add(mntmNewMenuItem_8);
		
		JMenuItem mntmNewMenuItem_10 = new JMenuItem("我的借阅申请");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ToBeApproved(ae);
			}
		});
		mnNewMenu_5_1.add(mntmNewMenuItem_10);
		
		JMenu mnNewMenu_2 = new JMenu("软件开发人员");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("小组分工     ");
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
		desktopPane.setBounds(0, 0, 994, 590);
		contentPane.add(desktopPane);
		
		JLabel lblNewLabel = new JLabel("");
		//lblNewLabel.setIcon(new ImageIcon(TeacherFrame.class.getResource("/Pages/image/background.jpg")));
		lblNewLabel.setBounds(0, 0, 994, 564);
		desktopPane.add(lblNewLabel);
	}
	protected void CheckMyStudent(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (mystudentframe==null)
		{mystudentframe=new MyStudentFrame();
	
		desktopPane.add(mystudentframe);
		}
		
		mystudentframe.setVisible(true);
		mystudentframe.setBounds(100, 100, 728, 468);
	}

	protected void ToBeApproved(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (borrowinglist==null)
		{borrowinglist=new BorrowingList();
	
		desktopPane.add(borrowinglist);
		}
		
		borrowinglist.setVisible(true);
		borrowinglist.setBounds(100, 100, 728, 468);
	}

	protected void ReturnBook(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (borrowedlist==null)
		{borrowedlist=new BorrowedList();
	
		desktopPane.add(borrowedlist);
		}
		
		borrowedlist.setVisible(true);
		borrowedlist.setBounds(100, 100, 728, 468);
	}

	protected void BorrowNewBook(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (booklist==null)
		{booklist=new BookList();
	
		desktopPane.add(booklist);
		}
		
		booklist.setVisible(true);
		booklist.setBounds(100, 100, 728, 468);
		
	}

	protected void Purchase(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (shoppinglist==null)
		{shoppinglist=new ShoppingList();
	
		desktopPane.add(shoppinglist);
		}
		
		shoppinglist.setVisible(true);
		shoppinglist.setBounds(100, 100, 728, 468);
		
		
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
			LoginFrame loginframe=new LoginFrame();//用户确认退出
			loginframe.setVisible(true);
			this.dispose();
			}
		
	}

	protected void LoginOut(ActionEvent ae) {
		// TODO Auto-generated method stub
				if(JOptionPane.showConfirmDialog(this, "是否确认退出?","正在退出...",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					LoginFrame loginframe=new LoginFrame();//用户确认退出
						loginframe.setVisible(true);
						this.dispose();}//关闭界面}
		
	}

//修改密码响应函数
	protected void RevisePassword(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(changepasswordframe==null) {
			changepasswordframe=new ChangePasswordFrame();
			desktopPane.add(changepasswordframe);
		}
		
		 changepasswordframe.setVisible(true);//配合重写密码修改页面x按钮效果，使界面只出现一个修改密码界面
		 changepasswordframe.setBounds(100, 100, 397, 241);
		
		
	}
}
