package DialogDesign;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import TextEdidtor.MainFrame;

import java.awt.event.*;

public class ReplaceDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton button;
	private JTextField textField_1;
	public JCheckBox chckbxc;

	/**
	 * Create the dialog.
	 */
	public ReplaceDialog() {
		super(MainFrame.frame,"Ìæ»»");
		setBounds(100, 100, 523, 136);
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
		textField.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		textField.setBounds(79, 10, 301, 22);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("²éÕÒÄÚÈÝ£º");
		label.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		label.setBounds(10, 10, 72, 22);
		contentPanel.add(label);
		
		button = new JButton("²éÕÒÏÂÒ»¸ö(F)");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.frame.key++;
				MainFrame.frame.findText(textField.getText());
			}
		});
		button.setEnabled(false);
		button.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		button.setBounds(390, 10, 116, 22);
		contentPanel.add(button);
		
		JButton button_1 = new JButton("È¡Ïû");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.frame.isReplace=false;
				dispose();
			}
		});
		button_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		button_1.setBounds(390, 67, 116, 22);
		contentPanel.add(button_1);
		
		chckbxc = new JCheckBox("Çø·Ö´óÐ¡Ð´(C)");
		chckbxc.setSelected(true);
		chckbxc.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		chckbxc.setBounds(79, 67, 116, 22);
		contentPanel.add(chckbxc);
		
		JButton button_2 = new JButton("Ìæ»»");
		button_2.setEnabled(false);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.frame.replaceSelectingText(textField_1.getText());
			}
		});
		button_2.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		button_2.setBounds(264, 38, 116, 22);
		contentPanel.add(button_2);
		
		JButton button_3 = new JButton("Ìæ»»È«²¿");
		button_3.setEnabled(false);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.frame.replaceAllText(textField.getText(),textField_1.getText());
			}
		});
		button_3.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		button_3.setBounds(390, 38, 116, 22);
		contentPanel.add(button_3);
		
		textField_1 = new JTextField();
		textField_1.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if(textField_1.getText().equals(new String())) {
					button_2.setEnabled(false);
					button_3.setEnabled(false);
				}
				else {
					button_2.setEnabled(true);
					button_3.setEnabled(true);
				}
			}
		});
		textField_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(79, 39, 175, 22);
		contentPanel.add(textField_1);
		
		JLabel label_1 = new JLabel("Ìæ»»ÄÚÈÝ£º");
		label_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		label_1.setBounds(10, 38, 72, 22);
		contentPanel.add(label_1);
		
		this.setResizable(false);
	}
}
