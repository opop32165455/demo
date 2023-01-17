package com.function.functional.vavr;

import io.vavr.Predicates;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Try;
import io.vavr.control.Validation;

import static io.vavr.API.*;

/**
 * @author zhangxuecheng4441
 * @date 2021/5/30/030 13:32
 */
public class VavrFunction {

    public static void main(String[] args) {

        Integer i = 0;
        Integer null1 = null;
        String of = Match(i).of(
                Case($(0), () -> i + " Match = 0"),
                Case($(0), () -> i + " Match = 000"),
                Case($(1), () -> i + " Match = 1")
        );
        System.out.println("of = " + of);

        String s = Option(null1).toString();
        System.out.println("s = " + s);

        Integer orElse = Option(null1).getOrElse(10);
        System.out.println("orElse = " + orElse);

        Try<Integer> result = Try(() -> 1 / 10);
        result.onFailure(Throwable::printStackTrace);

        result.onSuccess(res -> {
            System.out.println("result = " + res);
        });

        List<Integer> integers = List.ofAll(result);
        Void voidEntity = Match(i).of(
                Case($(Predicates.isIn(1, 3, 5, 7, 9)), o -> run(() -> {
                    System.out.println("o  奇数 = " + o);
                })),
                Case($(Predicates.isIn(0, 2, 4, 6, 8)), o -> {
                    System.out.println("o  偶数 = " + o);
                    return null;
                }),
                Case($(), o -> run(() -> {
                    throw new RuntimeException();
                }))
        );
        PersonValidator personValidator = new PersonValidator();

        Validation<Seq<String>, Person> valid =
                personValidator.validatePerson("John Doe", 30);
        System.out.println("valid = " + valid.get().toString());

        Validation<Seq<String>, Person> invalid =
                personValidator.validatePerson("John? Doe!4", -1);

        System.out.println("invalid = " + invalid.get());

    }


    static class PersonValidator {
        String NAME_ERR = "Invalid characters in name: ";
        String AGE_ERR = "Age must be at least 0";

        public Validation<Seq<String>, Person> validatePerson(String name, int age) {
            return Validation.combine(validateName(name), validateAge(age)).ap((t1, t2) -> {
                return new Person();
            });
        }

        private Validation<String, String> validateName(String name) {
            String invalidChars = name.replaceAll("[a-zA-Z ]", "");
            return invalidChars.isEmpty() ?
                    Validation.valid(name)
                    : Validation.invalid(NAME_ERR + invalidChars);
        }

        private Validation<String, Integer> validateAge(int age) {
            return age < 0 ? Validation.invalid(AGE_ERR)
                    : Validation.valid(age);
        }


    }


}
