package TextEdidtor;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import DialogDesign.*;

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
	private JScrollPane scrollPane;
	private String currentFileName="新建文本文档.txt";
	private String currentFilePath;
	
	public JTextPane textPane;
	
	/*
	 * 查找对话框
	 */
	private SearchDialog searchDialog;
	
	/*
	 * 字体设置对话框
	 */
	private FontDialog fontDialog;
	
	public Color currentColor;
	public String currentFont;
	public int currentStyle;
	public int currentStyleIndex=0;
	public int currentFontSize;
	
	/*
	 * 替换对话框
	 */
	private ReplaceDialog replaceDialog;
	
	/*
	 * 其他
	 */
	private boolean isChange=false;
	public boolean isReplace=false;
	public int key=0;
	
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
		 * 窗体初始化
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
				searchDialog=new SearchDialog();
				searchDialog.setVisible(true);
			}
		});
		menuItem_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		menuItem_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menu_1.add(menuItem_3);
		
		JMenuItem menuItem_4 = new JMenuItem("替换");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isReplace=true;
				replaceDialog=new ReplaceDialog();
				replaceDialog.setVisible(true);
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
				if(textPane.getSelectionStart()!=textPane.getSelectionEnd()) {
					textPane.replaceSelection(textPane.getSelectedText().toUpperCase());
				}
				else {
					int confirmMessege=JOptionPane.showConfirmDialog(contentPane, 
							"你没有选择任何文本，默认转换所有小写字母为大写，你确定要这样做吗？","确认",JOptionPane.YES_NO_OPTION);
					if(confirmMessege==JOptionPane.YES_OPTION) {
						String text=textPane.getText().toUpperCase();
						textPane.setText(text);
					}
				}
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
				if(textPane.getSelectionStart()!=textPane.getSelectionEnd()) {
					textPane.replaceSelection(textPane.getSelectedText().toLowerCase());
				}
				else {
					int confirmMessege=JOptionPane.showConfirmDialog(contentPane, 
							"你没有选择任何文本，默认转换所有大写字母为小写，你确定要这样做吗？","确认",JOptionPane.YES_NO_OPTION);
					if(confirmMessege==JOptionPane.YES_OPTION) {
						String text=textPane.getText().toLowerCase();
						textPane.setText(text);
					}
				}
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
				fontDialog=new FontDialog();
				fontDialog.setVisible(true);
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
		if(isReplace) {
			if(!replaceDialog.chckbxc.isSelected()) {
				regex=regex.toLowerCase();
				text=text.toLowerCase();
			}
		}
		else {
			if(!searchDialog.chckbxc.isSelected()) {
				regex=regex.toLowerCase();
				text=text.toLowerCase();
			}
		}
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(text);
		if(m.find()) {
			int tempKey=key;
			for(int i=0;i<text.length()-regex.length()+1;i=(i+1)%(text.length()-regex.length()+1)) {
				String temp=text.substring(i, i+regex.length());
				if(temp.equals(regex)) {
					if(--tempKey==0) {
						textPane.select(i, i+regex.length());
						break;
					}
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(contentPane, "没找到"+originRegex, "查找失败", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*
	 * 替换全部匹配的文本
	 */
	public void replaceAllText(String regex,String str) {
		String text=this.textPane.getText();
		text=text.replaceAll(regex, str);
		this.textPane.setText(text);
	}
	
	/*
	 * 替换当前选择的文本 
	 */
	public void replaceSelectingText(String str) {
		if(this.textPane.getSelectionStart()!=this.textPane.getSelectionEnd()) {
			this.textPane.replaceSelection(str);
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
} 
