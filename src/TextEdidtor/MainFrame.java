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
	 * �汾��
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * ������Ψһʵ��
	 */
	public static MainFrame frame=new MainFrame();
	
	/*
	 * ������
	 */
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private String currentFileName="�½��ı��ĵ�.txt";
	private String currentFilePath;
	
	public JTextPane textPane;
	
	/*
	 * ���ҶԻ���
	 */
	private SearchDialog searchDialog;
	
	/*
	 * �������öԻ���
	 */
	private FontDialog fontDialog;
	
	public Color currentColor;
	public String currentFont;
	public int currentStyle;
	public int currentStyleIndex=0;
	public int currentFontSize;
	
	/*
	 * �滻�Ի���
	 */
	private ReplaceDialog replaceDialog;
	
	/*
	 * ����
	 */
	private boolean isChange=false;
	public boolean isReplace=false;
	public int key=0;
	
	/*
	 * ���췽��
	 */
	public MainFrame() {
		/*
		 * ����ʵ��
		 */
		if(frame!=null) {
			this.dispose();
			return;
		}
		
		/*
		 * �����������ʼ��
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(isChange) {
					int confirmMessege=JOptionPane.showConfirmDialog(contentPane, "���Ƿ�ϣ�����浱ǰ�ļ���", "ȷ��", JOptionPane.YES_NO_OPTION);
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
		 * �����ʼ��
		 */
		initialize();
	}
	
	/*
	 *  �����ʼ��
	 */
	public void initialize() {
		setFont(new Font("΢���ź�", Font.PLAIN, 15));
		setType(Type.POPUP);
		setTitle("�½��ı��ĵ�.txt-�ı��༭��");
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
		
		JMenu menu = new JMenu(" �ļ�");
		menu.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		menu.setBackground(Color.WHITE);
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("�½�(N)");
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
		menuItem.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("��(O)");
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
		menuItem_1.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("����(S)");
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
		menuItem_2.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu.add(menuItem_2);
		
		JMenu menu_1 = new JMenu(" �༭");
		menu_1.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		menu_1.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menu_1);
		
		JMenuItem menuItem_3 = new JMenuItem("����");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				key=0;
				searchDialog=new SearchDialog();
				searchDialog.setVisible(true);
			}
		});
		menuItem_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		menuItem_3.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu_1.add(menuItem_3);
		
		JMenuItem menuItem_4 = new JMenuItem("�滻");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isReplace=true;
				replaceDialog=new ReplaceDialog();
				replaceDialog.setVisible(true);
			}
		});
		menuItem_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		menuItem_4.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu_1.add(menuItem_4);
		
		/*
		 * Сд��ĸת���ɴ�д
		 */
		JMenuItem menuItem_5 = new JMenuItem("ת�ɴ�д");
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textPane.getSelectionStart()!=textPane.getSelectionEnd()) {
					textPane.replaceSelection(textPane.getSelectedText().toUpperCase());
				}
				else {
					int confirmMessege=JOptionPane.showConfirmDialog(contentPane, 
							"��û��ѡ���κ��ı���Ĭ��ת������Сд��ĸΪ��д����ȷ��Ҫ��������","ȷ��",JOptionPane.YES_NO_OPTION);
					if(confirmMessege==JOptionPane.YES_OPTION) {
						String text=textPane.getText().toUpperCase();
						textPane.setText(text);
					}
				}
			}
		});
		menuItem_5.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu_1.add(menuItem_5);
		
		/*
		 * ��д��ĸת����Сд
		 */
		JMenuItem menuItem_6 = new JMenuItem("ת��Сд");
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textPane.getSelectionStart()!=textPane.getSelectionEnd()) {
					textPane.replaceSelection(textPane.getSelectedText().toLowerCase());
				}
				else {
					int confirmMessege=JOptionPane.showConfirmDialog(contentPane, 
							"��û��ѡ���κ��ı���Ĭ��ת�����д�д��ĸΪСд����ȷ��Ҫ��������","ȷ��",JOptionPane.YES_NO_OPTION);
					if(confirmMessege==JOptionPane.YES_OPTION) {
						String text=textPane.getText().toLowerCase();
						textPane.setText(text);
					}
				}
			}
		});
		menuItem_6.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu_1.add(menuItem_6);
		
		JMenu menu_2 = new JMenu(" ��ʽ");
		menu_2.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		menu_2.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menu_2);
		
		/*
		 * ��������
		 */
		JMenuItem menuItem_7 = new JMenuItem("��������");
		menuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontDialog=new FontDialog();
				fontDialog.setVisible(true);
			}
		});
		menuItem_7.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu_2.add(menuItem_7);
		
		/*
		 * ������ɫ����
		 */
		JMenuItem menuItem_8 = new JMenuItem("������ɫ");
		menuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color=JColorChooser.showDialog(contentPane, "������ɫ����", textPane.getBackground());
				textPane.setBackground(color);
			}
		});
		menuItem_8.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu_2.add(menuItem_8);
		
		JMenu menu_3 = new JMenu(" ͳ��");
		menu_3.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		menu_3.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menu_3);
		
		/*
		 * ����ͳ��
		 */
		JMenuItem menuItem_9 = new JMenuItem("����");
		menuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordCount();
			}
		});
		menuItem_9.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu_3.add(menuItem_9);
		
		/*
		 * ����ͳ��
		 */
		JMenuItem menuItem_10 = new JMenuItem("����");
		menuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rowCount();
			}
		});
		menuItem_10.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu_3.add(menuItem_10);
		
		/*
		 * �ַ���ͳ��
		 */
		JMenuItem menuItem_11 = new JMenuItem("�ַ���");
		menuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				characterCount();
			}
		});
		menuItem_11.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu_3.add(menuItem_11);
		
		textPane = new JTextPane();
		textPane.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				isChange=true;
			}
		});
		currentFont="����";
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
	 * ����ͳ��
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
		JOptionPane.showMessageDialog(contentPane, "������"+wordNum, "����ͳ��", JOptionPane.PLAIN_MESSAGE);
	}
	
	/*
	 * ����ͳ��
	 */
	private void rowCount() {
		String str=textPane.getText();
		String[] strArr=str.split("\n");
		int lineNum=strArr.length;
		JOptionPane.showMessageDialog(contentPane, "������"+lineNum, "����ͳ��", JOptionPane.PLAIN_MESSAGE);
	}
	
	/*
	 * �ַ���ͳ��
	 */
	private void characterCount() {
		String str=textPane.getText();
		String[] strArr=str.split("[\n\r]");
		int wordNum=0;
		for(String strItem : strArr) {
			wordNum+=strItem.length();
		}
		JOptionPane.showMessageDialog(contentPane, "�ַ�����"+wordNum, "�ַ���ͳ��", JOptionPane.PLAIN_MESSAGE);
	}
	
	/*
	 * �½��ļ�
	 */
	private void newFile() throws IOException {
		if(isChange) {
			int confirmMessege = JOptionPane.showConfirmDialog(contentPane, "���Ƿ�ϣ�����浱ǰ�ļ���", "ȷ��", JOptionPane.YES_NO_OPTION);
			if(confirmMessege==JOptionPane.YES_OPTION) {
				saveFile();
			}
		}
		textPane.setText(new String());
		this.setTitle("�½��ı��ĵ�.txt-�ı��༭��");
		isChange=false;
	}
	
	/*
	 * �����ļ�
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
			FileDialog saveDialog=new FileDialog(this,"�����ļ�",FileDialog.SAVE);
			saveDialog.setFile("�½��ı��ĵ�");
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
				setTitle(currentFileName+".txt-�ı��༭��");
			}
			else return;
		}
		isChange=false;
	}
	
	/*
	 * ���ļ�
	 */
	public void openFile() throws IOException {
		if(isChange) {
			int confirmMessege = JOptionPane.showConfirmDialog(contentPane, "���Ƿ�ϣ�����浱ǰ�ļ���", "ȷ��", JOptionPane.YES_NO_OPTION);
			if(confirmMessege==JOptionPane.YES_OPTION) {
				saveFile();
			}
		}
		FileDialog openDialog=new FileDialog(this,"���ļ�",FileDialog.LOAD);
		openDialog.setVisible(true);
		String path=openDialog.getDirectory();
		String name=openDialog.getFile();
		if(path!=null&&name!=null) {
			if(!findDir(path,name)) {
				JOptionPane.showMessageDialog(contentPane, "�Ҳ����ļ���", "����", JOptionPane.ERROR_MESSAGE);
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
				setTitle(currentFileName+"-�ı��༭��");
				textPane.setCaretPosition(0);
				isChange=false;
			}
		}
	}
	
	/*
	 * �����ı�
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
			JOptionPane.showMessageDialog(contentPane, "û�ҵ�"+originRegex, "����ʧ��", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*
	 * �滻ȫ��ƥ����ı�
	 */
	public void replaceAllText(String regex,String str) {
		String text=this.textPane.getText();
		text=text.replaceAll(regex, str);
		this.textPane.setText(text);
	}
	
	/*
	 * �滻��ǰѡ����ı� 
	 */
	public void replaceSelectingText(String str) {
		if(this.textPane.getSelectionStart()!=this.textPane.getSelectionEnd()) {
			this.textPane.replaceSelection(str);
		}
	}
	
	/*
	 * ɾ���ļ�
	 */
	private static void delDir(String path) {
		File file = new File(path);
		if(!file.exists()) {
			System.err.println("�ļ�Ŀ¼�����ڣ�");
			return;
		}
		if(!file.delete()) {
			System.err.println(file.getName()+"�ļ�ɾ��ʧ��");
			return;
		}
	}
	
	/*
	 * ����·���Ƿ���ȷ����
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
