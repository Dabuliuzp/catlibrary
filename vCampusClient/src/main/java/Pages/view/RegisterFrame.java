package Pages.view;
//**@author 唐可成
import Pages.MainApp;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RegisterFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField TextFile1;
	private JTextField TextFile2;

	ObjectInputStream in = MainApp.getIn();
	ObjectOutputStream out = MainApp.getOut();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterFrame frame = new RegisterFrame();
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
	public RegisterFrame() {
		setTitle("正在注册");
		setBounds(100, 100, 374, 201);
		setClosable(true);
		setIconifiable(true);//最小化与关闭
		getContentPane().setLayout(null);
		setBorder (null);
		
		TextFile1 = new JTextField();
		TextFile1.setBounds(149, 32, 163, 23);
		getContentPane().add(TextFile1);
		TextFile1.setOpaque(false);
		TextFile1.setColumns(10);
		
		TextFile2 = new JTextField();
		TextFile2.setColumns(10);
		TextFile2.setBounds(149, 79, 163, 23);
		TextFile2.setOpaque(false);
		getContentPane().add(TextFile2);
		
		JLabel lblNewLabel = new JLabel("用户名:");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel.setBounds(76, 28, 63, 30);
		
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("密码:");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(89, 75, 50, 30);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("注册");
		btnNewButton.setFont(new Font("宋体", Font.BOLD, 14));
		btnNewButton.setBackground(new Color(64, 128, 128));
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				RegisterButton(ae);
			}
		});
		btnNewButton.setBounds(149, 119, 93, 30);
		btnNewButton.setOpaque(false);
		btnNewButton.setFocusable(false);//去除按钮虚线
		
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("");
		//lblNewLabel_2.setIcon(new ImageIcon(RegisterFrame.class.getResource("/main/java/Pages/image/register.jpeg")));
		lblNewLabel_2.setBounds(0, 0, 374, 197);
		getContentPane().add(lblNewLabel_2);

	}
	protected void RegisterButton(ActionEvent ae) {
		try {
			setVisible(false);
			//后台由此获取输入框内容
			String name=this.TextFile1.getText();//用户名
			String password=this.TextFile2.getText();//密码

			out.writeObject("1");
			out.writeObject("Register");
			out.writeObject(name);
			out.writeObject(password);
			out.flush();

			boolean response = (boolean)in.readObject();
			if(response == true) {
				JOptionPane.showMessageDialog(this,"注册成功");
			}
			else{
				JOptionPane.showMessageDialog(this,"注册失败");
			}

			TextFile1.setText("");
			TextFile2.setText("");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		
		
	}

	public void doDefaultCloseAction() {
		setVisible(false);
		this.TextFile1.setText("");
		this.TextFile2.setText("");
		
		
	}
}
