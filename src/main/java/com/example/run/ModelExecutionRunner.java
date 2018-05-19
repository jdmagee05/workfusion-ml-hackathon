package com.example.run;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.workfusion.vds.sdk.run.ModelRunner;
import com.workfusion.vds.sdk.run.config.LocalExecutionConfiguration;

import com.example.model.modelIE;

/**
 *  Runner class for local model execution.
 *  Could be used for model tuning and post-processing development.
 *  Paths to input documents, trained model and output folders are required for the lauch.
 */
public class ModelExecutionRunner {

    public static void main(String[] args) throws Exception {
        System.setProperty("WORKFLOW_LOG_FOLDER", "./logs/");

        //TODO put correct values for the paths
        Path trainedModelPath = Paths.get("TODO add path to trained model folder");
        Path inputDirPath = Paths.get("TODO add path to folder with training set");
        Path outputDirPath = Paths.get("TODO add path to output folder");

        //TODO add parameters if needed.
        Map<String, Object> parameters = new HashMap<>();

        LocalExecutionConfiguration configuration = LocalExecutionConfiguration.builder()
                .inputDir(inputDirPath)
                .outputDir(outputDirPath)
                .trainedModelDir(trainedModelPath)
                .parameters(parameters)
                .build();

        ModelRunner.run(modelIE.class, configuration);
    }
}
