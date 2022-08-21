import picocli.CommandLine
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command
class App : Callable<Int> {

    @CommandLine.Option(names = ["--appId"])
    private var appId: String? = null

    @CommandLine.Option(names = ["--secret"])
    private var secrets: String? = null

    @CommandLine.Option(names = ["--serviceAccount"])
    private var serviceAccount: String? = null

    override fun call(): Int {
        if (appId.isNullOrEmpty()) {
            throw IllegalArgumentException("You need to provide app id to generate mocked remote config")
        } else if (secrets.isNullOrEmpty()) {
            throw IllegalArgumentException("You need to save secrets json file")
        } else if (serviceAccount.isNullOrEmpty()) {
            throw IllegalArgumentException("You need to generate service account and pass to script to interact with " +
                    "firebase remote config apis")
        } else {
            val auth = Auth(requireNotNull(serviceAccount))
            val remoteConfigJson = GetRemoteConfigTemplate(auth, requireNotNull(secrets)).invoke()
            GetConditions().invoke(remoteConfigJson).forEach {
                val paramsValueMap = GetParamsValueMap().invoke(remoteConfigJson, it)
                CreateActivatedRemoteConfig(requireNotNull(appId)).invoke(it, paramsValueMap)
            }
            return 0
        }
    }
}

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    val exitCode = CommandLine(App())
        .setExitCodeExceptionMapper { 1 }
        .execute(*args)
    exitProcess(exitCode)
}