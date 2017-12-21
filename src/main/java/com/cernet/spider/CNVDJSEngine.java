package com.cernet.spider;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class CNVDJSEngine {
	private static Logger log = Logger.getLogger(CNVDJSEngine.class);
	static String testUrl = "http://www.cnvd.org.cn/webinfo/list?type=14";  
	private static final String jsFileName = "cnvdJsEngine.js";
	 
    public static void main(String[] args){
		try {
			  HashMap<String,Object> map = getCookieMap();
			  String __jsluid = map.get("__jsluid").toString();
			  String __jsl_clearance = map.get("__jsl_clearance").toString();
			  System.out.println(__jsluid);
			  System.out.println(__jsl_clearance);
			  testUrl(__jsluid,__jsl_clearance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
	  }
    
    public static void WriteToFile(String fileName, String content){   
        FileWriter writer = null;  
        try {     
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件,false表示覆盖的方式写入
            writer = new FileWriter(fileName, false);     
            writer.write(content);       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            }catch (IOException e){     
                e.printStackTrace();     
            }     
        }
    }
    
    
    
    /*public static String execJs() throws Exception {
    		String __jsl_clearance = "";
            // 获取脚本引擎
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("javascript");
            try {
                 // 读取js文件     
                 FileReader reader = new FileReader(jsFileName);    
                 // 执行指定脚本    
                engine.eval(reader);  
                if(engine instanceof Invocable) {     
                    Invocable invoke = (Invocable)engine;      
                    Object result = (String)invoke.invokeFunction("getResult");    
                    String resultStr = result.toString();
                    System.out.println("result = " + resultStr);     
                    if(resultStr!=null && !"".equals(resultStr)){
                    	//修改result，去掉无用代码
                    	resultStr = resultStr.replaceAll("while\\(window\\._phantom\\|\\|window\\.__phantomas\\)\\{\\};", "");
                    	System.out.println(resultStr);
                    	resultStr = resultStr.substring(0, resultStr.indexOf("setTimeout"));
                    	resultStr +="return(dc);};";
                    	System.out.println(resultStr);
                    	//再次执行js
                    	engine.eval(resultStr);
                    	if(engine instanceof Invocable) {
                    		Invocable jsinvoke = (Invocable)engine;      
                            __jsl_clearance = jsinvoke.invokeFunction("l").toString();  
                            System.out.println(__jsl_clearance.toString());
                    	}
                    }
                 }  
                reader.close();
            } catch (Exception e) {
                log.info("EXECJS EXCEPTION : EXECUTE JAVASCRIPT EXCEPTION", e);
                throw (e);
            }
            return __jsl_clearance;
   }*/
    
    public static String execJs() throws Exception {
		String __jsl_clearance = "";
        // 获取脚本引擎
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("javascript");
        try {
             // 读取js文件     
             FileReader reader = new FileReader(jsFileName);    
             // 执行指定脚本    
            engine.eval(reader);  
            if(engine instanceof Invocable) {     
                Invocable invoke = (Invocable)engine;      
                Object result = (String)invoke.invokeFunction("getResult");    
                String resultStr = result.toString();
//                System.out.println("127:result = " + resultStr);     
                if(resultStr!=null && !"".equals(resultStr)){
                	//修改result，去掉无用代码
                	resultStr = resultStr.replaceAll("while\\(window\\._phantom\\|\\|window\\.__phantomas\\)\\{\\};", "");
//                	System.out.println(resultStr);
                	resultStr = resultStr.substring(0, resultStr.indexOf("setTimeout"));
                	resultStr +="return(dc);};";
                	//新加
                	resultStr = resultStr.replaceAll("var h=document.createElement\\(\\'div\\'\\);","");
                	resultStr = resultStr.replaceAll("h.innerHTML=\\'<a href=\\\\\'/\\\\\'>x</a>\\';","");
                	resultStr = resultStr.replaceAll("h=h.firstChild.href;","var h=\\'/https://\\';");
                	resultStr = resultStr.replaceAll("return return", "return");
//                	System.out.println(resultStr);
                	//再次执行js
                	engine.eval(resultStr);
                	if(engine instanceof Invocable) {
                		Invocable jsinvoke = (Invocable)engine;      
                		resultStr = jsinvoke.invokeFunction("l").toString();  
                      System.out.println("134:"+resultStr.toString());
                        //再次解析__jsl_clearance
                        resultStr = resultStr.replace("__jsl_clearance=", "var __jsl_clearance='");
                        resultStr = resultStr.replaceAll("String.fromCharCode\\(", "'+String.fromCharCode\\(");
                        resultStr = resultStr.replaceAll("\\)", "\\)+'");
                        System.out.println("139:"+resultStr.toString());
                        if(resultStr.endsWith("+'")){
                        	//去掉最后的两个字符+'
                            resultStr = resultStr.substring(0, resultStr.length()-2);
                        }else{
                        	resultStr += "'";
                        }
                        resultStr = "var l = function(){"+resultStr+";return ('__jsl_clearance='+__jsl_clearance);};";
                        //再次执行js
                    	engine.eval(resultStr);
                    	if(engine instanceof Invocable) {
                    	    jsinvoke = (Invocable)engine;      
                    	    __jsl_clearance = jsinvoke.invokeFunction("l").toString();  
                            System.out.println(__jsl_clearance.toString());
                    	}
                	}
                }
             }  
            reader.close();
        } catch (Exception e) {
            log.info("EXECJS EXCEPTION : EXECUTE JAVASCRIPT EXCEPTION", e);
            throw (e);
        }
        return __jsl_clearance;
}
    
    public static HashMap<String,Object>  getCookieMap() throws Exception {  
    	HashMap<String,Object> map = new HashMap<String,Object>();
	    System.out.println("----getCookieMap----");  
	    CloseableHttpClient client = HttpClients.createDefault();  
	    try {  
	      HttpGet httpGet = new HttpGet(testUrl); 
	      //设置headers
	      httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
	      httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");  
	      httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");  
	      httpGet.setHeader("Connection", "keep-alive");  
	      httpGet.setHeader("Host", "www.cnvd.org.cn");  
	      httpGet.setHeader("If-Modified-Since", "Mon, 29 Aug 2016 01:59:34 GMT");  
	      httpGet.setHeader("If-None-Match", "W/\"1724-1472435974000\"");  
	      httpGet.setHeader("Referer", testUrl);  
	      httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
	      
//	      System.out.println("request line:" + httpGet.getRequestLine());  
	      HttpResponse httpResponse = client.execute(httpGet);  
	      map = getContent(httpResponse);
	      String sHtmlJs = map.get("htmlJs").toString();
//		  System.out.println(sHtmlJs);
		  sHtmlJs = sHtmlJs.replaceAll("<script>", "").replaceAll("</script>", "");
		  sHtmlJs = sHtmlJs.replaceAll("eval", "return");
		  sHtmlJs = "function getResult(){"+sHtmlJs+"}";
		  //写入到文件中
		  WriteToFile(jsFileName,sHtmlJs);
		  //解析js文件
		  String __jsl_clearance = execJs();
		  map.put("__jsl_clearance", __jsl_clearance);
	    } catch (IOException e) {  
	      e.printStackTrace();  
	    } finally {  
	      try {  
	        client.close();  
	      } catch (IOException e) {  
	        e.printStackTrace();  
	      }  
	    }
	    return map;
//    	return null;
  }
	  
	  public static void printResponse(HttpResponse httpResponse)  
		      throws ParseException, IOException {  
		    HttpEntity entity = httpResponse.getEntity();  
//		    System.out.println("status:" + httpResponse.getStatusLine());  
//		    System.out.println("headers:");  
		    HeaderIterator iterator = httpResponse.headerIterator();  
		    while (iterator.hasNext()) {  
		      System.out.println("\t" + iterator.next());  
		    }  
		    // 判断响应实体是否为空  
		    if (entity != null) {  
		      String responseString = EntityUtils.toString(entity);  
		      System.out.println("response length:" + responseString.length());  
		      System.out.println("response content:"  
		    		  + responseString.trim().replace("\r\n", ""));  
		    }  
	}
	  
	public static HashMap<String,Object> getContent(HttpResponse httpResponse)  
		      throws ParseException, IOException { 
		HashMap<String,Object> map = new HashMap<String,Object>();
		String responseString = "";
	    HttpEntity entity = httpResponse.getEntity();  
	    HeaderIterator iterator = httpResponse.headerIterator();  
	    while (iterator.hasNext()) {  
	    	String header = iterator.next().toString();
			 if(header!=null && header.contains("__jsluid")){
				 String __jsluid = header.substring(header.indexOf("__jsluid="), header.indexOf(";"));
				 map.put("__jsluid", __jsluid);
			 }
	    }  
	    // 判断响应实体是否为空  
	    if (entity != null) {  
	       responseString = EntityUtils.toString(entity);  
	       responseString = responseString.trim();
	       map.put("htmlJs", responseString);
	    }
	    return map;  
	}
	
	public static void testUrl(String __jsluid,String __jsl_clearance) throws Exception {  
	    System.out.println("----testUrl----");  
	    CloseableHttpClient client = HttpClients.createDefault();  
	    try {  
	      HttpGet httpGet = new HttpGet(testUrl); 
	      //设置headers
	      httpGet.setHeader("Accept", "*/*");  
	      httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");  
	      httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");  
	      httpGet.setHeader("Connection", "keep-alive");  
	      httpGet.setHeader("Cookie",__jsluid+";"+__jsl_clearance);
	      httpGet.setHeader("Host", "www.cnvd.org.cn");  
	      httpGet.setHeader("If-Modified-Since", "Mon, 29 Aug 2016 01:59:34 GMT");  
	      httpGet.setHeader("If-None-Match", "W/\"1724-1472435974000\"");  
	      httpGet.setHeader("Referer", testUrl);  
	      httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
    	  
//	      System.out.println("request line:" + httpGet.getRequestLine()); 
	      HttpResponse httpResponse1 = client.execute(httpGet);  
	      printResponse(httpResponse1);  
	    } catch (IOException e) {  
	      e.printStackTrace();  
	    } finally {  
	      try {  
	        client.close();  
	      } catch (IOException e) {  
	        e.printStackTrace();  
	      }  
	    }  
  }
}
