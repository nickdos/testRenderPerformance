class BootStrap {

    def init = { servletContext ->
        def searchResultsJson = this.getClass().getResource("facets.json").text
        log.debug "searchResultsJson = ${searchResultsJson}"
    }
    def destroy = {
    }
}
