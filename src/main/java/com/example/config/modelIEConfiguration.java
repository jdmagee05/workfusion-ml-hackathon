package com.example.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.workfusion.vds.sdk.api.hypermodel.annotation.ModelConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Named;
import com.workfusion.vds.sdk.api.nlp.annotator.Annotator;
import com.workfusion.vds.sdk.api.nlp.configuration.IeConfigurationContext;
import com.workfusion.vds.sdk.api.nlp.fe.FeatureExtractor;
import com.workfusion.vds.sdk.api.nlp.model.Element;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Import;
import com.workfusion.vds.nlp.hypermodel.ie.generic.config.GenericIeHypermodelConfiguration;

import com.example.fe.FeatureExtractorExample;
import com.example.processing.PostProcessorExample;

/**
 * The model configuration class.
 * Here you can configure set of Feature Extractors, Annotators and Post-Processors.
 * Also you can import configuration with set of predefined components or your own configuration
 * */
@ModelConfiguration
@Import(configurations = {
        @Import.Configuration(value = GenericIeHypermodelConfiguration.class)
})
public class modelIEConfiguration {

    @Named("annotators")
    public List<Annotator<Document>> getAnnotators(IeConfigurationContext context) {
        //TODO configure annotators here.
        return new ArrayList<>();
    }

    @Named("featureExtractors")
    public List<FeatureExtractor<Element>> getFeatureExtractors(IeConfigurationContext context) {
        //TODO configure feature extractors here.
        return Arrays.asList(new FeatureExtractorExample<Element>());
    }

    @Named("processors")
    public List<Processor<IeDocument>> getProcessors() {
        //TODO configure post processors here.
        return Arrays.asList(new PostProcessorExample());
    }
}