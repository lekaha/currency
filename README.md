# Currency Conversion

## Android development

 * Entirely written in [Kotlin](https://kotlinlang.org/)
 * Uses [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html)
 * Uses many of the [Architecture Components](https://developer.android.com/topic/libraries/architecture/): Room, LiveData and Lifecycle
 * Uses [koin](https://insert-koin.io/) for dependency injection
 
## Access Key
 
1. Sign up on [Currencylayer](https://currencylayer.com/) and get the access key.
2. Add the key in `local.properties` file like,
```
accessKey="42346b23493229dfsdf2***********"
```

## Build Environment

 * `debug`: Pure offline environment (not sending any Http requests to Remote) with build-in (fake) data.
 * `release`: Connecting the remote service with the access key in Free plan. It might hit no usage quota if use too many times.

## Architecture

Inspired by Clean Architecture, with using Dependency Injection, Kotlin Flow, and Androidx Room. 
 * `data`: Data layer, where includes Remote service and Local cache handed data by DAO class.
 * `domain`: Folder for Business Logic Components (Blocs) where isolates presentation and data layer.
 * `ui`: All presentation views and view controllers here within the folders.
 
## Persistent data

 * Database schemas

```
currency(
  id    text Primary Key,
  name  text 
)

currency_rate(
  id                int Primary Key,
  from_currency_id  text,
  to_currency_id    text,
  rate              float 
)

// join currency_rate and currency
CurrencyRateWithCurrency(
  id                int,
  from_currency_id  text,
  to_currency_id    text,
  rate              float,
  from_id           text,
  from_name         text,
  to_id             text,
  to_name           text 
)
```

 * Share Preference
 - `selected_currency`: store the selected country ID
 - `last_amount`: store the last amount

## Sync Up the latest Currency exchange rate

Using Android's [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) 
with the custom [Worker](https://developer.android.com/reference/kotlin/androidx/work/CoroutineWorker), 
[RefreshCurrencyRatesWork](https://github.com/lekaha/currency/blob/master/app/src/main/java/io/github/lekaha/currency/domain/RefreshCurrencyRatesWork.kt)
periodically fetch the latest currency exchange rate and store in the database. 
 
## Test code

Focus on testing Domain layer's Blocs, verifying the requests from views until fetch data if the logic is correct.

## CI 

Using [Github Action](https://github.com/features/actions) as the project's CI, please find result [here](https://github.com/lekaha/currency/actions)

## Screen capture

![image](assets/screencapture.gif)

License
-------
```
Copyright (c) 2011-2020 lekaha.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```