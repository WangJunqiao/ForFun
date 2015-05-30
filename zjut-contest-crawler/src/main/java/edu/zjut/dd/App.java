package edu.zjut.dd;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class App {
	final String out_dir = "D:/zjut-contest/";
	
	final String lineSeparator = System.getProperty("line.separator", "\n");  
	
	
	CloseableHttpClient httpclient = HttpClients.createDefault();
	
	void download(String url, String local_filename) throws Exception{
		HttpGet httpGet = new HttpGet(url);
    	CloseableHttpResponse response1 = httpclient.execute(httpGet);
    	try {
    	    HttpEntity entity1 = response1.getEntity();
    
    	    BufferedReader reader = new BufferedReader(new InputStreamReader(entity1.getContent(), "UTF-8"));
    	    StringBuilder sb = new StringBuilder();
    	    String line;
    	    
    	    while ((line = reader.readLine()) != null) {
    	    	line = line.replace("Standing.aspx?MID", "Standing-MID");
    	    	sb.append(line).append(lineSeparator);
    	    }
    	    FileWriter fw = new FileWriter(out_dir + local_filename);
    	    fw.write(sb.toString());
    	    fw.close();
    	    
    	    EntityUtils.consume(entity1);
    	} finally {
    	    response1.close();
    	}
	}
	
	String parseTitle(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
    	CloseableHttpResponse response1 = httpclient.execute(httpGet);
    	// The underlying HTTP connection is still held by the response object
    	// to allow the response content to be streamed directly from the network socket.
    	// In order to ensure correct deallocation of system resources
    	// the user MUST call CloseableHttpResponse#close() from a finally clause.
    	// Please note that if response content is not fully consumed the underlying
    	// connection cannot be safely re-used and will be shut down and discarded
    	// by the connection manager. 
    	try {
    	    System.out.println(response1.getStatusLine());
    	    HttpEntity entity1 = response1.getEntity();
    	    // do something useful with the response body
    	    // and ensure it is fully consumed
    
    	    BufferedReader reader = new BufferedReader(new InputStreamReader(entity1.getContent(), "UTF-8"));
    	    StringBuilder sb = new StringBuilder();
    	    String line, fileName = "noname";
    	    
    	    while ((line = reader.readLine()) != null) {
    	    	sb.append(line).append(lineSeparator);
    	    	if (line.startsWith("<h3>Contest Problems -<label id=\"cphPage_ContestName\">")) {
    	    		line = line.replace("<h3>Contest Problems -<label id=\"cphPage_ContestName\">", "");
    	    		line = line.replace("</label></h3>", "");
    	    		fileName = line;
    	    	} else if (line.startsWith("Start time:")) {
    	    		line = line.replace("Start time:  <span id=\"cphPage_Label1\"><font color=\"#FF6699\">", "");
    	    		Scanner scanner = new Scanner(line);
    	    		String time = scanner.next();
    	    		fileName = time + " " + fileName + ".aspx";
    	    	}
    	    }
    	    FileWriter fw = new FileWriter(out_dir + fileName);
    	    fw.write(sb.toString());
    	    fw.close();
    	    
    	    EntityUtils.consume(entity1);
    	    return fileName;
    	    
    	} finally {
    	    response1.close();
    	}
	}
	
	void run() {
		for (int i = 1; i < 1000; i ++) {
    		String url = "http://cpp.zjut.edu.cn/Contest_Problems.aspx?MID=" + i;
    		try {
    			String title = parseTitle(url);
    			
    			download(url, title);
    			download("http://cpp.zjut.edu.cn/Standing.aspx?MID=" + i, "Standing-MID=" + i);
    		} catch (Exception e) {
    			System.out.println(e.toString());
    		}
    	}
    	
	}
	
    public static void main(String[] args) throws Exception{
    	System.out.println("Run zjut contest crawler......");
    	
    	
    	App ins = new App();
    	ins.run();
    	
    }
}
