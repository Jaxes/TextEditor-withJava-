package DialogDesign;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import TextEdidtor.MainFrame;

public class FontDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public FontDialog() {
		super(MainFrame.frame,"◊÷ÃÂ…Ë÷√");
		setBounds(100, 100, 480, 147);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(29, 20, 415, 43);
		contentPanel.add(layeredPane);
		
		JLabel lbly = new JLabel("◊÷–Œ(Y)£∫");
		lbly.setBounds(174, 0, 58, 15);
		layeredPane.add(lbly);
		lbly.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));
		
		JLabel lbls = new JLabel("¥Û–°(S)£∫");
		lbls.setBounds(326, 0, 58, 15);
		layeredPane.add(lbls);
		lbls.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(0, 20, 150, 23);
		layeredPane.add(comboBox);
		comboBox.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 12));
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] font=ge.getAvailableFontFamilyNames();
		comboBox.setModel(new DefaultComboBoxModel<String>(font));
		comboBox.setSelectedItem(MainFrame.frame.currentFont);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(174, 20, 130, 23);
		layeredPane.add(comboBox_1);
		comboBox_1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 12));
		comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"≥£πÊ", "«„–±", "º”¥÷","«„–±º”¥÷"}));
		comboBox_1.setSelectedIndex(MainFrame.frame.currentStyleIndex);
		
		JComboBox<Integer> comboBox_2 = new JComboBox<Integer>();
		comboBox_2.setBounds(328, 20, 87, 23);
		layeredPane.add(comboBox_2);
		comboBox_2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 12));
		Integer[] FontSize=new Integer[95];
		for(int i=5;i<100;i++) {
			FontSize[i-5]=i;
		}
		comboBox_2.setModel(new DefaultComboBoxModel<Integer>(FontSize));
		comboBox_2.setSelectedItem(MainFrame.frame.currentFontSize);
		
		JLabel lblf = new JLabel("◊÷ÃÂ(F)£∫");
		lblf.setBounds(0, 0, 58, 15);
		layeredPane.add(lblf);
		lblf.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));
		
		JButton button = new JButton("◊÷ÃÂ—’…´");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.frame.currentColor=JColorChooser.showDialog(contentPanel, "◊÷ÃÂ—’…´", MainFrame.frame.currentColor);
			}
		});
		button.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));
		button.setBounds(29, 73, 150, 27);
		contentPanel.add(button);
		
		JButton button_1 = new JButton("»∑∂®");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.frame.textPane.setForeground(MainFrame.frame.currentColor);
				MainFrame.frame.currentFont=comboBox.getSelectedItem().toString();
				String style=comboBox_1.getSelectedItem().toString();
				MainFrame.frame.currentStyleIndex=comboBox_1.getSelectedIndex();
				if(style.equals("≥£πÊ")) {
					MainFrame.frame.currentStyle=Font.PLAIN;
				}
				else if(style.equals("«„–±")) {
					MainFrame.frame.currentStyle=Font.ITALIC;
				}
				else if(style.equals("º”¥÷")) {
					MainFrame.frame.currentStyle=Font.BOLD;
				}
				else if(style.equals("«„–±º”¥÷")) {
					MainFrame.frame.currentStyle=Font.BOLD|Font.ITALIC;
				}
				MainFrame.frame.currentFontSize=(int)comboBox_2.getSelectedItem();
				MainFrame.frame.textPane.setFont(new Font(MainFrame.frame.currentFont,MainFrame.frame.currentStyle,MainFrame.frame.currentFontSize));
				dispose();
			}
		});
		button_1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 13));
		button_1.setBounds(338, 73, 106, 29);
		contentPanel.add(button_1);
	}
}
