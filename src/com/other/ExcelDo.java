package com.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.sql.SqlHelper;
import com.sql.SqlModel;
import com.tools.Tool;

public class ExcelDo {

	public static void countExcel(String userId)
	{
		String filePath = "";
		File f=null;
		JFileChooser jfc = new JFileChooser(".");
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileSelectionMode(2);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel文件(*.xls)", new String[] { "xls", "xlsx" });
	    jfc.setFileFilter(filter);
	    jfc.setDialogTitle("请选择导出路径");
	    
	    jfc.setSelectedFile(new File("统计信息"));
	    
		String csql0="select className from classinfo where grade='"+Tool.getGrade(userId)+"'";
	    String[] classArray=SqlModel.classInfoQuery(csql0);
	    
	    List<String[]> classList=new ArrayList<String[]>();
	   
	    for(int i=1;i<classArray.length;i++)
	    {
	    	//System.out.println(classArray[i]);
	    	String[] classinfo=new String[10];
	    	classinfo[0]=classArray[i];
	    	//班级人数
			String strCell1="select count(*) from jobinfo where class='"+Tool.string2UTF8(classArray[i])+"'";
			classinfo[1]=SqlModel.getInfo(strCell1);
			String strCell2="select count(*) from jobinfo where class='"+Tool.string2UTF8(classArray[i])+"' and state='"+Tool.string2UTF8("保研")+"'";
			classinfo[2]=SqlModel.getInfo(strCell2);
			String strCell3="select count(*) from jobinfo where class='"+Tool.string2UTF8(classArray[i])+"' and state='"+Tool.string2UTF8("考研")+"'";
			classinfo[3]=SqlModel.getInfo(strCell3);
			String strCell4="select count(*) from jobinfo where class='"+Tool.string2UTF8(classArray[i])+"' and state='"+Tool.string2UTF8("已签")+"'";
			classinfo[4]=SqlModel.getInfo(strCell4);
			String strCell5="select count(*) from jobinfo where class='"+Tool.string2UTF8(classArray[i])+"' and state='"+Tool.string2UTF8("未签")+"'";
			classinfo[5]=SqlModel.getInfo(strCell5);
			String strCell6="select count(*) from jobinfo where class='"+Tool.string2UTF8(classArray[i])+"' and state='"+Tool.string2UTF8("考公务员")+"'";
			classinfo[6]=SqlModel.getInfo(strCell6);
			String strCell7="select avg(salary) from jobinfo where class='"+Tool.string2UTF8(classArray[i])+"' and state='"+Tool.string2UTF8("已签")+"' and salary!=''";
			String avg_salary=SqlModel.getInfo(strCell7);   
			if(avg_salary==null){
				classinfo[7]="";
			}else{
				double d=Double.valueOf(avg_salary.toString());
				classinfo[7]=get2Double(d)+"";				
			}
			String strCell8="select count(*) from jobinfo where class='"+Tool.string2UTF8(classArray[i])+"' and state='"+Tool.string2UTF8("出国")+"'";
			classinfo[8]=SqlModel.getInfo(strCell8);
			
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		    String newdate = sdf.format(new Date());
			classinfo[9]=newdate;
			
			classList.add(classinfo);
	    }
	    
	    int result = jfc.showSaveDialog(null);
	    if (result == 0) {
	    	f = jfc.getSelectedFile();
	    	filePath = f.getAbsolutePath() + ".xls";
	    	String[] name = filePath.split("xls");
	    	
	        if (name.length == 2) {
	        	filePath = name[0] + "xls";
	        }
	        
	        String[] title = { "班级", "班级人数", "保研", "考研", "已签约", "未签约", 
	          "考公务员", "已签约平均工资", "出国", "统计日期" };
	        
        	OutputStream os = null;
        	WritableWorkbook wwb = null;
        	WritableSheet sheet;
	        
	        try
	        {
	        	os = new FileOutputStream(filePath);
	        	wwb = Workbook.createWorkbook(os);
	        	
	        	sheet = wwb.createSheet("111", 0);
	        	
	        	sheet.setColumnView(0, 15);
	        	sheet.setColumnView(1, 15);
	        	sheet.setColumnView(2, 8);
	        	sheet.setColumnView(3, 8);
	        	sheet.setColumnView(4, 11);
	        	sheet.setColumnView(5, 11);
	        	sheet.setColumnView(6, 11);
	        	sheet.setColumnView(7, 23);
	        	sheet.setColumnView(8, 11);
	        	sheet.setColumnView(9, 20);

	        	WritableFont wfont = new WritableFont(WritableFont.createFont("宋体"), 11);
	        	WritableCellFormat wc = new WritableCellFormat(wfont);
	        	wc.setAlignment(Alignment.CENTRE);
	        	wc.setBorder(Border.ALL, BorderLineStyle.THIN);

	        	WritableFont wfont2 = new WritableFont(WritableFont.createFont("宋体"), 11, WritableFont.BOLD);
	        	WritableCellFormat wc2 = new WritableCellFormat(wfont2);
	        	wc2.setAlignment(Alignment.CENTRE);
	        	wc2.setBorder(Border.ALL, BorderLineStyle.THIN);


	        	WritableCellFormat wc1 = new WritableCellFormat(wfont);
	        	wc1.setAlignment(Alignment.CENTRE);
	        	wc1.setBackground(Colour.GRAY_50);
	        	wc1.setBorder(Border.ALL, BorderLineStyle.THIN);

	        	for (int i = 0; i < title.length; i++) 
	        	{
	        		Label label = new Label(i, 0, title[i], wc1);
	        		sheet.addCell(label);
	        	}
	        	
	    	    ListIterator<String[]> lit=classList.listIterator();
	    	    int j=1;
	    	    while(lit.hasNext())
	    	    {
	    	    	String[] temp=lit.next();
		        	for (int i = 0; i < temp.length; i++) {
	        			Label label = new Label(i, j, temp[i], wc);
	        			sheet.addCell(label);
		        	}
		        	j++;
	    	    }


	        	wwb.write();

	        	
	        } catch (Exception e1) {
	        	e1.printStackTrace();
	        }finally{
	        	try {
					wwb.close();
					os.close();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        	
	        }
	        
	    }
	    
	}
	
	
	public static void excel2db(String userId,JTable jt,JComboBox classJcb,JLabel infoLabel)
	{
		String error = "";
		boolean judge = true;

	    String filePath = "";
	    File f = null;
	    JFileChooser jfc = new JFileChooser(".");
	    jfc.setAcceptAllFileFilterUsed(false);
	    jfc.setFileSelectionMode(2);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel文件(*.xls)", new String[] { "xls", "xlsx" });
	    jfc.setFileFilter(filter);
	    jfc.setDialogTitle("请选择导入路径");
	    int result = jfc.showOpenDialog(jt);
	    
	    if (result == 0) {
	    	f = jfc.getSelectedFile();
	        filePath = f.getAbsolutePath();

	        InputStream inStream = null;
	        Workbook workBook = null;
	        try {
	        	judge = false;
	        	inStream = new FileInputStream(filePath);
	        	workBook = Workbook.getWorkbook(inStream);
	        	Sheet inSheet = workBook.getSheet(0);

	        	SqlHelper sh=new SqlHelper();
	        	String strSql = "";

	        	int columns = inSheet.getColumns();
	        	int rows = inSheet.getRows();

	        	String lie = (String)classJcb.getSelectedItem();
	        	String major = "";

	        	int sum = 0;
	        	int sum2 = 0;

	        	if (columns > 12) {
	        		infoLabel.setText("excel列数过多！！");
	        	} else {
	        		for (int i = 1; i < rows - 4; i++) {
	        			if (inSheet.getCell(1, i).getContents().equals(""))
	        				sum++;
	        			else {
	        				try
	        				{
	        					if (!inSheet.getCell(10, i).getContents().equals("")) {
	        						String money = inSheet.getCell(10, i).getContents();
	        						Double.parseDouble(money);
	        					}
	        					strSql = "select * from jobinfo where id = '" + Tool.string2UTF8(inSheet.getCell(1, i).getContents()) + "'";
	        					//Statement stmt = con.createStatement();
	        					//ResultSet rs = stmt.executeQuery(strSql);
	        					ResultSet rs=sh.Query(strSql);
	        					if (rs.next()) 
	        					{
	        						String yuanBanJi = Tool.UTF82String(rs.getString("class"));
	        						String xueHao = Tool.UTF82String(rs.getString("id"));
	        						String yuanXingMing = Tool.UTF82String(rs.getString("studentName"));

	        						int a = JOptionPane.showConfirmDialog(null, yuanBanJi + "的" + yuanXingMing + "（" +  xueHao + ")已经存在，要覆盖吗?", "",  0);
	        						
	        						if (a == 0) {
	        							String banJi = (String)classJcb.getSelectedItem();
	        							
	        							major=SqlModel.getInfo("select major from classinfo where grade='"+Tool.getGrade(userId)+"' and className='"+Tool.string2UTF8(banJi)+"'");

	        							strSql = "update jobinfo set class = '" + 
					                        Tool.string2UTF8(classJcb.getSelectedItem().toString().trim()) + 
					                        "', state = '" + 
					                        Tool.string2UTF8(inSheet.getCell(4, i).getContents()) + 
					                        "', company = '" + 
					                        Tool.string2UTF8(inSheet.getCell(5, i).getContents()) + 
					                        "', protocalState = '" + 
					                        Tool.string2UTF8(inSheet.getCell(6, i).getContents()) + 
					                        "', nowState = '" + 
					                        Tool.string2UTF8(inSheet.getCell(7, i).getContents()) + 
					                        "',area = '" + 
					                        Tool.string2UTF8(inSheet.getCell(8, i).getContents()) + 
					                        "',city = '" + 
					                        Tool.string2UTF8(inSheet.getCell(9, i).getContents()) + 
					                        "', salary = '" + 
					                        Tool.string2UTF8(inSheet.getCell(10, i).getContents()) + 
					                        "',note = '" + 
					                        Tool.string2UTF8(inSheet.getCell(11, i).getContents()) + 
					                        "',major = '" + 
					                        Tool.string2UTF8(major) + 
					                        "',mtime = '" + 
					                        Tool.string2UTF8(Tool.whoModify(userId)) + 
					                        "',grade='"+
					                        Tool.getGrade(userId)+
					                        "'where id = '" + 
					                        Tool.string2UTF8(xueHao.trim()) + "'";

	        							sh.Update(strSql);
//				                    	this.stmt = this.con.createStatement();
//				                    	this.stmt.executeUpdate(strSql);
				                    	sum2++;
	        						} else {
	        							sum++;
	        						}
	        					} else {
	        						
	        						String banJi = (String)classJcb.getSelectedItem();
	        						major=SqlModel.getInfo("select major from classinfo where grade='"+Tool.getGrade(userId)+"' and className='"+Tool.string2UTF8(banJi)+"'");

	        						strSql = "insert into jobinfo (class,id,studentName,sex,state,company,protocalState,nowState,area,city,salary,note,major,mtime,grade) values ('" + 
	        								Tool.string2UTF8(classJcb.getSelectedItem().toString().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(1, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(2, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(3, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(4, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(5, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(6, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(7, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(8, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(9, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(10, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(inSheet.getCell(11, i).getContents().trim()) + 
					                      "','" + 
					                      Tool.string2UTF8(major.trim()) +
					                      "','" + 
					                      Tool.string2UTF8(Tool.whoModify(userId)) + 
					                      "','"+
					                      Tool.getGrade(userId)+
					                      "')";
//	                    this.stmt = this.con.createStatement();
//	                    this.stmt.executeUpdate(strSql);
	        						sh.Update(strSql);

	                    sum2++;
	                  }
	                }
	                catch (Exception e1) {
	                  infoLabel.setText(inSheet.getCell(1, i)
	                    .getContents() + "的薪金不为数!");
	                  e1.printStackTrace();
	                }
	              }
	            }
	            sh.close();
	            judge = true;
	            error = "由于学号为空或者薪金不为数无法增加或没有覆盖的个数为:  " + sum + 
	              "  其他的都导入成功！   成功数为：" + sum2;
	          }
	        }
	        catch (FileNotFoundException e1) {
	          infoLabel.setText(e1.getMessage());
	        } catch (BiffException e1) {
	        	infoLabel.setText(e1.getMessage());
	        } catch (IOException e1) {
	        	infoLabel.setText(e1.getMessage());
	        } finally {
	          
	        }

	        if (judge)
	          infoLabel.setText(error);
	      }
		
	}
	
	public static void db2excel(String userId,JTable jt,JComboBox classJcb,JComboBox majorJcb,JLabel infoLabel,JobInfoTable jit,DefaultTableModel dt)
	{
		String filePath = "";
		File f=null;
		JFileChooser jfc = new JFileChooser(".");
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileSelectionMode(2);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel文件(*.xls)", new String[] { "xls", "xlsx" });
	    jfc.setFileFilter(filter);
	    jfc.setDialogTitle("请选择导出路径");
	    if(classJcb.getSelectedItem().toString().trim().equals("")&&majorJcb.getSelectedItem().toString().trim().equals("")){
	    	jfc.setSelectedFile(new File("全部"));
	    }else if(classJcb.getSelectedIndex()!=0){
	    	jfc.setSelectedFile(new File(classJcb.getSelectedItem().toString()));
	    }else if(majorJcb.getSelectedIndex()!=0){
	    	jfc.setSelectedFile(new File(majorJcb.getSelectedItem().toString()));
	    }else{
	    	jfc.setSelectedFile(new File(""));
	    }
	    //jfc.setSelectedFile(new File(classJcb.getSelectedItem().toString()));
	    int result = jfc.showSaveDialog(null);
	    if (result == 0) {
	    	f = jfc.getSelectedFile();
	    	filePath = f.getAbsolutePath() + ".xls";
	    	String[] name = filePath.split("xls");
	    	
	        if (name.length == 2) {
	        	filePath = name[0] + "xls";
	        }
	        
	        String[] title = { "序号", "学号", "姓名", "性别", "签约状态", "签约单位", 
	          "协议书状态", "当前状态", "签约单位所在城市", "薪金/月", "备注（主要写近期做什么）" };
	        
        	OutputStream os = null;
        	WritableWorkbook wwb = null;
        	WritableSheet sheet;
	        
	        try
	        {
	        	os = new FileOutputStream(filePath);
	        	wwb = Workbook.createWorkbook(os);
	        	
	        	sheet = wwb.createSheet("111", 0);
	        	
//	        	if (!this.out)
//	        	{
//	        		//WritableSheet sheet;
//	        		if (majorJcb.getSelectedIndex() == 0)
//	        			sheet = wwb.createSheet("", 0);
//	        		else
//	        			sheet = wwb.createSheet((String)majorJcb.getSelectedItem(), 0);
//	        	}else{
//	        		sheet = wwb.createSheet((String)classJcb.getSelectedItem(), 0);
//	        	}
	        	
	        	sheet.setColumnView(0, 8);
	        	sheet.setColumnView(1, 15);
	        	sheet.setColumnView(2, 12);
	        	sheet.setColumnView(3, 8);
	        	sheet.setColumnView(4, 11);
	        	sheet.setColumnView(5, 23);
	        	sheet.setColumnView(6, 15);
	        	sheet.setColumnView(7, 12);
	        	sheet.setColumnView(8, 17);
	        	sheet.setColumnView(9, 8);
	        	sheet.setColumnView(10, 8);
	        	sheet.setColumnView(11, 35);

	        	sheet.mergeCells(8, 0, 9, 0);

	        	WritableFont wfont = new WritableFont(WritableFont.createFont("宋体"), 11);
	        	WritableCellFormat wc = new WritableCellFormat(wfont);
	        	wc.setAlignment(Alignment.CENTRE);
	        	wc.setBorder(Border.ALL, BorderLineStyle.THIN);

	        	NumberFormat nf = new NumberFormat("#.##");
	        	WritableCellFormat wcf = new WritableCellFormat(nf);
	        	wcf.setAlignment(Alignment.CENTRE);
	        	wcf.setBorder(Border.ALL, BorderLineStyle.THIN);

	        	WritableFont wfont2 = new WritableFont(WritableFont.createFont("宋体"), 11, WritableFont.BOLD);
	        	WritableCellFormat wc2 = new WritableCellFormat(wfont2);
	        	wc2.setAlignment(Alignment.CENTRE);
	        	wc2.setBorder(Border.ALL, BorderLineStyle.THIN);

	        	boolean judge = false;
	        	for (int i = 0; i <= title.length; i++) {
	        		if (i == 9) {
	        			judge = true;
	        		}else if (!judge) {
	        			Label label = new Label(i, 0, title[i], wc2);
	        			sheet.addCell(label);
	        		} else {
	        			Label label = new Label(i, 0, title[(i - 1)], wc2);
	        			sheet.addCell(label);
	        		}
	        	}
	        	
	        	if(jt.getRowCount()!=jit.getRowCount()){
	        		if(classJcb.getSelectedItem().toString().equals(""))
	        		{
		       		 	String sql0="select class,id,studentName,sex,state,company,protocalState," +
		       				"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and 1=?";
		       			String []paras0={"1"};
		       			jit=new JobInfoTable();
		       			jit.jobTableQuery(sql0, paras0);	        			
	        		}else{
		       		 	String sql0="select class,id,studentName,sex,state,company,protocalState," +
		       				"nowState,area,city,salary, note,mtime from jobinfo where grade='"+Tool.getGrade(userId)+"' and class=?";
		       			String []paras0={Tool.string2UTF8(classJcb.getSelectedItem().toString().trim())};
		       			jit=new JobInfoTable();
		       			jit.jobTableQuery(sql0, paras0);
	        		}

	        	}
	        	
	        	for (int i = 1; i <= jit.getRowCount(); i++) 
	        	{
	        		Number number = new Number(0, i, i, wc);
	        		sheet.addCell(number);
	        		for (int x = 1; x < 12; x++) 
	        		{
	        			Label label = new Label(x, i, (String)jit.getValueAt(i - 1, x + 1), wc);
	        			sheet.addCell(label);
	        		}
	        	}

	        	WritableCellFormat wc1 = new WritableCellFormat(wfont);
	        	wc1.setAlignment(Alignment.CENTRE);
	        	wc1.setBackground(Colour.GRAY_50);
	        	wc1.setBorder(Border.ALL, BorderLineStyle.THIN);

	        	String[] title2 = { "班级人数", "保研", "考研", "已签约", "未签约", "考公务员", "已签约平均工资", "出国", "统计日期" };
	        	for (int i = 0; i < title2.length; i++) 
	        	{
	        		Label label = new Label(i, jt.getRowCount() + 3, title2[i], wc1);
	        		sheet.addCell(label);
	        	}
	        	
	        	for (int i = 0; i < 9; i++) {
	        		
        			Label label = new Label(i, jt.getRowCount() + 4, dt.getValueAt(0, i).toString(), wc);
        			sheet.addCell(label);

	        	}

	        	wwb.write();

	        	infoLabel.setText("导出成功！");
	        	
	        } catch (Exception e1) {
	        	infoLabel.setText(e1.getMessage());
	        	e1.printStackTrace();
	        }finally{
	        	try {
					wwb.close();
					os.close();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        	
	        }
	        
	    }
	    

	}
	
	public static double get2Double(double a){
	    DecimalFormat df=new DecimalFormat("0.00");
	    return new Double(df.format(a).toString());
	}
}
