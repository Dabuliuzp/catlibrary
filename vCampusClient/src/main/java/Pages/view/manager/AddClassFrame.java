package Pages.view.manager;
//**@author 唐可成
import java.awt.EventQueue;


import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
public class AddClassFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField ClassName;
	private JComboBox GradeComboBox;
	private JComboBox MajorComboBox ;
	private JComboBox CollegeComboBox ;
	

	/**
	 * Create the frame.
	 */
	public AddClassFrame() {
		setTitle("正在添加班级");
		setBounds(100, 100, 652, 433);
		setClosable(true);
		setIconifiable(true);//最小化与关闭
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("班级名称");
		lblNewLabel.setBounds(22, 27, 83, 22);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("所属学院");
		lblNewLabel_1.setBounds(22, 108, 83, 22);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("所属年级");
		lblNewLabel_2.setBounds(281, 31, 83, 22);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("所属专业");
		lblNewLabel_3.setBounds(281, 112, 83, 22);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("班级介绍");
		lblNewLabel_4.setBounds(22, 191, 83, 22);
		getContentPane().add(lblNewLabel_4);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(142, 190, 412, 158);
		getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(142, 370, 93, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("重置");
		btnNewButton_1.setBounds(351, 370, 93, 23);
		getContentPane().add(btnNewButton_1);
		
		ClassName = new JTextField();
		ClassName.setBounds(88, 28, 162, 33);
		getContentPane().add(ClassName);
		ClassName.setColumns(10);
		
		String[] Grade= {"大一","大二"};
		 GradeComboBox=new JComboBox(new DefaultComboBoxModel(Grade)) ;
		GradeComboBox.setBounds(351, 27, 174, 33);
		getContentPane().add(GradeComboBox);
		
		String[] ComputerMajor= {"土墓工程","寄算机"};
		String[] TumuMajor= {"土墓工程"};
		MajorComboBox= new JComboBox(new DefaultComboBoxModel(TumuMajor));
		MajorComboBox.setBounds(351, 108, 174, 33);
		getContentPane().add(MajorComboBox);
		
		String[] College= {"土墓工程学院","寄算机学院"};
		CollegeComboBox =new JComboBox(new DefaultComboBoxModel(College));
		CollegeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		CollegeComboBox.setBounds(88, 108, 162, 33);
		getContentPane().add(CollegeComboBox);

	}
	//点×后关闭界面
		public void doDefaultCloseAction() {
			this.setVisible(false);
			this. ClassName.setText("");
		}
}
