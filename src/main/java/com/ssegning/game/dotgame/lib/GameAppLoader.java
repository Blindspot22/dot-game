package com.ssegning.game.dotgame.lib;

import com.ssegning.game.dotgame.model.InitMethod;
import com.ssegning.game.dotgame.model.Loadable;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class GameAppLoader {
    private static final ConcurrentHashMap<String, Object> loadedClassesInstances = new ConcurrentHashMap<>();
    private static final Reflections reflections = new Reflections("com.ssegning.game.dotgame");

    private GameAppLoader() {
    }

    public static void setupApp() throws Exception {
        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Loadable.class)) {
            setupInstance(clazz);
        }
        log.info("Game started");
    }

    private static <T> Object createInstance(Class<T> clazz) throws Exception {
        final var constructors = clazz.getConstructors();
        if (constructors.length != 1) {
            throw new IllegalStateException("Must have more than one constructor");
        }

        final var declaredConstructor = constructors[0];
        final var params = new ArrayList<>();
        for (final var parameterType : declaredConstructor.getParameterTypes()) {
            final var paramInstance = setupInstance(parameterType);
            params.add(paramInstance);
        }

        return declaredConstructor.newInstance(params.toArray());
    }

    private static <T> Object setupInstance(Class<T> clazz) throws Exception {
        final var annotation = clazz.getAnnotation(Loadable.class);
        final var name = annotation != null ? annotation.value() : clazz.getName();

        final var key = "%s#%s".formatted(name, clazz.getName());
        if (loadedClassesInstances.containsKey(key)) {
            return loadedClassesInstances.get(key);
        }

        final var instance = createInstance(clazz);
        callInit(instance);

        for (final var pClazz : getClassAndParents(instance)) {
            final var pKey = "%s#%s".formatted(name, pClazz.getName());
            if (loadedClassesInstances.contains(pKey)) {
                break;
            }

            loadedClassesInstances.put(pKey, instance);
        }

        return instance;
    }

    private static <T> Set<Class<?>> getClassAndParents(T instance) {
        final var list = new HashSet<Class<?>>();
        var c = instance.getClass();
        while (c != null) {
            list.add(c);
            c = c.getSuperclass();
        }
        return list;
    }

    private static void callInit(Object instance) throws Exception {
        for (final var declaredMethod : instance.getClass().getDeclaredMethods()) {
            final var annotation = declaredMethod.getAnnotation(InitMethod.class);
            if (annotation != null) {
                declaredMethod.invoke(instance);
            }
        }
    }
}
