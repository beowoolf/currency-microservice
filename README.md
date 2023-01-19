# currency-microservice

Usługa napisana w Spring Boot, która pod adresem /currency/{code} zwraca na sztywno wartość podanej waluty o podanym w
adresie URL kodzie.

Dostępne są waluty: {code} = PLN, USD, EUR

Dla przykładu wywołanie:

1. /currency/PLN zwraca wartość 3,55
2. /currency/USD zwraca wartość 5,55
3. /currency/EUR zwraca wartość 4,55

Dla uproszczenia kodu wartości są zapisane na sztywno w mapie, a w przypadku braku waluty o danym kodzie jest wykonywana
obsługa wyjątku.

Jeśli wpisze się kod waluty, która nie istnieje w aplikacji robiąc jedno z tych przykładowych wywołań:

- /currency/CHF
- /currency/xyz
- /currency/ADASDcc

wtedy zostaje obsłużony wyjątek i mikroserwis zamiast zwrócić wartości tej waluty zwraca wiadomość, że taki {code} nie
istnieje.
