package com.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.sql.SqlHelper;
import com.sql.SqlModel;
import com.tools.Tool;

@SuppressWarnings("serial")
public class AddNewClassDialog extends JFrame{
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	JPanel jp1=null;
	JLabel jlb1,jlb2,jlb3,jlb4;
	JTextField classNameJtf,monitorNameJtf,monitorIdJtf;
	JComboBox majorJcb=null;
	JButton addButton=null;
	
	SqlHelper sh=null;
	String userId=null;
	
	public AddNewClassDialog(final JComboBox classJcb,final String userId) {
		// TODO Auto-generated constructor stub
		
		this.userId=userId;
		jp1=new JPanel();
		jp1.setLayout(null);
		
		jlb1=new JLabel("班级名称：");
		jlb1.setBounds(30, 20, 100, 40);
		jp1.add(jlb1);
		
		classNameJtf=new JTextField();
		classNameJtf.setBounds(120, 30, 120, 24);
		jp1.add(classNameJtf);
		
		jlb2=new JLabel("专业：");
		jlb2.setBounds(30, 67, 100, 40);
		jp1.add(jlb2);
		
		String[] majorArray=SqlModel.majorInfoQuery(userId);
		majorJcb=new JComboBox(majorArray);
		majorJcb.setBounds(120, 77, 120, 24);
		jp1.add(majorJcb);
		
		jlb3=new JLabel("班长：");
		jlb3.setBounds(30, 114, 100, 40);
		jp1.add(jlb3);
		
		monitorNameJtf=new JTextField();
		monitorNameJtf.setBounds(120, 124, 120, 24);
		jp1.add(monitorNameJtf);
		
		jlb4=new JLabel("班长学号：");
		jlb4.setBounds(30, 161, 100, 40);
		jp1.add(jlb4);
		
		monitorIdJtf=new JTextField();
		monitorIdJtf.setBounds(120, 171, 120, 24);
		jp1.add(monitorIdJtf);
		
		addButton=new JButton("确定");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				String temp=classJcb.getSelectedItem().toString().trim();
				String classNameStr=Tool.string2UTF8(classNameJtf.getText().trim());
				String majorStr=Tool.string2UTF8(majorJcb.getSelectedItem().toString().trim());
				String monitorNameStr=Tool.string2UTF8(monitorNameJtf.getText().trim());
				String monitorIdStr=Tool.string2UTF8(monitorIdJtf.getText().trim());
				
				if(classNameStr.equals("")){
					JOptionPane.showMessageDialog(jp1, "请输入班级名称");
				}else if(majorStr.equals("")){
					JOptionPane.showMessageDialog(jp1, "请选择专业");
				}
				
				String sql="insert into classinfo values('"+classNameStr+"','"+majorStr+"','"+monitorNameStr+"','"+monitorIdStr+"','"+Tool.getGrade(userId)+"')";
				
				if(SqlModel.updateInfo(sql)){
					
					String usql="insert into userinfo values('"+monitorIdStr+"','"+monitorIdStr+"','"+Tool.getGrade(userId)+"')";
					//System.out.println(usql);
					SqlModel.updateInfo(usql);
					
					JOptionPane.showMessageDialog(jp1, "添加成功");
					dispose();
				}else{
					JOptionPane.showMessageDialog(jp1, "添加失败");
				}
				
				String classArraySql="select className from classinfo where grade='"+Tool.getGrade(userId)+"'";
				String[] classArray=SqlModel.classInfoQuery(classArraySql);
				classJcb.setModel(new DefaultComboBoxModel(classArray));
				classJcb.setSelectedItem(temp);
				
			}
		});
		addButton.setBounds(100, 230, 100, 33);
		jp1.add(addButton);
		
		this.add(jp1);
		this.setTitle("添加班级");
		this.setBounds(this.screenSize.width / 2 - 150, this.screenSize.height / 2 - 160, 300, 320);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
}
