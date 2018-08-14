import groovy.json.JsonSlurper

try {
    def buildNexusUrl = 'http://10.90.156.234:8081'
    def buildRepository = 'tpt-snapshots'

    def groups = []
    groups.add('Choose one...:selected')
    def url = "${buildNexusUrl}/service/rest/beta/search?repository=${buildRepository}"
    def artifactsObjectRaw = ["curl", "-s", "-H", "accept: application/json", "-k", "--url", "${url}"].execute().text

    def jsonSlurper = new JsonSlurper()
    def artifactJsonObject = jsonSlurper.parseText(artifactsObjectRaw)
    def itemsArray = artifactJsonObject.items

    itemsArray.each { item ->
        if (!groups.contains(item.group)) {
            groups.add(item.group)
        }
    }
    //hardcoded since beta search does not return fi.trafi, but only fi.trafi.tpt, may be removed after MS
    //groupId is aligned with other Tpt apps
    groups.add('fi.trafi')
    print groups
    return groups
} catch (Exception e) {
    print e
}