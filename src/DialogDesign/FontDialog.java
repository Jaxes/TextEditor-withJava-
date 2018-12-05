package DialogDesign;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

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
		setBounds(100, 100, 480, 147);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(29, 20, 415, 43);
		contentPanel.add(layeredPane);
		
		JLabel lbly = new JLabel("×ÖÐÎ(Y)£º");
		lbly.setBounds(174, 0, 58, 15);
		layeredPane.add(lbly);
		lbly.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		
		JLabel lbls = new JLabel("´óÐ¡(S)£º");
		lbls.setBounds(326, 0, 58, 15);
		layeredPane.add(lbls);
		lbls.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(0, 20, 150, 23);
		layeredPane.add(comboBox);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(174, 20, 130, 23);
		layeredPane.add(comboBox_1);
		comboBox_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"\u5E38\u89C4", "\u503E\u659C", "\u7C97\u4F53", "\u7C97\u504F\u659C\u4F53"}));
		
		JComboBox<Integer> comboBox_2 = new JComboBox<Integer>();
		comboBox_2.setBounds(328, 20, 87, 23);
		layeredPane.add(comboBox_2);
		
		JLabel lblf = new JLabel("×ÖÌå(F)£º");
		lblf.setBounds(0, 0, 58, 15);
		layeredPane.add(lblf);
		lblf.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		
		JButton button = new JButton("×ÖÌåÑÕÉ«");
		button.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		button.setBounds(29, 73, 150, 27);
		contentPanel.add(button);
		
		JButton button_1 = new JButton("È·¶¨");
		button_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		button_1.setBounds(338, 73, 106, 29);
		contentPanel.add(button_1);
	}
}
