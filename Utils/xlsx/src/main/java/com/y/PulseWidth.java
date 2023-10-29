package com.y;

import cn.hutool.core.util.NumberUtil;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Y
 * @version 1.0
 * @date 2023/10/23 11:03
 */
@Getter
public class PulseWidth {
    //1米=1000毫米
    private static final double M_MM = 1000;
    //1秒=1E12皮秒
    private static final double S_PS = 1000000000000.0;

    /**
     * 位置的原始数据(M)
     */
    private final double locationM;
    /**
     * 脉宽原始数据(S)
     */
    private final double timeS;
    /**
     * 位置的原始数据(mm)
     */
    private final double locationMM;
    /**
     * 脉宽原始数据(Ps)
     */
    private final double timePs;
    /**
     * 关系常量
     */
    private final double constantOld;
    /**
     * 关系常量
     */
    private final double constantNew;

    public PulseWidth(double locationM, double timeS) {
        //米和秒
        this.locationM = locationM;
        this.timeS = timeS;
        this.constantOld = div(timeS, locationM);


        //毫米和皮秒
        this.locationMM = mul(this.locationM, M_MM);
        this.timePs = mul(this.timeS, S_PS);
        this.constantNew = div(this.timePs, this.locationMM);
    }

    /**
     * Xlsx
     *
     * @param path       路径
     * @param name       名字
     * @param targetPath 目标路径
     */
    public static void xlsx(String path, String name, String targetPath, boolean write) {
        try (FileInputStream input = new FileInputStream(path)) {
            // 获取第一个工作表
            Workbook workbookR = new XSSFWorkbook(input);
            Sheet sheetR = workbookR.getSheetAt(0);
            List<PulseWidth> list = new ArrayList<>(9000);
            double numOld = 0;
            double numNew = 0;
            sheetR.forEach(s -> list.add(new PulseWidth(
                    s.getCell(0).getNumericCellValue(),
                    s.getCell(1).getNumericCellValue())));
            for (PulseWidth pulseWidth : list) {
                numOld = add(numOld, pulseWidth.constantOld);
                numNew = add(numNew, pulseWidth.constantNew);
            }
            System.out.println(name + "常数(旧)" + div(numOld, list.size()));
            System.out.println(name + "常数(新)" + div(numNew, list.size()));


            if (write) {
                // 创建一个新的XLSX工作簿
                Workbook workbookW = new XSSFWorkbook();
                // 创建一个工作表
                Sheet sheetW = workbookW.createSheet(name);
                // 创建标题行
                Row headerRow = sheetW.createRow(0);
                headerRow.createCell(0).setCellValue("米");
                headerRow.createCell(1).setCellValue("秒");
                headerRow.createCell(2).setCellValue("常数(秒/米)");
                headerRow.createCell(3).setCellValue("  ");
                headerRow.createCell(4).setCellValue("毫米");
                headerRow.createCell(5).setCellValue("皮秒");
                headerRow.createCell(6).setCellValue("常数(皮秒/毫米)");
                // 写入对象数据
                for (int i = 0; i < list.size(); i++) {
                    PulseWidth pw = list.get(i);
                    Row row = sheetW.createRow(i + 1);
                    row.createCell(0).setCellValue(pw.getLocationM());
                    row.createCell(1).setCellValue(pw.getTimeS());
                    row.createCell(2).setCellValue(pw.getConstantOld());
                    row.createCell(3).setCellValue(" ");
                    row.createCell(4).setCellValue(pw.getLocationMM());
                    row.createCell(5).setCellValue(pw.getTimePs());
                    row.createCell(6).setCellValue(pw.getConstantNew());
                }
                FileOutputStream fileOut = new FileOutputStream(targetPath);
                workbookW.write(fileOut);
                fileOut.close();
                workbookW.close();
                System.out.println(name + ".xlsx写入完成");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Div
     *
     * @param n1 n1
     * @param n2 氮气
     * @return double
     */
    public static double div(double n1, double n2) {
        return NumberUtil.div(n1, n2, 14);
    }

    public static double add(double b1, double b2) {
        return NumberUtil.add(b1, b2);
    }

    public static double mul(double n1, double n2) {
        return NumberUtil.mul(n1, n2);
    }
}
