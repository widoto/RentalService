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

public class ChangePasswordJFrame extends JFrame { // ��й�ȣ�� �����ϴ� â�Դϴ�.

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
		
		
		confirmBtn.setFont(new Font("����", Font.PLAIN, 14));
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
	
	// ���� Ŭ������ ����ϴ� ����
	private class Pw2Listener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == confirmBtn) // Ȯ�� ��ư�� Ŭ���ߴٸ�
			{	
				try {
					if(pw.equals(bPassword.getText())) {
						int password1 = Integer.parseInt(aPassword.getText());
						int password2 = Integer.parseInt(cPassword.getText());
						// 0. �� �Է°��� ���ڸ� �������� Ȯ��
						if ( password1 / 1000 >= 10 || password1 / 1000 < 1 || password2 / 1000 >= 10 || password2 / 1000 < 1)
							JOptionPane.showMessageDialog(null,"��й�ȣ�� 4�ڸ� ���ڷ� �̷������ �մϴ�.");
						else {
							// 1. �� �Է°��� ������ Ȯ��
							if(password1 == password2) // ���� ��й�ȣ�� �Է����� ��
							{
								// 2. ���ٸ� ���� �� �ݱ�
								dos = new ObjectOutputStream(new FileOutputStream("password.dat"));
								dos.writeInt(password1);
								if (dos != null)
									dos.close();
								dispose(); // ���� â �ݱ�
							} else {
								// 3. �ٸ��ٸ� ���â
								JOptionPane.showMessageDialog(null,"������ ��й�ȣ�� ���� �ʽ��ϴ�.");
							}
						}
					} else {
						JOptionPane.showMessageDialog(null,"���� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					}
				} catch (InputMismatchException ex2) { // ���ڰ� �ƴ� ���ڸ� �Է�
					JOptionPane.showMessageDialog(null,"��й�ȣ�� ���ڷ� �̷������ �մϴ�.");
				} catch (Exception ex2) { // ���� ���� ���� // IOException, IndexOutOfBoundsException
					JOptionPane.showMessageDialog(null,"��й�ȣ�� �����ϴµ� �����߽��ϴ�.");
				}
			}
		}
	}			
}
