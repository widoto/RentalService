import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class HomeJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton sellBtn = new JButton("\uD310\uB9E4"); // 판매 버튼
	private JButton manageBtn = new JButton("\uAD00\uB9AC"); // 관리 버튼
	private static Manager act = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			ObjectInputStream dis = null;
			public void run() {
				try {
					// HomeJFrame frame = new HomeJFrame();
					// frame.setVisible(true); // 창 띄우기
					dis = new ObjectInputStream(new FileInputStream("info.dat")); // 객체 입력 스트림을 생성
					act = new Manager(100, 100, dis); // 매니저 생성자에게 전달
					if (dis != null) 
						dis.close();
				} catch (FileNotFoundException fnfe) { // 파일을 찾을 수 없을 때 예외처리
					int input = JOptionPane.showConfirmDialog(null, "파일을 찾을 수 없습니다. 이전에 저장한 적이 있나요?", "경고 메시지", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (input == 0) // 대답이 예스일 경우 (시스템 종료)
					{
						JOptionPane optionPane = new JOptionPane("파일을 찾을 수 없습니다. 시스템을 종료합니다.");
						JDialog dialog = optionPane.createDialog("FileNotFoundException...");
			            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			            // 0.8초동안만 메시지를 띄우기
			            Timer timer = new Timer(800, e -> dialog.setVisible(false));
			            timer.setRepeats(false);
			            timer.start();
			            dialog.setVisible(true);
			            // 시스템 종료
						System.exit(0);
					} else if (input == 1) // 대답이 노일 경우 (파일 생성)
					{
						JOptionPane.showMessageDialog(null,"완전히 새로운 파일로 시작합니다.");
						act = new Manager(100, 100);
					}
				} catch (Exception e) { // Manager로부터 전달받은 예외를 처리
					JOptionPane.showMessageDialog(null,"데이터 로드를 실패하였습니다.");
				} 
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomeJFrame() {
		// 절대 좌표 이용
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 1));
		
		JLabel lblNewLabel_1 = new JLabel("[렌탈 서비스 프로그램]");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 15));
		panel.add(lblNewLabel_1);
		
		// 어떤 모드가 필요하신가요?
		JLabel lblNewLabel = new JLabel("\uC5B4\uB5A4 \uBAA8\uB4DC\uAC00 \uD544\uC694\uD558\uC2E0\uAC00\uC694?");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		// 판매 버튼
		sellBtn.setFont(new Font("굴림", Font.PLAIN, 15));
		sellBtn.addActionListener(new AskingListener());
		panel_1.add(sellBtn);
		
		// 관리 버튼
		manageBtn.setFont(new Font("굴림", Font.PLAIN, 15));
		manageBtn.addActionListener(new AskingListener());
		panel_1.add(manageBtn);
	}

	// 내부 클래스를 사용하는 버전
	private class AskingListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == sellBtn) // 판매 버튼을 클릭했다면
			{	SellJFrame frame = new SellJFrame(act); // 판매 관리창으로 이동
				frame.setVisible(true);
			} else if (e.getSource() == manageBtn) { // 관리 버튼을 클릭했다면
				ManageJFrame frame = new ManageJFrame(act); // 판매 관리창으로 이동
				frame.setVisible(true);
			}
		}
	}	
}
