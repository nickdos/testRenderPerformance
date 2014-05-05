package testrenderperformance

import grails.converters.JSON
import groovy.SpatialSearchRequestParams
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class SearchController {

    def postProcessingService

    def index(SpatialSearchRequestParams requestParams) {
        def start = System.currentTimeMillis()
        def facets = new File("grails-app/conf/facets.json").text
        log.debug "facets = ${facets}"

        def defaultFacetsJsonObj = JSON.parse(new File("grails-app/conf/facets.json").text)
        //def groupedFacetsJsonObj = JSON.parse(this.getClass().getResource( 'groupedFacets.json' ).text)
        def searchResultsJsonObj = JSON.parse(new File("grails-app/conf/search.json").text)

        try {
            Map defaultFacets = postProcessingService.getAllFacets(defaultFacetsJsonObj)
            String[] filteredFacets = postProcessingService.getFilteredFacets(defaultFacets)
            requestParams.facets = filteredFacets
            def wsStart = System.currentTimeMillis()
            //JSONObject searchResults = webServicesService.fullTextSearch(requestParams)
            def wsTime = (System.currentTimeMillis() - wsStart)

            [
                    sr: searchResultsJsonObj,
                    searchRequestParams: requestParams,
                    defaultFacets: defaultFacetsJsonObj,
                    groupedFacets: this.getGroupedFacets(),
                    groupedFacetsMap: postProcessingService.getMapOfGroupedFacets(searchResultsJsonObj.facetResults),
                    //dynamicFacets: null, // TODO
                    //hasImages: postProcessingService.resultsHaveImages(searchResultsJsonObj),
                    showSpeciesImages: false, // TODO
                    sort: requestParams.sort,
                    dir: requestParams.dir,
                    //userId: authService?.getUserId(),
                    //userEmail: authService?.getEmail(),
                    processingTime: (System.currentTimeMillis() - start),
                    wsTime: wsTime
            ]
        } catch (Exception ex) {
            log.warn "Error getting search results: $ex.message", ex
            flash.message = "${ex.message}"
            render view:'../error'
        }
    }

    private Map getGroupedFacets() {
        def groupedArray = JSON.parse(new File("grails-app/conf/groupedFacets.json").text)
        Map groupedMap = [:] // LinkedHashMap by default so ordering is maintained

        // simplify DS into a Map with key as group name and value as list of facets
        groupedArray.each { group ->
            groupedMap.put(group.title, group.facets.collect { it.field })
        }

        groupedMap
    }
}
