package com.stone.h2;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class H2HighVolumeSelect {

	public static void main(String[] args){
//		System.out.println(new H2HighVolumeSelect().getRandomPath(4));
/*		try {
			new H2HighVolumeSelect().populateTable();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			new H2HighVolumeSelect().populateFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void populateTable() throws ClassNotFoundException, SQLException {
//		DeleteDbFiles("");
		
		// TODO Auto-generated method stub
		Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        
        Statement stat = conn.createStatement();  
        // insert data  
        stat.execute("DROP TABLE IF EXISTS TFS_FILE");  
        stat.execute("CREATE TABLE TFS_FILE(ID int auto_increment primary key, path VARCHAR,FILE_SIZE int)");  
        stat.execute("CREATE INDEX idx_path ON TFS_FILE(path)");
        
        stat.close();
        
        PreparedStatement ps = conn.prepareStatement("INSERT INTO TFS_FILE(path,file_size) VALUES(?,?)");
        int count = 0;
        for(int i=0; i<1000*10000; i++){
        	++count;
        	ps.setString(1, getRandomPath(4));
        	ps.setInt(2, 100000);
        	
        	ps.addBatch();
        	if(count % 5000 == 0 ){
        		ps.executeBatch();
        		System.out.println("已插入数：" + count);
        	}
        }
        
        if(count % 5000 != 0){
        	ps.executeBatch();
        }
        
        ps.close();
        // add application code here
        conn.close();
	}

	public void populateFile() throws IOException{
		
		File file = new File("test1.txt");
		int count = 0;
		List<String> paths = new LinkedList<String>();
		for(int i=0; i<1000*10000; i++){
			++count;
			paths.add(getRandomPath(4) + ";100000");
			if(count % 5000 == 0){
				FileUtils.writeLines(file, paths, true);
				paths.clear();
				System.out.println("已产生文件记录：" + count);
			}
		}
		
		if(count % 5000 != 0){
			FileUtils.writeLines(file, paths);
		}
	}
	
	public String getRandomPath(int num){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<num; i++){
			sb.append("/").append(RandomString.getRandomString(8));			
		}

		return sb.toString();
	}
	
	
}
