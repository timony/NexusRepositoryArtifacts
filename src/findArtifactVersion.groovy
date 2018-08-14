import groovy.json.JsonSlurper

try {
    def buildNexusUrl = 'http://10.90.156.234:8081'
    def buildArtifactId = 'hallintapalvelu'
    def buildRepository = 'tpt-snapshots'
    def buildGroupId = 'fi.trafi'

    def artifacts = []
    def url = "${buildNexusUrl}/service/rest/beta/search?repository=${buildRepository}&maven.groupId=${buildGroupId}&maven.artifactId=${buildArtifactId}"
    def artifactsObjectRaw = ["curl", "-s", "-H", "accept: application/json", "-k", "--url", "${url}"].execute().text

    def jsonSlurper = new JsonSlurper()
    def artifactJsonObject = jsonSlurper.parseText(artifactsObjectRaw)
    def itemsArray = artifactJsonObject.items
    itemsArray.each{item ->
        def version = item.version
        artifacts.add(version)
    }
    artifacts = artifacts.sort{
        def tokens = it.tokenize('-')
        return tokens[1]
    }.reverse(true)
    print artifacts
    return artifacts
} catch (Exception e) {
    print e
}