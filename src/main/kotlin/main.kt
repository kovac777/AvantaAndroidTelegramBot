import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.system.exitProcess

const val BASE_URL = "https://api.telegram.org"
const val PARSE_MODE = "markdown"

const val ANDROID_ROLLOUT_BOT_TOKEN = "983501720:AAE-AH2qamz4GZaEzqelz0i0QZnavvzMDS8"
const val PUSH_NOTIFICATION_BOT_TOKEN = "774481227:AAFz6YCHq2_91DOYpQ7gQZoY13Ja0mjwk4U"


fun main(args: Array<String>) = runBlocking<Unit> {
    // args[0] - bot type
    when (args[0]) {
        "rollout_build" -> {
            // args[1] - android build version
            // args[2] - android build description
            val version = args[1]
            var description = ""
            try {
                description = args[2]
                sendRolloutNotification(version, description)
            } catch (ex: Throwable) {
                sendRolloutNotification(version, description)
            }
            exitProcess(0)
        }
        "push_notification" -> {
            // args[1] - commiter name
            // args[2] - branch name
            // args[3] - commit message
            val commiterName = args[1]
            val branchName = args[2]
            val commitMessage = args[3]
            sendPushNotification(commiterName, branchName, commitMessage)
            exitProcess(0)
        }
        else -> throw IllegalStateException("Can't define bot type!")
    }
}

suspend fun sendPushNotification(commiterName: String, branchName: String, commitMessage: String) {
    val chatId = -359171100
    val text = "*${commiterName}* запушил в *${branchName}*\n```${commitMessage}```"
    getBotService().sendPushNotification(chatId.toString(), text, PARSE_MODE)
}

suspend fun sendRolloutNotification(version: String, description: String) {
    val chatId = -346458703
    val text = "Билд залит! *Версия $version*\n_${description}_"
    getBotService().sendRolloutNotification(chatId.toString(), text, PARSE_MODE)
}

fun getBotService(): BotService =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BotService::class.java)
