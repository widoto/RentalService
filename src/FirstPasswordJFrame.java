
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class FirstPasswordJFrame extends JFrame { // 비밀번호를 최초로 설정하는 창입니다.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField pw1;
	private JTextField pw2;
	private JButton confirmBtn = new JButton("\uD655\uC778");
	ObjectOutputStream dos = null;
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
	public FirstPasswordJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel text = new JLabel("\uC22B\uC790 \uB124\uC790\uB9AC \uBE44\uBC00\uBC88\uD638\uB97C \uC785\uB825\uD558\uC138\uC694");
		text.setBounds(111, 58, 200, 15);
		contentPane.add(text);
		
		pw1 = new JTextField();
		pw1.setBounds(142, 83, 116, 30);
		contentPane.add(pw1);
		pw1.setColumns(10);
		
		JLabel text1 = new JLabel("\uB2E4\uC2DC \uD55C\uBC88 \uC785\uB825\uD558\uC138\uC694");
		text1.setBounds(145, 132, 116, 15);
		contentPane.add(text1);
		
		pw2 = new JTextField();
		pw2.setBounds(142, 155, 116, 30);
		contentPane.add(pw2);
		pw2.setColumns(10);
		
		
		confirmBtn.addActionListener(new PwListener());
		confirmBtn.setBounds(154, 206, 91, 23);
		
		contentPane.add(confirmBtn);
		
		JLabel text2 = new JLabel("[\uBE44\uBC00\uBC88\uD638 \uC124\uC815]");
		text2.setBounds(154, 22, 91, 15);
		contentPane.add(text2);
	}
	
	// 내부 클래스를 사용하는 버전
	private class PwListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == confirmBtn) // 확인 버튼을 클릭했다면
			{	
				try {
					int password1 = Integer.parseInt(pw1.getText());
					int password2 = Integer.parseInt(pw2.getText());
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
				} catch (Exception ex2) { // 파일 관련 예외 // IOException, IndexOutOfBoundsException
					JOptionPane.showMessageDialog(null,"비밀번호를 설정하는데 실패했습니다.");
				}
			}
		}
	}		
}
