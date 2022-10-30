# Отчёт к учебному практическому заданию №2-2 #
_Работу выполнил Артамонов Кирилл, студент группы Б01-007_
Отчёт и программный код можно найти на [`GitHub`](https://github.com/ksartamonov/Netcracker-Special-Course/tree/master/PracticalTask2-2).

## 1. Печать собственного кода
Необходимо разработать программу, которая выводит собственный код. 
В методе __main()__ создается экземпляр класса __SourceCodePrinter__ (для удобства определения названия класса):
```java
    public static void main(String[] args) {
        SourceCodePrinter scp = new SourceCodePrinter();
    }
```

### 1.1 Служебный класс FileSearch.java.
Для поиска необходимого файла используется слежубный класс __FileSearch.java__:
```java
public class FileSearch implements FileVisitor<Path> {

    private final String fileName;
    private final Path startDir;

    private Path resultPath;

    public FileSearch(String fileName, Path startDir) {
        this.fileName = fileName;
        this.startDir = startDir;
    }
    public Path getResultPath() {
        return resultPath;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String fileName = file.getFileName().toString();
        if (this.fileName.equals(fileName)) {
            this.resultPath = file;
            return TERMINATE;
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.out.println("Cannot access file: " + file.toString() + ".");
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        boolean finishedSearch = false;
        try {
            finishedSearch = Files.isSameFile(dir, this.startDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (finishedSearch) {
            System.out.println("File:" + this.fileName + " not found.");
            return TERMINATE;
        }
        return CONTINUE;
    }
}
```
### 1.2 Основной класс SourceCodePrinter.java 
В конструкторе выполняется основная работаа по печати source-кода программы:
```java
    public SourceCodePrinter() {
        Path curDirectory = Paths.get("").toAbsolutePath(); // getting current directory
        String curFileName = this.getClass().getSimpleName() + ".java"; // getting current class name

        FileSearch fs = new FileSearch(curFileName, curDirectory); // searching our class in directory
        Path curFilePath = null;
        try { // getting our file path
            Files.walkFileTree(curDirectory, fs);
            curFilePath = fs.getResultPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(readFile(curFilePath.toString())); // printing source code
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file by path: " + curFilePath.toString());
        }
    }
```

Для чтения файла в строку используется метод __readFile()__:
```java
    private static String readFile(String path) {
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + path + ".");
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }
```

## 2. Запись и чтение чисел из файла
Необходимо разработать программу, которая записывает в типизированный файл
целые числа, затем считывает и рекуррентно рассчитывает среднее.

### 2.1 Класс NumberReaderWriter.java
Основной класс, реализующий данное задание.
Содержимое метода __main()__:
```java
    public static void main(String[] args) {
        // Creating list to write into file
        List<Integer> numbers = new ArrayList<>();
        int n = 100000;
        for (int i = 0; i < n; i++) {
            numbers.add(i);
        }

        Path path = Paths.get("src/main/resources/file2.txt");

        // writing
        writeNumbersToFile(numbers, path);

        // reading into byte array
        byte[] data = readBytesFromFile(path);
        try {
            printStat(Arrays.asList(toObjects(data)), getIntFromBytes(data), getFloatFromBytes(data)); // printing required statistics
        } catch (IOException e) {
            throw new RuntimeException("Problem with DataInputStream");
        }
    }
```

Далее опишем служебные методы. Метод __writeNumbersToFile()__ записывает список чисел __list__ в файл, находящийся по пути __path__:
```java
    private static void writeNumbersToFile(List<Integer> list, Path path) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            for (Integer integer : list) {
                writer.write(String.valueOf(integer));
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
```

Для считывания байтов из файла используется метод __readBytesFromFile()__:
```java
    private static byte[]  readBytesFromFile(Path path) {
        byte[] data;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
```

Для преобразования считанных байтов в числа типа int и float используются следующие два метода:
```java
    private static List<Integer> getIntsFromBytes(byte[] data) throws IOException {
        List<Integer> intList= new ArrayList<>();
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        while (dis.available() >=  4) { // int takes 4 bytes
            intList.add(dis.readInt());
        }
        dis.close();
        return intList;
    }
    
    private static List<Float> getFloatsFromBytes(byte[] data) throws IOException {
        List<Float> intList= new ArrayList<>();
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        while (dis.available() >=  4) { // float takes 4 bytes
            intList.add(dis.readFloat());
        }
        dis.close();
        return intList;
    }
```

Также нам потребуется метод, выполняющий преобразование `byte[] -> Byte[]`:
```java
    private static Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytesObj = new Byte[bytesPrim.length];
        Arrays.setAll(bytesObj, n -> bytesPrim[n]);
        return bytesObj;
    }
```


Для вывода на экран  статистики, требуемой в задании используется метод __printStat()__:
```java
    private static void printStat(List<Byte> byteList, List<Integer> intList, List<Float> floatList) {
        ElementsStat<Byte> byteElementsStat = new ElementsStat<>(byteList);
        System.out.println("Byte elements:");
        System.out.println(byteElementsStat);

        ElementsStat<Integer> intElementsStat = new ElementsStat<>(intList);
        System.out.println("Integer elements:");
        System.out.println(intElementsStat);

        ElementsStat<Float> floatElementsStat = new ElementsStat<>(floatList);
        System.out.println("Float elements:");
        System.out.println(floatElementsStat);
    }
```

### 2.2 Класс ElementsStat.java
Вынесем в отдельный класс методы, касающиеся статистики элементов конкретного типа (количество, среднее арифметическое второй половины списка элементов, среднее арифметическое третьей четверти).

Данный класс обладает следующими полями:
```java
    private final int quantity; // amount of numbers
    private final Object secondHalfAverage; // average element of second half
    private final Object thirdQuarterAverage; // average element of the third quarter
```

Все поля инициализируются в конструкторе:
```java
    public ElementsStat(List<T> elements) {
        this.quantity = elements.size();

        T sample = elements.get(0);
        if(sample instanceof Byte)
        {
            this.secondHalfAverage = getAverageByte((List<Byte>) elements.subList((this.quantity / 2), this.quantity - 1));
            this.thirdQuarterAverage = getAverageByte((List<Byte>)elements.subList((this.quantity / 2), this.quantity * 3 / 4));
        }
        else if (sample instanceof Integer)
        {
            this.secondHalfAverage = getAverageInteger((List<Integer>) elements.subList((this.quantity / 2), this.quantity - 1));
            this.thirdQuarterAverage = getAverageInteger((List<Integer>)elements.subList((this.quantity / 2), this.quantity * 3 / 4));
        }
        else if (sample instanceof Float)
        {
            this.secondHalfAverage = getAverageFloat((List<Float>) elements.subList((this.quantity / 2), this.quantity - 1));
            this.thirdQuarterAverage = getAverageFloat((List<Float>)elements.subList((this.quantity / 2), this.quantity * 3 / 4));
        }

        else
        {
            throw new IllegalStateException("Constructor must be initialized with either of Double, Integer or Float list");
        }

    }
```

Следующие методы определяют средние элементы дляя списков List<Byte>, List<Integer> и List<Float>:
```java
    private Integer getAverageByte(List<Byte> list) {
        Integer sum = 0;

        for(Byte e : list) {
            sum += e;
        }

        return sum / list.size();
    }
    
    private Integer getAverageInteger(List<Integer> list) {
        Integer sum = 0;

        for(Integer e : list) {
            sum += e;
        }

        return sum / list.size();
    }
    
    private Float getAverageFloat(List<Float> list) {
        Float sum = (float) 0;

        for(Float e : list) {
            sum += e;
        }

        return sum / list.size();
    }
```

Метод __toString()__ переопределим для более удобного вывода статистики:
```java
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Numbers amount: ")
                .append(this.quantity)
                .append('\n');
        sb.append("Second Half average: ")
                .append(this.secondHalfAverage)
                .append('\n');
        sb.append("Third quarter average: ")
                .append(this.thirdQuarterAverage)
                .append('\n');
        return sb.toString();
    }
```

## 3. JavaDocCopier.java
Необходимо реализовать класс, копирующий документацию по Java в другой файл и считывающий символьный поток до тех пор, пока слово java не встретится трижды.
Метод __main()__:
```java
    public static void main(String[] args) {
        Path pathFrom = Paths.get("src/main/resources/index.html");
        Path pathInto = Paths.get("src/main/resources/task3_out.txt");

        writeLinesToFile(
                pathInto,
                readUntil(pathFrom, "java", 3)
        );
    }
```

Метод __readUntil()__ считывает текстовый файл адреса __path__ до тех пор, пока слово __word__ не встретится __maxWordMentions__ раз:
```java
    private static List<String> readUntil(Path path, String word, int maxWordMentions) {
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read from file.");
        }

        List<String> lines = new ArrayList<>();
        int wordMentions = 0;
        while (wordMentions < maxWordMentions) {
            String nextLine = null;
            try {
                nextLine = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Cannot read a line.");
            }
            if (nextLine.toLowerCase().contains(word)) {
                StringBuilder sb = new StringBuilder(); // line can contain more than one chosen word^ builder will contain only
                String remains = nextLine; // remaining part of String
                String buffer = null;
                while (remains.toLowerCase().contains(word) && wordMentions != maxWordMentions) {
                    buffer = remains.substring(0, remains.indexOf(word) + word.length());
                    remains = remains.substring(buffer.length());
                    sb.append(buffer);
                    wordMentions++;
                    if (wordMentions != maxWordMentions) { // adding rest part of the string if needed
                        sb.append(remains);
                    }
                }
                lines.add(sb.toString()); // Adding only required part of line
            } else { // just adding full line
                lines.add(nextLine);
            }
        }
        return lines;
    }
```

Метод __writeLinesToFile()__  записывает список строк в файл:
```java
    private static void writeLinesToFile(Path path, List<String> lines) {
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter finalWriter = writer;
        lines.forEach(s -> {
            try {
                finalWriter.write(s);
                finalWriter.write("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            writer.close();
            finalWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
```

## 4. CharacterFrequencyCounter.java
Необходимо написать пример, подсчитывающий, сколько раз конкректный символ появляется в файле, символ передается в аргументах командной строки:
```java
    public static void main(String[] args) {
        Path path = Paths.get("src/main/java/task4/CharacterFrequencyCounter.java"); // counting characters in file of this class
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read from file.");
        }
        int charCounter = 0;
        int c; // read character

        while (true) {
            try {
                if ((c = reader.read()) == -1) break; // if reached the end
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if ((char) c == args[0].charAt(0)) {
                charCounter++;
            }
        }
        System.out.println(args[0].charAt(0));
        System.out.println("Character " + args[0].charAt(0) + " occurs in file " + charCounter + " times.");
    }
```