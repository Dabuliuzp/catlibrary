package Pages.view.student;

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
//**   @author 唐可成

public class ShoppingList extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField TextFile0;
	private JTextField TextFile1;
	private JTextField TextFile3;
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
					ShoppingList frame = new ShoppingList();
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
	public ShoppingList() {
		setBounds(100, 100, 728, 468);
		setClosable(true);
		setTitle("商店");
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
						"\u5546\u54C1ID", "\u5546\u54C1\u540D\u79F0", "\u4EF7\u683C", "\u5E93\u5B58"
				}));
		scrollPane.setViewportView(ClassListTable);

		JLabel lblNewLabel = new JLabel("商品名称");
		lblNewLabel.setBounds(10, 298, 55, 30);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("购买数量");
		lblNewLabel_2.setBounds(265, 298, 55, 30);
		getContentPane().add(lblNewLabel_2);

		TextFile1 = new JTextField();
		TextFile1.setBounds(83, 298, 158, 25);
		getContentPane().add(TextFile1);
		TextFile1.setColumns(10);

		TextFile3 = new JTextField();
		TextFile3.setBounds(322, 298, 158, 25);
		TextFile3.setColumns(10);
		getContentPane().add(TextFile3);

		Button2 = new JButton("搜索");
		Button2.setBounds(567, 298, 133, 23);
		Button2.setFocusable(false);// 去除按钮虚线
		Button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				SelectSomeone(ae);

			}

		});
		getContentPane().add(Button2);

		Button4 = new JButton("刷新商品列表");
		Button4.setFocusable(false);
		Button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ResetButton(ae);

			}
		});
		Button4.setBounds(567, 337, 133, 23);
		getContentPane().add(Button4);

		JLabel lblNewLabel_1_1 = new JLabel("商品ID");
		lblNewLabel_1_1.setBounds(10, 333, 48, 30);
		getContentPane().add(lblNewLabel_1_1);

		TextFile0 = new JTextField();
		TextFile0.setColumns(10);
		TextFile0.setBounds(83, 336, 158, 25);
		getContentPane().add(TextFile0);

		Button6 = new JButton("购买");
		Button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				AddMyProduct(ae);
			}
		});
		Button6.setBounds(567, 377, 133, 23);
		getContentPane().add(Button6);
		this.dtm = (DefaultTableModel) ClassListTable.getModel();
		requestAllClass();
	}

	// 后台获取前端数据，变量注释在下面，不再重复注释
	protected void AddMyProduct(ActionEvent ae) {
		// TODO Auto-generated method stub
		try {
			String id = this.TextFile0.getText();// 商品id
			String textfile1 = this.TextFile1.getText();// 商品名称
			String textfile3 = this.TextFile3.getText();// 购买数量
			if (JOptionPane.showConfirmDialog(this, "是否购买商品？", "正在购买...",
					JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				out.writeObject("10");
				out.writeObject("addMyProduct");
				out.writeObject(MainApp.getCurrentUserId());
				out.writeObject(id);
				out.writeObject(textfile1);
				out.writeObject(textfile3);
				out.flush();
				int ActionType = (int) in.readObject();// 响应模式，逻辑由后台判断。后台传入。1用户未选择任何商品就购买，2购买失败；3购买成功

				if (ActionType == 1) {
					requestAllClass();
					return;
				} else if (ActionType == 2) {
					JOptionPane.showMessageDialog(this, "购买失败");

				} else if (ActionType == 3) {
					dtm.setRowCount(0);
					JOptionPane.showMessageDialog(this, "购买成功");
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
		this.TextFile3.setText("");
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
		this.TextFile3.setText("1");

	}

	// 搜索按钮响应，后台获取前端用户选择数据
	protected void SelectSomeone(ActionEvent ae) {

		// TODO Auto-generated method stub
		try {
			String id = this.TextFile0.getText();// 商品id
			String textfile1 = this.TextFile1.getText();// 商品名称
			if (JOptionPane.showConfirmDialog(this, "是否查找商品?", "正在选择...",
					JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				out.writeObject("10");
				out.writeObject("SelectSomeone");
				out.writeObject(id);
				out.writeObject(textfile1);
				out.flush();
				int ActionType = (int)in.readObject();// 响应模式，逻辑由后台判断。后台传入。1用户未输入内容就搜索，2查无此项目；3查找成功，后台传入所要查找的数据。
				if (ActionType == 1) {
					requestAllClass();
					return;
				} else if (ActionType == 2) {
					JOptionPane.showMessageDialog(this, "查找失败");

				} else if (ActionType == 3) {
					dtm.setRowCount(0);
					Integer allgoodsnum = (Integer) in.readObject();
					System.out.println(allgoodsnum);

					for(int i=0;i<allgoodsnum;i++){
						Vector v = new Vector();
						v.add((Long) in.readObject());//id
						v.add((String) in.readObject());//名称
						v.add((Double) in.readObject());//价格
						v.add((Integer) in.readObject());//库存
						dtm.addRow(v);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}

	}

	public void requestAllClass() {
		try{
			out.writeObject("10");
			out.writeObject("requestAllClass");
			out.flush();

			dtm.setRowCount(0);
			Integer allgoodsnum = (Integer) in.readObject();
			System.out.println(allgoodsnum);

			for(int i=0;i<allgoodsnum;i++){
				Vector v = new Vector();
				v.add((Long) in.readObject());//id
				v.add((String) in.readObject());//名称
				v.add((Double) in.readObject());//价格
				v.add((Integer) in.readObject());//库存
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
		this.TextFile3.setText("");

	}
}
