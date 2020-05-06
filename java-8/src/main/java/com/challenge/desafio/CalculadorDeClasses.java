package com.challenge.desafio;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class CalculadorDeClasses implements Calculavel {

    @Override
    public BigDecimal somar (Object object) {
        return computar(object, Somar.class);
    }

    @Override
    public BigDecimal subtrair (Object object) {
        return computar(object, Subtrair.class);
    }

    @Override
    public BigDecimal totalizar (Object object) {
        return somar(object).subtract(subtrair(object));
    }

    private BigDecimal computar (Object object, Class annotation) {
        BigDecimal result = BigDecimal.ZERO;

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(annotation) && field.getType().equals(BigDecimal.class)) {
                try {
                    result = result.add((BigDecimal) field.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

}