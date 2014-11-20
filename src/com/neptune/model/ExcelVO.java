package com.neptune.model;

import java.util.List;

public class ExcelVO {  
    private String path;  
    private List<SheetVO> sheets;  
    private String prefix;  
      
    public String getPrefix() {  
        return prefix;  
    }  
    public void setPrefix(String prefix) {  
        this.prefix = prefix;  
    }  
    public String getPath() {  
        return path;  
    }  
    public void setPath(String path) {  
        this.path = path;  
    }  
    public List<SheetVO> getSheets() {  
        return sheets;  
    }  
    public void setSheets(List<SheetVO> sheet) {  
        this.sheets = sheet;  
    }  
}  