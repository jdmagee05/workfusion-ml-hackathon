package com.example.model;

import com.workfusion.vds.nlp.hypermodel.ie.generic.GenericIeHypermodel;
import com.workfusion.vds.sdk.api.hypermodel.ModelType;
import com.workfusion.vds.sdk.api.hypermodel.annotation.HypermodelConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.annotation.ModelDescription;

import com.example.config.modelIEConfiguration;

/**
 * The model class. Define here you model details like code, version etc.
 */
@ModelDescription(
        code = "EYHackathonML",
        title = "EYHackathonML",
        description = "Ascend hackathon EY",
        version = "1.0",
        type = ModelType.IE
)
@HypermodelConfiguration(modelIEConfiguration.class)
public class modelIE extends GenericIeHypermodel {

        public modelIE() throws Exception {
        }
}
