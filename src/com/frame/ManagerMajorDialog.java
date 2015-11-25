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

import com.other.MajorInfoTable;
import com.sql.SqlModel;
import com.tools.Tool;

@SuppressWarnings("serial")
public class ManagerMajorDialog extends JFrame{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private JPanel jp=null;
	private JPanel jp_south=null;
	private JTable jt=null;
	private JScrollPane jsp=null;
	private JLabel classJlb=null;
	
	private JButton addButton=null;
	private JButton modifyButton=null;
	private JButton deleteButton=null;
	
	MajorInfoTable mit=null;
	JComboBox classJcb=null;
	String userId=null;
	
	public ManagerMajorDialog(final String userId)
	{
		this.userId=userId;
		jp=new JPanel(new BorderLayout());
		
		classJlb=new JLabel("               רҵ��Ϣ");
	    classJlb.setFont(new Font("����", 1, 20));
		jp.add(classJlb,BorderLayout.NORTH);
		
		String sql="select majorName from majorinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
		String[] paras={"1"};
		mit=new MajorInfoTable();
		mit.majorTableQuery(sql, paras);
		jt=new JTable(mit);
		initJTable(jt);
		jsp=new JScrollPane(jt);
		jp.add(jsp,BorderLayout.CENTER);
		
		jp.add(new JLabel("            "),BorderLayout.EAST);
		jp.add(new JLabel("            "),BorderLayout.WEST);
		
		jp_south=new JPanel(new FlowLayout(FlowLayout.CENTER,36,10));
		addButton=new JButton("    ���     ");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				AddNewMajorDialog addNewMajorDialog=new AddNewMajorDialog(userId);
				
				addNewMajorDialog.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							refresh(jt);
						}
				});	
				
			}
		});
		
		modifyButton=new JButton("    �޸�    ");
		modifyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int RowNum=jt.getSelectedRow();
				if(RowNum==-1){
					JOptionPane.showMessageDialog(jt, "��ѡ��һ��");
				    return ;
			    }
		        
		        String info=((String)mit.getValueAt(RowNum, 1));
		        
				String majorNewIn=JOptionPane.showInputDialog("���޸�רҵ",info);
				if(majorNewIn!=null)
				{
					if(!majorNewIn.equals("")){
						
						String majorIdSql="select id from majorinfo where majorName='"+Tool.string2UTF8(info)+"'";
						String majorid=SqlModel.getInfo(majorIdSql);
						
						String mclassSql="update classinfo set major='"+Tool.string2UTF8(majorNewIn)+"' where major='"+Tool.string2UTF8(info)+"'";
						String mjobSql="update jobinfo set major='"+Tool.string2UTF8(majorNewIn)+"' where major='"+Tool.string2UTF8(info)+"'";
						String mmajorSql="update majorinfo set majorName='"+Tool.string2UTF8(majorNewIn)+"' where id='"+majorid+"'";
						
						SqlModel.updateInfo(mclassSql);
						SqlModel.updateInfo(mjobSql);
						if(SqlModel.updateInfo(mmajorSql)){
							refresh(jt);
						}else{
							JOptionPane.showMessageDialog(null, "�޸�ʧ��");
						}
									
					}
				}

		        
			}
		});
		
		deleteButton=new JButton("    ɾ��    ");
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int RowNum=jt.getSelectedRow();
				if(RowNum==-1){
					JOptionPane.showMessageDialog(jt, "��ѡ��һ��");
				    return ;
			    }
				
				int isDelete=JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ����רҵ�Լ���רҵ���а༶��ѧ����Ϣ��");
				
				if(isDelete==JOptionPane.YES_OPTION){
					String majorName=(String)mit.getValueAt(RowNum, 1);
					String deleteSql="delete from majorinfo where grade='"+Tool.getGrade(userId)+"' and majorName=?";
					String deleteclass="delete from classinfo where grade='"+Tool.getGrade(userId)+"' and major=?";
					String deletejob="delete from jobinfo where grade='"+Tool.getGrade(userId)+"' and major=?";
					
					String[] dleteParas={Tool.string2UTF8(majorName)};
					
					mit.Update(deleteclass, dleteParas);
					mit.Update(deletejob, dleteParas);
					
					if(mit.Update(deleteSql, dleteParas)){
						refresh(jt);
					}else{
						JOptionPane.showMessageDialog(jt, "ɾ��ʧ��");
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
		
		this.setTitle("רҵ��Ϣ");
		this.setBounds(this.screenSize.width / 2 - 200, this.screenSize.height / 2 - 250, 400, 420);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void refresh(JTable jt)
	{
		String refreshSql="select majorname from majorinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
		String[] refreshParas={"1"};
		
		mit=new MajorInfoTable();
		mit.majorTableQuery(refreshSql, refreshParas);
		jt.setModel(mit);
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
