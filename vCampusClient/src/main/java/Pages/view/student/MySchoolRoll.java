package Pages.view.student;
//**   @author 唐可成

import Pages.MainApp;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
import javax.swing.ImageIcon;

public class MySchoolRoll extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField TextFile;

	ObjectInputStream in = MainApp.getIn();
	ObjectOutputStream out = MainApp.getOut();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MySchoolRoll frame = new MySchoolRoll();
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
	public MySchoolRoll() {
			setTitle("我的学籍信息");
			setBounds(100, 100, 358, 282);
			setClosable(true);
			setBorder (null);
			setIconifiable(true);//最小化与关闭
			getContentPane().setLayout(null);

			requestAllClass();

			JLabel lblNewLabel_2_3_3_3_3 = new JLabel("充值金额：");
			lblNewLabel_2_3_3_3_3.setFont(new Font("宋体", Font.PLAIN, 13));
			lblNewLabel_2_3_3_3_3.setBounds(65, 179, 122, 35);
			getContentPane().add(lblNewLabel_2_3_3_3_3);

			TextFile = new JTextField();
			TextFile.setBounds(152, 179, 158, 35);
			getContentPane().add(TextFile);
			TextFile.setColumns(10);

			JButton btnNewButton = new JButton("充值");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					GetBalance(ae);
				}
			});
			btnNewButton.setBounds(103, 224, 87, 23);

			getContentPane().add(btnNewButton);
			btnNewButton.setFocusable(false);//去除按钮虚线
			//btnNewButton.setContentAreaFilled(false) ;//设置透明
			btnNewButton.setBorder (null);//去除边框

			JLabel lblNewLabel_1 = new JLabel("");
			//lblNewLabel_1.setIcon(new ImageIcon(MySchoolRoll.class.getResource("/Pages/image/shared.jpg")));
			lblNewLabel_1.setBounds(0, 0, 358, 255);
			getContentPane().add(lblNewLabel_1);
	}

	protected void GetBalance(ActionEvent ae) {
		try{
			String money = this.TextFile.getText();
			if(JOptionPane.showConfirmDialog(this, "是否确认充值","正在充值...",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				out.writeObject("11");
				out.writeObject("GetBalance");
				out.writeObject(MainApp.getCurrentUserId());
				out.writeObject(money);
				out.flush();
				JOptionPane.showMessageDialog(this, "充值成功");
				ResetButton(ae);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}

	}

	protected void ResetButton(ActionEvent ae) {
		// TODO Auto-generated method stub
		this.TextFile.setText("");
		requestAllClass();
		//this.setVisible(false);
	}

	public void requestAllClass() {
		try{
			out.writeObject("11");
			out.writeObject("GetMySchool");
			out.writeObject(MainApp.getCurrentUserId());
			out.flush();

			String name = (String) in.readObject();
			String studentid = (String) in.readObject();
			String college = (String) in.readObject();
			Double balance = (Double) in.readObject();

			JLabel lblNewLabel = new JLabel("学籍状态：");
			lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 12));
			lblNewLabel.setBounds(65, 22, 77, 23);
			getContentPane().add(lblNewLabel);

			JLabel lblNewLabel_2 = new JLabel("学号：");
			lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 13));
			lblNewLabel_2.setBounds(65, 81, 146, 35);
			getContentPane().add(lblNewLabel_2);

			JLabel StudentID = new JLabel(name);
			StudentID.setFont(new Font("宋体", Font.PLAIN, 13));
			StudentID.setBounds(152, 56, 120, 26);
			getContentPane().add(StudentID);

			JLabel StudenName = new JLabel(college);
			StudenName.setFont(new Font("宋体", Font.PLAIN, 13));
			StudenName.setBounds(152, 81, 146, 35);
			getContentPane().add(StudenName);

			JLabel lblNewLabel_2_3_3 = new JLabel("姓名：");
			lblNewLabel_2_3_3.setFont(new Font("宋体", Font.PLAIN, 13));
			lblNewLabel_2_3_3.setBounds(65, 55, 146, 28);
			getContentPane().add(lblNewLabel_2_3_3);

			JLabel lblNewLabel_2_3_3_1 = new JLabel("学院：");
			lblNewLabel_2_3_3_1.setFont(new Font("宋体", Font.PLAIN, 13));
			lblNewLabel_2_3_3_1.setBounds(65, 112, 146, 35);
			getContentPane().add(lblNewLabel_2_3_3_1);

			JLabel Grade = new JLabel(studentid);
			Grade.setFont(new Font("宋体", Font.PLAIN, 13));
			Grade.setBounds(152, 112, 157, 35);
			getContentPane().add(Grade);

			JLabel lblNewLabel_2_3_3_3 = new JLabel("余额：");
			lblNewLabel_2_3_3_3.setFont(new Font("宋体", Font.PLAIN, 13));
			lblNewLabel_2_3_3_3.setBounds(65, 146, 350, 35);
			getContentPane().add(lblNewLabel_2_3_3_3);

			JLabel Major = new JLabel(Double.toString(balance));
			Major.setFont(new Font("宋体", Font.PLAIN, 13));
			Major.setBounds(152, 148, 163, 35);
			getContentPane().add(Major);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}
	//点×后关闭界面
			public void doDefaultCloseAction() {
				this.setVisible(false);
				
			}
}
