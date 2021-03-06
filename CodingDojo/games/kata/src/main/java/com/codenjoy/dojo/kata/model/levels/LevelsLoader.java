package com.codenjoy.dojo.kata.model.levels;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 - 2017 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.kata.model.levels.algorithms.*;
import org.reflections.Reflections;

import java.util.*;

/**
 * Created by indigo on 2017-03-04.
 */
public class LevelsLoader {

    public static List<Level> getAlgorithms() {

        List<Level> result = new LinkedList<Level>();
        result.add(new HelloWorldAlgorithm());
        result.add(new FizzBuzzAlgorithm());
        result.add(new SumSquareDifferenceAlgorithm());
        result.add(new Sequence1Algorithm());
        result.add(new FibonacciNumbersAlgorithm());
        result.add(new PrimeFactoryAlgorithm());
        result.add(new PowerDigitSumAlgorithm());
        result.add(new MakeBricksAlgorithm());
        result.add(new FactorialAlgorithm());
        result.add(new ReverseAddPalindromeAlgorithm());
        result.add(new Sequence2Algorithm());
        result.add(new XthPrimeAlgorithm());
        result.add(new LongDivisionAlgorithm());


        return result;
    }

    public static List<Level> getAlgorithmsOrederByComplexity() {
        List<Class<? extends Level>> classes = loadClasses();

        List<Level> result = createLevels(classes);
        result.add(new FizzBuzzAlgorithm());

        sortByComplexity(result);

        return result;
    }


    private static void sortByComplexity(List<Level> result) {
        Collections.sort(result, new Comparator<Level>() {
            @Override
            public int compare(Level o1, Level o2) {
                return Integer.compare(o1.complexity(), o2.complexity());
            }
        });
    }

    private static List<Level> createLevels(List<Class<? extends Level>> classes) {
        List<Level> result = new LinkedList<>();
        for (Class<? extends Level> clazz : classes) {
            try {
                Level level = clazz.newInstance();
                result.add(level);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static List<Class<? extends Level>> loadClasses() {
        List<Class<? extends Level>> classes = new LinkedList<>();
        classes.addAll(findInPackage("com"));
        classes.addAll(findInPackage("org"));
        classes.addAll(findInPackage("net"));

        classes.remove(Level.class);
        classes.remove(NullLevel.class);
        classes.remove(WaitLevel.class);
        classes.remove(LevelsPoolImpl.class);
        classes.remove(AlgorithmLevelImpl.class);
        classes.remove(QuestionAnswerLevelImpl.class);
        for (Class aClass : classes.toArray(new Class[0])) {
            String name = aClass.getName();
            if (name.contains("Test$") || name.contains("TestLevel")) {
                classes.remove(aClass);
            }
        }

        return classes;
    }

    private static Collection<? extends Class<? extends Level>> findInPackage(String packageName) {
        return new Reflections(packageName).getSubTypesOf(Level.class);
    }
}
