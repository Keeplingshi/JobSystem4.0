package com.temp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.sql.SqlModel;
import com.tools.Tool;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test();
	}
	
	public Test()
	{
		//select className from classinfo where major='%E8%AE%A1%E7%A7%91'
		
//		String sql="select className from classinfo where major=?";
//		String[] paras={Tool.string2UTF8("¼Æ¿Æ")};
//		String sql="select className from classinfo where major='%E8%AE%A1%E7%A7%91'";
//		String[] classArray=SqlModel.majorInfoQuery();
//		
//		for(int i=0;i<classArray.length;i++)
//		{
//			System.out.println(classArray[i]);
//		}
//		
//	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//	    String newdate = sdf.format(new Date());
//	    String m=newdate+" ³Â±ó";
//		String usql="update jobinfo set mtime='"+Tool.string2UTF8(m)+"' where id='08123320'";
//		SqlModel.updateInfo(usql);
//		
		
//		 String cellSql="select id,positive from jobinfo";
//		 //initJTable(jt,SqlModel.jobInfoQuery(cellSql));
//		HashMap<String, String> h=SqlModel.jobPositiveQuery(cellSql);
//		System.out.println(h);
//		//System.out.println(h.get(""));
	}

}
