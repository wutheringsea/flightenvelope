package com.neptune.model;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;

public class SheetVO {  
    private String title; //Sheet中标题  
    private String[] headerTitle;  //表中中的表头  
    private List<Map<String,Object>> sheetContentMap;//表中的数据  
    private String[] titles;//绑定的标题头  
    private int rowNum; //表头起始的行数  
    private String sheetName; //sheet的名称  
    private  Map<String,CellStyle> styles ;  
    public String[] getHeaderTitle() {  
        return headerTitle;  
    }  
    public void setHeaderTitle(String[] headerTitle) {  
        this.headerTitle = headerTitle;  
    }  
    public List<Map<String, Object>> getSheetContentMap() {  
        return sheetContentMap;  
    }  
    public String getTitle() {  
        return title;  
    }  
    public void setTitle(String title) {  
        this.title = title;  
    }  
    public int getRowNum() {  
        return rowNum;  
    }  
    public void setRowNum(int rowNum) {  
        this.rowNum = rowNum;  
    }  
    public void setSheetContentMap(List<Map<String, Object>> sheetContentMap) {  
        this.sheetContentMap = sheetContentMap;  
    }  
    public String getSheetName() {  
        return sheetName;  
    }  
    public void setSheetName(String sheetName) {  
        this.sheetName = sheetName;  
    }  
    public String[] getTitles() {  
        return titles;  
    }  
    public void setTitles(String[] titles) {  
        this.titles = titles;  
    }  
    public Map<String, CellStyle> getStyles() {  
        return styles;  
    }  
    public void setStyles(Map<String, CellStyle> styles) {  
        this.styles = styles;  
    }  
}  
