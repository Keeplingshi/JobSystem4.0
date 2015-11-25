package com.other;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.sql.SqlHelper;
import com.tools.Tool;

public class GradeInfoTable extends AbstractTableModel{

	private SqlHelper sh;
	//rowData用来存放数据
	//columnNames存放列名
	@SuppressWarnings("rawtypes")
	private Vector<Vector> rowData;
	private Vector<String> columnNames;
	
	//查询需要显示的人事信息
	@SuppressWarnings("rawtypes")
	public void gradeTableQuery(String sql,String []paras)
	{
		sh=new SqlHelper();
		ResultSet rs=sh.Query(sql, paras);
		columnNames=new Vector<String>();
		rowData=new Vector<Vector>();
		//此方法便 于扩展
		try{
			ResultSetMetaData rsmd=rs.getMetaData();
			columnNames.add("序号");
			columnNames.add("年级");
			
			int x=0;
			while(rs.next())
			{
				Vector<String> temp=new Vector<String>();
				temp.add(++x+"");
				for(int i=0;i<rsmd.getColumnCount();i++)
				{
					temp.add(Tool.UTF82String(rs.getString(i+1)));
				}
				rowData.add(temp);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			sh.close();
		}
	}
	
	//增删改人事信息
	//添加、修改的方法
	public boolean Update(String sql,String []paras)
	{   
		boolean b=true;
		sh = new SqlHelper();
		try{
			b=sh.Update(sql, paras);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sh.close();
		}
		return b;
	}	
	
	public String getColumnName(int column) {
		return (String)columnNames.get(column);
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowData.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector)rowData.get(rowIndex)).get(columnIndex);
	}

	
}
