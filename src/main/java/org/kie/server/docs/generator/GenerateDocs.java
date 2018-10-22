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

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.OrderBy;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;

public class GenerateDocs {

    public static void main(String[] args) throws MalformedURLException {
        URL remoteSwaggerFile = new URL("http://localhost:8080/kie-server/services/rest/server/swagger.json");
        Path outputDirectory = Paths.get("target/asciidoc");
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withGeneratedExamples()
                .withOperationOrdering(OrderBy.NATURAL)
                .withPathsGroupedBy(GroupBy.TAGS)
                .build();
                
        Swagger2MarkupConverter.from(remoteSwaggerFile) 
        .withConfig(config)
                .build() 
                .toFolder(outputDirectory);
    }

}
