/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.modulith.docs.Documenter.CanvasOptions;
import org.springframework.modulith.docs.Documenter.DiagramOptions;
import org.springframework.modulith.docs.Documenter.DiagramOptions.DiagramStyle;

/**
 * @author Oliver Drotbohm
 */
class ModularityTests {

	ApplicationModules modules = ApplicationModules.of(Application.class);

	@Test
	void verifyModularity() {

		// --> Module model
		// System.out.println(modules.toString());

		// --> Trigger verification
		modules.verify();
	}

	@Test
	void renderDocumentation() throws Exception {

		var canvasOptions = CanvasOptions.defaults()

		// --> Optionally enable linking of JavaDoc
		// .withApiBase("https://foobar.something")

		;

		var docOptions = DiagramOptions.defaults()
				.withStyle(DiagramStyle.UML);

		new Documenter(modules) //
				.writeDocumentation(docOptions, canvasOptions);
	}
}
