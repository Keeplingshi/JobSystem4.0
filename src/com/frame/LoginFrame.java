package com.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sql.SqlModel;
import com.tools.Tool;

public class LoginFrame extends JFrame{

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	JPanel jp1=null; 
	
	JLabel jlb1,jlb2,jlb3;
	JTextField jtf;
	JPasswordField jpf;
	JButton jb=null;
	
	public LoginFrame() {
		// TODO Auto-generated constructor stub
		
		jp1=new JPanel();
		jp1.setLayout(null);
		
		jlb1=new JLabel("用户名:");
		jlb1.setBounds(40, 20, 100, 60);
		jp1.add(jlb1);
		
		jtf=new JTextField();
		jtf.setText("5237");
		jtf.setBorder(BorderFactory.createLoweredBevelBorder()); 
		jtf.setBounds(120, 40, 120, 24);
		jp1.add(jtf);
		
		
		jlb2=new JLabel("密码：");
		jlb2.setBounds(40, 70, 100, 60);
		jp1.add(jlb2);
		
		jpf=new JPasswordField();
		jpf.addKeyListener(new KeyAdapter() 
		{ 
			public void keyPressed(KeyEvent e) 
			{ 
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					jb.doClick(); 
				}
				
			}
			
		} );
		jpf.setBorder(BorderFactory.createLoweredBevelBorder()); 
		jpf.setBounds(120, 90, 120, 24);
		jp1.add(jpf);
		
		jlb3=new JLabel("用户名或密码错误");
		jlb3.setForeground(Color.RED);
		jlb3.setBounds(170, 120, 140, 40);
		jlb3.setVisible(false);
		jp1.add(jlb3);
		
		jb=new JButton("登录");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(jtf.getText().trim().equals("")||new String(jpf.getPassword()).trim().equals("")){
					jlb3.setText("请输入用户名或密码");
					jlb3.setVisible(true);
				}else{
					String sql="select passwd from userinfo where id='"+Tool.string2UTF8(jtf.getText().trim())+"'";
					String passwd=SqlModel.getInfo(sql);
					//System.out.println(passwd+"    "+new String(jpf.getPassword()));
					if(passwd.equals(new String(jpf.getPassword()).trim())){
						MainFrame mainFrame=new MainFrame(jtf.getText().trim());
						dispose();
					}else{
						jpf.setText("");
						jlb3.setText("用户名或密码错误");
						jlb3.setVisible(true);
					}
				}

			}
		});

		jb.setBounds(120, 170, 100, 33);
		jp1.add(jb);
		
		this.add(jp1);
		this.setTitle("毕业生就业管理系统");
		this.setBounds(this.screenSize.width / 2 - 170, this.screenSize.height / 2 - 150, 340, 300);
		this.setResizable(false);
		this.setVisible(true);
	}

	
}
