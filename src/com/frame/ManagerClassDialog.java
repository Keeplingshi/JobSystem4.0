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
import com.other.ClassInfoTable;
import com.sql.SqlModel;
import com.tools.Tool;


@SuppressWarnings("serial")
public class ManagerClassDialog extends JFrame{

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private JPanel jp=null;
	private JPanel jp_south=null;
	private JTable jt=null;
	private JScrollPane jsp=null;
	private JLabel classJlb=null;
	
	private JButton addButton=null;
	private JButton modifyButton=null;
	private JButton deleteButton=null;
	
	ClassInfoTable cit=null;
	JComboBox classJcb=null;
	String userId=null;
	
	public ManagerClassDialog(final JComboBox classJcb,final String userId)
	{
		this.userId=userId;
		jp=new JPanel(new BorderLayout());
		
		classJlb=new JLabel("                  班级信息");
	    classJlb.setFont(new Font("宋体", 1, 20));
		jp.add(classJlb,BorderLayout.NORTH);
		
		String sql="select * from classinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
		String[] paras={"1"};
		cit=new ClassInfoTable();
		cit.classTableQuery(sql, paras);
		jt=new JTable(cit);
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
				
				AddNewClassDialog addNewClassDialog=new AddNewClassDialog(classJcb,userId);
				
				addNewClassDialog.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							
							refresh(jt);

						}
				});	
				

			}
		});
		
		modifyButton=new JButton("    修改    ");
		modifyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int RowNum=jt.getSelectedRow();
				if(RowNum==-1){
					JOptionPane.showMessageDialog(jt, "请选择一行");
				    return ;
			    }
				
				String[] info=new String[4];
		        for (int i = 0; i < info.length; i++) 
		        {
		            info[i] = ((String)cit.getValueAt(RowNum, i + 1));
		        }
				ModifyClassDialog modifyClassDialog=new ModifyClassDialog(userId,info);
				
				modifyClassDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
						refresh(jt);

					}
			});	
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
				
				int isDelete=JOptionPane.showConfirmDialog(null, "是否删除本班级所有成员数据？");
				
				if(isDelete==JOptionPane.YES_OPTION){
					String className=(String)cit.getValueAt(RowNum, 1);
					String deleteSql="delete from classinfo where grade='"+Tool.getGrade(userId)+"' and className=?";
					String[] dleteParas={Tool.string2UTF8(className)};
					
					if(cit.Update(deleteSql, dleteParas)){
						
						String monitorId=(String)cit.getValueAt(RowNum, 4);
						String usql="delete from userinfo where id='"+monitorId+"'";
						SqlModel.updateInfo(usql);
						
						String djsql="delete from jobinfo where grade='"+Tool.getGrade(userId)+"' and class='"+Tool.string2UTF8(className)+"'";
						SqlModel.updateInfo(djsql);
						
						JOptionPane.showMessageDialog(jt, "删除成功");
					}else{
						JOptionPane.showMessageDialog(jt, "删除失败");
					}

					refresh(jt);
					
				}

			}
		});
		jp_south.add(addButton);
		jp_south.add(modifyButton);
		jp_south.add(deleteButton);
		
		jp.add(jp_south,BorderLayout.SOUTH);
		
		this.add(jp);
		
		this.setTitle("班级信息");
		this.setBounds(this.screenSize.width / 2 - 250, this.screenSize.height / 2 - 250, 500, 450);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void refresh(JTable jt)
	{
		String refreshSql="select * from classinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
		String[] refreshParas={"1"};
		
		cit=new ClassInfoTable();
		cit.classTableQuery(refreshSql, refreshParas);
		jt.setModel(cit);
		initJTable(jt);
	}
	
	private void initJTable(JTable jt)
	{
		 TableColumnModel tm=jt.getColumnModel();
		 tm.getColumn(0).setPreferredWidth(40);
		 tm.getColumn(1).setPreferredWidth(100);
		 tm.getColumn(2).setPreferredWidth(100);
		 tm.getColumn(3).setPreferredWidth(100);
		 tm.getColumn(4).setPreferredWidth(100);
		 
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
