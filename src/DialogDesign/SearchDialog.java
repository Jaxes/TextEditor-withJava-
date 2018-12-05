package DialogDesign;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class SearchDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton button;

	/**
	 * Create the dialog.
	 */
	public SearchDialog() {
		initialize();
	}
	
	private void initialize() {
		setTitle("����");
		setBounds(100, 100, 523, 129);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textField = new JTextField();
			textField.addCaretListener(new CaretListener() {
				public void caretUpdate(CaretEvent e) {
					if(textField.getText().equals(new String())) {
						button.setEnabled(false);
					}
					else {
						button.setEnabled(true);
					}
				}
			});
			textField.setFont(new Font("΢���ź�", Font.PLAIN, 12));
			textField.setBounds(79, 10, 301, 22);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		
		JLabel label = new JLabel("�������ݣ�");
		label.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		label.setBounds(10, 10, 72, 22);
		contentPanel.add(label);
		
		button = new JButton("������һ��(F)");
		button.setEnabled(false);
		button.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		button.setBounds(390, 10, 116, 22);
		contentPanel.add(button);
		
		JButton button_1 = new JButton("ȡ��");
		button_1.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		button_1.setBounds(390, 42, 116, 22);
		contentPanel.add(button_1);
		
		JCheckBox chckbxc = new JCheckBox("���ִ�Сд(C)");
		chckbxc.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		chckbxc.setBounds(89, 38, 116, 22);
		contentPanel.add(chckbxc);
	}
}
