package de.cegeka.jax;

import org.openapitools.codegen.*;
import org.openapitools.codegen.languages.SpringCodegen;

import java.io.File;

public class SecuredSpringGenerator extends SpringCodegen {

  public CodegenType getTag() {
    return CodegenType.SERVER;
  }

  public String getName() {
    return "secured-spring";
  }

  public String getHelp() {
    return "Generates a secured-spring based server.";
  }

	@Override
	public void processOpts() {
		super.processOpts();
		if (SPRING_BOOT.equals(getLibrary())) {
			supportingFiles().add(new SupportingFile("apiSecurity.mustache",
					(getSourceFolder() + File.separator + getBasePackage()).replace(".", File.separator), "ApiSecurityDefinition.java"));
		}
  }

}
