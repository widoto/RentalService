import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class RentalProductJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textSearch;
	private JTable table;
	private JTable table_1;
	private JButton searchBtn = new JButton("\uAC80\uC0C9");
	private JButton okBtn = new JButton("\uC120\uD0DD \uC644\uB8CC");
	private TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RentalProductJFrame frame = new RentalProductJFrame();
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
	public RentalProductJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 563, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textSearch = new JTextField();
		textSearch.setBounds(149, 59, 143, 21);
		contentPane.add(textSearch);
		textSearch.setColumns(10);
		
		
		searchBtn.setBounds(304, 58, 60, 23);
		contentPane.add(searchBtn);
		
		table = new JTable();
		table.setBounds(0, 0, 1, 1);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 90, 500, 100);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("[\uC0C1\uD488\uBAA9\uB85D]");
		lblNewLabel.setBounds(20, 62, 73, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("[\uB300\uC5EC\uD558\uB824\uB294 \uC0C1\uD488 \uBAA9\uB85D]");
		lblNewLabel_1.setBounds(20, 200, 158, 15);
		contentPane.add(lblNewLabel_1);
		
		table_1 = new JTable();
		table_1.setBounds(0, 0, 1, 1);
		
		JScrollPane scrollPane_1 = new JScrollPane(table_1);
		scrollPane_1.setBounds(20, 225, 500, 100);
		contentPane.add(scrollPane_1);
		
		
		okBtn.setBounds(227, 349, 91, 23);
		contentPane.add(okBtn);
	}
	
	private class SellListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == okBtn) // 상품 등록 버튼일 때
			{
				try {
					//긁어서 보내고 창 닫기
					
				} catch (Exception exception) { // 예외 발생
					JOptionPane.showMessageDialog(null,"잘못된 상품 등록입니다.");
				}
				
			}  else if (e.getSource() == searchBtn) { // 고객 검색 버튼일 때
				// 상품 찾기
				RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter(textSearch.getText(), 0); // tSearch의 값을 코드 컬럼(1)기준으로 찾기
			    } catch (Exception exception) { 
			    	JOptionPane.showMessageDialog(null,"상품을 찾을 수 없습니다.");
			    }
			    sorter.setRowFilter(rf);
			}}} 
}
	
