package com.neptune.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * @author CSM
 *
 */
public class ExcelRead {
	
	/**
	 * 直接根据InputStream生成Excel工作薄对象
	 * @param in Excel格式的文件输入流
	 * @return
	 */
	public Workbook getExcelWorkBookByStream(InputStream in){
		Workbook book = null;
		
		try {
			book = WorkbookFactory.create(in);
			in.close();
			return book;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	
	}
	
	/**
	 * 根据xls文件路径获得Excel工作薄对象
	 * @param filePath Excel文件存放路径
	 * @return
	 */
	public Workbook getExcelWorkBook(String filePath){
		InputStream ins = null;
		Workbook book = null;
		
		try {
			ins = new FileInputStream(new File(filePath));
			book = WorkbookFactory.create(ins);
			ins.close();
			return book;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ins != null){
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/** 
     * 根据输入流读取多sheet页的Excel文件的数
     * @param filePath   excel 文件的 
     * @param headTitle    
     * @return 
     */  
    public Map<String,List<Map<String,Object>>> readEXCELMap(InputStream in,String[] headTitle){  
        //获取workbook对象  
        Workbook workbook=getExcelWorkBookByStream(in);  
        //获取sheet页数  
        int sheetNum=workbook.getNumberOfSheets();  
        //存储excel相关的数据 
        Map<String,List<Map<String,Object>>> excelData=new HashMap<String,List<Map<String,Object>>>();  
        //遍历相关sheet页面获取相关的数据
        if(sheetNum>0){  
            for (int index = 0; index < sheetNum; index++) {  
                //创建sheet  
                Sheet sheet=workbook.getSheetAt(index);  
                //获取sheet的名称
                String sheetName=workbook.getSheetName(index);  
//                //获取相关的数据
//                List<Map<String,Object>> sheetData=getExcelMapData(sheet, headTitle);  
                //获取相关的数据
                List<Map<String,Object>> sheetData=getExcelMapData(sheet); 
                excelData.put(sheetName, sheetData);  
            }  
        }  
        return excelData;  
    } 
	
	/** 
     * 读取多sheet页的Excel文件的数
     * @param filePath   excel 文件的 
     * @param headTitle    
     * @return 
     */  
    public Map<String,List<Map<String,Object>>> readEXCELMap(String filePath,String[] headTitle){  
        //获取workbook对象  
        Workbook workbook=getExcelWorkBook(filePath);  
        //获取sheet页数  
        int sheetNum=workbook.getNumberOfSheets();  
        //存储excel相关的数据 
        Map<String,List<Map<String,Object>>> excelData=new HashMap<String,List<Map<String,Object>>>();  
        //遍历相关sheet页面获取相关的数据
        if(sheetNum>0){  
            for (int index = 0; index < sheetNum; index++) {  
                //创建sheet  
                Sheet sheet=workbook.getSheetAt(index);  
                //获取sheet的名称
                String sheetName=workbook.getSheetName(index);  
//                //获取相关的数据
//                List<Map<String,Object>> sheetData=getExcelMapData(sheet, headTitle);  
                //获取相关的数据
                List<Map<String,Object>> sheetData=getExcelMapData(sheet); 
                excelData.put(sheetName, sheetData);  
            }  
        }  
        return excelData;  
    }  
	
	/** 
     * 获取sheet表中的数
     * @param sheet 
     * @return headTitle 列标做为key 
     */  
    private List<Map<String,Object>> getExcelMapData(Sheet sheet,String[] headTitle){  
        //获取开始和结束行  
        int startRow=sheet.getFirstRowNum();  
        int lastRow=sheet.getLastRowNum();  
        List<Map<String,Object>> allRowMapData=new ArrayList<Map<String,Object>>();  
        if(startRow!=lastRow){  
            //忽略第一行数 
            startRow=startRow+1;  
            //获取行数 
            for(int indexRow=startRow;indexRow<=lastRow;indexRow++){  
                Row row=sheet.getRow(indexRow);  
                if(row==null){  
                    continue;  
                }  
                int firstCellNum=row.getFirstCellNum();  
                int lastCellNum=row.getLastCellNum();  
                Map<String,Object> RowDataMap=new HashMap<String,Object>();  
                //遍历相关的列数据  
                for (int indexCol = firstCellNum; indexCol <lastCellNum; indexCol++) {  
                    Cell cell=row.getCell(indexCol);  
                    String cellKey=headTitle[indexCol-firstCellNum];  
                    if(cell==null){  
                        continue;  
                    }  
                    //获取列的数据的信
                    Object cellValue = getCellValue(cell);  
                    RowDataMap.put(cellKey, cellValue);  
                }  
                allRowMapData.add(RowDataMap);  
            }  
        }  
          
        return allRowMapData;  
    }  
    
    /** 
     * 获取sheet表中的数据
     * @param sheet 
     * @return headTitle 列标做为key 
     */  
    private List<Map<String,Object>> getExcelMapData(Sheet sheet){ 
    	String headtitle[] = null;
        //获取开始和结束行  
        int startRow=sheet.getFirstRowNum();  
        int lastRow=sheet.getLastRowNum();  
        List<Map<String,Object>> allRowMapData=new ArrayList<Map<String,Object>>();  
        if(startRow!=lastRow){  
            //获取行数 
            for(int indexRow=startRow;indexRow<=lastRow;indexRow++){  
                Row row=sheet.getRow(indexRow);  
                if(row==null){  
                    continue;  
                }  
                int firstCellNum=row.getFirstCellNum();  
                int lastCellNum=row.getLastCellNum(); 
                Map<String,Object> RowDataMap=new HashMap<String,Object>();  
                //遍历相关的列数据  
                for (int indexCol = firstCellNum; indexCol <lastCellNum; indexCol++) {  
                    Cell cell=row.getCell(indexCol);  
                    if(indexRow==0){  //如果是导入的Excel的首行数据，默认为headtitle
                    	if(indexCol==0){
                    	headtitle = new String[lastCellNum];  //初始化Excel的headtitle
                    	}if(cell==null){  
                            continue;  
                        }  
                    	headtitle[indexCol] = getCellValue(cell).toString();
                    	continue;
                    }else{
                    String cellKey=headtitle[indexCol-firstCellNum];  
                    if(cell==null){  
                        continue;  
                    }  
                    //获取列的数据的信
                    Object cellValue = getCellValue(cell);  
                    RowDataMap.put(cellKey, cellValue);  
                    }  
                }
                if(indexRow>0){  //忽略第一行的title数据
                allRowMapData.add(RowDataMap);  
                }
            }  
        }  
          
        return allRowMapData;  
    }
    
    /** 
     * 获取列的数据信息  
     * @param cell 
     * @return 
     */  
    private Object getCellValue(Cell cell) {  
        Object cellValue=null;  
        switch (cell.getCellType()) {  
                case Cell.CELL_TYPE_BLANK:  
                    cellValue = "";  
                    break;  
                case Cell.CELL_TYPE_ERROR:  
                    cellValue = Byte.toString(cell.getErrorCellValue());  
                    break;  
                case Cell.CELL_TYPE_STRING:  
                    cellValue= cell.getRichStringCellValue().getString();  
                    break;  
                /** 在excel中日期也可能是数字,在此要进行判定*/   
                case Cell.CELL_TYPE_NUMERIC:  
                    double number=cell.getNumericCellValue();  
                    if (DateUtil.isCellDateFormatted(cell)) {  
//                         cellValue =getTime(number);   
                         cellValue = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getJavaDate(number));
                         
                    } else {  
//                        cellValue= Integer.toString((int) cell.getNumericCellValue());  
                    	cellValue= cell.getNumericCellValue();  
                    }  
                    break;  
                case Cell.CELL_TYPE_BOOLEAN:  
                    cellValue= Boolean.toString(cell.getBooleanCellValue());  
                    break;  
                case Cell.CELL_TYPE_FORMULA:  
                    cellValue= cell.getCellFormula();  
                    break;  
                default:  
                    cellValue = "";  
        }  
        return cellValue;  
    }  
    
    /**  
     * [正确地处理整数后自动加零的情况]</li>  
     * @param sNum  
     * @return  
     */   
    private static String getTime(double daynum)   
    {   
        double totalSeconds=daynum*86400.0D;  
        //总的分钟数  
        int seconds =(int)totalSeconds/60;  
        //实际小时数  
        int hours =seconds/60;  
        int minutes = seconds-hours*60;  
        //剩余的实际分钟数  
        StringBuffer sb=new StringBuffer();  
        if(String.valueOf(hours).length()==1){  
         sb.append("0"+hours);  
        }else{  
         sb.append(hours);  
        }  
        sb.append(":");  
        if(String.valueOf(minutes).length()==1){  
         sb.append("0"+minutes);  
        }else{  
         sb.append(minutes);  
        }  
        return sb.toString();  
    } 
    
    /** 
     *  
     * 以Bean的方式存储bean对象 
     * 读取Excel文件的数据
     * @param filePath   excel 文件的路 
     * @param headTitle    
     * @param clazz 
     * @return 
     */  
    public Map<String,List<Object>> readEXCELBean(String filePath,String[] headTitle,Class clazz){  
        //获取workbook对象  
        Workbook workbook=getExcelWorkBook(filePath);  
        //获取sheet页数  
        int sheetNum=workbook.getNumberOfSheets();  
        //存储excel相关的数据  
        Map<String,List<Object>> excelData=new HashMap<String,List<Object>>();  
        //遍历相关sheet页面获取相关的数据  
        if(sheetNum>0){  
            for (int index = 0; index < sheetNum; index++) {  
                //创建sheet  
                Sheet sheet=workbook.getSheetAt(index);  
                //获取sheet的名称 
                String sheetName=workbook.getSheetName(index);  
                //获取相关的数据  
                List<Object> sheetData=getExcelBeanData(sheet, headTitle,clazz);  
                excelData.put(sheetName, sheetData);  
            }  
        }  
        return excelData;  
    }  
    
    /** 
     *  
     * 从流中读取并以Bean的方式存储bean对象 
     * 读取Excel文件的数据
     * @param InputStream in  excel 文件的二进制流
     * @param headTitle    
     * @param clazz 
     * @return 
     */  
    public Map<String,List<Object>> readEXCELBeanFromInputStream(InputStream in,String[] headTitle,Class clazz){  
        //获取workbook对象  
        Workbook workbook=getExcelWorkBookByStream(in);  
        //获取sheet页数  
        int sheetNum=workbook.getNumberOfSheets();  
        //存储excel相关的数据  
        Map<String,List<Object>> excelData=new HashMap<String,List<Object>>();  
        //遍历相关sheet页面获取相关的数据  
        if(sheetNum>0){  
            for (int index = 0; index < sheetNum; index++) {  
                //创建sheet  
                Sheet sheet=workbook.getSheetAt(index);  
                //获取sheet的名称 
                String sheetName=workbook.getSheetName(index);  
                //获取相关的数据  
                List<Object> sheetData=getExcelBeanData(sheet, headTitle,clazz);  
                excelData.put(sheetName, sheetData);  
            }  
        }  
        return excelData;  
    }  
    
    /** 
     * 获取sheet表中的数据? 
     * @param sheet 
     * @param sheetheadTitle bean每列对应的属性数据? 
     * @param clazz   bean对应的类 
     * @throws InstantiationException  
     */  
    private List<Object> getExcelBeanData(Sheet sheet,String[] headTitle,Class clazz) {  
        //获取起始行和结束行  
        int startRow=sheet.getFirstRowNum();  
        int lastRow=sheet.getLastRowNum();  
        List<Object> allRowMapData=new ArrayList<Object>();  
        if(startRow!=lastRow){  
            //忽略第一行数据?  
            startRow=startRow+1;  
            //获取行数据?  
            for(int indexRow=startRow;indexRow<=lastRow;indexRow++){  
                Row row=sheet.getRow(indexRow);  
                if(row==null){  
                    continue;  
                }  
                int firstCellNum=row.getFirstCellNum();  
                int lastCellNum=row.getLastCellNum();  
                Object bean=null;  
                try {  
                    bean = clazz.newInstance();  
                    //遍历相关的列数据  
                    for (int indexCol = firstCellNum; indexCol <lastCellNum; indexCol++) {  
                        Cell cell=row.getCell(indexCol);  
                        String cellKey=headTitle[indexCol-firstCellNum];  
                        if(cell==null){  
                            continue;  
                        }  
                        //获取列的数据的信息  
                        Object cellValue = getCellValue(cell);  
                        try {  
                            BeanUtils.setProperty(bean, cellKey, cellValue);  
                        } catch (InvocationTargetException e) {  
                            e.printStackTrace();  
                        }  
                    }  
                    allRowMapData.add(bean);  
                } catch (InstantiationException e1) {  
                    e1.printStackTrace();  
                } catch (IllegalAccessException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
          
        return allRowMapData;  
    } 
    
    public static void readExcel(){  
        //以map方式遍历  
        ExcelRead  excelreader=new ExcelRead();  
          
//         String[] trianDeatailheadTitle=new String[]{"科目","高度","速度","马赫数"};  
//        Map<String,List<Map<String,Object>>> list0=excelreader.readEXCELMap("E:\\MyWorkspace\\Excel2Imp\\WebRoot\\exceltemp\\test.xlsx", trianDeatailheadTitle);  
        
        String[] trianDeatailheadTitle=new String[]{};  
        Map<String,List<Map<String,Object>>> list0=excelreader.readEXCELMap("D:\\WorkDoc\\项目文档\\生产管控系统\\2011指令性计划导入.xls", trianDeatailheadTitle);  
//        System.out.println(((List)list0.get("70")).size());  //数据记录数量
        
        JSONArray jsonArray0=JSONArray.fromObject(list0);  
        System.out.println(jsonArray0.toString());  
    }
    
    public static void main(String[] args) {
		readExcel();
	}
}
