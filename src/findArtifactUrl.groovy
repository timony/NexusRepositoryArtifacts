import groovy.json.JsonSlurper

try {
    def buildNexusUrl = 'http://10.90.156.234:8081'
    def buildRepository = 'tpt-snapshots'
    def buildGroupId = 'fi.trafi.tpt'
    def buildArtifactId = 'tpt-scheduler'
    def buildArtifactVersion = '2.0-20180227.134633-3'

    def artifacts = []
    def url = "${buildNexusUrl}/service/rest/beta/search?repository=${buildRepository}&maven.groupId=${buildGroupId}&maven.artifactId=${buildArtifactId}&version=${buildArtifactVersion}"
    def artifactsObjectRaw = ["curl", "-s", "-H", "accept: application/json", "-k", "--url", "${url}"].execute().text

    def jsonSlurper = new JsonSlurper()
    def artifactJsonObject = jsonSlurper.parseText(artifactsObjectRaw)
    def itemsArray = artifactJsonObject.items

    itemsArray[0].assets.each { item ->
        def downloadUrl = item.downloadUrl
        artifacts.add(downloadUrl)
    }
    artifacts = artifacts.findAll {
        !it.endsWith('.pom') && !it.endsWith('.md5') && !it.endsWith('.sha1')
    }

    print artifacts
    return artifacts
} catch (Exception e) {
    print e
}