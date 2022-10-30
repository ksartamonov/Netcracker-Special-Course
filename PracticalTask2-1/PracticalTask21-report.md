# Отчёт к учебному практическому заданию №2.1 #
_Работу выполнил Артамонов Кирилл, студент группы Б01-007_

Отчёт и программный код можно найти на [`GitHub`](https://github.com/ksartamonov/Netcracker-Special-Course/tree/master/PracticalTask2-1).

## 1. Класс Vectors
Необходимо создать класс __Vectors__, содержащий статические методы работы с векторами.
Класс обладает следующими полями:
```java
    private final int dim; // Vector's dimension
    private final List<Double> components; // vector's components
```

Добавим несколько конструкторов класса:
```java
    public Vectors(List<Double> components) {
        this.dim = components.size();
        this.components = components;
    }
    
    public Vectors(Double[] components) {
        this.dim = components.length;
        this.components = Arrays.asList(components);
    }
```
Реализуем метод умножения вектора на скаляр __multiplyByScalar()__:
```java
    public static Vectors multiplyByScalar(double c, Vectors v1) {
        List<Double> newComponents = v1.getComponents().stream().map(aDouble -> aDouble * c).toList();
        return new Vectors(newComponents);
    }
```

Метод сложения двух векторов, отметим, что складывать можно только вектора одинаковой размерности:
```java
    public static Vectors sum(Vectors v1, Vectors v2) {
        if (v1.getDim() == v2.getDim()) {
            List<Double> newComponents = new ArrayList<>();

            for (int i = 0; i < v1.getDim(); i++) {
                newComponents.add(v1.getComponents().get(i) + v2.getComponents().get(i));
            }

            return new Vectors(newComponents);
        }
        throw new IllegalArgumentException("Vectors have different dimensions.");
    }
```

Также реализуем метод, вычисляющий скалярное произведение векторов. Ему тоже нужны векторы одинаковых размерностей:
```java
    public static double scalarProduct(Vectors v1, Vectors v2) {
        double result = 0;

        if (v1.getDim() == v2.getDim()) {
            List<Double> newComponents = new ArrayList<>();

            for (int i = 0; i < v1.getDim(); i++) {
                result += v1.getComponents().get(i) * v2.getComponents().get(i);
            }

            return result;
        }
        throw new IllegalArgumentException("Vectors have different dimensions.");
    }
```

Переопределим метод __toString()__ для более удобного вывода. Вектор выводится в следующем формате:
`<Размерность>  <Компонента1> <Компонента2> ...` (Например: `3 1.1 2.2 3.3`)
Именно в таком формате с вектором будут работать методы 2 задания (для чтения и записи в потоки).
```java
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getDim());
        for (int i = 0; i < this.getDim(); i++) {
            sb.append(" ");
            sb.append(this.getComponents().get(i));
        }
        return sb.toString();
    }
```

## 2. Дополнение класса __Vectors__
### 2.1 Реализация требуемых методов
В класс __Vectors__ необзодимо добавить методы для работы с байтовыми и символьными потоками.
Метод __outputVector()__ записывает вектор в байтовый поток:
```java
    public static void outputVector(Vectors v, OutputStream out) {
        try {
            out.write(String.valueOf(v.toString()).getBytes());
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
```

Метод __inputVector()__ выполняет чтение вектора из байтового потока:
```java
    public static Vectors inputVector(InputStream in) {
        InputStreamReader isReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();

        try {
            sb.append(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return parseVectorFromString(sb.toString());
    }
```

Метод __writeVector()__ записывает вектор в символьный поток:
```java
    public static void writeVector(Vectors v, Writer out) throws IOException {
        out.write(v.toString());
        out.close();
    }
```

Метод __readVector()__ выполняет чтение вектора из символьного потока:
```java
    public static Vectors readVector(Reader in) throws IOException {
        char[] buffer = new char[100];
        in.read(buffer);
        String str = String.valueOf(buffer);

        return parseVectorFromString(str);
    }
```

Для чтения вектора из обоих потоков использовался служебный метод __parseVectorFromString()__:
```java
    private static Vectors parseVectorFromString(String str) {
        StringTokenizer st = new StringTokenizer(str, " ");

        int dimension = Integer.parseInt(st.nextToken());

        List<Double> vAttributes = new ArrayList<>();
        while (dimension > 0) {
            vAttributes.add(Double.parseDouble(st.nextToken()));
            dimension--;
        }

        return new Vectors(vAttributes);
    }
```

### 2.1 Тестирование класса
Используем класс __VectorsTest.java__ для тестирования написанного класса.
Чтение и запись потоков __System.in__  и __System.out__ соответственно:
```java
        Vectors v3 = Vectors.inputVector(System.in);
        Vectors.outputVector(v3, System.out);
```

Также попробуем использовать файловые потоки для чтения и записи вектора:
```java
        try {
            Reader input = new FileReader("input.txt");
            Vectors v4 = Vectors.readVector(input);
            Writer output = new FileWriter("output.txt");
            Vectors.writeVector(v4, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
```

## 3. Сериализация вектора
Были созданы реализации вектора на основе классов __LinkedList__ и __Array_. Рассмотрим каждую реализацию по отдельности:

### 3.1 LinkedListVector
Класс обладает единственным полем, его и будем сериализовывать:
```java
    private LinkedList list = new LinkedList<>();
```

Конструкторы класса:
```java
    public LinkedListVector() {
        this.list = new LinkedList<>();
    }
    
    @SafeVarargs
    public LinkedListVector(T... elements) {
        list.addAll(Arrays.asList(elements));
    }
```

Также переопределим метод __toString()__. Формат вывода --- такой же, как и в заданиях 1-2: `<Размерность>  <Компонента1> <Компонента2> ...` (Например: `3 1.1 2.2 3.3`)
```java
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(list.size());
        for (Object node : list) {
            sb.append(" ");
            sb.append(node);
        }
        return sb.toString();
    }
```

Метод __writeSerialized()__ сериализует список и записывает его в поток __ObjectOutput__:
```java
    public void writeSerialized(ObjectOutput out) {
        try {
            out.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
```

Метод __readSerialized()__ читает данные из потока __ObjectInput__ и десериализует их в список:
```java
    public void readSerialized(ObjectInput in) {
        try {
            this.list = (LinkedList) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
```

Для тестирования напишем класс __LinkedListSerializationTest.java__:
Тестирование сериализации и записи в файл:
```java
        LinkedListVector<Double> vec1 = new LinkedListVector<>(1.5, 2.5, 3.5);

        OutputStream fOut = null;
        try {
            fOut = new FileOutputStream("file3.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Cannot find file file3.txt");
        }

        try {
            ObjectOutput output = new ObjectOutputStream(fOut);
            vec1.writeSerialized(output);
            output.close();
            fOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
```

Тестирование чтения из файла и десериализации:
```java
        InputStream fIn = null;
        LinkedListVector vec2 = new LinkedListVector();

        try {
            fIn = new FileInputStream("file3.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            ObjectInput input = new ObjectInputStream(fIn);
            vec2.readSerialized(input);
            input.close();
            fIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
```

### 3.2 ArrayVector
Данный класс очень похож на класс __LinkedListVector__.
Поле класса:
```java
    private T [] array;
```
Конструкторы класса:
```java
    public ArrayVector() {
        this.array = null;
    }
    
    public ArrayVector(T...elements) {
        array = elements;
    }
```

Переопределение метода __toString()__:
```java
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(array.length);
        for (Object e : array) {
            sb.append(" ");
            sb.append(e);
        }
        return sb.toString();
    }
```

Метод __writeSerialized()__ сериализует массив и записывает его в поток __ObjectOutput__:
```java
    public void writeSerialized(ObjectOutput out) {
        try {
            out.writeObject(array);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
```

Метод __readSerialized()__ читает данные из потока __ObjectInput__ и десериализует их в массив:
```java
    public void readSerialized(ObjectInput in) {
        try {
            this.array = (T[]) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
```

## 4. MyClassToBePersisted
Необходимо реализовать класс __MyClassToBePersisted.java__, обладающий полями:
`<Поле профиля>`
`<Поле группы>`

Поля класса __MyClassToBePersisted.java__:
```java
    private String profile;
    private String group;
```

Конструкторы класса:
```java
    public MyClassToBePersisted() {
        this.profile = "";
        this.group = "";
    }

    public MyClassToBePersisted(String profile, String group) {
        this.profile = profile;
        this.group = group;
    }
```

Также для удобства тестирования переопределим метод __toString()__:
```java
    @Override
    public String toString() {
        return "profile: " + this.profile + "\n"
                + "group: " + this.group;
    }
```

### 4.1 SerializeMyClassToBePersisted.java
В основном методе этого класса необходимо создать экземпляр класса __MyClassToBePersisted__ и сеарилизовать его в файл:
```java
    public static void main(String[] args) {
        MyClassToBePersisted data = new MyClassToBePersisted("profile1", "group1");
        OutputStream fOut = null;
        try {
            fOut = new FileOutputStream("file4.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Cannot find file file4.txt");
        }

        try {
            ObjectOutput output = new ObjectOutputStream(fOut);
            output.writeObject(data);
            output.close();
            fOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
```

### 4.2 DeserializeMyClassToBePersisted.java
В основном методе этого класса необходимо считать сериализованный класс и десериализовать его в  экземпляр класса __MyClassToBePersisted__:
```java
    public static void main(String[] args) {
        MyClassToBePersisted data = new MyClassToBePersisted();

        InputStream fIn = null;
        try {
            fIn = new FileInputStream("file4.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            ObjectInput input = new ObjectInputStream(fIn);
            data = (MyClassToBePersisted) input.readObject();
            input.close();
            fIn.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
    }
```

