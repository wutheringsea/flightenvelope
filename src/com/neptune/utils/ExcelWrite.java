package com.neptune.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.neptune.model.ExcelVO;
import com.neptune.model.SheetVO;

public class ExcelWrite {

	/** 
     * 创建TableView类型的Excel文件 
     * @param excelVo excel模型 
     * @throws IOException 
     */  
    public void createTableViewerExcelFile(ExcelVO  excelVo) throws IOException{  
        //创建一个EXCEL  
          
        Workbook wb =null;  
        //支持2007  
        if("xlsx".equals(excelVo.getPrefix())){  
            wb=new XSSFWorkbook();  
        //支持97 ~2003  
        }else{  
            wb=new HSSFWorkbook();  
        }  
        List<SheetVO> sheetList=excelVo.getSheets();  
        if(CollectionUtils.isNotEmpty(sheetList)){  
            for (int sheet = 0; sheet < sheetList.size(); sheet++) {  
                createExcelSheet(wb, sheetList, sheet);  
            }  
        }  
          
        //仅作测试在本地生成文件  
        FileOutputStream fileOut = new FileOutputStream(excelVo.getPath());  
        wb.write(fileOut);  
        fileOut.flush();  
        fileOut.close();  
    }  
    
    /** 
     * 创建TableView类型的Excel文件 
     * @param excelVo excel模型 
     * @throws IOException 
     */  
    public InputStream createTableViewerExcelStream(ExcelVO  excelVo) throws IOException{  
        //创建一个EXCEL  
        Workbook wb =null;  
        //支持2007  
        if("xlsx".equals(excelVo.getPrefix())){  
            wb=new XSSFWorkbook();  
        //支持97 ~2003  
        }else{  
            wb=new HSSFWorkbook();  
        }  
        List<SheetVO> sheetList=excelVo.getSheets();  
        if(CollectionUtils.isNotEmpty(sheetList)){  
            for (int sheet = 0; sheet < sheetList.size(); sheet++) {  
                createExcelSheet(wb, sheetList, sheet);  
            }  
        }  
        //存储流信息  
         ByteArrayOutputStream  out = new ByteArrayOutputStream();  
        wb.write(out);  
          
          
        //临时存储流信息  
        ByteArrayInputStream in  = new ByteArrayInputStream(out.toByteArray());  
        out.close();  
        return in;  
    }  
    
    /** 
     * 创建Excel的Sheet 
     * @param wb  Excel的对象 
     * @param sheetList  
     * @param sheetNum 
     */  
    private void createExcelSheet(Workbook wb, List<SheetVO> sheetList, int sheetNum) {  
        SheetVO sheetVo=sheetList.get(sheetNum);  
        //获取各种样式  
        //获取数据格式化对象  
        DataFormat dataformat = wb.createDataFormat();  
        //获取Sheet的名称  
        String sheetName=sheetVo.getSheetName();  
        //创建Sheet  
        Sheet sheet=wb.createSheet(sheetName);  
           // create 2 cell styles  
        CellStyle cs = wb.createCellStyle();  
        CellStyle cs2 = wb.createCellStyle();  
        DataFormat df = wb.createDataFormat();  
  
        // create 2 fonts objects  
        Font f = wb.createFont();  
        Font f2 = wb.createFont();  
  
        // Set font 1 to 12 point type, blue and bold  
        f.setFontHeightInPoints((short) 12);  
        f.setColor( IndexedColors.RED.getIndex() );  
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);  
  
        // Set font 2 to 10 point type, red and bold  
        f2.setFontHeightInPoints((short) 10);  
        f2.setColor( IndexedColors.RED.getIndex() );  
        f2.setBoldweight(Font.BOLDWEIGHT_BOLD);  
  
        // Set cell style and formatting  
        cs.setFont(f);  
        cs.setDataFormat(df.getFormat("#,##0.0"));  
  
        // Set the other cell style and formatting  
        cs2.setBorderBottom(cs2.BORDER_THIN);  
        cs2.setDataFormat(df.getFormat("text"));  
        cs2.setFont(f2);  
          
          
        //获取开始写的行号  
        int rowNum=sheetVo.getRowNum();  
        //创建标题  
        Row headerRow = sheet.createRow(0);  
        headerRow.setHeightInPoints(40.0F);  
        Cell titleCell = headerRow.createCell(0);  
        titleCell.setCellValue(sheetVo.getTitle());  
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$T$1"));     
          
        CreationHelper createHelper = wb.getCreationHelper();  
        String[] headerTitles=sheetVo.getHeaderTitle();  
        if(!ArrayUtils.isEmpty(headerTitles)){  
            //创建表头  
            Row row = sheet.createRow((short)rowNum);  
            for (int index=0; index < headerTitles.length; index++) {  
                //创建列信息  
                String headerTitle=headerTitles[index];  
                Cell cell = row.createCell(index);  
                cell.setCellValue(createHelper.createRichTextString(headerTitle));  
                //设置列宽，行高  
                sheet.setColumnWidth((short)index, 5000);  
            }  
            //行记录添加  
            rowNum++;  
        }  
        //编写sheet的内容  
        List<Map<String,Object>> contentMap=sheetVo.getSheetContentMap();  
        if(CollectionUtils.isNotEmpty(contentMap)){  
            for (int index = 0; index < contentMap.size(); index++) {  
                Map<String,Object> rowMap=contentMap.get(index);  
                Row row = sheet.createRow((short)rowNum);  
                createCell(wb, dataformat, rowMap, row,sheetVo);  
                rowNum++;  
            }  
        }  
    }  
    
    /** 
     * 创建Excel的Cell 
     * @param wb 
     * @param dataformat 
     * @param rowMap 
     * @param row 
     */  
    private void createCell(Workbook wb, DataFormat dataformat,  
            Map<String, Object> rowMap, Row row,SheetVO sheetVo) {  
        String[] headerTitles=sheetVo.getTitles();  
        if(MapUtils.isNotEmpty(rowMap)){  
            CreationHelper createHelper = wb.getCreationHelper();  
            for (int cellNum=0;cellNum<headerTitles.length;cellNum++) {  
                CellStyle style;  
                //创建列值  
                Cell cell = row.createCell(cellNum);  
                String key=headerTitles[cellNum];  
                Object cellValue=rowMap.get(key);  
                  
                if(cellValue instanceof String){  
                    cell.setCellValue(createHelper.createRichTextString((String)cellValue));  
                }else if((cellValue instanceof Integer)||(cellValue instanceof Long)){  
                    cell.setCellValue(createHelper.createRichTextString(cellValue.toString()));  
                //针对带小数点的数据的处理  
                }else if((cellValue instanceof Double)||(cellValue instanceof Float)){  
                        cell.setCellValue(Double.valueOf(cellValue.toString()));  
                        style = wb.createCellStyle();  
                        style.setDataFormat(dataformat.getFormat("#.##"));  
                        //设定样式  
                        cell.setCellStyle(style);  
                //针对Date格式  
                }else if(cellValue instanceof Date){  
                    /*   
                     * 定义显示日期的公共格式   
                     * 如:yyyy-MM-dd hh:mm   
                     * */  
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");     
                    String newdate = sdf.format(new Date());   
                    // 填充出产日期     
                    cell.setCellValue(createHelper.createRichTextString(newdate));  
                }else if(cellValue instanceof Boolean){  
                    cell.setCellValue((Boolean)cellValue);  
                }  
            }  
        }  
    }  
    
    /** 
     * 测试数据 
     * @param args 
     */  
    public static void writeExcel() throws Exception {  
          
        //创建一个EXCEL  
        ExcelVO  excelVo=new ExcelVO();  
        excelVo.setPath("C:\\test_poi.xlsx");  
        excelVo.setPrefix("xlsx");  
        List<SheetVO> sheetInfoList=new ArrayList<SheetVO>();  
        for (int i = 0; i < 1; i++) {  
            SheetVO sheetVo=new SheetVO();  
            sheetVo.setHeaderTitle(new String[]{"问题编号","项目名称","项目问题数量"});  
            sheetVo.setRowNum(1);  
            sheetVo.setTitles(new String[]{"0","1","2"});  
            sheetVo.setSheetName("项目名称"+i);  
            sheetVo.setTitle("易程股份有限公司");  
            List<Map<String, Object>> sheetContentMap=new ArrayList<Map<String, Object>>();  
            for (int j = 0; j <20; j++) {  
                Map<String, Object> map=new HashMap<String,Object>();  
                map.put("0", "问题"+j);  
                map.put("1", "项目名称"+i);  
                map.put("2", j+"");  
                sheetContentMap.add(map);  
            }  
            sheetVo.setSheetContentMap(sheetContentMap);  
            sheetInfoList.add(sheetVo);  
        }  
        excelVo.setSheets(sheetInfoList);  
        ExcelWrite  excelService=new ExcelWrite();  
        excelService.createTableViewerExcelFile(excelVo);  
          
    }  
	
}
