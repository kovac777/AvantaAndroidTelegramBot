import response.SendMessageResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BotService {

    @POST("/bot$ANDROID_ROLLOUT_BOT_TOKEN/sendMessage")
    @FormUrlEncoded
    suspend fun sendRolloutNotification(
        @Field("chat_id") chatId: String,
        @Field("text") text: String,
        @Field("parse_mode") parseMode: String
    ): SendMessageResponse

    @POST("/bot$PUSH_NOTIFICATION_BOT_TOKEN/sendMessage")
    @FormUrlEncoded
    suspend fun sendPushNotification(
        @Field("chat_id") chatId: String,
        @Field("text") text: String,
        @Field("parse_mode") parseMode: String
    )

}