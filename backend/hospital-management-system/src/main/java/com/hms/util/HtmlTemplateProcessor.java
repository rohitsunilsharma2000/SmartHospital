package com.hms.util;

import com.hms.dto.PdfBillDTO;
import com.hms.dto.PdfBillItemDTO;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class HtmlTemplateProcessor {

    public String generateHtmlFromTemplate(String templatePath, PdfBillDTO billDTO) throws Exception {
        String html = new String(Files.readAllBytes(Paths.get(templatePath)));

        // Replace placeholders in the HTML
        html = html.replace("${clinicName}", billDTO.getClinicName())
                   .replace("${clinicAddress}", billDTO.getClinicAddress())
                   .replace("${phoneNumber}", billDTO.getPhoneNumber())
                   .replace("${email}", billDTO.getEmail())
                   .replace("${website}", billDTO.getWebsite())
                   .replace("${ivrPhone}", billDTO.getIvrPhone())

                   .replace("${patientName}", billDTO.getPatientName())
                   .replace("${contactNumber}", billDTO.getContactNumber())
                   .replace("${patientId}", billDTO.getPatientId())
                   .replace("${ageSex}", billDTO.getAgeSex())
                   .replace("${dateTime}", billDTO.getDateTime().toString())
                   .replace("${billNumber}", billDTO.getBillNumber())
                   .replace("${doctorName}", billDTO.getDoctorName())

                   .replace("${totalBilled}", String.format("%.2f", billDTO.getTotalBilled()))
                   .replace("${discount}", String.format("%.2f", billDTO.getDiscount()))
                   .replace("${insurancePaid}", String.format("%.2f", billDTO.getInsurancePaid()))
                   .replace("${finalPayableAmount}", String.format("%.2f", billDTO.getFinalPayableAmount()))
                   .replace("${amountPaid}", String.format("%.2f", billDTO.getAmountPaid()))

                   .replace("${amountInWords}", billDTO.getAmountInWords())
                   .replace("${receiptNumber}", billDTO.getReceiptNumber())
                   .replace("${paymentMode}", billDTO.getPaymentMode())

                   .replace("${tokenNumber}", String.valueOf(billDTO.getTokenNumber()))
                   .replace("${queue}", billDTO.getQueue())
                   .replace("${room}", billDTO.getRoom())
                   .replace("${authorizedSignatory}", billDTO.getAuthorizedSignatory());

        // Generate items dynamically
        StringBuilder itemRows = new StringBuilder();
        List<PdfBillItemDTO> items = billDTO.getItems();
        for (PdfBillItemDTO item : items) {
            itemRows.append("<tr>")
                    .append("<td>").append(item.getSerialNumber()).append("</td>")
                    .append("<td>").append(item.getParticulars()).append("</td>")
                    .append("<td>").append(String.format("%.2f", item.getCharges())).append("</td>")
                    .append("<td>").append(item.getQuantity()).append("</td>")
                    .append("<td>").append(String.format("%.2f", item.getAmount())).append("</td>")
                    .append("</tr>");
        }
        // ✅ Calculate Total Payable
        double totalPayable = Math.max(
                billDTO.getTotalBilled() - billDTO.getInsurancePaid()- billDTO.getDiscount(), 0.00);

        // ✅ Add Total Payable Row
        itemRows.append("<tr>")
                .append("<td colspan='4' style='text-align:right; font-weight:bold;'>Total Payable</td>")
                .append("<td style='font-weight:bold;'>").append(String.format("%.2f", totalPayable)).append("</td>")
                .append("</tr>");

        html = html.replace("${items}", itemRows.toString());

        return html;
    }
}
