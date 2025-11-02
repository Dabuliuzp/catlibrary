package Pages.view.manager;
//**@author 唐可成
import Pages.MainApp;

import java.awt.EventQueue;


import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;

import java.util.Arrays;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;  

public class AllStudentFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField TextFile1;
	private JTextField TextFile3;
	private JTextField TextFile2;
	private JTextField TextFile4;
	private JButton Button3;
	private JButton Button2;
	private JButton Button4;//将按钮设置成为属性
	private JTable ClassListTable;
	private DefaultTableModel dtm=null;
	private JTextField TextFile0;

	ObjectInputStream in = MainApp.getIn();
	ObjectOutputStream out = MainApp.getOut();
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AllStudentFrame frame = new AllStudentFrame();
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
	@SuppressWarnings("serial")
	public AllStudentFrame() {
		setBounds(100, 100, 728, 468);
		setClosable(true);
		setTitle("学生信息管理");
		setIconifiable(true);//增加窗口最小化选项
	setBorder (null);
		getContentPane().setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 5, 690, 283);
		getContentPane().add(scrollPane);
		
		ClassListTable = new JTable();
		ClassListTable.setBackground(new Color(176, 193, 198));
		ClassListTable.setShowVerticalLines(false);
		
		//选中某一行后的响应
		ClassListTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				selectRow(me);
			}
		});
		ClassListTable.setShowGrid(false);
		ClassListTable.setShowHorizontalLines(false);
		ClassListTable.getTableHeader().setReorderingAllowed(false);//设置表头不可拖动
		//设置表头 高度
		ClassListTable.setRowHeight(25);
		/*
		 * //设置表格居中 DefaultTableCellRenderer r=new DefaultTableCellRenderer();
		 * r.setHorizontalAlig
		 */
		
		
		
		ClassListTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u5B66\u53F7", "\u5B66\u751F\u59D3\u540D", "\u5E74\u7EA7", "专业", "\u5B66\u7C4D\u72B6\u6001"
			}
		));
		scrollPane.setViewportView(ClassListTable);
		
		JLabel lblNewLabel = new JLabel("学生姓名");
		lblNewLabel.setBounds(10, 298, 66, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("专业");
		lblNewLabel_1.setBounds(10, 338, 49, 30);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("年级");
		lblNewLabel_2.setBounds(265, 298, 59, 30);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("学籍状态");
		lblNewLabel_3.setBounds(265, 338, 59, 30);
		getContentPane().add(lblNewLabel_3);
		
		TextFile1 = new JTextField();
		TextFile1.setBounds(70, 301, 158, 25);
		getContentPane().add(TextFile1);
		TextFile1.setColumns(10);
		
		TextFile3 = new JTextField();
		TextFile3.setBounds(70, 341, 158, 25);
		TextFile3.setColumns(10);
		getContentPane().add(TextFile3);
		
		TextFile2 = new JTextField();
		TextFile2.setBounds(336, 298, 158, 25);
		TextFile2.setColumns(10);
		getContentPane().add(TextFile2);
		
		TextFile4 = new JTextField();
		TextFile4.setBounds(334, 341, 158, 25);
		TextFile4.setColumns(10);
		getContentPane().add(TextFile4);
		
		Button3 = new JButton("编辑");
		Button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				EditButton(ae);
			}
		});
		Button3.setBounds(504, 337, 93, 23);
		getContentPane().add(Button3);
		
		 Button2 = new JButton("搜索");
		Button2.setBounds(607, 298, 93, 23);
		Button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			
			SelectSomeone(ae);
			
			}
			
		});
		getContentPane().add(Button2);
		
	    Button4 = new JButton("刷新列表");
	    Button4.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		ResetButton(ae);
	    		//添加条目后立即刷新展示列表
	    		
	    	}
	    });
		Button4.setBounds(607, 337, 93, 23);
		getContentPane().add(Button4);
		
		JLabel lblNewLabel_1_1 = new JLabel("学号");
		lblNewLabel_1_1.setBounds(10, 373, 59, 30);
		getContentPane().add(lblNewLabel_1_1);
		
		TextFile0 = new JTextField();
		TextFile0.setColumns(10);
		TextFile0.setBounds(70, 378, 158, 25);
		getContentPane().add(TextFile0);

		this.dtm=(DefaultTableModel) ClassListTable.getModel();
		requestAllClass() ;
	}

	//编辑按钮响应，后台获取前端用户选择数据。注释同上
	protected void EditButton(ActionEvent ae) {
		try{
			// TODO Auto-generated method stub
			String id=this.TextFile0.getText();
			String textfile1=this.TextFile1.getText();
			String textfile2=this.TextFile2.getText();
			String textfile3=this.TextFile3.getText();
			String textfile4=this.TextFile4.getText();

			out.writeObject("6");
			out.writeObject("StudentEdit");
			out.writeObject(id);
			out.writeObject(textfile1);
			out.writeObject(textfile2);
			out.writeObject(textfile3);
			out.writeObject(textfile4);
			out.flush();

			if(JOptionPane.showConfirmDialog(this, "是否确认编辑?","正在编辑...",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				Boolean ActionType=(Boolean) in.readObject();
				//编辑响应，后台传入。成功1，失败2
				if(ActionType==true) {
					JOptionPane.showMessageDialog(this,"编辑成功");
					requestAllClass();
				}else {
					JOptionPane.showMessageDialog(this,"编辑失败");

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}

	//重置按钮响应
	protected void ResetButton(ActionEvent ae) {
		// TODO Auto-generated method stub
		//立即刷新展示列表
		this.TextFile0.setText("");
		this.TextFile1.setText("");
		this.TextFile2.setText("");
		this.TextFile3.setText("");
		this.TextFile4.setText("");

		requestAllClass();
		/*测试代码
		 * dtm.setRowCount(0); Vector v=new
		 * Vector();//vector代表一行信息，请传入一个二维vector，dtm.addRow(v);代表把其中一行添加进滚动条 v.add("4");
		 * v.add("22"); dtm.addRow(v); Vector<Vector>vectors=new
		 * Vector<>();//请传入一个二维vector用来展示学生信息，把值赋给vectors
		 *
		 * for(Vector<Vector> row:vectors) { dtm.addRow(row);
		 */

	}
	//鼠标选中某一行后的响应
	protected void selectRow(MouseEvent me) {
		// TODO Auto-generated method stub
		this.TextFile0.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(),  0).toString());
		this.TextFile1.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(),  1).toString());//讲所选中行的信息显示到对应的框里面
		this.TextFile2.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(),  2).toString());
		this.TextFile3.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(),  3).toString());
		this.TextFile4.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(),  4).toString());

		this.Button3.setEnabled(true);


	}
	//搜索按钮响应，后台获取前端用户选择数据。注释同上
	protected void SelectSomeone(ActionEvent ae) {
		try{
			String id=this.TextFile0.getText();
			String textfile1=this.TextFile1.getText();
			String textfile2=this.TextFile2.getText();
			String textfile3=this.TextFile3.getText();
			String textfile4=this.TextFile4.getText();

			out.writeObject("6");
			out.writeObject("SelectSomeone");
			out.writeObject(id);
			out.writeObject(textfile1);
			out.writeObject(textfile2);
			out.writeObject(textfile3);
			out.writeObject(textfile4);
			out.flush();

			//后台由此获取输入框数据
			Integer ActionType=(Integer) in.readObject();//响应模式，后台传入。1用户未输入内容就搜索，2查无此项目；3查找成功，后台传入所要查找的数据

			if(ActionType==1) {
				requestAllClass();
				return;
			}
			else if(ActionType==2) {
				JOptionPane.showMessageDialog(this,"查找失败");

			}
			else if(ActionType==3) {
				dtm.setRowCount(0);
				Integer allgoodsnum = (Integer) in.readObject();
				System.out.println(allgoodsnum);

				for(int i=0;i<allgoodsnum;i++){
					Vector v = new Vector();
					v.add((Long) in.readObject());//学号
					v.add((String) in.readObject());//学生姓名
					v.add((String) in.readObject());//年级
					v.add((String) in.readObject());//院系
					v.add((String) in.readObject());
					dtm.addRow(v);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}

	public void requestAllClass() {
		try{
			out.writeObject("6");
			out.writeObject("requestAllClass");
			out.flush();

			dtm.setRowCount(0);
			Integer allgoodsnum = (Integer) in.readObject();
			System.out.println(allgoodsnum);

			for(int i=0;i<allgoodsnum;i++){
				Vector v = new Vector();
				v.add((Long) in.readObject());//学号
				v.add((String) in.readObject());//学生姓名
				v.add((String) in.readObject());//年级
				v.add((String) in.readObject());//专业
				v.add((String) in.readObject());
				dtm.addRow(v);
			}
			this.Button3.setEnabled(false);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}
	//点×后关闭界面
	public void doDefaultCloseAction() {
		this.setVisible(false);
		this.TextFile0.setText("");
		this.TextFile1.setText("");
		this.TextFile2.setText("");
		this.TextFile3.setText("");
		this.TextFile4.setText("");
	}
}
