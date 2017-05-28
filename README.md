# Analizator Bilansu


## Opis
<p align="center">
  <img src="https://github.com/Aeszma/Analizator_Bilansu/blob/master/Screens/1ab.png" width="350"/>
</p>

Aplikacja przechowuje dane o firmie, a jej bilans musi być zgodny z ustawą o rachunkowości.
Przedsiębiorstwo musi także wskazać rodzaj swojej działalności(produkcyjna, handlowa, usługowa) i jej formę prawną.
Na postawie tych danych dokonywana jest analiza pionowa i pozioma bilansu. 
Obliczone wartości i wprowadzone dane firmy przechowywane są w bazie danych i możliwe jest ich porównywanie w kolejnych latach, aby móc obserwować zmianę sytuacji przedsiębiorstwa na przestrzeni kolejnych okresów.

## Wykaz dostęnych funkcjonalności

<p align="center">
  <img src="https://github.com/Aeszma/Analizator_Bilansu/blob/master/Screens/2buttony.png" width="350"/>
</p>

 
1. Bilans - umożliwia wprowadzenie nazwy firmy, jej rodzaju działalności oraz roku, za który chcemy przeprowadzić analizę bilansu. Użytkownik wypełnia także bilans analityczny, którego wartości pozwolą na przeprowadzenie analizy wskaźnikowej.

2. Analiza - pozwala wybrać rodzaj analizy blansu: pionową lub poziomą. 
<p align="center">
  <img src="https://github.com/Aeszma/Analizator_Bilansu/blob/master/Screens/Screenshot_1495985531.png" width="350"/>
</p>

Analiza pionowa pozwala na badanie procentowego udziału poszczególnych pozycji składników aktywów i pasywów w ogólnej sumie bilansowej. W analizie tej obliczane są wskaźniki struktury, czyli udział poszczególnych części w całości badanego zjawiska.
Analiza pionowa oblicza wskaźniki struktury: 
- aktywów trwałych,
- aktywów obrotowych,
- aktywów ogółem,
- kapitałów własnych,
- kapitałów obcych,
- źródeł finansowania,
- pokrycia zobowiązań.

Analiza pozioma polega na porównaniu w czasie poszczególnych pozycji bilansu. W analizie tej oblicza się wskaźniki dynamiki i wskaźniki tempa zmian.
Analiza pozioma oblicza wskaźniki: 
- dynamiki aktywów trwałych,
- dynamiki aktywów obrotowych,
- dynamiki aktywów ogółem,
- dynamiki kapitałów własnych,
- dynamiki kapitałów obcych,
- dynamiki pasyw ogółem,
- tempa zmian aktywów trwałych,
- tempa zmian aktywów obrotowych,
- tempa zmian aktywów ogółem,
- tempa zmian kapitałów własnych,
- tempa zmian kapitałów obcych,
- tempa zmian pasyw ogółem,
- pokrycia aktywów trwałych kapitałem własnym,
- pokrycia aktywów obrotowych kapitałem obcym.

3. Wyniki - pozwala użytkownikowi na wgląd w wyniki na podstawie wskazanego przez niego roku obrotowego.


## Dokumentacja

### Wykorzystane pojęcia/klasy oraz ich opis. 
W programie użyto 12 klas. Każda z klas posiada swóją metodę onCreate() oraz metody odpowiedzialne za przejścia między poszczególnymi layoutami.
Aplikacja zawiera następujące klasy: 
<p align="center">
  <img src="https://github.com/Aeszma/Analizator_Bilansu/blob/master/Screens/Screenshot_1495985429.png" width="350"/>
</p>

* AktywaObrotowe - klasa ta dziedziczy z AppCompatActivity, posiada ona dwie główne funkcje:

```
public void wyliczAktywaTrwale(View view) {
	//odpowiada za wyliczynie sum aktyw trwałych, obrotowych oraz aktyw ogółem na podstawie danych wprowadzonych przez użytkownika.
	...
}
```

oraz

```
public void zapiszAktywaTrwale(View view) {
	//przekazuje poszczególne wyniki do bazy danych znajdującej się na serwerze v-ie.uek.krakow.pl
	...
}
```

* AktywaTrwale - klasa ta dziedziczy z AppCompatActivity, posiada ona dwie główne funkcje:

```
public void wyliczAktywaObrotowe(View view) {
	//odpowiada za wyliczynie sum aktyw trwałych, obrotowych oraz aktyw ogółem na podstawie danych wprowadzonych przez użytkownika.
	...
}
```

oraz

```
public void zapiszAktywaObrotowe(View view) {
	//przekazuje poszczególne wyniki do bazy danych znajdującej się na serwerze v-ie.uek.krakow.pl
	...
}
```

* Analiza - klasa ta dziedziczy z AppCompatActivity, posiada ona trzy główne funkcje:
<p align="center">
  <img src="https://github.com/Aeszma/Analizator_Bilansu/blob/master/Screens/Screenshot_1495985534.png" width="350"/>
</p>

```
public void pobierz(final String nazwa, final float[] tabela) {
	//odpowiada za pobranie danych z serwera
	...
}
```

oraz

```
//metody działające na podstawie pobranych danych z funkcji pobierz() liczy wzkaźniki analizy poziomej lub pionowej
public void przejdzAnalizaPionowa(View view) {
	...
}
public void przejdzAnalizaPozioma(View view) {
	...
}
```

* Bilans - klasa ta dziedziczy z AppCompatActivity, pozwala na wybranie profilu działalności przedsiębiorstwa.
* DatabaseHandler - klasa ta dziedziczy z SQLiteOpenHelper. Odpowiedzialna za obsługę bazy danych. 
* DatabaseHandlerRok - klasa ta dziedziczy z SQLiteOpenHelper. Obsługuje bazę danych poszczególnych lat, któe później są wykorzystane do oblicznia wyników.
* MainActivity - klasa uruchamiana na początku działania aplikacji.
* Menu - klasa ta dziedziczy z AppCompatActivity, pozwala na wybranie jednej z pośród trzech funkcjonalności aplikacji:  
* Pasywa - klasa ta dziedziczy z AppCompatActivity, posiada ona dwie główne funkcje:

```
public void wyliczPasywa(View view) {
	//odpowiada za wyliczynie sum aktyw trwałych, obrotowych oraz aktyw ogółem na podstawie danych wprowadzonych przez użytkownika.
	...
}
```

oraz

```
public void zapiszPasywa(View view) {
	//przekazuje poszczególne wyniki do bazy danych znajdującej się na serwerze v-ie.uek.krakow.pl
	...
}
```
* Rok - klasa pozwala na stworzenie instancji danego roku, posiada metody do ustawiania oraz pobierania roku.
* Wskaźniki - klasa pozwala na stworzenie instancji wskaźnika, posiada metody do ustawiania oraz pobierania wskaźnika.
* Wyniki - klasa ta dziedziczy z AppCompatActivity, klasa posiada 6 głównych metod:
<p align="center">
  <img src="https://github.com/Aeszma/Analizator_Bilansu/blob/master/Screens/Screenshot_1495985547.png" width="350"/>
</p>

```
 public void dodajElementyDoSpinner() {};
 public void przejdzDoWynikow(View view) {//na podstawie podanego przez użytkownika roku, pobiera dane o wskaźnikach przechowywane w bazie lokalnej};
 public void okAnalizaPionowa(View view) {};
 public void przejdzWskTempaZmian(View view) {};
 public void przejdzWskPokrycia(View view) {};
 public void okAnalizaPozioma(View view) {};
```

## Autorzy

* **Barbara Czaicka** - 186128 
* **Dorota Czaicka** - 186129 

Grupa: KrDZIs3011Io

## Informacje

Projekt przygotowany w ramach przedmiotu "Programowanie systemów mobilnych." 
Uniwersytet Ekonomiczny w Krakowie, maj 2017r.

