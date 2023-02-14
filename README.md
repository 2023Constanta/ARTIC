# ARTIC
ARTIC -- мобильное приложение для знакомства с экспонатами Чикагского института исскуств (The Art Institute of Chicago, USA).

# Описание
Приложение обеспечивает
•	иллюстрированный реестр экспонатов и выставок;
•	удобный поиск по названиям, и текстам каталогов музея;
•	проигрывание звуковых сообщений для отдельных экспонатов;
•	покупку и хранение билетов для посещения многочисленных выставок;
•	помощь в регистрации в Google календаре напоминания о времени работы выставки.

# Стек технологий
Информационная составляющая приложения всецело базируется на обширном информационном ресурсе музея Artic API. Архитектура приложения соответствует принципам MVVM.
-	Версия Android SDK 2.
-	Написано на Kotlin.
-	Koin для DI.
-	Библиотеки семейства JetPack:
    -	LiveData
    -	Room
    -	ViewModel
    -	Navigation Component
-	Retrofit для работы с сетью
-	Zxing для работы с QR-кодами

# Лицензия
Designed and developed by 2023Constanta (Tamerlan Mamukhov)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
