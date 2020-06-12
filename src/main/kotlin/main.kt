import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.system.exitProcess

const val BASE_URL = "https://api.telegram.org"
const val PARSE_MODE = "markdown"

const val ANDROID_ROLLOUT_BOT_TOKEN = "983501720:AAE-AH2qamz4GZaEzqelz0i0QZnavvzMDS8"


fun main(args: Array<String>) = runBlocking<Unit> {
    // args[0] - bot type
    when (args[0]) {
        "rollout_build_meetville" -> {
            // args[1] - android build version
            // args[2] - android build description
            val version = args[1]
            var description = ""
            try {
                description = args[2]
                sendRolloutMeetvilleNotification(version, description)
            } catch (ex: Throwable) {
                sendRolloutMeetvilleNotification(version, description)
            }
            exitProcess(0)
        }
        "rollout_build_good_book" -> {
            // args[1] - android build version
            // args[2] - android build description
            val version = args[1]
            var description = ""
            try {
                description = args[2]
                sendRolloutGoodBookNotification(version, description)
            } catch (ex: Throwable) {
                sendRolloutGoodBookNotification(version, description)
            }
            exitProcess(0)
        }
        "rollout_build_wink" -> {
            // args[1] - android build version
            // args[2] - android build description
            val version = args[1]
            var description = ""
            try {
                description = args[2]
                sendRolloutWinkNotification(version, description)
            } catch (ex: Throwable) {
                sendRolloutWinkNotification(version, description)
            }
            exitProcess(0)
        }
        else -> throw IllegalStateException("Can't define bot type!")
    }
}

suspend fun sendRolloutMeetvilleNotification(version: String, description: String) {
    val chatId = -346458703
    val text = "Meetville билд залит! *Версия $version*\n_${description}_"
    getBotService().sendRolloutNotification(chatId.toString(), text, PARSE_MODE)
}

suspend fun sendRolloutGoodBookNotification(version: String, description: String) {
    val chatId = -346458703
    val text = "GoodBook билд залит! *Версия $version*\n_${description}_"
    getBotService().sendRolloutNotification(chatId.toString(), text, PARSE_MODE)
}

suspend fun sendRolloutWinkNotification(version: String, description: String) {
    val chatId = -346458703
    val text = "Wink билд залит! *Версия $version*\n_${description}_"
    getBotService().sendRolloutNotification(chatId.toString(), text, PARSE_MODE)
}

fun getBotService(): BotService =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BotService::class.java)
