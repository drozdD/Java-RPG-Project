# Instrukcja Uruchomienia JavaRPG-Core

## Wymagania

- **Java JDK 17** lub nowsza
- **Maven** (może być wbudowany w IDE)

## Opcja A: Uruchomienie w IDE (Zalecane)

Ponieważ jest to projekt Maven, najłatwiej uruchomić go w środowisku programistycznym takim jak **IntelliJ IDEA** lub **Eclipse**.

1.  Uruchom swoje IDE.
2.  Wybierz opcję **Open** lub **Import Project**.
3.  Wskaż plik `pom.xml` lub główny folder projektu `JavaRPG`.
4.  Poczekaj, aż IDE pobierze zależności (biblioteki Hibernate, H2, Lombok).
5.  Otwórz plik `src/main/java/com/rpg/core/Main.java`.
6.  Kliknij prawym przyciskiem myszy w kodzie i wybierz **Run 'Main.main()'**.

## Opcja B: Uruchomienie z Konsoli (Cmd/PowerShell)

Jeśli masz zainstalowanego Mavena w systemie i dodanego do zmiennej PATH:

1.  Otwórz terminal w folderze projektu (tam gdzie jest `pom.xml`).
2.  Skompiluj projekt:
    ```powershell
    mvn clean compile
    ```
3.  Uruchom aplikację:
    ```powershell
    mvn exec:java -Dexec.mainClass="com.rpg.core.Main"
    ```

## Uwagi

- Baza danych H2 jest typu in-memory, co oznacza, że dane są resetowane przy każdym uruchomieniu aplikacji.
- Jeśli nie możesz uruchomić komendy `mvn`, upewnij się, że Maven jest dodany do zmiennych środowiskowych systemu Windows (PATH), lub skorzystaj z Opcji A.
