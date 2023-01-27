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
	private JTextField textName; // 상품 이름
	private JTextField textCode; // 상품 코드
	private JTextField textCount; // 상품 개수
	private JTextField textPrice; // 상품 가격
	private JTextField textSearch; // 검색 이름
	private JTextField textAdd; // 재고 추가
	private JTextField textMinus; // 재고 추감
	private JButton addBtn = new JButton("\uB4F1\uB85D"); // 등록 버튼
	private JButton deleteBtn = new JButton("\uC0AD\uC81C"); // 삭제 버튼
	private JButton saveBtn = new JButton("\uD30C\uC77C\uC800\uC7A5"); // 파일 저장 버튼
	private JButton searchBtn = new JButton("\uAC80\uC0C9"); // 검색 버튼
	private JButton extraAddBtn = new JButton("\uC7AC\uACE0 \uCD94\uAC00"); // 잔고 추가 버튼
	private JButton extraMinusBtn = new JButton("\uC7AC\uACE0 \uCD94\uAC10"); // 잔고 추감 버튼
	private JLabel undo = new JLabel(""); // 되돌리기 라벨
	// 컬럼 네임
	private String colNames[] = { "이름", "코드", "개수", "가격", "재고"}; 
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
					// 주석처리: asking.java에서 관리 버튼을 누르면 본 창을 띄우도록 설계하였기 때문
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
		// 매니저 넘겨받기
		this.act = act;
		// 절대 좌표로 요소 배치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 621, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 저장되어있는 productList의 상품들을 행 추가
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
		// model에 데이터를 저장하는 테이블을 생성
		table.setRowSorter(sorter);
		table.setFont(new Font("굴림", Font.PLAIN, 13));
		
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		// 상품 개수를 기준으로 오름차순 정렬
		int columnIndexToSort = 2;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
		 
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		// 스크롤이 가능하도록 설정
		table.setBounds(10, 59, 580, 181);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setSize(580, 167);
		scrollPane.setLocation(10, 73);
		contentPane.add(scrollPane);
		
		// 절대 좌표로 요소 배치
		JLabel text0 = new JLabel("[\uAD00\uB9AC]"); // [관리]
		text0.setForeground(Color.BLUE);
		text0.setFont(new Font("굴림", Font.BOLD, 15));
		text0.setBounds(10, 43, 50, 20);
		contentPane.add(text0);
		
		// 파일 저장 버튼
		saveBtn.addActionListener(new ManageListener());
		saveBtn.setBounds(490, 10, 100, 25);
		contentPane.add(saveBtn);
		
		JLabel text1 = new JLabel("\uC774\uB984:"); // 이름
		text1.setFont(new Font("굴림", Font.PLAIN, 13));
		text1.setBounds(10, 250, 40, 20);
		contentPane.add(text1);
		// 상품 이름 입력 필드
		textName = new JTextField();
		textName.setBounds(44, 250, 80, 21);
		contentPane.add(textName);
		textName.setColumns(10);
		
		JLabel text2 = new JLabel("\uCF54\uB4DC:"); // 코드
		text2.setFont(new Font("굴림", Font.PLAIN, 13));
		text2.setBounds(136, 250, 35, 20);
		contentPane.add(text2);
		// 상품 코드 입력 필드
		textCode = new JTextField();
		textCode.setBounds(173, 250, 96, 21);
		contentPane.add(textCode);
		textCode.setColumns(10);
		
		JLabel text3 = new JLabel("\uAC1C\uC218:"); // 개수
		text3.setFont(new Font("굴림", Font.PLAIN, 13));
		text3.setBounds(281, 250, 35, 20);
		contentPane.add(text3);
		// 상품 개수 입력 필드
		textCount = new JTextField();
		textCount.setBounds(315, 250, 33, 21);
		contentPane.add(textCount);
		textCount.setColumns(10);
		
		JLabel text4 = new JLabel("\uAC00\uACA9(\uC6D0):"); // 가격
		text4.setFont(new Font("굴림", Font.PLAIN, 13));
		text4.setBounds(358, 250, 53, 20);
		contentPane.add(text4);
		// 상품 가격 입력 필드
		textPrice = new JTextField();
		textPrice.setBounds(417, 250, 80, 21);
		contentPane.add(textPrice);
		textPrice.setColumns(10);
		
		// 상품 등록 버튼
		addBtn.addActionListener(new ManageListener());
		addBtn.setFont(new Font("굴림", Font.BOLD, 13));
		addBtn.setBounds(505, 250, 91, 23);
		contentPane.add(addBtn);
		
		// 상품 삭제 버튼
		deleteBtn.addActionListener(new ManageListener());
		deleteBtn.setBounds(505, 283, 91, 23);
		contentPane.add(deleteBtn);
		
		// (삭제하는 상품의 테이블 행을 클릭하세요)
		JLabel text5 = new JLabel("(\uC0AD\uC81C\uD558\uB294 \uC0C1\uD488\uC758 \uD14C\uC774\uBE14 \uD589\uC744 \uD074\uB9AD\uD558\uC138\uC694)");
		text5.setFont(new Font("굴림", Font.PLAIN, 13));
		text5.setBounds(252, 284, 258, 20);
		contentPane.add(text5);
		
		// (재고를 추가하거나 추감하고자 하는 상품의 테이블 행을 클릭하세요)
		JLabel text6 = new JLabel("(\uC7AC\uACE0\uB97C \uCD94\uAC00\uD558\uAC70\uB098 \uCD94\uAC10\uD558\uACE0 \uC2F6\uC740 \uC0C1\uD488\uC758 \uD14C\uC774\uBE14 \uD589\uC744 \uD074\uB9AD\uD558\uC138\uC694)");
		text6.setFont(new Font("굴림", Font.PLAIN, 13));
		text6.setBounds(206, 330, 400, 20);
		contentPane.add(text6);
		
		// 잔고 추가 필드
		textAdd = new JTextField();
		textAdd.setBounds(173, 351, 96, 21);
		contentPane.add(textAdd);
		textAdd.setColumns(10);
		// 잔고 추감 필드
		textMinus = new JTextField();
		textMinus.setBounds(401, 351, 96, 21);
		contentPane.add(textMinus);
		textMinus.setColumns(10);
		
		// 잔고 추가 버튼
		extraAddBtn.setBounds(281, 350, 100, 23);
		extraAddBtn.addActionListener(new ManageListener());
		contentPane.add(extraAddBtn);
		// 잔고 추감 버튼	
		extraMinusBtn.setBounds(505, 350, 91, 23);
		extraMinusBtn.addActionListener(new ManageListener());
		contentPane.add(extraMinusBtn);
		
		// 상품 찾기 필드
		textSearch = new JTextField();
		textSearch.setText("(\uC0C1\uD488 \uCF54\uB4DC\uB97C \uC785\uB825\uD558\uC138\uC694.)");
		textSearch.setBounds(193, 12, 155, 20);
		contentPane.add(textSearch);
		textSearch.setColumns(10);
		// 상품 찾기 버튼
		searchBtn.setBounds(353, 10, 60, 21);
		contentPane.add(searchBtn);
		searchBtn.addActionListener(new ManageListener());
		
		// search 결과에서 다시 원상태의 table로 복귀하는 아이콘 클릭 시
		Image img = new ImageIcon(this.getClass().getResource("/undoIcon.png")).getImage();
		undo.setIcon(new ImageIcon(img));
		undo.setBounds(555, 38, 40, 32);
		undo.addMouseListener(new MouseAdapter() { 
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter("", 1); // ""를 기준으로 검색시 모든 데이터가 나타남을 이용
			    } catch (java.util.regex.PatternSyntaxException exception) {
			        return;
			    }
			    sorter.setRowFilter(rf);
	        }
	    });
		contentPane.add(undo);
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
	}

	// 내부 클래스를 사용하는 버전
	private class ManageListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addBtn) // 상품 등록 버튼일 때
			{
				try {
					String arr[] = new String[5];
					arr[0] = textName.getText();
					arr[1] = textCode.getText();
					arr[2] = textCount.getText();
					arr[3] = textPrice.getText();
					arr[4] = textPrice.getText(); // 막 처음에는 둘이 동일하다.
					//상품 추가
					Product p1;
					p1 = new Product(arr[0], arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
					
					// 상품 추가하기
					act.add(p1);
					model.addRow(arr);
				} catch (Exception exception) { // 예외 발생
					JOptionPane.showMessageDialog(null,"잘못된 상품 등록입니다.");
				}
				
			} else if (e.getSource() == deleteBtn) { // 상품 삭제 버튼일 때
				try {
					int row = table.getSelectedRow();
					Object value = table.getValueAt(row, 1);
					int input = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "확인 메시지", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (input == 0) // 대답이 예스일 경우 (상품 삭제)
					{
						if (row == -1) // 찾는 행이 없으면 return
							return;
						model.removeRow(row); // 행 삭제
						act.delete((String) value);
					}
				}  catch(Exception exception){ // 예외 발생
					JOptionPane.showMessageDialog(null,"상품의 삭제될 수 없습니다.");
				}
				
			} else if (e.getSource() == saveBtn) { // 상품 저장 버튼일 때
				try {
					// 객체 출력 스트림을 생성
					infoFile = new ObjectOutputStream(new FileOutputStream("info.dat"));
					// Manager객체의 saveToFile메소드에게 파일 객체 전달
					act.saveToFile(infoFile);
					if (infoFile != null) // infoFile이 null이 아닌지 확인 후 스트림 닫기
						infoFile.close();
				} catch (FileNotFoundException fnfe) { // 파일 못찾음 예외 처리
					JOptionPane.showMessageDialog(null,"파일이 존재하지 않습니다.");
				} catch (IOException exception) { // 파일 입출력 예외 처리
					JOptionPane.showMessageDialog(null,"입출력이 잘못된 파일 저장입니다.");
				} catch (Exception exception) { 
					JOptionPane.showMessageDialog(null,"잘못된 파일 저장입니다.");
				}
			} else if (e.getSource() == searchBtn) { // 상품 검색 버튼일 때
				// 상품 찾기
				RowFilter<Object, Object> rf = null;
			    try {
			        rf = RowFilter.regexFilter(textSearch.getText(), 0); // tSearch의 값을 상품이름 컬럼(1)기준으로 찾기
			    } catch (Exception exception) { 
			    	JOptionPane.showMessageDialog(null,"상품을 찾을 수 없습니다.");
			    }
			    sorter.setRowFilter(rf);
			} else if (e.getSource() == extraAddBtn) {
				try {
					int extra = Integer.parseInt(textAdd.getText()); // 기입한 수량
					int row = table.getSelectedRow(); // 선택한 상품의 행
					String pCode = (String) table.getValueAt(row, 1); // 선택한 상품의 코드
					// 선택한 상품이 productList에서 가지는 위치
					int index = act.search(pCode);
					// ProductList의 상품에 잔고 추가
					act.addStock(index, extra);
					// 테이블에 나타나는 상품의 잔고도 추가
					table.setValueAt(act.productAt(index).getNumber(), row, 2); // 값, 행, 열
				} catch(InputMismatchException exception1) {
					JOptionPane.showMessageDialog(null, "숫자가 아닌 문자를 입력할 수 없습니다.");
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, "상품 재고를 추가할 수 없습니다.");
				}
				
			} else if (e.getSource() == extraMinusBtn) {
				try {
					int minus = Integer.parseInt(textMinus.getText()); // 기입한 수량
					int row = table.getSelectedRow(); // 선택한 상품의 행
					String pCode = (String) table.getValueAt(row, 1); // 선택한 상품의 코드
					// 선택한 상품이 productList에서 가지는 위치
					int index = act.search(pCode);
					// ProductList의 상품에 잔고 추감
					act.subStock(index, minus);
					// 테이블에 나타나는 상품의 잔고도 추감
					table.setValueAt(act.productAt(index).getNumber(), row, 2); // 값, 행, 열
				} catch(InputMismatchException exception1) {
					JOptionPane.showMessageDialog(null, "숫자가 아닌 문자를 입력할 수 없습니다.");
				} catch (Exception exception2) { // 재고 - minus가 0보다 작을 때 등
					JOptionPane.showMessageDialog(null, "상품 재고를 추감할 수 없습니다.");
				}
			}
		}
	}
}
