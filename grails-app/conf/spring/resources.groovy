// Place your Spring DSL code here
beans = {
    // Custom message source
    messageSource(ExtendedPluginAwareResourceBundleMessageSource) {
        basenames = ["classpath:grails-app/i18n/messages","${application.config.biocache.baseUrl}/facets/i18n"] as String[]
        cacheSeconds = (60 * 60 * 6) // 6 hours
        useCodeAsDefaultMessage = false
    }
}
