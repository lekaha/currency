package io.github.lekaha.currency.data.response

import com.google.gson.GsonBuilder
import org.amshove.kluent.should
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class CurrenciesResponseTest {

    var gson = GsonBuilder().create()

    @Test
    fun `convert JSON to object`() {
        val jsonString = "{\n" +
                "    \"success\": true,\n" +
                "    \"terms\": \"https://currencylayer.com/terms\",\n" +
                "    \"privacy\": \"https://currencylayer.com/privacy\",\n" +
                "    \"currencies\": {\n" +
                "        \"AED\": \"United Arab Emirates Dirham\",\n" +
                "        \"AFN\": \"Afghan Afghani\",\n" +
                "        \"ALL\": \"Albanian Lek\",\n" +
                "        \"AMD\": \"Armenian Dram\",\n" +
                "        \"ANG\": \"Netherlands Antillean Guilder\",\n" +
                "        \"AOA\": \"Angolan Kwanza\",\n" +
                "        \"ARS\": \"Argentine Peso\",\n" +
                "        \"AUD\": \"Australian Dollar\",\n" +
                "        \"AWG\": \"Aruban Florin\",\n" +
                "        \"AZN\": \"Azerbaijani Manat\",\n" +
                "        \"BAM\": \"Bosnia-Herzegovina Convertible Mark\",\n" +
                "        \"BBD\": \"Barbadian Dollar\",\n" +
                "        \"BDT\": \"Bangladeshi Taka\",\n" +
                "        \"BGN\": \"Bulgarian Lev\",\n" +
                "        \"BHD\": \"Bahraini Dinar\",\n" +
                "        \"BIF\": \"Burundian Franc\",\n" +
                "        \"BMD\": \"Bermudan Dollar\",\n" +
                "        \"BND\": \"Brunei Dollar\"\n" +
                "    }\n" +
                "}\n"
        val obj = gson.fromJson(jsonString, CurrenciesResponse::class.java)
        obj.currencies.shouldNotBeNull()
        obj.should {
            success && terms == "https://currencylayer.com/terms" && currencies!!.size == 18
        }
    }

}