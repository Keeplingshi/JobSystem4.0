package com.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.other.ExcelDo;
import com.other.JobInfoTable;
import com.sql.SqlModel;
import com.tools.Tool;

public class MainFrame{

	private String userId=null;
	
	JFrame mainFrame=new JFrame();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private JPanel jpNorth=null;
	private JPanel jpSouth=null;
	private JPanel jpCenter=null;
	
	JComboBox classJcb=null;
	JComboBox majorJcb=null;
	
	JTable jt=null;
	JTable jp5_jt=null;
	
	String[] header = { "班级人数", "保研", "考研", "已签约", "未签约", "考公务员", 
		    "已签约平均工资", "出国", "统计日期" };
	Object[][] cell = new Object[1][9];
	
	JobInfoTable jit=null;
	
	JLabel infoLabel=null;
	DefaultTableModel dt=null;
	JButton addClassButton=null;
	JButton addMajorButton=null;
	JButton addNewButton=null;
	JButton userButton=null;
	JTextField searchJtf=null;
	JComboBox gradeJcb=null;
	
	public MainFrame(String userId)
	{
		this.userId=userId;
		
		mainFrame.setTitle("班级就业情况管理系统 2.0版本");
		//System.out.println(this.screenSize.getWidth()+"  "+this.screenSize.getHeight());
		mainFrame.setSize((int)this.screenSize.getWidth(),(int)(this.screenSize.getHeight() - 45.0D));
		mainFrame.setMinimumSize(new Dimension(1025, 630));
		
		initNorth();
		intiSouth();
		initCenter();
		
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(jpNorth,BorderLayout.NORTH);
		mainFrame.add(jpSouth,BorderLayout.SOUTH);
		mainFrame.add(jpCenter,BorderLayout.CENTER);
		mainFrame.add(new JLabel("            "),BorderLayout.EAST);
		mainFrame.add(new JLabel("            "),BorderLayout.WEST);
		
	    mainFrame.setVisible(true);
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * 中部面板
	 */
	private void initCenter()
	{
		
		jpCenter=new JPanel();
		
		GridBagLayout gridbag =new GridBagLayout();
		
		jpCenter.setLayout(gridbag);
		
		GridBagConstraints c= new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		 
		JPanel jp1;
		jp1=new JPanel(new BorderLayout());
		initCenterJPabel1(jp1);
		c.gridwidth=GridBagConstraints.REMAINDER;		 
		c.weightx=1;
		c.weighty=0;
		gridbag.setConstraints(jp1, c);
		jpCenter.add(jp1);
		 
		 JLabel jlb1=new JLabel("就业信息：");
		 c.weightx=1;
		 c.weighty=0;
		 gridbag.setConstraints(jlb1, c);
		 jpCenter.add(jlb1);
		 
		 //就业信息表格显示
		 //jp4=new JPanel(new BorderLayout());
//		 String sql="select class 班级,id 学号 ,studentName 姓名,sex 性别,state 签约状态,company 签约单位,protocalState 协议书状态," +
//					"nowState 当前状态,area 签约单位所在地区,city 签约单位所在城市,salary 薪金, note 备注（主要写最近做什么） from jobinfo where 1=?";
		 
		 String sql="";
		 if(userId.equals("admin")||userId.equals("5237"))
		 {
			 sql="select class,id,studentName,sex,state,company,protocalState," +
						"nowState,area,city,salary,note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
			 String []paras={"1"};
			 jit=new JobInfoTable();
			 jit.jobTableQuery(sql, paras);
		 }else{
			 sql="select class,id,studentName,sex,state,company,protocalState," +
						"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and class=?";
			 
			  String csql="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and monitorId='"+userId+"'";
			  String classNameFromSql=SqlModel.getInfo(csql).trim();
			 String []paras={Tool.string2UTF8(classNameFromSql)};
			 jit=new JobInfoTable();
			 jit.jobTableQuery(sql, paras);
		 }
		 

		 jt=new JTable(jit);
//		 RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jit);  
//	     jt.setRowSorter(sorter);  
//		 jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		 jt.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getButton() == MouseEvent.BUTTON3) {
					if(userId.equals("admin")||userId.equals("5237"))
					{
						selectTable(jt, e);
						JPopupMenu popupmenu = new JPopupMenu();
						JMenuItem menuItem1=new JMenuItem("标记");
						JMenuItem menuItem2=new JMenuItem("取消标记");
						JMenuItem menuItem3=new JMenuItem("查找标记");
						popupmenu.add(menuItem1);
						popupmenu.add(menuItem2);
						popupmenu.add(menuItem3);
						popupmenu.show(e.getComponent(), e.getX(), e.getY());
						
						menuItem1.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent ae) {
								// TODO Auto-generated method stub
								//System.out.println("标记");
								int row = jt.convertRowIndexToModel(jt.getSelectedRow());
								String menuXuehao = ((String)jit.getValueAt(row, 2));
								String menuSql="update jobinfo set positive='1',mtime='"+Tool.whoModify(userId)+"' where grade='"+Tool.getGrade(userId)+"' and id='"+menuXuehao+"'";
								SqlModel.updateInfo(menuSql);
								refresh();
							}
						});
						
						
						menuItem2.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent ae) {
								// TODO Auto-generated method stub
								//System.out.println("取消标记");
								int row = jt.convertRowIndexToModel(jt.getSelectedRow());
								String menuXuehao = ((String)jit.getValueAt(row, 2));
								String menuSql="update jobinfo set positive='0',mtime='"+Tool.whoModify(userId)+"' where grade='"+Tool.getGrade(userId)+"' and id='"+menuXuehao+"'";
								SqlModel.updateInfo(menuSql);
								
								refresh();
							}
						});
						
						menuItem3.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent ae) {
								// TODO Auto-generated method stub
								
								String menuSql="select class,id,studentName,sex,state,company,protocalState," +
										"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and positive=?";
								String []menuparas={"1"};
								
								String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"' and positive='1'";
								refreshTable(menuSql,menuparas,jt,SqlModel.jobPositiveQuery(cellSql));
								initJTable5(jp5_jt,"",3);
							}
						});
					}

					
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==2)
				{
					
					 int row = jt.convertRowIndexToModel(jt.getSelectedRow());
				     String[] lie = new String[12];
				     for (int i = 0; i < lie.length; i++) 
				     {
				         lie[i] = ((String)jit.getValueAt(row, i + 1));
				     }
				     
				     final String modifyXuehao=lie[1];
				     
				     ModifyDialog modifyDialog=null;
				     
				     if(userId.equals("admin")||userId.equals("5237")){
				    	 modifyDialog=new ModifyDialog(userId,lie);
				     }else{
						String csql="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and monitorId='"+userId+"'";
						String classNameFromSql=SqlModel.getInfo(csql).trim();	//汉字
						
						if(lie[0].equals(classNameFromSql)){
							modifyDialog=new ModifyDialog(userId,lie);
						}else{
							//JOptionPane.showMessageDialog(jt, "对不起，该同学不是你班学生");
						}
			        }
					
				     if(modifyDialog!=null){
					     modifyDialog.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosed(WindowEvent e) {
									// TODO Auto-generated method stub
									
									refresh();
									findInTable(modifyXuehao);
								}
						});				    	 
				     }
				     

				     
				}
			}
		});
		 String cellSql="select id,positive from jobinfo";
		 initJTable(jt,SqlModel.jobPositiveQuery(cellSql));
		 JScrollPane jsp=new JScrollPane(jt);
		 jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		 jsp.setOpaque(false);
		 jsp.getViewport().setOpaque(false);
		 c.weightx=1;
		 c.weighty=3;
		 jsp.setPreferredSize(new Dimension(0, 300));
		 gridbag.setConstraints(jsp, c);
		 jpCenter.add(jsp);
		 
		 
		 JLabel infoLabel3=new JLabel();
		 infoLabel3.setText("         ");
		 c.weightx=0;
		 c.weighty=0;
		 gridbag.setConstraints(infoLabel3, c);
		 jpCenter.add(infoLabel3);
		 
		 infoLabel=new JLabel();
		 c.gridheight=1;
		 c.weightx=0;
		 c.weighty=0;
		 gridbag.setConstraints(infoLabel, c);
		 jpCenter.add(infoLabel);
		 
		 JLabel infoLabel5=new JLabel();
		 infoLabel5.setMaximumSize(new Dimension(400, 30));
		 infoLabel5.setText("         ");
		 c.weightx=0;
		 c.weighty=0;
		 gridbag.setConstraints(infoLabel5, c);
		 jpCenter.add(infoLabel5);
		 
		 JPanel jptest=new JPanel(new BorderLayout());
		 c.gridwidth = GridBagConstraints.RELATIVE;
		 c.weightx=0;
		 c.weighty=0.1;
		 gridbag.setConstraints(jptest, c);
		 initJPanel5(jptest);
		 jpCenter.add(jptest);
	}
	
	private void initJPanel5(JPanel jp5)
	{
		jp5_jt=new JTable();
		
		if(userId.equals("admin")||userId.equals("5237"))
		{
			initJTable5(jp5_jt,"1",2);
		}else{
			initJTable5(jp5_jt,Tool.string2UTF8(classJcb.getSelectedItem().toString().trim()),0);
		}
		
		
	    JScrollPane jsp=new JScrollPane(jp5_jt);
	    jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    jsp.setPreferredSize(new Dimension(600, 60));
	    jsp.setMaximumSize(new Dimension(700, 80));
	    jp5.add(jsp);
	    jp5.setMaximumSize(new Dimension(700, 60));
	}
	
	/**
	 * initJTable5(jp5_jt,"1",2);      统计全部学生信息
	 * initJTable5(jp5_jt,Tool.string2UTF8(className),0);   统计某班级信息
	 * initJTable5(jp5_jt,Tool.string2UTF8(majorname),1);   统计某专业信息
	 * @param jp5_jt
	 * @param s
	 * @param flag
	 */
	private void initJTable5(JTable jp5_jt,String s,int flag)
	{
		String c="";
		if(flag==0){
			c="class=";
			
			String strCell0="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and "+c+"'"+s+"'";
			cell[0][0]=SqlModel.getInfo(strCell0);
			String strCell1="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("保研")+"' and "+c+"'"+s+"'";
			cell[0][1]=SqlModel.getInfo(strCell1);
			String strCell2="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("考研")+"' and "+c+"'"+s+"'";
			cell[0][2]=SqlModel.getInfo(strCell2);
			String strCell3="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("已签")+"' and "+c+"'"+s+"'";
			cell[0][3]=SqlModel.getInfo(strCell3);
			String strCell4="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("未签")+"' and "+c+"'"+s+"'";
			cell[0][4]=SqlModel.getInfo(strCell4);
			String strCell5="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("考公务员")+"' and "+c+"'"+s+"'";
			cell[0][5]=SqlModel.getInfo(strCell5);
			String strCell6="select avg(salary) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("已签")+"' and "+c+"'"+s+"'"+" and salary!=''";
			String avg_salary=SqlModel.getInfo(strCell6);   
			if(avg_salary==null){
				cell[0][6]="";
			}else{
				double d=Double.valueOf(avg_salary.toString());
				cell[0][6]=get2Double(d);				
			}
//			double d=Double.valueOf(avg_salary.toString());
//			cell[0][6]=get2Double(d);
			String strCell7="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("出国")+"' and "+c+"'"+s+"'";
			cell[0][7]=SqlModel.getInfo(strCell7);
			
		}else if(flag==1){
			
			String strCell0="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and "+s;
			cell[0][0]=SqlModel.getInfo(strCell0);
			String strCell1="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("保研")+"' and ("+s+")";
			cell[0][1]=SqlModel.getInfo(strCell1);
			String strCell2="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("考研")+"' and ("+s+")";
			cell[0][2]=SqlModel.getInfo(strCell2);
			String strCell3="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("已签")+"' and ("+s+")";
			cell[0][3]=SqlModel.getInfo(strCell3);
			String strCell4="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("未签")+"' and ("+s+")";
			cell[0][4]=SqlModel.getInfo(strCell4);
			String strCell5="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("考公务员")+"' and ("+s+")";
			cell[0][5]=SqlModel.getInfo(strCell5);
			String strCell6="select avg(salary) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("已签")+"' and ("+s+")"+" and salary!=''";
			String avg_salary=SqlModel.getInfo(strCell6);
			if(avg_salary==null){
				cell[0][6]="";
			}else{
				double d=Double.valueOf(avg_salary.toString());
				cell[0][6]=get2Double(d);				
			}
//			double d=Double.valueOf(avg_salary.toString());
//			cell[0][6]=get2Double(d);
			String strCell7="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("出国")+"' and ("+s+")";
			cell[0][7]=SqlModel.getInfo(strCell7);
		}else if(flag==2){
			c="1=";
			
			String strCell0="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and "+c+"'"+s+"'";
			cell[0][0]=SqlModel.getInfo(strCell0);
			String strCell1="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("保研")+"' and "+c+"'"+s+"'";
			cell[0][1]=SqlModel.getInfo(strCell1);
			String strCell2="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("考研")+"' and "+c+"'"+s+"'";
			cell[0][2]=SqlModel.getInfo(strCell2);
			String strCell3="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("已签")+"' and "+c+"'"+s+"'";
			cell[0][3]=SqlModel.getInfo(strCell3);
			String strCell4="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("未签")+"' and "+c+"'"+s+"'";
			cell[0][4]=SqlModel.getInfo(strCell4);
			String strCell5="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("考公务员")+"' and "+c+"'"+s+"'";
			cell[0][5]=SqlModel.getInfo(strCell5);
			String strCell6="select avg(salary) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("已签")+"' and "+c+"'"+s+"'"+" and salary!=''";
			String avg_salary=SqlModel.getInfo(strCell6);
			if(avg_salary==null){
				cell[0][6]="";
			}else{
				double d=Double.valueOf(avg_salary.toString());
				cell[0][6]=get2Double(d);				
			}

			String strCell7="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("出国")+"' and "+c+"'"+s+"'";
			cell[0][7]=SqlModel.getInfo(strCell7);
		}else{
			
			String strCell0="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and positive='1'";
			cell[0][0]=SqlModel.getInfo(strCell0);
			String strCell1="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("保研")+"' and positive='1'";
			cell[0][1]=SqlModel.getInfo(strCell1);
			String strCell2="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("考研")+"' and positive='1'";
			cell[0][2]=SqlModel.getInfo(strCell2);
			String strCell3="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("已签")+"' and positive='1'";
			cell[0][3]=SqlModel.getInfo(strCell3);
			String strCell4="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("未签")+"' and positive='1'";
			cell[0][4]=SqlModel.getInfo(strCell4);
			String strCell5="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("考公务员")+"' and positive='1'";
			cell[0][5]=SqlModel.getInfo(strCell5);
			String strCell6="select avg(salary) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("已签")+"' and positive='1' and salary!=''";
			String avg_salary=SqlModel.getInfo(strCell6);
			if(avg_salary==null){
				cell[0][6]="";
			}else{
				double d=Double.valueOf(avg_salary.toString());
				cell[0][6]=get2Double(d);				
			}

			String strCell7="select count(*) from jobinfo where grade='"+Tool.getGrade(userId)+"' and state='"+Tool.string2UTF8("出国")+"' and positive='1'";
			cell[0][7]=SqlModel.getInfo(strCell7);
		}
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	    String newdate = sdf.format(new Date());
		cell[0][8]=newdate;
	    
		dt = new DefaultTableModel(cell , header);
		
		jp5_jt.setModel(dt);
		jp5_jt.setRowHeight(27);
		
		TableColumnModel tm=jp5_jt.getColumnModel();
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	    render.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i=0;i<9;i++)
		{
			tm.getColumn(i).setPreferredWidth(80);
			tm.getColumn(i).setCellRenderer(render);
		}
	    tm.getColumn(6).setPreferredWidth(120);
	    tm.getColumn(8).setPreferredWidth(120);
	    jp5_jt.setAutoResizeMode(0);

	    jp5_jt.setEnabled(false);
	}
	
	private void initJTable(JTable jt,final HashMap cellp)
	{	
		 RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jit);  
	     jt.setRowSorter(sorter);
		 jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		 TableColumnModel tm=jt.getColumnModel();
		 tm.getColumn(0).setPreferredWidth(40);
		 tm.getColumn(1).setPreferredWidth(100);
		 tm.getColumn(2).setPreferredWidth(100);
		 tm.getColumn(4).setPreferredWidth(40);
		 tm.getColumn(6).setPreferredWidth(200);
		 tm.getColumn(9).setPreferredWidth(100);
		 tm.getColumn(10).setPreferredWidth(100);
		 tm.getColumn(12).setPreferredWidth(150);
		 tm.getColumn(13).setPreferredWidth(200);
		 
		 jt.setRowHeight(23);
		 
		 
//		 String cellSql="select positive from jobinfo";
//		 final String[] cellp=SqlModel.jobInfoQuery(cellSql);
		 
		 DefaultTableCellRenderer render = new DefaultTableCellRenderer(){
			 
			 public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) 
			 {
				 //System.out.println(table.getValueAt(row, 2));
				 if(userId.equals("admin")||userId.equals("5237"))
				 {
					 if(cellp.get(table.getValueAt(row, 2)).equals("1")){
						 setBackground(Color.RED);
					 }else{
						 setBackground(Color.white);
					 }
				 }

				 return super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
			 }
		 };
	     render.setHorizontalAlignment(SwingConstants.CENTER);
	     for(int i=0;i<tm.getColumnCount();i++)
	     {
	    	 tm.getColumn(i).setCellRenderer(render);
	     }
	     //render.setBackground(Color.GRAY);
	     
		 jt.setOpaque(false);
	}
	
	private void refreshTable(String sql,String[] paras,JTable jt,HashMap<String, String> cellp)
	{
		jit=new JobInfoTable();
		jit.jobTableQuery(sql, paras);
		jt.setModel(jit);
		
		//String cellSql="select id,positive from jobinfo";
		//initJTable(jt,SqlModel.jobPositiveQuery(cellSql));
		initJTable(jt,cellp);
	}
	
	private void initCenterJPabel1(JPanel jp1)
	{
		if(jp1==null){
			return;
		}
		
		JPanel jp1_left=new JPanel();
		JPanel jp1_right=new JPanel();
		
		jp1_left.setLayout(new FlowLayout(FlowLayout.LEFT,0,5));
		
		JLabel classLabel=new JLabel("班级：   ");
		classLabel.setSize(42, 18);
		jp1_left.add(classLabel);
		
//		String sql="select className from classinfo";
//		String[] classArray=SqlModel.classInfoQuery(sql);
		
		String[] classArray=null;
		if(userId.equals("admin")||userId.equals("5237"))
		{
			String csql0="select className from classinfo where grade='"+Tool.getGrade(userId)+"'";
			classArray=SqlModel.classInfoQuery(csql0);
		}else{
			//System.out.println("2111111111111");
			String msql0="select major from classinfo where grade='"+Tool.getGrade(userId)+"' and monitorId='"+Tool.string2UTF8(userId)+"'";
			//System.out.println(msql0);
			//System.out.println(SqlModel.getInfo(msql0));
			String csql0="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and major='"+Tool.string2UTF8(SqlModel.getInfo(msql0))+"'";
			classArray=SqlModel.classInfoQuery(csql0);
			classArray[0]="";
		}
		
		classJcb=new JComboBox(classArray);
		
		if(userId.equals("admin")||userId.equals("5237")){
			
		}else{
			  String csql="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and monitorId='"+userId+"'";
			  String classNameFromSql=SqlModel.getInfo(csql).trim();
			  classJcb.setSelectedItem(classNameFromSql);
		}
		
		classJcb.setPreferredSize(new Dimension(120, 23));
		classJcb.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("11111:  "+classJcb.getSelectedIndex());
				if(classJcb.getSelectedIndex()==0)
				{
					if(userId.equals("admin")||userId.equals("5237")){
						String sql="select class,id,studentName,sex,state,company,protocalState," +
									"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
						String []paras={"1"};
						String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"'";
						// initJTable(jt,SqlModel.jobInfoQuery(cellSql));
						refreshTable(sql,paras,jt,SqlModel.jobPositiveQuery(cellSql));
						initJTable5(jp5_jt,"1",2);						
					}else{
						
					}

				}else{
					
					String sql="select class,id,studentName,sex,state,company,protocalState," +
								"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and class=?";
					String []paras={Tool.string2UTF8(classJcb.getSelectedItem().toString().trim())};
					String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"' and class='"+Tool.string2UTF8(classJcb.getSelectedItem().toString().trim())+"'";
					refreshTable(sql,paras,jt,SqlModel.jobPositiveQuery(cellSql));
					
					initJTable5(jp5_jt,Tool.string2UTF8(classJcb.getSelectedItem().toString().trim()),0);
				}
				
				majorJcb.setSelectedIndex(0);
			}
		});
		jp1_left.add(classJcb);
		
		JLabel majorLabel=new JLabel("      专业：");
		majorLabel.setSize(42, 18);
		jp1_left.add(majorLabel);
		
		String[] majorArray=null;
		if(userId.equals("admin")||userId.equals("5237"))
		{
			//String msql="select distinct major from classinfo";
			//majorArray=SqlModel.classInfoQuery(msql);
			majorArray=SqlModel.majorInfoQuery(userId);
		}else{
			String msql="select major from classinfo where grade='"+Tool.getGrade(userId)+"' and monitorId='"+userId+"'";
			majorArray=SqlModel.classInfoQuery(msql);
			majorArray[0]="";
		}

		//String[] majorArray={"全部","计科","信科","信安","网络"};
		majorJcb=new JComboBox(majorArray);
		majorJcb.setPreferredSize(new Dimension(120, 23));
		majorJcb.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				
				if(majorJcb.getSelectedIndex()==0)
				{
					if(userId.equals("admin")||userId.equals("5237")){
						//System.out.println("11111:  "+majorJcb.getSelectedIndex());
						String sql="select class,id,studentName,sex,state,company,protocalState," +
									"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
						String []paras={"1"};
						String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"'";
						refreshTable(sql,paras,jt,SqlModel.jobPositiveQuery(cellSql));
						//refreshTable(sql,paras,jt);
						initJTable5(jp5_jt,"1",2);
					}else{
						
					}
					
				}else{
					String sql="select class,id,studentName,sex,state,company,protocalState," +
							"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
					String []paras={"2"};
					
					String sql1="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and major='"+Tool.string2UTF8(majorJcb.getSelectedItem().toString().trim())+"'";
					String[] majorClass=SqlModel.classInfoQuery(sql1);
					
					String s="";
					for(int i=1;i<majorClass.length;i++){
						s+=" or class='"+Tool.string2UTF8(majorClass[i])+"'";
					}
					
					sql+=s;
					String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"' and 1=2"+s;
					refreshTable(sql,paras,jt,SqlModel.jobPositiveQuery(cellSql));
					//refreshTable(sql,paras,jt);
					
					String classStr="class='"+Tool.string2UTF8(majorClass[1])+"'";
					
					for(int i=2;i<majorClass.length;i++){
						classStr+=" or class='"+Tool.string2UTF8(majorClass[i])+"'";
					}
					
					initJTable5(jp5_jt,classStr,1);
				}
				
				classJcb.setSelectedIndex(0);
			}
		});
		jp1_left.add(majorJcb);
		
		addNewButton=new JButton("添加学生");
		addNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				final AddDialog addDialog=new AddDialog(userId);
				
				addDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						refresh();
						String xuehao=addDialog.getReturnXuehao();
						findInTable(xuehao);
					}
					
				});
				
			}
		});
		jp1_right.add(addNewButton);
		
		jp1_right.add(new JLabel("      "));
		
		userButton=new JButton("修改密码");
		userButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ManagerUserDialog(userId);
			}
		});
		jp1_right.add(userButton);
		
		JLabel jlbtemp0=new JLabel("     ");
		jp1_right.add(jlbtemp0);
		
		addClassButton=new JButton("班级管理");
		addClassButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(userId.equals("admin")||userId.equals("5237")){
					ManagerClassDialog addClassDialog=new ManagerClassDialog(classJcb,userId);
					
					addClassDialog.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							// TODO Auto-generated method stub
							
							String temp=classJcb.getSelectedItem().toString().trim();
							
							refresh();
							
							String csql0="select className from classinfo where grade='"+Tool.getGrade(userId)+"'";
							String[] classArray0=SqlModel.classInfoQuery(csql0);
							classJcb.setModel(new DefaultComboBoxModel(classArray0));
							
							classJcb.setSelectedItem(temp);
					        
						}
					});
					

				}else{
					JOptionPane.showMessageDialog(null, "对不起，您没有此权限");
				}
			}
		});
		jp1_right.add(addClassButton);
		
		
		JLabel jlbtemp=new JLabel("     ");
		jp1_right.add(jlbtemp);
		
		//专业管理
		addMajorButton=new JButton("专业管理");
		addMajorButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManagerMajorDialog managerMajorDialog=new ManagerMajorDialog(userId);
				managerMajorDialog.addWindowListener(new WindowAdapter() {
					
					@Override
					public void windowClosing(WindowEvent ae) {
						// TODO Auto-generated method stub
						
						String csql0="select className from classinfo where grade='"+Tool.getGrade(userId)+"'";
						String[] classArray0=SqlModel.classInfoQuery(csql0);
						classJcb.setModel(new DefaultComboBoxModel(classArray0));
						
						String msql0="select majorName from majorinfo where grade='"+Tool.getGrade(userId)+"'";
						String[] majorArray0=SqlModel.classInfoQuery(msql0);
						majorJcb.setModel(new DefaultComboBoxModel(majorArray0));
						
						refresh();
						
//						String[] gradeArray0=SqlModel.gradeinfoQuery();
//						gradeJcb.setModel(new DefaultComboBoxModel(gradeArray0));
//						//gradeJcb.setSelectedItem("新增");
					}
					
				});
				
			}
		});
		jp1_right.add(addMajorButton);
		
		
		JLabel jlbtemp2=new JLabel("     ");
		jp1_right.add(jlbtemp2);
		
		
		searchJtf=new JTextField(8);
		searchJtf.setBorder(BorderFactory.createLoweredBevelBorder());
		searchJtf.addKeyListener(new KeyAdapter() 
		{ 
			public void keyPressed(KeyEvent e) 
			{ 
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					
					if(searchJtf.getText().trim().equals("")){
						return;
					}
					
					findStudent(searchJtf.getText().trim());
					
				}
			}
			
		} );
		
		jp1_right.add(searchJtf);
		
		
		if(userId.equals("admin")||userId.equals("5237"))
		{
			
		}else{
			addMajorButton.setVisible(false);
			searchJtf.setVisible(false);
			addClassButton.setVisible(false);
			jlbtemp.setVisible(false);
			jlbtemp0.setVisible(false);
		}
		
		jp1.add(jp1_left,BorderLayout.WEST);
		jp1.add(jp1_right,BorderLayout.EAST);
	}
	
	/**
	 * 南部面板显示
	 */
	private void intiSouth()
	{	
		jpSouth=new JPanel();
		jpSouth.setLayout(new FlowLayout(FlowLayout.CENTER,80,30));
		
		JButton excel2dbButton=new JButton("将excel的内容导入到表格中");
		excel2dbButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(classJcb.getSelectedIndex()==0){
					JOptionPane.showMessageDialog(jpCenter, "请选择班级");
					return;
				}
				
		        if(userId.equals("admin")||userId.equals("5237")){
		        	ExcelDo.excel2db(userId,jt, classJcb, infoLabel);
		        }else{
					String csql="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and monitorId='"+userId+"'";
					String classNameFromSql=SqlModel.getInfo(csql).trim();	
					
					if(classJcb.getSelectedItem().equals(classNameFromSql)){
						ExcelDo.excel2db(userId,jt, classJcb, infoLabel);
					}else{
						JOptionPane.showMessageDialog(null, "请选择本班班级");
					}
		        }
				
				String sql="select class,id,studentName,sex,state,company,protocalState," +
							"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and class=?";
				String []paras={Tool.string2UTF8(classJcb.getSelectedItem().toString())};
				String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"' and class='"+Tool.string2UTF8(classJcb.getSelectedItem().toString().trim())+"'";
				refreshTable(sql,paras,jt,SqlModel.jobPositiveQuery(cellSql));
				//refreshTable(sql,paras,jt);
				
				//initJTable5(jp5_jt,Tool.string2UTF8(className),0);   统计某班级信息
				initJTable5(jp5_jt,Tool.string2UTF8(classJcb.getSelectedItem().toString()),0);
				//initJTable5(jp5_jt,"1",2);
						
			}
		});
		excel2dbButton.setFocusPainted(false);
		excel2dbButton.setPreferredSize(new Dimension(200, 33));
		excel2dbButton.setMinimumSize(new Dimension(100,33));
		jpSouth.add(excel2dbButton);
		
		JButton db2excelButton=new JButton("查询内容导出到Excel");
		db2excelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				ExcelDo.db2excel(userId,jt, classJcb,majorJcb, infoLabel, jit, dt);

			}
		});
		db2excelButton.setFocusPainted(false);
		db2excelButton.setPreferredSize(new Dimension(200, 33));
		db2excelButton.setMinimumSize(new Dimension(100,33));
		jpSouth.add(db2excelButton);
		
		JButton count2excelButton=new JButton("统计信息导出");
		count2excelButton.setFocusPainted(false);
		count2excelButton.setPreferredSize(new Dimension(160, 33));
		count2excelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ExcelDo.countExcel(userId);
			}
		});
		jpSouth.add(count2excelButton);
		
		if(userId.equals("admin")||userId.equals("5237")){
			
		}else{
			count2excelButton.setVisible(false);
		}
		
		JButton deleteButton=new JButton("删除");
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				int RowNum=jt.getSelectedRow();
				if(RowNum==-1){
					JOptionPane.showMessageDialog(jt, "请选择一行");
				    return ;
			    }
				
				int isDelete=JOptionPane.showConfirmDialog(null, "是否确定删除？");
				
				if(isDelete==JOptionPane.YES_OPTION){
			        if(userId.equals("admin")||userId.equals("5237")){
						String id=(String)jit.getValueAt(RowNum, 2);
						String sql="delete from jobinfo where grade='"+Tool.getGrade(userId)+"' and id=?";
						String[] paras={id};
						
						if(jit.Update(sql, paras)){
							JOptionPane.showMessageDialog(null, "删除成功");
						}else{
							JOptionPane.showMessageDialog(null, "删除失败");
						}		        	
			        }else{
						String csql="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and monitorId='"+userId+"'";
						String classNameFromSql=SqlModel.getInfo(csql).trim();	
						
						if(jt.getValueAt(RowNum, 1).equals(classNameFromSql)){
							
							String id=(String)jit.getValueAt(RowNum, 2);
							String sql="delete from jobinfo where grade='"+Tool.getGrade(userId)+"' and id=?";
							String[] paras={id};
							
							if(jit.Update(sql, paras)){
								JOptionPane.showMessageDialog(null, "删除成功");
							}else{
								JOptionPane.showMessageDialog(null, "删除失败");
							}	
							
						}else{
							JOptionPane.showMessageDialog(null, "该同学不是你班学生，你没有权限这么做");
						}
			        }	
			        
			        refresh();
			        
				}
			}
		});
		deleteButton.setFocusPainted(false);
		deleteButton.setPreferredSize(new Dimension(100, 33));
		jpSouth.add(deleteButton);
		
	}
	
	private void refresh()
	{
		//System.out.println(classJcb.getSelectedIndex()+"    "+majorJcb.getSelectedIndex());
		
        if(classJcb.getSelectedIndex()==0&&majorJcb.getSelectedIndex()==0){
			String sql0="select class,id,studentName,sex,state,company,protocalState," +
					"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
			String []paras0={"1"};
			String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"'";
			refreshTable(sql0,paras0,jt,SqlModel.jobPositiveQuery(cellSql));
			//refreshTable(sql0,paras0,jt);
			initJTable5(jp5_jt,"1",2);					        	
        }else if(classJcb.getSelectedIndex()!=0){
        	//initJTable5(jp5_jt,Tool.string2UTF8(className),0);
			String sql="select class,id,studentName,sex,state,company,protocalState," +
					"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and class=?";
			String []paras={Tool.string2UTF8(classJcb.getSelectedItem().toString())};
			String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"' and class='"+Tool.string2UTF8(classJcb.getSelectedItem().toString().trim())+"'";
			refreshTable(sql,paras,jt,SqlModel.jobPositiveQuery(cellSql));
			//refreshTable(sql,paras,jt);
			initJTable5(jp5_jt,Tool.string2UTF8(classJcb.getSelectedItem().toString()),0);
			
        }else if(majorJcb.getSelectedIndex()!=0){
			String sql="select class,id,studentName,sex,state,company,protocalState," +
					"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
			String []paras={"2"};
			String sql1="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and major='"+Tool.string2UTF8(majorJcb.getSelectedItem().toString().trim())+"'";
			String[] majorClass=SqlModel.classInfoQuery(sql1);
			
			String s="";
			for(int i=1;i<majorClass.length;i++){
				s+=" or class='"+Tool.string2UTF8(majorClass[i])+"'";
			}
			
			sql+=s;
			String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"' and 1=2"+s;
			refreshTable(sql,paras,jt,SqlModel.jobPositiveQuery(cellSql));
			//refreshTable(sql,paras,jt);
			
			String classStr="class='"+Tool.string2UTF8(majorClass[1])+"'";
			
			for(int i=2;i<majorClass.length;i++){
				classStr+=" or class='"+Tool.string2UTF8(majorClass[i])+"'";
			}
			
			initJTable5(jp5_jt,classStr,1);
        }
	}
	
	/**
	 * 北部面板  显示标题
	 */
	private void initNorth()
	{
		jpNorth=new JPanel();
		JLabel titleLabelLeft=new JLabel();
		JLabel titleLabelCenter=new JLabel();
		JLabel titleLabelRight=new JLabel();
		gradeJcb=new JComboBox(SqlModel.gradeinfoQuery());
		gradeJcb.setSelectedItem(Tool.getGrade(userId));
		
		titleLabelLeft.setText("计算机学院 ");
		titleLabelCenter.setText(Tool.getGrade(userId));
		titleLabelRight.setText("届毕业生就业统计");
	    
		titleLabelLeft.setBounds(this.screenSize.width / 2 - 173, 10, 130, 30);
		titleLabelLeft.setFont(new Font("宋体", 1, 22));
	    jpNorth.add(titleLabelLeft);
	    
	    /*
	     * 年级选择
	     */
	    jpNorth.add(gradeJcb);
	    gradeJcb.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(gradeJcb.getSelectedItem().equals("新增"))
				{
					if(e.getStateChange()==ItemEvent.SELECTED){
						ManagerGradeDialog managerGradeDialog=new ManagerGradeDialog();
						
					    if(managerGradeDialog!=null){
					    	 managerGradeDialog.addWindowListener(new WindowAdapter() {
					    		 
									@Override
									public void windowClosing(WindowEvent ae) {
										// TODO Auto-generated method stub
										String[] gradeArray0=SqlModel.gradeinfoQuery();
										gradeJcb.setModel(new DefaultComboBoxModel(gradeArray0));
										//gradeJcb.setSelectedItem("新增");
									}
							});				    	 
					     }
						
					}
				}else{

					String gradenow=gradeJcb.getSelectedItem().toString().trim();
					Tool.modifyGradeId(gradenow);
					
					String sql="select class,id,studentName,sex,state,company,protocalState," +
							"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
					String []paras={"1"};
					String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"'";
					refreshTable(sql,paras,jt,SqlModel.jobPositiveQuery(cellSql));
					initJTable5(jp5_jt,"1",2);
					
					String Tcsql0="select className from classinfo where grade='"+Tool.getGrade(userId)+"'";
					String[] TclassArray=SqlModel.classInfoQuery(Tcsql0);
					
					ComboBoxModel Tccm=new DefaultComboBoxModel(TclassArray);
					classJcb.setModel(Tccm);
					
					ComboBoxModel Tcmm=new DefaultComboBoxModel(SqlModel.majorInfoQuery(userId));
					majorJcb.setModel(Tcmm);
				}
			}
		});
	    
	    titleLabelCenter.setBounds(this.screenSize.width / 2 - 43, 20, 141, 30);
	    titleLabelCenter.setFont(new Font("宋体", 1, 22));
	    jpNorth.add(titleLabelCenter);
	    
	    titleLabelRight.setBounds(this.screenSize.width / 2 +98, 10, 197, 30);
	    titleLabelRight.setFont(new Font("宋体", 1, 22));
	    jpNorth.add(titleLabelRight);
	    

	    if(userId.equals("admin")||userId.equals("5237")){
	    	titleLabelCenter.setVisible(false);
	    }else{
	    	gradeJcb.setVisible(false);
	    }
	}
	
	public static double get2Double(double a){
	    DecimalFormat df=new DecimalFormat("0.00");
	    return new Double(df.format(a).toString());
	}
	
	private boolean findInTable(String str)
	{
		int rowCount = jt.getRowCount();
		int columnCount = jt.getColumnCount();
		for (int i = 0; i < rowCount; i++) 
		{
			for (int k = 0; k < columnCount; k++) 
			{
				Object value = jt.getValueAt(i, k);
				if (str.equals(value)) {
					
					jt.getSelectionModel().setSelectionInterval(i, i);
					Rectangle cellRect = jt.getCellRect(i, k, true);
					jt.scrollRectToVisible(cellRect);
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	private boolean findStudent(String str)
	{
		String fsql="select class,id,studentName,sex,state,company,protocalState," +
				"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and studentName like ?";
		String []fparas={"%"+Tool.string2UTF8(str)+"%"};
		
		String cellSql="select id,positive from jobinfo where grade='"+Tool.getGrade(userId)+"' and studentName like '"+"%"+Tool.string2UTF8(str)+"%'";
		refreshTable(fsql,fparas,jt,SqlModel.jobPositiveQuery(cellSql));
		//refreshTable(fsql,fparas,jt,null);
		
		return false;
	}
	
	private void selectTable(JTable jt,MouseEvent e) 
	{
		int row = jt.rowAtPoint(e.getPoint());
		if(row>=0){
			jt.setRowSelectionInterval(row,row);
		}
	}
}
