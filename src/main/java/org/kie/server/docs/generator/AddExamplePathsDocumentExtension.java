/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.server.docs.generator;

import java.io.StringReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import io.github.swagger2markup.markup.builder.MarkupDocBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import io.github.swagger2markup.model.PathOperation;
import io.github.swagger2markup.spi.PathsDocumentExtension;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class AddExamplePathsDocumentExtension extends PathsDocumentExtension {

    @Override
    public void apply(Context context) {

        MarkupDocBuilder markupBuilder = context.getMarkupDocBuilder();
        Position position = context.getPosition();

        if (context.getOperation().isPresent()) {
            PathOperation operation = context.getOperation().get();
            if (position.equals(Position.OPERATION_PARAMETERS_AFTER)) {

                Optional<Parameter> param = operation.getOperation().getParameters().stream().filter(p -> p instanceof BodyParameter).findFirst();
                if (param.isPresent()) {

                    Map<String, Object> extensions = param.get().getVendorExtensions();

                    if (extensions.containsKey("x-examples")) {
                        Map<String, Object> examples = (Map<String, Object>) extensions.get("x-examples");
                        markupBuilder.sectionTitleLevel3("Examples");

                        for (Entry<String, Object> entry : examples.entrySet()) {

                            StringBuilder sourceExample = new StringBuilder();
                            sourceExample
                                .append("[source]\n")
                                .append("----\n")
                                .append(entry.getValue())
                                .append("\n")
                                .append("----\n");

                            markupBuilder.sectionTitleLevel4(entry.getKey())
                            .importMarkup(new StringReader(sourceExample.toString()), MarkupLanguage.ASCIIDOC)
                            .newLine();
                        }
                    }
                }
            }
        }
    }

}
