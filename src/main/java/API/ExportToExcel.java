package API;

import entities.Reclamation;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportToExcel {
    public static void exportToExcel(List<Reclamation> reclamations, File file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reclamations");
        CellStyle headerStyle = createHeaderStyle(workbook);

        int row = 0;
        Row headerRow = sheet.createRow(row++);
        createStyledCell(headerRow, 0, "Name", headerStyle);
        createStyledCell(headerRow, 1, "Last Name", headerStyle);
        createStyledCell(headerRow, 2, "Description", headerStyle);
        createStyledCell(headerRow, 3, "Date of Reclamation", headerStyle);
        createStyledCell(headerRow, 4, "Category", headerStyle);
        createStyledCell(headerRow, 5, "Status", headerStyle);

        for (Reclamation r : reclamations) {
            Row fillRow = sheet.createRow(row++);
            createCell(fillRow, 0, r.getName());
            createCell(fillRow, 1, r.getLastName());
            createCell(fillRow, 2, r.getDescriRec());
            createCell(fillRow, 3, r.getDateRec().toString());
            createCell(fillRow, 4, r.getCategorieRec());
            createCell(fillRow, 5, r.getStatutRec());
        }

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            Logger.getLogger(ExportToExcel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            workbook.close();
        }
    }

    private static void createCell(Row row, int column, String value) {
        Cell cell = row.createCell(column, CellType.STRING);
        cell.setCellValue(value);
    }

    private static void createStyledCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column, CellType.STRING);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private static CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }
}
