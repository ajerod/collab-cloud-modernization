package com.collab.workspace_service.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = "com.collab.workspace_service",
        importOptions = ImportOption.DoNotIncludeTests.class
)
class ArchitectureRulesTest {

    @ArchTest
    static final ArchRule domain_should_not_depend_on_spring_or_jakarta =
            noClasses()
                    .that()
                    .resideInAPackage("..domain..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            "org.springframework..",
                            "jakarta.persistence..",
                            "jakarta.validation..",
                            "org.hibernate.."
                    );

    @ArchTest
    static final ArchRule domain_should_not_depend_on_application_adapters_config_or_common =
            noClasses()
                    .that()
                    .resideInAPackage("..domain..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            "..application..",
                            "..adapter..",
                            "..config..",
                            "..common.."
                    );

    @ArchTest
    static final ArchRule application_should_not_depend_on_adapters_or_config =
            noClasses()
                    .that()
                    .resideInAPackage("..application..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            "..adapter..",
                            "..config.."
                    );

    @ArchTest
    static final ArchRule application_should_not_depend_on_web_or_persistence_frameworks =
            noClasses()
                    .that()
                    .resideInAPackage("..application..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            "org.springframework.web..",
                            "org.springframework.http..",
                            "org.springframework.data..",
                            "jakarta.persistence..",
                            "jakarta.validation.."
                    );

    @ArchTest
    static final ArchRule inbound_resources_should_not_depend_on_outbound_adapters =
            noClasses()
                    .that()
                    .resideInAPackage("..adapter.in.web.resource..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            "..adapter.out..",
                            "..application.port.out..",
                            "..application.service.."
                    );

    @ArchTest
    static final ArchRule inbound_facades_should_not_depend_on_outbound_adapters =
            noClasses()
                    .that()
                    .resideInAPackage("..adapter.in.web.facade..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            "..adapter.out..",
                            "..application.port.out..",
                            "..application.service.."
                    );

    @ArchTest
    static final ArchRule outbound_adapters_should_not_depend_on_inbound_adapters =
            noClasses()
                    .that()
                    .resideInAPackage("..adapter.out..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage("..adapter.in..");

    @ArchTest
    static final ArchRule resources_should_reside_in_resource_package =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("Resource")
                    .should()
                    .resideInAPackage("..adapter.in.web.resource..");

    @ArchTest
    static final ArchRule facades_should_reside_in_facade_package =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("Facade")
                    .should()
                    .resideInAPackage("..adapter.in.web.facade..");

    @ArchTest
    static final ArchRule use_cases_should_be_interfaces =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("UseCase")
                    .should()
                    .beInterfaces();

    @ArchTest
    static final ArchRule ports_should_be_interfaces =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("Port")
                    .should()
                    .beInterfaces();
}