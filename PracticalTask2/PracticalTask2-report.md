# Отчёт к учебному практическому заданию №2 #
_Работу выполнил Артамонов Кирилл, студент группы Б01-007_
Программный код можно найти на [`GitHub`](https://github.com/ksartamonov/Netcracker-Special-Course/tree/master/PracticalTask2)
## 1. Говорящие животные ##
Необходимо реализовать следующую диаграмму классов:
![Speaking animals](https://github.com/ksartamonov/Netcracker-Special-Course/blob/master/PracticalTask2/pictures/speaking-animals-diagram.png)

Начнем с описания интерфейса __Voice__:
### 1.1 interface Voice ###
Интерфейс содержит единственный метод __voice()__, который свой для каждого животного.
```java
public interface Voice {
    public void voice();
}
```
Далее опишем классы, реализующие этот интерфейс:
### 1.2 Cat.java, Dog.java, Cow.java
Все методы отличаются только реализацией метода __voice()__
```java
public class Dog implements Voice {
    @Override
    public void voice() {
        System.out.println("Bark!");
    }
}
```
```java
public class Cat implements Voice {
    @Override
    public void voice() {
        System.out.println("Meow!");
    }
}
```
```java
public class Cow implements Voice {
    @Override
    public void voice() {
        System.out.println("Moo!");
    }
}
```

## 2. Игра в кости
Была решена задача реализации игры в кости с использованием интерфейсов и немного изменена логика.
### 2.1 Interface Player
Был создан интерфейс __Player__:
```java
public interface Player {
    int getGamesWon();
    int getCurrentThrow();
    void winThrow();
    void setCurrentThrow(int currentThrow);
    String getName();
}
```

Этот интерфейс реализуют 2 класса: __Human.java__ (имитирующий человека) и __Computer.java__(имитирующий компьютер). Несмотря на то, что в данной задаче реализации методов в этих классах практически не отличаются, для более крупных проектов такая реализация была бы удобнее.

Сначала рассмотрим поля каждого класса. Поля __Human.java__:
```java
    private String name;
    private int gamesWon;
    private int currentThrow;
```
Поле __name__ хранит имя игрока, __gamesWon__--- количество выигранных им партий, __currentThrow__ --- количество очков, выпавшее при данном броске.

Поля класса __Сomputer.java__ отличаются только отсутствием имени у компьютера.
```java
    private int gamesWon;
    private int currentThrow;
```

Теперь рассмотрим реализации методов в обоих классах:
Конструкторы классов: 
```java
    public Human(String name) {
        this.name = name;
        gamesWon = 0;
        currentThrow = 0;
    }
```

```java
    public Computer() {
        gamesWon = 0;
        currentThrow = 0;
    }
```
Метод __getGamesWon()__ возвращает число выигранных игр, это необходимо для дальнейшего определения победителя.
```java
    @Override
    public int getGamesWon() {
        return gamesWon;
    }
```

Метод __getCurrentThrow()__ возвращает количество очков, выпавшее при текущем броске костей.
```java
    @Override
    public int getCurrentThrow() {
        return currentThrow;
    }
```

Метод __winThrow()__ инкрементирует количество выигранных партий каждым участником.
```java
    @Override
    public void winThrow() {
        gamesWon++;
    }
```

Метод __setCurrentThrow(int currentThrow)__ присваивает количество выпавших очков при текущем броске:
```java
    @Override
    public void setCurrentThrow(int currentThrow) {
        this.currentThrow = currentThrow;
    }
```

Реализация методов, описанных выше, совпадает в обоих классах __Human.java__ и __Computer.java__, единственный метод, реализации которого отличаются __getName()__.
Для класса __Computer.java__:
```java
    @Override
    public String getName() {
        return "Computer"; //computers does not have name
    }
```
Для класса __Human.java__:
```java
    public String getName() {
        return name;
    }
```

### 2.2 Dice.java
Помимо использования интерфейса для игрока был создан отдельный класс __Dice.java__, реализующий игральные кости, он содержит в себе только метод подкидывания кубиков:
```java
    public static int roll(int nDice) {
        Random rand = new Random();
        int val = 0;
        for (int i = 0; i < nDice; i++) { // value is in bound [nDice ; 6*nDice]
            val += rand.nextInt(6) + 1;
        }
        return val;
    }
```

### 2.3 Game.java
Класс __Game.java__ реализует непосредственно саму игру. 
Класс обладает следующими полями:
```java
    private final int GAMES_TO_WIN = 7;
    private final int nDices;
    private List<Player> players;
    private Player winner;
```
Постоянная __GAMES_TO_WIN__ ---количество побед в партиях, которое необходимо для победы в игре. 
Постоянная __nDices__ --- количество кубиков, которые подкидывают участники.
Список __List<Player> players__ --- хранит в себе всех игроков.
Переменная __winner__ хранит победителя игры.

Рассмотрим методы данного класса:

Конструктор после добавления всех игроков автоматически добавляет в список игроков игрока __Computer__:
```java
    public Game(int nDices, List<Player> players) {
        this.players = players;
        this.players.add(new Computer());
        this.nDices = nDices;
        play();
        printResults();
```

Метод __play()__ реализует основную логику игры в кости:
```java
    public void play() {
        players.forEach(player -> player.setCurrentThrow(Dice.roll(nDices))); // all players make a throw

        (players.stream().max(Comparator // finding the player with most points
                        .comparing(Player::getCurrentThrow))
                .stream().collect(Collectors.toList()))// Putting all throw winners to List
                .forEach(Player::winThrow); // incrementing won games amount

        if ((winner = getWinner()) != null) { // checking if we have a winner already
            return;
        }

        players = players.stream()
                .sorted(Comparator.comparingInt(Player::getCurrentThrow).reversed()) // sort players for next Throw
                .collect(Collectors.toList());

        play(); // continue recursively
    }
```
Порядок бросания кубиков в следующем ходу определяется победителями текущего, это реализовано так:
```java
        players = players.stream()
                .sorted(Comparator.comparingInt(Player::getCurrentThrow).reversed()) // sort players for next Throw
                .collect(Collectors.toList());
```
Метод __getWinner()__ определяет победителя всей игры и возвращает его. В случае, если победителя еще нет --- возвращает `null`:
```java
    private Player getWinner() {
        return players.stream()
                .filter(player -> player.getGamesWon() == GAMES_TO_WIN) // finding the winner if we have
                .findFirst().orElse(null);
    }
```

Метод __printResults()__ печатает в консоль победителя игры:
```java
    private void printResults() {
        System.out.println("Winner of the game: " + winner.getName());
    }
```

### 2.4 DiceGameTest.java
Класс для тестирования игры в кости:
```java
public class DiceGameTest {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Human("Player1"));
        players.add(new Human("Player2"));
        players.add(new Human("Player3"));
        Game gm = new Game(2, players);
    }
}
```
## 3. Extended Class
Необходимо реализовать класс, представленный на изображении:
![Extended class](https://github.com/ksartamonov/Netcracker-Special-Course/blob/master/PracticalTask2/pictures/extended-class-diagram.png)

Реализация класса:
```java
public class ExtendedClass {
    private byte b;
    private int i;
    private double d;
    private String s;

    // ************************************************************************
    // Getters
    // ************************************************************************

    public byte getB() {
        return b;
    }

    public int getI() {
        return i;
    }

    public double getD() {
        return d;
    }

    public String getS() {
        return s;
    }

    // ************************************************************************
    // Setters
    // ************************************************************************

    public void setB(byte b) {
        this.b = b;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setS(String s) {
        this.s = s;
    }

    
    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject == null || getClass() != anObject.getClass()) {
            return false;
        }

        ExtendedClass that = (ExtendedClass) anObject;
        return getB() == that.getB()
                && getI() == that.getI()
                && Double.compare(that.getD(), getD()) == 0
                && Objects.equals(getS(), that.getS());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getB(), getI(), getD(), getS());
    }

    @Override
    public String toString() {
        return "ExtendedClass{" +
                "b = " + b +
                ", i = " + i +
                ", d = " + d +
                ", s = '" + s + '\'' +
                '}';

```

## 4. Персональное задание
Необходимо было создать интерфейс __UsdRubConverter__ с методами __setRate()__, __convertRubToUsd()__ и __convertUsdToRub()__:
```java
public interface UsdRubConverter {
    void setRate(double rate);
    double convertRubToUsd(double rub);
    double convertUsdToRub(double usd);
}
```
Интерфейс был реализован в классе __UsdRubConverterImpl__. Метод __setRate(double rate)__ устанавливает курс доллара к рублю (изначально 60) равным `rate`, методы __convert__ принимают на входе значения в рублях/долларах и возвращают конвертированное в доллары/рубли значение.

__UsdRubConverterImpl.java__:
```java
public class UsdRubConverterImpl implements UsdRubConverter {
    private static double rate = 60;
    
    public void setRate(double rate) {
        this.rate = rate;
    }
    public double convertRubToUsd(double rub) {
        return rub / rate;
    }
    public double convertUsdToRub(double usd) {
        return usd * rate;
    }
}
```
