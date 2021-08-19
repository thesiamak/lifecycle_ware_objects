package ir.drax.samples.lifecycle.network

import com.google.gson.Gson
import ir.drax.samples.lifecycle.BuildConfig
import ir.drax.samples.lifecycle.network.model.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.time.Instant
import java.util.concurrent.TimeUnit

class MockInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            var requestCode = 200
            val path = chain.request().url.pathSegments.joinToString("/")

            val responseString = when(path) {
                "v1/orders"->{
                    val now = Instant.now()

                    Gson().toJson(
                        ServerResponse(
                        listOf(
                            Order(0,"Large rice", listOf(
                                Addon(21,"Fried Egg",1)
                            ),1,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(0)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(15)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(5)).toString(),
                            ),

                            Order(0,"Second Large rice", listOf(
                                Addon(22,"Second Fried Egg",1),
                                Addon(22,"Second Chicken",5),
                                Addon(22,"Second Salad",2)
                            ),2,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(15)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(30)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(5)).toString(),
                                ),

                            Order(0,"Third Large rice", listOf(
                                Addon(23,"Third Fried Egg",2),
                                Addon(23,"Third Chicken",1),
                            ),7,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(30)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(65)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(6)).toString(),
                                ),

                            Order(0,"Fourth Large rice", listOf(
                                Addon(23,"Fourth Fried Egg",5),
                                Addon(23,"Fourth Salad",2),
                                Addon(23,"Fourth Chicken",1),
                                Addon(23,"Fourth Noodle",1),
                            ),1,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(45)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(75)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(6)).toString(),
                                ),

                            Order(0,"Fifth Large rice", listOf(
                                Addon(23,"Fifth Fried Egg",6)
                            ),3,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(60)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(100)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(6)).toString(),
                                ),

                            Order(0,"Sixth Large rice", listOf(
                                Addon(23,"Sixth Fried Egg",1)
                            ),2,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(85)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(120)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(7)).toString(),
                                ),

                        ), Status(true,requestCode,"success"))
                        )
                }
                "v1/ingredients"->{
                    Gson().toJson(
                        ServerResponse(
                        listOf(
                            Ingredient(System.currentTimeMillis(),"Large rice",1,false,Type.Bento,"https://images.immediate.co.uk/production/volatile/sites/30/2020/08/processed-food700-350-e6d0f0f.jpg?webp=true&quality=90&resize=385%2C350"),
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",5,true,Type.Bento,"https://www.refrigeratedfrozenfood.com/ext/resources/NEW_RD_Website/DefaultImages/default-pasta.jpg?1430942591"),
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",12,false,Type.Bento,"https://images.squarespace-cdn.com/content/v1/5990d645cd0f685c51b8b9b9/1541520115128-KZDFSLHFH8KXCSRA21HJ/pasta2.jpg?format=1500w"),
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",6,true,Type.Bento,"https://www.cidrap.umn.edu/sites/default/files/public/styles/ss_media_popup/public/media/article/pasta.jpg?itok=yWbkRMAF"),
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",2,false,Type.Appetizer,"http://blog.markethallfoods.com/media/zoo/images/MHFoods_pasta_herby_buttered_tomatoes_500x500_b4cfbc1a9b0f9be5a244eb66e6587c14.jpg"),
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",5,true,Type.Appetizer,"https://tastesbetterfromscratch.com/wp-content/uploads/2018/02/Instant-Pot-Tuscan-Chicken-Pasta-8-500x500.jpg"),
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",12,false,Type.Appetizer,"https://foodtasia.com/wp-content/uploads/2019/02/tuscan-pasta-20.jpg"),
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",6,true,Type.Main,"https://midwestfoodieblog.com/wp-content/uploads/2019/11/FINAL-tuscan-chicken-1-2-1-1200x1800.jpg"),
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",2,false,Type.Chicken,"https://tastykitchen.com/recipes/wp-content/uploads/sites/2/2011/08/Penne-with-Creamy-Tomato-Sauce-and-Grilled-Chicken-410x273.jpg"),

                        ), Status(true,requestCode,"success"))
                        )
                }

                "v1/ingredients/search"->{
                    Gson().toJson(
                        ServerResponse(
                        listOf(
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",5,true,Type.Bento,"https://www.refrigeratedfrozenfood.com/ext/resources/NEW_RD_Website/DefaultImages/default-pasta.jpg?1430942591"),
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",12,false,Type.Bento,"https://images.squarespace-cdn.com/content/v1/5990d645cd0f685c51b8b9b9/1541520115128-KZDFSLHFH8KXCSRA21HJ/pasta2.jpg?format=1500w"),
                            Ingredient(System.currentTimeMillis(),"Japanese Fried Rice Katsu",6,true,Type.Main,"https://midwestfoodieblog.com/wp-content/uploads/2019/11/FINAL-tuscan-chicken-1-2-1-1200x1800.jpg"),

                            ), Status(true,requestCode,"success"))
                        )
                }
                else -> "{}"
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(requestCode)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create(
                        "application/json".toMediaTypeOrNull(),
                        responseString.toByteArray()))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " +
                    "bound to be used only with DEBUG mode")
        }
    }

}
