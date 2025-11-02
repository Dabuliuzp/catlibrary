package Pages.view.student;

import java.awt.EventQueue;
//**   @author 唐可成

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
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
 
import java.util.Arrays;  
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;  

public class LessonList extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField TextFile1;
	private JTextField TextFile3;
	private JTextField TextFile2;
	private JButton Button1 ;
	private JButton Button2;
	private JButton Button4;
	private JTable ClassListTable;
	private DefaultTableModel dtm=null;
	private JTextField TextFile0;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LessonList frame = new LessonList();
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
	public LessonList() {
		setBounds(100, 100, 728, 468);
		setClosable(true);
		setTitle("我的课表");
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
				"\u8BFE\u7A0BID", "\u8BFE\u7A0B\u540D\u79F0", "\u6559\u5E08\uFF0C\u5B66\u5206", "\u4E0A\u8BFE\u65F6\u95F4"
			}
		));
		scrollPane.setViewportView(ClassListTable);
		
		JLabel lblNewLabel = new JLabel("课程名称");
		lblNewLabel.setBounds(10, 298, 51, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("上课时间");
		lblNewLabel_1.setBounds(10, 333, 51, 30);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("教师，课程学分");
		lblNewLabel_2.setBounds(234, 298, 103, 30);
		getContentPane().add(lblNewLabel_2);
		
		TextFile1 = new JTextField();
		TextFile1.setBounds(66, 301, 158, 25);
		getContentPane().add(TextFile1);
		TextFile1.setColumns(10);
		
		TextFile3 = new JTextField();
		TextFile3.setBounds(66, 338, 158, 25);
		TextFile3.setColumns(10);
		getContentPane().add(TextFile3);
		
		TextFile2 = new JTextField();
		TextFile2.setBounds(347, 298, 158, 25);
		TextFile2.setColumns(10);
		getContentPane().add(TextFile2);
		
		 Button1 = new JButton("退选");
		Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				deletebutton(ae);
			}
		});
		Button1.setBounds(504, 298, 93, 23);
		getContentPane().add(Button1);
		
		 Button2 = new JButton("搜索");
		Button2.setBounds(607, 298, 93, 23);
		Button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			
			SelectSomeone(ae);
			
			}
			
		});
		getContentPane().add(Button2);
		
	    Button4 = new JButton("刷新课表");
	    Button4.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		ResetButton(ae);
	    		//添加条目后立即刷新展示列表
	    		
	    	}
	    });
		Button4.setBounds(607, 337, 93, 23);
		getContentPane().add(Button4);
		
		JLabel lblNewLabel_1_1 = new JLabel("课程ID");
		lblNewLabel_1_1.setBounds(234, 333, 51, 30);
		getContentPane().add(lblNewLabel_1_1);
		
		TextFile0 = new JTextField();
		TextFile0.setColumns(10);
		TextFile0.setBounds(347, 336, 158, 25);
		getContentPane().add(TextFile0);
		this.dtm=(DefaultTableModel) ClassListTable.getModel();
		requestAllClass() ;
	}

	//重置按钮响应
	protected void ResetButton(ActionEvent ae) {
		// TODO Auto-generated method stub
		requestAllClass();//立即刷新展示列表
		this.TextFile0.setText("");
		this.TextFile1.setText("");
		this.TextFile2.setText("");
		this.TextFile3.setText("");
		
		
		/*测试代码
		 * dtm.setRowCount(0); Vector v=new
		 * Vector();//vector代表一行信息，请传入一个二维vector，dtm.addRow(v);代表把其中一行添加进滚动条 v.add("4");
		 * v.add("22"); dtm.addRow(v); Vector<Vector>vectors=new
		 * Vector<>();//请传入一个二维vector用来展示学生信息，把值赋给vectors
		 * 
		 * for(Vector<Vector> row:vectors) { dtm.addRow(row);
		 */
		
		}
		
		
	

	//退选按钮响应
	protected void deletebutton(ActionEvent ae) {
		// TODO Auto-generated method stub
		//弹出确认对话框，对话框的的响应
		
		
		if(JOptionPane.showConfirmDialog(this, "是否退选?","正在退选...",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
			String id =this.TextFile0.getText();//课程ID，后台可获取
			int ActionType=1;
			//删除响应，后台传入。成功1，失败2
			if(ActionType==1) {
				JOptionPane.showMessageDialog(this,"退选成功");
				requestAllClass();
			}else {
			JOptionPane.showMessageDialog(this,"退选失败");
			
			}
		}
		/*
		 * Vector v=new
		 * Vector();//vector代表一行信息，请传入一个二维vector，dtm.addRow(v);代表把其中一行添加进滚动条
		 * v.add("1 "); v.add("22"); dtm.addRow(v);
		 */
		
		//
	}

	//鼠标选中某一行后的响应
	
	protected void selectRow(MouseEvent me) {
		// TODO Auto-generated method stub
		this.TextFile0.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(),  0).toString());//课程ID
		this.TextFile1.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(),  1).toString());//课程名称讲所选中行的信息显示到对应的框里面
		this.TextFile2.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(),  2).toString());//教师，学分
		this.TextFile3.setText(dtm.getValueAt(this.ClassListTable.getSelectedRow(),  3).toString());//上课时间
		
        this.Button1.setEnabled(true);//选中某一行后按钮才可以点击
		
		
		
		
	}
//搜索按钮响应,后端获取用户选择列表具体数据接口，注释同上
	protected void SelectSomeone(ActionEvent ae) {
		// TODO Auto-generated method stub
		String id=this.TextFile0.getText();
		String textfile1=this.TextFile1.getText();
		String textfile2=this.TextFile2.getText();
		String textfile3=this.TextFile3.getText();
		//后台由此获取输入框数据
		int ActionType=2;//响应模式，后台传入。1用户未输入内容就搜索，2查无此项目；3查找成功，后台传入所要查找的数据
		
		if(ActionType==1) {
			requestAllClass();
			return;
		}
		else if(ActionType==2) {
			JOptionPane.showMessageDialog(this,"查找失败");
			
		}	
		else if(ActionType==3) {
			dtm.setRowCount(0);
			Vector<Vector>vectors=new Vector<>();//请传入一个二维vector用来展示信息
	  
			for(Vector<Vector> row:vectors) {
				
				dtm.addRow(row);
				
				
			}	
			
			
		}
		
		
	}

	public void requestAllClass() {
		dtm.setRowCount(0);
		/*
		 * Vector v=new
		 * Vector();//vector代表一行信息，请传入一个二维vector，dtm.addRow(v);代表把其中一行添加进滚动条
		 * v.add("30 "); v.add("22"); dtm.addRow(v);
		 */
		Vector<Vector>vectors=new Vector<>();//请传入一个二维vector用来展示学生信息
		
		/*
		 * for(Integer i=0;i< 20;i++) { Vector<String> firstRow = new Vector<>();
		 * firstRow.add("Apple"); firstRow.add("Banana"); vectors.add(firstRow); }
		 * Vector<String> firstRow = new Vector<>(); firstRow.add("Apple");
		 * firstRow.add("Banana"); vectors.add(firstRow);
		 */
		for(Vector<Vector> row:vectors) {
			
			dtm.addRow(row);
		}
		this.Button1.setEnabled(false);
		
		
	}
	//点×后关闭界面
	public void doDefaultCloseAction() {
		this.setVisible(false);
		this.TextFile0.setText("");
		this.TextFile1.setText("");
		this.TextFile2.setText("");
		this.TextFile3.setText("");
		
	}
}
