package TextEdidtor;

import java.awt.*;
import java.awt.event.*;
//import java.awt.Window.Type;
import java.util.regex.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * 主窗体唯一实例
	 */
	public static MainFrame frame=new MainFrame();
	
	/*
	 * 主窗体
	 */
	private JPanel contentPane;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private String currentFileName="新建文本文档.txt";
	private String currentFilePath;
	
	/*
	 * 查找对话框
	 */
	private JDialog searchDialog;
	private JButton button;
	private JButton button_1;
	private JTextField textField;
	private JCheckBox chckbxc;
	
	/*
	 * 字体设置对话框
	 */
	private JDialog FontDialog;
	private JPanel fontDialogPanel;
	private Color currentColor;
	private String currentFont;
	private int currentStyle;
	private int currentStyleIndex=0;
	private int currentFontSize;
	
	private static boolean isChange=false;
	private static boolean isNothing=false;
	private static int key=0;
	
	/*
	 * 构造方法
	 */
	public MainFrame() {
		/*
		 * 单例实现
		 */
		if(frame!=null) {
			this.dispose();
			return;
		}
		
		/*
		 * 窗体监听器初始化
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(isChange) {
					int confirmMessege=JOptionPane.showConfirmDialog(contentPane, "你是否希望保存当前文件？", "确认", JOptionPane.YES_NO_OPTION);
					if(confirmMessege==JOptionPane.YES_OPTION) {
						try {
							saveFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						frame.dispose();
					}
					else if(confirmMessege==JOptionPane.NO_OPTION) {
						frame.dispose();
					}
				}
				else {
					frame.dispose();
				}
			}
		});
		
		/*
		 * 初始化
		 */
		initialize();
	}
	
	/*
	 *  窗体初始化
	 */
	public void initialize() {
		setFont(new Font("微软雅黑", Font.PLAIN, 15));
		setType(Type.POPUP);
		setTitle("新建文本文档.txt-文本编辑器");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 813, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setBounds(0, 0, 809, 25);
		contentPane.add(menuBar);
		
		JMenu menu = new JMenu(" 文件");
		menu.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		menu.setBackground(Color.WHITE);
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("新建(N)");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					newFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menuItem.setSelected(true);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		menuItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("打开(O)");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		menuItem_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("保存(S)");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					saveFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		menuItem_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu.add(menuItem_2);
		
		JMenu menu_1 = new JMenu(" 编辑");
		menu_1.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		menu_1.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menu_1);
		
		JMenuItem menuItem_3 = new JMenuItem("查找");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				key=0;
				createSearchDialog();
				searchDialog.setVisible(true);
			}
		});
		menuItem_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		menuItem_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu_1.add(menuItem_3);
		
		JMenuItem menuItem_4 = new JMenuItem("替换");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceText();
			}
		});
		menuItem_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		menuItem_4.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu_1.add(menuItem_4);
		
		/*
		 * 小写字母转换成大写
		 */
		JMenuItem menuItem_5 = new JMenuItem("转成大写");
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=textPane.getText();
				text=text.toUpperCase();
				textPane.setText(text);
			}
		});
		menuItem_5.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu_1.add(menuItem_5);
		
		/*
		 * 大写字母转换成小写
		 */
		JMenuItem menuItem_6 = new JMenuItem("转成小写");
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=textPane.getText();
				text=text.toLowerCase();
				textPane.setText(text);
			}
		});
		menuItem_6.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu_1.add(menuItem_6);
		
		JMenu menu_2 = new JMenu(" 格式");
		menu_2.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		menu_2.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menu_2);
		
		/*
		 * 字体设置
		 */
		JMenuItem menuItem_7 = new JMenuItem("字体设置");
		menuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFontDialog();
				FontDialog.setVisible(true);
			}
		});
		menuItem_7.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu_2.add(menuItem_7);
		
		/*
		 * 背景颜色设置
		 */
		JMenuItem menuItem_8 = new JMenuItem("背景颜色");
		menuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color=JColorChooser.showDialog(contentPane, "背景颜色设置", textPane.getBackground());
				textPane.setBackground(color);
			}
		});
		menuItem_8.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu_2.add(menuItem_8);
		
		JMenu menu_3 = new JMenu(" 统计");
		menu_3.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		menu_3.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menu_3);
		
		/*
		 * 字数统计
		 */
		JMenuItem menuItem_9 = new JMenuItem("字数");
		menuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordCount();
			}
		});
		menuItem_9.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu_3.add(menuItem_9);
		
		/*
		 * 行数统计
		 */
		JMenuItem menuItem_10 = new JMenuItem("行数");
		menuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowCount();
			}
		});
		menuItem_10.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu_3.add(menuItem_10);
		
		/*
		 * 字符数统计
		 */
		JMenuItem menuItem_11 = new JMenuItem("字符数");
		menuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				characterCount();
			}
		});
		menuItem_11.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu_3.add(menuItem_11);
		
		textPane = new JTextPane();
		textPane.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				isChange=true;
			}
		});
		currentFont="宋体";
		currentStyle=Font.PLAIN;
		currentFontSize=15;
		textPane.setFont(new Font(currentFont, currentStyle, currentFontSize));
		textPane.setBounds(0, 23, 799, 487);
		contentPane.add(textPane);
		
		scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(0, 23, 800, 503);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		
		currentColor=textPane.getForeground();
		this.setResizable(false);
	}
	
	/*
	 * 字数统计
	 */
	private void wordCount() {
		int wordNum=0;
		String str=textPane.getText();
		String regex1="[\u4E00-\u9FA5]",regex2="[\u4E00-\u9FA5]+",regex3="\\s+";
		Pattern p=Pattern.compile(regex1,Pattern.CASE_INSENSITIVE);
		Matcher m=p.matcher(str);
		while(m.find()) {
			wordNum++;
		}
		String[] strArr=str.split(regex3);
		for(String strItem : strArr) {
			if(strItem.matches(regex2)) {
				String[] strArr2=strItem.split(regex2);
				for(String strItem2 : strArr2) {
					if(strItem2.equals(null))continue;
					else {
						wordNum++;
					}
				}
			}
			else {
				wordNum++;
			}
		}
		JOptionPane.showMessageDialog(contentPane, "字数："+wordNum, "字数统计", JOptionPane.PLAIN_MESSAGE);
	}
	
	/*
	 * 行数统计
	 */
	private void rowCount() {
		String str=textPane.getText();
		String[] strArr=str.split("\n");
		int lineNum=strArr.length;
		JOptionPane.showMessageDialog(contentPane, "行数："+lineNum, "行数统计", JOptionPane.PLAIN_MESSAGE);
	}
	
	/*
	 * 字符数统计
	 */
	private void characterCount() {
		String str=textPane.getText();
		String[] strArr=str.split("[\n\r]");
		int wordNum=0;
		for(String strItem : strArr) {
			wordNum+=strItem.length();
		}
		JOptionPane.showMessageDialog(contentPane, "字符数："+wordNum, "字符数统计", JOptionPane.PLAIN_MESSAGE);
	}
	
	/*
	 * 新建文件
	 */
	private void newFile() throws IOException {
		if(isChange) {
			int confirmMessege = JOptionPane.showConfirmDialog(contentPane, "你是否希望保存当前文件？", "确认", JOptionPane.YES_NO_OPTION);
			if(confirmMessege==JOptionPane.YES_OPTION) {
				saveFile();
			}
		}
		textPane.setText(new String());
		this.setTitle("新建文本文档.txt-文本编辑器");
		isChange=false;
	}
	
	/*
	 * 保存文件
	 */
	public void saveFile() throws IOException {
		if(!isChange) {
			return;
		}
		if(findDir(this.currentFilePath,this.currentFileName)) {
			delDir(currentFilePath+currentFileName);
			BufferedWriter writer=new BufferedWriter(new FileWriter(currentFilePath+currentFileName));
			writer.write(this.textPane.getText());
			writer.close();
		}
		else {
			FileDialog saveDialog=new FileDialog(this,"保存文件",FileDialog.SAVE);
			/*FilenameFilter filter=new FilenameFilter() {
				public boolean accept(File dir,String name) {
					if(name.endsWith("txt")) {
						return true;
					}
					else {
						return false;
					}
				}
			};
			saveDialog.setFilenameFilter(filter);*/
			saveDialog.setFile("新建文本文档");
			saveDialog.setVisible(true);
			String path=saveDialog.getDirectory();
			String name=saveDialog.getFile();
			if(path!=null&&name!=null) {
				currentFilePath=path;
				currentFileName=name;
				BufferedWriter writer=new BufferedWriter(new FileWriter(currentFilePath+currentFileName+".txt"));
				writer.write(this.textPane.getText());
				writer.flush();
				writer.close();
				setTitle(currentFileName+".txt-文本编辑器");
			}
			else return;
		}
		isChange=false;
	}
	
	/*
	 * 打开文件
	 */
	public void openFile() throws IOException {
		if(isChange) {
			int confirmMessege = JOptionPane.showConfirmDialog(contentPane, "你是否希望保存当前文件？", "确认", JOptionPane.YES_NO_OPTION);
			if(confirmMessege==JOptionPane.YES_OPTION) {
				saveFile();
			}
		}
		FileDialog openDialog=new FileDialog(this,"打开文件",FileDialog.LOAD);
		/*FilenameFilter filter=new FilenameFilter() {
			public boolean accept(File dir,String name) {
				if(name.endsWith("txt")) {
					return true;
				}
				else {
					return false;
				}
			}
		};
		openDialog.setFilenameFilter(filter);*/
		openDialog.setVisible(true);
		String path=openDialog.getDirectory();
		String name=openDialog.getFile();
		if(path!=null&&name!=null) {
			if(!findDir(path,name)) {
				JOptionPane.showMessageDialog(contentPane, "找不到文件！", "错误", JOptionPane.ERROR_MESSAGE);
			}
			else {
				currentFilePath=path;
				currentFileName=name;
				BufferedReader reader = new BufferedReader(new FileReader(currentFilePath+currentFileName));
				StringBuilder strBuilder=new StringBuilder();
				String str=null;
				while((str=reader.readLine())!=null) {
					strBuilder.append(str+"\r\n");
				}
				textPane.setText(strBuilder.toString());
				reader.close();
				setTitle(currentFileName+"-文本编辑器");
				textPane.setCaretPosition(0);
				isChange=false;
			}
		}
	}
	
	/*
	 * 查找文本
	 */
	public void findText(String regex) {
		String originRegex=regex;
		String text=textPane.getText();
		if(!chckbxc.isSelected()) {
			regex=regex.toLowerCase();
			text=text.toLowerCase();
		}
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(text);
		if(m.find()) {
			String[] strArr=p.split(text);
			if(key>strArr.length) {
				key=1;
			}
			int pos=0;
			for(int n=0;n<key;n++) {
				if(n!=0) {
					pos+=regex.length();
				}
				if(!strArr[n].isEmpty()) {
					pos+=strArr[n].length();
				}
			}
			if(pos<text.length()) {
				textPane.select(pos, pos+regex.length());
			}
			else {
				JOptionPane.showMessageDialog(contentPane, "找不到下一个"+originRegex, "查找失败", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(contentPane, "没找到"+originRegex, "查找失败", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*
	 * 替换文本
	 */
	private void replaceText() {
		String regex=JOptionPane.showInputDialog(contentPane,"请输入要替换掉的内容：", "替换", JOptionPane.PLAIN_MESSAGE);
		if(regex!=null) {
			String str=JOptionPane.showInputDialog(contentPane,"请输入要替换成的内容：", "替换", JOptionPane.PLAIN_MESSAGE);
			if(str!=null) {
				String text=this.textPane.getText();
				text=text.replaceAll(regex, str);
				this.textPane.setText(text);
			}
		}
	}
	
	/*
	 * 删除文件
	 */
	private static void delDir(String path) {
		File file = new File(path);
		if(!file.exists()) {
			System.err.println("文件目录不存在！");
			return;
		}
		if(!file.delete()) {
			System.err.println(file.getName()+"文件删除失败");
			return;
		}
	}
	
	/*
	 * 检查该路径是否正确存在
	 */
	private static boolean findDir(String path,String name) {
		File file = new File(path+name);
		if(file.exists()&&file.getName().equals(name)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * 创建查找对话框
	 */
	private void createSearchDialog() {
		searchDialog=new JDialog(this);
		searchDialog.setTitle("查找");
		searchDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		searchDialog.setBounds(100, 100, 523, 129);
		searchDialog.getContentPane().setLayout(new BorderLayout());
		JPanel dialogPanel= new JPanel();
		dialogPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		searchDialog.getContentPane().add(dialogPanel, BorderLayout.CENTER);
		dialogPanel.setLayout(null);
		
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
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		textField.setBounds(79, 10, 301, 22);
		dialogPanel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("查找内容：");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		label.setBounds(10, 10, 72, 22);
		dialogPanel.add(label);
		
		button = new JButton("查找下一个(F)");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				key++;
				MainFrame.frame.findText(textField.getText());
			}
		});
		button.setEnabled(false);
		button.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		button.setBounds(390, 10, 116, 22);
		dialogPanel.add(button);
		
		button_1 = new JButton("取消");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchDialog.dispose();
			}
		});
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		button_1.setBounds(390, 42, 116, 22);
		dialogPanel.add(button_1);
		
		chckbxc = new JCheckBox("区分大小写(C)");
		chckbxc.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		chckbxc.setBounds(89, 38, 116, 22);
		dialogPanel.add(chckbxc);
		
		searchDialog.setResizable(false);
	}
	
	/*
	 * 创建字体设置对话框
	 */
	private void createFontDialog() {
		FontDialog=new JDialog(this);
		FontDialog.setBounds(100, 100, 480, 147);
		FontDialog.getContentPane().setLayout(new BorderLayout());
		
		fontDialogPanel=new JPanel();
		fontDialogPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		FontDialog.getContentPane().add(fontDialogPanel, BorderLayout.CENTER);
		fontDialogPanel.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(29, 20, 415, 43);
		fontDialogPanel.add(layeredPane);
		
		JLabel lbly = new JLabel("字形(Y)：");
		lbly.setBounds(174, 0, 58, 15);
		layeredPane.add(lbly);
		lbly.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		
		JLabel lbls = new JLabel("大小(S)：");
		lbls.setBounds(326, 0, 58, 15);
		layeredPane.add(lbls);
		lbls.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(0, 20, 150, 23);
		layeredPane.add(comboBox);
		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] font=ge.getAvailableFontFamilyNames();
		comboBox.setModel(new DefaultComboBoxModel<String>(font));
		comboBox.setSelectedItem(currentFont);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(174, 20, 130, 23);
		layeredPane.add(comboBox_1);
		comboBox_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"常规", "倾斜", "加粗","倾斜加粗"}));
		comboBox_1.setSelectedIndex(currentStyleIndex);
		
		JComboBox<Integer> comboBox_2 = new JComboBox<Integer>();
		comboBox_2.setBounds(328, 20, 87, 23);
		layeredPane.add(comboBox_2);
		comboBox_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		Integer[] FontSize=new Integer[95];
		for(int i=5;i<100;i++) {
			FontSize[i-5]=i;
		}
		comboBox_2.setModel(new DefaultComboBoxModel<Integer>(FontSize));
		comboBox_2.setSelectedItem(currentFontSize);
		
		JLabel lblf = new JLabel("字体(F)：");
		lblf.setBounds(0, 0, 58, 15);
		layeredPane.add(lblf);
		lblf.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		
		JButton button = new JButton("字体颜色");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor=JColorChooser.showDialog(fontDialogPanel, "字体颜色", currentColor);
			}
		});
		button.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		button.setBounds(29, 73, 150, 27);
		fontDialogPanel.add(button);
		
		JButton button_1 = new JButton("确定");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.setForeground(currentColor);
				currentFont=comboBox.getSelectedItem().toString();
				String style=comboBox_1.getSelectedItem().toString();
				currentStyleIndex=comboBox_1.getSelectedIndex();
				if(style.equals("常规")) {
					currentStyle=Font.PLAIN;
				}
				else if(style.equals("倾斜")) {
					currentStyle=Font.ITALIC;
				}
				else if(style.equals("加粗")) {
					currentStyle=Font.BOLD;
				}
				else if(style.equals("倾斜加粗")) {
					currentStyle=Font.BOLD|Font.ITALIC;
				}
				currentFontSize=(int)comboBox_2.getSelectedItem();
				textPane.setFont(new Font(currentFont,currentStyle,currentFontSize));
				FontDialog.dispose();
			}
		});
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		button_1.setBounds(338, 73, 106, 29);
		fontDialogPanel.add(button_1);
	}
} 
