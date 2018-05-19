package com.example.run;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.workfusion.nlp.uima.pipeline.constants.ConfigurationConstants;
import com.workfusion.vds.sdk.api.nlp.configuration.FieldInfo;
import com.workfusion.vds.sdk.api.nlp.configuration.FieldType;
import com.workfusion.vds.sdk.run.ModelRunner;
import com.workfusion.vds.sdk.run.config.LocalTrainingConfiguration;

import com.example.model.modelIE;

/**
 *  This runner allows you to start model training on your local machine.
 *  Paths to training set and output folders, fields configuration are required for the lauch.
 */
public class ModelTrainingRunner {

    public static void main(String[] args) throws Exception {
        System.setProperty("WORKFLOW_LOG_FOLDER", "./logs/");
        

        //TODO Configure input/output
        Path inputDirPath = Paths.get("C:\\workfusion\\train\\train");
        Path outputDirPath = Paths.get("C:\\workfusion\\train_output");

        //TODO Configure fields according to your use-case
        List<FieldInfo> fields = new ArrayList<>();
        
        fields.add(new FieldInfo.Builder("invoice_number").type(FieldType.INVOICE_NUMBER).
        		required(true).
        		multiValue(false).
        		build());
        
        fields.add(new FieldInfo.Builder("invoice_date").type(FieldType.INVOICE_DATE).
        		required(true).
        		multiValue(false).
        		build());
        
        fields.add(new FieldInfo.Builder("total_amount").type(FieldType.TOTAL_AMOUNT).
        		required(true).
        		multiValue(false).
        		build());
        
        fields.add(new FieldInfo.Builder("supplier_name").type(FieldType.COMPANY).
        		required(true).
        		multiValue(false).
        		build());
        
        fields.add(new FieldInfo.Builder("email").type(FieldType.EMAIL).
        		required(true).
        		multiValue(false).
        		build());
        
        fields.add(new FieldInfo.Builder("line_item").type(FieldType.GROUP).
                required(true).
                multiValue(false).
                child(new FieldInfo.Builder("product").type(FieldType.FREE_TEXT).
                    required(true).
                    multiValue(true).build()).
                child(new FieldInfo.Builder("price").type(FieldType.PRICE).
                        required(true).
                        multiValue(true).build()).
                child(new FieldInfo.Builder("quantity").type(FieldType.NUMBER).
                        required(true).
                        multiValue(true).build()).
                build()); 
        		
        //TODO add parameters if needed.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ConfigurationConstants.HPO_TIME_LIMIT, 5 * 60);

        LocalTrainingConfiguration configuration = LocalTrainingConfiguration.builder()
                .inputDir(inputDirPath)
                .outputDir(outputDirPath)
                .fields(fields)
                .parameters(parameters)
                .build();

        ModelRunner.run(modelIE.class, configuration);
    }
}
