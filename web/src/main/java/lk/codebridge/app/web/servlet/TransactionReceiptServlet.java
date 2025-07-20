package lk.codebridge.app.web.servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lk.codebridge.app.core.model.Transfer;
import lk.codebridge.app.core.service.BankAccountService;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/user/modernReceipt")
public class TransactionReceiptServlet extends HttpServlet {

    @EJB
    private BankAccountService bankAccountService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String id = request.getParameter("id");

        if (id == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Transfer transfer = bankAccountService.getTransfer(Integer.parseInt(id));

        if (transfer == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        // Dummy data (replace with real data source)
        String bankName = "National Bank";
        String senderName = transfer.getAccountFrom().getUser().getFullName();
        String senderAcc = transfer.getAccountFrom().getAccountNumber();
        String accountType = transfer.getAccountFrom().getAccountType().toString();
        double amount = transfer.getAmount();
        LocalDateTime txnDate = transfer.getTime();
        LocalDateTime generatedDate = LocalDateTime.now();
        String receiverName = transfer.getAccountTo().getUser().getFullName();
        String status = transfer.getStatus().toString();
        String type = transfer.getType().toString();
        String receiverAcc = transfer.getAccountTo().getAccountNumber();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=ModernReceipt.pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // Fonts
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, new BaseColor(30, 60, 120));
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            Font statusFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);

            // --- Header Section ---
            Paragraph title = new Paragraph(bankName + "\nTransaction Receipt", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10f);
            document.add(title);

            LineSeparator line = new LineSeparator();
            line.setLineColor(new BaseColor(200, 200, 200));
            document.add(line);
            document.add(Chunk.NEWLINE);

            // --- Transaction Summary Box ---
            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(100);
            summaryTable.setWidths(new int[]{1, 2});
            summaryTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            addSummary(summaryTable, "Amount", String.format("LKR %.2f", amount), headerFont, normalFont);
            addSummary(summaryTable, "Status", status, headerFont, getStatusCellFont(status));

            document.add(summaryTable);
            document.add(Chunk.NEWLINE);

            // --- Details Section ---
            PdfPTable detailTable = new PdfPTable(2);
            detailTable.setWidthPercentage(100);
            detailTable.setSpacingBefore(5f);
            detailTable.setWidths(new int[]{1, 2});
            detailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            addDetailRow(detailTable, "Sender Name", senderName, headerFont, normalFont);
            addDetailRow(detailTable, "Sender Account No", senderAcc, headerFont, normalFont);
            addDetailRow(detailTable, "Account Type", accountType, headerFont, normalFont);
            addDetailRow(detailTable, "Transfer Type", type, headerFont, normalFont);
            addDetailRow(detailTable, "Receiver Name", receiverName, headerFont, normalFont);
            addDetailRow(detailTable, "Receiver Account No", receiverAcc, headerFont, normalFont);
            addDetailRow(detailTable, "Transaction Date", txnDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), headerFont, normalFont);
            addDetailRow(detailTable, "Receipt Generated", generatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), headerFont, normalFont);

            document.add(detailTable);

            document.add(Chunk.NEWLINE);
            document.add(line);
            document.add(new Paragraph("Thank you for using National Bank", normalFont));

            document.close();
            writer.flush();

        } catch (DocumentException e) {
            throw new IOException("Error creating PDF receipt", e);
        }
    }

    private void addSummary(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));

        labelCell.setBackgroundColor(new BaseColor(245, 245, 245));
        valueCell.setBackgroundColor(new BaseColor(220, 235, 250));

        labelCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setBorder(Rectangle.NO_BORDER);

        labelCell.setPadding(8f);
        valueCell.setPadding(8f);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addDetailRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));

        labelCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setBorder(Rectangle.NO_BORDER);

        labelCell.setPadding(6f);
        valueCell.setPadding(6f);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private Font getStatusCellFont(String status) {
        if ("Success".equalsIgnoreCase(status)) {
            return new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.GREEN.darker());
        } else if ("Pending".equalsIgnoreCase(status)) {
            return new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.ORANGE);
        } else {
            return new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED);
        }
    }
}
