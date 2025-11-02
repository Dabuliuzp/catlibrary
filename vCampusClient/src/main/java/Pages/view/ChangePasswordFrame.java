package Pages.view;
//**@author 唐可成
import Pages.MainApp;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ChangePasswordFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField OldPassword;
	private JTextField NewPassword;
	private JTextField ComfirmPassword;

	ObjectInputStream in = MainApp.getIn();
	ObjectOutputStream out = MainApp.getOut();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePasswordFrame frame = new ChangePasswordFrame();
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
	public ChangePasswordFrame() {
		setTitle("正在修改密码....");
		setClosable(true);//使窗口出现关闭选线
		setBorder (null);
		setBounds(100, 100, 397, 241);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("输入原密码:");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel.setBounds(68, 36, 93, 23);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("请输入新密码：");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setBounds(65, 69, 110, 31);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("确认密码：");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(68, 118, 93, 23);
		getContentPane().add(lblNewLabel_2);
		
		OldPassword = new JTextField();
		OldPassword.setBounds(171, 36, 171, 23);
		getContentPane().add(OldPassword);
		OldPassword.setColumns(10);
		OldPassword.setOpaque(false);
		
		NewPassword = new JTextField();
		NewPassword.setColumns(10);
		NewPassword.setOpaque(false);
		NewPassword.setBounds(171, 80, 171, 23);
		getContentPane().add(NewPassword);
		
		ComfirmPassword = new JTextField();
		ComfirmPassword.setColumns(10);
		ComfirmPassword.setBounds(171, 118, 171, 23);
		getContentPane().add(ComfirmPassword);
		ComfirmPassword.setOpaque(false);
		
		JButton btnNewButton = new JButton("确认");
		btnNewButton.setBackground(new Color(128, 128, 128));
		

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ComFirmButton(ae);
				
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 15));
		btnNewButton.setBounds(171, 163, 93, 23);
		btnNewButton.setOpaque(false);//设置透明
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("");
		//lblNewLabel_3.setIcon(new ImageIcon(ChangePasswordFrame.class.getResource("/Pages/image/shared.jpg")));
		lblNewLabel_3.setBounds(0, 0, 404, 229);
		getContentPane().add(lblNewLabel_3);
		
        
        
	}
	//重写确认按钮响应函数
	protected void ComFirmButton(ActionEvent ae) {
		// TODO Auto-generated method stub
		setVisible(false);
		//后台由此获取输入框内容
		String SendOldPassword=this.OldPassword.getText();
		String SendNldPassword=this.NewPassword.getText();
		String SendComfirmPassword=this.ComfirmPassword.getText();
		try{
			out.writeObject("1");
			out.writeObject("ChangePassword");
			out.writeObject(MainApp.getCurrentUserId());
			System.out.println(MainApp.getCurrentUserId());
			out.writeObject(SendOldPassword);
			out.writeObject(SendNldPassword);
			out.writeObject(SendComfirmPassword);
			out.flush();
			//响应模式，1原密码错误，2两次填写新密码不一致，3修改成功
			Integer ActionType=(Integer) in.readObject();
			if(ActionType==0) {
				JOptionPane.showMessageDialog(this,"输入的原密码错误");

			}
			if(ActionType==1) {
				JOptionPane.showMessageDialog(this,"两次填写新密码不一致");

			}
			if(ActionType==2) {
				JOptionPane.showMessageDialog(this,"修改密码成功");

			}

			OldPassword.setText("");
			NewPassword.setText("");
			ComfirmPassword.setText("");//按确认键后关闭窗口并清除已填写内容
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}

		
	}

	//重写密码修改页面x按钮效果
	public void doDefaultCloseAction() {
		setVisible(false);
		this.OldPassword.setText("");
		this.NewPassword.setText("");
		this.ComfirmPassword.setText("");
		
	}
}
