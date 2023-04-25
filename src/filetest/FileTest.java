package filetest;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FileTest extends JFrame {

	Vector<String> data;
	JList<String> list;
	JTextArea jta;
	String path = "c:/temp/test";
	public void loadFileTest() {
		
		File dir = new File(path);
		String []file_list = dir.list(); //.list() 불러오는 메소드
		for(String fname : file_list) {
			File f = new File(path + "/" + fname);
			if(f.isFile()) {
				String ext = fname.substring(fname.lastIndexOf("."));
				if(ext.equals(".java")) {
					data.add(fname);
				}
			}
		}
		list.updateUI();
	}
	
	public FileTest() {
		data = new Vector<String>();
		list = new JList<String>(data);
		jta = new JTextArea(10,80);
		JScrollPane jsp_jta = new JScrollPane(jta);
		JScrollPane jsp_list = new JScrollPane(list);
		
		JButton btn = new JButton("읽어오기");
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(jsp_list,BorderLayout.CENTER);
		p1.add(btn,BorderLayout.SOUTH);
		add(p1, BorderLayout.WEST);
		add(jsp_jta, BorderLayout.CENTER);
		
		loadFileTest();
		setSize(700,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String fname = list.getSelectedValue();	// 파일의 안에 있는 내용을 불러오는 메소드 
				if( fname != null) {
					readfile(fname);
				}
			}
		});
	}
	protected void readfile (String fname) {
		try {
			FileReader fr = new FileReader(path + "/"+ fname);
			int ch;
			String str ="";
			while( (ch = fr.read()) != -1) {
				str = str + (char)ch;
			}
			jta.setText(str);
			fr.close();
		} catch (Exception e) {
			System.out.println("예외발생 :"+ e.getMessage());
		}
	}
	public static void main(String[] args) {
		new FileTest();
	}

}
