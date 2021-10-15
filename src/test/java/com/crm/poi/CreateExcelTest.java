package com.crm.poi;

import com.sun.tools.internal.ws.wsdl.document.Output;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

public class CreateExcelTest {

    public static void main(String[] args) throws Exception{
        //1.excel文件对象,空的excel
        HSSFWorkbook wb=new HSSFWorkbook();
        //2.工作表
        HSSFSheet sheet=wb.createSheet("学生列表");
        //3.行 从0开始
        HSSFRow row=sheet.createRow(0);
        //4.列（单元格） 从0开始
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("学号");
        cell=row.createCell(1);
        cell.setCellValue("姓名");
        cell=row.createCell(2);
        cell.setCellValue("年龄");

        //样式对象
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        System.out.println(new Date());
        for(int i=1;i<=60000;i++){
            //第2行开始
            row=sheet.createRow(i);
            //第1列
            cell=row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(i);
            //第2列
            cell=row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("tom"+i);

            //第3列
            cell=row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(20+i);
        }
        System.out.println(new Date());
        OutputStream os=new FileOutputStream("d:\\student.xls");
        wb.write(os);
        os.close();
        wb.close();
        System.out.println("-----------------------end------------------");

    }
}
