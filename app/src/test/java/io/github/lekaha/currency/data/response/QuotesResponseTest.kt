package io.github.lekaha.currency.data.response

import com.google.gson.GsonBuilder
import org.amshove.kluent.should
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class QuotesResponseTest {

    private var gson = GsonBuilder().create()

    @Test
    fun `convert JSON to object`() {
        val jsonString = "{\n" +
                "    \"success\": true,\n" +
                "    \"terms\": \"https://currencylayer.com/terms\",\n" +
                "    \"privacy\": \"https://currencylayer.com/privacy\",\n" +
                "    \"timestamp\": 1589560506,\n" +
                "    \"source\": \"USD\",\n" +
                "    \"quotes\": {\n" +
                "        \"USDUSD\": 1,\n" +
                "        \"USDEUR\": 0.92431\n" +
                "    }\n" +
                "}"
        val obj = gson.fromJson(jsonString, QuotesResponse::class.java)
        obj.quotes.shouldNotBeNull()
        obj.should {
            success && terms == "https://currencylayer.com/terms" && quotes!!.size == 2
        }
        obj.quotes!!.shouldContainSame(
            mapOf(
                "USDUSD" to 1.0,
                "USDEUR" to 0.92431
            )
        )
    }

    @Test
    fun `convert JSON to error object`() {
        val jsonString = "{\n" +
                "    \"success\": false,\n" +
                "    \"error\": {\n" +
                "        \"code\": 105,\n" +
                "        \"info\": \"Access Restricted - Your current Subscription Plan does not support Source Currency Switching.\"\n" +
                "    }\n" +
                "}"
        val obj = gson.fromJson(jsonString, QuotesResponse::class.java)
        obj.should {
            !success
        }
        obj.error.shouldNotBeNull()
        obj.error!!.shouldContainSame(
            mapOf(
                "code" to 105.0,
                "info" to "Access Restricted - Your current Subscription Plan does not support Source Currency Switching."
            )
        )
    }
}