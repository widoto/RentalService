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

public class PasswordJFrame extends JFrame { // �α��� â�Դϴ�. ���α׷� ���� �� �� ó�� ��Ÿ���� â�Դϴ�.

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
					frame.setVisible(true); // â ����
					dis = new ObjectInputStream(new FileInputStream("password.dat")); // ��ü �Է� ��Ʈ���� ����
					pw = ((Integer) dis.readInt()).toString(); // ��й�ȣ �ҷ�����
					if (dis != null) 
						dis.close();
				} catch (FileNotFoundException fnfe) { // ������ ã�� �� ���� �� ����ó��
					// ��й�ȣ���� ���� �����ϵ��� �Ѵ�.
					FirstPasswordJFrame frame = new FirstPasswordJFrame();
					frame.setVisible(true);
				} catch (Exception e) { 
					JOptionPane.showMessageDialog(null,"���α׷� ���ۿ� �����Ͽ����ϴ�.");
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
		changePassword.setFont(new Font("����", Font.ITALIC, 12));
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
	
	// ���� Ŭ������ ����ϴ� ����
	private class PasswordListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == confirmBtn) // �α��� ��ư�� Ŭ���ߴٸ�
			{	
				try {
					dis = new ObjectInputStream(new FileInputStream("password.dat")); // ��ü �Է� ��Ʈ���� ����
					pw = ((Integer) dis.readInt()).toString(); // ��й�ȣ �ҷ�����
					if (dis != null) 
						dis.close();
					if(pw.equals(password.getText())) // ���� ��й�ȣ�� �Է����� ��
					{
						dispose(); // ���� â�� �ݰ�
						// HomeJFrame â�� ����
						HomeJFrame frame = new HomeJFrame();
						frame.setVisible(true); // â ����
					} else {
						JOptionPane.showMessageDialog(null,"��й�ȣ�� ���� �ʽ��ϴ�.");
					}
				} catch (Exception exc) { 
					JOptionPane.showMessageDialog(null,"���α׷� ���ۿ� �����Ͽ����ϴ�.");
				} 
			}
		}
	}	
}
