package com.example.processing;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.workfusion.vds.sdk.api.nlp.model.Field;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.ProcessingException;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;
import com.workfusion.vds.sdk.nlp.component.processing.normalization.OcrAmountNormalizer;
import com.workfusion.vds.sdk.nlp.component.processing.normalization.OcrDateNormalizer;

public class PostProcessorExample implements Processor<IeDocument> {

    @Override
    public void process(IeDocument document) throws ProcessingException {
        //TODO add post-processing here
       Optional<Field> invoiceDate = document.findField("invoice_date"); 
       Collection<Field> quantity = document.findFields("quantity");
       Collection<Field> totalAmount = document.findFields("total_amount"); 
       Collection<Field> prices = document.findFields("price");
       Optional<Field> email = document.findField("email");
       Optional<Field> invoiceNumber = document.findField("invoice_number");
       Optional<Field> supplierName = document.findField("supplier_name");
       
       
       NormalizeTotalAmount(totalAmount);
       NormalizeInvoiceDate(invoiceDate);
       NormalizeQuantity(quantity);
       NormalizePrice(prices);
       NormalizeEmail(email);
       NormalizeInvoiceNumber(invoiceNumber);
       NormalizeSupplierName(supplierName);
    }
    
    private void NormalizeTotalAmount(Collection<Field> totalAmounts) {
       OcrAmountNormalizer ocrAmountNormalizer = new OcrAmountNormalizer();
       
       for(Field totalAmount: totalAmounts) {
             if(totalAmount != null && totalAmount.getValue() != null) {
                    totalAmount.setValue(ocrAmountNormalizer.normalize(totalAmount.getValue()));
             }
       }
    }
    
	private void NormalizeInvoiceDate(Optional<Field> invoiceDate) {
       OcrDateNormalizer ocrDateNormalizer = new OcrDateNormalizer("MM/dd/yyyy");
       try {
            String dateStr = ocrDateNormalizer.normalize(invoiceDate.get().getValue());
            invoiceDate.get().setValue(dateStr);
       } catch(Exception e) {
             
       }
    }
    
    private void NormalizeQuantity(Collection<Field> quantities) {
       DecimalFormat decFormatter = new DecimalFormat("#.00");
       for(Field quantity : quantities) {
                    if(quantity != null && !StringUtils.isBlank(quantity.getValue())) {
                           String quantityValue = quantity.getValue();
                           String value = quantityValue.replaceAll("[^0-9.]","");
                           Double doubleQuantity = Double.parseDouble(value);
                           quantity.setValue(decFormatter.format(doubleQuantity));
             }
       }
    }
    
    private void NormalizePrice(Collection<Field> prices) {
       OcrAmountNormalizer ocrAmountNormalizer = new OcrAmountNormalizer();
       for(Field price: prices) {
             if(price != null && price.getValue() != null) {
                    String priceValue = price.getValue().replaceAll("[^0-9.]","");
                    price.setValue(ocrAmountNormalizer.normalize(priceValue));
             }
       }
    }
    
    private void NormalizeEmail(Optional<Field> emailField) {
      if(emailField.isPresent() && !StringUtils.isBlank(emailField.get().getValue())){
       String email = emailField.get().getValue();
       email = email.replaceAll("(S)", "@");
       email = email.replaceAll(" ", "");
       email = email.replaceAll("\n", "");
       email = email.replaceAll("<3", "@");
       if(!email.contains("@")) {
             if(email.contains("S")) {
                    if(email.indexOf("S") != 0) {
                           email = email.replaceAll("S", "@");
                    }
             }
       }
       emailField.get().setValue(email);
      }
    }
    
    private void NormalizeInvoiceNumber(Optional<Field> invoiceNumberField) {
       if(invoiceNumberField.isPresent() && !StringUtils.isBlank(invoiceNumberField.get().getValue())){
             String invoiceNumber = invoiceNumberField.get().getValue();
             if(invoiceNumber.contains("#")) {
                    invoiceNumber = invoiceNumber.substring(invoiceNumber.indexOf("#") + 1);
             }
             invoiceNumberField.get().setValue(invoiceNumber);
       }
    }
    
    private void NormalizeSupplierName(Optional<Field> supplierNameField) {
    	if(supplierNameField.isPresent() && !StringUtils.isBlank(supplierNameField.get().getValue())) {
    		String supplierName = supplierNameField.get().getValue();
    		supplierName = supplierName.replaceAll("\\s+"," ");
    		
    		supplierNameField.get().setValue(supplierName);
    	}
    }
}