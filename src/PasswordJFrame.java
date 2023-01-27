import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import javax.swing.JButton;

public class PasswordJFrame extends JFrame { // 로그인 창입니다. 프로그램 시작 시 맨 처음 나타나는 창입니다.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField password;
	private JButton confirmBtn = new JButton("\uB85C\uADF8\uC778");
	private static String pw;
	static ObjectInputStream dis = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordJFrame frame = new PasswordJFrame();
					frame.setVisible(true); // 창 띄우기
					dis = new ObjectInputStream(new FileInputStream("password.dat")); // 객체 입력 스트림을 생성
					pw = ((Integer) dis.readInt()).toString(); // 비밀번호 불러오기
					if (dis != null) 
						dis.close();
				} catch (FileNotFoundException fnfe) { // 파일을 찾을 수 없을 때 예외처리
					// 비밀번호부터 새로 설정하도록 한다.
					FirstPasswordJFrame frame = new FirstPasswordJFrame();
					frame.setVisible(true);
				} catch (Exception e) { 
					JOptionPane.showMessageDialog(null,"프로그램 시작에 실패하였습니다.");
				} 
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PasswordJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("[\uB80C\uD0C8 \uC11C\uBE44\uC2A4 \uB85C\uADF8\uC778]");
		title.setBounds(152, 53, 120, 15);
		contentPane.add(title);
		
		password = new JTextField();
		password.setBounds(119, 100, 192, 40);
		contentPane.add(password);
		password.setColumns(10);
		
		JLabel text0 = new JLabel(">>\uBE44\uBC00\uBC88\uD638\uB97C \uC785\uB825\uD558\uC138\uC694.");
		text0.setBounds(146, 75, 149, 15);
		contentPane.add(text0);
		
		JLabel changePassword = new JLabel("\uBE44\uBC00\uBC88\uD638\uB97C \uBC14\uAFB8\uACE0 \uC2F6\uC73C\uC2E0\uAC00\uC694?");
		changePassword.setFont(new Font("굴림", Font.ITALIC, 12));
		changePassword.setForeground(Color.BLUE);
		changePassword.setBounds(129, 150, 178, 15);
		changePassword.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	ChangePasswordJFrame frame = new ChangePasswordJFrame(pw);
				frame.setVisible(true);
	        }
	    });
		contentPane.add(changePassword);
		
		confirmBtn.setBounds(168, 175, 91, 23);
		confirmBtn.addActionListener(new PasswordListener());
		contentPane.add(confirmBtn);
	}
	
	// 내부 클래스를 사용하는 버전
	private class PasswordListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == confirmBtn) // 로그인 버튼을 클릭했다면
			{	
				try {
					dis = new ObjectInputStream(new FileInputStream("password.dat")); // 객체 입력 스트림을 생성
					pw = ((Integer) dis.readInt()).toString(); // 비밀번호 불러오기
					if (dis != null) 
						dis.close();
					if(pw.equals(password.getText())) // 옳은 비밀번호를 입력했을 때
					{
						dispose(); // 현재 창을 닫고
						// HomeJFrame 창을 열기
						HomeJFrame frame = new HomeJFrame();
						frame.setVisible(true); // 창 띄우기
					} else {
						JOptionPane.showMessageDialog(null,"비밀번호가 옳지 않습니다.");
					}
				} catch (Exception exc) { 
					JOptionPane.showMessageDialog(null,"프로그램 시작에 실패하였습니다.");
				} 
			}
		}
	}	
}
