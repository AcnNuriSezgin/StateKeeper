package nurisezgin.com.android.statekeeper.reflection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by nuri on 15.07.2018
 */
public class ReflectionAdapterImpTest {

    @Test
    public void should_ListTypeOfCollectionCorrect() {
        final boolean expected = true;
        List list = new ArrayList<>();

        boolean actual = new ReflectionAdapterImp(list)
                .isTypeOfThatInterface(Collection.class);

        assertThat(actual, is(expected));
    }

    @Test
    public void should_MapNotTypeOfCollectionCorrect() {
        final boolean expected = false;
        Map map = new HashMap();

        boolean actual = new ReflectionAdapterImp(map)
                .isTypeOfThatInterface(Collection.class);

        assertThat(actual, is(expected));
    }

    @Test
    public void should_ManipulateFieldValueCorrect() {
        final String fieldAge = "age";
        final String name = "John";
        final int age = 12;
        final int expected = 15;

        Person person = Person.newPerson(name, age);

        new ReflectionAdapterImp(person)
                .forEachField(
                        field -> {
                            if (field.getName().equals(fieldAge)) {
                                field.trySetValue(expected);
                            }
                        });

        int actual = person.age;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void should_ManipulateSuperClassFieldValueCorrect() {
        final String fieldAge = "age";
        final String name = "John";
        final int age = 12;
        final String school = "School-1";
        final int expected = 15;

        Student student = Student.newStudent(name, age, school);

        new ReflectionAdapterImp(student)
                .forEachField(
                        field -> {
                            if (field.getName().equals(fieldAge)) {
                                field.trySetValue(expected);
                            }
                        });

        int actual = student.age;

        assertThat(actual, is(equalTo(expected)));
    }

    public static class Student extends Person{

        public String school;

        public static Student newStudent(String name, int age, String school) {
            Student student = new Student();
            student.name = name;
            student.age = age;
            student.school = school;
            return student;
        }

    }

    public static class Person {

        public String name;
        public int age;

        public static Person newPerson(String name, int age) {
            Person person = new Person();
            person.name = name;
            person.age = age;
            return person;
        }

    }

}