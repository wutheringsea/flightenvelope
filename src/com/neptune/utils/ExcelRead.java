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
	 * ֱ�Ӹ���InputStream����Excel����������
	 * @param in Excel��ʽ���ļ�������
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
	 * ����xls�ļ�·�����Excel����������
	 * @param filePath Excel�ļ����·��
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
     * ������������ȡ��sheetҳ��Excel�ļ�����
     * @param filePath   excel �ļ��� 
     * @param headTitle    
     * @return 
     */  
    public Map<String,List<Map<String,Object>>> readEXCELMap(InputStream in,String[] headTitle){  
        //��ȡworkbook����  
        Workbook workbook=getExcelWorkBookByStream(in);  
        //��ȡsheetҳ��  
        int sheetNum=workbook.getNumberOfSheets();  
        //�洢excel��ص����� 
        Map<String,List<Map<String,Object>>> excelData=new HashMap<String,List<Map<String,Object>>>();  
        //�������sheetҳ���ȡ��ص�����
        if(sheetNum>0){  
            for (int index = 0; index < sheetNum; index++) {  
                //����sheet  
                Sheet sheet=workbook.getSheetAt(index);  
                //��ȡsheet������
                String sheetName=workbook.getSheetName(index);  
//                //��ȡ��ص�����
//                List<Map<String,Object>> sheetData=getExcelMapData(sheet, headTitle);  
                //��ȡ��ص�����
                List<Map<String,Object>> sheetData=getExcelMapData(sheet); 
                excelData.put(sheetName, sheetData);  
            }  
        }  
        return excelData;  
    } 
	
	/** 
     * ��ȡ��sheetҳ��Excel�ļ�����
     * @param filePath   excel �ļ��� 
     * @param headTitle    
     * @return 
     */  
    public Map<String,List<Map<String,Object>>> readEXCELMap(String filePath,String[] headTitle){  
        //��ȡworkbook����  
        Workbook workbook=getExcelWorkBook(filePath);  
        //��ȡsheetҳ��  
        int sheetNum=workbook.getNumberOfSheets();  
        //�洢excel��ص����� 
        Map<String,List<Map<String,Object>>> excelData=new HashMap<String,List<Map<String,Object>>>();  
        //�������sheetҳ���ȡ��ص�����
        if(sheetNum>0){  
            for (int index = 0; index < sheetNum; index++) {  
                //����sheet  
                Sheet sheet=workbook.getSheetAt(index);  
                //��ȡsheet������
                String sheetName=workbook.getSheetName(index);  
//                //��ȡ��ص�����
//                List<Map<String,Object>> sheetData=getExcelMapData(sheet, headTitle);  
                //��ȡ��ص�����
                List<Map<String,Object>> sheetData=getExcelMapData(sheet); 
                excelData.put(sheetName, sheetData);  
            }  
        }  
        return excelData;  
    }  
	
	/** 
     * ��ȡsheet���е���
     * @param sheet 
     * @return headTitle �б���Ϊkey 
     */  
    private List<Map<String,Object>> getExcelMapData(Sheet sheet,String[] headTitle){  
        //��ȡ��ʼ�ͽ�����  
        int startRow=sheet.getFirstRowNum();  
        int lastRow=sheet.getLastRowNum();  
        List<Map<String,Object>> allRowMapData=new ArrayList<Map<String,Object>>();  
        if(startRow!=lastRow){  
            //���Ե�һ���� 
            startRow=startRow+1;  
            //��ȡ���� 
            for(int indexRow=startRow;indexRow<=lastRow;indexRow++){  
                Row row=sheet.getRow(indexRow);  
                if(row==null){  
                    continue;  
                }  
                int firstCellNum=row.getFirstCellNum();  
                int lastCellNum=row.getLastCellNum();  
                Map<String,Object> RowDataMap=new HashMap<String,Object>();  
                //������ص�������  
                for (int indexCol = firstCellNum; indexCol <lastCellNum; indexCol++) {  
                    Cell cell=row.getCell(indexCol);  
                    String cellKey=headTitle[indexCol-firstCellNum];  
                    if(cell==null){  
                        continue;  
                    }  
                    //��ȡ�е����ݵ���
                    Object cellValue = getCellValue(cell);  
                    RowDataMap.put(cellKey, cellValue);  
                }  
                allRowMapData.add(RowDataMap);  
            }  
        }  
          
        return allRowMapData;  
    }  
    
    /** 
     * ��ȡsheet���е�����
     * @param sheet 
     * @return headTitle �б���Ϊkey 
     */  
    private List<Map<String,Object>> getExcelMapData(Sheet sheet){ 
    	String headtitle[] = null;
        //��ȡ��ʼ�ͽ�����  
        int startRow=sheet.getFirstRowNum();  
        int lastRow=sheet.getLastRowNum();  
        List<Map<String,Object>> allRowMapData=new ArrayList<Map<String,Object>>();  
        if(startRow!=lastRow){  
            //��ȡ���� 
            for(int indexRow=startRow;indexRow<=lastRow;indexRow++){  
                Row row=sheet.getRow(indexRow);  
                if(row==null){  
                    continue;  
                }  
                int firstCellNum=row.getFirstCellNum();  
                int lastCellNum=row.getLastCellNum(); 
                Map<String,Object> RowDataMap=new HashMap<String,Object>();  
                //������ص�������  
                for (int indexCol = firstCellNum; indexCol <lastCellNum; indexCol++) {  
                    Cell cell=row.getCell(indexCol);  
                    if(indexRow==0){  //����ǵ����Excel���������ݣ�Ĭ��Ϊheadtitle
                    	if(indexCol==0){
                    	headtitle = new String[lastCellNum];  //��ʼ��Excel��headtitle
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
                    //��ȡ�е����ݵ���
                    Object cellValue = getCellValue(cell);  
                    RowDataMap.put(cellKey, cellValue);  
                    }  
                }
                if(indexRow>0){  //���Ե�һ�е�title����
                allRowMapData.add(RowDataMap);  
                }
            }  
        }  
          
        return allRowMapData;  
    }
    
    /** 
     * ��ȡ�е�������Ϣ  
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
                /** ��excel������Ҳ����������,�ڴ�Ҫ�����ж�*/   
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
     * [��ȷ�ش����������Զ���������]</li>  
     * @param sNum  
     * @return  
     */   
    private static String getTime(double daynum)   
    {   
        double totalSeconds=daynum*86400.0D;  
        //�ܵķ�����  
        int seconds =(int)totalSeconds/60;  
        //ʵ��Сʱ��  
        int hours =seconds/60;  
        int minutes = seconds-hours*60;  
        //ʣ���ʵ�ʷ�����  
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
     * ��Bean�ķ�ʽ�洢bean���� 
     * ��ȡExcel�ļ�������
     * @param filePath   excel �ļ���· 
     * @param headTitle    
     * @param clazz 
     * @return 
     */  
    public Map<String,List<Object>> readEXCELBean(String filePath,String[] headTitle,Class clazz){  
        //��ȡworkbook����  
        Workbook workbook=getExcelWorkBook(filePath);  
        //��ȡsheetҳ��  
        int sheetNum=workbook.getNumberOfSheets();  
        //�洢excel��ص�����  
        Map<String,List<Object>> excelData=new HashMap<String,List<Object>>();  
        //�������sheetҳ���ȡ��ص�����  
        if(sheetNum>0){  
            for (int index = 0; index < sheetNum; index++) {  
                //����sheet  
                Sheet sheet=workbook.getSheetAt(index);  
                //��ȡsheet������ 
                String sheetName=workbook.getSheetName(index);  
                //��ȡ��ص�����  
                List<Object> sheetData=getExcelBeanData(sheet, headTitle,clazz);  
                excelData.put(sheetName, sheetData);  
            }  
        }  
        return excelData;  
    }  
    
    /** 
     *  
     * �����ж�ȡ����Bean�ķ�ʽ�洢bean���� 
     * ��ȡExcel�ļ�������
     * @param InputStream in  excel �ļ��Ķ�������
     * @param headTitle    
     * @param clazz 
     * @return 
     */  
    public Map<String,List<Object>> readEXCELBeanFromInputStream(InputStream in,String[] headTitle,Class clazz){  
        //��ȡworkbook����  
        Workbook workbook=getExcelWorkBookByStream(in);  
        //��ȡsheetҳ��  
        int sheetNum=workbook.getNumberOfSheets();  
        //�洢excel��ص�����  
        Map<String,List<Object>> excelData=new HashMap<String,List<Object>>();  
        //�������sheetҳ���ȡ��ص�����  
        if(sheetNum>0){  
            for (int index = 0; index < sheetNum; index++) {  
                //����sheet  
                Sheet sheet=workbook.getSheetAt(index);  
                //��ȡsheet������ 
                String sheetName=workbook.getSheetName(index);  
                //��ȡ��ص�����  
                List<Object> sheetData=getExcelBeanData(sheet, headTitle,clazz);  
                excelData.put(sheetName, sheetData);  
            }  
        }  
        return excelData;  
    }  
    
    /** 
     * ��ȡsheet���е�����? 
     * @param sheet 
     * @param sheetheadTitle beanÿ�ж�Ӧ����������? 
     * @param clazz   bean��Ӧ���� 
     * @throws InstantiationException  
     */  
    private List<Object> getExcelBeanData(Sheet sheet,String[] headTitle,Class clazz) {  
        //��ȡ��ʼ�кͽ�����  
        int startRow=sheet.getFirstRowNum();  
        int lastRow=sheet.getLastRowNum();  
        List<Object> allRowMapData=new ArrayList<Object>();  
        if(startRow!=lastRow){  
            //���Ե�һ������?  
            startRow=startRow+1;  
            //��ȡ������?  
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
                    //������ص�������  
                    for (int indexCol = firstCellNum; indexCol <lastCellNum; indexCol++) {  
                        Cell cell=row.getCell(indexCol);  
                        String cellKey=headTitle[indexCol-firstCellNum];  
                        if(cell==null){  
                            continue;  
                        }  
                        //��ȡ�е����ݵ���Ϣ  
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
        //��map��ʽ����  
        ExcelRead  excelreader=new ExcelRead();  
          
//         String[] trianDeatailheadTitle=new String[]{"��Ŀ","�߶�","�ٶ�","�����"};  
//        Map<String,List<Map<String,Object>>> list0=excelreader.readEXCELMap("E:\\MyWorkspace\\Excel2Imp\\WebRoot\\exceltemp\\test.xlsx", trianDeatailheadTitle);  
        
        String[] trianDeatailheadTitle=new String[]{};  
        Map<String,List<Map<String,Object>>> list0=excelreader.readEXCELMap("D:\\WorkDoc\\��Ŀ�ĵ�\\�����ܿ�ϵͳ\\2011ָ���Լƻ�����.xls", trianDeatailheadTitle);  
//        System.out.println(((List)list0.get("70")).size());  //���ݼ�¼����
        
        JSONArray jsonArray0=JSONArray.fromObject(list0);  
        System.out.println(jsonArray0.toString());  
    }
    
    public static void main(String[] args) {
		readExcel();
	}
}
