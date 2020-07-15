import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.system.exitProcess

const val BASE_URL = "https://api.telegram.org"
const val PARSE_MODE = "markdown"

const val ANDROID_ROLLOUT_BOT_TOKEN = "983501720:AAE-AH2qamz4GZaEzqelz0i0QZnavvzMDS8"


// args[0] - Project name
// args[1] - Build version
// args[2] - Description
fun main(args: Array<String>) = runBlocking<Unit> {
    val projectName = args[0]
    val buildVersion = args[1]
    val description = try {
        args[2]
    } catch (e: Exception) {
        ""
    }

    sendRolloutNotification(projectName, buildVersion, description)
    exitProcess(0)
}

suspend fun sendRolloutNotification(projectName: String, buildVersion: String, description: String) {
    val chatId = -346458703
    val text = "$projectName билд залит! *Версия $buildVersion*\n_${description}_"
    getBotService().sendRolloutNotification(chatId.toString(), text, PARSE_MODE)
}

fun getBotService(): BotService =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BotService::class.java)
