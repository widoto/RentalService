import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ManageJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textName; // ��ǰ �̸�
	private JTextField textCode; // ��ǰ �ڵ�
	private JTextField textCount; // ��ǰ ����
	private JTextField textPrice; // ��ǰ ����
	private JTextField textSearch; // �˻� �̸�
	private JTextField textAdd; // ��� �߰�
	private JTextField textMinus; // ��� �߰�
	private JButton addBtn = new JButton("\uB4F1\uB85D"); // ��� ��ư
	private JButton deleteBtn = new JButton("\uC0AD\uC81C"); // ���� ��ư
	private JButton saveBtn = new JButton("\uD30C\uC77C\uC800\uC7A5"); // ���� ���� ��ư
	private JButton searchBtn = new JButton("\uAC80\uC0C9"); // �˻� ��ư
	private JButton extraAddBtn = new JButton("\uC7AC\uACE0 \uCD94\uAC00"); // �ܰ� �߰� ��ư
	private JButton extraMinusBtn = new JButton("\uC7AC\uACE0 \uCD94\uAC10"); // �ܰ� �߰� ��ư
	private JLabel undo = new JLabel(""); // �ǵ����� ��
	// �÷� ����
	private String colNames[] = { "�̸�", "�ڵ�", "����", "����", "���"}; 
	private DefaultTableModel model = new DefaultTableModel(colNames, 0);
	private JTable table = new JTable(model);
	private TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
	ObjectOutputStream infoFile = null; 
	Manager act = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// �ּ�ó��: asking.java���� ���� ��ư�� ������ �� â�� ��쵵�� �����Ͽ��� ����
					// ManageJFrame frame = new ManageJFrame();
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
	public ManageJFrame(Manager act) {
		// �Ŵ��� �Ѱܹޱ�
		this.act = act;
		// ���� ��ǥ�� ��� ��ġ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 621, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// ����Ǿ��ִ� productList�� ��ǰ���� �� �߰�
		int pCount = (act.getProductList()).size();
		for	(int i=0; i< pCount; i++)
		{
			Product p = act.getProductList().get(i);
			String arr[] = new String[4];
			arr[0] = p.getName();
			arr[1] = p.getCode();
			arr[2] = Integer.toString(p.getNumber());
			arr[3] = Integer.toString(p.getPrice()); 
			model.addRow(arr); 
		}
		// model�� �����͸� �����ϴ� ���̺��� ����
		table.setRowSorter(sorter);
		table.setFont(new Font("����", Font.PLAIN, 13));
		
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		// ��ǰ ������ �������� �������� ����
		int columnIndexToSort = 2;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
		 
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		// ��ũ���� �����ϵ��� ����
		table.setBounds(10, 59, 580, 181);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setSize(580, 167);
		scrollPane.setLocation(10, 73);
		contentPane.add(scrollPane);
		
		// ���� ��ǥ�� ��� ��ġ
		JLabel text0 = new JLabel("[\uAD00\uB9AC]"); // [����]
		text0.setForeground(Color.BLUE);
		text0.setFont(new Font("����", Font.BOLD, 15));
		text0.setBounds(10, 43, 50, 20);
		contentPane.add(text0);
		
		// ���� ���� ��ư
		saveBtn.addActionListener(new ManageListener());
		saveBtn.setBounds(490, 10, 100, 25);
		contentPane.add(saveBtn);
		
		JLabel text1 = new JLabel("\uC774\uB984:"); // �̸�
		text1.setFont(new Font("����", Font.PLAIN, 13));
		text1.setBounds(10, 250, 40, 20);
		contentPane.add(text1);
		// ��ǰ �̸� �Է� �ʵ�
		textName = new JTextField();
		textName.setBounds(44, 250, 80, 21);
		contentPane.add(textName);
		textName.setColumns(10);
		
		JLabel text2 = new JLabel("\uCF54\uB4DC:"); // �ڵ�
		text2.setFont(new Font("����", Font.PLAIN, 13));
		text2.setBounds(136, 250, 35, 20);
		contentPane.add(text2);
		// ��ǰ �ڵ� �Է� �ʵ�
		textCode = new JTextField();
		textCode.setBounds(173, 250, 96, 21);
		contentPane.add(textCode);
		textCode.setColumns(10);
		
		JLabel text3 = new JLabel("\uAC1C\uC218:"); // ����
		text3.setFont(new Font("����", Font.PLAIN, 13));
		text3.setBounds(281, 250, 35, 20);
		contentPane.add(text3);
		// ��ǰ ���� �Է� �ʵ�
		textCount = new JTextField();
		textCount.setBounds(315, 250, 33, 21);
		contentPane.add(textCount);
		textCount.setColumns(10);
		
		JLabel text4 = new JLabel("\uAC00\uACA9(\uC6D0):"); // ����
		text4.setFont(new Font("����", Font.PLAIN, 13));
		text4.setBounds(358, 250, 53, 20);
		contentPane.add(text4);
		// ��ǰ ���� �Է� �ʵ�
		textPrice = new JTextField();
		textPrice.setBounds(417, 250, 80, 21);
		contentPane.add(textPrice);
		textPrice.setColumns(10);
		
		// ��ǰ ��� ��ư
		addBtn.addActionListener(new ManageListener());
		addBtn.setFont(new Font("����", Font.BOLD, 13));
		addBtn.setBounds(505, 250, 91, 23);
		contentPane.add(addBtn);
		
		// ��ǰ ���� ��ư
		deleteBtn.addActionListener(new ManageListener());
		deleteBtn.setBounds(505, 283, 91, 23);
		contentPane.add(deleteBtn);
		
		// (�����ϴ� ��ǰ�� ���̺� ���� Ŭ���ϼ���)
		JLabel text5 = new JLabel("(\uC0AD\uC81C\uD558\uB294 \uC0C1\uD488\uC758 \uD14C\uC774\uBE14 \uD589\uC744 \uD074\uB9AD\uD558\uC138\uC694)");
		text5.setFont(new Font("����", Font.PLAIN, 13));
		text5.setBounds(252, 284, 258, 20);
		contentPane.add(text5);
		
		// (��� �߰��ϰų� �߰��ϰ��� �ϴ� ��ǰ�� ���̺� ���� Ŭ���ϼ���)
		JLabel text6 = new JLabel("(\uC7AC\uACE0\uB97C \uCD94\uAC00\uD558\uAC70\uB098 \uCD94\uAC10\uD558\uACE0 \uC2F6\uC740 \uC0C1\uD488\uC758 \uD14C\uC774\uBE14 \uD589\uC744 \uD074\uB9AD\uD558\uC138\uC694)");
		text6.setFont(new Font("����", Font.PLAIN, 13));
		text6.setBounds(206, 330, 400, 20);
		contentPane.add(text6);
		
		// �ܰ� �߰� �ʵ�
		textAdd = new JTextField();
		textAdd.setBounds(173, 351, 96, 21);
		contentPane.add(textAdd);
		textAdd.setColumns(10);
		// �ܰ� �߰� �ʵ�
		textMinus = new JTextField();
		textMinus.setBounds(401, 351, 96, 21);
		contentPane.add(textMinus);
		textMinus.setColumns(10);
		
		// �ܰ� �߰� ��ư
		extraAddBtn.setBounds(281, 350, 100, 23);
		extraAddBtn.addActionListener(new ManageListener());
		contentPane.add(extraAddBtn);
		// �ܰ� �߰� ��ư	
		extraMinusBtn.setBounds(505, 350, 91, 23);
		extraMinusBtn.addActionListener(new ManageListener());
		contentPane.add(extraMinusBtn);
		
		// ��ǰ ã�� �ʵ�
		textSearch = new JTextField();
		textSearch.setText("(\uC0C1\uD488 \uCF54\uB4DC\uB97C \uC785\uB825\uD558\uC138\uC694.)");
		textSearch.setBounds(193, 12, 155, 20);
		contentPane.add(textSearch);
		textSearch.setColumns(10);
		// ��ǰ ã�� ��ư
		searchBtn.setBounds(353, 10, 60, 21);
		contentPane.add(searchBtn);
		searchBtn.addActionListener(new ManageListener());
		
		// search ������� �ٽ� �������� table�� �����ϴ� ������ Ŭ�� ��
		Image img = new ImageIcon(this.getClass().getResource("/undoIcon.png")).getImage();
		undo.setIcon(new ImageIcon(img));
		undo.setBounds(555, 38, 40, 32);
		undo.addMouseListener(new MouseAdapter() { 
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter("", 1); // ""�� �������� �˻��� ��� �����Ͱ� ��Ÿ���� �̿�
			    } catch (java.util.regex.PatternSyntaxException exception) {
			        return;
			    }
			    sorter.setRowFilter(rf);
	        }
	    });
		contentPane.add(undo);
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
	}

	// ���� Ŭ������ ����ϴ� ����
	private class ManageListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addBtn) // ��ǰ ��� ��ư�� ��
			{
				try {
					String arr[] = new String[5];
					arr[0] = textName.getText();
					arr[1] = textCode.getText();
					arr[2] = textCount.getText();
					arr[3] = textPrice.getText();
					arr[4] = textPrice.getText(); // �� ó������ ���� �����ϴ�.
					//��ǰ �߰�
					Product p1;
					p1 = new Product(arr[0], arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
					
					// ��ǰ �߰��ϱ�
					act.add(p1);
					model.addRow(arr);
				} catch (Exception exception) { // ���� �߻�
					JOptionPane.showMessageDialog(null,"�߸��� ��ǰ ����Դϴ�.");
				}
				
			} else if (e.getSource() == deleteBtn) { // ��ǰ ���� ��ư�� ��
				try {
					int row = table.getSelectedRow();
					Object value = table.getValueAt(row, 1);
					int input = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "Ȯ�� �޽���", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (input == 0) // ����� ������ ��� (��ǰ ����)
					{
						if (row == -1) // ã�� ���� ������ return
							return;
						model.removeRow(row); // �� ����
						act.delete((String) value);
					}
				}  catch(Exception exception){ // ���� �߻�
					JOptionPane.showMessageDialog(null,"��ǰ�� ������ �� �����ϴ�.");
				}
				
			} else if (e.getSource() == saveBtn) { // ��ǰ ���� ��ư�� ��
				try {
					// ��ü ��� ��Ʈ���� ����
					infoFile = new ObjectOutputStream(new FileOutputStream("info.dat"));
					// Manager��ü�� saveToFile�޼ҵ忡�� ���� ��ü ����
					act.saveToFile(infoFile);
					if (infoFile != null) // infoFile�� null�� �ƴ��� Ȯ�� �� ��Ʈ�� �ݱ�
						infoFile.close();
				} catch (FileNotFoundException fnfe) { // ���� ��ã�� ���� ó��
					JOptionPane.showMessageDialog(null,"������ �������� �ʽ��ϴ�.");
				} catch (IOException exception) { // ���� ����� ���� ó��
					JOptionPane.showMessageDialog(null,"������� �߸��� ���� �����Դϴ�.");
				} catch (Exception exception) { 
					JOptionPane.showMessageDialog(null,"�߸��� ���� �����Դϴ�.");
				}
			} else if (e.getSource() == searchBtn) { // ��ǰ �˻� ��ư�� ��
				// ��ǰ ã��
				RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter(textSearch.getText(), 0); // tSearch�� ���� ��ǰ�̸� �÷�(1)�������� ã��
			    } catch (Exception exception) { 
			    	JOptionPane.showMessageDialog(null,"��ǰ�� ã�� �� �����ϴ�.");
			    }
			    sorter.setRowFilter(rf);
			} else if (e.getSource() == extraAddBtn) {
				try {
					int extra = Integer.parseInt(textAdd.getText()); // ������ ����
					int row = table.getSelectedRow(); // ������ ��ǰ�� ��
					String pCode = (String) table.getValueAt(row, 1); // ������ ��ǰ�� �ڵ�
					// ������ ��ǰ�� productList���� ������ ��ġ
					int index = act.search(pCode);
					// ProductList�� ��ǰ�� �ܰ� �߰�
					act.addStock(index, extra);
					// ���̺� ��Ÿ���� ��ǰ�� �ܰ� �߰�
					table.setValueAt(act.productAt(index).getNumber(), row, 2); // ��, ��, ��
				} catch(InputMismatchException exception1) {
					JOptionPane.showMessageDialog(null, "���ڰ� �ƴ� ���ڸ� �Է��� �� �����ϴ�.");
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, "��ǰ ��� �߰��� �� �����ϴ�.");
				}
				
			} else if (e.getSource() == extraMinusBtn) {
				try {
					int minus = Integer.parseInt(textMinus.getText()); // ������ ����
					int row = table.getSelectedRow(); // ������ ��ǰ�� ��
					String pCode = (String) table.getValueAt(row, 1); // ������ ��ǰ�� �ڵ�
					// ������ ��ǰ�� productList���� ������ ��ġ
					int index = act.search(pCode);
					// ProductList�� ��ǰ�� �ܰ� �߰�
					act.subStock(index, minus);
					// ���̺� ��Ÿ���� ��ǰ�� �ܰ� �߰�
					table.setValueAt(act.productAt(index).getNumber(), row, 2); // ��, ��, ��
				} catch(InputMismatchException exception1) {
					JOptionPane.showMessageDialog(null, "���ڰ� �ƴ� ���ڸ� �Է��� �� �����ϴ�.");
				} catch (Exception exception2) { // ��� - minus�� 0���� ���� �� ��
					JOptionPane.showMessageDialog(null, "��ǰ ��� �߰��� �� �����ϴ�.");
				}
			}
		}
	}
}
