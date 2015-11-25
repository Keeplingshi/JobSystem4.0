package com.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.other.GradeInfoTable;
import com.sql.SqlModel;
import com.tools.Tool;

public class ManagerGradeDialog extends JFrame{

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private JPanel jp=null;
	private JPanel jp_south=null;
	private JTable jt=null;
	private JScrollPane jsp=null;
	private JLabel classJlb=null;
	
	private JButton addButton=null;
	private JButton deleteButton=null;
	
	GradeInfoTable git=null;
	JComboBox classJcb=null;
	
	public ManagerGradeDialog()
	{
		
		jp=new JPanel(new BorderLayout());
		
		classJlb=new JLabel("               年级信息");
	    classJlb.setFont(new Font("宋体", 1, 20));
		jp.add(classJlb,BorderLayout.NORTH);
		
		String sql="select grade from gradeinfo where 1=?";
		String[] paras={"1"};
		git=new GradeInfoTable();
		git.gradeTableQuery(sql, paras);
		jt=new JTable(git);
		initJTable(jt);
		jsp=new JScrollPane(jt);
		jp.add(jsp,BorderLayout.CENTER);
		
		jp.add(new JLabel("            "),BorderLayout.EAST);
		jp.add(new JLabel("            "),BorderLayout.WEST);
		
		jp_south=new JPanel(new FlowLayout(FlowLayout.CENTER,36,10));
		addButton=new JButton("    添加     ");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String gradeNewIn=JOptionPane.showInputDialog("请输入年级");
				if(gradeNewIn!=null){
					if(!gradeNewIn.equals("")){
						String gradeun="select id from gradeinfo where grade='"+gradeNewIn.trim()+"'";
						String gradeResult=SqlModel.getInfo(gradeun).trim();
						
						if(gradeResult.equals("0")||gradeResult.equals("1")){
							JOptionPane.showMessageDialog(null, "该年级已存在");					
						}else{
							String isql="insert into gradeinfo values('0','"+gradeNewIn.trim()+"')";
							if(SqlModel.updateInfo(isql)){
								refresh(jt);
							}
						}					
					}
				}
				
			}
		});
		
		deleteButton=new JButton("    删除    ");
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int RowNum=jt.getSelectedRow();
				if(RowNum==-1){
					JOptionPane.showMessageDialog(jt, "请选择一行");
				    return ;
			    }
				
				int isDelete=JOptionPane.showConfirmDialog(null, "是否确定删除该年级包括专业，班级，学生在内所有数据？");
				
				if(isDelete==JOptionPane.YES_OPTION){
					String gradeName=(String)git.getValueAt(RowNum, 1);
					String deleteSql="delete from gradeinfo where grade=?";
					String deletemajor="delete from majorinfo where grade=?";
					String deleteclass="delete from classinfo where grade=?";
					String deletestudent="delete from jobinfo where grade=?";
					String deleteuser="delete from userinfo where grade=?";
					String[] dleteParas={Tool.string2UTF8(gradeName)};
					
					git.Update(deletemajor, dleteParas);
					git.Update(deleteclass, dleteParas);
					git.Update(deletestudent, dleteParas);
					git.Update(deleteuser, dleteParas);
					
					if(git.Update(deleteSql, dleteParas)){
						refresh(jt);
					}else{
						JOptionPane.showMessageDialog(jt, "删除失败");
					}

					refresh(jt);
					
				}

			}
		});
		jp_south.add(addButton);
		jp_south.add(deleteButton);
		
		jp.add(jp_south,BorderLayout.SOUTH);
		
		this.add(jp);
		
		this.setTitle("专业信息");
		this.setBounds(this.screenSize.width / 2 - 200, this.screenSize.height / 2 - 250, 400, 420);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void refresh(JTable jt)
	{
		String refreshSql="select grade from gradeinfo where 1=?";
		String[] refreshParas={"1"};
		
		git=new GradeInfoTable();
		git.gradeTableQuery(refreshSql, refreshParas);
		jt.setModel(git);
		initJTable(jt);
	}
	
	private void initJTable(JTable jt)
	{
		 TableColumnModel tm=jt.getColumnModel();
		 tm.getColumn(0).setPreferredWidth(130);
		 tm.getColumn(1).setPreferredWidth(270);
		 
		 jt.setRowHeight(23);
		 
		 DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	     render.setHorizontalAlignment(SwingConstants.CENTER);
	     for(int i=0;i<tm.getColumnCount();i++)
	     {
	    	 tm.getColumn(i).setCellRenderer(render);
	     }
	     
		 jt.setOpaque(false);
	}
	
}
