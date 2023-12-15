package ru.lesson.stream;

import ru.lesson.stream.dto.Employee;
import ru.lesson.stream.dto.PositionType;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

public class LessonStreamApi {

    /**
     * Задача №1.
     * Получить список сотрудников, у которых рейтинг {@link Employee#getRating()} больше 50.
     * Важно: Необходимо учесть, что List<Employee> employees может содержать дублирующие записи.
     */
    public List<Employee> task1(List<Employee> employees) {
        return employees.stream()
                .distinct()
                .filter((o) -> o.getRating() > 50)
                .collect(toList());
    }

    /**
     * Задача №2.
     * Получить список сотрудников (List<String> - формат строки {@link Employee#getName()}"="{@link Employee#getRating()})
     * У которых рейтинг {@link Employee#getRating()} меньше 50.
     */
    public List<String> task2(List<Employee> employees) {
        return employees.stream()
                .distinct()
                .filter((o) -> o.getRating() < 50)
                .map((o) -> o.getName() + "=" + o.getRating())
                .collect(toList());
    }

    /**
     * Задача №3.
     * Получить средний рейтнг всех сотрудников.
     */
    public double task3(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.averagingDouble(Employee::getRating));
    }

    /**
     * Задача №4.
     * Получить общий список сотрудников из разных отделов и
     * отсортировать его по убыванию рейтинга {@link Employee#getRating()}.
     * Важно: необходимо учесть, что один сотрудник может числиться в разных отделах.
     * Необходимо устранить дублирование.
     */
    public List<Employee> task4(List<List<Employee>> employeeDepartments) {
        return employeeDepartments.stream()
                .flatMap(Collection::stream)
                .distinct()
                .sorted(Comparator.comparingInt(Employee::getRating).reversed())
                .collect(toList());
    }

    /**
     * Задача №5.
     * Предположим, что требуется выводить список сотрудников на веб-страницу.
     * Необходимо реализовать построчный вывод (пагинацию).
     * <p>
     * Пример:
     * Если number = 1, size = 3, то результирующий список будет
     * Employee{id=1, name='Name1', rating=11}, Employee{id=2, name='Name2', rating=12}, Employee{id=3, name='Name3', rating=13}
     * <p>
     * Если number = 2, size = 3, то результирующий список
     * Employee{id=4, name='Name4', rating=14}, Employee{id=5, name='Name5', rating=15}, Employee{id=6, name='Name6', rating=16}
     */
    public List<Employee> task5(List<Employee> employees, int number, int size) {
        if (number <= 0) {
            throw new IllegalArgumentException(Integer.toString(number));
        }
        return employees.stream()
                .skip((long) (number - 1) * size)
                .limit(size)
                .collect(toList());
    }

    /**
     * Задача №6.
     * Получить имена сотрудников в String: начинается строка с '[',
     * затем следуют имена сотрудников через ', ' и заканчивается строка ']'.
     * <p>
     * Пример результата: [Ivan, Olga, John]
     */
    public String task6(List<Employee> employees) {
        return employees.stream()
                .map(Employee::getName)
                .collect(toList())
                .toString().replace("\\[", "]");
    }

    /**
     * Задача №7.
     * Проверить наличие дублирующихся имен сотрудников {@link Employee#getName()} в списке.
     * <p>
     * Если дубли существуют - вернуть true, если дублей нет - вернуть false
     */
    public boolean task7(List<Employee> employees) {
        var uniqueNames = new HashSet<String>();
        return employees.stream()
                .map(Employee::getName)
                .anyMatch(o -> !(uniqueNames.add(o)));
    }

    /**
     * Задача №8.
     * Получить средний рейтинг {@link Employee#getRating()} по каждой
     * должности сотрудника {@link Employee#getPositionType()}
     */
    public Map<PositionType, Double> task8(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::getPositionType,
                        averagingDouble(Employee::getRating)
                        )
                );
    }

    /**
     * Задача 9.
     * Получить словарь, который содержит две записи:
     * Ключи: true - эффективные и false - неэффективные сторудники.
     * Значение: количество сотрудников для каждой из двух групп.
     * Сотрудник является эффективным, если его рейтинг больше 50.
     */
    public Map<Boolean, Long> task9(List<Employee> employees) {
        return employees.stream()
                .collect(partitioningBy(
                        x -> x.getRating() > 50,
                        counting()
                        )
                );

    }

    /**
     * Задача 10.
     * Получить словарь, который содержит две записи:
     * Ключи: true - эффективные и false - неэффективные сотрудники.
     * Значение: список имен сотрудников через ', '.
     * Сотрудник является эффективным, если его рейтинг больше 50.
     */
    public Map<Boolean, String> task10(List<Employee> employees) {
        return employees.stream()
                .collect(partitioningBy(
                        o -> o.getRating() > 50,
                        mapping(Employee::getName, joining(", "))
                        )
                );

    }

}
