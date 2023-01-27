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
	private JButton sellBtn = new JButton("\uD310\uB9E4"); // �Ǹ� ��ư
	private JButton manageBtn = new JButton("\uAD00\uB9AC"); // ���� ��ư
	private static Manager act = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			ObjectInputStream dis = null;
			public void run() {
				try {
					// HomeJFrame frame = new HomeJFrame();
					// frame.setVisible(true); // â ����
					dis = new ObjectInputStream(new FileInputStream("info.dat")); // ��ü �Է� ��Ʈ���� ����
					act = new Manager(100, 100, dis); // �Ŵ��� �����ڿ��� ����
					if (dis != null) 
						dis.close();
				} catch (FileNotFoundException fnfe) { // ������ ã�� �� ���� �� ����ó��
					int input = JOptionPane.showConfirmDialog(null, "������ ã�� �� �����ϴ�. ������ ������ ���� �ֳ���?", "��� �޽���", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (input == 0) // ����� ������ ��� (�ý��� ����)
					{
						JOptionPane optionPane = new JOptionPane("������ ã�� �� �����ϴ�. �ý����� �����մϴ�.");
						JDialog dialog = optionPane.createDialog("FileNotFoundException...");
			            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			            // 0.8�ʵ��ȸ� �޽����� ����
			            Timer timer = new Timer(800, e -> dialog.setVisible(false));
			            timer.setRepeats(false);
			            timer.start();
			            dialog.setVisible(true);
			            // �ý��� ����
						System.exit(0);
					} else if (input == 1) // ����� ���� ��� (���� ����)
					{
						JOptionPane.showMessageDialog(null,"������ ���ο� ���Ϸ� �����մϴ�.");
						act = new Manager(100, 100);
					}
				} catch (Exception e) { // Manager�κ��� ���޹��� ���ܸ� ó��
					JOptionPane.showMessageDialog(null,"������ �ε带 �����Ͽ����ϴ�.");
				} 
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomeJFrame() {
		// ���� ��ǥ �̿�
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
		
		JLabel lblNewLabel_1 = new JLabel("[��Ż ���� ���α׷�]");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("����", Font.BOLD, 15));
		panel.add(lblNewLabel_1);
		
		// � ��尡 �ʿ��ϽŰ���?
		JLabel lblNewLabel = new JLabel("\uC5B4\uB5A4 \uBAA8\uB4DC\uAC00 \uD544\uC694\uD558\uC2E0\uAC00\uC694?");
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		// �Ǹ� ��ư
		sellBtn.setFont(new Font("����", Font.PLAIN, 15));
		sellBtn.addActionListener(new AskingListener());
		panel_1.add(sellBtn);
		
		// ���� ��ư
		manageBtn.setFont(new Font("����", Font.PLAIN, 15));
		manageBtn.addActionListener(new AskingListener());
		panel_1.add(manageBtn);
	}

	// ���� Ŭ������ ����ϴ� ����
	private class AskingListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == sellBtn) // �Ǹ� ��ư�� Ŭ���ߴٸ�
			{	SellJFrame frame = new SellJFrame(act); // �Ǹ� ����â���� �̵�
				frame.setVisible(true);
			} else if (e.getSource() == manageBtn) { // ���� ��ư�� Ŭ���ߴٸ�
				ManageJFrame frame = new ManageJFrame(act); // �Ǹ� ����â���� �̵�
				frame.setVisible(true);
			}
		}
	}	
}
