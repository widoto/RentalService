import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import javax.swing.JComboBox;

public class SellJFrame extends JFrame {
	private JPanel contentPane;
	private JTextField textName;
	private JTextField textPhone;
	private JTextField textCount;
	private JTextField textSearch1;
	private JTextField textSearch2;
	private JButton searchBtn1 = new JButton("\uAC80\uC0C9");
	private JButton searchBtn2 = new JButton("\uAC80\uC0C9");
	private JButton rentBtn = new JButton("\uB300\uC5EC");
	private JButton returnBtn = new JButton("\uBC18\uB0A9");
	private JButton confirmBtn = new JButton("\uD655\uC778");
	private JButton undoBtn = new JButton("\uC120\uD0DD \uCDE8\uC18C");
	// �� ���� ���̺�
	private String cColNames[] = { "�̸�", "��ȭ��ȣ", "�뿩��", "�ݳ���", "����", "��ǰ1", "��ǰ2", "��ǰ3", "�뿩��"};
	private DefaultTableModel model1 = new DefaultTableModel(cColNames, 0);
	private JTable cTable = new JTable(model1);
	private TableRowSorter<TableModel> sorter1 = new TableRowSorter<>(cTable.getModel());
	// ��ǰ ���� ���̺�
	private String pColNames[] = { "�̸�", "�ڵ�", "����", "����"};
	private DefaultTableModel model2 = new DefaultTableModel(pColNames, 0);
	private JTable pTable = new JTable(model2);
	private TableRowSorter<TableModel> sorter2 = new TableRowSorter<>(pTable.getModel());
	// ���� �����ϴ� ��ǰ ���̺�
	private DefaultTableModel model3 = new DefaultTableModel(pColNames, 0);
	private JTable sTable = new JTable(model3);
	private TableRowSorter<TableModel> sorter3 = new TableRowSorter<>(sTable.getModel());
	
	private JDateChooser textReturnDate = new JDateChooser();
	private JLabel revenue = new JLabel("0");
	ObjectOutputStream infoFile = null; 
	Manager act = null;
	private JTextField p1;
	private JTextField p2;
	private JTextField p3;
	private JLabel undo1 = new JLabel("");
	private JLabel undo2 = new JLabel("");
	
	// value�� = ��ǰ �ڵ尪�� �̿��Ͽ� ��ǰ ���̺� �ִ� ��ġ�� ã�Ƴ� -> ��� ������Ʈ�� ���
	/*int getRowByValue(TableModel model, Object value) {
	    for (int i = model.getRowCount() - 1; i >= 0; i--) {
	        for (int j = model.getColumnCount() - 1; j >= 0; j--) {
	            if (model.getValueAt(i, j).equals(value)) {
	            
	                return i;
	            }
	        }
	    }
	    return -1;
	 }*/
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// SellJFrame frame = new SellJFrame();
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SellJFrame(Manager act) {
		this.act = act;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// ����Ǿ��ִ� userList�� ��ǰ���� �� �߰�
		int uCount = (act.getUserList()).size();
		for	(int i=0; i< uCount; i++)
		{
			try{
				User u = act.getUserList().get(i);
				String arr[] = new String[9];
				arr[0] = u.getName();
				arr[1] = u.getPhone();
				arr[2] = u.getRentalDay();
				arr[3] = u.getReturnDay();
				arr[4] = Integer.toString(u.getRentalCount());
				arr[5] = u.codeAt(0);
				arr[6] = u.codeAt(1);
				arr[7] = u.codeAt(2);
				arr[8] = Integer.toString(u.pay());
				model1.addRow(arr); 
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"�����͸� �ҷ����µ� ������ �߻��Ͽ����ϴ�."); // u.pay()���� ���� �߻�
			}
			
		}		
		
		revenue.setText(Integer.toString(act.getRevenue()));
		// HomeJFrame���� �����ϴ� ������
		JLabel home = new JLabel("");
		home.setBounds(13, 15, 20, 15);
		Image imgHome = new ImageIcon(this.getClass().getResource("/homeIcon.png")).getImage();
		home.setIcon(new ImageIcon(imgHome));
		home.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	dispose(); // ���� â �ݱ�
	        }
	    });
		contentPane.add(home);		
		// �Ǹ�
		JLabel text0 = new JLabel("[\uD310\uB9E4]");
		text0.setForeground(Color.BLUE);
		text0.setBounds(50, 10, 50, 20);
		text0.setBackground(Color.YELLOW);
		text0.setFont(new Font("����", Font.BOLD, 15));
		contentPane.add(text0);
		// �����
		JLabel text1 = new JLabel("\uB9E4\uCD9C\uC561\uC740");
		text1.setBounds(360, 10, 70, 20);
		text1.setFont(new Font("����", Font.PLAIN, 15));
		contentPane.add(text1);
		// ����� ���̱�
		revenue.setForeground(Color.RED);
		revenue.setBounds(435, 10, 70, 20);
		revenue.setHorizontalAlignment(SwingConstants.TRAILING);
		revenue.setFont(new Font("����", Font.BOLD, 20));
		contentPane.add(revenue);
		
		JLabel text2 = new JLabel("(\uC6D0)\uC785\uB2C8\uB2E4.");
		text2.setBounds(515, 10, 85, 20);
		text2.setFont(new Font("����", Font.PLAIN, 15));
		contentPane.add(text2);
		// �� ȭ�� ����
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(18, 40, 573, 381);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("�� ����", null, panel, null);
		panel.setLayout(null);
		// �� �˻�
		textSearch1 = new JTextField();
		textSearch1.setBounds(167, 10, 146, 21);
		panel.add(textSearch1);
		textSearch1.setColumns(10);
		// �˻� ��ư
		searchBtn1.setBounds(322, 9, 60, 23);
		searchBtn1.addActionListener(new SellListener());
		panel.add(searchBtn1);
		
		// model�� �����͸� �����ϴ� ���̺��� ����
		cTable.setRowSorter(sorter1);
		cTable.setFont(new Font("����", Font.PLAIN, 13));
		// �뿩���� �������� �������� ����
		List<RowSorter.SortKey> sortKeys1 = new ArrayList<>();
		int columnIndexToSort1 = 2;
		sortKeys1.add(new RowSorter.SortKey(columnIndexToSort1, SortOrder.ASCENDING));
		 
		sorter1.setSortKeys(sortKeys1);
		sorter1.sort();

		cTable.setBounds(10, 40, 550, 120);
		JScrollPane scrollPane2 = new JScrollPane(cTable);
		scrollPane2.setSize(550, 120);
		scrollPane2.setLocation(10, 40);
		panel.add(scrollPane2);
		// �뿩 ���� �Է�
		// �̸�
		textName = new JTextField();
		textName.setBounds(43, 170, 60, 20);
		panel.add(textName);
		textName.setColumns(10);

		JLabel text3 = new JLabel("\uC774\uB984: ");
		text3.setBounds(10, 170, 40, 20);
		panel.add(text3);
		text3.setFont(new Font("����", Font.PLAIN, 13));
		
		JLabel text4 = new JLabel("\uC804\uD654\uBC88\uD638:");
		text4.setBounds(108, 170, 55, 20);
		panel.add(text4);
		text4.setFont(new Font("����", Font.PLAIN, 13));
		// ��ȭ��ȣ
		textPhone = new JTextField();
		textPhone.setBounds(168, 170, 96, 21);
		panel.add(textPhone);
		textPhone.setText("(-\uBE7C\uACE0 \uC785\uB825)");
		textPhone.setColumns(10);
		// �ݳ���
		JLabel text5 = new JLabel("\uBC18\uB0A9\uC77C:");
		text5.setBounds(268, 170, 50, 20);
		panel.add(text5);
		text5.setFont(new Font("����", Font.PLAIN, 13));
		textReturnDate.setBounds(314, 170, 95, 20);
		panel.add(textReturnDate);
		// �뿩��
		JLabel text6 = new JLabel("\uB300\uC5EC\uC218(\uCD5C\uB3003\uAC1C):");
		text6.setBounds(413, 170, 110, 20);
		panel.add(text6);
		text6.setFont(new Font("����", Font.PLAIN, 13));
		
		textCount = new JTextField();
		textCount.setBounds(516, 170, 40, 21);
		panel.add(textCount);
		textCount.setColumns(10);
		// �뿩 ��ư
		JLabel text7 = new JLabel("(\uC0C1\uD488 \uBAA9\uB85D\uC5D0\uC11C \uB300\uC5EC\uD558\uACE0\uC790 \uD558\uB294 \uC0C1\uD488\uC744 \uC120\uD0DD\uD558\uC138\uC694)");
		text7.setBounds(275, 225, 293, 20);
		panel.add(text7);
		rentBtn.setBounds(480, 287, 80, 23);
		rentBtn.addActionListener(new SellListener());
		panel.add(rentBtn);
		// �ݳ� ��ư
		JLabel text8 = new JLabel("(\uBC18\uB0A9\uD558\uB294 \uACE0\uAC1D\uC758 \uD14C\uC774\uBE14 \uD589\uC744 \uD074\uB9AD\uD558\uC138\uC694)");
		text8.setBounds(236, 320, 250, 20);
		panel.add(text8);
		returnBtn.setBounds(480, 319, 80, 23);
		returnBtn.addActionListener(new SellListener());
		panel.add(returnBtn);
		
		JLabel text9 = new JLabel("\uC120\uD0DD\uD55C \uC0C1\uD488:");
		text9.setBounds(178, 255, 103, 15);
		panel.add(text9);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.controlHighlight);
		panel_2.setBounds(251, 246, 309, 31);
		panel.add(panel_2);
		panel_2.setLayout(null);
		// ������ ��ǰ1, ��ǰ2, ��ǰ3
		p1 = new JTextField();
		p1.setText("--");
		p1.setBounds(12, 5, 85, 21);
		panel_2.add(p1);
		p1.setColumns(10);
		
		p2 = new JTextField();
		p2.setText("--");
		p2.setBounds(112, 5, 85, 21);
		panel_2.add(p2);
		p2.setColumns(10);
		
		p3 = new JTextField();
		p3.setText("--");
		p3.setBounds(212, 5, 85, 21);
		panel_2.add(p3);
		p3.setColumns(10);
		
		
		undo1.setBounds(506, 11, 50, 30);
		panel.add(undo1);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("\uC0C1\uD488 \uBAA9\uB85D", null, panel_1, null);
		panel_1.setLayout(null);
		// ��ǰ �˻�
		textSearch2 = new JTextField();
		textSearch2.setBounds(167, 10, 146, 21);
		panel_1.add(textSearch2);
		textSearch2.setColumns(10);
		// �˻� ��ư
		searchBtn2.setBounds(322, 9, 60, 23);
		searchBtn2.addActionListener(new SellListener());
		panel_1.add(searchBtn2);
		
		// ����Ǿ��ִ� productList�� ��ǰ���� �� �߰�(������ �ε�)
		int pCount = (act.getProductList()).size();
		for	(int i=0; i< pCount; i++)
		{
			Product p = act.getProductList().get(i);
			String arr[] = new String[4];
			arr[0] = p.getName();
			arr[1] = p.getCode();
			arr[2] = Integer.toString(p.getNumber());
			arr[3] = Integer.toString(p.getPrice()); 
			model2.addRow(arr); 
		}
		
		
		// model�� �����͸� �����ϴ� ���̺��� ����
		pTable.setRowSorter(sorter2);
		pTable.setFont(new Font("����", Font.PLAIN, 13));
		
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		// ���糯� �������� �������� ����
		int columnIndexToSort2 = 2;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort2, SortOrder.ASCENDING));
		 
		sorter2.setSortKeys(sortKeys);
		sorter2.sort();		
				
		pTable.setBounds(11, 45, 545, 110);
		JScrollPane scrollPane = new JScrollPane(pTable);
		scrollPane.setSize(550, 120);
		scrollPane.setLocation(10, 40);
		panel_1.add(scrollPane);
		
		JLabel text10 = new JLabel("(�뿩�Ϸ��� ��ǰ�� ������ŭ �����Ͻÿ�)");
		text10.setBounds(12, 170, 301, 15);
		panel_1.add(text10);
		// Ȯ�� ��ư
		confirmBtn.setBounds(469, 319, 91, 23);
		confirmBtn.addActionListener(new SellListener());
		panel_1.add(confirmBtn);
		
		sTable.setBounds(0, 0, 1, 1);
		JScrollPane scrollPane_1 = new JScrollPane(sTable);
		scrollPane_1.setBounds(10, 193, 550, 90);
		panel_1.add(scrollPane_1);
		
		// model�� �����͸� �����ϴ� ���̺��� ����
		sTable.setRowSorter(sorter3);
		sTable.setFont(new Font("����", Font.PLAIN, 13));
		// ���� �������� �������� ����
		List<RowSorter.SortKey> sortKeys3 = new ArrayList<>();
		int columnIndexToSort3 = 1;
		sortKeys3.add(new RowSorter.SortKey(columnIndexToSort3, SortOrder.ASCENDING));
		 
		sorter1.setSortKeys(sortKeys1);
		sorter1.sort();		
				
		// �˻� ��� ��ư
		undoBtn.setBounds(469, 293, 91, 23);
		undoBtn.addActionListener(new SellListener());
		panel_1.add(undoBtn);
		
		JLabel lblNewLabel = new JLabel("(\uC120\uD0DD\uD55C \uC0C1\uD488\uC744 \uCDE8\uC18C\uD558\uB824\uBA74 \uD589\uC744 \uD074\uB9AD\uD6C4 '\uC120\uD0DD \uCDE8\uC18C' \uBC84\uD2BC\uC744 \uB204\uB974\uC138\uC694.)");
		lblNewLabel.setBounds(85, 297, 382, 15);
		panel_1.add(lblNewLabel);
		
		
		undo2.setBounds(506, 11, 50, 30);
		panel_1.add(undo2);
		
		// search ������� �ٽ� �������� cTable�� �����ϴ� ������ Ŭ�� ��
		Image img1 = new ImageIcon(this.getClass().getResource("/undoIcon.png")).getImage();
		undo1.setIcon(new ImageIcon(img1));
		undo1.addMouseListener(new MouseAdapter() { 
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter("", 1); // ""�� �������� �˻��� ��� �����Ͱ� ��Ÿ���� �̿�
			    } catch (java.util.regex.PatternSyntaxException exception) {
			        return;
			    }
			    sorter1.setRowFilter(rf);
	        }
	    });	
		
		// search ������� �ٽ� �������� pTable�� �����ϴ� ������ Ŭ�� ��
		Image img2 = new ImageIcon(this.getClass().getResource("/undoIcon.png")).getImage();
		undo2.setIcon(new ImageIcon(img2));
		undo2.addMouseListener(new MouseAdapter() { 
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter("", 1); // ""�� �������� �˻��� ��� �����Ͱ� ��Ÿ���� �̿�
			    } catch (java.util.regex.PatternSyntaxException exception) {
			        return;
			    }
			    sorter2.setRowFilter(rf);
	        }
	    });			
				
		// �뿩�ϰ� ���� ��ǰ�� ���� Ŭ���ϸ�, ����(������ 1��) sTable�� ����
		pTable.addMouseListener(new MouseAdapter() { 
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	//���� ������ ���� ������ �Է��� ���� �̻��̸� ���â
        		if (Integer.parseInt(textCount.getText()) <= sTable.getRowCount()) {
        			JOptionPane.showMessageDialog(null,"������ �� �ִ� ��ǰ�� ������ �ʰ��Ͽ����ϴ�.");
        		} else {
        			int row = pTable.getSelectedRow();
        			String pName = (String) pTable.getValueAt(row, 0);
        			String pCode = (String) pTable.getValueAt(row, 1);
        			String pNumber = "1"; // �ϳ��� ���� ���̹Ƿ�
        			String pMoney = (String) pTable.getValueAt(row, 3);
        			String arr[] = {pName, pCode, pNumber, pMoney};
        			model3.addRow(arr);
        	    }
	        }
	    });
	}
	
	// ���� Ŭ������ ����ϴ� ����
	private class SellListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == rentBtn) // ��ǰ ��� ��ư�� ��
			{
				try {
					String arr[] = new String[9];
					arr[0] = textName.getText();
					arr[1] = textPhone.getText();
					// ��¥ ���� ��ȯ
					textReturnDate.setDateFormatString("yyyy-MM-dd");
					String returnDay = ((JTextField)textReturnDate.getDateEditor().getUiComponent()).getText();
					arr[2] = returnDay;
					Calendar getToday = Calendar.getInstance();
					String today = getToday.get(Calendar.YEAR) + "-" +getToday.get(Calendar.MONTH)+"-" +getToday.get(Calendar.DATE);
					arr[3] = today;
					arr[4] = textCount.getText();
					
					//��ǰ �߰�
					User u1;
					u1 = new User(arr[0], arr[1], arr[2], arr[3]);
					// ������ ��ǰ�� ������ ���� ����
					if (Integer.parseInt(textCount.getText()) >= 1) {
						String value1 = p1.getText(); 
						arr[5] = value1;
						int index1 = act.search(p1.getText());
						int amount = act.productAt(index1).getPrice();
						u1.addProduct(p1.getText(), amount); // �뿩 ��ǰ �ڵ� �迭�� �ڵ� �߰�
					}
					if (Integer.parseInt(textCount.getText()) >= 2) {
						String value2 = p2.getText(); 
						arr[6] = value2;
						int index2 = act.search(p2.getText());
						int amount2 = act.productAt(index2).getPrice();
						u1.addProduct(p2.getText(), amount2); // �뿩 ��ǰ �ڵ� �迭�� �ڵ� �߰�
					} 
					if (Integer.parseInt(textCount.getText()) >= 3) {
						String value3 = p3.getText(); 
						arr[7] = value3;
						int index3 = act.search(p3.getText());
						int amount3 = act.productAt(index3).getPrice();
						u1.addProduct(p3.getText(), amount3); // �뿩 ��ǰ �ڵ� �迭�� �ڵ� �߰�
					}
					
					
					act.checkIn(u1);
					// ��ǰǥ ��� ������Ʈ�ϱ� -> Sorter�� �ִµ� setValue�� ��ĥ �� ����(sort��� �÷��� setValue��� �÷��� ���� ��) => ���� �����̱⵵ ��.
					int index = act.getUserList().size() - 1;
					arr[8] = String.valueOf((act.getUserList().get(index)).pay());
					model1.addRow(arr);
				} catch(InputMismatchException exception1) {
					JOptionPane.showMessageDialog(null, "���ڰ� �ƴ� ���ڸ� �Է��� �� �����ϴ�.");
				} catch (Exception exception) { // ���� �߻�
					exception.printStackTrace();
					JOptionPane.showMessageDialog(null,"�߸��� �뿩 ����Դϴ�.");
				}
				
			} else if (e.getSource() == returnBtn) { // ��ǰ �ݳ� ��ư�� ��
				try {
					int row = cTable.getSelectedRow();
					Object value = cTable.getValueAt(row, 1);
					int input = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "Ȯ�� �޽���", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (input == 0) // ����� ������ ��� (��ǰ ����)
					{
						if (row == -1) // ã�� ���� ������ return
							return;
						// ���� ã�Ƽ�
						int index = act.searchUser((String) value);
						int money = act.userAt(index).pay();
						int input2 = JOptionPane.showConfirmDialog(null, "�� �ݾ��� " + money + "�Դϴ�. �����Ͻðڽ��ϱ�?", "Ȯ�� �޽���", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if(input2 == 0) {
							model1.removeRow(row); // �� ����
							act.checkOut(index);
							// �̶� ���⵵ ����
							revenue.setText(Integer.toString(act.getRevenue()));
						} else {
							JOptionPane.showMessageDialog(null,"üũ�ƿ��� ����մϴ�.");
						}
					}
				}  catch(Exception exception){ // ���� �߻�
					JOptionPane.showMessageDialog(null,"üũ�ƿ��� �� �����ϴ�.");
				}
				
			} else if (e.getSource() == searchBtn1) { // �� �˻� ��ư�� ��
				// �� ã��
				RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter(textSearch1.getText(), 0); // tSearch�� �̸��������� ã��
			    } catch (Exception exception) { 
			    	JOptionPane.showMessageDialog(null,"��ǰ�� ã�� �� �����ϴ�.");
			    }
			    sorter1.setRowFilter(rf);
			} else if (e.getSource() == searchBtn2) { // ��ǰ �˻� ��ư�� ��
				// ��ǰ ã��
				RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter(textSearch2.getText(), 0); // tSearch�� ���� ��ǰ �̸�(0)�������� ã��
			    } catch (Exception exception) { 
			    	JOptionPane.showMessageDialog(null,"��ǰ�� ã�� �� �����ϴ�.");
			    }
			    sorter2.setRowFilter(rf);
			} else if (e.getSource() == confirmBtn) {
				//�� ��ǰ�� �����ͼ�(�뿩�ϱ�� �� ������ ����)
				if(Integer.parseInt(textCount.getText()) >= 1) {
					Object value = sTable.getValueAt(0, 1);
					p1.setText((String) value);
				} 
				if(Integer.parseInt(textCount.getText()) >= 2) {
					Object value2 = sTable.getValueAt(1, 1);
					p2.setText((String) value2);
				} 
				if(Integer.parseInt(textCount.getText()) >= 3) {
					Object value3 = sTable.getValueAt(2, 1);
					p3.setText((String) value3);
				}
			} else if (e.getSource() == undoBtn) { // ��ǰ�� �� �� ����ϴ� ��ư
				try {
					int row = sTable.getSelectedRow();
					int input = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "Ȯ�� �޽���", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (input == 0) // ����� ������ ��� (��ǰ ����)
					{
						if (row == -1) // ã�� ���� ������ return
							return;
						model3.removeRow(row); // �� ����
					}
				}  catch(Exception exception){ // ���� �߻�
					JOptionPane.showMessageDialog(null,"��ǰ�� ������ �� �����ϴ�.");
				}
			}
		}
	}	
}

