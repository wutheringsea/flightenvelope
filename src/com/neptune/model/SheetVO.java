package com.neptune.model;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;

public class SheetVO {  
    private String title; //Sheet�б���  
    private String[] headerTitle;  //�����еı�ͷ  
    private List<Map<String,Object>> sheetContentMap;//���е�����  
    private String[] titles;//�󶨵ı���ͷ  
    private int rowNum; //��ͷ��ʼ������  
    private String sheetName; //sheet������  
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
