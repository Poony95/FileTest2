package jtablevectorinputfile;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class JTableTestVectorInputFile extends JFrame {
	int row;
	
	JTable table;
	Vector<String > colName;
	Vector<Vector<String>> rowData;
	
	JTextField jtf_name;
	JTextField jtf_kor;
	JTextField jtf_eng;
	JTextField jtf_math;
	
	public JTableTestVectorInputFile() {
		
	
		jtf_name = new JTextField();
		jtf_kor = new JTextField();
		jtf_eng = new JTextField();
		jtf_math = new JTextField();
		
		colName = new Vector<String>();
		colName.add("이름");
		colName.add("국어");
		colName.add("영어");
		colName.add("수학");
		colName.add("총점");
		colName.add("평균");
		
		rowData = new Vector<Vector<String>>();
		table = new  JTable(rowData, colName);
		JScrollPane jsp = new JScrollPane(table);
		add(jsp, BorderLayout.CENTER);
		
		JPanel p_input  = new JPanel();
		JPanel p_btn = new JPanel();
		JPanel p_down = new JPanel();
		
		p_input.setLayout(new GridLayout(4,2));
		p_down.setLayout(new BorderLayout());
		
		p_input.add(new JLabel("이름 : "));
		p_input.add(jtf_name);
		p_input.add(new JLabel("국어 : "));
		p_input.add(jtf_kor);
		p_input.add(new JLabel("영어 : "));
		p_input.add(jtf_eng);
		p_input.add(new JLabel("수학 : "));
		p_input.add(jtf_math);
		
		JButton btn_prev = new JButton("이전");
		JButton btn_add = new JButton("추가");
		JButton btn_update = new JButton("수정");
		JButton btn_delete = new JButton("삭제");
		JButton btn_save= new JButton("저장");
		JButton btn_read= new JButton("읽어오기");
		JButton btn_next = new JButton("다음");
		
		p_btn.add(btn_prev);
		p_btn.add(btn_add);
		p_btn.add(btn_update);
		p_btn.add(btn_delete);
		p_btn.add(btn_save);
		p_btn.add(btn_read);
		p_btn.add(btn_next);
		
		p_down.add(p_input, BorderLayout.CENTER);
		p_down.add(p_btn, BorderLayout.SOUTH);
		
		add(p_down, BorderLayout.SOUTH);
		
		setSize(500,600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				row = table.getSelectedRow();
				viewData();
			}
		});
		btn_prev.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(row == 0) {
					JOptionPane.showMessageDialog(null, "처음 데이터입니다.");
				}else {
					row--;
					viewData();
				}
			}
		});
		btn_next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(row == rowData.size()-1) {
					JOptionPane.showMessageDialog(null,"마지막 데이터입니다.");
				}else {
					row++;
					viewData();
				}
			}
		});
		btn_read.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("읽어오기 동작");
				try {
					ObjectInputStream ois 
					= new ObjectInputStream(new FileInputStream("c:/temp/student.dat"));
					Vector<Vector<String>> list=(Vector<Vector<String>>) ois.readObject();
					for(Vector<String> row:list) {
						rowData.add(row);
					}
					System.out.println(rowData);
					table.updateUI();
					ois.close();
					viewData();
				}catch (Exception ex) {
					System.out.println("예외발생:"+ex.getMessage());
				}
			}
		});
		btn_save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("c:/temp/student.dat"));
					oos.writeObject(rowData);
					oos.close();
					JOptionPane.showMessageDialog(null, "파일로 저장하였습니다.");
				} catch (Exception e2) {
					System.out.println("예외발생"+e2.getMessage());
				}
			}
		});
		btn_delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int re = JOptionPane.showConfirmDialog(null, "정말 삭제할까요?");
				
				if(re == 0) {
					rowData.remove(row);
					table.updateUI();
				}
			}
		});
		btn_update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int kor = Integer.parseInt(jtf_kor.getText());
				int eng = Integer.parseInt(jtf_eng.getText());
				int math = Integer.parseInt(jtf_math.getText());
				int tot = kor + eng + math;
				int avg = tot /3;
				
				Vector<String> v = new Vector<String>();
				v.add(jtf_name.getText());
				v.add(jtf_kor.getText());
				v.add(jtf_eng.getText());
				v.add(jtf_math.getText());
				v.add(tot + "");
				v.add(avg+"");
				
				rowData.set(row, v);
				table.updateUI();
			}
		});
		btn_add.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int kor = Integer.parseInt(jtf_kor.getText());
				int eng = Integer.parseInt(jtf_eng.getText());
				int math = Integer.parseInt(jtf_math.getText());
				int tot = kor +eng + math;
				int avg = tot /3 ;
				
				Vector<String>  v = new Vector<>();
				v.add(jtf_name.getText());
				v.add(jtf_kor.getText());
				v.add(jtf_eng.getText());
				v.add(jtf_math.getText());
				v.add(tot+"");
				v.add(avg+"");
				rowData.add(v);
				table.updateUI();
				jtf_name.setText("");
				jtf_kor.setText("");
				jtf_eng.setText("");
				jtf_math.setText("");
			}
		});
	}
	
	public void viewData() {
		Vector<String> r= rowData.get(row);
		jtf_name.setText(r.get(0));
		jtf_kor.setText(r.get(1));
		jtf_eng.setText(r.get(2));
		jtf_math.setText(r.get(3));
	}
	
	public static void main(String[] args) {
		new JTableTestVectorInputFile();
	}

}
