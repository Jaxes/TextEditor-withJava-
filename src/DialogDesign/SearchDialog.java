package DialogDesign;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import TextEdidtor.MainFrame;

public class SearchDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	public JTextField textField;
	public JButton button;
	public JButton button_1;
	public JCheckBox chckbxc;

	/**
	 * Create the dialog.
	 */
	public SearchDialog() {
		super(MainFrame.frame,"≤È’“");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 523, 129);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
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
		textField.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 12));
		textField.setBounds(79, 10, 301, 22);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("≤È’“ƒ⁄»›£∫");
		label.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));
		label.setBounds(10, 10, 72, 22);
		contentPanel.add(label);
		
		button = new JButton("≤È’“œ¬“ª∏ˆ(F)");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.frame.key++;
				MainFrame.frame.findText(textField.getText());
			}
		});
		button.setEnabled(false);
		button.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));
		button.setBounds(390, 10, 116, 22);
		contentPanel.add(button);
		
		button_1 = new JButton("»°œ˚");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));
		button_1.setBounds(390, 42, 116, 22);
		contentPanel.add(button_1);
		
		chckbxc = new JCheckBox("«¯∑÷¥Û–°–¥(C)");
		chckbxc.setSelected(true);
		chckbxc.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));
		chckbxc.setBounds(89, 38, 116, 22);
		contentPanel.add(chckbxc);
		
		setResizable(false);
	}
}
