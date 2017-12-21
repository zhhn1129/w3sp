<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="java.util.*,com.cernet.cwui.util.*" %>    
<%
out.clear();
out = pageContext.pushBody();
  HSSFExcelHandler hSSFExcelHandler = (HSSFExcelHandler) request.getAttribute("hSSFExcelHandler");
  if(hSSFExcelHandler!=null)hSSFExcelHandler.handle(); 
%>