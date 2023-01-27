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
	// 고객 관련 테이블
	private String cColNames[] = { "이름", "전화번호", "대여일", "반납일", "개수", "상품1", "상품2", "상품3", "대여비"};
	private DefaultTableModel model1 = new DefaultTableModel(cColNames, 0);
	private JTable cTable = new JTable(model1);
	private TableRowSorter<TableModel> sorter1 = new TableRowSorter<>(cTable.getModel());
	// 상품 관련 테이블
	private String pColNames[] = { "이름", "코드", "개수", "가격"};
	private DefaultTableModel model2 = new DefaultTableModel(pColNames, 0);
	private JTable pTable = new JTable(model2);
	private TableRowSorter<TableModel> sorter2 = new TableRowSorter<>(pTable.getModel());
	// 내가 선택하는 상품 테이블
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
	
	// value값 = 상품 코드값을 이용하여 상품 테이블에 있는 위치를 찾아냄 -> 재고 업데이트에 사용
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
		
		// 저장되어있는 userList의 상품들을 행 추가
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
				JOptionPane.showMessageDialog(null,"데이터를 불러오는데 문제가 발생하였습니다."); // u.pay()에서 예외 발생
			}
			
		}		
		
		revenue.setText(Integer.toString(act.getRevenue()));
		// HomeJFrame으로 복귀하는 아이콘
		JLabel home = new JLabel("");
		home.setBounds(13, 15, 20, 15);
		Image imgHome = new ImageIcon(this.getClass().getResource("/homeIcon.png")).getImage();
		home.setIcon(new ImageIcon(imgHome));
		home.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	dispose(); // 현재 창 닫기
	        }
	    });
		contentPane.add(home);		
		// 판매
		JLabel text0 = new JLabel("[\uD310\uB9E4]");
		text0.setForeground(Color.BLUE);
		text0.setBounds(50, 10, 50, 20);
		text0.setBackground(Color.YELLOW);
		text0.setFont(new Font("굴림", Font.BOLD, 15));
		contentPane.add(text0);
		// 매출액
		JLabel text1 = new JLabel("\uB9E4\uCD9C\uC561\uC740");
		text1.setBounds(360, 10, 70, 20);
		text1.setFont(new Font("굴림", Font.PLAIN, 15));
		contentPane.add(text1);
		// 매출액 보이기
		revenue.setForeground(Color.RED);
		revenue.setBounds(435, 10, 70, 20);
		revenue.setHorizontalAlignment(SwingConstants.TRAILING);
		revenue.setFont(new Font("굴림", Font.BOLD, 20));
		contentPane.add(revenue);
		
		JLabel text2 = new JLabel("(\uC6D0)\uC785\uB2C8\uB2E4.");
		text2.setBounds(515, 10, 85, 20);
		text2.setFont(new Font("굴림", Font.PLAIN, 15));
		contentPane.add(text2);
		// 탭 화면 구성
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(18, 40, 573, 381);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("고객 관리", null, panel, null);
		panel.setLayout(null);
		// 고객 검색
		textSearch1 = new JTextField();
		textSearch1.setBounds(167, 10, 146, 21);
		panel.add(textSearch1);
		textSearch1.setColumns(10);
		// 검색 버튼
		searchBtn1.setBounds(322, 9, 60, 23);
		searchBtn1.addActionListener(new SellListener());
		panel.add(searchBtn1);
		
		// model에 데이터를 저장하는 테이블을 생성
		cTable.setRowSorter(sorter1);
		cTable.setFont(new Font("굴림", Font.PLAIN, 13));
		// 대여일을 기준으로 오름차순 정렬
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
		// 대여 정보 입력
		// 이름
		textName = new JTextField();
		textName.setBounds(43, 170, 60, 20);
		panel.add(textName);
		textName.setColumns(10);

		JLabel text3 = new JLabel("\uC774\uB984: ");
		text3.setBounds(10, 170, 40, 20);
		panel.add(text3);
		text3.setFont(new Font("굴림", Font.PLAIN, 13));
		
		JLabel text4 = new JLabel("\uC804\uD654\uBC88\uD638:");
		text4.setBounds(108, 170, 55, 20);
		panel.add(text4);
		text4.setFont(new Font("굴림", Font.PLAIN, 13));
		// 전화번호
		textPhone = new JTextField();
		textPhone.setBounds(168, 170, 96, 21);
		panel.add(textPhone);
		textPhone.setText("(-\uBE7C\uACE0 \uC785\uB825)");
		textPhone.setColumns(10);
		// 반납일
		JLabel text5 = new JLabel("\uBC18\uB0A9\uC77C:");
		text5.setBounds(268, 170, 50, 20);
		panel.add(text5);
		text5.setFont(new Font("굴림", Font.PLAIN, 13));
		textReturnDate.setBounds(314, 170, 95, 20);
		panel.add(textReturnDate);
		// 대여수
		JLabel text6 = new JLabel("\uB300\uC5EC\uC218(\uCD5C\uB3003\uAC1C):");
		text6.setBounds(413, 170, 110, 20);
		panel.add(text6);
		text6.setFont(new Font("굴림", Font.PLAIN, 13));
		
		textCount = new JTextField();
		textCount.setBounds(516, 170, 40, 21);
		panel.add(textCount);
		textCount.setColumns(10);
		// 대여 버튼
		JLabel text7 = new JLabel("(\uC0C1\uD488 \uBAA9\uB85D\uC5D0\uC11C \uB300\uC5EC\uD558\uACE0\uC790 \uD558\uB294 \uC0C1\uD488\uC744 \uC120\uD0DD\uD558\uC138\uC694)");
		text7.setBounds(275, 225, 293, 20);
		panel.add(text7);
		rentBtn.setBounds(480, 287, 80, 23);
		rentBtn.addActionListener(new SellListener());
		panel.add(rentBtn);
		// 반납 버튼
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
		// 선택한 상품1, 상품2, 상품3
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
		// 상품 검색
		textSearch2 = new JTextField();
		textSearch2.setBounds(167, 10, 146, 21);
		panel_1.add(textSearch2);
		textSearch2.setColumns(10);
		// 검색 버튼
		searchBtn2.setBounds(322, 9, 60, 23);
		searchBtn2.addActionListener(new SellListener());
		panel_1.add(searchBtn2);
		
		// 저장되어있는 productList의 상품들을 행 추가(데이터 로드)
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
		
		
		// model에 데이터를 저장하는 테이블을 생성
		pTable.setRowSorter(sorter2);
		pTable.setFont(new Font("굴림", Font.PLAIN, 13));
		
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		// 현재날까를 기준으로 오름차순 정렬
		int columnIndexToSort2 = 2;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort2, SortOrder.ASCENDING));
		 
		sorter2.setSortKeys(sortKeys);
		sorter2.sort();		
				
		pTable.setBounds(11, 45, 545, 110);
		JScrollPane scrollPane = new JScrollPane(pTable);
		scrollPane.setSize(550, 120);
		scrollPane.setLocation(10, 40);
		panel_1.add(scrollPane);
		
		JLabel text10 = new JLabel("(대여하려는 상품의 개수만큼 선택하시오)");
		text10.setBounds(12, 170, 301, 15);
		panel_1.add(text10);
		// 확인 버튼
		confirmBtn.setBounds(469, 319, 91, 23);
		confirmBtn.addActionListener(new SellListener());
		panel_1.add(confirmBtn);
		
		sTable.setBounds(0, 0, 1, 1);
		JScrollPane scrollPane_1 = new JScrollPane(sTable);
		scrollPane_1.setBounds(10, 193, 550, 90);
		panel_1.add(scrollPane_1);
		
		// model에 데이터를 저장하는 테이블을 생성
		sTable.setRowSorter(sorter3);
		sTable.setFont(new Font("굴림", Font.PLAIN, 13));
		// 개수 기준으로 오름차순 정렬
		List<RowSorter.SortKey> sortKeys3 = new ArrayList<>();
		int columnIndexToSort3 = 1;
		sortKeys3.add(new RowSorter.SortKey(columnIndexToSort3, SortOrder.ASCENDING));
		 
		sorter1.setSortKeys(sortKeys1);
		sorter1.sort();		
				
		// 검색 취소 버튼
		undoBtn.setBounds(469, 293, 91, 23);
		undoBtn.addActionListener(new SellListener());
		panel_1.add(undoBtn);
		
		JLabel lblNewLabel = new JLabel("(\uC120\uD0DD\uD55C \uC0C1\uD488\uC744 \uCDE8\uC18C\uD558\uB824\uBA74 \uD589\uC744 \uD074\uB9AD\uD6C4 '\uC120\uD0DD \uCDE8\uC18C' \uBC84\uD2BC\uC744 \uB204\uB974\uC138\uC694.)");
		lblNewLabel.setBounds(85, 297, 382, 15);
		panel_1.add(lblNewLabel);
		
		
		undo2.setBounds(506, 11, 50, 30);
		panel_1.add(undo2);
		
		// search 결과에서 다시 원상태의 cTable로 복귀하는 아이콘 클릭 시
		Image img1 = new ImageIcon(this.getClass().getResource("/undoIcon.png")).getImage();
		undo1.setIcon(new ImageIcon(img1));
		undo1.addMouseListener(new MouseAdapter() { 
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter("", 1); // ""를 기준으로 검색시 모든 데이터가 나타남을 이용
			    } catch (java.util.regex.PatternSyntaxException exception) {
			        return;
			    }
			    sorter1.setRowFilter(rf);
	        }
	    });	
		
		// search 결과에서 다시 원상태의 pTable로 복귀하는 아이콘 클릭 시
		Image img2 = new ImageIcon(this.getClass().getResource("/undoIcon.png")).getImage();
		undo2.setIcon(new ImageIcon(img2));
		undo2.addMouseListener(new MouseAdapter() { 
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter("", 1); // ""를 기준으로 검색시 모든 데이터가 나타남을 이용
			    } catch (java.util.regex.PatternSyntaxException exception) {
			        return;
			    }
			    sorter2.setRowFilter(rf);
	        }
	    });			
				
		// 대여하고 싶은 상품의 행을 클릭하면, 행을(개수는 1개) sTable에 더함
		pTable.addMouseListener(new MouseAdapter() { 
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	//만약 선택한 행의 개수가 입력한 개수 이상이면 경고창
        		if (Integer.parseInt(textCount.getText()) <= sTable.getRowCount()) {
        			JOptionPane.showMessageDialog(null,"선택할 수 있는 상품의 개수를 초과하였습니다.");
        		} else {
        			int row = pTable.getSelectedRow();
        			String pName = (String) pTable.getValueAt(row, 0);
        			String pCode = (String) pTable.getValueAt(row, 1);
        			String pNumber = "1"; // 하나씩 담을 것이므로
        			String pMoney = (String) pTable.getValueAt(row, 3);
        			String arr[] = {pName, pCode, pNumber, pMoney};
        			model3.addRow(arr);
        	    }
	        }
	    });
	}
	
	// 내부 클래스를 사용하는 버전
	private class SellListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == rentBtn) // 상품 등록 버튼일 때
			{
				try {
					String arr[] = new String[9];
					arr[0] = textName.getText();
					arr[1] = textPhone.getText();
					// 날짜 형식 변환
					textReturnDate.setDateFormatString("yyyy-MM-dd");
					String returnDay = ((JTextField)textReturnDate.getDateEditor().getUiComponent()).getText();
					arr[2] = returnDay;
					Calendar getToday = Calendar.getInstance();
					String today = getToday.get(Calendar.YEAR) + "-" +getToday.get(Calendar.MONTH)+"-" +getToday.get(Calendar.DATE);
					arr[3] = today;
					arr[4] = textCount.getText();
					
					//상품 추가
					User u1;
					u1 = new User(arr[0], arr[1], arr[2], arr[3]);
					// 선택한 상품의 개수에 따라 구분
					if (Integer.parseInt(textCount.getText()) >= 1) {
						String value1 = p1.getText(); 
						arr[5] = value1;
						int index1 = act.search(p1.getText());
						int amount = act.productAt(index1).getPrice();
						u1.addProduct(p1.getText(), amount); // 대여 물품 코드 배열에 코드 추가
					}
					if (Integer.parseInt(textCount.getText()) >= 2) {
						String value2 = p2.getText(); 
						arr[6] = value2;
						int index2 = act.search(p2.getText());
						int amount2 = act.productAt(index2).getPrice();
						u1.addProduct(p2.getText(), amount2); // 대여 물품 코드 배열에 코드 추가
					} 
					if (Integer.parseInt(textCount.getText()) >= 3) {
						String value3 = p3.getText(); 
						arr[7] = value3;
						int index3 = act.search(p3.getText());
						int amount3 = act.productAt(index3).getPrice();
						u1.addProduct(p3.getText(), amount3); // 대여 물품 코드 배열에 코드 추가
					}
					
					
					act.checkIn(u1);
					// 상품표 재고 업데이트하기 -> Sorter가 있는데 setValue로 고칠 수 없다(sort대상 컬럼과 setValue대상 컬럼이 같을 때) => 버전 문제이기도 함.
					int index = act.getUserList().size() - 1;
					arr[8] = String.valueOf((act.getUserList().get(index)).pay());
					model1.addRow(arr);
				} catch(InputMismatchException exception1) {
					JOptionPane.showMessageDialog(null, "숫자가 아닌 문자를 입력할 수 없습니다.");
				} catch (Exception exception) { // 예외 발생
					exception.printStackTrace();
					JOptionPane.showMessageDialog(null,"잘못된 대여 방법입니다.");
				}
				
			} else if (e.getSource() == returnBtn) { // 상품 반납 버튼일 때
				try {
					int row = cTable.getSelectedRow();
					Object value = cTable.getValueAt(row, 1);
					int input = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "확인 메시지", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (input == 0) // 대답이 예스일 경우 (상품 삭제)
					{
						if (row == -1) // 찾는 행이 없으면 return
							return;
						// 유저 찾아서
						int index = act.searchUser((String) value);
						int money = act.userAt(index).pay();
						int input2 = JOptionPane.showConfirmDialog(null, "총 금액은 " + money + "입니다. 결제하시겠습니까?", "확인 메시지", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if(input2 == 0) {
							model1.removeRow(row); // 행 삭제
							act.checkOut(index);
							// 이때 매출도 저장
							revenue.setText(Integer.toString(act.getRevenue()));
						} else {
							JOptionPane.showMessageDialog(null,"체크아웃을 취소합니다.");
						}
					}
				}  catch(Exception exception){ // 예외 발생
					JOptionPane.showMessageDialog(null,"체크아웃할 수 없습니다.");
				}
				
			} else if (e.getSource() == searchBtn1) { // 고객 검색 버튼일 때
				// 고객 찾기
				RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter(textSearch1.getText(), 0); // tSearch의 이름기준으로 찾기
			    } catch (Exception exception) { 
			    	JOptionPane.showMessageDialog(null,"상품을 찾을 수 없습니다.");
			    }
			    sorter1.setRowFilter(rf);
			} else if (e.getSource() == searchBtn2) { // 상품 검색 버튼일 때
				// 상품 찾기
				RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter(textSearch2.getText(), 0); // tSearch의 값을 상품 이름(0)기준으로 찾기
			    } catch (Exception exception) { 
			    	JOptionPane.showMessageDialog(null,"상품을 찾을 수 없습니다.");
			    }
			    sorter2.setRowFilter(rf);
			} else if (e.getSource() == confirmBtn) {
				//세 상품을 가져와서(대여하기로 한 개수에 따라)
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
			} else if (e.getSource() == undoBtn) { // 상품을 고를 때 취소하는 버튼
				try {
					int row = sTable.getSelectedRow();
					int input = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "확인 메시지", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (input == 0) // 대답이 예스일 경우 (상품 삭제)
					{
						if (row == -1) // 찾는 행이 없으면 return
							return;
						model3.removeRow(row); // 행 삭제
					}
				}  catch(Exception exception){ // 예외 발생
					JOptionPane.showMessageDialog(null,"상품의 삭제될 수 없습니다.");
				}
			}
		}
	}	
}

