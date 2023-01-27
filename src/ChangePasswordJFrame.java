import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;

public class ChangePasswordJFrame extends JFrame { // 비밀번호를 변경하는 창입니다.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField bPassword;
	private JTextField aPassword;
	private JTextField cPassword;
	private JButton confirmBtn = new JButton("\uD655\uC778");
	ObjectOutputStream dos = null;
	private String pw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChangePasswordJFrame(String pw) {
		this.pw = pw;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel text0 = new JLabel("\uC774\uC804 \uBE44\uBC00\uBC88\uD638:");
		text0.setBounds(87, 66, 87, 15);
		contentPane.add(text0);
		
		bPassword = new JTextField();
		bPassword.setBounds(221, 55, 130, 38);
		contentPane.add(bPassword);
		bPassword.setColumns(10);
		
		JLabel text1 = new JLabel("\uC0C8\uB85C \uC124\uC815\uD560 \uBE44\uBC00\uBC88\uD638:");
		text1.setBounds(47, 127, 142, 15);
		contentPane.add(text1);
		
		aPassword = new JTextField();
		aPassword.setBounds(221, 116, 130, 38);
		contentPane.add(aPassword);
		aPassword.setColumns(10);
		
		
		confirmBtn.setFont(new Font("굴림", Font.PLAIN, 14));
		confirmBtn.setBounds(158, 230, 91, 23);
		confirmBtn.addActionListener(new Pw2Listener());
		contentPane.add(confirmBtn);
		
		JLabel text2 = new JLabel("[\uBE44\uBC00\uBC88\uD638 \uC7AC\uC124\uC815]");
		text2.setBounds(158, 21, 106, 15);
		contentPane.add(text2);
		
		JLabel text3 = new JLabel("\uB2E4\uC2DC \uD55C\uBC88 \uC785\uB825:");
		text3.setBounds(80, 178, 87, 15);
		contentPane.add(text3);
		
		cPassword = new JTextField();
		cPassword.setBounds(221, 164, 130, 38);
		contentPane.add(cPassword);
		cPassword.setColumns(10);
	}
	
	// 내부 클래스를 사용하는 버전
	private class Pw2Listener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == confirmBtn) // 확인 버튼을 클릭했다면
			{	
				try {
					if(pw.equals(bPassword.getText())) {
						int password1 = Integer.parseInt(aPassword.getText());
						int password2 = Integer.parseInt(cPassword.getText());
						// 0. 두 입력값이 네자리 숫자인지 확인
						if ( password1 / 1000 >= 10 || password1 / 1000 < 1 || password2 / 1000 >= 10 || password2 / 1000 < 1)
							JOptionPane.showMessageDialog(null,"비밀번호는 4자리 숫자로 이루어져야 합니다.");
						else {
							// 1. 두 입력값이 같은지 확인
							if(password1 == password2) // 옳은 비밀번호를 입력했을 때
							{
								// 2. 같다면 저장 후 닫기
								dos = new ObjectOutputStream(new FileOutputStream("password.dat"));
								dos.writeInt(password1);
								if (dos != null)
									dos.close();
								dispose(); // 현재 창 닫기
							} else {
								// 3. 다르다면 경고창
								JOptionPane.showMessageDialog(null,"설정한 비밀번호가 같지 않습니다.");
							}
						}
					} else {
						JOptionPane.showMessageDialog(null,"이전 비밀번호가 일치하지 않습니다.");
					}
				} catch (InputMismatchException ex2) { // 숫자가 아닌 문자를 입력
					JOptionPane.showMessageDialog(null,"비밀번호는 숫자로 이루어져야 합니다.");
				} catch (Exception ex2) { // 파일 관련 예외 // IOException, IndexOutOfBoundsException
					JOptionPane.showMessageDialog(null,"비밀번호를 설정하는데 실패했습니다.");
				}
			}
		}
	}			
}
