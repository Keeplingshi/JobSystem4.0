package com.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class ModifyClassDialog extends JFrame{
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	JPanel jp1=null;
	JLabel jlb1,jlb2,jlb3,jlb4;
	JTextField classNameJtf,monitorNameJtf,monitorIdJtf;
	JComboBox majorJcb=null;
	JButton modifyButton=null;
	
	SqlHelper sh=null;
	
//	ClassInfoTable cit=null;
//	JTable jt=null;
	String userId;
	public ModifyClassDialog(final String userId,String[] info) {
		// TODO Auto-generated constructor stub
		this.userId=userId;
//		this.cit=cit;
//		this.jt=jt;
		
		jp1=new JPanel();
		jp1.setLayout(null);
		
		jlb1=new JLabel("班级名称：");
		jlb1.setBounds(30, 20, 100, 40);
		jp1.add(jlb1);
		
		classNameJtf=new JTextField();
		classNameJtf.setText(info[0]);
		classNameJtf.setEditable(false);
		classNameJtf.setBounds(120, 30, 120, 24);
		jp1.add(classNameJtf);
		
		jlb2=new JLabel("专业：");
		jlb2.setBounds(30, 67, 100, 40);
		jp1.add(jlb2);
		
		String[] majorArray=SqlModel.majorInfoQuery(userId);
		majorJcb=new JComboBox(majorArray);
		majorJcb.setSelectedItem(info[1]);
		majorJcb.setBounds(120, 77, 120, 24);
		jp1.add(majorJcb);
		
		jlb3=new JLabel("班长：");
		jlb3.setBounds(30, 114, 100, 40);
		jp1.add(jlb3);
		
		monitorNameJtf=new JTextField();
		monitorNameJtf.setText(info[2]);
		monitorNameJtf.setBounds(120, 124, 120, 24);
		jp1.add(monitorNameJtf);
		
		jlb4=new JLabel("班长学号：");
		jlb4.setBounds(30, 161, 100, 40);
		jp1.add(jlb4);
		
		monitorIdJtf=new JTextField();
		monitorIdJtf.setText(info[3]);
		monitorIdJtf.setBounds(120, 171, 120, 24);
		jp1.add(monitorIdJtf);
		
		modifyButton=new JButton("确定");
		modifyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				String classNameStr=Tool.string2UTF8(classNameJtf.getText().trim());
				String majorStr=Tool.string2UTF8(majorJcb.getSelectedItem().toString().trim());
				String monitorNameStr=Tool.string2UTF8(monitorNameJtf.getText().trim());
				String monitorIdStr=Tool.string2UTF8(monitorIdJtf.getText().trim());
				
				if(classNameStr.equals("")){
					JOptionPane.showMessageDialog(jp1, "请输入班级名称");
				}else if(majorStr.equals("")){
					JOptionPane.showMessageDialog(jp1, "请选择专业");
				}
				
				String sql="update classinfo set major='"+majorStr+"',monitor='"+monitorNameStr+"',monitorId='"+monitorIdStr+"',grade='"+Tool.getGrade(userId)+"' where className='"+classNameStr+"'";
				String dusql="delete from userinfo where id='"+monitorIdStr+"'";
				String usql="insert into userinfo values('"+monitorIdStr+"','"+monitorIdStr+"','"+Tool.getGrade(userId)+"')";
				if(SqlModel.updateInfo(dusql))
				{
					if(SqlModel.updateInfo(usql)&&SqlModel.updateInfo(sql)){
						JOptionPane.showMessageDialog(jp1, "修改成功");
						dispose();
					}else{
						JOptionPane.showMessageDialog(jp1, "修改失败");
					}
				}else{
					JOptionPane.showMessageDialog(jp1, "修改失败");
				}

			}
		});
		modifyButton.setBounds(100, 230, 100, 33);
		jp1.add(modifyButton);
		
		this.add(jp1);
		this.setTitle("添加班级");
		this.setBounds(this.screenSize.width / 2 - 150, this.screenSize.height / 2 - 160, 300, 320);
		this.setResizable(false);
		this.setVisible(true);
		
	}


}
