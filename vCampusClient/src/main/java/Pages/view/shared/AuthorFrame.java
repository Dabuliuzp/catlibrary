package Pages.view.shared;
//**   @author 唐可成

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class AuthorFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorFrame frame = new AuthorFrame();
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
	public AuthorFrame() {
		setTitle("软件开发人员名单");
		setBounds(100, 100, 864, 542);
		setClosable(true);
		setBorder (null);
		setIconifiable(true);//最小化与关闭
		getContentPane().setLayout(null);
		
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("前端界面：唐可成");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel.setBounds(117, 170, 217, 40);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("数据库：肖旭杰");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(117, 220, 160, 40);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblSocket = new JLabel("Socket+网络：王梓鹏");
		lblSocket.setFont(new Font("宋体", Font.PLAIN, 20));
		lblSocket.setBounds(117, 270, 207, 40);
		getContentPane().add(lblSocket);
		
		JLabel lblNewLabel_3 = new JLabel("后端：崔经瀚，柴嘉翊");
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(117, 320, 286, 40);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("日期：2024/9/13 东南大学");
		lblNewLabel_4.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_4.setBounds(117, 370, 256, 40);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("E:\\小米\\test\\100003401_p0.png"));
		lblNewLabel_2.setBounds(0, 0, 876, 515);
		getContentPane().add(lblNewLabel_2);

	}
	public void doDefaultCloseAction() {
		setVisible(false);
		
		
	}

}
