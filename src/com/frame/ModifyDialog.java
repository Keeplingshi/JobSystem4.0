package com.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.other.JobInfoTable;
import com.sql.SqlHelper;
import com.sql.SqlModel;
import com.tools.Tool;

public class ModifyDialog extends JFrame{

	  String name;
	  private String[] lie = new String[12];
	  //private SqlHelper sh=null;
	  private String major;
	  private final JButton btnModify = new JButton();

	  private final JTextField tfName = new JTextField();
	  private final JTextField tfcompany = new JTextField();
	  private final JTextField tfCity = new JTextField();
	  private final JTextField tfMoney = new JTextField();
	  //private final JTextField tfNote = new JTextField();
	  private final JTextArea tfNodeArea=new JTextArea();
	  private final JTextField tfXueHao = new JTextField();
	  
	  private final JLabel areaLabel = new JLabel();
	  private final JLabel nameLabel = new JLabel();
	  private final JLabel stateLabel = new JLabel();
	  private final JLabel classLabel = new JLabel();
	  private final JLabel companyLabel = new JLabel();
	  private final JLabel protocalLabel = new JLabel();
	  private final JLabel nowLabel = new JLabel();
	  private final JLabel cityLabel = new JLabel();
	  private final JLabel moneyLabel = new JLabel();
	  private final JLabel noteLabel = new JLabel();
	  private final JLabel makesureLabel = new JLabel();
	  private final JLabel xueHaoLabel = new JLabel();
	  private final JLabel xingBieLabel = new JLabel();

	  private JComboBox nowStateJcb = null;
	  private JComboBox areaJcb = null;
	  private JComboBox classJcb =null;
	  private JComboBox stateJcb = null;
	  private JComboBox protocalJcb = null;
	  private JComboBox sexJcb = null;
	  
	  private JScrollPane jScrollPane=new JScrollPane();
//	  private JComboBox mainFclassJcb=null;
//	  private JTable mainFjt=null;
//	  private JobInfoTable mainFjit=null;
	  
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  private String userId=null;
	  
	  public ModifyDialog(String userId,String lie[])
	  {
		  
//		  for(int j=0;j<lie.length;j++)
//		  {
//			  System.out.println(lie[j]);
//		  }
		  
		  this.userId=userId;
//		  this.mainFclassJcb=mainFclassJcb;
//		  this.mainFjt=mainFjt;
//		  this.mainFjit=mainFjit;
		  
		  this.setBounds(this.screenSize.width / 2 - 250, this.screenSize.height / 2 - 250, 500, 450);
		  this.setResizable(false);
		  try {
			  jbInit();
			  
			  classJcb.setSelectedItem(lie[0]);
		      tfXueHao.setText(lie[1]);
		      tfName.setText(lie[2]);
		      sexJcb.setSelectedItem(lie[3]);
		      stateJcb.setSelectedItem(lie[4]);
		      tfcompany.setText(lie[5]);
		      
		      nowStateJcb.setSelectedItem(lie[7]);
		      areaJcb.setSelectedItem(lie[8]);
		      tfCity.setText(lie[9]);
		      tfMoney.setText(lie[10]);
		      tfNodeArea.setText(lie[11]);
		      //tfNote.setText(lie[11]);
		      
				if(stateJcb.getSelectedItem().equals("已签")){
				    protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "", "三方已交", "已签未交" }));
				}else if(stateJcb.getSelectedItem().equals("未签")){
				    protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "", "未找到工作","已有offer在考虑"}));
				}else {
					protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "" }));
				}
				
			protocalJcb.setSelectedItem(lie[6]);
		  } catch (Throwable e) {
			  e.printStackTrace();
		  }
		  this.setVisible(true);
	  }
	  
	  private void jbInit() {
		  
		  getContentPane().setLayout(null);
		  this.setTitle("就业情况修改");

		  getContentPane().add(this.classLabel);
		  this.classLabel.setText("班级：");
		  this.classLabel.setBounds(30, 25, 42, 18);

		  if(userId.equals("admin")||userId.equals("5237"))
		  {
			  String sql="select className from classinfo where grade='"+Tool.getGrade(userId)+"'";
			  String[] classArray=SqlModel.classInfoQuery(sql);
			  classArray[0]="";
			  classJcb=new JComboBox(classArray);			  
		  }else{
			  String csql="select className from classinfo where grade='"+Tool.getGrade(userId)+"' and monitorId='"+userId+"'";
			  String classNameFromSql=SqlModel.getInfo(csql).trim();
			  String[] classArray={classNameFromSql};
			  classJcb=new JComboBox(classArray);
		  }
//		  String sql="select className from classinfo";
//		  String[] classArray=SqlModel.classInfoQuery(sql);
//		  classArray[0]="";
//		  classJcb=new JComboBox(classArray);
		  getContentPane().add(classJcb);
		  classJcb.setBounds(104, 21, 106, 27);

		  getContentPane().add(this.nameLabel);
		  this.nameLabel.setText(" 姓名：");
		  this.nameLabel.setBounds(30, 69, 42, 18);

		  getContentPane().add(this.tfName);
		  this.tfName.setBounds(104, 67, 106, 22);

		  getContentPane().add(this.stateLabel);
		  this.stateLabel.setText("签约状态：");
		  this.stateLabel.setBounds(10, 118, 80, 18);

		  stateJcb=new JComboBox(new String[] { "已签", "未签", "保研", "考研", "考公务员", "出国" });
		  stateJcb.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(protocalJcb==null){
					return;
				}
				if(e.getItem().equals("已签")){
					protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "", "三方已交", "已签未交" }));
				}else if(e.getItem().equals("未签")){
					protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "", "未找到工作","已有offer在考虑"}));
				}else {
					protocalJcb.setModel(new DefaultComboBoxModel(new String[] { "" }));
				}
			}
		});
		  stateJcb.setBounds(104, 114, 106, 27);
		  getContentPane().add(stateJcb);

		  getContentPane().add(this.companyLabel);
		  this.companyLabel.setText(" 签约单位：");
		  this.companyLabel.setBounds(10, 163, 75, 18);

		  getContentPane().add(this.tfcompany);
		  this.tfcompany.setBounds(104, 161, 106, 22);

		  getContentPane().add(this.protocalLabel);
		  this.protocalLabel.setText(" 协议书状态：");
		  this.protocalLabel.setBounds(239, 118, 84, 18);

//		  if(stateJcb.getSelectedItem().equals("已签")){
//			  protocalJcb=new JComboBox<>(new String[] { "", "交学院",  "已签未交" });
//		  }else if(stateJcb.getSelectedItem().equals("未签")){
//			  protocalJcb=new JComboBox<>(new String[] { "", "自己存留未签", "正在签约" });
//		  }else{
//			  protocalJcb=new JComboBox<>(new String[] {""});
//		  }
		  protocalJcb=new JComboBox(new String[] {""});
		  protocalJcb.setBounds(344, 114, 106, 27);
		  getContentPane().add(protocalJcb);
		  
		  getContentPane().add(this.nowLabel);
		  this.nowLabel.setText("当前状态：");
		  this.nowLabel.setBounds(239, 163, 80, 18);

		  nowStateJcb=new JComboBox(new String[] {"在学校",  "在家", "实习" ,"其他"});
		  nowStateJcb.setBounds(344, 161, 106, 22);
		  getContentPane().add(nowStateJcb);
		  
		  getContentPane().add(this.areaLabel);
		  this.areaLabel.setText("签约所在地区：");
		  this.areaLabel.setBounds(10, 209, 100, 18);

		  areaJcb=new JComboBox(new String[] { "", "华北", "东北", "华东", "华中", "华南", "西南", "西北" });
		  areaJcb.setBounds(104, 207, 106, 22);
		  getContentPane().add(areaJcb);

		  getContentPane().add(this.cityLabel);
		  this.cityLabel.setText("签约所在城市：");
		  this.cityLabel.setBounds(239, 209, 99, 18);

		  getContentPane().add(this.tfCity);
		  this.tfCity.setBounds(344, 207, 106, 22);

		  getContentPane().add(this.moneyLabel);
		  this.moneyLabel.setText("薪资/月：");
		  this.moneyLabel.setBounds(259, 251, 59, 18);

		  getContentPane().add(this.tfMoney);
		  this.tfMoney.setBounds(344, 249, 106, 22);

		  getContentPane().add(this.noteLabel);
		  this.noteLabel.setText("备注（主要写近期做什么）：");
		  this.noteLabel.setBounds(10, 251, 200, 18);

		  jScrollPane=new JScrollPane(tfNodeArea);
		  getContentPane().add(jScrollPane);
		  this.jScrollPane.setBounds(10, 283, 300, 50);
		  tfNodeArea.setLineWrap(true);
		  
//		  getContentPane().add(this.tfNote);
//		  this.tfNote.setBounds(10, 283, 200, 22);

		  getContentPane().add(this.makesureLabel);
		  this.makesureLabel.setText("");
		  this.makesureLabel.setBounds(20, 325, 1000, 18);

		  getContentPane().add(this.btnModify);
		  this.btnModify.setBounds(177, 349, 106, 33);
		  this.btnModify.setText("修改");
		  this.btnModify.addMouseListener(new BtnModifyMouseListener());

		  getContentPane().add(this.xueHaoLabel);
		  this.xueHaoLabel.setText("学号:");
		  this.xueHaoLabel.setBounds(259, 25, 42, 18);

		  getContentPane().add(this.xingBieLabel);
		  this.xingBieLabel.setText("性别：");
		  this.xingBieLabel.setBounds(259, 69, 42, 18);

		  sexJcb=new JComboBox(new String[] { "男", "女" });
		  sexJcb.setBounds(344, 67, 106, 25);
		  getContentPane().add(sexJcb);
		  
		  getContentPane().add(this.tfXueHao);
		  tfXueHao.setEditable(false);
		  this.tfXueHao.setBounds(342, 23, 108, 22);
	}
	  
	  
	  class BtnModifyMouseListener extends MouseAdapter
	  {
		  
		  public void mouseClicked(MouseEvent e)
		  {
			  if ((tfName.getText().trim().equals("")) || (tfName.getText().length() == 0)) {
				  makesureLabel.setText("姓名不能为空！！");
			  } else if ((tfXueHao.getText().trim().length() == 0)) {
				  makesureLabel.setText("学号不能为空！！");
		      } else {
					lie[0] = ((String)classJcb.getSelectedItem());
					lie[1] = tfXueHao.getText();
					lie[2] = tfName.getText();
					lie[3] = ((String)sexJcb.getSelectedItem());
					lie[4] = ((String)stateJcb.getSelectedItem());
					lie[5] = tfcompany.getText();
					lie[6] = ((String)protocalJcb.getSelectedItem());
					lie[7] = ((String)nowStateJcb.getSelectedItem());
					lie[8] = ((String)areaJcb.getSelectedItem());
					lie[9] = tfCity.getText();
					lie[10] = tfMoney.getText();
					//lie[11] = tfNote.getText();
					lie[11] = tfNodeArea.getText();
			        try
			        {
			        	if (!tfMoney.getText().equals("")) {
				            String money = tfMoney.getText();
				            Double.parseDouble(money);
			        	}
			        	
			        	String strSql = "";
			        	//sh=new SqlHelper();
			        	
//				            String[] st = lie[0].split("", 4);
//			
//				            if (st[1].equals("计"))
//				            	major = "计科";
//				            else if ((st[1].equals("信")) && (st[2].equals("科")))
//				              	major = "信科";
//				            else if ((st[1].equals("信")) && (st[2].equals("安")))
//				              	major = "信安";
//				            else if (st[1].endsWith("网")) {
//				              	major = "网络";
//				            }
			        	
			        		major=SqlModel.getInfo("select major from classinfo where grade='"+Tool.getGrade(userId)+"' and className='"+Tool.string2UTF8(lie[0])+"'");
		
				            strSql = "update jobinfo set class = '" + Tool.string2UTF8(lie[0]) + 
				                    "',id = '" + Tool.string2UTF8(lie[1]) + "',studentName = '" + 
				                    Tool.string2UTF8(lie[2]) + "',sex = '" + Tool.string2UTF8(lie[3]) + 
				                    "', state = '" + Tool.string2UTF8(lie[4]) + "', company = '" + 
				                    Tool.string2UTF8(lie[5]) + "', protocalState = '" + Tool.string2UTF8(lie[6]) + 
				                    "', nowState = '" + Tool.string2UTF8(lie[7]) + "',area = '" + 
				                    Tool.string2UTF8(lie[8]) + "',city = '" + Tool.string2UTF8(lie[9]) + 
				                    "', salary = '" + Tool.string2UTF8(lie[10]) + "',note = '" + 
				                    Tool.string2UTF8(lie[11]) + "',major = '" + Tool.string2UTF8(major) 
				                    +"',mtime='"+Tool.string2UTF8(Tool.whoModify(userId))+ 
				                    "' where id = '" + Tool.string2UTF8(tfXueHao.getText().trim()) + "'";

				            //System.out.println("ModifyDialog       "+strSql);
				            
			            if (SqlModel.updateInfo(strSql))
			              	makesureLabel.setText("成功！！");
			            else {
			              	makesureLabel.setText("失败!");
			            	
			            //sh.close();
			          }
			        } catch (Exception e2) {
			          	makesureLabel.setText(e2.getMessage() + " ，薪金必须是数！");
			        } finally {
			        	//sh.close();
			        }
	      }
			  
//		        if(mainFclassJcb.getSelectedIndex()==0){
//		        	
//		        	String sql="";
//		        	String []paras=new String[1];
//		        	if(userId.equals("admin")||userId.equals("5237")){
//		        		sql="select class,id,studentName,sex,state,company,protocalState," +
//							"nowState,area,city,salary, note,mtime from jobinfo where 1=?";
//		        		paras[0]="1";
//		        	}else{
//		        		String csql="select className from classinfo where monitorId='"+Tool.string2UTF8(userId)+"'";
//		        		String classname=SqlModel.getInfo(csql);
//		        		sql="select class,id,studentName,sex,state,company,protocalState," +
//							"nowState,area,city,salary, note,mtime from jobinfo where class=?";
//		        		paras[0]=classname;
//		        	}
//		        	
//					mainFjit=new JobInfoTable();
//					mainFjit.jobTableQuery(sql, paras);
//					mainFjt.setModel(mainFjit);
//				    RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mainFjit);  
//				    mainFjt.setRowSorter(sorter);  
//					mainFjt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//					
//					 TableColumnModel tm=mainFjt.getColumnModel();
//					 tm.getColumn(0).setPreferredWidth(40);
//					 tm.getColumn(4).setPreferredWidth(40);
//					 tm.getColumn(6).setPreferredWidth(200);
//					 tm.getColumn(9).setPreferredWidth(100);
//					 tm.getColumn(10).setPreferredWidth(100);
//					 tm.getColumn(12).setPreferredWidth(150);
//					 tm.getColumn(13).setPreferredWidth(200);
//					 
//					 mainFjt.setRowHeight(23);
//					 
//					 DefaultTableCellRenderer render = new DefaultTableCellRenderer();
//				     render.setHorizontalAlignment(SwingConstants.CENTER);
//				     for(int i=0;i<tm.getColumnCount();i++)
//				     {
//				    	 tm.getColumn(i).setCellRenderer(render);
//				     }
//				     
//				     mainFjt.setOpaque(false);
//					//refreshTable(sql,paras,mainFjt);
//		        }else{
//					String sql="select class,id,studentName,sex,state,company,protocalState," +
//							"nowState,area,city,salary, note,mtime from jobinfo where class=?";
//					String []paras={Tool.string2UTF8(mainFclassJcb.getSelectedItem().toString().trim())};
//					//refreshTable(sql,paras,mainFjt);
//					mainFjit=new JobInfoTable();
//					mainFjit.jobTableQuery(sql, paras);
//					mainFjt.setModel(mainFjit);
//				    RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mainFjit);  
//				    mainFjt.setRowSorter(sorter);  
//					mainFjt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//					
//					 TableColumnModel tm=mainFjt.getColumnModel();
//					 tm.getColumn(0).setPreferredWidth(40);
//					 tm.getColumn(4).setPreferredWidth(40);
//					 tm.getColumn(6).setPreferredWidth(200);
//					 tm.getColumn(9).setPreferredWidth(100);
//					 tm.getColumn(10).setPreferredWidth(100);
//					 tm.getColumn(12).setPreferredWidth(150);
//					 tm.getColumn(13).setPreferredWidth(200);
//					 
//					 mainFjt.setRowHeight(23);
//					 
//					 DefaultTableCellRenderer render = new DefaultTableCellRenderer();
//				     render.setHorizontalAlignment(SwingConstants.CENTER);
//				     for(int i=0;i<tm.getColumnCount();i++)
//				     {
//				    	 tm.getColumn(i).setCellRenderer(render);
//				     }
//				     
//				     mainFjt.setOpaque(false);
//		        }
			  
			  dispose();
	    }
	  }
}
