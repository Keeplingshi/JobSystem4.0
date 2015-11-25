package com.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sql.SqlModel;

public class ManagerUserDialog extends JFrame{

	private String userId=null;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	JPanel jp1=null;
	JLabel jlb1,jlb2,jlb3,jlb4;
	JTextField idJtf;
	JPasswordField oldJpf,newJpf,confirmJpf;
	JButton confirmButton=null;

	public ManagerUserDialog(final String userId)
	{
		this.userId=userId;
		
		jp1=new JPanel();
		jp1.setLayout(null);
		
		jlb1=new JLabel("用户名（学号）：");
		jlb1.setBounds(30, 20, 100, 40);
		jp1.add(jlb1);
		
		idJtf=new JTextField();
		idJtf.setText(userId);
		idJtf.setEditable(false);
		idJtf.setBounds(120, 30, 120, 24);
		jp1.add(idJtf);
		
		jlb2=new JLabel("原密码：");
		jlb2.setBounds(30, 67, 100, 40);
		jp1.add(jlb2);
		
		oldJpf=new JPasswordField();
		oldJpf.setBorder(BorderFactory.createLoweredBevelBorder()); 
		oldJpf.setBounds(120, 77, 120, 24);
		jp1.add(oldJpf);
		
		jlb3=new JLabel("密码：");
		jlb3.setBounds(30, 114, 100, 40);
		jp1.add(jlb3);
		
		newJpf=new JPasswordField();
		newJpf.setBorder(BorderFactory.createLoweredBevelBorder()); 
		newJpf.setBounds(120, 124, 120, 24);
		jp1.add(newJpf);
		
		jlb4=new JLabel("确认密码：");
		jlb4.setBounds(30, 161, 100, 40);
		jp1.add(jlb4);
		
		confirmJpf=new JPasswordField();
		confirmJpf.setBorder(BorderFactory.createLoweredBevelBorder()); 
		confirmJpf.setBounds(120, 171, 120, 24);
		jp1.add(confirmJpf);
		
		confirmButton=new JButton("确定");
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String oldPasswd=new String(oldJpf.getPassword()).trim();
				String newPasswd=new String(newJpf.getPassword()).trim();
				String confirmPasswd=new String(confirmJpf.getPassword()).trim();
				
				String sql="select passwd from userinfo where id='"+userId+"'";
				String checkPasswd=SqlModel.getInfo(sql);
				System.out.println("checkPasswd:"+checkPasswd);
				
				if(oldPasswd.equals(checkPasswd))
				{
					if(newPasswd.equals(confirmPasswd))
					{
						String usql="update userinfo set passwd='"+newPasswd+"' where id='"+userId+"'";
						if(SqlModel.updateInfo(usql)){
							dispose();
						}else{
							JOptionPane.showMessageDialog(jp1, "修改失败");
						}
						
					}else{
						newJpf.setText("");
						confirmJpf.setText("");
						JOptionPane.showMessageDialog(jp1, "两次输入密码不一致");
					}
				}else{
					oldJpf.setText("");
					newJpf.setText("");
					confirmJpf.setText("");
					JOptionPane.showMessageDialog(jp1, "原密码错误");
				}
				
			}
		});
		confirmButton.setBounds(100, 230, 100, 33);
		jp1.add(confirmButton);
		
		this.add(jp1);
		this.setTitle("个人信息管理");
		this.setBounds(this.screenSize.width / 2 - 150, this.screenSize.height / 2 - 160, 300, 320);
		this.setResizable(false);
		this.setVisible(true);
	}
	
}
