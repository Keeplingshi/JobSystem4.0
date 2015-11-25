/*
 * 数据库操作
 */

package com.sql;

import java.sql.*;

import javax.swing.JOptionPane;

public class SqlHelper {

	//定义所有数据库操作需要的工具
	Connection ct=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	//定义数据库信息
//	String driver="com.mysql.jdbc.Driver";	//数据库驱动。
//	String url="jdbc:mysql://219.219.60.248:3306/JobSystem?user=root&password=root";
	
//	String driver="com.mysql.jdbc.Driver";	//数据库驱动。
//	String url="jdbc:mysql://202.119.206.153:3306/JobSystem?user=root&password=root";
	
//	String driver="com.mysql.jdbc.Driver";	//数据库驱动。
//	String url="jdbc:mysql://csxg.cumt.edu.cn:3306/JobSystem?user=root&password=root";
	
	String driver="com.mysql.jdbc.Driver";	//数据库驱动。
	String url="jdbc:mysql://localhost:3306/JobSystem?user=root&password=123456";
	
	//加载驱动
	public void Driver()
	{
		try {
			Class.forName(driver);
			ct=DriverManager.getConnection(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "数据库无法连接");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	//关闭
	public void close()
	{
		try {
			if(rs!=null) {rs.close();}
			if(ct!=null) {ct.close();}
			if(ps!=null) {ps.close();}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet Query(String sql)
	{
		this.Driver();
		try{
			ps = ct.prepareStatement(sql);
			rs=ps.executeQuery();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	//查询
	public ResultSet Query(String sql,String []paras)
	{
		this.Driver();
		
		try{
			ps=ct.prepareStatement(sql);
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	public boolean Update(String sql)
	{
		this.Driver();
		boolean b=true;
		try{
			ps=ct.prepareStatement(sql);
			ps.executeUpdate();
		}catch (SQLException e){
			b=false;
			e.printStackTrace();
		}
		return b;
	}
	
	//增删改(更新)
	public boolean Update(String sql,String []paras)
	{
		this.Driver();
		boolean b=true;
		try{
			ps=ct.prepareStatement(sql);
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			ps.executeUpdate();
		}catch (SQLException e){
			b=false;
			e.printStackTrace();
		}
		return b;
	}
	
}
