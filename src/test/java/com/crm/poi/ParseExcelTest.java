package com.crm.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;

public class ParseExcelTest {

    public static void main(String[] args) throws Exception{
        InputStream is=new FileInputStream("d:\\市场活动.xls");
        HSSFWorkbook wb=new HSSFWorkbook(is); //不是空的excel
        HSSFSheet sheet=wb.getSheetAt(0); //第一张表
        HSSFRow row=null;
        HSSFCell cell=null;

        //获取excel中的数据
        //读取行
        for(int i=1;i<=sheet.getLastRowNum();i++){
            row=sheet.getRow(i);
            //读取列
            for(int j=0;j<row.getLastCellNum();j++){
                cell=row.getCell(j); //读取到每一列
                //写一个方法
                System.out.print(getCellValue(cell)+" ");
            }
            System.out.println();
        }

    }

    //判断单元格类型用对应的方法来获取值,并转成string类型
    public static String getCellValue(HSSFCell cell){
        String ret="";
        switch (cell.getCellType()){
            case HSSFCell.CELL_TYPE_STRING:
                ret=cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                ret=cell.getBooleanCellValue()+"";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                ret=cell.getNumericCellValue()+"";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                ret=cell.getCellFormula()+"";
                break;
            default:
                ret="";

        }
        return ret;
    }
}
