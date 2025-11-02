package Pages.view.student;
//**   @author 唐可成

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

public class SelectedLessonList extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField TextFile0;
	private JTextField TextFile1;
	private JTextField TextFile3;
	private JTextField TextFile2;
	private JTextField TextFile4;
	private JButton Button2;
	private JButton Button4;// 将按钮设置成为属性
	private JTable ClassListTable;
	private DefaultTableModel dtm = null;
	private JButton Button6;

	ObjectInputStream in = MainApp.getIn();
	ObjectOutputStream out = MainApp.getOut();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectedLessonList frame = new SelectedLessonList();
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
	public SelectedLessonList() {
		setBounds(100, 100, 728, 468);
		setClosable(true);
		setTitle("课程列表");
		setIconifiable(true);// 增加窗口最小化选项
		setBorder(null);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 5, 690, 283);
		getContentPane().add(scrollPane);

		ClassListTable = new JTable();
		ClassListTable.setBackground(new Color(176, 193, 198));
		ClassListTable.setShowVerticalLines(false);

		// 选中某一行后的响应
		ClassListTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				selectRow(me);
			}
		});
		ClassListTable.setShowGrid(false);
		ClassListTable.setShowHorizontalLines(false);
		ClassListTable.getTableHeader().setReorderingAllowed(false);// 设置表头不可拖动
		// 设置表头 高度
		ClassListTable.setRowHeight(25);
		/*
		 * //设置表格居中 DefaultTableCellRenderer r=new DefaultTableCellRenderer();
		 * r.setHorizontalAlig
		 */

		ClassListTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"\u8BFE\u7A0BID", "\u8BFE\u7A0B\u540D\u79F0", "授课老师",
						"学分", "课程安排", "课容量"
				}));
		scrollPane.setViewportView(ClassListTable);

		JLabel lblNewLabel = new JLabel("课程名称");
		lblNewLabel.setBounds(10, 298, 55, 30);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("课程学分");
		lblNewLabel_1.setBounds(10, 333, 55, 30);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("教师");
		lblNewLabel_2.setBounds(265, 298, 100, 30);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("课程安排");
		lblNewLabel_3.setBounds(265, 338, 87, 30);
		getContentPane().add(lblNewLabel_3);

		TextFile1 = new JTextField();
		TextFile1.setBounds(83, 298, 158, 25);
		getContentPane().add(TextFile1);
		TextFile1.setColumns(10);

		TextFile3 = new JTextField();
		TextFile3.setBounds(83, 338, 158, 25);
		TextFile3.setColumns(10);
		getContentPane().add(TextFile3);

		TextFile2 = new JTextField();
		TextFile2.setBounds(375, 298, 158, 25);
		TextFile2.setColumns(10);
		getContentPane().add(TextFile2);

		TextFile4 = new JTextField();
		TextFile4.setBounds(375, 336, 158, 25);
		TextFile4.setColumns(10);
		getContentPane().add(TextFile4);

		Button2 = new JButton("搜索");
		Button2.setBounds(567, 298, 133, 23);
		Button2.setFocusable(false);// 去除按钮虚线
		Button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				SelectSomeone(ae);

			}

		});
		getContentPane().add(Button2);

		Button4 = new JButton("刷新课程列表");
		Button4.setFocusable(false);
		Button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ResetButton(ae);
				// requestAllClass();//添加条目后立即刷新展示列表

			}
		});
		Button4.setBounds(567, 337, 133, 23);
		getContentPane().add(Button4);

		JLabel lblNewLabel_1_1 = new JLabel("课程ID");
		lblNewLabel_1_1.setBounds(10, 373, 48, 30);
		getContentPane().add(lblNewLabel_1_1);

		TextFile0 = new JTextField();
		TextFile0.setColumns(10);
		TextFile0.setBounds(83, 378, 158, 25);
		getContentPane().add(TextFile0);

		Button6 = new JButton("加入课表");
		Button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				AddMyLesson(ae);
			}
		});
		Button6.setBounds(567, 377, 133, 23);
		getContentPane().add(Button6);
		this.dtm = (DefaultTableModel) ClassListTable.getModel();
		requestAllClass();
	}

	protected void AddMyLesson(ActionEvent ae) {

		// TODO Auto-generated method stub
		try {
			String id = this.TextFile0.getText();// 课程id
			String textfile1 = this.TextFile1.getText();// 课程名
			String textfile2 = this.TextFile2.getText();// 教师
			String textfile3 = this.TextFile3.getText();// 学分
			String textfile4 = this.TextFile4.getText();// 是否已选后台由此获取输入框数据
			if (JOptionPane.showConfirmDialog(this, "是否选择该课程?", "正在添加...",
					JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				out.writeObject("12");
				out.writeObject("addMyLesson");
				out.writeObject(MainApp.getCurrentUserId());
				out.writeObject(id);
				out.flush();
				int ActionType = (Integer) in.readObject();// 响应模式，逻辑由后台判断。后台传入。1用户未输入内容就添加，2课程冲突；3添加成功，4不在计划内后台传入所要查找的数据。

				if (ActionType == 1) {
					requestAllClass();
					return;
				} else if (ActionType == 2) {
					JOptionPane.showMessageDialog(this, "课容量已满");

				} else if (ActionType == 3) {
					JOptionPane.showMessageDialog(this, "课程冲突");

				} else if (ActionType == 4) {
					dtm.setRowCount(0);
					JOptionPane.showMessageDialog(this, "添加成功");
					requestAllClass();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}

	}

	// 重置

	// 刷新按钮响应
	protected void ResetButton(ActionEvent ae) {
		// TODO Auto-generated method stub
		requestAllClass();// 添加条目后立即刷新展示列表
		this.TextFile0.setText("");//
		this.TextFile1.setText("");
		this.TextFile2.setText("");
		this.TextFile3.setText("");
		this.TextFile4.setText("");
		requestAllClass();
		// 测试代码

		/*
		 * Vector v=new Vector();
		 * v.add("30 "); v.add("22"); v.add("30 "); v.add("22"); v.add("30 ");
		 * dtm.addRow(v);
		 */

	}

	// 鼠标选中某一行后的响应

	protected void selectRow(MouseEvent me) {
		// TODO Auto-generated method stub
		this.TextFile0.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(), 0).toString());//
		this.TextFile1.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(), 1).toString());// 讲所选中行的信息显示到对应的框里面
		this.TextFile2.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(), 2).toString());
		this.TextFile3.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(), 3).toString());
		this.TextFile4.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(), 4).toString());

	}

	// 搜索按钮响应，后台由此获取所选数据
	protected void SelectSomeone(ActionEvent ae) {
		try{
			String id=this.TextFile0.getText();//课程ID
			String textfile1=this.TextFile1.getText();//课程名称
			String textfile2=this.TextFile2.getText();//授课老师
			String textfile3=this.TextFile3.getText();//学分
			String textfile4=this.TextFile4.getText();//是否已选

			out.writeObject("12");
			out.writeObject("SelectSomeone");
			out.writeObject(id);
			out.writeObject(textfile1);
			out.writeObject(textfile2);
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
				Integer allcoursenum = (Integer) in.readObject();
				System.out.println(allcoursenum);

				for(int i=0;i<allcoursenum;i++){
					Vector v = new Vector();
					v.add((Long) in.readObject());//id
					v.add((String) in.readObject());//课程名称
					v.add((String) in.readObject());//授课老师
					v.add((int) in.readObject());//学分
					v.add((String) in.readObject());//课程安排
					v.add((int) in.readObject());//课容量
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
			out.writeObject("12");
			out.writeObject("requestAllClass");
			out.flush();

			dtm.setRowCount(0);
			Integer allcoursenum = (Integer) in.readObject();
			System.out.println(allcoursenum);

			for(int i=0;i<allcoursenum;i++){
				Vector v = new Vector();
				v.add((Long) in.readObject());//id
				v.add((String) in.readObject());//课程名称
				v.add((String) in.readObject());//授课老师
				v.add((int) in.readObject());//学分
				v.add((String) in.readObject());//课程安排
				v.add((int) in.readObject());//课容量
				dtm.addRow(v);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}

	// 点×后关闭界面
	public void doDefaultCloseAction() {
		this.setVisible(false);
		this.TextFile0.setText("");
		this.TextFile1.setText("");
		this.TextFile2.setText("");
		this.TextFile3.setText("");
		this.TextFile4.setText("");
	}
}
