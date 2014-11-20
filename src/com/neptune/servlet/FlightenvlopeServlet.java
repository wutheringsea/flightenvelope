package com.neptune.servlet;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.neptune.model.FlightOcxTask;
import com.neptune.utils.ExcelRead;

public class FlightenvlopeServlet extends HttpServlet {

	private enum IntevalTypes {
		DOUBLEINTEVAL, HINTEVAL, MINTEVAL, POINT   //区间类型
	}
	
	//八种状态（两种形状：圆形、三角形；四种颜色：红黄蓝绿）
	private static final String ANCHORSIDES_ORIGIN = "0"; //非新增
	private static final String ANCHORSIDES_NEW = "3"; //新增
	
	private static final String COLOR_NOT_EDIT = "EE2C2C";  //未编制
	private static final String COLOR_NOT_COMPLETE = "0000EE";  //已编制-未完成
	private static final String COLOR_HALF_COMPLETE = "FFD700";  //已编制-部分完成
	private static final String COLOR_COMPLETE = "008B00";  //已编制-已完成
	
	
	/**
	 * Constructor of the object.
	 */
	public FlightenvlopeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ExcelRead reader = new ExcelRead();
		Map<String, List<Object>>  datamap_line = null;  //速度线dataset
		Map<String, List<Object>>  datamap_area = null;  //边界线dataset
		Map<String, List<Object>>  datamap_point = null;  //采样点dataset

		
		StringBuilder sb = new StringBuilder();
		
		request.setCharacterEncoding("UTF-8");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);//检查输入请求是否为multipart表单数据。
	    if (isMultipart == true) {
	    	 FileItemFactory factory = new DiskFileItemFactory();//为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。
	    	 ServletFileUpload upload = new ServletFileUpload(factory);
	    	 try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem fileItem : items) {
					if ("SpeedFile".equals(fileItem.getFieldName())) {
						InputStream in = fileItem.getInputStream();
						datamap_line = reader.readEXCELBeanFromInputStream(in, new String[]{"statusMachNum","statusHeight"},FlightOcxTask.class);
						
					}else if ("AreaFile".equals(fileItem.getFieldName())) {
						InputStream in = fileItem.getInputStream();
						datamap_area = reader.readEXCELBeanFromInputStream(in, new String[]{"statusMachNum","statusHeight"},FlightOcxTask.class);
					}else if ("PointFile".equals(fileItem.getFieldName())) {
						InputStream in = fileItem.getInputStream();
						datamap_point = reader.readEXCELBeanFromInputStream(in, new String[]{"statusMachNum","statusHeight",
								"statusSpeed","attribute03","statusAction","isfinished","isadded"},FlightOcxTask.class);
					}
				}
				
				sb.append("<graph caption='飞行包线图' subcaption='完成情况统计' xAxisName='马赫数' yAxisName='飞行高度' xAxisLabelMode='categories' formatNumberScale='0' decimalPrecision='0' shownames='1' xAxisMaxValue='2.0' xAxisMinValue='0' yaxisminvalue='0' yaxismaxvalue='20000' rotateNames='0' >");
				sb.append("<categories><category label='0' x='0' showVerticalLine='1' lineDashed='1'/><category label='0.2' x='0.2' showVerticalLine='1' lineDashed='1'/><category label='0.4' x='0.4' showVerticalLine='1' lineDashed='1'/><category label='0.6' x='0.6' showVerticalLine='1' lineDashed='1'/><category label='0.8' x='0.8' showVerticalLine='1' lineDashed='1' clickURL='n-http://www.avicit.com' />" +
						"<category label='1.0' x='1.0' showVerticalLine='1' lineDashed='1'/><category label='1.2' x='1.2' showVerticalLine='1' lineDashed='1'/><category label='1.4' x='1.4' showVerticalLine='1' lineDashed='1'/><category label='1.6' x='1.6' showVerticalLine='1' lineDashed='1'/><category label='1.8' x='1.8' showVerticalLine='1' lineDashed='1'/><category label='2.0' x='2.0' showVerticalLine='1' lineDashed='1'/>" +
				"</categories>");
				
				//1.处理速度线文件
				dealSpeedLine(sb,datamap_line);
				
				//2.处理边界线文件
				dealAreaLine(sb,datamap_area);
				    
				//3.处理采样点数据文件(考虑嵌套dataset)
				dealSamplePoint(sb,datamap_point);
				
				sb.append("</graph>");
				
				
			} catch (FileUploadException e) {
				e.printStackTrace();
			}

	    	 
	    }
		
	    System.out.println(sb.toString());
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/xml");
//		response.setHeader("Cache-Control", "no-cache");
		request.setAttribute("dataxml", sb.toString());
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/futest/flightenvelope.jsp");
		rd.forward(request, response);
		
//		PrintWriter out = response.getWriter();
//		out.println(sb.toString());
//		out.flush();
//		out.close();
		
		
	}

	
	/**
	 * 处理速度线文件
	 * @param sb 返回速度线对应的dataset的xml标签数据
	 * @param datamap_line 速度线的原始数据
	 */
	private void dealSpeedLine(StringBuilder sb,Map<String, List<Object>> datamap_line){
		
		if (datamap_line!=null) {
		    
			for (Iterator it = datamap_line.keySet().iterator(); it.hasNext();) {
				sb.append("<dataset anchorRadius='0' color='A2B5CD' drawLine='1'>");
				
				String key = (String) it.next();
				List<Object> data = datamap_line.get(key);
				if (data != null && data.size() != 0) {
					for (Object object : data) {
						FlightOcxTask ocxTask = (FlightOcxTask) object;
						sb.append("<set x='"+ocxTask.getStatusMachNum()+"' ");
						sb.append("y='"+ocxTask.getStatusHeight()+"' />");

					}
				}
				
				sb.append("</dataset>");
			}
		}
	}
	
	/**
	 * 处理边界线文件 
	 * @param sb 返回边界线对应的dataset的xml标签数据
	 * @param datamap_area 边界线的原始数据
	 */
	private void dealAreaLine(StringBuilder sb,Map<String, List<Object>> datamap_area){
		if (datamap_area!=null) {
		    
			for (Iterator it = datamap_area.keySet().iterator(); it.hasNext();) {
				sb.append("<dataset anchorRadius='0' color='009900' drawLine='1'>");
				
				String key = (String) it.next();
				List<Object> data = datamap_area.get(key);
				if (data != null && data.size() != 0) {
					for (Object object : data) {
						FlightOcxTask ocxTask = (FlightOcxTask) object;
						sb.append("<set x='"+ocxTask.getStatusMachNum()+"' ");
						sb.append("y='"+ocxTask.getStatusHeight()+"' />");

					}
				}
				
				sb.append("</dataset>");
			}
		}
	}
	
	/**
	 * 处理采样点文件
	 * @param sb 返回采样点对应的dataset的xml标签数据
	 * @param datamap_point 采样点的原始数据
	 */
	private void dealSamplePoint(StringBuilder sb,Map<String, List<Object>> datamap_point){
		
		if (datamap_point!=null) {
			
			for (Iterator it = datamap_point.keySet().iterator(); it.hasNext();) {

				//根据是否完成，是否是新增点，颜色也要有所不同
				String key = (String) it.next();
				List<Object> data = datamap_point.get(key);
				if (data != null && data.size() != 0) {
					for (Object object : data) {
						FlightOcxTask ocxTask = (FlightOcxTask) object;
						
						String height = ocxTask.getStatusHeight();
						String mach = ocxTask.getStatusMachNum();

						if (height == null || mach == null) {
							continue;
						}

						if (-1 != height.indexOf("~") && -1 != mach.indexOf("~")) {   //都有范围值的情况

							String[] heights = height.split("~");
							String[] machs = mach.split("~");
							Double[] heights_d = new Double[2];
							Double[] machs_d = new Double[2];
							if (heights.length != 2 || machs.length != 2) {
								continue;
							}
							for (int i = 0; i < heights.length; i++) {
								double h = Double.valueOf(heights[i]);
								heights_d[i] = h;
							}
							for (int i = 0; i < machs.length; i++) {
								double m = Double.valueOf(machs[i]);
								machs_d[i] = m;
							}
							Arrays.sort(heights_d);
							Arrays.sort(machs_d);

							setDatasetInfoByData(sb,ocxTask,IntevalTypes.DOUBLEINTEVAL,machs_d, heights_d);

						} else if (-1 != height.indexOf("~")) {  //高度有范围值
							String[] heights = height.split("~");
							Double[] heights_d = new Double[2];
							if (heights.length != 2) {
								continue;
							}
							for (int i = 0; i < heights.length; i++) {
								double h = Double.valueOf(heights[i]);
								heights_d[i] = h;
							}
							Arrays.sort(heights_d);

							Double[] machs_d = new Double[] { Double.valueOf(mach) };
							setDatasetInfoByData(sb,ocxTask,IntevalTypes.HINTEVAL,machs_d, heights_d);
							
						} else if (-1 != mach.indexOf("~")) {  //马赫数有范围值
							String[] machs = mach.split("~");
							Double[] machs_d = new Double[2];
							if (machs.length != 2) {
								continue;
							}
							for (int i = 0; i < machs.length; i++) {
								double m = Double.valueOf(machs[i]);
								machs_d[i] = m;
							}
							Arrays.sort(machs_d);

							Double[] heights_d = new Double[] { Double.valueOf(height) };
							setDatasetInfoByData(sb,ocxTask,IntevalTypes.MINTEVAL,machs_d, heights_d);


						} else {   //都无范围值情况
							Double[] machs_d = {Double.valueOf(mach)};
							Double[] heights_d = {Double.valueOf(height)};
							
							setDatasetInfoByData(sb,ocxTask,IntevalTypes.POINT,machs_d, heights_d);

						}

					}
				}
				
				
			}
		}
	}
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	/**
	 * 根据参数生成fusionchart dataset的字符串
	 * @param sb 要生成的字符串builder
	 * @param color 点的颜色
	 * @param anchorsides 点的形状
	 * @param mach 马赫数
	 * @param height 高度
	 * @param isAnchorsides 是否要描绘形状
	 * @param isInteval 是否是区间数据
	 * @param isdrawline 是否要连线
	 */
	private void generateDatasetScript(StringBuilder sb,String color,String anchorsides,Double[] mach,Double height[],boolean isAnchorsides,boolean isInteval,boolean isdrawline){
		
		//有范围值描点
		if(isInteval&&isAnchorsides&&isdrawline){
		sb.append("<dataset anchorRadius='4' drawLine='1' anchorSides='"+anchorsides+"'"); 
		sb.append("anchorBgColor='"+color+"' anchorBorderColor='000000' color='000000'>");
		sb.append("<set x='"+mach[0]+"' ");
		sb.append("y='"+height[0]+"' toolText='马赫数:"+mach[0]+",高度:"+height[0]+"' />");
		sb.append("<set x='"+mach[1]+"' ");
		sb.append("y='"+height[1]+"' toolText='马赫数:"+mach[1]+",高度:"+height[1]+"' />");
		sb.append("</dataset>");
		}else if (isInteval&&!isAnchorsides&&isdrawline) {
			sb.append("<dataset anchorRadius='4' drawLine='1'"); 
			sb.append("anchorBgColor='"+color+"' anchorBorderColor='000000' color='000000'>");
			sb.append("<set x='"+mach[0]+"' ");
			sb.append("y='"+height[0]+"' toolText='马赫数:"+mach[0]+",高度:"+height[0]+"' />");
			sb.append("<set x='"+mach[1]+"' ");
			sb.append("y='"+height[1]+"' toolText='马赫数:"+mach[1]+",高度:"+height[1]+"' />");
			sb.append("</dataset>");
			
			//以下是无范围值描点
		}else if (!isInteval&&isAnchorsides&&!isdrawline) {
			sb.append("<dataset anchorRadius='4' anchorSides='"+anchorsides+"'"); 
			sb.append("anchorBgColor='"+color+"' anchorBorderColor='000000' color='000000'>");
			sb.append("<set x='"+mach[0]+"' ");
			sb.append("y='"+height[0]+"' toolText='马赫数:"+mach[0]+",高度:"+height[0]+"' />");
			sb.append("</dataset>");
		}else if (!isInteval&&!isAnchorsides&&!isdrawline){
			sb.append("<dataset anchorRadius='4' drawLine='1'"); 
			sb.append("anchorBgColor='"+color+"' anchorBorderColor='000000' color='000000'>");
			sb.append("<set x='"+mach[0]+"' ");
			sb.append("y='"+height[0]+"' toolText='马赫数:"+mach[0]+",高度:"+height[0]+"' />");
			sb.append("</dataset>");
		}
	
	}
	
	
	private void setDatasetInfoByData(StringBuilder sb,FlightOcxTask ocxTask,IntevalTypes type,Double[] mach,Double[] height){
		
		String isfinished = ocxTask.getIsfinished().charAt(0)+"";
		String isadded = ocxTask.getIsadded().charAt(0)+"";
		
		if (type == IntevalTypes.DOUBLEINTEVAL) { //马赫数，高度是范围值
			//判断是否是新增数据，且要判断完成情况
			if ("0".equals(isadded)) { //未编制
				generateDatasetScript(sb, COLOR_NOT_EDIT, ANCHORSIDES_ORIGIN, mach, height, false, true, true);
			} else if ("1".equals(isadded)) { //新增
				if ("0".equals(isfinished)) { //未完成
					generateDatasetScript(sb, COLOR_NOT_COMPLETE, ANCHORSIDES_NEW, mach, height, true, true, true);
				} else if ("1".equals(isfinished)) { //已完成
					generateDatasetScript(sb, COLOR_COMPLETE, ANCHORSIDES_NEW, mach, height, true, true, true);
				} else if ("2".equals(isfinished)) { //部分完成
					generateDatasetScript(sb, COLOR_HALF_COMPLETE, ANCHORSIDES_NEW, mach, height, true, true, true);
				}
			} else if ("2".equals(isadded)) { //已编制
				if ("0".equals(isfinished)) { //未完成
					generateDatasetScript(sb, COLOR_NOT_COMPLETE, ANCHORSIDES_ORIGIN, mach, height, false, true, true);
				} else if ("1".equals(isfinished)) { //已完成
					generateDatasetScript(sb, COLOR_COMPLETE, ANCHORSIDES_ORIGIN, mach, height, false, true, true);
				} else if ("2".equals(isfinished)) { //部分完成
					generateDatasetScript(sb, COLOR_HALF_COMPLETE, ANCHORSIDES_ORIGIN, mach, height, false, true, true);
				}
			}

		} else if (type == IntevalTypes.HINTEVAL) { //高度是范围值，马赫数不是
			Double[] mach_origin = {mach[0],mach[0]};
			if ("0".equals(isadded)) { //未编制
				generateDatasetScript(sb, COLOR_NOT_EDIT, ANCHORSIDES_ORIGIN, mach_origin, height, false, true, true);

			} else if ("1".equals(isadded)) { //新增
				if ("0".equals(isfinished)) { //未完成
					generateDatasetScript(sb, COLOR_NOT_COMPLETE, ANCHORSIDES_NEW, mach_origin, height, true, true, true);

				} else if ("1".equals(isfinished)) { //已完成
					generateDatasetScript(sb, COLOR_COMPLETE, ANCHORSIDES_NEW, mach_origin, height, true, true, true);

				} else if ("2".equals(isfinished)) { //部分完成
					generateDatasetScript(sb, COLOR_HALF_COMPLETE, ANCHORSIDES_NEW, mach_origin, height, true, true, true);

				}
			} else if ("2".equals(isadded)) { //已编制
				if ("0".equals(isfinished)) { //未完成
					generateDatasetScript(sb, COLOR_NOT_COMPLETE, ANCHORSIDES_ORIGIN, mach_origin, height, false, true, true);

				} else if ("1".equals(isfinished)) { //已完成
					generateDatasetScript(sb, COLOR_COMPLETE, ANCHORSIDES_ORIGIN, mach_origin, height, false, true, true);

				} else if ("2".equals(isfinished)) { //部分完成
					generateDatasetScript(sb, COLOR_HALF_COMPLETE, ANCHORSIDES_ORIGIN, mach_origin, height, false, true, true);

				}

			}
		} else if (type == IntevalTypes.MINTEVAL) { //马赫数是范围值，高度不是
			Double[] height_origin = {height[0],height[0]};
			if ("0".equals(isadded)) { //未编制
				generateDatasetScript(sb, COLOR_NOT_EDIT, ANCHORSIDES_ORIGIN, mach, height_origin, false, true, true);

			} else if ("1".equals(isadded)) { //新增
				if ("0".equals(isfinished)) { //未完成
					generateDatasetScript(sb, COLOR_NOT_COMPLETE, ANCHORSIDES_NEW, mach, height_origin, true, true, true);

				} else if ("1".equals(isfinished)) { //已完成
					generateDatasetScript(sb, COLOR_COMPLETE, ANCHORSIDES_NEW, mach, height_origin, true, true, true);
				} else if ("2".equals(isfinished)) { //部分完成
					generateDatasetScript(sb, COLOR_HALF_COMPLETE, ANCHORSIDES_NEW, mach, height_origin, true, true, true);
				}
			} else if ("2".equals(isadded)) { //已编制
				if ("0".equals(isfinished)) { //未完成
					generateDatasetScript(sb, COLOR_NOT_COMPLETE, ANCHORSIDES_ORIGIN, mach, height_origin, false, true, true);

				} else if ("1".equals(isfinished)) { //已完成
					generateDatasetScript(sb, COLOR_COMPLETE, ANCHORSIDES_ORIGIN, mach, height_origin, false, true, true);

				} else if ("2".equals(isfinished)) { //部分完成
					generateDatasetScript(sb, COLOR_HALF_COMPLETE, ANCHORSIDES_ORIGIN, mach, height_origin, false, true, true);

				}
			}
		}else if (type == IntevalTypes.POINT) {  //不是范围值的情况
			//判断是否是新增数据，且要判断完成情况 (无区间值的情况)
			if ("0".equals(isadded)) { //未编制
				generateDatasetScript(sb, COLOR_NOT_EDIT, ANCHORSIDES_ORIGIN, mach, height, false, false, false);

			} else if ("1".equals(isadded)) { //新增
				if ("0".equals(isfinished)) { //未完成
					generateDatasetScript(sb, COLOR_NOT_COMPLETE, ANCHORSIDES_NEW, mach, height, true, false, false);
				} else if ("1".equals(isfinished)) { //已完成
					generateDatasetScript(sb, COLOR_COMPLETE, ANCHORSIDES_NEW, mach, height, true, false, false);

				} else if ("2".equals(isfinished)) { //部分完成
					generateDatasetScript(sb, COLOR_HALF_COMPLETE, ANCHORSIDES_NEW, mach, height, true, false, false);

				}
			} else if ("2".equals(isadded)) { //已编制
				if ("0".equals(isfinished)) { //未完成
					generateDatasetScript(sb, COLOR_NOT_COMPLETE, ANCHORSIDES_ORIGIN, mach, height, false, false, false);

				} else if ("1".equals(isfinished)) { //已完成
					generateDatasetScript(sb, COLOR_COMPLETE, ANCHORSIDES_ORIGIN, mach, height, false, false, false);

				} else if ("2".equals(isfinished)) { //部分完成
					generateDatasetScript(sb, COLOR_HALF_COMPLETE, ANCHORSIDES_ORIGIN, mach, height, false, false, false);

				}

			}
		}

	
		
	}
}
