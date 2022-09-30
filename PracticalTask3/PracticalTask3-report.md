# Отчёт к учебному практическому заданию №3 #
_Работу выполнил Артамонов Кирилл, студент группы Б01-007_
Отчёт и программный код можно найти на [`GitHub`](https://github.com/ksartamonov/Netcracker-Special-Course/tree/master/PracticalTask3).

## 1. Генератор SQL-инструкций. ##
Необходимо разработать программу, формирующую инструкцию SQL, которая вставляет строки в таблицу __T_GroupSelected <id_Student, firstName, lastName, id_Group>__ из таблицы __T_Student<id_Student, firstName, lastName, id_Group, dolgCount >__ тех студентов, которые относятся к некоторой группе (строковый параметр `group`) и количество долгов (целочисленный параметр) которых превышает заданное значение `nDebts`.
Для создания запррсов будем использовать класс __StringBuilder__(_О его эффективности см. №2_). Реализация метода:
```java
    public static String InsertGenerate(String group, int nDebts) {
        StringBuilder strB = new StringBuilder();
        strB.append("INSERT INTO T_GroupSelected\n")
                .append("(id_Student, firstName, lastName, id_Group)\n")
                .append("VALUES\n")
                .append("id_Student, firstName, lastName, id_Group\n")
                .append("WHERE " + "id_Group = '")
                .append(group).append("' AND dolgCount > ")
                .append(nDebts)
                .append(";");
        return new String(strB);
    }
```

## 2. Класс  Person.
Вновь необходимо разработать класс __Person__, в котором имеется функция, возвращающая __Фамилию И.О__. необходимо оптимизировать программу с точки зрения быстродействия. 

Для начала реализуем конструкторы класса, принимающие различное число аргументов. Отметим также, что нет конструктора, принимающего на вход отчество без имени:
```java
public Person(String lastName, String name, String middleName) {
        this.lastName = lastName;
        this.name = name;
        this.middleName = middleName;
    }

    public Person(String lastName, String name) {
        this.lastName = lastName;
        this.name = name;
        this.middleName = null;
    }

    public Person(String lastName) {
        this.lastName = lastName;
        this.name = null;
        this.middleName = null;
    }
```

Для начала реализуем требуемый метод, который будет основан на конкатенации строк. В названии сразу отметим его неэффективность:)
```java
    public String slowToString() {
        String str = "";
        str += lastName;

        if (name != null) {
            str += " " + name.charAt(0) + ".";

            if (middleName != null) {
                str += " " + middleName.charAt(0) + ".";
            }
        }
        return str;
    }
```

Переопределим метод __toString()__, который и будет возвращать __Фамилию И.О__ в корректном формате:
```java
    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();
        strB.append(lastName);

        if (name != null) {
            strB.append(" ")
                    .append(name.charAt(0))
                    .append(".");
            if (middleName != null) {
                strB.append(" ")
                        .append(middleName.charAt(0))
                        .append(".");
            }
        }
        return new String(strB);
    }
```

Оценим эффективность по времени обоих методов. Для этого используем метод __System.nanoTime()__.Рассмотрим класс __PersonTest__:
```java
public class PersonTest {
    public static void main(String[] args) {
        Person p1 = new Person("Ivanov", "Ivan", "Ivanovich");
        Person p2 = new Person("Justin", "Bieber");
        Person p3 = new Person("Viktorov");

        long startTime = System.nanoTime();
        System.out.println(p1.toString());
        System.out.println(p2.toString());
        System.out.println(p3.toString());
        long time = System.nanoTime() - startTime;
        System.out.println("Running time[using StringBuilder]: " + time + " ns.");
        
        startTime = System.nanoTime();
        System.out.println(p1.slowToString());
        System.out.println(p2.slowToString());
        System.out.println(p3.slowToString());
        time = System.nanoTime() - startTime;
        System.out.println("Running time[without StringBuilder]: " + time + " ns.");
    }
}
```

Оценим время работы по выводу в консоли:
```sh
Running time[using StringBuilder]: 229350 ns.
Running time[without StringBuilder]: 28794134 ns.
```

>Как мы видим, метод, использующий __StringBuilder__  работает быстрее в __≈126__ раз даже на таком маленьком объеме данных.

## 3. Класс Adress.

Необходимо доработать класс __Adress.java__, реализовав метод, который из строки извлекает аттрибуты адреса. Необходимо предусмотреть 2 реализации метода. Кроме того, в начале и конце разобранной части адреса не должно быть пробелов --- для этого будем использовать метод __trim()__ в обоих реализацих требуемой функции. Будем также проверять на корректность введенного адреса --- он должен содержать все строки.
Начнем с реализации с использованием метода __split()__:
```java
    public void parseAddressAttributesSplit(String address) {
        String[] addressAttributes = address.split(",");

        if (addressAttributes.length != 7) {
            throw new IllegalArgumentException("[exception] String must contain all 7 address attributes.");
        }

        country = addressAttributes[0].trim();
        region  = addressAttributes[1].trim();
        city = addressAttributes[2].trim();
        street = addressAttributes[3].trim();
        house = addressAttributes[4].trim();
        building = addressAttributes[5].trim();
        flat = addressAttributes[6].trim();
    }
```
Реализация метода, с использованием __String Tikenizer__:
```java
    public void parseAddressAttributesStringTokenizer(String address) {
        StringTokenizer st = new StringTokenizer(address, ",.;-");

        if (st.countTokens() != 7) {
            throw new IllegalArgumentException("[exception] String must contain all 7 address attributes.");
        }

        country = st.nextToken().trim();
        region = st.nextToken().trim();
        city = st.nextToken().trim();
        street = st.nextToken().trim();
        house = st.nextToken().trim();
        building = st.nextToken().trim();
        flat = st.nextToken().trim();
    }
```

## 4. Класс Shirt.
Необходимо реализовать следующий класс:
![Shirt](https://github.com/ksartamonov/Netcracker-Special-Course/blob/master/PracticalTask3/pictures/shirt-diagram.png)


Метод __toString()__  должен выводить объяснение и значения полей построчно. 
Рассмотрим методы класса __Shirt.java__:
Конструктор, принимающий на вход строку, из которой получает характеристики футболки. Опять используем __StringTokenizer__:
```java
    public Shirt(String info) {
        StringTokenizer st = new StringTokenizer(info, ",");

        if (st.countTokens() != 4) {
            throw new IllegalArgumentException("[exception] String must contain all 4 shirt attributes.");
        }
        id = st.nextToken().trim();
        description = st.nextToken().trim();
        color = st.nextToken().trim();
        size = st.nextToken().trim();
    }
```

Переопределим метод __toString()__:
```java
    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder()
                .append("id = '")
                .append(id)
                .append("'\n")
                .append("description = '")
                .append(description)
                .append("'\n")
                .append("color = '")
                .append(color)
                .append("'\n")
                .append("size = '")
                .append(size)
                .append("'\n");
        return new String(strB);
    }
```

Необзодимо преобразовать строковый массив в массив класса __Shirt__:
```java
public class ShirtTest {
    public static void main(String[] args) {
        String[] shirts = new String[11];
        shirts[0] = "S001,Black Polo Shirt,Black,XL";
        shirts[1] = "S002,Black Polo Shirt,Black,L";
        shirts[2] = "S003,Blue Polo Shirt,Blue,XL";
        shirts[3] = "S004,Blue Polo Shirt,Blue,M";
        shirts[4] = "S005,Tan Polo Shirt,Tan,XL";
        shirts[5] = "S006,Black T-Shirt,Black,XL";
        shirts[6] = "S007,White T-Shirt,White,XL";
        shirts[7] = "S008,White T-Shirt,White,L";
        shirts[8] = "S009,Green T-Shirt,Green,S";
        shirts[9] = "S010,Orange T-Shirt,Orange,S";
        shirts[10] = "S011,Maroon Polo Shirt,Maroon,S";

        Shirt[] shirtsArr = new Shirt[11];
        for (int i = 0; i < shirts.length; i++) {
            shirtsArr[i] = new Shirt(shirts[i]);
            System.out.println(shirtsArr[i]);
        }

    }
}
```

## 5. Телефонный номер
Необходимо разработать класс (__TelephoneNumber__), который получает строковое представление телефонного номера в одном из двух возможных строковых форматов:
    `+<Код страны><Номер 10 цифр>`
    `8<Номер 10 цифр>` (для России)
и преобразует полученную строку в формат:
`+<Код страны><Три цифры>–<Три цифры>–<Четыре цифры>`

Класс содержит следующие поля:
```java
    private final String code;
    private final String sub_number; // subscriber's number
```

Реализуем конструктор, принимающий на вход строку --- номер телефона. Используем регулярные выражения:
```java
    public TelephoneNumber(String number) {
        Pattern numberPattern = Pattern.compile("(\\+(\\d+)|8)((\\d){10})");
        Matcher numberMatcher = numberPattern.matcher(number);

        if (numberMatcher.matches()) { // if pattern is found
            if (numberMatcher.group(2) != null) { // if number contains regioncode
                code = numberMatcher.group(2);
            } else {
                code = "7"; // if Russian number
            }
            sub_number = numberMatcher.group(3);
        } else {
            throw new IllegalArgumentException("Incorrect Phone Number");
        }
    }
```

Для корректного вывода номера телефона переопределим метод __toString()__:
```java
    @Override
    public String toString() {
        StringBuilder strB =  new StringBuilder("+");
        return new String(strB.append(code)
                .append(sub_number.substring(0, 3))
                .append("-")
                .append(sub_number.substring(3, 6))
                .append("-")
                .append(sub_number.substring(6)));
    }
```




