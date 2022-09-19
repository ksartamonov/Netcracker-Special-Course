# Отчёт к учебному практическому заданию №1 #
_Работу выполнил Артамонов Кирилл, студент группы Б01-007_
Программный код можно найти на [`GitHub`](https://github.com/ksartamonov/Netcracker-Special-Course/tree/master/PracticalTask1)
## 1. Квадратное уравнение ##
Рассмотрим класс __QuadraticEquation.java__, осуществляющий решения квадратных уравнений:
### 1.1 Поля класса

__PRECISION__ - точность вычислений, будем использовать её для оценки дискриминанта.
```java
private static final double PRECISION = 1e-6;
```
Значения коэффициентов квадратного уравнения:
```java
    private final double coeffA;
    private final double coeffB;
    private final double coeffC;
```
Решения уравнения будем хранить в переменных __String__ для удобства обработки всех корней (действительных и комплексных)
```java
    private String x1, x2;
```

### 1.2 Методы класса

Конструктор класса, которому необходимы все коэффициенты квадратного уравнения:
```java
    public QuadraticEquation(double coeffA, double coeffB, double coeffC) {
        this.coeffA = coeffA;
        this.coeffB = coeffB;
        this.coeffC = coeffC;
    }
```

Далее реализована основная функция решения квадратного уравнения __solveEquation()__, записывающая решения в поля __x1, x2__:
```java
    public void solveEquation() {
        /** Inner class calculating Discriminant */
        class Discriminant {
            private double value;
            public Discriminant() {
                value = calculateValue();
            }
            private double calculateValue() {
                return coeffB * coeffB - 4 * coeffA * coeffC;
            }
        }

        Discriminant d = new Discriminant(); // value is calculated when constructing
        if (d.value > PRECISION) { // real roots
            x1 = String.valueOf((-coeffB - Math.sqrt(d.value)) / (2 * coeffA));
            x2 = String.valueOf((-coeffB + Math.sqrt(d.value)) / (2 * coeffA));
        }
        else if (d.value >= -PRECISION && d.value <= PRECISION) { // one root, second condition is not necessary
            x1 = String.valueOf((-coeffB) / (2 * coeffA));
            x2 = x1;
        }
        else if (d.value < PRECISION) { // complex roots
            double rePart = ((-coeffB) / (2 * coeffA)); // real part of both roots
            double imPart = Math.sqrt((-1) * d.value) / (2 * coeffA); // imaginary multiplier
            x1 = rePart + " + " + imPart + "i";
            x2 = rePart + " - " + imPart + "i";
        }
    }
```

> Дискриминант вычисляется внуренним классом. При компиляции основного класса __QuadraticEquation.java__ создаются 2 файла: файл основного класса __QuadraticEquation.class__ и файл вложенного класса __QuadraticEquation$1Discriminant.class__. Возможно, при компиляции этот класс рассматривается как отдельный класс.

Метод __printSolutions()__ реализованный в данном классе печатает решения уравнения в консоль:
```java
    public void printSolutions() {
        System.out.println("x1 = " + x1);
        if (!x1.equals(x2)) {
            System.out.println("x2 = " + x2);
        }
    }
```

### 1.3 Тестирование класса
Класс __QuadraticEquationTest.java__ позволяет протестировать наш класс:
```java
public class QuadraticEquationTest {
    public static void main(String[] args) {
        try {
            rootsCalculatedCorrectly();
        } catch (TestsNotPassedException e) {
            throw new RuntimeException(e);
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the coefficients of the equation ax^2 + bx + c = 0:");
        System.out.print("a = ");
        double a = sc.nextDouble();
        System.out.print("b = ");
        double b = sc.nextDouble();
        System.out.print("c = ");
        double c = sc.nextDouble();

        System.out.println("Solving the equation: " + a + "x^2 + " + b + "x  + " + c + " = 0" );

        QuadraticEquation eq = new QuadraticEquation(a, b, c);
        eq.solveEquation();
        eq.printSolutions();
    }
```

Помимо метода __main()__ в него добавлены небольшие  тесты вычисления корней уравнения:
```java
    public static void rootsCalculatedCorrectly() throws TestsNotPassedException {
        System.out.println("----------------------------------");
        System.out.println("[test] Running tests:");

        int nTests = 3;
        double[][] testData = {{1, 2, 2}, {1, 2, 1}, {1, 3, -4}};
        String[][] testAnswers = {{"-1.0 + 1.0i", "-1.0 - 1.0i"}, {"-1.0", "-1.0"}, {"-4.0", "1.0"}};

        for (int i = 0; i < nTests; i++) {
            QuadraticEquation eq = new QuadraticEquation(testData[i][0], testData[i][1], testData[i][2]);
            eq.solveEquation();
            if (!eq.getX1().equals(testAnswers[i][0]) || !eq.getX2().equals(testAnswers[i][1])) {
                throw new TestsNotPassedException("[err] Test № " + i + ": " + testData[i][0] + "x^2 + "
                        + testData[1][i] + "x  + " + testData[2][i] + " = 0 - is not passed\n" +
                        "Waited roots: x1 = " + testAnswers[i][0] + ", x2 = " + testAnswers[i][1] + ";\n" +
                        "Calculated roots: x1 = " + eq.getX1() + ", x2 = " + eq.getX2() + ".");
            }
            System.out.println("[test] Test №" + (i+1) + " passed.");
        }
        System.out.println("[test] All tests passed correctly.");
        System.out.println("----------------------------------");
    }
```

## 2. Игра в кости ##
Класс __DiceGame.java__ с использованием __Player.java__ реализует игру в кости. Рассмотрим класс __Player.java__:

### 2.1 __Player.java__ ###

Класс обладает следующими полями (_см. комментарии_):
```java
    /** Sum of points dropped on the dice in the current throw */
    private int totalPoints;
    /** Number of games won */
    private int gamesWon;
    /** Score on all dices in one cast */
    private int currentThrow;
    /** Player's name */
    private String name;
```

Для создания игрока в конструктор класса нужно передать его имя:
```java
    public Player(String name) {
        this.name = name;
        totalPoints = 0;
        gamesWon = 0;
    }
```

Метод __winThrow()__ вызывается для определенного игрока -- победителя текущего розыгрыша:
```java
    public void winThrow() {
        gamesWon++;
    }
```

Метод __roll(int nDice)__ реализует подбрасывание кубиков с использованием __java.util.Random__:
```java
    public void roll(int nDice) {
        Random rand = new Random();
        currentThrow = nDice * (rand.nextInt(6) + 1); // value is in bound [nDice ; 6*nDice]
    }
```

### 2.1 __DiceGame.java__ ###
Теперь рассмотрим основной класс, реализующий саму игру в кости:

#### Класс обладает следующими полями:
Количество выигранных игр, необходимое для победы
```java
    private final int gamesToWin = 7;
```
Список из всех участников игры:
```java
    private final List<Player> players;
```

Список из победителей игры:
```java
    private List<Player> winners;
```

#### Рассмотрим методы класса:

Конструктор, принимающий на вход количество игральных костей и список с участниками игры: 
```java
    public DiceGame(int nDices, List<Player> players) {
        this.players = players;
        this.nDices = nDices;
        play();
        printResults();
    }
```

Рекурсивный метод __play()__ реализует логику игры, выход из него осщуествляется тогда, когда появляется хотя бы один победитель:
```java
    public List<Player> play() {
        if (!(winners = getWinners()).isEmpty()) { // if there is a winner
            return winners;
        }

        players.forEach(player -> player.roll(nDices)); // all players make a throw

        (players.stream().max(Comparator // finding the player moth most points
                .comparing(Player::getCurrentThrow))
                .stream().collect(Collectors.toList()))// Putting all throw winners to List
                .forEach(Player::winThrow); // incrementing won games amount

        play(); // continue recursively
        return null;
    }
```

Метод __getWinners()__ проверяет, появился ли уже победитель и возвращает список победителей:
```java
    private List<Player> getWinners() {

        return players.stream()
                .filter(player -> player.getGamesWon() == gamesToWin) // finding players who have enough wins
                .collect(Collectors.toList()); // collecting them all into returned list
    }
```

Метод __printResults()__ печатает победителей всей игры:
```java 
    private void printResults() {
        System.out.println("List of winners:");
        if (!winners.isEmpty()) {
            winners.forEach(player -> System.out.println(player.getName()));
        }
    }
```

### 2.3 __DiceGameTest.java__ ###
Этот класс позволяет протестировать работу основного класса __DiceGame.java__:
```java
public class DiceGameTest {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        DiceGame dg = new DiceGame(2, players);
    }
}
```

## 3. Адрес человека ##

Необходимо реализовать массив объектов типа __Person__ и реализовать в нем некоторые функции. Сначала рассмотрим класс __Person.java__:

### 3.1 __Person.java__
Класс обладает следующими полями:
```java
    private Address address;
    private String surname;
    private LocalDate birthDate;
```

Конструктору необходимы все параметры:
```java
    public Person(Address address, String surname, LocalDate birthDate) {

        if (address == null) throw new RuntimeException("[err] address is null");
        if (surname == null) throw new RuntimeException("[err] surname is null");
        if (birthDate == null) throw new RuntimeException("[err] birthDate is null");

        this.address = address;
        this.surname = surname;
        this.birthDate = birthDate;
    }
```

Кроме того, перегрузим метод __toString()__:
```java
    @Override
    public String toString() {
        return "Person{" +
                "address=" + address +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
```

Также добавим геттеры и сеттеры для всех полей.

### 3.2 __Adress.java__
Теперь подробно рассмотрим класс __Address.java__, созданную для упорядовичвания аттрибутов адреса.

Поля метода: 
```java
    private String country;
    private String city;
    private String street;
    private int houseNumber;
```

У клласса есть 2 конструктора. У первого Аргументами конструктора являются все поля, а у второго отсутствует поле __houseNumber__:
```java
    public Address(String country, String city, String street, int houseNumber) {

        if (country == null) throw new RuntimeException("[err] country is null");
        if (city == null) throw new RuntimeException("[err] city is null");
        if (street == null) throw new RuntimeException("[err] street is null");

        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }
```

```java
    public Address(String country, String city, String street) {

        if (country == null) throw new RuntimeException("[err] country is null");
        if (city == null) throw new RuntimeException("[err] city is null");
        if (street == null) throw new RuntimeException("[err] street is null");

        this.country = country;
        this.city = city;
        this.street = street;
    }
```

Также перегрузим метод __toString()__:
```java
    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house=" + houseNumber +
                '}';
    }
```

Также нам понадобится метод __equalsTillStreet()__. Он сравнивает, соответствуют ли 2 адреса одной улице и возвращает __1__ в случае, если совпадают, и __0__ -- если нет:
```java
    public boolean equalsTillStreet(Object obj) {
        if (!(obj instanceof Address))
            return false;

        Address address = (Address) obj;
        return address.getCountry().equals(this.country) &&
                address.getCity().equals(this.city) &&
                address.getStreet().equals(this.street);
    }
```

### 3.3 __PersonsArray.java__

Создадим отдельный класс, который будет реализовывать массив элементов __Person__, в этом же классе будут определены методы, указанные в задании.

Единственным полем класса __PersonsArray.java__ будет являться массив (согласно заданию):
```java
 private Person[] persons;
```

Теперь рассмотрим методы класса:

Конструктор принимает на вход количество элементов в массиве: 
```java
    public PersonsArray(int arraySize) {
        persons = new Person[arraySize];
    }
```

Далее опишем методы, указанные в задании. Метод __searchBySurname(String surname)__ выполняет поиск человека по имени и возвращает массив __Person[]__ людей с этой фамилией:
```java
    public Person[] searchBySurname(String surname) {
        return castToPersonsArray(Arrays.stream(persons)
                        .filter(person -> person.getSurname().equals(surname)) //filtering array by surname
                .collect(Collectors.toList()));
    }
```

Метод __searchByAddressAttribute(String[] addressAttributes)__ выполняет поиск людей по аттрибутам адреса, которые, в свою очередь передаются массивом строк:
```java
    public Person[] searchByAddressAttribute(String[] addressAttributes) {
        List<String> addressAttributesList = List.of(addressAttributes);

        return castToPersonsArray(Arrays.stream(persons)
        .filter(person ->
                        addressAttributesList.contains(person.getAdress().getCountry()) || // checking all possible matches
                            addressAttributesList.contains(person.getAdress().getCity()) ||
                                addressAttributesList.contains(person.getAdress().getStreet()) ||
                                    addressAttributesList.contains(String.valueOf(person.getAdress().getHouseNumber()))
                )
                .collect(Collectors.toList())); // putting all matches to list
    }
```
Метод __searchBornInThePeriod(LocalDate periodBegin, LocalDate periodEnd)__ выполняет поиск людей, родившихся в определенный период времени, начало и конец которого передаются в аргументах.
> Если аргументы переданы в некорректном порядке (начало периода позже, чем его окончание), то функция автоматически расставляет даты в правильном порядке и выводит в консоль предупреждение.

```java
    public Person[] searchBornInThePeriod(LocalDate periodBegin, LocalDate periodEnd) {

        if (periodBegin.isAfter(periodEnd)) {   //incorrect order of arguments
            System.err.println("[warning] periodBegin is later than periodEnd, swapping the values.");
            LocalDate tmp = periodBegin;
            periodBegin = periodEnd;
            periodEnd = tmp;
        }
        LocalDate finalPeriodBegin = periodBegin; // to use in lambda expression
        LocalDate finalPeriodEnd = periodEnd;   // to use in lambda expression



        return castToPersonsArray(Arrays.stream(persons)
                .filter(person ->
                        person.getBirthDate().isAfter(finalPeriodBegin) && // Search for fitting persons
                            person.getBirthDate().isBefore(finalPeriodEnd))
                .collect(Collectors.toList())); // put the right people in the list
    }
```

Методы __findOldest()__ и __findYoungest()__ возвращают самых старых и самых молодых людей из нашего массива.
```java
    public Person[] findOldest() {
        LocalDate minDate = Arrays.stream(persons)
                .map(Person::getBirthDate)
                .min(LocalDate::compareTo).get(); // finding minimal date of birth

        return castToPersonsArray(Arrays.stream(persons) // checking if there are more than one born in that day
                .filter(person ->
                        person.getBirthDate().equals(minDate))
                .collect(Collectors.toList()));
    }
```
```java
    public Person[] findYoungest() {
        LocalDate maxDate = Arrays.stream(persons)
                .map(Person::getBirthDate)
                .max(LocalDate::compareTo).get(); // finding max date of birth

        return castToPersonsArray(Arrays.stream(persons) // checking if there are more than one born in that day
                .filter(person ->
                        person.getBirthDate().equals(maxDate))
                .collect(Collectors.toList()));
    }
```

Метод __findNeighbours(Address address)__  возвращает список людей, проживающих на одной улице.
```java
    public Person[] findNeighbours(Address address) {
        return castToPersonsArray(Arrays.stream(persons)
                .filter(person ->
                        address.equalsTillStreet(person.getAdress()))
                .collect(Collectors.toList()));
    }
```
Для добавления элемента в массив на свободную позицию реализован метод __addPerson(Address address, String surname, LocalDate birthDate)__. Он добавляет человека с указанными аттрибутами на свободное положение в массиве, если массив полон -- выбрасывается исключение.
```java
    public Person addPerson(Address address, String surname, LocalDate birthDate) {
        for (int i = 0; i < persons.length; i++) {
            if (persons[i] == null) {
                persons[i] = new Person(address, surname, birthDate);
                return persons[i];
            }
        }

        throw new RuntimeException("Persons array is full!!!");
    }
```
Далее представлены методы для получения элемента  и присваивания элемента массива __PersonsArray__:
```java
    public void setPerson(Person person, int index) {
        persons[index] = person;
    }
```
```java
    public Person getPerson(int index) {
        return persons[index];
    }
```

Служебный метод __castToPersonsArray(List<Person> personList)__ конвертирует список  __List<Person>__ в массив __Person[]__. Его использование обусловлено желанием повысить читаемость кода в некоторых методах.
```java
    private Person[] castToPersonsArray(List<Person> personList) {

        return personList.toArray(new Person[personList.size()]);
    }
```
Также реализован метод вывода массива элементов __Person__ на экран, он позволяет вывести результаты работы любого метода:
```java
    public static void print(Person[] printedArray) {
        Arrays.stream(printedArray).forEach(System.out::println);
    }
```

### 3.3 __PersonsArrayTest.java__
Данный класс предназначен для тестирования основного класса __PersonsArray.java__:
```java
public class PersonsArrayTest {
    public static void main(String[] args) {
        PersonsArray pA = new PersonsArray(4);
        Person person1 = pA.addPerson(new Address("Russia",
                "Moscow", "Street1", 1),
                "Ivanov", LocalDate.of(2002, Calendar.OCTOBER, 13));

        Person person2 = pA.addPerson(new Address("US",
                "NY", "Street3", 2),
                "Bieber", LocalDate.of(2003, Calendar.JULY, 18));

        Person person3 = pA.addPerson(new Address("Germany",
                "Leipzig", "Street3", 3), "Muller",
                LocalDate.of(2006, Calendar.MAY, 5));

        Person person4 = pA.addPerson(new Address("Germany",
                "Leipzig", "Street3", 8), "Diesel",
                LocalDate.of(1998, Calendar.DECEMBER, 17));

//        PersonsArray.print(pA.findYoungest());
//        PersonsArray.print(pA.findOldest());
//        PersonsArray.print(pA.searchBornInThePeriod(LocalDate.of(1997, Calendar.MAY, 5), LocalDate.of(2000, Calendar.MAY, 5)));
//        PersonsArray.print(pA.findNeighbours(new Address("Germany", "Leipzig", "Street3")));
//        PersonsArray.print(pA.searchByAddressAttribute(new String[]{"US", "NY"}));
    }
}
```

>Возможно, лучше было бы реализовать требуемые методы, не создавая собственный класс __PersonsArray__