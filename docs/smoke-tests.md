# Smoke Tests

Этот файл собирает в одном месте smoke-проверки, которые выполнялись в рамках доработок проекта.

## Подготовка

```bash
./mvnw test
./mvnw -DskipTests package
```

Ожидаемо:
- `BUILD SUCCESS`
- в тестах `Tests run: 69, Failures: 0, Errors: 0, Skipped: 0`

## 1) Write mode: короткие значения A/W

### 1.1 A

```bash
printf '%s\n' '--model=CARS --write-mode=A' 'Q' | java -jar ../target/FinalJavaProject-1.5.jar
```

Ожидаемо:
- Команда принимается без validation error.
- Приложение не падает.

### 1.2 W

```bash
printf '%s\n' '--model=CARS --write-mode=W' 'Q' | java -jar ../target/FinalJavaProject-1.5.jar
```

Ожидаемо:
- Команда принимается без validation error.
- Приложение не падает.

## 2) Import: невалидный путь

```bash
printf '%s\n' '--model=CARS --import=/tmp/nonexistent.json' 'Q' | java -jar ../target/FinalJavaProject-1.5.jar
```

Ожидаемо:
- Выводится validation error про невалидный путь импорта.
- Приложение не падает со stacktrace.

## 3) Import без предварительного create

```bash
printf '[{"power":120,"model":"TestCarA","productionYear":2010},{"power":180,"model":"TestCarB","productionYear":2015}]\n' > /tmp/cars_import_test.json
printf '%s\n' '--model=CARS --import=/tmp/cars_import_test.json' '--model=CARS --display' 'Q' | java -jar ../target/FinalJavaProject-1.5.jar
```

Ожидаемо:
- Импорт проходит.
- `display` печатает 2 машины `TestCarA` и `TestCarB`.

## 4) Create: отрицательная длина

```bash
printf '%s\n' '--model=CARS --create=-1 --display' 'Q' | java -jar ../target/FinalJavaProject-1.5.jar
```

Ожидаемо:
- Validation error: `create` должен быть положительным целым.
- Приложение не создаёт коллекцию.

## 5) Manual input: базовый сценарий

```bash
printf '%s\n' '--model=CARS --manual=1 --display' 'ManualCar' '150' '2018' 'Q' | java -jar ../target/FinalJavaProject-1.5.jar
```

Ожидаемо:
- Приложение запрашивает поля машины.
- После ввода `display` показывает машину `ManualCar`, `150`, `2018`.

## 6.1) Multithread counter из CLI (manual + count)

```bash
printf '%s\n' '--model=CARS --manual=3 --count=2' 'CarA' '100' '2015' 'CarB' '120' '2018' 'CarA' '100' '2015' 'ARRAY' 'CarA' '100' '2015' 'Q' | java -jar ../target/FinalJavaProject-1.5.jar
```

Ожидаемо:
- После ручного ввода 3 машин приложение спросит метод: `ARRAY|SUBLIST|STREAM`.
- После ввода target-объекта выведет `Occurrences found: 2` (или локализованный вариант).

## 6.2) Дополнительный check для count
```bash
printf '%s\n' '--model=CARS --manual=3 --count=2' 'CarA' '100' '2015' 'CarB' '120' '2018' 'CarA' '100' '2015' 'SUBLIST' 'CarA' '100' '2015' 'Q' | java -jar ../target/FinalJavaProject-1.5.jar
```

Ожидаемо:
- Команда завершается без падений.
- Результат совпадает с ожидаемым числом вхождений.

## 6.3) Multithread counter из CLI (manual + count) с методом STREAM
```bash
printf '%s\n' '--model=CARS --manual=3 --count=2' 'CarA' '100' '2015' 'CarB' '120' '2018' 'CarA' '100' '2015' 'STREAM' 'CarA' '100' '2015' 'Q' | java -jar ../target/FinalJavaProject-1.5.jar
```

Ожидаемо:
- Команда завершается без падений.
- Результат совпадает с ожидаемым числом вхождений.

---

## Примечания
- Эти smoke-тесты не заменяют unit/integration тесты, а проверяют пользовательский сценарий CLI end-to-end.