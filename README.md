# Ćwiczenie nr 2:

https://www.wykop.pl/wpis/30787895/zadanie-rekrutacyjne-no-2-https-notehub-org-r6blk-/
https://notehub.org/r6blk

### Wprowadzenie

Otrzymałeś zlecenie na napisanie systemu wyszukiwania połączeń pomiędzy
miastami od nowego przewoźnika kolejowego. System musi wyszukać
najkrótszą drogę pomiędzy dwoma miastami. Zleceniodawcy zależy, aby
wyszukiwanie było jak najszybsze.

Zadanie
-------

1.  Napisz serwis API zgodnie z dokumentacją,
2.  Napisz testy automatyczne dla obu endpointów,
3.  Zadanie może zostać wykonane z użyciem dowolnej technologii,
4.  Użyj Gita do udokumentowania historii projektu,
5.  Logowanie nie jest wymagane

Dokumentacja
============

Add train
---------

Endpoint do dodawania połączenia kolejowego. Dodanie połączenia Wrocław
-\> Warszawa **nie** oznacza, że istnieje połączenie Warszawa -\>
Wrocław.

**HTTP REQUEST** `POST http://example.com/api/trains` **JSON Payload:**

    {
      "train": ["Poznań", "Wadowice"]
    }

**Walidacje:**

-   Trasa jest unikalna - nie można dodać dwa razy tej samej,
-   Nie można dodać trasy A-\>A. W każdym scenariuszu serwer powinien
    zwrócić odpowiedni status HTTP.

* * * * *

Find shortest route
-------------------

Endpoint do wyszukiwania najkrótszej trasy pomiędzy dwoma miastami.
Zwraca wiele tras jeśli mają tę samą długość.

**HTTP REQUEST**
`GET http://example.com/api/shortest_route?start=START&destination=DESTINATION`

**Request params:**

    {
      "start": "Poznań",
      "destination": "Wadowice"
    }

**JSON Response:**

    {
      "routes": [
        [
          "Poznań",
          "Wrocław",
          "Wadowice"
        ],
        [
          "Poznań",
          "Łódź",
          "Wadowice"
        ]
      ],
      "distance": 2
    }

Jeśli połączenie między miastami nie istnieje, aplikacja zwraca status
404.

### Dodatkowe punkty za:

-   jak najlepiej zoptymalizowany algorytm (szybkość i potrzebne
    zasoby),
-   implementację systemu cachingu (dla ułatwienia cache kasowany przy
    dodaniu pociągu)
-   użycie nierelacyjnej bazy danych
