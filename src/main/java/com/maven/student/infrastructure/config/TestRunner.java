//package com.maven.student.infrastructure.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.env.*;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class TestRunner implements CommandLineRunner {
//
//    private final ServiceProperties properties;
//    private final Environment env;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        System.out.println(properties.getCompany());
//        properties.getCompany().forEach(company -> {
//            System.out.println("Company Name: " + company.getName());
//            System.out.println("Company Code: " + company.getCode());
//            System.out.println("Company RUC: " + company.getRuc());
//            System.out.println("Company Address: " + company.getAddress());
//            System.out.println("-----------------------------");
//        });
//        System.out.println("-----------------");
//        System.out.println(env.getProperty("test.message"));
//    }
////    private final Environment env;
////
////    @Value("${test.message}")
////    private String testMessage;
////
////    @Override
////    public void run(String... args) throws Exception {
////        System.out.println("\n=== TODOS LOS PROPERTY SOURCES ===");
////
////        if (env instanceof ConfigurableEnvironment configEnv) {
////            MutablePropertySources propertySources = configEnv.getPropertySources();
////
////            // Iterar sobre TODOS los PropertySources
////            propertySources.forEach(propertySource -> {
////                System.out.println("\n📁 PropertySource: " + propertySource.getName());
////                System.out.println("   Tipo: " + propertySource.getClass().getSimpleName());
////
////                if (propertySource instanceof EnumerablePropertySource<?> enumerable) {
////                    System.out.println("   Propiedades:");
////                    String[] propertyNames = enumerable.getPropertyNames();
////                    for (String key : propertyNames) {
////                        Object value = propertySource.getProperty(key);
////                        System.out.println("     - " + key + " = " + value);
////                    }
////                } else {
////                    System.out.println("   ⚠️ No es enumerable, intentando obtener propiedad específica...");
////                    // Intentar obtener propiedades comunes
////                    String[] commonKeys = {"test.message", "spring.application.name", "server.port"};
////                    for (String key : commonKeys) {
////                        Object value = propertySource.getProperty(key);
////                        if (value != null) {
////                            System.out.println("     - " + key + " = " + value);
////                        }
////                    }
////                }
////            });
////        }
////
////        // También buscar usando el Environment directamente
////        System.out.println("\n=== BÚSQUEDA EN ENVIRONMENT ===");
////        System.out.println("spring.application.name = " + env.getProperty("spring.application.name"));
////        System.out.println("server.port = " + env.getProperty("server.port"));
////
////        // Buscar cualquier propiedad que contenga "test"
////        System.out.println("\n=== BÚSQUEDA DE PROPIEDADES CON 'test' ===");
////        if (env instanceof ConfigurableEnvironment configEnv) {
////            for (PropertySource<?> ps : configEnv.getPropertySources()) {
////                if (ps instanceof EnumerablePropertySource<?> enumerable) {
////                    for (String key : enumerable.getPropertyNames()) {
////                        if (key.toLowerCase().contains("test")) {
////                            System.out.println("✅ Encontrada en [" + ps.getName() + "]: " + key + " = " + env.getProperty(key));
////                        }
////                    }
////                }
////            }
////        }
////        System.out.println("=== FIN DEL DIAGNÓSTICO ===\n");
////
////        System.out.println("Valor: " + testMessage);
////        System.out.println("------");
////        System.out.println(env.getProperty("test.message"));
////    }
//}
