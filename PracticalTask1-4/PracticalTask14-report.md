# Отчёт к учебному практическому заданию №4 #
_Работу выполнил Артамонов Кирилл, студент группы Б01-007_

Отчёт и программный код можно найти на [`GitHub`](https://github.com/ksartamonov/Netcracker-Special-Course/tree/master/PracticalTask4).

## 1. Класс  Person.
Вновь необходимо разработать класс __Person__ для хранения даты рождения, в котором имеется метод, возвращающий строковое представление даты рождения по вводимому в метод формату даты.

Для начала реализуем конструкторы класса:
```java
    public Person(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Person() {
    }
```

Реализуем требуемый метод, который будет возвращать дату рождения в требуемом формате. Формат строкки будет регламентироваться [`паттернами для форматирования и парсинга`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html).
```java
    public String getDateInFormat(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return birthDate.format(formatter);
```

## 2. Формирование Date и Calendar.
Необходимо написать код, который формирует объекты __Date__ и __Calendar__ по следующим данным, вводимым пользователем:
     _<Год><Месяц><Число>_
    _<Часы1><минуты>_
Создадим класс со следующими полями:
```java
    private Date date;
    private Calendar calendar;
```

Реализуем требуемый конструктор клаасса:
```java
    public DateCalendar(int year, int month, int day, int hours, int minutes) {
        date = new Date(year, month, day, hours, minutes);
        calendar = new GregorianCalendar(year, month, day, hours, minutes);
    }
```

## 3. Анализ строк
Необходимо провести частотный анализ символов из первой и из второй строки.

Для начала реализуем метод, определяющий символы, принадлежащие обеим строкам6 его результат будем хранить в множестве:
```java
    private final Set<Character> charsInBoth;
```
Реализация метода:
```java
    public void setCharsInBoth(String str1, String str2) {
        for (char c : str1.toCharArray()) {
            if (str2.contains(String.valueOf(c))) {
                charsInBoth.add(c);
            }
        }
    }
```

Реализуем метод, определяющий символы, входящие в первую строку, но не входящие во вторую:
```java
    public void setCharsOnlyInFirst(String str1, String str2) {
        for (char c : str1.toCharArray()) {
            if (!str2.contains(String.valueOf(c))) {
                charsOnlyInFirst.add(c);
            }
        }
    }
```

Этот метод записывает результаты в поле:
```java
    private final Set<Character> charsOnlyInFirst;
```

Имплементируем также метод, определяющий символы, содержащиеся хотя бы в одной строке:
```java
    public void setCharsAtLeastInOne(String str1, String str2) {
        for (char c : str1.toCharArray()) {
            charsAtLeastInOne.add(c);
        }
        for (char c : str2.toCharArray()) {
            charsAtLeastInOne.add(c);
        }
    }
```

Он записывает результат своей работы в поле:
```java
    private final Set<Character> charsAtLeastInOne;
```

Необходимо реализовать три различных вывода:
Метод __static void printInDefaultOrder()__  выводит символы в обычном порядке:
```java
    static void printInDefaultOrder(@NotNull List<Set<Character>> sets) {
        sets.forEach(System.out::println);
    }
```

Метод __static void printInReversedOrder()__  выводит символы в обратном порядке:
```java
    static void printInReversedOrder(@NotNull List<Set<Character>> sets) {
        sets.forEach(characters -> {
            List<Character> setList = new ArrayList<Character>(characters);
            Collections.reverse(setList);
            System.out.println(setList);
        });
    }
```

Метод __static void printInCyclicLeftShiftOrder()__ выводит символы в порядке возрастания циклического сдвига влево на n разрядов хеш-функции символа:
```java
    static void printInCyclicLeftShiftOrder(@NotNull List<Set<Character>> sets, int n) {
        sets.forEach(characters -> {
            List<Character> setList = new ArrayList<Character>(characters);
            Collections.sort(setList, Comparator.comparingInt(o -> leftCyclicShift(o.hashCode(), n)));
            System.out.println(setList);
        });
    }
```

Функция циклического сдвига:
```java
    private static int leftCyclicShift(int value, int n) {
        return (value << (n % 32)) | (value >>> ((n % 32) - 32));
    }
```

## 4. Бинарное дерево
Необходимо реализовать простое бинарное дерево.
### 4.1 Класс Node.java
Для начала создадим класс __Node.java__, который будет представлять узел дерева. Он обладает следующими полями: 
```java
    private Object value;
    private Node left;
    private Node right;
```
Класс обладает следующими конструкторами:
```java
    public Node() {
    }
    
    public Node(Object value) {
        this.value = value;
    }
```

Переопределим также метод __toString()__:
```java
    @Override
    public String toString() {
        return String.valueOf(value);
    }
```

### 4.2 Класс BinaryTree.java
Этот класс будет представлять основную логику бинарного дерева. 
Он обладает одним полем:
```java
    private Node root;
```

Конструкторы класса:
```java
    public BinaryTree() {
        root = null;
    }
    
    public BinaryTree(Node root) {
        this.root = root;
    }
```

Реализуем метод __addElement()__ добавления элемента в дерево:
```java
    public void addElement(Object item) {
        Node newNode = new Node(item);

        if (root == null) {
            root = newNode;
            return;
        }

        insert(root, newNode);
    }
    
    private void insert(Node curNode, Node newNode) {

        if ((Double) newNode.getValue() < (Double) curNode.getValue()) {
            if (curNode.getLeft() == null) {
                curNode.setLeft(newNode);
            } else {
                insert(curNode.getLeft(), newNode);
            }
        }

        if ((Double) newNode.getValue() > (Double) curNode.getValue()) {
            if (curNode.getRight() == null) {
                curNode.setRight(newNode);
            } else {
                insert(curNode.getRight(), newNode);
            }
        }

        if (Objects.equals(newNode.getValue(), curNode.getValue())) {
            System.out.println("[warning] element" + newNode.getValue() +"already exists");
        }
    }
```

Также добавим метод удаления элемента __removeElement()__:
```java
    public void removeElement(Object item) {
        if (this.root != null) {
            this.delete(this.root, item);
        }
    }

    private Node delete(Node curNode, Object item) {
        if (curNode.getValue() == item) {
            if (curNode.getLeft() == null && curNode.getRight() == null) {
                return null;
            }

            if (curNode.getLeft() == null) {
                return curNode.getRight();
            }

            if (curNode.getRight() == null) {
                return curNode.getLeft();
            }

            curNode.setValue(findMinElement(curNode.getRight()));

            curNode.setRight(delete(curNode.getRight(), findMinElement(curNode.getRight()).getValue()));

            return curNode;
        }

        if ((Integer) item < (Integer) curNode.getValue()) {
            if (curNode.getLeft() == null) {
                return curNode; // element not found
            }

            curNode.setLeft(delete(curNode.getLeft(), item));

            return curNode;
        }

        if ((Integer) item > (Integer) curNode.getValue()) {
            if (curNode.getRight() == null) {
                return curNode; // element not found
            }

            curNode.setRight(delete(curNode.getRight(), item));

            return curNode;
        }
        return curNode;
    }
```

Теперь реализуем различные методы обхода дерева:
Прямой метод обхода:
```java
    public List<Node> preorderTraversal() {
        return traverseDLR(root, new LinkedList<Node>());
    }
    
    private List<Node> traverseDLR(Node curNode, List<Node> preorderList) {
        preorderList.add(curNode);
        if (curNode.getLeft() != null) {
            traverseDLR(curNode.getLeft(), preorderList);
        }
        if (curNode.getRight() != null) {
            traverseDLR(curNode.getRight(), preorderList);
        }
        return preorderList;
    }
```
Обратный метод обхода:
```java
    public List<Node> postorderTraversal() {
        return traverseLRD(root, new LinkedList<Node>());
    }
    
    private List<Node> traverseLRD(Node curNode, List<Node> postorderList) {
        if (curNode.getLeft() != null) {
            traverseLRD(curNode.getLeft(), postorderList);
        }
        if (curNode.getRight() != null) {
            traverseLRD(curNode.getRight(), postorderList);
        }
        postorderList.add(curNode);
        return postorderList;
    }
```

Центрированный обход дерева:
```java
    public List<Node> inorderTraversal() { 
        return traverseLDR(root, new LinkedList<Node>());
    }
    
    private List<Node> traverseLDR(Node curNode, List<Node> inorderList) {
        if (curNode.getLeft() != null) {
            traverseLDR(curNode.getLeft(), inorderList);
        }
        inorderList.add(curNode);
        if (curNode.getRight() != null) {
            traverseLDR(curNode.getRight(), inorderList);
        }
        return inorderList;
    }
```

