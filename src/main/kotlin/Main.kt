fun main() {
    val remoteConfigJson = GetRemoteConfigTemplate(Auth()).invoke()
    GetConditions().invoke(remoteConfigJson).forEach {
        val paramsValueMap = GetParamsValueMap().invoke(remoteConfigJson, it)
        CreateActivatedRemoteConfig("com.erkutaras.remoteconfigsample").invoke(it, paramsValueMap)
    }
}