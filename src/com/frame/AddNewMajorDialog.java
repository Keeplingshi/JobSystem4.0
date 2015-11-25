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
import javax.swing.JTextField;

import com.sql.SqlHelper;
import com.sql.SqlModel;
import com.tools.Tool;

@SuppressWarnings("serial")
public class AddNewMajorDialog extends JFrame{

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	JPanel jp1=null;
	JLabel jlb1=null;
	JTextField majorNameJtf;
	JButton addButton=null;
	JButton cancelButton=null;
	
	SqlHelper sh=null;
	String userId=null;
	
	public AddNewMajorDialog(final String userId)
	{
		this.userId=userId;
		jp1=new JPanel();
		jp1.setLayout(null);
		
		jlb1=new JLabel("专业名称：");
		jlb1.setBounds(30, 20, 100, 40);
		jp1.add(jlb1);
		
		majorNameJtf=new JTextField();
		majorNameJtf.setBorder(BorderFactory.createBevelBorder(1));
		majorNameJtf.setBounds(120, 30, 120, 24);
		jp1.add(majorNameJtf);
		
		addButton=new JButton("确定");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				String majorName=majorNameJtf.getText().trim();
				if(majorName!=null&&!majorName.equals("")){
					String imSql="insert into majorinfo(majorName,grade) values('"+Tool.string2UTF8(majorName)+"','"+Tool.getGrade(userId)+"')";
					if(SqlModel.updateInfo(imSql)){
						dispose();
					}else{
						JOptionPane.showMessageDialog(null, "添加失败");
					}
				}else{
					JOptionPane.showMessageDialog(null, "请输入专业名称");
				}
			}
		});
		addButton.setBounds(50, 80, 80, 33);
		jp1.add(addButton);
		
		cancelButton=new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		cancelButton.setBounds(160, 80, 80, 33);
		jp1.add(cancelButton);
		
		this.add(jp1);
		this.setTitle("添加专业");
		this.setBounds(this.screenSize.width / 2 - 150, this.screenSize.height / 2 - 120, 300, 187);
		this.setResizable(false);
		this.setVisible(true);
	}
	
}
